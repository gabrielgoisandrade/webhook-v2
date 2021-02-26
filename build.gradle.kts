import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.4.2"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.4.21"
    kotlin("plugin.spring") version "1.4.21"
    kotlin("plugin.jpa") version "1.4.21"
    jacoco
}

description = "Webhook integration"
group = "com.gga"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_15

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

extra["springBootAdminVersion"] = "2.3.1"

dependencies {
    /* Model mapper */
    implementation("org.modelmapper:modelmapper:2.3.9")

    // springDoc
    implementation("org.springdoc:springdoc-openapi-ui:1.5.4")
    implementation("org.junit.jupiter:junit-jupiter:5.4.2")

    // mockito
    testImplementation("org.mockito:mockito-core:3.7.7")

    // assertJ
    testImplementation("org.assertj:assertj-core:3.11.1")

    // actuator
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    // cache
    implementation("org.springframework.boot:spring-boot-starter-cache")

    // jpa
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // HATEOAS
    implementation("org.springframework.boot:spring-boot-starter-hateoas")

    // spring admin
    implementation("de.codecentric:spring-boot-admin-starter-client")

    // migration
    implementation("org.flywaydb:flyway-core")

    // H2
    runtimeOnly("com.h2database:h2")

    // POSTGRES
    runtimeOnly("org.postgresql:postgresql")

    // devtools
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    // default
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

dependencyManagement {
    imports {
        mavenBom("de.codecentric:spring-boot-admin-dependencies:${property("springBootAdminVersion")}")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "15"
        this.useIR = true //Kotlin JVM
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.processResources {
    expand(project.properties)
}

// JACOCO CONFIGURATION
tasks.jacocoTestReport {
    reports {
        xml.isEnabled = true
        xml.destination = file("${buildDir}/reports/jacoco/report.xml")
        csv.isEnabled = false
        html.isEnabled = false
    }
}