package org.metamechanists.slimefunblocktextures;

import lombok.experimental.UtilityClass;
import org.bukkit.NamespacedKey;


@UtilityClass
public class Util {
    public NamespacedKey blockTextureKey;

    public void init() {
        blockTextureKey = new NamespacedKey(SlimefunBlockTextures.getInstance(), "BLOCK_TEXTURE");
    }
}
