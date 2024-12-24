package com.harleylizard.enhancedcelestials.shaders;

import dev.corgitaco.enhancedcelestials.EnhancedCelestialsWorldData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import org.joml.Vector3f;

import javax.annotation.Nullable;

import static org.lwjgl.opengl.GL20.*;

public final class ProgramExtension {
    public static final Vector3f WHITE = new Vector3f(1.0F, 1.0F, 1.0F);

    private final int color;
    private final int lightmap;

    private ProgramExtension(int program) {
        color = verifyUniformLocation(glGetUniformLocation(program, "enhancedCelestialsColor"));
        lightmap = verifyUniformLocation(glGetUniformLocation(program, "enhancedCelestialsLightmap"));
    }

    public void uploadColor() {
        @Nullable
        var level = Minecraft.getInstance().level;
        if (level != null) {
            var vector3f = getColor(level);
            glUniform3f(color, vector3f.x, vector3f.y, vector3f.z);
        }
    }

    public void uploadLightmap(int unit) {
        glUniform1i(lightmap, unit);
    }

    public static ProgramExtension of(int program) {
        if (!glIsProgram(program)) {
            throw new UnsupportedOperationException("Attempted to use a non GL program.");
        }
        return new ProgramExtension(program);
    }

    private static Vector3f getColor(ClientLevel level) {
        return getColor((EnhancedCelestialsWorldData) level, level.getDayTime());
    }

    private static Vector3f getColor(EnhancedCelestialsWorldData worldData, long dayTime) {
        @Nullable
        var lunarContext = worldData.getLunarContext();
        if (lunarContext != null) {
            var t = 1.0F; // TODO:: Lerp between day -> night (ambient color)
            return ((ShaderLunarForecast) lunarContext.getLunarForecast()).harley$getColorSettings().mix(t);
        }
        return WHITE;
    }

    private static int verifyUniformLocation(int location) {
        if (location < 0) {
            // Uncomment when needed.
            // EnhancedCelestials.LOGGER.error("Unable to find uniform location for program.");
        }
        return location;
    }

    // TODO:: Replace curve function to be parabola and use in interpolation.
    private static float sigmoid(long x) {
        return 1.0F / (1.0F + (float) Math.exp(-(float)x));
    }
}