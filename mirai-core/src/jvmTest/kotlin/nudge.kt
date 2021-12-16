/*
 * Copyright 2019-2021 Mamoe Technologies and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license that can be found through the following link.
 *
 * https://github.com/mamoe/mirai/blob/dev/LICENSE
 */

package net.mamoe.mirai.internal

import net.mamoe.mirai.BotFactory
import net.mamoe.mirai.message.action.Nudge.Companion.sendNudge
import net.mamoe.mirai.utils.BotConfiguration

suspend fun main() {
    val bot = BotFactory.newBot(1040400290, "asdHim188moe666") {
        enableContactCache()
        fileBasedDeviceInfo()
        protocol = BotConfiguration.MiraiProtocol.ANDROID_WATCH
    }
    bot.asQQAndroidBot()
    bot.login()

    repeat(10) {
        bot.getFriendOrFail(2978594313).run {
            sendNudge(nudge())
        }
    }

    bot.close()
}