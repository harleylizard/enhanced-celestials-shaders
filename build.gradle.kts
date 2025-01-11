plugins {
    id("java")
    id("fabric-loom") version "1.9-SNAPSHOT"
}

group = "com.harleylizard"
version = "1.21.1+1.2-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://api.modrinth.com/maven")
}

dependencies {
    minecraft("com.mojang:minecraft:1.21.1")
    mappings(loom.officialMojangMappings())

    modImplementation("net.fabricmc:fabric-loader:0.16.9")
    modImplementation("net.fabricmc.fabric-api:fabric-api:0.110.0+1.21.1")

    modImplementation("maven.modrinth:enhanced-celestials:6.0.1.2-fabric")
    modImplementation("maven.modrinth:sodium:mc1.21.1-0.6.1-fabric")
    modImplementation("maven.modrinth:iris:1.8.1+1.21.1-fabric")
    modApi(fileTree("libs"))

    runtimeOnly("io.github.douira:glsl-transformer:2.0.1")
    runtimeOnly("org.anarres:jcpp:1.4.14")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

tasks.named("processResources", ProcessResources::class.java) {
    inputs.property("version", project.version)

    filesMatching(listOf("fabric.mod.json")) {
        expand("version" to project.version)
    }
}

java {
    withSourcesJar()
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}