package net.LeFroshEh.AlternativeFuels.mixin;

import blusunrize.immersiveengineering.api.multiblocks.blocks.env.IMultiblockContext;
import blusunrize.immersiveengineering.common.blocks.multiblocks.logic.mixer.MixerLogic;
import net.LeFroshEh.AlternativeFuels.mixer.TwoFluidMixerSupport;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MixerLogic.class)
public class MixerLogicMixin {
    @Inject(method = "enqueueNewRecipes", at = @At("HEAD"))
    private void alternativefuels$beginRecipeLookup(
            MixerLogic.State state,
            Level level,
            CallbackInfoReturnable<?> cir
    ) {
        TwoFluidMixerSupport.CURRENT_MIXER_TANK.set(state.tank);
    }

    @Inject(method = "enqueueNewRecipes", at = @At("RETURN"))
    private void alternativefuels$endRecipeLookup(
            MixerLogic.State state,
            Level level,
            CallbackInfoReturnable<?> cir
    ) {
        TwoFluidMixerSupport.CURRENT_MIXER_TANK.remove();
    }

    @Inject(method = "tickServer", at = @At("HEAD"))
    private void alternativefuels$beginServerTick(
            IMultiblockContext<MixerLogic.State> context,
            CallbackInfo ci
    ) {
        TwoFluidMixerSupport.CURRENT_LEVEL.set(context.getLevel().getRawLevel());
    }

    @Inject(method = "tickServer", at = @At("RETURN"))
    private void alternativefuels$endServerTick(
            IMultiblockContext<MixerLogic.State> context,
            CallbackInfo ci
    ) {
        TwoFluidMixerSupport.CURRENT_LEVEL.remove();
    }


    @Inject(method = "outputFluids", at = @At("HEAD"), cancellable = true)
    private void alternativefuels$holdBackIngredients(
            MixerLogic.State state,
            boolean foundRecipe,
            CallbackInfoReturnable<Boolean> cir
    ) {
        if (state.outputAll) {
            return;
        }
        Level level = TwoFluidMixerSupport.CURRENT_LEVEL.get();
        if (level != null && TwoFluidMixerSupport.shouldHoldBackOutput(level, state.tank)) {
            cir.setReturnValue(false);
        }
    }
}
