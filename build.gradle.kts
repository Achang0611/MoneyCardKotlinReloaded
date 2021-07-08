import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.net.URI

plugins {
    kotlin("jvm") version "1.4.30"
    id("com.github.johnrengelman.shadow") version "5.0.0"
}

group = "com.vm"
version = "1.1"

repositories {
    mavenCentral()
    maven { url = URI.create("https://hub.spigotmc.org/nexus/content/repositories/snapshots") }
    maven { url = URI.create("https://oss.sonatype.org/content/repositories/snapshots/") }
    maven { url = URI.create("https://jitpack.io") }
}

dependencies {
    testImplementation(kotlin("test-junit"))
    compileOnly("org.spigotmc:spigot-api:1.15.2-R0.1-SNAPSHOT")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
    compileOnly("ch.qos.logback:logback-core:1.2.3")
    compileOnly("ch.qos.logback:logback-classic:1.2.3")
    implementation(kotlin("stdlib-jdk8"))
}

tasks.test {
    useJUnit()
}

tasks.withType<ProcessResources> {
    filesMatching("plugin.yml") {
        expand(project.properties)
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        useIR = true
    }
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Jar> {
    archiveVersion.set("")
    archiveBaseName.set("MoneyCard")
}

//tasks.withType<ShadowJar> {
//    archiveClassifier.set("")
//}
//
//val build = (tasks["build"] as Task).apply {
//    arrayOf(
//        tasks["shadowJar"]
//    ).forEach { dependsOn(it) }
//}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}