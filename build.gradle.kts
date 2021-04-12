import net.minecraftforge.gradle.userdev.UserDevExtension
import net.minecraftforge.gradle.common.util.RunConfig

buildscript {
    repositories {
        maven(url = "https://files.minecraftforge.net/maven")
        maven(url = "https://repo.spongepowered.org/maven")
        mavenCentral()
    }
    dependencies {
        classpath("net.minecraftforge.gradle", "ForgeGradle", "4.1.+") {
            isChanging = true
        }
        classpath("org.spongepowered", "mixingradle", "0.7-SNAPSHOT")
    }
}

plugins {
    id("java")
}

apply {
    plugin("net.minecraftforge.gradle")
    plugin("org.spongepowered.mixin")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

// gradle.properties
val modGroup: String by project
val modVersion: String by project
val modBaseName: String by project
val forgeVersion: String by project
val minecraftVersion: String by project

base {
    version = modVersion
    group = modGroup
}

configure<UserDevExtension> {
    mappings(mapOf(
            "channel" to "official",
            "version" to minecraftVersion
    ))

    runs {
        val runConfig = Action<RunConfig> {
            properties(mapOf(
                    "forge.logging.markers" to "SCAN,REGISTRIES,REGISTRYDUMP",
                    "forge.logging.console.level" to "debug",
                    "Dmixin.hotSwap" to "true",
                    "Dmixin.checks.interfaces" to "true"
            ))
            workingDirectory = project.file("run").canonicalPath
            source(sourceSets["main"])
        }
        create("client", runConfig)
    }
}

configure<BasePluginConvention> {
    archivesBaseName = modBaseName
}

dependencies {
    "minecraft"("net.minecraftforge:forge:1.16.5-36.1.0")
    "annotationProcessor"("org.spongepowered:mixin:0.8.2:processor")
}

sourceSets {
    main {
        resources.srcDir("src/generated/resources")
        ext.set("refMap", "mixins.forgeblockerbypass.refmap.json")
    }
}

tasks.named<Copy>("processResources") {
    val properties = mapOf(
            "version" to modVersion,
            "forgeVersion" to forgeVersion,
            "minecraftVersion" to minecraftVersion,
            "MixinConfigs" to "mixins.forgeblockerbypass.json"
    )
    properties.forEach { (key, value) ->
        inputs.property(key, value)
    }

    filesMatching("/META-INF/mods.toml") {
        expand("modversion" to modVersion)
    }
}