/*
 * Copyright 2019-2021 Mamoe Technologies and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license that can be found through the following link.
 *
 * https://github.com/mamoe/mirai/blob/dev/LICENSE
 */

package net.mamoe.mirai.internal.network.protocol.data.richstatus

import net.mamoe.mirai.utils.pos
import net.mamoe.mirai.utils.toIntUnsigned
import java.nio.ByteBuffer
import java.nio.ByteOrder

internal class RichStatus(
    @JvmField var actId: Int = 0,
    @JvmField var actionId: Int = 0,
    @JvmField var actionText: String? = null,

    @JvmField var dataId: Int = 0,
    @JvmField var dataText: String? = null,

    @JvmField var feedsId: String? = null,

    @JvmField var fontId: Int = 0,
    @JvmField var fontType: Int = 0,


    @JvmField var key: ByteArray? = null,

    @JvmField var latitude: Int = 0,
    @JvmField var locationPosition: Int = 0,
    @JvmField var locationText: String? = null,
    @JvmField var lontitude: Int = 0,

    // @JvmField var mStickerInfos: MutableList<StickerInfo>? = null,
    // @JvmField var mUins: MutableList<String>? = null,
    @JvmField var plainText: MutableList<String>? = null,

    // public HashMap<Integer, /**/> sigZanInfo,
    @JvmField var signType: Int = 0,
    @JvmField var time: Long = 0,

    // var topics: MutableList<Pair<Integer, String>> = mutableListOf(),
    // var topicsPos: MutableList<Pair<Integer, Integer>> = mutableListOf(),
    @JvmField var tplId: Int = 0,
    @JvmField var tplType: Int = 0,
) {

    fun addPlainText(var1: String) {
        var pts = this.plainText
        if (pts == null) {
            pts = mutableListOf()
            this.plainText = pts
        }
        pts.add(var1)
    }

    companion object {
        @Suppress("UsePropertyAccessSyntax")
        fun parseStatus(rawData: ByteArray?): RichStatus {
            val rsp = RichStatus()

            if (rawData == null || rawData.size <= 2) return rsp

            val byteBuffer = ByteBuffer.wrap(rawData).order(ByteOrder.BIG_ENDIAN)

            var lastPosition = 0
            var lastStringData: String? = null

            while (byteBuffer.remaining() >= 2) {
                val dataType = byteBuffer.get().toIntUnsigned()
                val dataLength = byteBuffer.get().toIntUnsigned()

                if (byteBuffer.remaining() < dataLength) break

                val dataStartPosition = lastPosition + 2

                // Origin: dataType > 0 && dataType < 128
                if (dataType in 1..127) {
                    val dataContent = String(rawData, dataStartPosition, dataLength)
                    lastPosition = dataStartPosition + dataLength
                    byteBuffer.pos = lastPosition

                    when (dataType) {
                        1 -> rsp.actionText = dataContent
                        2 -> rsp.dataText = dataContent
                        4 -> {
                            if (lastStringData != null) {
                                rsp.addPlainText(lastStringData)
                                lastStringData = null
                            }
                            if (rsp.plainText != null) {
                                rsp.locationPosition = rsp.plainText!!.size
                            } else {
                                rsp.locationPosition = 0
                            }
                            rsp.locationText = dataContent
                        }
                        else -> {
                            if (lastStringData == null) {
                                lastStringData = dataContent
                            } else {
                                lastStringData += dataContent
                            }
                        }
                    }
                } else {
                    kotlin.run theSwitch@{
                        when (dataType) {
                            129 -> {
                                if (byteBuffer.remaining() >= 8) {
                                    rsp.actionId = byteBuffer.getInt()
                                    rsp.dataId = byteBuffer.getInt()
                                }
                            }
                            130 -> {
                                if (byteBuffer.remaining() >= 8) {
                                    rsp.lontitude = byteBuffer.getInt()
                                    rsp.latitude = byteBuffer.getInt()
                                }
                            }
                            144 -> rsp.feedsId = String(rawData, dataStartPosition, dataLength)
                            145 -> rsp.tplId = byteBuffer.getInt()
                            146 -> rsp.tplType = byteBuffer.getInt()
                            147 -> rsp.actId = byteBuffer.getInt()
                            148 -> {
                                if (byteBuffer.remaining() >= 4) {
                                    lastPosition = byteBuffer.getInt()
                                    /*
                                    if (var1 > 4) {
                                        var19 = String(var0, var5+4, var1-4)
                                        if (var19.isNotEmpty()) {
                                            var9.topics.add(Pair(var2, var19))
                                        }
                                    }
                                    */
                                }
                            }
                            149 -> {
                                if (byteBuffer.remaining() >= 5) {
                                    lastPosition = dataLength
                                    while (true) {
                                        if (lastPosition < 5) return@theSwitch

                                        val var6 = byteBuffer.getInt()
                                        var var4 = byteBuffer.get().toIntUnsigned()

                                        // var9.topicsPos.add(new Pair(var6, var3));
                                        lastPosition -= 5
                                    }
                                }
                            }
                            161 -> {
                                /*
                                val var11 = ByteArray(dataLength)
                                byteBuffer.get(var11)
                                 */
                                byteBuffer.pos += dataLength
                                // Parse richstatus_sticker$RichStatus_Sticker
                            }
                            162 -> {
                                rsp.fontId = byteBuffer.getInt()
                            }
                            163 -> {
                                rsp.fontType = byteBuffer.getInt()
                            }

                        }
                    }
                    lastPosition = dataStartPosition + dataLength
                    byteBuffer.pos = lastPosition
                }
            }

            if (lastStringData != null) {
                rsp.addPlainText(lastStringData)
            }

            return rsp
        }
    }
}
