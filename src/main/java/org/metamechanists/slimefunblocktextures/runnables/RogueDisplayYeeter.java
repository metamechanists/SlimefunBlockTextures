package org.metamechanists.slimefunblocktextures.runnables;

import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.metamechanists.slimefunblocktextures.Util;
import org.metamechanists.slimefunblocktextures.config.BlockModels;


public class RogueDisplayYeeter implements Runnable {
    @Override
    public void run() {
        for (World world : Bukkit.getServer().getWorlds()) {
            for (ItemDisplay display : world.getEntitiesByClass(ItemDisplay.class)) {
                String id = display.getPersistentDataContainer().get(Util.blockTextureKey, PersistentDataType.STRING);
                if (id == null) {
                    continue;
                }

                // Check if block is empty instead of more expensive BlockStorageCheck
                Block block = display.getLocation().clone().subtract(1.0, 1.0, 1.0).getBlock();
                if (block.isEmpty()) {
                    BlockStorage.addBlockInfo(block, Util.BLOCKSTORAGE_KEY, null);
                    display.remove();
                    continue;
                }

                // Check if block model data is outdated
                ItemStack stack = display.getItemStack();
                if (stack == null) {
                    continue;
                }

                ItemMeta meta = stack.getItemMeta();
                if (meta == null) {
                    continue;
                }

                if (BlockModels.getBlockModel(id) == null) {
                    BlockStorage.addBlockInfo(block, Util.BLOCKSTORAGE_KEY, null);
                    display.remove();
                    continue;
                }

                if (BlockModels.getBlockModel(id) != meta.getCustomModelData()) {
                    stack.editMeta(newMeta -> newMeta.setCustomModelData(BlockModels.getBlockModel(id)));
                }

                if (!BlockStorage.getLocationInfo(block.getLocation(), Util.BLOCKSTORAGE_KEY).equals(display.getUniqueId().toString())) {
                    display.remove();
                }
            }
        }
    }
}
