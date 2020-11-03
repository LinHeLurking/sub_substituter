import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion: String by System.getProperties()
    kotlin("jvm") version kotlinVersion

//    kotlin("jvm") version "1.4.10"
    id("org.openjfx.javafxplugin") version "0.0.9"
    application
    java
}

group = "online.ruin_of_future.sub_substituter"
version = "1.0-SNAPSHOT"

repositories {
    maven(url = "https://maven.aliyun.com/repository/central")
    mavenLocal()
    mavenCentral()
}

dependencies {
    val kotlinVersion: String by System.getProperties()
    val tornadoFxVersion: String by System.getProperties()
    implementation(kotlin("stdlib", kotlinVersion))
    implementation(kotlin("reflect", kotlinVersion))
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.0-M1")
    implementation("no.tornado:tornadofx:$tornadoFxVersion")
}

application {
    mainClassName = "online.ruin_of_future.sub_substituter.MainKt"
    applicationDefaultJvmArgs = mutableListOf("-D\"file.encoding=utf-8\"")
}

// compile bytecode to java 11 (default is java 6)
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "11"
}


javafx {
    modules("javafx.controls", "javafx.fxml")
}
