# 框架说明

## 该框架是以谷歌DataBinding+LiveData+ViewModel框架为基础，整合Okhttp+RxJava+Retrofit+Glide等主流模块，加上各种原生控件自定义的BindingAdapter，让事件与数据源轻松绑定的MVVM框架。

## **特点**

- **快速开发**

  只需要关心业务逻辑，不用再去关系网络请求、权限申请、生命周期等问题。

- **维护方便**

  MVVM开发模式，低耦合，逻辑分明。Model层负责将请求的数据交给ViewModel；ViewModel层负责将请求到的数据做业务逻辑处理，最后交给View层去展示，与View一一对应；View层只负责界面绘制刷新，不处理业务逻辑。

- **主流框架**

  [retrofit](https://github.com/square/retrofit)+[okhttp](https://github.com/square/okhttp)+[rxJava](https://github.com/ReactiveX/RxJava)负责网络请求；[gson](https://github.com/google/gson)负责解析json数据；[glide](https://github.com/bumptech/glide)负责加载图片；[rxlifecycle](https://github.com/trello/RxLifecycle)负责管理view的生命周期；[rxbinding](https://github.com/JakeWharton/RxBinding)结合databinding扩展UI事件。

- **数据绑定**

  databinding双向绑定，BindingAdapter扩展原控件的数据绑定

- **基类封装**

  针对MVVM模式打造的BaseActivity、BaseFragment、BaseViewModel，在View层中不再需要定义ViewDataBinding和ViewModel，直接在BaseActivity、BaseFragment上限定泛型即可使用。

- **依赖注入**

  使用[hilt](https://developer.android.google.cn/jetpack/androidx/releases/hilt)提供项目中依赖注入功能 

## 1、准备工作

### 1.1、启用databinding

```
    buildFeatures{
        dataBinding = true
    }
```

### 1.2、依赖Library

```
dependencies {	
    ...
    implementation project(':mvvmfoundation')
}
```

### 1.3、配置config.gradle

将例子程序中的config.gradle放入主项目根目录中，然后在根目录build.gradle的第一行加入：

```
apply from: "config.gradle"
```

**注意：** config.gradle中的

android = [] 是你的开发相关版本配置，可自行修改

dependencies = [] 是依赖第三方库的配置，可以加新库，但不要去修改原有第三方库的版本号，不然可能会编译不过

### 1.4、配置AndroidManifest

添加权限：

```
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```

配置Application：

继承**mvvmfoundation**中的BaseApplication，来初始化你的Application

## 2、快速开始

### 2.1、第一个Activity

以例子中主页面 MainActivity 为例：三个文件**MainActivity**、**MainViewModel**、**activity_main.xml**

##### 2.1.1、关联ViewModel

在activity_main.xml中关联MainViewModel。

```
<layout>
    <data>
        <variable
            name="viewModel"
            type="com.daqsoft.module_main.viewmodel.MainViewModel" />

    </data>
    .....

</layout>
```

variable - type：类的全路径
variable - name：变量名

##### 2.1.2、继承AppBaseActivity

```
@AndroidEntryPoint
@Route(path = ARouterPath.Main.PAGER_MAIN)
class MainActivity  : AppBaseActivity<ActivityMainBinding, MainViewModel>(){
    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_main
    }

    override fun initViewModel(): MainViewModel {
        return viewModels<MainViewModel>().value
    }

}
```

AppBaseActivity是一个抽象类，有两个泛型参数，一个是ViewDataBinding，另一个是BaseViewModel，上面的ActivityMainBinding则是继承的ViewDataBinding作为第一个泛型约束，MainViewModel继承BaseViewModel作为第二个泛型约束。

重写BaseActivity的二个抽象方法

initContentView() 返回界面layout的id
initVariableId() 返回变量的id，这里的BR跟R文件一样，由系统生成，使用BR.xxx找到这个ViewModel的id。

选择性重写initViewModel()方法，返回ViewModel对象

```
    override fun initViewModel(): MainViewModel {
        return viewModels<MainViewModel>().value
    }
```

**注意：** 不重写initViewModel()，默认会创建MainActivity中第二个泛型约束的MainViewModel，如果没有指定第二个泛型，则会创建BaseViewModel

@AndroidEntryPoint  Hilt 注解用于提供 Android 类的依赖（Activity、Fragment、View、Service、BroadcastReceiver）等等

##### 2.1.3、继承BaseViewModel

MainViewModel继承BaseViewModel

```
class MainViewModel : BaseViewModel<MainRepository> {

    @ViewModelInject
    constructor(application: Application,mainRepository:MainRepository):super(application,mainRepository)

}
```

BaseViewModel与BaseActivity通过LiveData来处理常用UI逻辑

@ViewModelInject  Hilt 注解用于提供 ViewModel类的依赖

### 2.2、数据绑定

拥有databinding框架自带的双向绑定，也有扩展

##### 2.2.1、传统绑定

以EditText为例

在ViewModel中定义

```
public ObservableField<String> observableField = new ObservableField<>("");
```

在标签中绑定

```
android:text="@={viewModel.observableField}"
```

这样一来，输入框中输入了什么，observableField.get()的内容就是什么，observableField.set("")设置什么，输入框中就显示什么。 **注意：** @符号后面需要加=号才能达到双向绑定效果；observableField需要是public的，不然viewModel无法找到它。

点击事件绑定：

```
val onclick = object : View.OnClickListener {
    override fun onClick(p0: View?) {
        
    }
}
```

在标签中绑定

```
android:onClick="@{viewModel.loginOnClick}"
```

这样一来，用户的点击事件直接被回调到ViewModel层了，更好的维护了业务逻辑

##### 2.2.2、自定义绑定

以点击事件为例

在ViewModel中定义

```
val onclick = BindingCommand<Unit>(BindingAction {

})
```

在activity_main中定义命名空间

```
xmlns:app="http://schemas.android.com/apk/res-auto"
```

在标签中绑定

```
app:onClickCommand="@{viewModel.onclick}"
```

以图片加载为例

在ViewModel中定义

```
val observableField = ObservableField<String("http://img0.imgtn.bdimg.com/it/u=2183314203,562241301&fm=26&gp=0.jpg")
```

在ImageView标签中

```
app:url="@{viewModel.observableField}
```

这就是BindingAdapter的扩展控件的数据绑定，更多用法请自行百度学习

##### 2.2.4、RecyclerView绑定

RecyclerView也是很常用的一种控件，传统的方式需要针对各种业务要写各种Adapter，项目中依赖[bindingcollectionadapter](https://github.com/evant/binding-collection-adapter)大大简化这种工作量，从此告别setAdapter()

在ViewModel中定义：

```
/**
 * 给RecyclerView添加ObservableList
 */
var observableList: ObservableList<MultiItemViewModel<*>> = ObservableArrayList()
/**
 * 给RecyclerView添加ItemBinding
 */
var itemBinding: ItemBinding<MultiItemViewModel<*>> = ItemBinding.of{ itemBinding, position, item ->
    when (item.itemType as String) {
        ConstantGlobal.ITEM -> itemBinding.set(BR.viewModel, R.layout.recyclerview_message_item)
        ConstantGlobal.TITLE -> itemBinding.set(BR.viewModel, R.layout.recyclerview_message_title)
        else -> itemBinding.set(BR.viewModel, R.layout.recyclerview_message_item)
    }
}
```

ObservableList<>和ItemBinding<>的泛型是Item布局所对应的ItemViewModel

在xml中绑定

```
<androidx.recyclerview.widget.RecyclerView
    android:background="@android:color/transparent"
    android:overScrollMode="never"
    android:scrollbars="none"
    app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager"
    app:itemAnimator="@{null}"
    app:itemBinding="@{viewModel.itemBinding}"
    app:items="@{viewModel.observableList}"
    android:id="@+id/recycler_view"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
</androidx.recyclerview.widget.RecyclerView>
```

更多RecyclerView、ListView、ViewPager等绑定方式，请自行参考[bindingcollectionadapter](https://github.com/evant/binding-collection-adapter)

### 2.3、网络请求

网络请求一直都是一个项目的核心，现在的项目基本都离不开网络，一个好用网络请求框架可以让开发事半功倍。

#### 2.3.1、Retrofit+Okhttp+RxJava

现今，这三个组合基本是网络请求的标配，项目中网络请求也基于Retrofit+Okhttp+RxJava，因为项目中使用到了依赖注入框架hilt，所以通过依赖注入的形式创建单例的RetrofitClient

创建请求接口服务

```
interface MainApiService {

}
```

创建数据服务管理类

```
class MainRepository @Inject constructor(private val mainApiService: MainApiService) : BaseModel(),MainApiService {

}
```

这里只是添加了网络数据，如果有本地room数据请自行添加

创建依赖注入的Module类

```
@InstallIn(ApplicationComponent::class)
@Module
class MainModule {

    @Singleton
    @Provides
    fun provideMainApiService() = RetrofitClient.Builder().build().create(MainApiService::class.java)

    @Singleton
    @Provides
    fun provideMainRepository(mainApiService: MainApiService)  = MainRepository(mainApiService)

}
```

我们在ViewModel中的泛型约束为对应模块的数据仓库Mode，并在构造函数中传入泛型约束类型，这样我们就可以在ViewModel中通过model调用对应的网络数据接口或者本地room数据接口

```
addSubscribe(
    model
        .method()
        .compose (RxUtils.schedulersTransformer())
        .compose (RxUtils.exceptionTransformer())
        .subscribeWith(object : AppDisposableObserver<AppResponse<Any>>() {
            override fun onSuccess(t: AppResponse<Any>) {
            	...
            }
        })
)
```

#### 2.3.2、网络异常处理

网络异常在网络请求中非常常见，比如请求超时、解析错误、资源不存在、服务器内部错误等，在客户端则需要做相应的处理(当然，你可以把一部分异常甩锅给网络，比如当出现code 500时，提示：请求超时，请检查网络连接，此时偷偷将异常信息发送至后台)。

在使用Retrofit请求时，加入组合操作符`.compose(RxUtils.exceptionTransformer())`，当发生网络异常时，回调onError(ResponseThrowable)方法，可以拿到异常的code和message，做相应处理。

> 基础框架中自定义了一个[ExceptionHandle](https://github.com/goldze/MVVMHabit/blob/master/mvvmhabit/src/main/java/me/goldze/mvvmhabit/http/ExceptionHandle.java)，已为你完成了大部分网络异常的判断，也可自行根据项目的具体需求调整逻辑。

**注意：** 这里的网络异常code，并非是与服务端协议约定的code。网络异常可以分为两部分，一部分是协议异常，即出现code = 404、500等，属于HttpException，另一部分为请求异常，即出现：连接超时、解析错误、证书验证失等。而与服务端约定的code规则，它不属于网络异常，它是属于一种业务异常。在请求中可以使用RxJava的filter(过滤器)，也可以自定义BaseSubscriber统一处理网络请求的业务逻辑异常。

## 3、辅助功能

一个完整的快速开发框架，当然也少不了常用的辅助类。基础框架中封装了部分的辅助类，仍需要持续的封装、补充，请不要吝啬你的智慧，为大家提供便捷方便的辅助方法、公共方法离不开你的贡献。

### 3.1、事件总线

事件总线存在的优点想必大家都很清楚了，android自带的广播机制对于组件间的通信而言，使用非常繁琐，通信组件彼此之间的订阅和发布的耦合也比较严重，特别是对于事件的定义，广播机制局限于序列化的类（通过Intent传递），不够灵活。

#### 3.3.1、LiveEventBus

[LiveEventBus](https://github.com/JeremyLiao/LiveEventBus)是一款Android消息总线，基于LiveData，具有生命周期感知能力，支持Sticky，支持AndroidX，支持跨进程，支持跨APP

- 以生命周期感知模式订阅消息

```
LiveEventBus
	.get("some_key", String.class)
	.observe(this, new Observer<String>() {
	    @Override
	    public void onChanged(@Nullable String s) {
	    }
	});
```

- 以Forever模式订阅消息

```
LiveEventBus
	.get("some_key", String.class)
	.observeForever(observer);
```

- 不定义消息直接发送

```
LiveEventBus
	.get("some_key")
	.post(some_value);
```

- 先定义消息，再发送消息

```
public class DemoEvent implements LiveEvent {
    public final String content;

    public DemoEvent(String content) {
        this.content = content;
    }
}
LiveEventBus
        .get(DemoEvent.class)
        .post(new DemoEvent("Hello world"));
```

详细使用文档请参照https://github.com/JeremyLiao/LiveEventBus

### 3.2、6.0权限申请

Google在 Android 6.0 开始引入了权限申请机制，将所有权限分成了正常权限和危险权限。应用的相关功能每次在使用危险权限时需要动态的申请并得到用户的授权才能使用。

使用方法

```
/**
 * 权限请求
 */
fun requestPermission(vararg permissions: String, callback: () -> Unit){
    PermissionX
        .init(this)
        .permissions(permissions.toList())
        .onExplainRequestReason { scope, deniedList ->
            // 解释原因 重新申请
            Timber.e("解释原因 重新申请  ${Gson().toJson(deniedList)}")
        }
        .onForwardToSettings { scope, deniedList ->
            Timber.e("跳转设置开启权限 ${Gson().toJson(deniedList)}")
            // 跳转设置开启权限
            goToSetting()
        }
        .request { allGranted, grantedList, deniedList ->
            // 权限通过
            if (allGranted) {

                callback()
            }
        }
}
```

更多权限申请方式请参考https://github.com/guolindev/PermissionX

### 3.3、其他辅助类

...

以上说明基本能够使用基础框架快速进行项目开发



# 组件化

