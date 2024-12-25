package com.harleylizard.enhancedcelestials.shaders;

import dev.corgitaco.enhancedcelestials.api.client.ColorSettings;
import org.joml.Vector3f;

public record Pair(ColorSettings left, ColorSettings right) {

    public Vector3f mix(float t, float f) {
        return mix(ProgramExtension.WHITE, rightLeftMix(f), t);
    }

    private Vector3f rightLeftMix(float f) {
        return right.getGLSkyLightColor().lerp(left.getGLSkyLightColor(), f, new Vector3f());
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
