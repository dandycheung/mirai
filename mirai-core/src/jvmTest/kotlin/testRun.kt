/*
 * Copyright 2019-2020 Mamoe Technologies and contributors.
 *
 *  此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 *  Use of this source code is governed by the GNU AGPLv3 license that can be found through the following link.
 *
 *  https://github.com/mamoe/mirai/blob/master/LICENSE
 */

@file:Suppress("INVISIBLE_MEMBER", "INVISIBLE_REFERENCE", "EXPERIMENTAL_API_USAGE")

package net.mamoe.mirai.internal

import io.ktor.client.request.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.mamoe.mirai.Bot
import net.mamoe.mirai.BotFactory
import net.mamoe.mirai.Mirai
import net.mamoe.mirai.alsoLogin
import net.mamoe.mirai.contact.Contact
import net.mamoe.mirai.contact.announcement.Announcement.Companion.publishAnnouncement
import net.mamoe.mirai.contact.file.AbsoluteFileFolder
import net.mamoe.mirai.contact.file.AbsoluteFileFolder.Companion.extension
import net.mamoe.mirai.contact.file.AbsoluteFileFolder.Companion.nameWithoutExtension
import net.mamoe.mirai.contact.getMemberOrFail
import net.mamoe.mirai.contact.nameCardOrNick
import net.mamoe.mirai.event.*
import net.mamoe.mirai.event.events.*
import net.mamoe.mirai.internal.message.ForceAsLongMessage
import net.mamoe.mirai.internal.message.IgnoreLengthCheck
import net.mamoe.mirai.internal.utils._miraiContentToString
import net.mamoe.mirai.message.code.MiraiCode.deserializeMiraiCode
import net.mamoe.mirai.message.data.*
import net.mamoe.mirai.message.data.Image.Key.queryUrl
import net.mamoe.mirai.message.data.MessageChain.Companion.serializeToJsonString
import net.mamoe.mirai.message.data.MessageSource.Key.quote
import net.mamoe.mirai.message.data.MessageSource.Key.recall
import net.mamoe.mirai.message.nextMessage
import net.mamoe.mirai.utils.*
import net.mamoe.mirai.utils.BotConfiguration.MiraiProtocol.ANDROID_PAD
import net.mamoe.mirai.utils.ExternalResource.Companion.sendAsImageTo
import net.mamoe.mirai.utils.ExternalResource.Companion.toExternalResource
import net.mamoe.mirai.utils.ExternalResource.Companion.uploadAsVoice
import net.mamoe.mirai.utils.ProgressionCallback.Companion.asProgressionCallback
import java.io.File
import kotlin.concurrent.thread
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.full.memberProperties
import kotlin.test.assertTrue
import kotlin.time.Duration

val Bot.miraiTiaoSeBan get() = this.getGroupOrFail(798310160)

@Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
suspend fun main() {

    //repeat(30_000 / 100) {
//   File("bigfile.txt").writeText("1".repeat(300_000_000)) // 300mb
    // }

//    exitProcess(1)

//
//
//    val x = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqrb6+7QrODVNz1o2REj5NQc86uqiJJf1yI2wLh0+35hal/CCe1IlnSKpraHC9xaVyf3luNGCHpCTKTCuKbuitoKTYHrKoNl1U23m5ibC3d367mPcmWE8sjWsRfW13A5gcKaUZQ3RP0be23CkoCGzlEcK5/tduz9BOFsfNIs8g8KFtq0XCZIdblZrucKWEJOc6EG1WB0S/Y+Kqqq1t7OFiGencTAkgsw4LLrzlrd8FPWDX9Ba5wbGRwecG9mAtTqOXR2uQNKLIK3GhaWxd+4+eCPLuAX7vdC5c7oaTy5/fQjf1vm43fytEY01yPM3/uu8RoYrp53AlIdTwBDiBMZreQIDAQAB"
//    Base64.getDecoder().decode(x).run {
//        println(size)
//        println(toUHexString())
//    }
//    exitProcess(1)

    val bot = BotFactory.newBot(1994701021, "xnjajsbsh:xunsA") {
        workingDir = File("test/run").apply { mkdirs() }
//    val bot = BotFactory.newBot(1040400290, "asdHim188moe666") {
        enableContactCache()
        fileBasedDeviceInfo()
//        autoReconnectOnForceOffline()
        protocol = ANDROID_PAD
    }
    bot.asQQAndroidBot()

    bot.alsoLogin()

    bot.miraiTiaoSeBan.files.root.files().first().toMessage().let { m ->
        repeat(2) {
            bot.miraiTiaoSeBan.sendMessage(m)
        }
    }

//    println(bot.miraiTiaoSeBan.files.root.resolveFolderById("/e083f93a-cd5b-4618-8b90-97aebee93888"))
//    exitProcess(0)

//    bot.miraiTiaoSeBan.run {
//        files.root.folders().toList()
//            .associateWith { files.root.resolveFolderById(it.id) }
//            .forEach() {
//                assertEquals(it.key, it.value)
//            }
//    }

//    bot.components.toString().soutv("components")

//    bot.getFriend(1040400290)!!.sendMessage("Hello")
//    bot.getGroup(798310160)!!.sendMessage("Hello from new network")

    Runtime.getRuntime().addShutdownHook(thread(start = false) {
        Bot.instances.forEach {
            it.close()
        }
    })

//    bot.miraiTiaoSeBan.run {
//        sendMessage(buildForwardMessage(this) {
//            3279826484 says "我是傻逼"
//        })
//    }

//    delay(5000)

//    bot.asFriend.sendMessage("ss")


    //  bot.network.refreshKeysNow()
    //  bot.network.run {
    //      WtLogin10(bot.client).sendAndExpect()
    //  }

    //  bot.getFriendOrFail(1994701021).sendMessage("hi")

    val desktop = File("C:\\Users\\Him188\\Desktop\\")

    bot.eventChannel.subscribeAlways<NewFriendRequestEvent> {
        if (fromId == 1040400290L) accept()
    }
    bot.eventChannel.filterIsInstance<GroupMessageEvent>().filter { it.sender.id == 1040400290L }
        .subscribeGroupMessages {
            suspend fun Flow<AbsoluteFileFolder>.render(): String =
                toList().joinToString(separator = "\n") { it.absolutePath }

            startsWith("downloadforward") quoteReply {
                kotlin.runCatching { Mirai.downloadForwardMessage(bot, it.trim()) }.getOrElse { it.toString() }
            }

            "f files" reply {
                subject.files.root.files().render()
            }

            "f exists" reply {
                assertTrue { subject.files.root.files().first().exists() }
            }

            "f stream" reply {
//                @OptIn(JavaFriendlyAPI::class)
//                subject.files.root.childrenStream().toList().joinToString(separator = "\n") { it.absolutePath }
            }

            "f folder rename" reply {
                subject.files.root.folders().first().run { renameTo("$name-new") }
            }

            "f delete folder" reply {
                subject.files.root.resolveFolder("myFolder")!!.delete()
            }

            "f move file" reply {
                subject.files.root.resolveFiles("foo-new-new.txt").first()
                    .moveTo(subject.files.root.resolveFolder("myFolder")!!)
            }

            "f delete file" reply {
                subject.files.root.resolveFiles("foo-new-new.txt").first().delete()
            }

            "f create folder" reply {
                subject.files.root.createFolder("myFolder")
            }

            "f renameTo" {
                val target = subject.files.root.files().first()
                subject.sendMessage("target: $target")
                subject.sendMessage(
                    target.renameTo("${target.nameWithoutExtension}-new.${target.extension}").toString()
                )
                subject.sendMessage("target: $target")
                subject.sendMessage("${target.exists()}")
            }

            "f folders" reply {
                subject.files.root.folders().render()
            }

            "f children" reply {
                subject.files.root.children().render()
            }

            startsWith("f resolveById") reply {
                subject.files.root.resolveFileById(message.content.substringAfter("resolveById").trim(), true)
            }

            startsWith("f resolveFiles") reply {
                subject.files.root.resolveFiles(message.content.substringAfter("resolveFiles").trim()).render()
            }

            startsWith("f resolveFolder") reply {
                subject.files.root.resolveFolder(message.content.substringAfter("resolveFolder").trim())?.absolutePath
            }

            startsWith("f resolveAll") reply {
                subject.files.root.resolveAll(message.content.substringAfter("resolveAll").trim()).render()
            }

            "f up" {
                val resource = File("C:\\Users\\Him188\\Downloads\\hello.txt").toExternalResource().toAutoCloseable()
                coroutineScope {
                    val progress = Channel<Long>(Channel.BUFFERED)
                    launch {
                        // 每 3 秒发送一次操作进度百分比
                        progress.receiveAsFlow().sample(Duration.seconds(3)).collect { bytes ->
                            group.sendMessage(
                                "File upload: ${String.format(".2f", bytes.toDouble() / resource.size)}%."
                            )
                        }
                    }
                    group.files.uploadNewFile("/foo.txt", resource, callback = progress.asProgressionCallback(true))
                    group.sendMessage("File uploaded successfully.")
                }
            }
        }

    bot.eventChannel
        .subscribeAlways<GroupMessageSyncEvent> {
            if (message.contentEquals("colorName", ignoreCase = true)) {
                group.botAsMember.nameCard = "Him188"
            }
        }

    bot.eventChannel.subscribeAlways<BotInvitedJoinGroupRequestEvent> {
        if (invitorId == 1040400290L) accept()
    }

    bot.eventChannel.subscribeMessages {

        "longi3" {
            subject.sendMessage(buildMessageChain {
                +Image("{88914B32-B758-74ED-B00D-CAA6D2A5D7F6}.jpg")
                +Image("{88914B32-B758-74ED-B00D-CAA6D2A5D7F6}.jpg")
                +Image("{88914B32-B758-74ED-B00D-CAA6D2A5D7F6}.jpg")
            } + ForceAsLongMessage)
        }

        "fwdi3" {
            subject.sendMessage(buildForwardMessage {
                sender says Image("{88914B32-B758-74ED-B00D-CAA6D2A5D7F6}.jpg")
                sender says Image("{88914B32-B758-74ED-B00D-CAA6D2A5D7F6}.jpg")
                sender says Image("{88914B32-B758-74ED-B00D-CAA6D2A5D7F6}.jpg")
            })
        }

        "breakpoint" {
            sender.nick
        }
    }

    bot.eventChannel.subscribeUserMessages {
        has<LightApp>() {
            subject.sendMessage(message[LightApp]!!.content)
        }


        "邀请" {

            class InvitationBuilder {
                var avatarUrl: String = "https://q.qlogo.cn/headimg_dl?dst_uin=${sender.id}&spec=100"
                var prompt: String = "prompt"
                var appName: String = "appName"
                var name: String = "name"
                var title: String = "title"
                var value: String = "value"

                fun getLightApp() = LightApp(
                    """
                    {"app":"com.tencent.miniapp","desc":"","view":"notification","ver":"1.0.0.11",
                    "prompt":${Json.encodeToString(prompt)},"meta":{"notification":{"appInfo":
                    {"appName":${Json.encodeToString(appName)},"appType":4,"appid":1109659848,
                    "iconUrl":${Json.encodeToString(avatarUrl)}},"button":
                    [{"action":"",
                    "name":${Json.encodeToString(name)}}],"data":[{
                    "title":${Json.encodeToString(title)},
                    "value":${Json.encodeToString(value)}}],"emphasis_keyword":""}}}
                """.trimIndent()
                )

            }

            val builder = InvitationBuilder()

            for (property in builder::class.memberProperties) {
                property as KMutableProperty1<InvitationBuilder, String>
                if (property.name == "avatarUrl") {
                    subject.sendMessage(builder.getLightApp())
                    subject.sendMessage("avatarUrl? 发送整数 QQ 号使用该用户头像, 发送 URL 使用该 URL.")
                    val content = nextMessage().content
                    property.set(
                        builder, when {
                            content.toLongOrNull() != null -> """https://q.qlogo.cn/headimg_dl?dst_uin=${content.toLong()}&spec=100"""
                            else -> {
                                Mirai.Http.get<ByteArray>(content.trim()).toExternalResource().use {
                                    subject.uploadImage(it)
                                }.queryUrl()
                            }
                        }
                    )
                } else {
                    subject.sendMessage(builder.getLightApp())
                    subject.sendMessage("${property.name}?")
                    property.set(builder, nextMessage().content)
                }
            }

            subject.sendMessage(builder.getLightApp())
        }

        "close" {
            bot.close()
        }
        "gc" {
            System.gc()
        }
        "recallyou" {
            subject.sendMessage("ok").recallIn(1000)
        }
        case("来了") {
            val group = subject

            subject.sendMessage("name?")
            var msg = nextMessage()
            val name = msg.content

            subject.sendMessage("picture URL? Send an image to use that.")
            msg = nextMessage()
            val url = when {
                msg.anyIsInstance<Image>() -> {
                    msg.firstIsInstance<Image>().queryUrl()
                }
                else -> refreshimage(msg.content, group)
            } ?: kotlin.run {
                subject.sendMessage("bad image")
                return@case
            }

            subject.sendMessage(createLaile(name, url))
        }
    }
    bot.eventChannel
        .filterIsInstance<GroupMessageEvent>()
        .filter { it.group.id == 798310160L || it.group.id == 622457678L || it.group.id == 772593325L }
        .subscribeGroupMessages {
            "long msg" {
                subject.sendMessage(ForceAsLongMessage + "long")
            }
            "frag msg" {
//                subject.sendMessage(ForceAsFragmentedMessage + "frag")
            }
            (sentBy(1040400290) and startsWith("戳")) l@{
                val member = this.group.getMemberOrFail(message.findIsInstance<At>()?.target ?: return@l)
                val times = message.last().let {
                    if (it is PlainText) it.content.trim().toIntOrNull() else null
                } ?: 3
                repeat(times) {
                    member.nudge().sendTo(subject)
                }
            }
            case("来了") {
                val group = group

                subject.sendMessage("name? Mention someone to use his name")
                var msg = nextMessage()
                val name = when {
                    msg.anyIsInstance<At>() -> {
                        group.getMemberOrFail(msg.firstIsInstance<At>().target).nameCardOrNick
                    }
                    else -> msg.content
                }

                subject.sendMessage("picture URL? Mention someone to use his avatar. Send an image to use that.")
                msg = nextMessage()
                val url = when {
                    msg.anyIsInstance<At>() -> {
                        refreshimage(group.getMemberOrFail(msg.firstIsInstance<At>().target).avatarUrl, group)
                    }
                    msg.anyIsInstance<Image>() -> {
                        msg.firstIsInstance<Image>().queryUrl()
                    }
                    else -> refreshimage(msg.content, group)
                } ?: kotlin.run {
                    subject.sendMessage("bad image")
                    return@case
                }

                subject.sendMessage(createLaile(name, url))
            }

            (endsWith("来了") and has<At>()) {
                val at: At by message
                val member = group.getMemberOrFail(at.target)

                val name = member.nameCardOrNick

                val bytes = retryCatching(3) { Mirai.Http.get<ByteArray>(member.avatarUrl) }.getOrThrow()
                val url = group.uploadImage(bytes.toExternalResource()).queryUrl()


                subject.sendMessage(createLaile(name, url))
//                buildXmlMessage(1) {
//                    templateId = -1
//                    action = "web"
//                    brief = "${member.nameCardOrNick}已介入"
//                    item(layout = 2) {
//                        title("${member.nameCardOrNick}已开始监控聊天")
//                        summary("${member.nameCardOrNick}已开始监控聊天")
//                        picture(member.avatarUrl)
//                    }
//                    source("${member.nameCardOrNick}已介入，请规范聊天")
//                }
            }

            "#1136" reply {
            }
            "ptt" reply {
                File("C:\\Users\\Him188\\Documents\\Tencent Files\\1040400290\\FileRecv\\out (1).silk").toExternalResource()
                    .withUse {
                        group.uploadVoice(this)
                    }
            }
//            "amr" reply {
//                File("""C:\Users\Him188\Desktop\rec\a.amr""").toExternalResource().use {
//                    group.uploadRecord(it)
//                }
//            }
            "silk" reply {
                File("""C:\Users\Him188\Desktop\rec\a.silk""").toExternalResource().use {
                    group.uploadVoice(it)
                }
            }
            "colorname" {
                group.botAsMember.nameCard = "Cinnamon"
            }

//            "annPublish" {
//                group.publishAnnouncement("text") {
//                    sendToNewMember = true
//                    pinned(true)
//                }
//            }
            "annPublishPic" {
                group.publishAnnouncement("text") {
                    image(group.announcements.uploadImage(desktop.resolve("aaaaa.jpg").toExternalResource()))
                }
            }

//            "annList" {
//                group.announcements.asFlow().collect {
//                    subject.sendMessage(it.toString())
//                }
//            }
//
//            "annDeleteAll" {
//                group.announcements.asFlow().collect {
//                    subject.sendMessage(it.delete().toString())
//                }
//            }

            "ttttt" reply {
                buildForwardMessage {
                    sender says Image("{97839EBA-6190-3A4F-9888-8C1590528B3B}.png")
                    sender says "Testing message https://pximg.rainchan.win/img?img_id=78305581&web=true"
                }
            }

            "西内黄色" reply {
                LightApp(
                    """
                    {"app":"com.tencent.miniapp","desc":"","view":"notification","ver":"1.0.0.11",
                    "prompt":"西内黄色邀请","meta":{"notification":{"appInfo":
                    {"appName":${Json.encodeToString(sender.nameCardOrNick)},"appType":4,"appid":1109659848,"iconUrl":"https:\/\/q.qlogo.cn\/headimg_dl?dst_uin=${sender.id}&spec=100"},"button":
                    [{"action":"西内黄色","name":"邀请你和 TA 一起西内黄色"}],"data":[{"title":"正在","value":"西内黄色"}],"emphasis_keyword":""}}}
                """.trimIndent()
                )
            }

            "耶陋" {
                subject.sendMessage(buildForwardMessage {
                    group[1148274576]?.says("我是傻逼")
                })
            }

//            ((contains("傻") and contains("逼")) or contains("sb")) reply {
//                buildForwardMessage {
//                    sender says "我是傻逼"
//                }
//            }

            (startsWith("delete") and sentBy(1040400290)) {
                val pattern = message.content.substringAfter("delete").trim().toRegex()
                val list = group.filesRoot.listFiles().toList().filter { it.name matches pattern }

                subject.sendMessage(message.quote() + list.joinToString() + "\n\nProceed?")
                selectMessagesUnit {
                    "yes" {
                        val event = this
                        event.subject.sendMessage(message.quote() + list.map { it.delete() }.joinToString())
                    }
                    default {
                        group
                        message
                    }
                }
            }
            (startsWith("mkdir") and sentBy(1040400290)) quoteReply {
                val name = message.content.substringAfter("mkdir").trim()
                group.filesRoot.resolve(name).mkdir()
            }
//            (case("bigfile") and sentBy(1040400290)) reply {
//                val file = group.filesRoot.resolve("/bigfile.txt")
//                subject.sendMessage(message.quote() + file.path + "  uploading...0%")
//                File("bigfile.txt").toExternalResource("txt").use { r ->
//                    val channel = Channel<Long>(Channel.BUFFERED)
//                    coroutineScope {
//                        launch {
//                            channel.receiveAsFlow().sample(30_000).collect {
//                                subject.sendMessage(message.quote() + file.path + "  uploading...${(it.toDouble() / r.size * 100).roundToInt()}%")
//                                    .recallIn(3000)
//                            }
//                        }
//                        subject.filesRoot.resolve("/bigfile.txt").run {
////                            this.upload(r, channel.asProgressionCallback())
//                            toMessage()!!.sendTo(subject)
//                        }
//                    }
//                }
//            }
            (startsWith("files") and sentBy(1040400290)) {
                val pattern = message.content.substringAfter("files").trim().toRegex()
                val list = group.filesRoot.listFilesCollection().filter { it.name matches pattern }
                list.forEach {
                    if (it.getInfo()!!.downloadTimes < -1) {
                        subject
                    }
                }
                subject.sendMessage(message.quote() + list.joinToString("\n"))
                selectMessagesUnit {
                    startsWith("rename") {
                        val target = message.content.substringAfter("rename").trim()
                        val event = this
                        event.subject.sendMessage(message.quote() + list.map { it.renameTo(target) }.joinToString())
                    }
                    startsWith("move") {
                        val target = message.content.substringAfter("move").trim()
                        val event = this
                        event.subject.sendMessage(message.quote() + list.map { it.moveTo(it.resolveSibling(target)) }
                            .joinToString())
                    }
                    default {}
                }
            }

//        "files" reply {
//            val files = group.filesRoot.listFiles()?.toList().orEmpty().take(3)
//            files.joinToString("\n")
//        }
//
//        "filed" reply {
//            val firstFile = group.filesRoot.listFiles()?.toList()?.filter { it.isFile() }?.randomOrNull()
//            firstFile?.getDownloadInfo() ?: "null"
//        }
//
//        "fileup" reply {
//            val file = group.filesRoot.resolve("/bigfile300.txt")
//            subject.sendMessage(message.quote() + file.path)
//            File("bigfile.txt").toExternalResource("txt").use { r ->
//                file.write(r, override = true)
//            }
//        }
//
//        "filesall" reply {
//            val files = group.filesRoot.listFiles()?.toList().orEmpty()
//            files.joinToString("\n")
//        }

            "silktest" reply {
                group.uploadVoice(File("C:\\Users\\Him188\\Desktop\\silk.amr").toExternalResource())
            }

            "amrtest" reply {
                group.uploadAudio(File("C:\\Users\\Him188\\Desktop\\amr.amr").toExternalResource())
            }


            startsWith("dice") reply {
                Dice(it.substringAfter("dice", "1").toIntOrNull() ?: Random.nextInt(1..6))
            }
        }

    bot.eventChannel.subscribeMessages {
        "randimg" reply {
            randomImage().saveToBytes().inputStream().withUse { sendAsImageTo(subject) }
        }

        startsWith("ti") reply { File("C:\\Users\\Him188\\Desktop\\${it.substringAfter("ti")}").sendAsImageTo(subject) }

        "hello" reply { "hello" }

        "recallme" reply {
            message.quote() + message.source.recall().toString()
        }

        "sourcetest" {
            fun MessageSource.toString1(): String = buildString {
                appendLine("${this@toString1::class.simpleName}")
                appendLine("ids: ${ids.joinToString(", ")}")
                appendLine("internalIds: ${internalIds.joinToString(", ")}")
                appendLine("from: $fromId")
                appendLine("target: $targetId")
                appendLine("time: $time")
                appendLine("original: ${originalMessage.serializeToJsonString()}")
                appendLine("kind: $kind")
            }

            val receipt = subject.sendMessage(
                message.quote() + message.source.toString1()
            )
            subject.sendMessage("Receipt: \n${receipt.source.toString1()}").recallIn(3000)
            receipt.recallIn(3000)
        }

        "forcelong" {
            subject.sendMessage(
                "test".toPlainText() + ForceAsLongMessage + IgnoreLengthCheck + getRandomString(5000) +
                        Image("{40A7C56B-45C9-23AE-0CFA-23F095B71035}.jpg").repeat(200)
            ).recallIn(3000)
        }

        "longf" l@{
            return@l
            val countMessage = subject.sendMessage(message.quote() + "count?")
            val count = kotlin.runCatching {
                nextMessage().content.toInt()
            }.getOrElse {
                subject.sendMessage(message.quote() + it.toString())
                return@l
            }
            subject.sendMessage(
                buildForwardMessage {
                    repeat(count) {
                        1148274576 named "123" says Image("{40A7C56B-45C9-23AE-0CFA-23F095B71035}.jpg")
                    }
                }// + IgnoreLengthCheck
            ).recallIn(5000)
            countMessage.recall()
        }

//        "forcenolong" {
//
//            subject.sendMessage(
//                "test".toPlainText() + DontAsLongMessage + IgnoreLengthCheck + getRandomString(500) +
//                        Image("{40A7C56B-45C9-23AE-0CFA-23F095B71035}.jpg").repeat(200)
//            ).recallIn(3000)
//        }

        "fragmented" reply {
            "test".toPlainText()
        }

        "music share" {
            subject.sendMessage(
                MusicShare(
                    kind = MusicKind.NeteaseCloudMusic,
                    title = "ファッション",
                    summary = "rinahamu/Yunomi",
                    jumpUrl = "http://music.163.com/song/1338728297/?userid=324076307",
                    pictureUrl = "http://p2.music.126.net/y19E5SadGUmSR8SZxkrNtw==/109951163785855539.jpg",
                    musicUrl = "http://music.163.com/song/media/outer/url?id=1338728297&userid=324076307",
                )
            )
        }

        "forward" reply {
            buildForwardMessage {
                whileSelectMessages {
                    "done" {
                        false
                    }

                    default {
                        add(this)
                        true
                    }
                }
            }
        }

        contains("source test") reply {
            val ret = kotlin.runCatching { message._miraiContentToString() }.onFailure {
                it.printStackTrace()
            }.getOrNull()
            ret
        }

        //    sentBy(1040400290) {
        //        subject.sendMessage(message.serializeToMiraiCode().ifEmpty { "<empty>" })
        //        subject.sendMessage(message.serializeToMiraiCode().deserializeMiraiCode(subject).ifEmpty { PlainText("<empty>") })
        //    }


        /*
          sentBy(1040400290) l@{
              val image: Image by message.orElse { return@l }

              if (subject is User) {
                  image.sendTo(bot.getGroupOrFail(798310160))
              } else {
                  image::class.qualifiedName.soutv("IMAGE")
                  image.sendTo(bot.getFriendOrFail(1040400290))
              }

              subject.sendMessage(buildMessageChain {
                  +"Md5 equals: "
                  +Mirai.Http.get<ByteArray>(image.queryUrl()).md5().contentEquals(image.md5).toString()
              })
          }

          (sentBy(1040400290) and contains("private")) {
              message.sendTo(bot.getFriendOrFail(1040400290))
          }*/

//        (has<FileMessage>() and sentByOperator()) {
//            subject.sendMessage(message).run {
//                quoteReply("test")
//                recallIn(3000)
//            }
//        }

        startsWith("yyout") reply {
            "out.silk".let { desktop.resolve(it) }.apply {
                appendBytes(getRandomByteArray(1))
            }.toExternalResource().withUse { uploadAsVoice(subject.cast()) }
        }

        startsWith("yy") reply {
            val message = message.content
                .substringAfter("yy", "")
                .ifEmpty { "77b18b66-e4d5-4847-8c44-cad582d75ad4.silk" }

            desktop.resolve(message).toExternalResource().withUse { uploadAsVoice(subject.cast()) }
        }
    }

//    val m: At? by messageChainOf().orNull()

    /*
    val channel = bot.eventChannel.asChannel<BotEvent>()
    bot.eventChannel.subscribeAlways(
        GroupMessageEvent::class,
    ) {
        val image = message.findIsInstance<Image>()
        if (image != null) {
            //     subject.sendMessage(image.queryUrl())
        }
    }
    //  bot.groups.find { it.name.contains("调色板") }?.sendMessage("Hello from mirai 2.0-M1")


    myApplication.eventChannel.subscribeGroupMessages {
        "t" {
            reply("ok")
        }

        "im" {
            val image = syncFromEvent { _: GroupMessageEvent -> message.findIsInstance<Image>() }
            image.send()
        }
    }*/

    bot.join()
}

private suspend fun MessageEvent.refreshimage(
    url0: String,
    group: Contact
): String? {
    val bytes = retryCatching(3) { Mirai.Http.get<ByteArray>(url0) }.getOrElse {
        subject.sendMessage(it.toString())
        return null
    }
    val url = group.uploadImage(bytes.toExternalResource()).queryUrl()
    return url
}

private fun createLaile(name: String, url: String) =
    """[mirai:service:1,<?xml version='1.0' encoding='UTF-8' standalone='yes' ?><msg serviceID="1" templateID="-1" action="web" brief="${name}已介入" sourceMsgId="0" url="" flag="0" adverSign="0" multiMsgFlag="0"><item layout="2" advertiser_id="0" aid="0"><picture cover="$url" w="0" h="0" /><title>${name}已加入该会话</title><summary>${name}已开始监控聊天</summary></item><source name="${name}已介入，请规范聊天" icon="" action="" appid="0" /></msg>]"""
        .deserializeMiraiCode()