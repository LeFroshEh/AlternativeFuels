package net.LeFroshEh.AlternativeFuels.mixin;

import blusunrize.immersiveengineering.api.crafting.MixerRecipe;
import net.LeFroshEh.AlternativeFuels.mixer.TwoFluidMixerSupport;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MixerRecipe.class)
public class MixerRecipeFindRecipeMixin {
    @Inject(method = "findRecipe", at = @At("HEAD"), cancellable = true)
    private static void alternativefuels$findTwoFluidRecipe(
            Level level,
            FluidStack fluid,
            NonNullList<ItemStack> itemInputs,
            CallbackInfoReturnable<RecipeHolder<MixerRecipe>> cir
    ) {
        RecipeHolder<MixerRecipe> recipe = TwoFluidMixerSupport.findRecipe(
                level,
                TwoFluidMixerSupport.CURRENT_MIXER_TANK.get(),
                fluid,
                itemInputs
        );

        if (recipe != null) {
            cir.setReturnValue(recipe);
        }
    }
}
