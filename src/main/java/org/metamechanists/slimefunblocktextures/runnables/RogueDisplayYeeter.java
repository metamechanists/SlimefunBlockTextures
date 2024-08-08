package org.metamechanists.slimefunblocktextures.runnables;

import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Bukkit;
import org.bukkit.World;
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

                display.remove();

                // Check if block is empty instead of more expensive BlockStorageCheck
                if (display.getLocation().clone().subtract(1.0, 1.0, 1.0).getBlock().isEmpty()) {
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
                    display.remove();
                    continue;
                }

                if (BlockModels.getBlockModel(id) != meta.getCustomModelData()) {
                    stack.editMeta(newMeta -> newMeta.setCustomModelData(BlockModels.getBlockModel(id)));
                }
            }
        }
    }
}
