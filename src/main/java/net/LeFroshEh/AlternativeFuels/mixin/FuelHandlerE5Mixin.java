package net.LeFroshEh.AlternativeFuels.mixin;

import flaxbeard.immersivepetroleum.api.energy.FuelHandler;
import flaxbeard.immersivepetroleum.common.cfg.IPServerConfig;
import net.LeFroshEh.AlternativeFuels.fluids.ModFluids;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.fml.event.config.ModConfigEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Immersive Petroleum wipes and rebuilds its generator fuel list from its config every time that
 * config loads or reloads, so fuels registered once at startup disappear again. This re-adds E5
 * Gasoline right after each rebuild, so it works without anyone editing the config.
 *
 * E5 copies gasoline's current settings (mB per second and flux per tick), so it always matches
 * whatever the pack's config says for gasoline. If E5 is already listed in the config, that entry wins.
 */
@Mixin(FuelHandler.class)
public class FuelHandlerE5Mixin {
    @Inject(method = "onConfigReload", at = @At("RETURN"))
    private static void alternativefuels$registerE5(ModConfigEvent ev, CallbackInfo ci) {
        if (ev.getConfig().getSpec() != IPServerConfig.ALL) {
            return;
        }

        Fluid e5 = ModFluids.E5_GASOLINE_SOURCE.get();
        if (FuelHandler.isValidFuel(e5)) {
            return; // already listed in the config
        }

        int mbPerSecond = 6;
        int fluxPerTick = 256;
        Fluid gasoline = BuiltInRegistries.FLUID.get(ResourceLocation.fromNamespaceAndPath("immersivepetroleum", "gasoline"));
        if (FuelHandler.isValidFuel(gasoline)) {
            mbPerSecond = FuelHandler.getGeneratorFuelUse(gasoline);
            fluxPerTick = FuelHandler.getFluxGeneratedPerTick(gasoline);
        }

        // Note: this overload's argument order is (fluid, fluxPerTick, mbPerSecond)
        FuelHandler.registerPortableGeneratorFuel(
                ResourceLocation.fromNamespaceAndPath("alternativefuels", "e5_gasoline"),
                fluxPerTick,
                mbPerSecond
        );
    }
}
