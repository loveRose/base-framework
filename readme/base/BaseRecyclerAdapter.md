# BaseRecyclerAdapter

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
接下来是多布局和多数据类型适配器使用方法

### 多布局多数据类型使用方式
#### adapter定义
```
var completeAdapter: BaseRecyclerAdapter<ItemData> = RecyclerAdapterFactory.createRecyclerAdapter {
}

completeAdapter.layoutMultiple {
			//定义顶部菜单布局项
            layout { R.layout.item_complete_top_mune_layout }
			//定义顶部圆形布局项
            layout { R.layout.item_complete_circle_layout }
			//定义普通数据布局项
            layout { R.layout.item_complete_default_layout }
        }
```
#### adapter视图绑定
```
        completeAdapter.bindViewData { itemType, holder, data ->
            when (itemType) {
                R.layout.item_complete_default_layout -> {
                    //普通数据适配
                    holder.bindView<TextView>(R.id.tv_default_name).text = data.data
                    holder.bindView<ImageView>(R.id.imv_cover).setImageResource(data.imgRes)
                }
            }
        }
```
#### adapter数据添加（基本布局数据添加）
```
completeAdapter.dataAdapter(dataList) {
            //第一波数据全部用通用布局类型
            addData(R.layout.item_complete_default_layout, it)
        }
```
#### adapter 添加拦截器对符合拦截规则的布局类型进行拦截处理
```
//拦截布局类型为 item_complete_top_mune_layout 的数据
completeAdapter.interceptBindViewData(R.layout.item_complete_top_mune_layout) { itemType, holder, data, otherData ->
            //拦截 item_complete_top_mune_layout 布局类型 并在此处进行数据绑定 使用的是otherData数据 而非data原数据
                holder.bindView<RecyclerView>(R.id.item_menu_recycler_view).layoutManager = GridLayoutManager(this, 4)
                menuAdapter.setData {
                    (otherData as MenuData).itemList!!
                }
                menuAdapter adapter holder.bindView(R.id.item_menu_recycler_view)
        }

//拦截布局类型为 item_complete_circle_layout 的数据
completeAdapter.interceptBindViewData(R.layout.item_complete_circle_layout) { itemType, holder, data, otherData ->
            //拦截圆形布局类型 并在此处进行数据绑定 使用的是otherData数据 而非data原数据
            if (otherData is CircleItemData) {
                holder.bindView<TextView>(R.id._banal_1).text = otherData.name1
                holder.bindView<TextView>(R.id._banal_2).text = otherData.name2
                holder.bindView<TextView>(R.id._banal_3).text = otherData.name3
            }
        }
```
#### adapter 添加额外数据和布局类型
```
//添加顶部菜单数据
completeAdapter.addOtherData(0, R.layout.item_complete_top_mune_layout, menuData)
//添加顶部横向布局
completeAdapter.addOtherData(1, R.layout.item_complete_circle_layout, CircleItemData("菜单1", "菜单2", "菜单3"))
completeAdapter.notifyDataSetChanged()
```

#### 基本使用方式都在此，还有其他没有讲明白的欢迎探索。
