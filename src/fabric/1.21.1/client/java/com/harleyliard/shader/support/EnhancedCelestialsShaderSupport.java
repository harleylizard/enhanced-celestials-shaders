package com.harleyliard.shader.support;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.resources.ResourceLocation;

public final class EnhancedCelestialsShaderSupport implements ClientModInitializer {
    private static final String MOD_ID = "enhanced-celestials-shaders";

    @Override
    public void onInitializeClient() {
    }

    public static ResourceLocation resourceLocation(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }
}
