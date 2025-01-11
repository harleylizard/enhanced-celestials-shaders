package com.harleylizard.enhancedcelestials.shaders;

import com.google.common.base.Suppliers;
import com.google.gson.*;
import dev.corgitaco.enhancedcelestials.EnhancedCelestials;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Supplier;

public final class ColorAdjusterConfig {
    private static final ColorAdjusterConfig FALLBACK = new ColorAdjusterConfig(0.0F, 127.0F / 255.0F);

    private static final Path PATH = FabricLoader.getInstance().getConfigDir().resolve("color_adjustor.json");

    private static final Serializer<ColorAdjusterConfig> SERIALIZER = new Serializer<>() {
        @Override
        public ColorAdjusterConfig deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            var jsonObject = json.getAsJsonObject();
            var brightness = jsonObject.getAsJsonPrimitive("brightness").getAsFloat();
            var grayness = jsonObject.getAsJsonPrimitive("grayness").getAsFloat();

            return new ColorAdjusterConfig(brightness, grayness);
        }

        @Override
        public JsonElement serialize(ColorAdjusterConfig src, Type typeOfSrc, JsonSerializationContext context) {
            var jsonObject = new JsonObject();
            jsonObject.addProperty("brightness", src.brightness);
            jsonObject.addProperty("grayness", src.grayness);

            return jsonObject;
        }
    };

    public static final Supplier<ColorAdjusterConfig> SUPPLIER = Suppliers.memoize(() -> {
        try {
            return getOrCreate();
        } catch (IOException e) {
            EnhancedCelestials.LOGGER.error("Failed to read config file.", e);
            return FALLBACK;
        }
    });

    private final float brightness;
    private final float grayness;

    private ColorAdjusterConfig(float brightness, float grayness) {
        this.brightness = brightness;
        this.grayness = grayness;
    }

    public float getBrightness() {
        return brightness;
    }

    public float getGrayness() {
        return grayness;
    }

    private static ColorAdjusterConfig getOrCreate() throws IOException {
        var gson = new GsonBuilder()
                .registerTypeAdapter(ColorAdjusterConfig.class, SERIALIZER)
                .create();

        var parent = PATH.getParent();
        if (Files.isDirectory(parent)) Files.createDirectories(parent);

        if (Files.isRegularFile(PATH)) {
            try (var reader = Files.newBufferedReader(PATH)) {
                return gson.fromJson(reader, ColorAdjusterConfig.class);
            }
        }
        try (var writer = Files.newBufferedWriter(PATH)) {
            gson.toJson(FALLBACK, ColorAdjusterConfig.class, writer);
            return FALLBACK;
        }
    }
}
