package org.metamechanists.slimefunblocktextures;

import de.cubbossa.cliententities.PlayerSpace;
import de.cubbossa.cliententities.entity.ClientEntity;
import de.cubbossa.cliententities.entity.ClientItemDisplay;
import io.github.bakedlibs.dough.blocks.BlockPosition;
import io.github.bakedlibs.dough.blocks.ChunkPosition;
import io.github.thebusybiscuit.slimefun4.api.events.SlimefunBlockBreakEvent;
import io.github.thebusybiscuit.slimefun4.api.events.SlimefunBlockPlaceEvent;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Display.Brightness;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.metamechanists.displaymodellib.sefilib.misc.TransformationBuilder;
import org.metamechanists.slimefunblocktextures.old.config.BlockModels;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public final class PlayerSpaces implements Listener {
    private static final Map<ChunkPosition, PlayerSpace> spaces = new HashMap<>();
    // why the hell do entities not have a getter in PlayerSpace
    private static final Map<ChunkPosition, Map<BlockPosition, ClientEntity>> entities = new HashMap<>();

    private static void createDisplay(Location location, @NotNull SlimefunItem slimefunItem) {
        SlimefunBlockTextures.getInstance().getLogger().severe("Spawning entity");
        ChunkPosition chunkPosition = new ChunkPosition(location);
        BlockPosition blockPosition = new BlockPosition(location);
        PlayerSpace space = spaces.computeIfAbsent(chunkPosition, k -> PlayerSpace.create().build());

        Integer blockModel = BlockModels.getBlockModel(slimefunItem.getId());
        if (blockModel == null) {
            return;
        }

        ItemStack stack = new ItemStack(Material.GOLD_BLOCK);
        stack.editMeta(meta -> meta.setCustomModelData(blockModel));

        ClientItemDisplay itemDisplay = space.spawn(location.clone().add(1.0005, 1.0005, 1.0005), ItemDisplay.class);
        itemDisplay.setBrightness(new Brightness(15, 15));
        itemDisplay.setItemStack(stack);
        itemDisplay.setTransformation(new TransformationBuilder().scale(1.001F, 1.001F, 1.001F).build());

        entities.computeIfAbsent(chunkPosition, k -> new HashMap<>()).put(blockPosition, itemDisplay);
    }

    private static void removeDisplay(@NotNull Location location) {
        ChunkPosition chunkPosition = new ChunkPosition(location.getChunk());
        BlockPosition blockPosition = new BlockPosition(location);
        spaces.remove(chunkPosition);
        entities.get(chunkPosition).remove(blockPosition);
    }

    @EventHandler
    public static void onPlayerEnterChunk(@NotNull PlayerMoveEvent e) {
        ChunkPosition chunkPositionFrom = new ChunkPosition(e.getFrom().getChunk());
        ChunkPosition chunkPositionTo = new ChunkPosition(e.getTo().getChunk());
        if (chunkPositionTo.equals(chunkPositionFrom)) {
            return;
        }

        SlimefunBlockTextures.getInstance().getLogger().severe("Making entity visible");
        spaces.get(chunkPositionFrom).removePlayer(e.getPlayer());
        spaces.get(chunkPositionTo).addPlayerIfAbsent(e.getPlayer());
    }

    @EventHandler
    public static void onLoad(@NotNull ChunkLoadEvent e) {
        // TODO decide ticking rate
        // TODO load for players in range
        ChunkPosition chunkPosition = new ChunkPosition(e.getChunk());
        Set<BlockPosition> blocksInChunk = BlockStorageCache.getBlocksInChunk(chunkPosition);

        for (BlockPosition blockPosition : blocksInChunk) {
            Location location = new Location(chunkPosition.getWorld(), blockPosition.getX(), blockPosition.getY(), blockPosition.getZ());
            SlimefunItem slimefunItem = BlockStorage.check(location);
            if (slimefunItem == null) {
                return;
            }

            createDisplay(location, slimefunItem);
        }
    }

    @EventHandler
    public static void onUnload(@NotNull ChunkUnloadEvent e) {
        ChunkPosition chunkPosition = new ChunkPosition(e.getChunk());
        Map<BlockPosition, ClientEntity> chunkEntities = entities.remove(chunkPosition);
        if (chunkEntities == null) {
            return;
        }

        // TODO check if this is necessary lol
        for (ClientEntity entity : chunkEntities.values()) {
            entity.remove();
        }

        try {
            spaces.remove(chunkPosition).close();
        } catch (IOException exception) {
            SlimefunBlockTextures.getInstance().getLogger().severe("Failed to close PlayerSpace: " + exception);
        }
    }

    @EventHandler
    public static void onBlockPlace(@NotNull SlimefunBlockPlaceEvent e) {
        createDisplay(e.getBlockPlaced().getLocation(), e.getSlimefunItem());
    }
    @EventHandler
    public static void onBlockBreak(@NotNull SlimefunBlockBreakEvent e) {
        removeDisplay(e.getBlockBroken().getLocation());
    }
}
