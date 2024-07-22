package org.metamechanists.slimefunblocktextures;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;
import org.metamechanists.slimefunblocktextures.listeners.PlayerClickListener;


public final class SlimefunBlockTextures extends JavaPlugin implements SlimefunAddon {

    @Getter
    private static SlimefunBlockTextures instance;

    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerClickListener(), this);

    }

    @Override
    public void onDisable() {

    }

    @NonNull
    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

    @Nullable
    @Override
    public String getBugTrackerURL() {
        return null;
    }
}
