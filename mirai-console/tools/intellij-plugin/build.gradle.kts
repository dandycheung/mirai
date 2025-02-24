/*
 * Copyright 2019-2021 Mamoe Technologies and contributors.
 *
 *  此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 *  Use of this source code is governed by the GNU AGPLv3 license that can be found through the following link.
 *
 *  https://github.com/mamoe/mirai/blob/master/LICENSE
 */

@file:Suppress("UnusedImport")

plugins {
    kotlin("jvm")
    id("java")
    `maven-publish`

    id("org.jetbrains.intellij") version Versions.intellijGradlePlugin
}

repositories {
    maven("https://maven.aliyun.com/repository/public")
}

version = Versions.consoleIdea
description = "IntelliJ plugin for Mirai Console"

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    version.set(Versions.intellij)
    downloadSources.set(true)
    updateSinceUntilBuild.set(false)

    sandboxDir.set(projectDir.resolve("run/idea-sandbox").absolutePath)

    plugins.set(
        listOf(
//            "org.jetbrains.kotlin:${Versions.kotlinIntellijPlugin}", // @eap
            "java",
            "gradle",
            "org.jetbrains.kotlin"
        )
    )
}

afterEvaluate {
    java {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
tasks.getByName("publishPlugin", org.jetbrains.intellij.tasks.PublishPluginTask::class) {
    val pluginKey = project.findProperty("jetbrains.hub.key")?.toString()
    if (pluginKey != null) {
        logger.info("Found jetbrains.hub.key")
        token.set(pluginKey)
    } else {
        logger.info("jetbrains.hub.key not found")
    }
}

fun File.resolveMkdir(relative: String): File {
    return this.resolve(relative).apply { mkdirs() }
}

kotlin.target.compilations.all {
    kotlinOptions {
        jvmTarget = "11"
    }
}

tasks.withType<org.jetbrains.intellij.tasks.PatchPluginXmlTask> {
    sinceBuild.set("201.*")
    untilBuild.set("215.*")
    pluginDescription.set(
        """
        Plugin development support for <a href='https://github.com/mamoe/mirai'>Mirai Console</a>
        
        <h3>Features</h3>
        <ul>
            <li>Inspections for plugin properties.</li>
            <li>Inspections for illegal calls.</li>
            <li>Intentions for resolving serialization problems.</li>
        </ul>
    """.trimIndent()
    )
    changeNotes.set(
        """
        See <a href="https://github.com/mamoe/mirai/releases">https://github.com/mamoe/mirai/releases</a>
    """.trimIndent()
    )
}

dependencies {
    api(`jetbrains-annotations`)
    api(`kotlinx-coroutines-jdk8`)
    api(`kotlinx-coroutines-swing`)

    api(project(":mirai-console-compiler-common"))

    implementation(`kotlin-stdlib-jdk8`)
    implementation(`kotlin-reflect`)
}
