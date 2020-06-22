# base-framework
Android基于Kotlin设计的基础框架，支持MVP基础架构，以及组件化中的公共模块封装，拿来即用。

## android基础架构设计：
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
### app-->通用工具
       获取app相关信息
       手机信息采集工具
       网络辅助类
       app版本工具
       剪切板工具
       权限相关工具类
       系统工具类
### debug-->调试工具
       错误信息反馈信息
       日志工具类
       Toast封装
### showview-->视图显示工具
       单位转换辅助类
       打开或关闭软键盘
       屏幕相关辅助类
       SnackBar的工具类
       图片处理工具
       字符串处理工具
       时间处理工具
### storage-->存储工具
       SDCard辅助类
       SharedPreferences封装类

## 计划收录功能：

## 网络请求模块

## 自定义控件模块


