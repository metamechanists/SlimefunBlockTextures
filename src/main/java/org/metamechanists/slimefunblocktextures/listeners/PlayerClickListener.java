package org.metamechanists.slimefunblocktextures.listeners;

import io.github.thebusybiscuit.slimefun4.api.events.SlimefunBlockPlaceEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.metamechanists.displaymodellib.models.components.ModelItem;


public class PlayerClickListener implements Listener {

    @EventHandler
    public static void onSlimefunBlockPlace(@NotNull SlimefunBlockPlaceEvent event) {
        Location location = event.getBlockPlaced().getLocation();

        new ModelItem()
                .item(new ItemStack(Material.DIAMOND_BLOCK))
                .brightness(15)
                .scale(1.001)
                .build(location.toCenterLocation());
    }
}
