package net.LeFroshEh.AlternativeFuels.Blocks;

import net.LeFroshEh.AlternativeFuels.AlternativeFuels;
import net.LeFroshEh.AlternativeFuels.fluids.ModFluids;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(AlternativeFuels.MOD_ID);

    public static final DeferredBlock<LiquidBlock> METHANOL_BLOCK = BLOCKS.register("methanol",
            () -> new LiquidBlock(ModFluids.METHANOL_SOURCE.get(),
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_LIGHT_BLUE)
                            .replaceable()
                            .noCollission()
                            .strength(100.0F)
                            .pushReaction(PushReaction.DESTROY)
                            .liquid()
                            .sound(SoundType.EMPTY)
                            .noLootTable()));

    public static final DeferredBlock<LiquidBlock> WOOD_TAR_BLOCK = BLOCKS.register("wood_tar",
            () -> new LiquidBlock(ModFluids.WOOD_TAR_SOURCE.get(),
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_BLACK)
                            .replaceable()
                            .noCollission()
                            .strength(100.0F)
                            .pushReaction(PushReaction.DESTROY)
                            .liquid()
                            .sound(SoundType.EMPTY)
                            .noLootTable()));

    public static final DeferredBlock<LiquidBlock> FORMALDEHYDE_BLOCK = BLOCKS.register("formaldehyde",
            () -> new LiquidBlock(ModFluids.FORMALDEHYDE_SOURCE.get(),
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_LIGHT_BLUE)
                            .replaceable()
                            .noCollission()
                            .strength(100.0F)
                            .pushReaction(PushReaction.DESTROY)
                            .liquid()
                            .sound(SoundType.EMPTY)
                            .noLootTable()));

    public static final DeferredBlock<LiquidBlock> BIO_OIL_BLOCK = BLOCKS.register("bio_oil",
            () -> new LiquidBlock(ModFluids.BIO_OIL_SOURCE.get(),
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_BROWN)
                            .replaceable()
                            .noCollission()
                            .strength(100.0F)
                            .pushReaction(PushReaction.DESTROY)
                            .liquid()
                            .sound(SoundType.EMPTY)
                            .noLootTable()));

    public static final DeferredBlock<LiquidBlock> E5_GASOLINE_BLOCK = BLOCKS.register("e5_gasoline",
            () -> new LiquidBlock(ModFluids.E5_GASOLINE_SOURCE.get(),
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_ORANGE)
                            .replaceable()
                            .noCollission()
                            .strength(100.0F)
                            .pushReaction(PushReaction.DESTROY)
                            .liquid()
                            .sound(SoundType.EMPTY)
                            .noLootTable()));
}