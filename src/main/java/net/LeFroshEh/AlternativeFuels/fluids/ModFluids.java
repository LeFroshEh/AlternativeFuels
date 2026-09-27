package net.LeFroshEh.AlternativeFuels.fluids;

import net.LeFroshEh.AlternativeFuels.AlternativeFuels;
import net.LeFroshEh.AlternativeFuels.Blocks.ModBlocks;
import net.LeFroshEh.AlternativeFuels.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(Registries.FLUID, AlternativeFuels.MOD_ID);

    public static final DeferredHolder<Fluid, FlowingFluid> METHANOL_SOURCE = FLUIDS.register("methanol",
            () -> new BaseFlowingFluid.Source(methanolProperties()));

    public static final DeferredHolder<Fluid, FlowingFluid> METHANOL_FLOWING = FLUIDS.register("methanol_flowing",
            () -> new BaseFlowingFluid.Flowing(methanolProperties()));

    public static final DeferredHolder<Fluid, FlowingFluid> WOOD_TAR_SOURCE = FLUIDS.register("wood_tar",
            () -> new BaseFlowingFluid.Source(woodTarProperties()));

    public static final DeferredHolder<Fluid, FlowingFluid> WOOD_TAR_FLOWING = FLUIDS.register("wood_tar_flowing",
            () -> new BaseFlowingFluid.Flowing(woodTarProperties()));

    public static final DeferredHolder<Fluid, FlowingFluid> FORMALDEHYDE_SOURCE = FLUIDS.register("formaldehyde",
            () -> new BaseFlowingFluid.Source(formaldehydeProperties()));

    public static final DeferredHolder<Fluid, FlowingFluid> FORMALDEHYDE_FLOWING = FLUIDS.register("formaldehyde_flowing",
            () -> new BaseFlowingFluid.Flowing(formaldehydeProperties()));

    public static final DeferredHolder<Fluid, FlowingFluid> BIO_OIL_SOURCE = FLUIDS.register("bio_oil",
            () -> new BaseFlowingFluid.Source(bioOilProperties()));

    public static final DeferredHolder<Fluid, FlowingFluid> BIO_OIL_FLOWING = FLUIDS.register("bio_oil_flowing",
            () -> new BaseFlowingFluid.Flowing(bioOilProperties()));

    private static BaseFlowingFluid.Properties methanolProperties() {
        return new BaseFlowingFluid.Properties(
                ModFluidTypes.METHANOL_TYPE,
                METHANOL_SOURCE,
                METHANOL_FLOWING)
                .slopeFindDistance(2)
                .levelDecreasePerBlock(2)
                .block(() -> ModBlocks.METHANOL_BLOCK.get())
                .bucket(() -> ModItems.METHANOL_BUCKET.get());
    }

    private static BaseFlowingFluid.Properties woodTarProperties() {
        return new BaseFlowingFluid.Properties(
                ModFluidTypes.WOOD_TAR_TYPE,
                WOOD_TAR_SOURCE,
                WOOD_TAR_FLOWING)
                .slopeFindDistance(2)
                .levelDecreasePerBlock(2)
                .block(() -> ModBlocks.WOOD_TAR_BLOCK.get())
                .bucket(() -> ModItems.WOOD_TAR_BUCKET.get());
    }

    private static BaseFlowingFluid.Properties formaldehydeProperties() {
        return new BaseFlowingFluid.Properties(
                ModFluidTypes.FORMALDEHYDE_TYPE,
                FORMALDEHYDE_SOURCE,
                FORMALDEHYDE_FLOWING)
                .slopeFindDistance(2)
                .levelDecreasePerBlock(2)
                .block(() -> ModBlocks.FORMALDEHYDE_BLOCK.get())
                .bucket(() -> ModItems.FORMALDEHYDE_BUCKET.get());
    }

    private static BaseFlowingFluid.Properties bioOilProperties() {
        return new BaseFlowingFluid.Properties(
                ModFluidTypes.BIO_OIL_TYPE,
                BIO_OIL_SOURCE,
                BIO_OIL_FLOWING)
                .slopeFindDistance(2)
                .levelDecreasePerBlock(2)
                .block(() -> ModBlocks.BIO_OIL_BLOCK.get())
                .bucket(() -> ModItems.BIO_OIL_BUCKET.get());
    }
}
