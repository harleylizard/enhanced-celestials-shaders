package com.harleylizard.enhancedcelestials.shaders;

import dev.corgitaco.enhancedcelestials.EnhancedCelestialsWorldData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL20.*;

public final class ProgramExtension {
    public static final Vector3f WHITE = new Vector3f(1.0F, 1.0F, 1.0F);

    private final int color;
    private final int lightmap;

    private ProgramExtension(int program) {
        color = verifyUniformLocation(glGetUniformLocation(program, "enhancedCelestialsColor"));
        lightmap = verifyUniformLocation(glGetUniformLocation(program, "enhancedCelestialsLightmap"));
    }

    public void upload() {
        var level = Minecraft.getInstance().level;
        if (level != null) {
            var vector3f = getColor(level);
            glUniform3f(color, vector3f.x, vector3f.y, vector3f.z);
            glUniform1i(lightmap, 2);
        }
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
        var lunarContext = worldData.getLunarContext();
        if (lunarContext != null) {
            var t = (float) (dayTime % 24000L) / 24000.0F;
            var forecast = lunarContext.getLunarForecast();
            return ((ShaderLunarForecast) forecast).harley$getColorSettings().mix(t, forecast.getBlend());
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
}