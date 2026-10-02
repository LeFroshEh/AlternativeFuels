package net.LeFroshEh.AlternativeFuels.mixer;

import blusunrize.immersiveengineering.api.crafting.MixerRecipe;
import blusunrize.immersiveengineering.common.blocks.multiblocks.logic.mixer.MixingProcess;
import blusunrize.immersiveengineering.common.blocks.multiblocks.process.MultiblockProcess;
import blusunrize.immersiveengineering.common.util.inventory.MultiFluidTank;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;

import java.util.List;

public final class TwoFluidMixerSupport {
    private TwoFluidMixerSupport() {
    }

    public record ProcessInfo(TwoFluidMixerRecipe recipe, int eventIndex) {
    }

    public static final ThreadLocal<MultiFluidTank> CURRENT_MIXER_TANK = new ThreadLocal<>();
    public static final ThreadLocal<Level> CURRENT_LEVEL = new ThreadLocal<>();
    public static final ThreadLocal<ProcessInfo> CURRENT_PROCESS = new ThreadLocal<>();

    /**
     * IE calls this once for every fluid in the tank. We only answer for the first fluid that
     * matches the recipe's first input, so exactly one process is queued per recipe per tick.
     * The order of fluids in the tank does not matter (the old version required the LAST fluid in
     * the tank to be an ingredient, which stopped new batches whenever E5 was in the tank).
     */
    public static RecipeHolder<MixerRecipe> findRecipe(
            Level level,
            MultiFluidTank tank,
            FluidStack fluidBeingChecked,
            NonNullList<ItemStack> itemInputs
    ) {
        if (tank == null || tank.fluids.isEmpty() || fluidBeingChecked.isEmpty()) {
            return null;
        }

        for (RecipeHolder<MixerRecipe> holder : MixerRecipe.RECIPES.getRecipes(level)) {
            if (!(holder.value() instanceof TwoFluidMixerRecipe recipe)) {
                continue;
            }

            FluidStack first = firstMatching(tank, recipe.fluidInput);
            FluidStack second = firstMatching(tank, recipe.getFluidInput2());
            if (first == null || second == null || first == second) {
                continue;
            }
            if (!FluidStack.isSameFluidSameComponents(first, fluidBeingChecked)) {
                continue;
            }
            if (recipe.matches(first, itemInputs)) {
                return holder;
            }
        }
        return null;
    }

    private static FluidStack firstMatching(MultiFluidTank tank, SizedFluidIngredient ingredient) {
        for (FluidStack fs : tank.fluids) {
            if (fs != null && !fs.isEmpty() && ingredient.test(fs)) {
                return fs;
            }
        }
        return null;
    }

    /**
     * True when the fluid that "output bottom fluid" would send out (the last one in the tank) is an
     * ingredient of a two-fluid recipe whose other ingredient is also still in the tank, i.e. the
     * mixer could still use it.
     */
    public static boolean shouldHoldBackOutput(Level level, MultiFluidTank tank) {
        FluidStack last = tank.getFluid();
        if (last.isEmpty()) {
            return false;
        }
        for (RecipeHolder<MixerRecipe> holder : MixerRecipe.RECIPES.getRecipes(level)) {
            if (!(holder.value() instanceof TwoFluidMixerRecipe recipe)) {
                continue;
            }
            FluidIngredient in1 = recipe.fluidInput.ingredient();
            FluidIngredient in2 = recipe.getFluidInput2().ingredient();
            boolean isFirst = in1.test(last);
            boolean isSecond = in2.test(last);
            if (!isFirst && !isSecond) {
                continue;
            }
            FluidIngredient other = isFirst ? in2 : in1;
            for (FluidStack fs : tank.fluids) {
                if (fs != last && fs != null && !fs.isEmpty() && other.test(fs)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean hasActiveTwoFluidProcess(
            List<? extends MultiblockProcess<?, ?>> queue,
            Level level
    ) {
        if (level == null) {
            return false;
        }

        for (MultiblockProcess<?, ?> process : queue) {
            if (process instanceof MixingProcess mixingProcess
                    && mixingProcess.getRecipe(level) instanceof TwoFluidMixerRecipe) {
                return true;
            }
        }
        return false;
    }
}
