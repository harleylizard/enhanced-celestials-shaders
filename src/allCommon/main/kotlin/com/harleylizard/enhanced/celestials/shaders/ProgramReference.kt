package com.harleylizard.enhanced.celestials.shaders

class ProgramReference(program: Int) {

    init {

        program.takeUnless { glClear() }

    }


}