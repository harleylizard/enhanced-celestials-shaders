package com.harleylizard.enhancedcelestials.shaders;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;

public final class EnhancedCelestialsShaders implements ClientModInitializer {
    private static final String MOD_ID = "enhanced_celestials_shaders";

    @Override
    public void onInitializeClient() {
        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(ReplaceColorSettings.SUPPLIER.get());
    }

    public static ResourceLocation resourceLocation(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }
}
