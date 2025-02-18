package com.harleylizard.enhanced.celestials.shaders

import java.util.*


class MutableSource private constructor(private val lines: MutableList<String>) {
    private val builder = StringBuilder()
    private val formatter = Formatter(builder)

    val version: Int get() = lines[0].let {
        val i = it.indexOf(versionMacro) + versionMacro.length + 1

        it.substring(i, it.indexOf(" ", i)).asInt
    }

    operator fun MutableList<String>.plusAssign(line: String) {
        add(1, line)
    }

    fun add(i: Int, line: String) {
        lines.add(i, line)
    }

    fun addUniform(type: String, name: String) {
        lines += "uniform %s %s;".format(type, name)
    }

    fun addConstant(type: String, name: String, result: String) {
        lines += "const %s %s = %s;".format(type, name, result)
    }

    fun replaceResult(name: String, replacement: String) {
        val regex = "%s = ".format(name)
        var j = -1
        for (i in lines.size - 1 downTo 1) {
            if (lines[i].contains(regex)) {
                j = i
                break
            }
        }
        if (j > -1) {
            clean()
            lines[j] = "%s = %s;".format(name, replacement)
        }
    }

    fun resultAt(i: Int) = lines[i].let { it ->
        it.substring(it.indexOf("=") + 1, it.lastIndexOf(";")).trim { it <= ' ' }
    }

    private fun clean() {
        builder.setLength(0)
    }

    private fun String.format(vararg params: Any) = params.let {
        clean()
        formatter.format(this, it).toString()
    }

    companion object {
        private const val versionMacro = "#version"

        private val String.asInt: Int get() {
            return try {
                Integer.parseInt(this)
            } catch (e: Exception) {
                0
            }
        }

    }
}