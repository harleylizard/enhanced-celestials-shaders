import soul.software.snail.dependency.exclusiveMaven
import soul.software.snail.dependency.snail

plugins {
    id("soul.software.snail") version "3.1-SNAPSHOT"
}

group = "com.harleylizard"
version = "1.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

snail {
    multiple("1.21.4") {
        properties {
            name = "Enhanced Celestials Shader Support"
            id = "enhanced-celestials-shader-support"
            version = "1.0-SNAPSHOT"
            description = "For Iris and Enhanced Celestials"
        }

        val common = project(":common") {
            sponge()
        }

        all {
            from(common)
        }

        project(":fabric") {
            fabric {
                entryPoints {
                    main = listOf("com.harleylizard.shadersupport.EnhancedCelestialsShaderSupport")
                }
            }
        }

        project(":neoforge") {
            neoForge()
        }
    }
}

subprojects {
    repositories {
        maven("https://api.modrinth.com/maven/")
    }

    dependencies {
        runtimeOnly("io.github.douira:glsl-transformer:2.0.1")
        runtimeOnly("org.anarres:jcpp:1.4.14")
    }
}
