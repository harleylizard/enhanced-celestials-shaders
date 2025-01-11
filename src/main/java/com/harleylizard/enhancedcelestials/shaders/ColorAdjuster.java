package com.harleylizard.enhancedcelestials.shaders;

import dev.corgitaco.enhancedcelestials.util.ColorUtil;

public final class ColorAdjuster {

    private ColorAdjuster() {}

    public static int adjustColor(int color) {
        if (color == 0x3333FF) {
            return 0xFFFFFF;
        }
        var unpacked = ColorUtil.unpack(color);
        var r = ((float) unpacked[1]) / 255.0F;
        var g = ((float) unpacked[2]) / 255.0F;
        var b = ((float) unpacked[3]) / 255.0F;

        var max = Math.max(Math.max(r, g), b);

        var config = ColorAdjusterConfig.SUPPLIER.get();
        var brightness = config.getBrightness();
        r += brightness;
        g += brightness;
        b += brightness;

        var grayness = config.getGrayness();
        r += max >= r ? 0.0F : grayness;
        g += max >= g ? 0.0F : grayness;
        b += max >= b ? 0.0F : grayness;

        return ColorUtil.pack(
                255,
                (int) (r * 255.0F) & 0xFF,
                (int) (g * 255.0F) & 0xFF,
                (int) (b * 255.0F) & 0xFF);
    }
}
