package net.LeFroshEh.AlternativeFuels;

import net.LeFroshEh.AlternativeFuels.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AlternativeFuels.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ALTERNATIVE_FUELS_TAB =
            CREATIVE_MODE_TABS.register("alternative_fuels_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("creativetab.alternativefuels.alternative_fuels_tab"))
                    .icon(() -> new ItemStack(ModItems.METHANOL_BUCKET.get()))
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.METHANOL_BUCKET.get());
                        output.accept(ModItems.WOOD_TAR_BUCKET.get());
                        output.accept(ModItems.FORMALDEHYDE_BUCKET.get());
                        output.accept(ModItems.BIO_OIL_BUCKET.get());
                        output.accept(ModItems.E5_GASOLINE_BUCKET.get());
                    })
                    .build());
}
