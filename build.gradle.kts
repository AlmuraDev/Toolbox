val common: Project by extra { project("common")}
val forge: Project by extra { project("forge")}
val sponge: Project by extra { project("sponge")}

plugins {
    `java-library`
    `maven-publish`
    id(Plugins.shadow) version Versions.shadow
    id(Plugins.licenser) version Versions.licenser
}

dependencies {
    compile(common)
    compile(forge)
    compile(sponge)
}

allprojects {
    apply(plugin="java-library")
    apply(plugin=Plugins.shadow)
    apply(plugin=Plugins.licenser)

    repositories {
        mavenCentral()
        maven(Repos.sonatype)
        maven(Repos.sonatypeSnapshots)
        maven(Repos.sponge)
    }

    defaultTasks("licenseFormat", "build")

    license {
        ext {
            this["name"] = License.name
            this["organization"] = License.organization
            this["url"] = License.url
        }
        header = rootProject.file("header.txt")
        newLine = false
    }
}

subprojects {
    dependencies {
        compile(Deps.configurate)
        compile(Deps.guice)
        compile(Deps.junit)
        compile(Deps.Flow.math)
        compile(Deps.Kyori.lunar)
        compile(Deps.Kyori.membrane)
        compile(Deps.Kyori.violet)
    }
}

val commonSources: SourceSetContainer by extra { common.convention.findPlugin(JavaPluginConvention::class)!!.sourceSets }
val forgeSources: SourceSetContainer by extra { forge.convention.findPlugin(JavaPluginConvention::class)!!.sourceSets }
val spongeSources: SourceSetContainer by extra { sponge.convention.findPlugin(JavaPluginConvention::class)!!.sourceSets }

val devJar by tasks.creating(Jar::class) {
    classifier = "dev"
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    afterEvaluate {
        from(commonSources["main"].output)
        from(forgeSources["main"].output)
        from(spongeSources["main"].output)
    }
}

val jar by tasks.getting(Jar::class) {
    afterEvaluate {
        from(commonSources["main"].output)
        from(forgeSources["main"].output)
        from(spongeSources["main"].output)
    }
}