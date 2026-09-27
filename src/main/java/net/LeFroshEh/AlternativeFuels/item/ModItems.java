package net.LeFroshEh.AlternativeFuels.item;

import net.LeFroshEh.AlternativeFuels.AlternativeFuels;
import net.LeFroshEh.AlternativeFuels.fluids.ModFluids;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(AlternativeFuels.MOD_ID);

    public static final DeferredItem<BucketItem> METHANOL_BUCKET = ITEMS.register("methanol_bucket",
            () -> new BucketItem(ModFluids.METHANOL_SOURCE.get(),
                    new Item.Properties()
                            .craftRemainder(Items.BUCKET)
                            .stacksTo(1)));

    public static final DeferredItem<BucketItem> WOOD_TAR_BUCKET = ITEMS.register("wood_tar_bucket",
            () -> new BucketItem(ModFluids.WOOD_TAR_SOURCE.get(),
                    new Item.Properties()
                            .craftRemainder(Items.BUCKET)
                            .stacksTo(1)));

    public static final DeferredItem<BucketItem> FORMALDEHYDE_BUCKET = ITEMS.register("formaldehyde_bucket",
            () -> new BucketItem(ModFluids.FORMALDEHYDE_SOURCE.get(),
                    new Item.Properties()
                            .craftRemainder(Items.BUCKET)
                            .stacksTo(1)));

    public static final DeferredItem<BucketItem> BIO_OIL_BUCKET = ITEMS.register("bio_oil_bucket",
            () -> new BucketItem(ModFluids.BIO_OIL_SOURCE.get(),
                    new Item.Properties()
                            .craftRemainder(Items.BUCKET)
                            .stacksTo(1)));
}
