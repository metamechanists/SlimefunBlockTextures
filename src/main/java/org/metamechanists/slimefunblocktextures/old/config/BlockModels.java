package org.metamechanists.slimefunblocktextures.old.config;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import lombok.experimental.UtilityClass;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.metamechanists.slimefunblocktextures.SlimefunBlockTextures;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@UtilityClass
public class BlockModels {
    private final Map<String, Integer> blockModels = new HashMap<>();

    public void init() {
        File blockModels = new File(SlimefunBlockTextures.getInstance().getJavaPlugin().getDataFolder(), "block-models.yml");

        FileConfiguration configuration = YamlConfiguration.loadConfiguration(blockModels);
        for (String key : configuration.getKeys(false)) {
            try {
                int modelData = configuration.getInt(key);
                if (modelData != 0) {
                    BlockModels.blockModels.put(key, modelData);
                }
            } catch (RuntimeException e) {
                SlimefunBlockTextures.getInstance().getLogger().severe("Error loading block model: " + e);
            }
        }

        // Fill in any missing IDs
        for (SlimefunItem item : Slimefun.getRegistry().getAllSlimefunItems()) {
            if (!configuration.contains(item.getId())) {
                configuration.set(item.getId(), 0);
                BlockModels.blockModels.put(item.getId(), 0);
            }
        }

        try {
            configuration.save(blockModels);
        } catch (IOException e) {
            SlimefunBlockTextures.getInstance().getLogger().severe("Failed to save config file: " + e);
        }
    }

    public Integer getBlockModel(String id) {
        return blockModels.get(id);
    }
}
