import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.6.6"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.jlleitschuh.gradle.ktlint") version "10.2.1"
    id("org.asciidoctor.jvm.convert") version "3.3.2"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.5.21"
    id("org.jetbrains.kotlin.plugin.noarg") version "1.5.21"
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.spring") version "1.6.10"
    kotlin("plugin.jpa") version "1.5.21"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

noArg { // “엔티티는 반드시 파라미터가 없는 (no-arg) public 또는 protected 생성자가 있어야 한다.” 자바 리플렉션 관련하여 넣음
    annotation("javax.persistence.Entity")
}

allOpen { // 코틀린은 final이라 상속이 불가능. JPA Lazy Loading을 위해선 프록시 객체를 생성해야하므로 open class 생성해야함.
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("mysql:mysql-connector-java")
    implementation("org.springframework.cloud:spring-cloud-starter-aws:2.0.1.RELEASE")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.restdocs:spring-restdocs-asciidoctor")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    testImplementation("com.h2database:h2")
}

// snippets이 저장되는 위치
val snippetsDir by extra { file("build/generated-snippets") }

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    outputs.dir(snippetsDir) // 테스트 코드 실행시 생기는 snippets를 snippetsDir에 저장하도록 설정
}

tasks.asciidoctor {
    inputs.dir(snippetsDir)
    dependsOn(tasks.test)
    attributes(
        mapOf("snippets" to snippetsDir) // src/docs/asciidoc/index.adoc 에서 사용할 snippets 변수 설정
    )
}

// jar파일 만들기전에 asciidoctor 실행하고,
// asciidoctor로 생성되는 파일(build/docs/asciidoc/index.html)을
// jar파일의 "BOOT-INF/classes/static/docs"경로로 이동
tasks.bootJar {
    dependsOn(tasks.asciidoctor)
    from(tasks.asciidoctor.get().outputDir) {
        into("BOOT-INF/classes/static/docs")
    }
}
