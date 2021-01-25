import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val springVersion = "5.3.3"
val hibernateVersion = "5.4.27.Final"

plugins {
    kotlin("jvm") version "1.4.21"
    kotlin("plugin.jpa") version "1.4.21"
    kotlin("plugin.spring") version "1.4.21"
    kotlin("kapt") version "1.4.21"
}

group = "jpastart"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation(kotlin("stdlib"))
    // https://github.com/spring-projects/spring-framework
    implementation("org.springframework:spring-orm:$springVersion")
    implementation("org.springframework:spring-context:$springVersion")
    testImplementation("org.springframework:spring-test:$springVersion")
    // https://projects.spring.io/spring-data-jpa
    implementation("org.springframework.data:spring-data-jpa:2.4.3")
    // http://hibernate.org/orm
    implementation("org.hibernate:hibernate-core:$hibernateVersion")
    kapt("org.hibernate:hibernate-jpamodelgen:5.4.27.Final")
    // https://github.com/swaldman/c3p0
    implementation("com.mchange:c3p0:0.9.5.5")
    // https://mariadb.com/kb/en/mariadb/about-mariadb-connector-j/
    implementation("org.mariadb.jdbc:mariadb-java-client:2.7.1")
    // https://log4jdbc.brunorozendo.com/
    implementation("org.bgee.log4jdbc-log4j2:log4jdbc-log4j2-jdbc4.1:1.16")
    // slf4j
    implementation("org.slf4j:slf4j-api:1.7.30")
    // http://logback.qos.ch/
    implementation("ch.qos.logback:logback-classic:1.2.3")
    // https://junit.org/junit5/
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.7.0")
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

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.Embeddable")
    annotation("javax.persistence.MappedSuperclass")
}
