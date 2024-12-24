package com.harleylizard.enhancedcelestials.shaders;

import static org.lwjgl.opengl.GL20.*;

public final class ProgramUniforms {
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
}