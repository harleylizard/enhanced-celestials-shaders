package com.harleylizard.enhancedcelestials.shaders;

import dev.corgitaco.enhancedcelestials.EnhancedCelestialsWorldData;
import net.minecraft.client.multiplayer.ClientLevel;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL20.*;

public final class ProgramUniforms {
    private static final Vector3f WHITE = new Vector3f(1.0F, 1.0F, 1.0F);

    private final int color;
    private final int lightmap;

    private ProgramUniforms(int program) {
        color = glGetUniformLocation(program, "enhancedCelestialsColor");
        lightmap = glGetUniformLocation(program, "enhancedCelestialsLightmap");
    }

    public void uploadColor(float r, float g, float b) {
        glUniform4f(color, r, g, b, 1.0F);
    }

    public void uploadLightmap(int unit) {
        glUniform1i(lightmap, unit);
    }

    public static ProgramUniforms of(int program) {
        if (!glIsProgram(program)) {
            throw new UnsupportedOperationException("Attempted to use a non GL program.");
        }
        return new ProgramUniforms(program);
    }

    private static Vector3f getColor(ClientLevel level) {
        return getColor((EnhancedCelestialsWorldData) level);
    }

    private static Vector3f getColor(EnhancedCelestialsWorldData worldData) {
        var lunarContext = worldData.getLunarContext();
        if (lunarContext != null) {
            var t = 0.0F;
            return ((ShaderLunarForecast) lunarContext.getLunarForecast()).harley$getColorSettings().mix(t);
        }
        return WHITE;
    }
}