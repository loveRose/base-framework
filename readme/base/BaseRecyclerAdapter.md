# BaseRecyclerAdapter
![reuse](https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=4259238012,97340610&fm=11&gp=0.jpg "reuse")
**BaseRecyclerAdapter「多布局多数据类型支持」**
*BaseRecyclerAdapter 支持最简单的列表数据加载，同时还支持不同布局加载，不同数据格式加载，头部布局添加，尾部布局添加。详细使用说明请参考使用说明*

* ##### 基础布局支持
* ##### 添加头部和尾部布局
* ##### 多布局类型支持
* ##### 多数据类型支持

## BaseRecyclerAdapter 使用说明
### 基础使用方式
#### adapter定义
```kotlin
var simpleAdapter = RecyclerAdapterFactory.createRecyclerAdapter<String> {
		//添加一个布局
        layout {
            R.layout.item_menu_layout
        }
		//数据绑定
        bindViewData { itemType, holder, data ->
            holder.bindView<TextView>(R.id.tv_menu_name).text = data
        }
		//点击事件绑定
        onItemClick { itemView, position ->
            "$position 位置被点击了".toast()
        }
    }

```
#### adapter设置数据
```kotlin
        simpleAdapter.setData {
            dataList
        }
```
#### RecyclerView绑定
```kotlin
		recyclerView.layoutManager = LinearLayoutManager(this)
		simpleAdapter adapter recyclerView
```
#### adapter新增数据刷新
```kotlin
		//新增单条数据
		simpleAdapter.addData("新增一条数据")
		simpleAdapter.notifyDataSetChanged()

		//指定位置添加一条数据
		simpleAdapter.addData(2, null, "新增一条数据在第2位置")
		simpleAdapter.notifyDataSetChanged()

		//新增一个集合数据
		simpleAdapter.addData(arrayListOf("批量新增1数据", "批量新增2数据"))
		simpleAdapter.notifyDataSetChanged()
```
以上为 Adapter 的基本使用方式
### 添加头部和尾部视图

#### 添加Header视图
```kotlin
        simpleAdapter.headerViewData(R.layout.item_header_layout) {
			//视图数据绑定
            it.item_tv_title.text = "添加的头部"
			//视图点击事件绑定
            it.item_tv_title.setOnClickListener {
                "头部被点击了".toast()
            }
        }
```
#### 添加Footer视图
```kotlin
        simpleAdapter.footerView(R.layout.item_footer_layout) {
			//视图数据绑定
            it.item_tv_title.text = "添加的尾部"
			//视图点击事件绑定
            it.item_tv_title.setOnClickListener {
                "尾部被点击了".toast()
            }
        }
```
接下来是多布局适配器使用方法

