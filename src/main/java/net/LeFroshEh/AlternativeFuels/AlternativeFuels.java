package net.LeFroshEh.AlternativeFuels;

import com.mojang.logging.LogUtils;
import net.LeFroshEh.AlternativeFuels.Blocks.ModBlocks;
import net.LeFroshEh.AlternativeFuels.fluids.ModFluidTypes;
import net.LeFroshEh.AlternativeFuels.fluids.ModFluids;
import net.LeFroshEh.AlternativeFuels.item.ModItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;

@Mod(AlternativeFuels.MOD_ID)
public class AlternativeFuels {
    public static final String MOD_ID = "alternativefuels";
    private static final Logger LOGGER = LogUtils.getLogger();

    public AlternativeFuels(IEventBus modEventBus) {
        // Register the DeferredRegister objects directly to the mod event bus
        ModItems.ITEMS.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModFluids.FLUIDS.register(modEventBus);
        ModFluidTypes.FLUID_TYPES.register(modEventBus);
        ModCreativeModeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        ModRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);

        // Register lifecycle listener
        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Alternative Fuels common setup initialized.");
    }
}