/*
 * Copyright 2019-2020 Mamoe Technologies and contributors.
 *
 *  此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 *  Use of this source code is governed by the GNU AGPLv3 license that can be found through the following link.
 *
 *  https://github.com/mamoe/mirai/blob/master/LICENSE
 */

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.*;
import net.mamoe.mirai.utils.BotConfiguration;
import net.mamoe.mirai.utils.RemoteFile;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class jTest {
    public static void main(String[] args) {
        Bot bot = BotFactory.INSTANCE.newBot(1, "", new BotConfiguration() {{
            fileBasedDeviceInfo();
            setDeviceInfo(bot -> null);
        }});

        RemoteFile file = Objects.requireNonNull(bot.getGroup(0)).getFilesRoot();
        file.resolve("*");
        if (Objects.requireNonNull(file.getInfo()).getLength() < -1) {
        }


        Group group = null;
        ForwardMessageBuilder builder = new ForwardMessageBuilder(group);
        builder.add(new ForwardMessage.Node(123456L, System.currentTimeMillis() / 1000, "群员A", message));
        builder.add(123456L, "群员A", MessageUtils.newChain(new PlainText("")));
        builder.setDisplayStrategy(new ForwardMessage.DisplayStrategy() {
            // 实现方法
        });
        builder.build();
    }
}
