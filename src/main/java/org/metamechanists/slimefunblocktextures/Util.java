package org.metamechanists.slimefunblocktextures;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import lombok.experimental.UtilityClass;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.metamechanists.displaymodellib.models.components.ModelItem;
import org.metamechanists.slimefunblocktextures.config.BlockModels;


@UtilityClass
public class Util {
    public NamespacedKey blockTextureKey;
    public final String BLOCKSTORAGE_KEY = "texture-item-display-entity";

    public void init() {
        blockTextureKey = new NamespacedKey(SlimefunBlockTextures.getInstance(), "BLOCK_TEXTURE");
    }

    public void createBlockTexture(Location location, @NotNull SlimefunItem slimefunItem) {
        Integer blockModel = BlockModels.getBlockModel(slimefunItem.getId());
        if (blockModel == null) {
            return;
        }

        ItemStack stack = new ItemStack(Material.GOLD_BLOCK);
        stack.editMeta(meta -> meta.setCustomModelData(blockModel));

        ItemDisplay display = new ModelItem()
                .item(stack)
                .brightness(15)
                .scale(1.001, 1.001, 1.001)
                .build(location.clone().add(1.0005, 1.0005, 1.0005));

        display.getPersistentDataContainer().set(blockTextureKey, PersistentDataType.STRING, slimefunItem.getId());

        BlockStorage.addBlockInfo(location, BLOCKSTORAGE_KEY, display.getUniqueId().toString());
    }
}
