package com.harleylizard.enhancedcelestials.shaders;

import dev.corgitaco.enhancedcelestials.api.client.ColorSettings;
import org.joml.Vector3f;

public record Pair(ColorSettings left, ColorSettings right) {

    // Corgi Taco has day and night hard-coded to be neon blue. Replaced with white until solution is done.
    public Vector3f mix(float t) {
        return mix(ProgramExtension.WHITE, left.getSkyLightColor() == 0x3333FF ? ProgramExtension.WHITE : left.getGLSkyLightColor(), t);
    }

    private static Vector3f mix(Vector3f first, Vector3f second, float t) {
        return new Vector3f(
                parabola(first.x, second.x, t),
                parabola(first.y, second.y, t),
                parabola(first.z, second.z, t)
        );
    }

    private static float parabola(float start, float end, float t) {
        return (1.0F - t) * start + t * end - 4.0F * (t - 0.5F) * (t - 0.5F) * (end - start);
    }
}
