import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import net.minecraftforge.gradle.tasks.DeobfuscateJar
import net.minecraftforge.gradle.user.UserBaseExtension
import net.minecraftforge.gradle.user.UserConstants

buildscript {
    repositories {
        mavenCentral()
        maven(Repos.forge)
    }
    dependencies {
        classpath(Plugins.FG23.classpath)
    }
}

apply(plugin = Plugins.FG23.id)

repositories {
    mavenCentral()
    maven(Repos.forge)
}

dependencies {
    compile(project(":common"))
}

configure<UserBaseExtension> {
    version = Deps.forgeVersion
    runDir = "run"
    mappings = Deps.mappings
    makeObfSourceJar = false
    isUseDepAts = true
}

val deobfMcMCP by tasks.getting(DeobfuscateJar::class) {
    isFailOnAtError = false
}

val deobfMcSRG by tasks.getting(DeobfuscateJar::class) {
    isFailOnAtError = false
}

val shadowJar by tasks.getting(ShadowJar::class)
val findByName = tasks.findByName(UserConstants.TASK_REOBF)
findByName?.apply {
    findByName.dependsOn(shadowJar)
}