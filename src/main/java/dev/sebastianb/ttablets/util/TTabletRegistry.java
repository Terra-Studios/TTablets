package dev.sebastianb.ttablets.util;

import dev.sebastianb.ttablets.TTablets;
import dev.sebastianb.ttablets.items.TTabletItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;


public final class TTabletRegistry {

    public static final DeferredRegister<Item> ITEM_REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, TTablets.MOD_ID);

    public static final RegistryObject<Item> TTABLET_ITEM = ITEM_REGISTRY.register("ttablet", TTabletItem::new);
}
