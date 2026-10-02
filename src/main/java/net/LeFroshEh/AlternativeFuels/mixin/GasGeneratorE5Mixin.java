package net.LeFroshEh.AlternativeFuels.mixin;

import flaxbeard.immersivepetroleum.common.blocks.tileentities.GasGeneratorTileEntity;
import net.LeFroshEh.AlternativeFuels.fluids.ModFluids;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Makes E5 Gasoline burn 1.6% faster than the amount set in the Immersive Petroleum config,
 * while the power output (flux per tick) stays exactly as configured.
 *
 * The config only accepts whole mB, so the extra 0.016 is collected as a fraction on each burn
 * cycle and paid as an extra 1 mB whenever it adds up to a whole mB.
 */
@Mixin(GasGeneratorTileEntity.class)
public abstract class GasGeneratorE5Mixin {
    @Unique
    private static final double ALTERNATIVEFUELS$EXTRA_BURN = 0.016;

    @Shadow protected int fluidTick;
    @Shadow @Final protected FluidTank tank;

    @Unique private boolean alternativefuels$wasIdle;
    @Unique private int alternativefuels$amountBefore;
    @Unique private Fluid alternativefuels$fluidBefore;
    @Unique private double alternativefuels$extraFuel;

    @Inject(method = "tickServer", at = @At("HEAD"))
    private void alternativefuels$beforeTick(CallbackInfo ci) {
        this.alternativefuels$wasIdle = this.fluidTick == 0;
        this.alternativefuels$amountBefore = this.tank.getFluidAmount();
        this.alternativefuels$fluidBefore = this.tank.getFluid().getFluid();
    }

    @Inject(method = "tickServer", at = @At("RETURN"))
    private void alternativefuels$afterTick(CallbackInfo ci) {
        // Only act on the tick where a new burn cycle has just started
        if (!this.alternativefuels$wasIdle || this.fluidTick <= 0) {
            return;
        }
        if (!this.alternativefuels$fluidBefore.isSame(ModFluids.E5_GASOLINE_SOURCE.get())) {
            return;
        }

        int burned = this.alternativefuels$amountBefore - this.tank.getFluidAmount();
        if (burned <= 0) {
            return;
        }

        this.alternativefuels$extraFuel += burned * ALTERNATIVEFUELS$EXTRA_BURN;
        while (this.alternativefuels$extraFuel >= 1.0) {
            this.alternativefuels$extraFuel -= 1.0;
            FluidStack drained = this.tank.drain(1, IFluidHandler.FluidAction.EXECUTE);
            if (drained.isEmpty()) {
                // tank ran dry: don't build up a debt
                this.alternativefuels$extraFuel = 0;
                break;
            }
        }
    }
}
