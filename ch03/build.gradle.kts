import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.21"
    kotlin("plugin.jpa") version "1.4.21"
    kotlin("plugin.allopen") version "1.4.21"
}

group = "jpa-start"
version = "1.0.0"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
}

dependencies {
    // kotlin
    implementation(kotlin("stdlib"))
    // http://hibernate.org/orm
    implementation("org.hibernate:hibernate-core:5.4.27.Final")
    implementation("org.hibernate:hibernate-c3p0:5.4.27.Final")
    // https://mariadb.com/kb/en/mariadb/about-mariadb-connector-j/
    implementation("org.mariadb.jdbc:mariadb-java-client:2.7.1")
    // slf4j
    implementation("org.slf4j:slf4j-api:1.7.30")
    implementation("org.slf4j:slf4j-nop:1.7.30")
    // https://junit.org/junit5/
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.7.0")
    testImplementation("org.junit.jupiter:junit-jupiter-migrationsupport:5.7.0")
    testImplementation("org.dbunit:dbunit:2.7.0")
    testImplementation("org.hamcrest:hamcrest-all:1.3")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
