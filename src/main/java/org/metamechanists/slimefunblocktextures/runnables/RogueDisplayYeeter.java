package org.metamechanists.slimefunblocktextures.runnables;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.persistence.PersistentDataType;
import org.metamechanists.slimefunblocktextures.Util;


public class RogueDisplayYeeter implements Runnable {
    @Override
    public void run() {
        for (World world : Bukkit.getServer().getWorlds()) {
            for (ItemDisplay display : world.getEntitiesByClass(ItemDisplay.class)) {
                if (display.getPersistentDataContainer().get(Util.blockTextureKey, PersistentDataType.BOOLEAN) == null) {
                    continue;
                }

                // Check if block is empty instead of more expensive BlockStorageCheck
                if (!display.getLocation().clone().subtract(1.0, 1.0, 1.0).getBlock().isEmpty()) {
                    continue;
                }

                display.remove();
            }
        }
    }
}
