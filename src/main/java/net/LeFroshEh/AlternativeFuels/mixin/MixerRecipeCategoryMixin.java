package net.LeFroshEh.AlternativeFuels.mixin;

import blusunrize.immersiveengineering.api.crafting.MixerRecipe;
import blusunrize.immersiveengineering.common.util.compat.jei.JEIHelper;
import blusunrize.immersiveengineering.common.util.compat.jei.mixer.MixerRecipeCategory;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.neoforge.NeoForgeTypes;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.LeFroshEh.AlternativeFuels.mixer.TwoFluidMixerRecipe;
import net.neoforged.neoforge.fluids.FluidType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

/**
 * Makes IE's normal "Mixer" JEI tab show BOTH fluid inputs of a two-fluid recipe.
 * The single input tank is split into two side-by-side tanks. All other recipes are left alone.
 */
@Mixin(value = MixerRecipeCategory.class, remap = false)
public class MixerRecipeCategoryMixin {
    @Shadow private IDrawableStatic tankOverlay;

    @Inject(
            method = "setRecipe(Lmezz/jei/api/gui/builder/IRecipeLayoutBuilder;Lblusunrize/immersiveengineering/api/crafting/MixerRecipe;Lmezz/jei/api/recipe/IFocusGroup;)V",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private void alternativefuels$twoFluidLayout(
            IRecipeLayoutBuilder builder,
            MixerRecipe recipe,
            IFocusGroup focuses,
            CallbackInfo ci
    ) {
        if (!(recipe instanceof TwoFluidMixerRecipe twoFluid)) {
            return;
        }

        int tankSize = Math.max(
                2 * FluidType.BUCKET_VOLUME,
                Math.max(
                        Math.max(twoFluid.fluidInput.amount(), twoFluid.getFluidInput2().amount()),
                        twoFluid.fluidOutput.getAmount()
                )
        );

        // Input tank 1 (left half) and input tank 2 (right half) inside IE's 58px wide input tank
        builder.addSlot(RecipeIngredientRole.INPUT, 48, 3)
                .setFluidRenderer(tankSize, false, 29, 47)
                .addIngredients(NeoForgeTypes.FLUID_STACK, Arrays.asList(twoFluid.fluidInput.getFluids()))
                .addRichTooltipCallback(JEIHelper.fluidTooltipCallback);

        builder.addSlot(RecipeIngredientRole.INPUT, 77, 3)
                .setFluidRenderer(tankSize, false, 29, 47)
                .addIngredients(NeoForgeTypes.FLUID_STACK, Arrays.asList(twoFluid.getFluidInput2().getFluids()))
                .addRichTooltipCallback(JEIHelper.fluidTooltipCallback);

        builder.addSlot(RecipeIngredientRole.OUTPUT, 139, 3)
                .setFluidRenderer(tankSize, false, 16, 47)
                .setOverlay(this.tankOverlay, 0, 0)
                .addIngredient(NeoForgeTypes.FLUID_STACK, twoFluid.fluidOutput)
                .addRichTooltipCallback(JEIHelper.fluidTooltipCallback);

        for (int i = 0; i < twoFluid.itemInputs.size(); i++) {
            int x = (i % 2) * 18 + 1;
            int y = i / 2 * 18 + 1;
            builder.addSlot(RecipeIngredientRole.INPUT, x, y)
                    .addItemStacks(Arrays.asList(twoFluid.itemInputs.get(i).getMatchingStacks()))
                    .setBackground(JEIHelper.slotDrawable, -1, -1);
        }

        ci.cancel();
    }
}
