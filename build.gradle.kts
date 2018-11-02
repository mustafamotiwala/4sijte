import org.jooby.run.JoobyTask

// In this section you declare where to find the dependencies of your project
repositories {
    // Use jcenter for resolving your dependencies.
    // You can declare any Maven/Ivy/file repository here.
    jcenter()
    mavenLocal()
    mavenCentral()
}

val junitPlatformVersion = "1.0.0"
val spekVersion: String by project
val joobyVersion: String by project
val version: String by project

project.version = version

plugins {
    // Apply the application plugin to add support for building an application
    application
    kotlin("jvm").version("1.2.60")
    id("jooby").version("1.5.0")
}

application {
    // Define the main class for the application
    mainClassName="fsijte.App"
}

dependencies {
    implementation(kotlin("stdlib"))

    // Jooby Dependencies:
    implementation("org.jooby:jooby-lang-kotlin:$joobyVersion")
    implementation("org.jooby:jooby-undertow:$joobyVersion")
    implementation("org.jooby:jooby-jackson:$joobyVersion")

    // JSON Serialization
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.8.3")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.2")

    //Security:
    implementation("io.jsonwebtoken:jjwt:0.7.0")
    implementation("de.svenkubiak:jBCrypt:0.4.1")
    implementation("org.hashids:hashids:1.0.2")

    testCompile("com.github.kittinunf.fuel:fuel:1.12.1")
    testCompile("com.github.kittinunf.fuel:fuel-gson:1.12.1")
    testCompile("io.mockk:mockk:1.7.7")
    testCompile("org.jetbrains.spek:spek-api:$spekVersion")
    testCompile("org.junit.platform:junit-platform-runner:1.0.0")
    testCompile("org.amshove.kluent:kluent:1.23")
}

//Jooby Specific configurations:
sourceSets["main"].resources {
    srcDirs("conf", "public")
}


val joobyRun = tasks["joobyRun"] as JoobyTask
joobyRun.apply {
    this.compiler = "on"
    this.includes = listOf("**/*.class", "**/*.conf", "**/*.properties")
    this.excludes = emptyList<String>()
    this.logLevel = "info"
}
