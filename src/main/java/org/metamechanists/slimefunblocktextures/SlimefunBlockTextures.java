package org.metamechanists.slimefunblocktextures;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;
import org.metamechanists.slimefunblocktextures.old.Util;
import org.metamechanists.slimefunblocktextures.old.config.BlockModels;
import org.metamechanists.slimefunblocktextures.old.runnables.RogueDisplayYeeter;


public final class SlimefunBlockTextures extends JavaPlugin implements SlimefunAddon {
    @Getter
    private static SlimefunBlockTextures instance;

    @Override
    public void onEnable() {
        instance = this;
        Util.init();
        BlockModels.init();
        BlockStorageCache.init();
        Bukkit.getServer().getPluginManager().registerEvents(new BlockStorageCache(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerSpaces(), this);
        Bukkit.getServer().getScheduler().runTaskTimer(this, new RogueDisplayYeeter(), 0, 100);
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
