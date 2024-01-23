import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.1"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.21"
    kotlin("plugin.spring") version "1.9.21"
    kotlin("plugin.jpa") version "1.9.21"
    // for kotlin formatting
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0"
}

group = "com.kms"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

subprojects {

    configurations{
        all {
            exclude(group="org.slf4j", module = "slf4j-simple")
            exclude(group="org.springframework.boot", module="spring-boot-starter-logging")
        }
    }

    extra["springCloudVersion"] = "2023.0.0"

    apply {
        plugin("kotlin")
        plugin("kotlin-spring")
        plugin("kotlin-jpa")
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin("org.jlleitschuh.gradle.ktlint")
    }

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
        implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
        implementation("org.slf4j:slf4j-simple:2.0.9")

        //declare logging
        implementation("org.springframework.boot:spring-boot-starter-log4j2")

        testImplementation("io.mockk:mockk:1.13.8")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.9.0")
    }

    dependencyManagement {
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
        }
    }
}

project(":infrastructure") {
    dependencies {
        implementation("it.ozimov:embedded-redis:0.7.3")
        implementation("org.springframework.boot:spring-boot-starter-data-redis")
        implementation("org.springframework.boot:spring-boot-starter-cache")
        runtimeOnly("com.h2database:h2")
        implementation(project(":core"))
    }
}

project(":api") {
    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-validation")
        implementation(project(":core"))
        implementation(project(":infrastructure"))
        implementation(project(":batch"))
    }
}

project(":batch") {
    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-batch")
        implementation(project(":core"))
        implementation(project(":infrastructure"))
    }
}
