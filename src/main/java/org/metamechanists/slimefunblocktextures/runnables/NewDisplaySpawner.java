package org.metamechanists.slimefunblocktextures.runnables;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.metamechanists.slimefunblocktextures.Util;


public class NewDisplaySpawner implements Runnable {
    @Override
    public void run() {
        for (World world : Bukkit.getServer().getWorlds()) {
            BlockStorage blockStorage = BlockStorage.getStorage(world);
            if (blockStorage == null) {
                continue;
            }

            for (Location location : blockStorage.getRawStorage().keySet()) {
                SlimefunItem slimefunItem = BlockStorage.check(location);
                if (slimefunItem == null) {
                    // just in case
                    continue;
                }

                if (BlockStorage.getLocationInfo(location, Util.BLOCKSTORAGE_KEY) != null) {
                    return;
                }

                Util.createBlockTexture(location, slimefunItem);
            }
        }
    }
}
