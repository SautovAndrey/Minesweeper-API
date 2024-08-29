plugins {
	java
	id("org.springframework.boot") version "3.3.3"
	id("io.spring.dependency-management") version "1.1.6"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
	}
}

repositories {
	mavenCentral()
}

dependencies {
	compileOnly ("org.projectlombok:lombok:1.18.34")
	annotationProcessor  ("org.projectlombok:lombok:1.18.34")
	implementation("org.springframework.boot:spring-boot-starter-web")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
