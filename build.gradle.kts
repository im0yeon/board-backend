plugins {
	java
	id("org.springframework.boot") version "4.1.2-SNAPSHOT"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.imooyoni"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.5")

	implementation("org.flywaydb:flyway-core")
	implementation("org.flywaydb:flyway-mysql")

	developmentOnly("org.springframework.boot:spring-boot-devtools")
	compileOnly("org.projectlombok:lombok")

	runtimeOnly("com.h2database:h2")
	runtimeOnly("com.mysql:mysql-connector-j")

	testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
	testImplementation("org.mybatis.spring.boot:mybatis-spring-boot-starter-test:3.0.5")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
