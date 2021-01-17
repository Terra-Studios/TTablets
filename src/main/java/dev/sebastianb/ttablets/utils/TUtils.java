package dev.sebastianb.ttablets.utils;

import dev.sebastianb.ttablets.TTablets;
import dev.sebastianb.ttablets.creativetab.TTabletsCreativeTab;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;

public class TUtils {
    @Nonnull
    public static <T extends IForgeRegistryEntry.Impl<?>> T setRegistryNames(@Nonnull final T entry, @Nonnull final String name) {
        return setRegistryNames(entry, new ResourceLocation(TTablets.MOD_ID, name));

    }




    @Nonnull
    public static <T extends IForgeRegistryEntry.Impl<?>> T setRegistryNames(@Nonnull final T entry, @Nonnull final ResourceLocation registryName) {
        return setRegistryNames(entry, registryName, registryName.getPath());
    }




    @Nonnull
    public static <T extends IForgeRegistryEntry.Impl<?>> T setRegistryNames(@Nonnull final T entry, @Nonnull final ResourceLocation registryName, @Nonnull final String translationKey) {
        entry.setRegistryName(registryName);
        if (entry instanceof Block) {
            ((Block) entry).setTranslationKey(translationKey);
        }
        if (entry instanceof Item) {
            ((Item) entry).setTranslationKey(translationKey);
        }
        return entry;
    }

    @Nonnull
    public static <T extends IForgeRegistryEntry.Impl<?>> T setCreativeTab(@Nonnull final T entry, final CreativeTabs creativeTab) {
        if (entry instanceof Block) {
            ((Block) entry).setCreativeTab(creativeTab);
        }
        if (entry instanceof Item) {
            ((Item) entry).setCreativeTab(creativeTab);
        }
        return entry;
    }
    @Nonnull
    public static <T extends IForgeRegistryEntry.Impl<?>> T setCreativeTab(@Nonnull final T entry) {
        return setCreativeTab(entry, TTabletsCreativeTab.CREATIVE_TAB);
    }

}
