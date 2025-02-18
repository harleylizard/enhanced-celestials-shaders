import earth.terrarium.cloche.target.FabricTarget

plugins {
    id("java")
    id("earth.terrarium.cloche") version "0.7.3"
    kotlin("jvm") version "2.1.0"
}

group = "com.harleylizard"
version = "1.1-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://api.modrinth.com/maven")
    maven(url = "https://maven.msrandom.net/repository/root")
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

cloche {
    metadata {
        modId.set("enhanced-celestials-shaders")
    }

    targets.all {
        dependencies {
            runtimeOnly("io.github.douira:glsl-transformer:2.0.1")
            runtimeOnly("org.anarres:jcpp:1.4.14")
        }
    }

    val common = common("allCommon") {
    }

    targets.withType<FabricTarget> {
        loaderVersion = "0.16.9"
        client()
        data()
    }

    fabric("fabric:1.20.1") {
        minecraftVersion = "1.20.1"
        dependsOn(common)
        dependencies {
            fabricApi("0.92.3+1.20.1")
            modImplementation("maven.modrinth:enhanced-celestials:1.20.1-5.0.2.3-fabric")
            modImplementation("maven.modrinth:sodium:mc1.20.1-0.5.11-fabric")
            modImplementation("maven.modrinth:iris:1.7.5+1.20.1-fabric")
        }
    }

    fabric("fabric:1.21.1") {
        minecraftVersion = "1.21.1"
        dependsOn(common)
        dependencies {
            fabricApi("0.110.0+1.21.1")
            modImplementation("maven.modrinth:enhanced-celestials:6.0.1.2-fabric")
            modImplementation("maven.modrinth:sodium:mc1.21.1-0.6.1-fabric")
            modImplementation("maven.modrinth:iris:1.8.1+1.21.1-fabric")
        }
    }

    forge("forge:1.20.1") {
        minecraftVersion = "1.20.1"
        loaderVersion = "47.1.3"
        dependsOn(common)
    }

    neoforge("neoforge:1.21.1") {
        minecraftVersion = "1.21.1"
        loaderVersion = "21.1.90"
        dependsOn(common)
    }
}

java {
    withSourcesJar()
}

tasks.test {
    useJUnitPlatform()
}

