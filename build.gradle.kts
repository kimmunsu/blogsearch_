import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.2.1"
	id("io.spring.dependency-management") version "1.1.4"
	kotlin("jvm") version "1.9.21"
	kotlin("plugin.spring") version "1.9.21"
	kotlin("plugin.jpa") version "1.9.21"
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
	extra["springCloudVersion"] = "2023.0.0"

	apply {
		plugin("kotlin")
		plugin("kotlin-spring")
		plugin("kotlin-jpa")
		plugin("org.springframework.boot")
		plugin("io.spring.dependency-management")
	}

	repositories {
		mavenCentral()
	}

	dependencies {
		implementation("org.springframework.boot:spring-boot-starter-data-jpa")
		implementation("org.springframework.boot:spring-boot-starter-web")
		implementation("org.springframework.boot:spring-boot-starter-batch")
		implementation("org.springframework.boot:spring-boot-starter-validation")
		implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
		implementation("org.jetbrains.kotlin:kotlin-reflect")
		implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
		implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
		runtimeOnly("com.h2database:h2")
		testImplementation("org.springframework.boot:spring-boot-starter-test")
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
		implementation(project(":core"))
	}
}

project(":batch") {
	dependencies {
		implementation(project(":core"))
	}
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
