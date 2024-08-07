package org.metamechanists.slimefunblocktextures.listeners;

import io.github.thebusybiscuit.slimefun4.api.events.SlimefunBlockBreakEvent;
import io.github.thebusybiscuit.slimefun4.api.events.SlimefunBlockPlaceEvent;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.metamechanists.displaymodellib.models.components.ModelItem;
import org.metamechanists.slimefunblocktextures.SlimefunBlockTextures;

import java.util.UUID;


public class PlayerClickListener implements Listener {

    @EventHandler
    public static void onSlimefunBlockPlace(@NotNull SlimefunBlockPlaceEvent event) {
        Location location = event.getBlockPlaced().getLocation();

        ItemStack stack = new ItemStack(Material.DIAMOND_BLOCK);
        stack.editMeta(meta -> meta.setCustomModelData(666));

        UUID uuid = new ModelItem()
                .item(stack)
                .brightness(15)
                .scale(1.001, 1.001, 1.001)
                .build(location.clone().add(1.0005, 1.0005, 1.0005))
                .getUniqueId();

        BlockStorage.addBlockInfo(location, "texture-item-display-entity", uuid.toString());
    }

    @EventHandler
    public static void onSlimefunBlockRemove(@NotNull SlimefunBlockBreakEvent event) {
        String uuidString = BlockStorage.getLocationInfo(event.getBlockBroken().getLocation(), "texture-item-display-entity");
        UUID uuid;
        try {
            uuid = UUID.fromString(uuidString);
        } catch (IllegalArgumentException e) {
            SlimefunBlockTextures.getInstance().getLogger().severe("Error while removing block: Invalid UUID");
            e.printStackTrace();
            return;
        }

        Entity entity = Bukkit.getEntity(uuid);
        if (entity != null) {
            entity.remove();
        }
    }
}
