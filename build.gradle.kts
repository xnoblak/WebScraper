val koinVersion: String = "3.5.3"

plugins {
    kotlin("jvm") version "1.9.21"
}

group = "org.noblak"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Koin for Kotlin apps
    compileOnly("io.insert-koin:koin-core:$koinVersion")
    // Kotlin coroutine dependency
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    // MongoDB Kotlin driver dependency
    implementation("org.mongodb:mongodb-driver-kotlin-coroutine:4.10.1")
    //Test implementations
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}

tasks.register<Jar>("uberJar") {
    manifest {
        attributes["Main-Class"] = "org.noblak.MainKt"
    }
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    archiveClassifier = "uber"

    from(sourceSets.main.get().output)

    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}

kotlin {
    jvmToolchain(21)
}