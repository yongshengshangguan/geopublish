# geopublish

一个基于 Java 11 的命令行工具，支持批量扫描本地 `.shp` 或 `.tif` 文件，并通过 GeoServer REST API 自动发布为 WMS 服务。

---

##  项目特点

- 支持 Shapefile（.shp + .dbf + .shx + .prj）自动打包上传
- 支持 GeoTIFF（.tif）直接上传并发布
- 支持递归扫描目录，自动识别图层名
- 自动创建数据存储并绑定至指定工作区
- 支持覆盖已有图层
- 使用简单，运行即发布

---

## 项目结构

```bash
geoserver-publisher/
├── src/
│   └── main/
│       ├── java/com/example/geopublish/
│       │   ├── GeoPublisherApplication.java       # 启动主类
│       │   ├── client/GeoServerRestClient.java    # 封装 REST API 调用
│       │   ├── service/ShpPublisher.java          # Shapefile 发布
│       │   ├── service/TifPublisher.java          # GeoTIFF 发布
│       │   └── util/FileScanner.java              # 目录文件扫描器
│       └── resources/application.properties       # 可选配置文件
├── pom.xml
