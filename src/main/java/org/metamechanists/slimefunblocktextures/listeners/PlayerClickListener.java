package org.metamechanists.slimefunblocktextures.listeners;

import io.github.thebusybiscuit.slimefun4.api.events.SlimefunBlockPlaceEvent;
import io.lumine.mythic.api.adapters.AbstractLocation;
import io.lumine.mythic.api.adapters.AbstractVector;
import io.lumine.mythic.api.volatilecode.virtual.PacketItemDisplay;
import io.lumine.utils.serialize.Position;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;


public class PlayerClickListener implements Listener {

    @EventHandler
    public static void onSlimefunBlockPlace(@NotNull SlimefunBlockPlaceEvent event) {
        Location location = event.getBlockPlaced().getLocation();

        ItemStack stack = new ItemStack(Material.DIAMOND_BLOCK);
        stack.editMeta(meta -> meta.setCustomModelData(666));

        PacketItemDisplay.create()
                .item(stack)
                .brightness(15)
                .scale(new AbstractVector(1.001, 1.001, 1.001))
                .build(new AbstractLocation(Position.of(location)));
    }
}
