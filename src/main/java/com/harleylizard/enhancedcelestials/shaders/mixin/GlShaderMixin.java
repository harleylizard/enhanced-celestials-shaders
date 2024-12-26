package com.harleylizard.enhancedcelestials.shaders.mixin;

import com.harleylizard.enhancedcelestials.shaders.ShaderTransformer;
import net.irisshaders.iris.gl.shader.GlShader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = GlShader.class, remap = false)
public final class GlShaderMixin {

    @ModifyArg(method = "createShader", at = @At(value = "INVOKE", target = "Lnet/irisshaders/iris/gl/shader/ShaderWorkarounds;safeShaderSource(ILjava/lang/CharSequence;)V"), index = 1, remap = false)
    private static CharSequence harley$createShader(CharSequence source) {
        return ShaderTransformer.of((String) source).transform();
    }
}
