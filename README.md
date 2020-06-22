# base-framework
Android基于Kotlin设计的基础框架，支持MVP基础架构，以及组件化中的公共模块封装，拿来即用。

![reuse](http://119.23.190.71/static/img/130.png "reuse")

# android基础架构设计：
开发语言：Kotlin
架构形式：组件化
开发模式：MVP

## 基础模块设计：
模块名称：base
模块包名：com.lvyerose.framework.base
Gradle地址：com.lvyerose.base-framework:1.0.0
模块内容：
### BaseActivity
    基础接口定义
    基础逻辑封装
### BaseFragment
    基础接口定义
    基础逻辑封装
### BaseMvpActivity
    基础 IView、IPresenter、IModel 模块封装
    基本逻辑 BaseView、BasePresenter、BaseModel 模块封装
    实例自动化组装以及订阅生命周期绑定
    多Presenter模式支持（未支持，无意义）
    多Model模式支持
### BaseMvpFragment

### BaseRecyclerAdapter
    基本逻辑封装
    支持多数据模型
    支持多视图模型
    支持添加头部布局
    支持添加尾部布局


## 工具模块设计：
模块名称：tools
模块包名：com.lvyerose.framework.tools
Gradle地址：com.lvyerose.base-tools:0.0.1
模块内容：
### 通用工具
        字符串工具
        数组、集合处理工具
        日期处理工具
### Android工具
        基本信息工具
        屏幕相关信息工具
        文件存储工具 （包含图片存储）
        图片处理工具（包含 图片无损压缩、图片自定义变换、提供矩阵操作等）
        权限工具
        文字处理工具
### 其他
        WebView截图功能

## 计划收录功能：

## 网络请求模块

## 自定义控件模块


