# GankPee

自己写 kotlin 其实已经有一段时间，但是一直没用上协程 coroutine，之前是说不太稳定，但现在 kotlin 都已经去到 1.4 了，协程也已经发布稳定版本了，就决定用协程 coroutine 来处理一波网络请求，康康何如。

项目采用 MVVM 的基础框架来实现， 框架主要由以下几个组件实现：
- [Kotlin、协程](https://developer.android.google.cn/kotlin?hl=zh-cn)
- [ViewModel](https://developer.android.google.cn/topic/libraries/architecture/viewmodel?hl=zh-cn)
- [LiveData](https://developer.android.google.cn/topic/libraries/architecture/livedata?hl=zh-cn)
- [Retrofit](https://github.com/square/retrofit)

当然项目中还用了一些“新”的控件，比如 AndroidX 下的 ViewPager2、TabLayout 等，这些控件和之前 support 包下的用法还是有些许不同的，陌生的朋友不妨可以康康，说不定有些帮助😉。



# MVVM
关于 MVVM 大家应该都有所了解了，这里附上一张网上的图

![](https://camo.githubusercontent.com/2b3ff9b3a5f99c5480b612aa8f4f678dc696987a/68747470733a2f2f757365722d676f6c642d63646e2e786974752e696f2f323031392f342f31352f313661323130313664663963373663353f773d39363026683d37323026663d7765627026733d3135333832)

View层主要就是负责界面的显示和一些通用的视图交互逻辑，对应来看就是我们的 Activity、Fragment。它会监听 VM 层的回调，当数据有更新的时候，直接刷新视图即可，我们并不需要过多的理会，仅需处理好数据如何在界面上显示即可。

ViewModel层主要就是负责业务逻辑和数据的处理，在这部分，我们通常会有 ViewModel➕Livedata 一起来完成。

Model层主要就是数据的获取，可以说对应上图的 Repository，仓库，也就是取数据的地方，当然，你可以从网路获取，也可以从数据库获取，数据的缓存可以在这一层处理。在这一层，仅仅处理好数据怎样的获取的就好，其他的不必过多理会。

从这里看，MVVM 的每一层都各司其职，都有自己专注做的事情，View 层仅持有 VM 层，VM 层持有 Model 层用于数据处理，不会有各种相互交错的持有。

就拿热门 Tab 来举例这一流程是怎么跑通的好了。

![看不到就是挂了⚡️](https://github.com/WGwangguan/GankPee/blob/master/app/src/main/java/screenshot/hot.png)

# Api 接口
```
    /**
     * 加载热门
     */
    @GET("hot/{type}/category/{category}/count/{count}")
    suspend fun loadHot(
        @Path("type") type: String,
        @Path("category") category: String,
        @Path("count") count: Int
    ): GankResponse<List<GankBean>>
```
因为我们用 kotlin 的协程替代了原先的 rxjava，所以这里我们需要在方法前加上“suspend”标识它是支持协程的挂起函数。本来这里异步方法是需要写await、Deferred的，但是把 Retrofit 升级到 2.6.0 以上就原生支持啦，不需要写这些东西了，可以直接返回具体的类型。

# Repository
```
    suspend fun loadHot(
        category: String,
        type: String = Constants.HOT_TYPE_VIEWS,
        count: Int = Constants.DEFAULT_COUNT
    ) = call { MyRetrofitClient.service.loadHot(type, category, count) }
```
这里也是一个可挂起函数，稍后我们会在 viewmodel 中调用。

# viewmodel
```
class GankViewModel : BaseViewModel() {
    private val repository by lazy { GankRepository() }

    val hotData by lazy { MutableLiveData<List<GankBean>>() }
    val subCategory by lazy { MutableLiveData<List<SubCategory>>() }
    val gankPage by lazy { MutableLiveData<GankPage<List<GankBean>>>() }

    fun loadHot(category: String, showLoading: Boolean = true) {
        launchOnUI {
            reqHelper.requestSuspend(showLoading = showLoading) { repository.loadHot(category) }
                .checkSuccess {
                    hotData.value = it
                }
        }
    }
  ... 

```
在这里，我们真正触发请求，当请求结束我们得到结果，这里就可以将结果赋值给相应的 liveData，在 view 层就可以被通知到最新的数据，然后更新 UI 即可。

# view
```
        vm.hotData.observe(this, Observer {
            articleAdapter.setNewInstance(it.toMutableList())
            refresh.finishRefresh()
        })
```
在 view 层，我们对 hotData 这个 liveData 添加了监听，当数据在 viewmodel 层更新的时候，我们这里会获得回调，直接更新 UI 即可，很方便。

从请求到获取结果到 UI显示大致就是这么一个流程，放眼望去，是不是很流畅，犹如行云流水🤣🤣🤣


