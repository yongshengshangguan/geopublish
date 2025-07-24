# geopublish JAR 工具包

这是一个可以被其他 Java 项目引用的工具类库，用于批量发布本地 `.shp` 或 `.tif` 文件到 GeoServer，并形成 WMS 服务。

---

## 环境要求
Java 11+

Maven 3.6+

GeoServer（需开启 REST 接口）

Spring Boot 2.7+


##  项目特点

- 支持 Shapefile（.shp + .dbf + .shx + .prj）自动打包上传
- 支持 GeoTIFF（.tif）直接上传并发布
- 支持递归扫描目录，自动识别图层名
- 自动创建数据存储并绑定至指定工作区
- 支持覆盖已有图层
- 使用简单，运行即发布

---

## 项目结构

geoserver-publisher/
├── com.sgtools.geopublish/
│   ├── GeoPublisher.java            # 对外暴露的主类
│   ├── client/GeoServerRestClient.java  # REST请求封装
│   ├── service/ShpPublisher.java    # 发布 SHP 的实现
│   ├── service/TifPublisher.java    # 发布 TIF 的实现
│   └── util/FileScanner.java        # 文件扫描工具类
└── resources/
└──（无配置文件，依赖调用方传参）

## 使用方法
mvn clean install

然后在其他项目中添加依赖：
<dependency>
<groupId>com.sgtools</groupId>
<artifactId>geoserver-publisher</artifactId>
<version>1.0.0</version>
</dependency>

### 使用示例

GeoPublisher publisher = new GeoPublisher("http://localhost:8080/geoserver", "admin", "geoserver");

// 发布所有 Shapefile 文件
publisher.publishShapefiles("my_workspace", "D:/data/shapefiles", true);

// 发布所有 GeoTIFF 文件
publisher.publishTiffFiles("my_workspace", "D:/data/tiff", false);
