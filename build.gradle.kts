import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.1.2"
	id("io.spring.dependency-management") version "1.1.0"
	id("com.github.ben-manes.versions") version "0.46.0"
	id("org.flywaydb.flyway") version "9.21.1"

	kotlin("plugin.spring") version "1.8.22"
	kotlin("jvm") version "1.9.10"
	kotlin("plugin.jpa") version "1.8.22"
	kotlin("kapt") version "1.8.21"
}

group = "info.martindupuis"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-web-services")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.flywaydb:flyway-core")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	
	implementation(project(":lib:j-questrade"))
	testImplementation("org.junit.jupiter:junit-jupiter:5.9.3")

	runtimeOnly("org.postgresql:postgresql")

	developmentOnly("org.springframework.boot:spring-boot-devtools")
	
	testImplementation(kotlin("test"))
	testImplementation("org.amshove.kluent:kluent:1.73")
	// To get JUnit errors from kotlin.test, to e.g. enable diff windows in failure messages
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:1.9.10")
	testImplementation("com.h2database:h2")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.mockk:mockk:1.13.5")
	testImplementation("io.projectreactor:reactor-test")

	kapt("org.springframework.boot:spring-boot-configuration-processor")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
