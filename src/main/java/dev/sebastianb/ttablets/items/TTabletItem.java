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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;


public final class TTabletItem extends Item {

    public TTabletItem() {
        super(new Item.Properties().maxStackSize(1).group(TTabletItemGroup.getInstance()));
    }

    @Override
    @Nonnull
    @OnlyIn(Dist.CLIENT)
    public ActionResult<ItemStack> onItemRightClick(World world, @Nonnull PlayerEntity player, @Nonnull Hand hand) {
        if (world.isRemote) {
            Minecraft.getInstance().displayGuiScreen(new TTabletScreen());
            return ActionResult.resultSuccess(new ItemStack(TTabletRegistry.TTABLET_ITEM.get()));
        }
        return ActionResult.resultPass(new ItemStack(TTabletRegistry.TTABLET_ITEM.get()));
    }
}
