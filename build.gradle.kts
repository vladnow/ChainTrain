import java.security.MessageDigest
plugins { java }
group = "dev.chaintrain"
version = "0.3.0-paper26.3-beta"
repositories { mavenCentral(); maven("https://repo.papermc.io/repository/maven-public/") }
val paperServerDir = file(providers.gradleProperty("paperServerDir").getOrElse("../.tools/paper263-smoke"))
dependencies {
    compileOnly("io.papermc.paper:paper-api:26.3.build.159-beta")
    // Exact Paper 26.3 internals: native minecart interpolation and physics adapter.
    compileOnly(files(paperServerDir.resolve("versions/26.3/paper-26.3.jar")))
    compileOnly(fileTree(paperServerDir.resolve("libraries")) { include("**/*.jar") })
    testImplementation(platform("org.junit:junit-bom:5.13.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.joml:joml:1.10.8") { isTransitive = false }
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
java { toolchain.languageVersion.set(JavaLanguageVersion.of(25)); withSourcesJar() }
tasks.withType<JavaCompile>().configureEach { options.encoding = "UTF-8" }
tasks.processResources { filteringCharset = "UTF-8"; filesMatching("plugin.yml") { expand("version" to project.version) } }
tasks.test { useJUnitPlatform() }
tasks.withType<AbstractArchiveTask>().configureEach { isPreserveFileTimestamps = false; isReproducibleFileOrder = true }
tasks.jar { manifest { attributes("paperweight-mappings-namespace" to "mojang") }; from("LICENSE.txt") { into("META-INF") } }
val sourceZip = tasks.register<Zip>("sourceZip") {
    archiveFileName.set("ChainTrain-${project.version}-source.zip")
    destinationDirectory.set(layout.buildDirectory.dir("distributions"))
    from(projectDir) { include("src/**", "verification/*.java", "verification/*.yml", "verification/*.ps1", "gradle/**", "gradlew*", "*.gradle.kts", "*.md", "LICENSE.txt", "LICENSES/**", "docs/**", ".github/**", "build.ps1", ".gitignore") }
}
val downloadZip = tasks.register<Zip>("downloadZip") {
    dependsOn(tasks.jar, sourceZip)
    archiveFileName.set("ChainTrain-${project.version}-download.zip")
    destinationDirectory.set(layout.buildDirectory.dir("distributions"))
    from(tasks.jar); from(sourceZip) { into("source") }; from("README.md", "TESTING.md", "CHANGELOG.md", "LICENSE.txt", "NOTICE.md"); from("LICENSES") { into("LICENSES") }
}
tasks.register("releaseBundle") {
    dependsOn(tasks.build, sourceZip, downloadZip)
    doLast {
        val destination = projectDir.resolve("dist").apply { mkdirs() }
        val artifacts = listOf(tasks.jar.get().archiveFile.get().asFile, sourceZip.get().archiveFile.get().asFile, downloadZip.get().archiveFile.get().asFile)
        destination.resolve("SHA256SUMS.txt").writeText(artifacts.joinToString("\n", postfix="\n") { artifact ->
            artifact.copyTo(destination.resolve(artifact.name), overwrite=true)
            MessageDigest.getInstance("SHA-256").digest(artifact.readBytes()).joinToString("") { "%02x".format(it.toInt() and 255) } + "  " + artifact.name
        }, Charsets.UTF_8)
    }
}
