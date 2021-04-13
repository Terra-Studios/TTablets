package dev.sebastianb.ttablets.items;

import dev.sebastianb.ttablets.client.gui.TTabletScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public final class TTabletItem extends Item {

    public TTabletItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    @NotNull
    @Environment(EnvType.CLIENT)
    public TypedActionResult<ItemStack> use(World world, @NotNull PlayerEntity player, @NotNull Hand hand) {
        if (world.isClient) {
            MinecraftClient.getInstance().openScreen(new TTabletScreen());
            return TypedActionResult.success(Items.TTABLET_ITEM.getDefaultStack());
        }
        return TypedActionResult.pass(Items.TTABLET_ITEM.getDefaultStack());
    }
}
