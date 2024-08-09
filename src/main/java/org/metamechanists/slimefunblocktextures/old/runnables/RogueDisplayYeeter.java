package org.metamechanists.slimefunblocktextures.old.runnables;

import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.persistence.PersistentDataType;
import org.metamechanists.slimefunblocktextures.old.Util;


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
                BlockStorage.addBlockInfo(block, Util.BLOCKSTORAGE_KEY, null);
                display.remove();
            }
        }
    }
}
