plugins {
	java
	id("org.springframework.boot") version "4.1.1"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.imooyoni"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

configurations.all {
	exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-log4j2")
	implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:4.1.0")
	implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:2.0.1")

	implementation("org.springframework.boot:spring-boot-flyway")
	implementation("org.flywaydb:flyway-core")
	implementation("org.flywaydb:flyway-mysql")

	developmentOnly("org.springframework.boot:spring-boot-devtools")

	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

	runtimeOnly("com.h2database:h2")
	runtimeOnly("com.mysql:mysql-connector-j")

	testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
	testImplementation("org.mybatis.spring.boot:mybatis-spring-boot-starter-test:4.1.0")
	testCompileOnly("org.projectlombok:lombok")
	testAnnotationProcessor("org.projectlombok:lombok")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
