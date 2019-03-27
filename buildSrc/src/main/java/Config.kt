@file:Suppress("MayBeConstant", "unused")

object Repos {
    const val forge = "https://files.minecraftforge.net/maven/"
    const val sponge = "https://repo.spongepowered.org/maven/"
    const val sonatype = "https://oss.sonatype.org/content/groups/public/"
    const val sonatypeSnapshots = "https://oss.sonatype.org/content/repositories/snapshots"
    const val sonatypeReleases = "https://oss.sonatype.org/content/repositories/releases"
}

object Groups {
    const val almura = "com.almuradev"
    const val asm = "org.ow2.asm"
    const val kyori = "net.kyori"
    const val flow = "com.flowpowered"
    const val google = "com.google"
    const val googleInject = "$google.inject"
    const val codeFindBugs = "$google.code.findbugs"
    const val junit = "junit"
    const val minecraftForge = "net.minecraftforge"
    const val minecraftForgeGradle = "$minecraftForge.gradle"
    const val sponge = "org.spongepowered"
}

object Plugins {
    object FG23 {
        const val classpath = "${Groups.minecraftForgeGradle}:ForgeGradle:${Versions.fg23}"
        const val extensionName = "minecraft"
        const val id = "${Groups.minecraftForgeGradle}.forge"
    }

    const val licenser = "net.minecrell.licenser"
    const val shadow = "com.github.johnrengelman.shadow"
}

object Deps {
    const val forgeVersion = "1.12.2-14.23.5.2768"
    const val mappings = "stable_39"

    const val configurate = "${Groups.sponge}:configurate-core:${Versions.configurate}"
    const val jsr305 = "${Groups.codeFindBugs}:jsr305:${Versions.jsr305}"
    const val guice = "${Groups.googleInject}:guice:${Versions.guice}"
    const val junit = "${Groups.junit}:junit:${Versions.junit}"
    const val spongeapi = "${Groups.sponge}:spongeapi:${Versions.spongeapi}"

    object Kyori {
        const val lunar = "${Groups.kyori}:lunar:${Versions.Kyori.lunar}"
        const val membrane = "${Groups.kyori}:membrane:${Versions.Kyori.membrane}"
        const val violet = "${Groups.kyori}:violet:${Versions.Kyori.violet}"
    }

    object Flow {
        const val math = "${Groups.flow}:flow-math:${Versions.Flow.math}"
    }
}

object License {
    const val name = "Toolbox"
    const val organization = "AlmuraDev"
    const val url = "https://www.almuramc.com"
}

object Shadow {
    object Exclude {
        object ForgeGradle {
            const val dummyThing = "dummyThing"
            const val template = "Version.java.template"
        }
    }
}