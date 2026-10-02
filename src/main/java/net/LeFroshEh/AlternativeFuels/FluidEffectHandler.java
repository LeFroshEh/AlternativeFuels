package net.LeFroshEh.AlternativeFuels.event;

import net.LeFroshEh.AlternativeFuels.AlternativeFuels;
import net.LeFroshEh.AlternativeFuels.fluids.ModFluids;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = AlternativeFuels.MOD_ID)
public class FluidEffectHandler {

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        // Perform checks only server-side on living players
        if (!player.level().isClientSide() && player.isAlive()) {

            // 1. FORMALDEHYDE: Nausea + Poison I (10 seconds = 200 ticks)
            if (player.isInFluidType(ModFluids.FORMALDEHYDE_SOURCE.get().getFluidType())) {
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0, false, true));
                player.addEffect(new MobEffectInstance(MobEffects.POISON, 200, 0, false, true));
            }

            // 2. METHANOL: Blindness + Nausea (10 seconds = 200 ticks)
            if (player.isInFluidType(ModFluids.METHANOL_SOURCE.get().getFluidType())) {
                player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 200, 0, false, true));
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0, false, true));
            }

            // 3. WOOD TAR & BIO-OIL: Slowness II (5 seconds = 100 ticks)
            if (player.isInFluidType(ModFluids.WOOD_TAR_SOURCE.get().getFluidType()) ||
                    player.isInFluidType(ModFluids.BIO_OIL_SOURCE.get().getFluidType())) {
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 1, false, true));
            }
        }
    }
}
