repositories {
    google()
    mavenCentral()
}

plugins {
    id("org.gradle.kotlin.kotlin-dsl") version "2.3.3"
}

dependencies {
    implementation("com.android.tools.build:gradle:7.1.3")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.7.0")
}

tasks.getByName<JavaCompile>("compileJava").also {
    it.targetCompatibility = "1.8"
}

tasks.getByName<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>("compileKotlin").also {
    it.kotlinOptions.jvmTarget = "1.8"
}
