package net.LeFroshEh.AlternativeFuels.mixin;

import blusunrize.immersiveengineering.api.crafting.CokeOvenRecipe;
import com.chen1335.immersiveMechanical.recipe.PyrolyseOvenRecipe;
import net.LeFroshEh.AlternativeFuels.fluids.ModFluids;
import net.neoforged.neoforge.fluids.FluidStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(PyrolyseOvenRecipe.class)
public class PyrolyseOvenRecipeMixin {

    @Inject(
            method = "fromCokeOvenRecipe",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void alternativefuels$replaceCreosote(
            CokeOvenRecipe recipe,
            CallbackInfoReturnable<PyrolyseOvenRecipe> cir
    ) {

        cir.setReturnValue(
                new PyrolyseOvenRecipe(
                        recipe.input,
                        recipe.output,
                        List.of(),
                        new FluidStack(
                                ModFluids.BIO_OIL_SOURCE.get(),
                                recipe.creosoteOutput
                        ),
                        recipe.time / 4,
                        25600
                )
        );
    }
}