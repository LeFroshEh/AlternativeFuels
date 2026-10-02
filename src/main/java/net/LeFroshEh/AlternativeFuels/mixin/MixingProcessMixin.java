package net.LeFroshEh.AlternativeFuels.mixin;

import blusunrize.immersiveengineering.api.crafting.MixerRecipe;
import blusunrize.immersiveengineering.common.blocks.multiblocks.logic.mixer.MixingProcess;
import blusunrize.immersiveengineering.common.blocks.multiblocks.process.MultiblockProcess;
import blusunrize.immersiveengineering.common.util.inventory.MultiFluidTank;
import net.LeFroshEh.AlternativeFuels.mixer.TwoFluidMixerRecipe;
import net.LeFroshEh.AlternativeFuels.mixer.TwoFluidMixerSupport;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MixingProcess.class)
public class MixingProcessMixin {
    @Shadow @Final private MultiFluidTank tank;

    /**
     * How much of the recipe output this process has produced so far.
     * Rebuilt from processTick at the start of every tick, so it stays correct after a world reload.
     */
    @Unique
    private int alternativefuels$produced = 0;

    @Inject(method = "canProcess", at = @At("HEAD"), cancellable = true)
    private void alternativefuels$checkBothFluids(
            blusunrize.immersiveengineering.common.blocks.multiblocks.process.ProcessContext.ProcessContextInMachine<MixerRecipe> context,
            Level level,
            CallbackInfoReturnable<Boolean> cir
    ) {
        MixerRecipe baseRecipe = ((MixingProcess)(Object)this).getRecipe(level);
        if (!(baseRecipe instanceof TwoFluidMixerRecipe recipe)) {
            return;
        }

        int maxTicks = ((MixingProcess)(Object)this).getMaxTicks(level);
        int energyPerTick = maxTicks > 0 ? recipe.getTotalProcessEnergy() / maxTicks : 0;
        if (context.getEnergy().extractEnergy(energyPerTick, true) != energyPerTick) {
            cir.setReturnValue(false);
            return;
        }

        boolean firstAvailable = !tank.drain(
                recipe.fluidInput.ingredient(),
                1,
                IFluidHandler.FluidAction.SIMULATE
        ).isEmpty();
        boolean secondAvailable = !tank.drain(
                recipe.getFluidInput2().ingredient(),
                1,
                IFluidHandler.FluidAction.SIMULATE
        ).isEmpty();

        cir.setReturnValue(firstAvailable && secondAvailable);
    }

    @Inject(method = "doProcessTick", at = @At("HEAD"))
    private void alternativefuels$beginProcess(
            blusunrize.immersiveengineering.common.blocks.multiblocks.process.ProcessContext.ProcessContextInMachine<MixerRecipe> context,
            blusunrize.immersiveengineering.api.multiblocks.blocks.env.IMultiblockLevel level,
            CallbackInfo ci
    ) {
        MixingProcess self = (MixingProcess)(Object)this;
        MixerRecipe baseRecipe = self.getRecipe(level.getRawLevel());
        if (baseRecipe instanceof TwoFluidMixerRecipe recipe) {
            int maxTicks = self.getMaxTicks(level.getRawLevel());
            int outputAmount = recipe.fluidOutput.getAmount();
            // processTick still holds the value from BEFORE this tick's increment here
            int tickBefore = ((MultiblockProcess<?, ?>) (Object) this).processTick;
            this.alternativefuels$produced = maxTicks > 0
                    ? (int) ((long) tickBefore * outputAmount / maxTicks)
                    : 0;
            TwoFluidMixerSupport.CURRENT_PROCESS.set(new TwoFluidMixerSupport.ProcessInfo(recipe, 0));
        } else {
            TwoFluidMixerSupport.CURRENT_PROCESS.remove();
        }
    }

    @Inject(method = "doProcessTick", at = @At("RETURN"))
    private void alternativefuels$endProcess(
            blusunrize.immersiveengineering.common.blocks.multiblocks.process.ProcessContext.ProcessContextInMachine<MixerRecipe> context,
            blusunrize.immersiveengineering.api.multiblocks.blocks.env.IMultiblockLevel level,
            CallbackInfo ci
    ) {
        TwoFluidMixerSupport.CURRENT_PROCESS.remove();
    }

    @Redirect(
            method = "doProcessTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lblusunrize/immersiveengineering/common/util/inventory/MultiFluidTank;drain(Lnet/neoforged/neoforge/fluids/crafting/FluidIngredient;ILnet/neoforged/neoforge/fluids/capability/IFluidHandler$FluidAction;)Lnet/neoforged/neoforge/fluids/FluidStack;"
            )
    )
    private FluidStack alternativefuels$drainTwoInputs(
            MultiFluidTank tank,
            net.neoforged.neoforge.fluids.crafting.FluidIngredient ingredient,
            int originalAmount,
            IFluidHandler.FluidAction action
    ) {
        TwoFluidMixerSupport.ProcessInfo info = TwoFluidMixerSupport.CURRENT_PROCESS.get();
        if (info == null || action != IFluidHandler.FluidAction.EXECUTE) {
            return tank.drain(ingredient, originalAmount, action);
        }

        TwoFluidMixerRecipe recipe = info.recipe();
        int outputAmount = recipe.fluidOutput.getAmount();
        if (outputAmount <= 0 || originalAmount <= 0) {
            return FluidStack.EMPTY;
        }

        // Cumulative accounting: after producing N of the output, exactly
        // input1 * N / output and input2 * N / output must have been consumed.
        int producedBefore = this.alternativefuels$produced;
        int producedAfter = Math.min(outputAmount, producedBefore + originalAmount);
        int firstAmount = recipe.fluidInput.amount() * producedAfter / outputAmount
                - recipe.fluidInput.amount() * producedBefore / outputAmount;
        int secondAmount = recipe.getFluidInput2().amount() * producedAfter / outputAmount
                - recipe.getFluidInput2().amount() * producedBefore / outputAmount;

        if (firstAmount > 0) {
            FluidStack firstSimulated = tank.drain(
                    recipe.fluidInput.ingredient(), firstAmount, IFluidHandler.FluidAction.SIMULATE
            );
            if (firstSimulated.getAmount() < firstAmount) {
                return FluidStack.EMPTY;
            }
        }
        if (secondAmount > 0) {
            FluidStack secondSimulated = tank.drain(
                    recipe.getFluidInput2().ingredient(), secondAmount, IFluidHandler.FluidAction.SIMULATE
            );
            if (secondSimulated.getAmount() < secondAmount) {
                return FluidStack.EMPTY;
            }
        }

        if (firstAmount > 0) {
            tank.drain(recipe.fluidInput.ingredient(), firstAmount, IFluidHandler.FluidAction.EXECUTE);
        }
        if (secondAmount > 0) {
            tank.drain(recipe.getFluidInput2().ingredient(), secondAmount, IFluidHandler.FluidAction.EXECUTE);
        }

        this.alternativefuels$produced = producedAfter;
        return recipe.fluidOutput.copyWithAmount(originalAmount);
    }
}
