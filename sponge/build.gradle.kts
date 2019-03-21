repositories {
    mavenCentral()
    maven(Repos.sponge)
}

dependencies {
    compile(project(":common"))
    compile(Deps.spongeapi)
}