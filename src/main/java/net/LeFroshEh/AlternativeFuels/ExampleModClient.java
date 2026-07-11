package net.LeFroshEh.AlternativeFuels;

import net.LeFroshEh.AlternativeFuels.fluids.ModFluidTypes;
import net.LeFroshEh.AlternativeFuels.fluids.ModFluids;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.LeFroshEh.AlternativeFuels.item.ModItems;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.model.DynamicFluidContainerModel;

// This class is only loaded on the physical client.
// You do not need to worry about it being called on a dedicated server.
@Mod(value = AlternativeFuels.MOD_ID, dist = Dist.CLIENT)
public class ExampleModClient {
    public ExampleModClient(IEventBus modEventBus) {
        modEventBus.addListener(this::onClientSetup);
        modEventBus.addListener(this::registerClientExtensions);
        modEventBus.addListener(this::registerItemColors);
    }

    private void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register(new DynamicFluidContainerModel.Colors(), ModItems.METHANOL_BUCKET.get());
        event.register(new DynamicFluidContainerModel.Colors(), ModItems.WOOD_TAR_BUCKET.get());
    }

    private void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(ModFluids.METHANOL_SOURCE.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.METHANOL_FLOWING.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.WOOD_TAR_SOURCE.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.WOOD_TAR_FLOWING.get(), RenderType.translucent());
        });
    }

    private void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerFluidType(new IClientFluidTypeExtensions() {
            @Override
            public ResourceLocation getStillTexture() {
                return ModFluidTypes.METHANOL_STILL_RL;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return ModFluidTypes.METHANOL_FLOWING_RL;
            }

            @Override
            public int getTintColor() {
                return 0xFFFFFFFF;
            }
        }, ModFluidTypes.METHANOL_TYPE.get());

        event.registerFluidType(new IClientFluidTypeExtensions() {
            @Override
            public ResourceLocation getStillTexture() {
                return ModFluidTypes.WOOD_TAR_STILL_RL;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return ModFluidTypes.WOOD_TAR_FLOWING_RL;
            }

            @Override
            public int getTintColor() {
                return 0xFFFFFFFF;
            }
        }, ModFluidTypes.WOOD_TAR_TYPE.get());
    }
}
