package com.harleylizard.enhancedcelestials.shaders;

import dev.corgitaco.enhancedcelestials.EnhancedCelestials;

public final class ShaderTransformer {
    private final Source source;

    private ShaderTransformer(Source source) {
        this.source = source;
    }

    public String transform() {
        var version = source.version();

        if (version >= 330) {
            var outputs = source.outputs();
            if (outputs.size() == 1) {
                var first = outputs.getFirst();
                if (first.type().equals("vec4")) transformFragment(first.name());
            }
        }
        else EnhancedCelestials.LOGGER.error("Older GLSL versions are currently not supported. This is intentional as of writing.");

        return source.toString();
    }

    private void transformFragment(String color) {
        source.uniform("vec3", Uniforms.SKYLIGHT_COLOR);
        source.uniform("sampler2D", Uniforms.LIGHTMAP);

        var i = source.indexOf("%s =", color);
        var assignment = source.assignment(i);
        source.add(i - 1, "vec4 skylightColor = vec4(ecSkylightColor, 1.0F);");

        source.result(color, "%s * skylightColor".formatted(assignment));
    }

    // TODO:: Add lightmap coordinates.
    private void transformVertex() {
    }

    public static ShaderTransformer of(String source) {
        return new ShaderTransformer(Source.of(source));
    }
}
