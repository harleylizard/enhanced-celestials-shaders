plugins {
    id("java")
    id("fabric-loom") version "1.9-SNAPSHOT"
}

group = "com.harleylizard"
version = "1.20.1+1.1-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://api.modrinth.com/maven")
}

dependencies {
    minecraft("com.mojang:minecraft:1.20.1")
    mappings(loom.officialMojangMappings())

    modImplementation("net.fabricmc:fabric-loader:0.16.9")
    modImplementation("net.fabricmc.fabric-api:fabric-api:0.92.3+1.20.1")

    modImplementation("maven.modrinth:enhanced-celestials:1.20.1-5.0.2.3-fabric")
    modImplementation("maven.modrinth:sodium:mc1.20.1-0.5.11-fabric")
    modImplementation("maven.modrinth:iris:1.7.5+1.20.1-fabric")
    modApi(fileTree("libs"))

    runtimeOnly("io.github.douira:glsl-transformer:2.0.1")
    runtimeOnly("org.anarres:jcpp:1.4.14")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

configurations.all {
    resolutionStrategy {
        force("org.lwjgl:lwjgl:3.3.1")
        force("org.lwjgl:lwjgl-glfw:3.3.1")
        force("org.lwjgl:lwjgl-jemalloc:3.3.1")
        force("org.lwjgl:lwjgl-openal:3.3.1")
        force("org.lwjgl:lwjgl-opengl:3.3.1")
        force("org.lwjgl:lwjgl-stb:3.3.1")
        force("org.lwjgl:lwjgl-tinyfd:3.3.1")
    }
}

tasks.test {
    useJUnitPlatform()
}

java {
    withSourcesJar()
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}