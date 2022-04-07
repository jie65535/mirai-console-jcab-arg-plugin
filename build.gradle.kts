plugins {
    val kotlinVersion = "1.6.10"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion

    id("net.mamoe.mirai-console") version "+"
}

group = "top.jie65535"
version = "1.2.0"

repositories {
    maven("https://maven.aliyun.com/repository/public")
    mavenCentral()
}
