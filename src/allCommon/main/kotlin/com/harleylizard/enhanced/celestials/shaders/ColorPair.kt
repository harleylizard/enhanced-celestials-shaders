package com.harleylizard.enhanced.celestials.shaders

import org.joml.Vector3f

@JvmRecord
data class ColorPair(val left: HasColor, val right: HasColor) {

    companion object {

        private fun Vector3f.parabola(end: Vector3f, time: Float) = Vector3f(
            x.parabola(end.x, time),
            y.parabola(end.y, time),
            z.parabola(end.z, time)
        )

        private fun Float.parabola(end: Float, time: Float): Float {
            return (1.0F - time) * this + time * end - 4.0F * (time - 0.5F) * (time - 0.5F) * (end - this)
        }
    }
}