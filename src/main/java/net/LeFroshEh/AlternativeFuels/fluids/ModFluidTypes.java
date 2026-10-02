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

    // Texture locations, referenced by ExampleModClient for rendering
    public static final ResourceLocation WOOD_TAR_STILL_RL =
            ResourceLocation.fromNamespaceAndPath(AlternativeFuels.MOD_ID, "block/wood_tar_still");
    public static final ResourceLocation WOOD_TAR_FLOWING_RL =
            ResourceLocation.fromNamespaceAndPath(AlternativeFuels.MOD_ID, "block/wood_tar_flow");

    public static final DeferredHolder<FluidType, FluidType> WOOD_TAR_TYPE = FLUID_TYPES.register("wood_tar",
            () -> new FluidType(FluidType.Properties.create()
                    .descriptionId("fluid.alternativefuels.wood_tar")
                    .canSwim(false)
                    .canDrown(true)
                    .canPushEntity(true)
                    .canExtinguish(false)
                    .canConvertToSource(false)
                    .supportsBoating(false)
                    .density(1200)
                    .viscosity(1800)
                    .lightLevel(0)
                    .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                    .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
            ));

    // Formaldehyde reuses the Methanol textures (block/methanol_still, block/methanol_flow)
    public static final DeferredHolder<FluidType, FluidType> FORMALDEHYDE_TYPE = FLUID_TYPES.register("formaldehyde",
            () -> new FluidType(FluidType.Properties.create()
                    .descriptionId("fluid.alternativefuels.formaldehyde")
                    .canSwim(true)
                    .canDrown(true)
                    .canPushEntity(true)
                    .canExtinguish(false)
                    .canConvertToSource(false)
                    .supportsBoating(true)
                    .density(815)
                    .viscosity(650)
                    .lightLevel(0)
                    .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                    .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
            ));

    public static final ResourceLocation BIO_OIL_STILL_RL =
            ResourceLocation.fromNamespaceAndPath(AlternativeFuels.MOD_ID, "block/bio_oil_still");
    public static final ResourceLocation BIO_OIL_FLOWING_RL =
            ResourceLocation.fromNamespaceAndPath(AlternativeFuels.MOD_ID, "block/bio_oil_flow");

    public static final DeferredHolder<FluidType, FluidType> BIO_OIL_TYPE = FLUID_TYPES.register("bio_oil",
            () -> new FluidType(FluidType.Properties.create()
                    .descriptionId("fluid.alternativefuels.bio_oil")
                    .canSwim(false)
                    .canDrown(true)
                    .canPushEntity(true)
                    .canExtinguish(false)
                    .canConvertToSource(false)
                    .supportsBoating(false)
                    .density(1100)
                    .viscosity(1400)
                    .lightLevel(0)
                    .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                    .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
            ));

    public static final ResourceLocation E5_GASOLINE_STILL_RL =
            ResourceLocation.fromNamespaceAndPath(AlternativeFuels.MOD_ID, "block/e5_gasoline_still");
    public static final ResourceLocation E5_GASOLINE_FLOWING_RL =
            ResourceLocation.fromNamespaceAndPath(AlternativeFuels.MOD_ID, "block/e5_gasoline_flow");

    public static final DeferredHolder<FluidType, FluidType> E5_GASOLINE_TYPE = FLUID_TYPES.register("e5_gasoline",
            () -> new FluidType(FluidType.Properties.create()
                    .descriptionId("fluid.alternativefuels.e5_gasoline")
                    .canSwim(true)
                    .canDrown(true)
                    .canPushEntity(true)
                    .canExtinguish(false)
                    .canConvertToSource(false)
                    .supportsBoating(true)
                    .density(755)
                    .viscosity(700)
                    .lightLevel(0)
                    .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                    .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
            ));
}