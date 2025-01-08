package com.harleylizard.enhancedcelestials.shaders;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.resources.ResourceLocation;

public final class EnhancedCelestialsShaders implements ClientModInitializer {
    private static final String MOD_ID = "enhanced_celestials_shaders";

    @Override
    public void onInitializeClient() {
    }

    public static ResourceLocation resourceLocation(String name) {
        return new ResourceLocation(MOD_ID, name);
    }
}
