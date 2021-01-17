package dev.sebastianb.ttablets.items;

import dev.sebastianb.ttablets.TTablets;
import dev.sebastianb.ttablets.creativetab.TTabletsCreativeTab;
import dev.sebastianb.ttablets.reference.GUI_IDs;
import dev.sebastianb.ttablets.utils.TUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class ItemTablet extends Item {


    public ItemTablet(@Nonnull String name) {
        TUtils.setRegistryNames(this, name);
        TUtils.setCreativeTab(this, TTabletsCreativeTab.CREATIVE_TAB);
        this.maxStackSize = 1;


    }
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer playerIn,
                                                    EnumHand hand) {
        ItemStack stack = playerIn.getHeldItem(hand);

        if (world.isRemote) {
            playerIn.openGui(TTablets.INSTANCE, GUI_IDs.TABLET.ordinal(), world,
                    (int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posZ);
            return new ActionResult(EnumActionResult.SUCCESS, stack);

        }

        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }



}
