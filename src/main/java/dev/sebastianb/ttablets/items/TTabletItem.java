package dev.sebastianb.ttablets.items;

import dev.sebastianb.ttablets.client.gui.TTabletScreen;
import dev.sebastianb.ttablets.util.TTabletRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import javax.annotation.Nonnull;


public class TTabletItem extends Item {

    public TTabletItem() {
        super(new Item.Properties().maxStackSize(1).group(TTabletItemGroup.INSTANCE));
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(World world, @Nonnull PlayerEntity player, @Nonnull Hand hand) {
        if (world.isRemote) {
            Minecraft.getInstance().displayGuiScreen(new TTabletScreen());
            return ActionResult.resultSuccess(new ItemStack(TTabletRegistry.TTABLET_ITEM.get()));
        }
        return ActionResult.resultPass(new ItemStack(TTabletRegistry.TTABLET_ITEM.get()));
    }
}
