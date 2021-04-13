package dev.sebastianb.ttablets.items;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static dev.sebastianb.ttablets.TTablets.MOD_ID;

public final class Items {
    public static final ItemGroup TTABLETS_ITEM_GROUP = FabricItemGroupBuilder.build(
            new Identifier(MOD_ID, "general"),
            net.minecraft.item.Items.BONE::getDefaultStack); // todo: fix circular reference in ItemGroup icon

    public static final String TTABLET_ITEM_NAME = "ttablet";
    public static final Item TTABLET_ITEM = new TTabletItem(new FabricItemSettings().maxCount(1).group(TTABLETS_ITEM_GROUP));

    public static void registerItems() {
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, TTABLET_ITEM_NAME), TTABLET_ITEM);
    }
}
