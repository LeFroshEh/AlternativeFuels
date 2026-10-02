package net.LeFroshEh.AlternativeFuels.mixer;

import blusunrize.immersiveengineering.api.crafting.IERecipeSerializer;
import blusunrize.immersiveengineering.api.crafting.IngredientWithSize;
import blusunrize.immersiveengineering.api.utils.codec.IEDualCodecs;
import blusunrize.immersiveengineering.common.register.IEMultiblockLogic;
import malte0811.dualcodecs.DualCodecs;
import malte0811.dualcodecs.DualCompositeMapCodecs;
import malte0811.dualcodecs.DualMapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.item.ItemStack;

public class TwoFluidMixerRecipeSerializer extends IERecipeSerializer<TwoFluidMixerRecipe> {
    public static final DualMapCodec<RegistryFriendlyByteBuf, TwoFluidMixerRecipe> CODECS =
            DualCompositeMapCodecs.composite(
                    IEDualCodecs.FLUID_STACK.fieldOf("result"), recipe -> recipe.fluidOutput,
                    IEDualCodecs.SIZED_FLUID_INGREDIENT.fieldOf("fluid"), recipe -> recipe.fluidInput,
                    IEDualCodecs.SIZED_FLUID_INGREDIENT.fieldOf("fluid2"), TwoFluidMixerRecipe::getFluidInput2,
                    IngredientWithSize.CODECS.listOf().fieldOf("inputs"), recipe -> recipe.itemInputs,
                    DualCodecs.INT.fieldOf("energy"), TwoFluidMixerRecipe::getBaseEnergy,
                    TwoFluidMixerRecipe::new
            );

    @Override
    protected DualMapCodec<RegistryFriendlyByteBuf, TwoFluidMixerRecipe> codecs() {
        return CODECS;
    }

    @Override
    public ItemStack getIcon() {
        return IEMultiblockLogic.MIXER.iconStack();
    }
}
