plugins {
    id("java")
    `java-library`
}

group = "net.nonswag.tnl"
version = "5.1"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(16))
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("net.nonswag.core:core-api:1.0")
    implementation("org.projectlombok:lombok:1.18.24")
    implementation("com.google.code.findbugs:jsr305:3.0.2")
    implementation("com.destroystokyo.paper:papermc:1.16.5")
    annotationProcessor("org.projectlombok:lombok:1.18.24")
}