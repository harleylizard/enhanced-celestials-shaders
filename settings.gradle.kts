pluginManagement {
	repositories {
		gradlePluginPortal()
		mavenCentral()
		maven("https://maven.fabricmc.net/")
		maven("https://maven.neoforged.net/")
		maven("https://maven.minecraftforge.net/")
		maven("https://repo.spongepowered.org/repository/maven-public/")
		maven("https://maven.soulsoftware.dev/releases")
	}
}

rootProject.name = "enhanced-celestials-shaders"
include("neoforge")
include("fabric")
include("common")
