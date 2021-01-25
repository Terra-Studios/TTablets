package dev.sebastianb.ttablets.items;

import dev.sebastianb.ttablets.util.TTabletRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;
import java.util.function.Supplier;


public final class TTabletItemGroup extends ItemGroup {

    private static final TTabletItemGroup INSTANCE = new TTabletItemGroup(() -> new ItemStack(TTabletRegistry.TTABLET_ITEM.get()));

    private final Supplier<ItemStack> ICON;


    public TTabletItemGroup(Supplier<ItemStack> item) {
        super(new TranslationTextComponent("ttablets").getKey());
        this.ICON = item;
    }

    public static TTabletItemGroup getInstance() {
        return INSTANCE;
    }

    @Override
    @Nonnull
    public ItemStack createIcon() {
        return ICON.get();
    }
}
