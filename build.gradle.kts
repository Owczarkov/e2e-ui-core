plugins {
    id("java")
    id("maven-publish")
    id("java-library")
    id("io.freefair.lombok") version "6.5.0.3"
}

val log4jVersion = "2.18.0"

dependencies {
    api("org.seleniumhq.selenium:selenium-java:4.4.0")
    api("io.github.bonigarcia:webdrivermanager:5.2.3")
    api("com.google.guava:guava:31.1-jre")
    implementation("ru.yandex.qatools.ashot:ashot:1.5.4")
    implementation("io.qameta.allure:allure-attachments:2.18.1")
    implementation("org.testng:testng:7.6.1")
    implementation("org.apache.logging.log4j:log4j-api:$log4jVersion")
    implementation("org.apache.logging.log4j:log4j-core:$log4jVersion")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:$log4jVersion")
    implementation("org.apache.commons:commons-collections4:4.4")
    testImplementation("org.assertj:assertj-core:3.23.1")
    testImplementation("io.qameta.allure:allure-testng:2.19.0")
    compileOnly("com.github.spotbugs:spotbugs-annotations:4.7.1")
}

version = "2.1.0"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

tasks.getByName<JavaCompile>("compileJava") {
    options.encoding = "UTF-8"
}

tasks.getByName<Wrapper>("wrapper") {
    gradleVersion = "7.5.1"
}
tasks.getByName<Test>("test") {
    @Suppress("UNCHECKED_CAST")
    systemProperties(System.getProperties().toMap() as Map<String, Any>)

    testLogging.showStandardStreams = true
    useTestNG()
    filter {
        includeTestsMatching("ImplementationTests")
    }
}
tasks.getByName<Jar>("jar") {
    archiveFileName.set("${rootProject.name}${archiveVersion.get()}")
}
