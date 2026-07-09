package net.LeFroshEh.AlternativeFuels.fluids;

import net.LeFroshEh.AlternativeFuels.AlternativeFuels;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModFluidTypes {
    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, AlternativeFuels.MOD_ID);

    // Texture locations, referenced by ExampleModClient for rendering
    public static final ResourceLocation METHANOL_STILL_RL =
            ResourceLocation.fromNamespaceAndPath(AlternativeFuels.MOD_ID, "block/methanol_still");
    public static final ResourceLocation METHANOL_FLOWING_RL =
            ResourceLocation.fromNamespaceAndPath(AlternativeFuels.MOD_ID, "block/methanol_flow");

    public static final DeferredHolder<FluidType, FluidType> METHANOL_TYPE = FLUID_TYPES.register("methanol",
            () -> new FluidType(FluidType.Properties.create()
                    .descriptionId("fluid.alternativefuels.methanol")
                    .canSwim(true)
                    .canDrown(true)
                    .canPushEntity(true)
                    .canExtinguish(false)
                    .canConvertToSource(false)
                    .supportsBoating(true)
                    .density(790)
                    .viscosity(600)
                    .lightLevel(0)
                    .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                    .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
            ));
}
