package org.metamechanists.slimefunblocktextures;

import de.cubbossa.cliententities.PlayerSpace;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;
import org.metamechanists.slimefunblocktextures.config.BlockModels;
import org.metamechanists.slimefunblocktextures.listeners.BlockTextureListener;
import org.metamechanists.slimefunblocktextures.runnables.NewDisplaySpawner;
import org.metamechanists.slimefunblocktextures.runnables.RogueDisplayYeeter;


public final class SlimefunBlockTextures extends JavaPlugin implements SlimefunAddon {

    @Getter
    private static SlimefunBlockTextures instance;

    @Override
    public void onEnable() {
        instance = this;
        Util.init();
        BlockModels.init();
//        Bukkit.getServer().getPluginManager().registerEvents(new BlockTextureListener(), this);
//        Bukkit.getServer().getScheduler().runTaskTimer(this, new RogueDisplayYeeter(), 0, 100);
//        Bukkit.getServer().getScheduler().runTaskLater(this, new NewDisplaySpawner(), 0);
        PlayerSpace playerSpace = PlayerSpace.createGlobal(this).withInterval(200).build();
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
