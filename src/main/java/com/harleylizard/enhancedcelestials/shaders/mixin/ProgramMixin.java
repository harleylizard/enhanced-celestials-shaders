package com.harleylizard.enhancedcelestials.shaders.mixin;

import com.harleylizard.enhancedcelestials.shaders.ProgramExtension;
import net.irisshaders.iris.gl.program.Program;
import net.irisshaders.iris.gl.program.ProgramImages;
import net.irisshaders.iris.gl.program.ProgramSamplers;
import net.irisshaders.iris.gl.program.ProgramUniforms;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Program.class, remap = false)
public final class ProgramMixin {
    @Unique
    private ProgramExtension extension;

    @Inject(method = "<init>", at = @At("TAIL"))
    public void harley$init(int program, ProgramUniforms uniforms, ProgramSamplers samplers, ProgramImages images, CallbackInfo ci) {
        extension = ProgramExtension.of(program);
    }

    @Inject(method = "use", at = @At("TAIL"), remap = false)
    public void harley$use(CallbackInfo ci) {
        extension.upload();
    }
}
