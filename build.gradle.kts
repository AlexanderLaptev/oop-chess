plugins {
    kotlin("jvm") version "1.9.21"
    application
}

group = "ru.trfx.games.chess"
version = "1.0.0"

application {
    mainClass.set("ru.trfx.games.chess.MainKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("net.coobird:thumbnailator:0.4.20")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.8.10")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}
