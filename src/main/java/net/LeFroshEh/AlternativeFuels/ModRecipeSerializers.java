package net.LeFroshEh.AlternativeFuels;

import net.LeFroshEh.AlternativeFuels.mixer.TwoFluidMixerRecipeSerializer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModRecipeSerializers {
    private ModRecipeSerializers() {
    }

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, AlternativeFuels.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, TwoFluidMixerRecipeSerializer> TWO_FLUID_MIXER =
            RECIPE_SERIALIZERS.register("two_fluid_mixer", TwoFluidMixerRecipeSerializer::new);
}
