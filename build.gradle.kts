import org.apache.tools.ant.filters.ReplaceTokens

plugins {
    java
    idea
    id("xyz.jpenilla.run-paper") version "3.0.2"
}

group = "nexus.slime"
version = "4.0.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.codemc.io/repository/maven-releases/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
    compileOnly("com.github.retrooper:packetevents-spigot:2.13.0")
    compileOnly("net.luckperms:api:5.5")

    testImplementation(platform("org.junit:junit-bom:5.13.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.release.set(21)
}

tasks.test {
    useJUnitPlatform()
}

tasks.processResources {
    val props = mapOf("version" to version)

    inputs.properties(props)
    filteringCharset = "UTF-8"

    filesMatching("plugin.yml") {
        expand(props)
    }

    filesMatching("config.yml") {
        filter(ReplaceTokens::class, mapOf("tokens" to props))
    }
}

tasks.jar {
    archiveBaseName.set("F3NPerm")
    from("LICENSE") {
        into("META-INF")
        rename { "LICENSE-GPL-3.0-only.txt" }
    }
    from("LICENSES/MIT.txt") {
        into("META-INF")
        rename { "LICENSE-F3NPerm-MIT.txt" }
    }
    from("NOTICE") {
        into("META-INF")
    }
}

tasks.runServer {
    minecraftVersion("1.21.11")
}
