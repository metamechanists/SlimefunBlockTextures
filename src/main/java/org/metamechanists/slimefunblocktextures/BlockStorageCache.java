package org.metamechanists.slimefunblocktextures;

import io.github.bakedlibs.dough.blocks.BlockPosition;
import io.github.bakedlibs.dough.blocks.ChunkPosition;
import io.github.thebusybiscuit.slimefun4.api.events.SlimefunBlockBreakEvent;
import io.github.thebusybiscuit.slimefun4.api.events.SlimefunBlockPlaceEvent;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public final class BlockStorageCache implements Listener {
    private static final Map<ChunkPosition, Set<BlockPosition>> slimefunBlockLocations = new HashMap<>();

    public static void init() {
        for (World world : Bukkit.getWorlds()) {
            BlockStorage blockStorage = BlockStorage.getStorage(world);
            if (blockStorage == null) {
                continue;
            }

            for (Location location : blockStorage.getRawStorage().keySet()) {
                ChunkPosition chunkPosition = new ChunkPosition(location);
                slimefunBlockLocations.computeIfAbsent(chunkPosition, k -> new HashSet<>());
                slimefunBlockLocations.get(new ChunkPosition(location)).add(new BlockPosition(location));
            }
        }
    }

    public static Set<BlockPosition> getBlocksInChunk(ChunkPosition chunkPosition) {
        return slimefunBlockLocations.get(chunkPosition);
    }

    @EventHandler
    public static void onBlockPlace(@NotNull SlimefunBlockPlaceEvent e) {
        ChunkPosition chunkPosition = new ChunkPosition(e.getBlockPlaced().getLocation());
        BlockPosition blockPosition = new BlockPosition(e.getBlockPlaced().getLocation());
        slimefunBlockLocations.computeIfAbsent(chunkPosition, k -> new HashSet<>());
        slimefunBlockLocations.get(chunkPosition).add(blockPosition);
    }

    @EventHandler
    public static void onBlockBreak(@NotNull SlimefunBlockBreakEvent e) {
        ChunkPosition chunkPosition = new ChunkPosition(e.getBlockBroken().getLocation());
        BlockPosition blockPosition = new BlockPosition(e.getBlockBroken().getLocation());
        Set<BlockPosition> blockPositions = slimefunBlockLocations.get(chunkPosition);
        if (blockPositions != null) {
            blockPositions.remove(blockPosition);
        }
    }
}
