package com.harleylizard.enhancedcelestials.shaders;

import com.google.common.base.Suppliers;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import dev.corgitaco.enhancedcelestials.EnhancedCelestials;
import dev.corgitaco.enhancedcelestials.api.EnhancedCelestialsRegistry;
import dev.corgitaco.enhancedcelestials.api.client.ColorSettings;
import dev.corgitaco.enhancedcelestials.api.lunarevent.LunarEvent;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

public final class ReplaceColorSettings implements SimpleSynchronousResourceReloadListener {
    public static final Supplier<ReplaceColorSettings> SUPPLIER = Suppliers.memoize(ReplaceColorSettings::new);

    private static final ColorSettings WHITE = new ColorSettings(0xFFFFFF, 0xFFFFFF);

    private static final Codec<Map<ResourceLocation, ColorSettings>> CODEC = Codec.unboundedMap(ResourceLocation.CODEC, ColorSettings.CODEC);

    private final Map<ResourceLocation, ColorSettings> map = new HashMap<>();

    @Override
    public ResourceLocation getFabricId() {
        return EnhancedCelestialsShaders.resourceLocation("replace_color_settings");
    }

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
        map.clear();
        try (var reader = resourceManager.getResource(EnhancedCelestialsShaders.resourceLocation("replace_color_settings.json")).get().openAsReader()) {
            var result = CODEC.parse(JsonOps.INSTANCE, new Gson().fromJson(reader, JsonElement.class));
            if (result.isError()) {
                EnhancedCelestials.LOGGER.error("Failed to read colors {}", result.error().get().message());
                return;
            }
            map.putAll(result.getOrThrow());
        } catch (IOException e) {
            EnhancedCelestials.LOGGER.error("Failed to read colors.", e);
        }
    }

    public ColorSettings get(Holder<LunarEvent> holder) {
        return get(holder.value());
    }

    private ColorSettings get(LunarEvent event) {
        return map.getOrDefault(getAsKey(event), WHITE);
    }

    private ResourceLocation getAsKey(LunarEvent event) {
        return getRegistry().getKey(event);
    }

    private static Registry<LunarEvent> getRegistry() {
        return requireNonNull(Minecraft.getInstance().level).registryAccess().registry(EnhancedCelestialsRegistry.LUNAR_EVENT_KEY).orElseThrow();
    }
}
