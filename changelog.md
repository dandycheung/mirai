## mirai-core

### 新特性

- 为设备信息增加版本号, 新版本以字符串方式存储数据, 使设备信息更易读 (#1295, #1704)
- 支持通过目录 ID 获取子目录: `AbsoluteFolder.resolveById` (#1712)
- 部署 KDoc 到 <https://kdoc.mirai.mamoe.net/> (#1482, #1708)

### 优化和修复

- 默认使用更高版本的 Log4J2 以避免其安全问题 (#1724 by @Nambers)
- 更新实现自定义事件的文档 (#1688 by @MrXiaoM)
- 为因群限制每分钟发言次数导致的发送失败抛出特定异常 (#1220, #1701)
- 为因移出群员操作频率过快导致的错误抛出特定异常 (#1503, #1701)
- 为因发送 `AtAll` 次数达到上限导致的错误抛出特定异常 (#1201, #1701)

## mirai-console

### 新特性

- 使用插件 ID 作为插件数据目录名, 自动完成迁移 (mamoe/mirai-console#276,
  mamoe/mirai-console#418 by @Nambers)
- 新增登出 Bot 的指令: `/logout <id>` (mamoe/mirai-console#417)
- 新增 `AbstractJvmPlugin.save` 和 `AbstractJvmPlugin.savePluginData`
  用于立即保存 `PluginData` (mamoe/mirai-console#408, mamoe/mirai-console#420)