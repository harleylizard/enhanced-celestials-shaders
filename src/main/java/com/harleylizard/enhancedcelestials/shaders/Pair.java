package com.harleylizard.enhancedcelestials.shaders;

import dev.corgitaco.enhancedcelestials.api.client.ColorSettings;
import org.joml.Vector3f;

public record Pair(ColorSettings left, ColorSettings right) {

    public Vector3f mix(float t) {
        return left.getGLSkyLightColor().lerp(right.getGLSkyLightColor(), t, new Vector3f());
    }
}
