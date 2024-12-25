package com.harleylizard.enhancedcelestials.shaders.mixin;

import com.harleylizard.enhancedcelestials.shaders.ColorAdjuster;
import com.harleylizard.enhancedcelestials.shaders.Pair;
import com.harleylizard.enhancedcelestials.shaders.ShaderLunarForecast;
import dev.corgitaco.enhancedcelestials.api.lunarevent.LunarEvent;
import dev.corgitaco.enhancedcelestials.lunarevent.LunarForecast;
import net.minecraft.core.Holder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LunarForecast.class)
public abstract class LunarForecastMixin implements ShaderLunarForecast {

    @Shadow public abstract Holder<LunarEvent> currentLunarEvent();

    @Shadow public abstract Holder<LunarEvent> lastLunarEvent();

    @Override
    public Pair harley$getColorSettings() {
        var left = currentLunarEvent();
        var right = lastLunarEvent();
        return new Pair(ColorAdjuster.adjustColor(left), ColorAdjuster.adjustColor(right));
    }
}
