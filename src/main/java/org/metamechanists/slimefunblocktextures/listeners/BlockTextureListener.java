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
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.metamechanists.displaymodellib.models.components.ModelItem;
import org.metamechanists.slimefunblocktextures.SlimefunBlockTextures;
import org.metamechanists.slimefunblocktextures.Util;
import org.metamechanists.slimefunblocktextures.config.BlockModels;

import java.util.UUID;


public class BlockTextureListener implements Listener {
    @EventHandler
    public static void onSlimefunBlockPlace(@NotNull SlimefunBlockPlaceEvent event) {
        Util.createBlockTexture(event.getBlockPlaced().getLocation(), event.getSlimefunItem());
    }

    @EventHandler
    public static void onSlimefunBlockRemove(@NotNull SlimefunBlockBreakEvent event) {
        String uuidString = BlockStorage.getLocationInfo(event.getBlockBroken().getLocation(), "texture-item-display-entity");
        if (uuidString == null) {
            return;
        }

        UUID uuid;
        try {
            uuid = UUID.fromString(uuidString);
        } catch (IllegalArgumentException e) {
            SlimefunBlockTextures.getInstance().getLogger().severe("Error while removing block: " + e);
            return;
        }

        Entity entity = Bukkit.getEntity(uuid);
        if (entity != null) {
            entity.remove();
        }
    }
}
