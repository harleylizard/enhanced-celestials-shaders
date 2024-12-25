package com.harleylizard.enhancedcelestials.shaders;

import dev.corgitaco.enhancedcelestials.api.client.ColorSettings;
import dev.corgitaco.enhancedcelestials.api.lunarevent.LunarEvent;
import dev.corgitaco.enhancedcelestials.util.ColorUtil;
import net.minecraft.core.Holder;

public final class ColorAdjuster {

    private ColorAdjuster() {}

    private static int adjustColor(int color) {
        var unpacked = ColorUtil.unpack(color);
        var r = ((float) unpacked[1]) / 255.0F;
        var g = ((float) unpacked[2]) / 255.0F;
        var b = ((float) unpacked[3]) / 255.0F;

        var max = Math.max(Math.max(r, g), b);

        var brightness = 0.0F;
        r += brightness;
        g += brightness;
        b += brightness;

        var grayness = 127.0F / 255.0F;
        r += max >= r ? 0.0F : grayness;
        g += max >= g ? 0.0F : grayness;
        b += max >= b ? 0.0F : grayness;

        return ColorUtil.pack(
                255,
                (int) (r * 255.0F) & 0xFF,
                (int) (g * 255.0F) & 0xFF,
                (int) (b * 255.0F) & 0xFF);
    }

    public static ColorSettings adjustColor(Holder<LunarEvent> holder) {
        var settings = holder.value().getClientSettings().colorSettings();
        return new ColorSettings(adjustColor(settings.getSkyLightColor()), settings.getMoonTextureColor());
    }
}
