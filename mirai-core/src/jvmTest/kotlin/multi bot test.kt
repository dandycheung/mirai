/*
 * Copyright 2020 Mamoe Technologies and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license that can be found through the following link.
 *
 * https://github.com/mamoe/mirai/blob/master/LICENSE
 */

package net.mamoe.mirai.internal

import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.delay
import net.mamoe.mirai.BotFactory
import net.mamoe.mirai.event.Event
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.internal.utils.soutv
import net.mamoe.mirai.utils.BotConfiguration

suspend fun main() {
    BotFactory.newBot(1040400290, "asdHim188moe666") {
        fileBasedDeviceInfo()
        protocol = BotConfiguration.MiraiProtocol.ANDROID_PAD
    }//.alsoLogin()

    val bot2 = BotFactory.newBot(1994701021, "xnjajsbsh:xunsA") {
        fileBasedDeviceInfo()
        //autoReconnectOnForceOffline()
        protocol = BotConfiguration.MiraiProtocol.ANDROID_PAD
    }
    bot2.login()

    GlobalEventChannel.subscribeAlways<Event> {
        this.soutv("EVENT")
    }
    delay(5000)

    bot2.close()

    delay(100)

    bot2.getGroup(798310160)?.sendMessage("123456")
    awaitCancellation()
}