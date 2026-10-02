package net.LeFroshEh.AlternativeFuels.mixer;

import blusunrize.immersiveengineering.api.crafting.IERecipeSerializer;
import blusunrize.immersiveengineering.api.crafting.IngredientWithSize;
import blusunrize.immersiveengineering.api.crafting.MixerRecipe;
import net.LeFroshEh.AlternativeFuels.ModRecipeSerializers;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;

import java.util.List;

public class TwoFluidMixerRecipe extends MixerRecipe {
    private final SizedFluidIngredient fluidInput2;

    public TwoFluidMixerRecipe(
            FluidStack fluidOutput,
            SizedFluidIngredient fluidInput,
            SizedFluidIngredient fluidInput2,
            List<IngredientWithSize> itemInputs,
            int energy
    ) {
        super(fluidOutput, fluidInput, itemInputs, energy);

        if (fluidInput.amount() + fluidInput2.amount() != fluidOutput.getAmount()) {
            throw new IllegalArgumentException(
                    "Two-fluid mixer recipes must have input amounts adding up to the output amount: "
                            + fluidInput.amount() + " + " + fluidInput2.amount()
                            + " != " + fluidOutput.getAmount()
            );
        }

        this.fluidInput2 = fluidInput2;
        this.fluidInputList = List.of(this.fluidInput, this.fluidInput2);
    }

    public SizedFluidIngredient getFluidInput2() {
        return fluidInput2;
    }

    @Override
    public int getMultipleProcessTicks() {
        return 1;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected IERecipeSerializer<MixerRecipe> getIESerializer() {
        return (IERecipeSerializer<MixerRecipe>)(IERecipeSerializer<?>) ModRecipeSerializers.TWO_FLUID_MIXER.get();
    }
}
