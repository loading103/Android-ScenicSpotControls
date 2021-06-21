package com.daqsoft.module_work.viewmodel

import android.app.Application
import android.graphics.Color
import android.view.View
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.alibaba.android.arouter.launcher.ARouter
import com.daqsoft.library_base.router.ARouterPath
import com.daqsoft.module_work.BR
import com.daqsoft.module_work.R
import com.daqsoft.module_work.repository.WorkRepository
import com.daqsoft.module_work.repository.pojo.vo.LeavaListBean
import com.daqsoft.library_common.bean.LeaveType
import com.daqsoft.module_work.viewmodel.itemviewmodel.LeaveItemViewModel
import com.daqsoft.mvvmfoundation.base.ItemViewModel
import com.daqsoft.mvvmfoundation.binding.command.BindingAction
import com.daqsoft.mvvmfoundation.binding.command.BindingCommand
import com.daqsoft.mvvmfoundation.toolbar.ToolbarViewModel
import me.tatarka.bindingcollectionadapter2.ItemBinding

class LeaveListViewModel :  ToolbarViewModel<WorkRepository> {

    @ViewModelInject
    constructor(application: Application,workBenchRepository: WorkRepository):super(application, workBenchRepository)


    /**
     * 刷新完成
     */
    val refreshCompleteLiveData = MutableLiveData<Boolean>()

    val ChooseLiveData = MutableLiveData<String>()

    val OrderLiveData = MutableLiveData<String>()

    val observableList: ObservableList<ItemViewModel<*>> = ObservableArrayList()

    /**
     * 给RecyclerView添加ItemBinding
     */
    var itemBinding: ItemBinding<ItemViewModel<*>> = ItemBinding.of(
        BR.viewModel,
        R.layout.recycleview_leave_item
    )
    override fun onCreate() {
        initToolbar()
        getdatas()
    }


    val chooseTypesLiveData = MutableLiveData<MutableList<LeaveType>>()

    /**
     * 审核状态选项（todo  等接口从后台获取数据）
     */
    val chooseStates: MutableList<LeaveType> = mutableListOf(
        LeaveType("1", "全部"),
        LeaveType("2", "已通过"),
        LeaveType("3", "待审核"),
        LeaveType("4", "被驳回"),
        LeaveType("5", "已撤销"),
    )

    /**
     * 请假类型选项（todo  等接口从后台获取数据）
     */
    val chooseTypes: MutableList<LeaveType> = mutableListOf(
        LeaveType("1", "全部"),
        LeaveType("2", "事假"),
        LeaveType("3", "病假"),
        LeaveType("4", "产假"),
        LeaveType("5", "年假"),
        LeaveType("6", "调休假"),
        )

    /**
     * 排序方式选项（todo  等接口从后台获取数据）
     */
    val OrderStates: MutableList<LeaveType> = mutableListOf(
        LeaveType("1", "正序排序"),
        LeaveType("2", "倒序排序"),
    )
    /**
     * 排序属性选项（todo  等接口从后台获取数据）
     */
    val OrderTypes: MutableList<LeaveType> = mutableListOf(
        LeaveType("1", "开始时间"),
        LeaveType("2", "结束时间"),
        LeaveType("3", "提交时间"),
        LeaveType("4", "审核时间"),
    )

    private fun getdatas() {
        val data = arrayListOf<ItemViewModel<*>>()
        observableList.add(LeaveItemViewModel(this, LeavaListBean("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic1.zhimg.com%2F50%2Fv2-e73ebe5fb7fbae39d69ed94dcc82f145_hd.jpg&refer=http%3A%2F%2Fpic1.zhimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1622963473&t=252019ae1d5748c367a2ba020826e300"
            ,"张三1","哈哈哈哈哈哈1","1","1","1","2019-04-01 17:27")))
        observableList.add(LeaveItemViewModel(this, LeavaListBean("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic1.zhimg.com%2F50%2Fv2-e73ebe5fb7fbae39d69ed94dcc82f145_hd.jpg&refer=http%3A%2F%2Fpic1.zhimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1622963473&t=252019ae1d5748c367a2ba020826e300"
            ,"张三2","哈哈哈哈哈哈2","2","1","3","2019-04-01 17:27")))
        observableList.add(LeaveItemViewModel(this, LeavaListBean("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic1.zhimg.com%2F50%2Fv2-e73ebe5fb7fbae39d69ed94dcc82f145_hd.jpg&refer=http%3A%2F%2Fpic1.zhimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1622963473&t=252019ae1d5748c367a2ba020826e300"
            ,"张三3","哈哈哈哈哈哈3","3","1","21","2019-04-01 17:27")))
        observableList.add(LeaveItemViewModel(this, LeavaListBean("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic1.zhimg.com%2F50%2Fv2-e73ebe5fb7fbae39d69ed94dcc82f145_hd.jpg&refer=http%3A%2F%2Fpic1.zhimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1622963473&t=252019ae1d5748c367a2ba020826e300"
            ,"张三4","哈哈哈哈哈哈4","1","1","1","2019-04-01 17:27")))
        observableList.add(LeaveItemViewModel(this, LeavaListBean("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic1.zhimg.com%2F50%2Fv2-e73ebe5fb7fbae39d69ed94dcc82f145_hd.jpg&refer=http%3A%2F%2Fpic1.zhimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1622963473&t=252019ae1d5748c367a2ba020826e300"
            ,"张三5","哈哈哈哈哈哈5","4","1","3","2019-04-01 17:27")))
        observableList.add(LeaveItemViewModel(this, LeavaListBean("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic1.zhimg.com%2F50%2Fv2-e73ebe5fb7fbae39d69ed94dcc82f145_hd.jpg&refer=http%3A%2F%2Fpic1.zhimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1622963473&t=252019ae1d5748c367a2ba020826e300"
            ,"张三5","哈哈哈哈哈哈哈哈哈5哈哈哈哈哈哈5哈哈哈哈哈哈5哈哈哈哈哈哈5哈哈哈哈哈哈5哈哈哈哈哈哈5哈哈哈哈哈哈5哈哈哈哈哈哈5哈哈哈5","4","1","3","2019-04-01 17:27")))
        observableList.add(LeaveItemViewModel(this, LeavaListBean("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic1.zhimg.com%2F50%2Fv2-e73ebe5fb7fbae39d69ed94dcc82f145_hd.jpg&refer=http%3A%2F%2Fpic1.zhimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1622963473&t=252019ae1d5748c367a2ba020826e300"
            ,"张三5","哈哈哈哈哈哈5","4","1","3","2019-04-01 17:27")))

    }

    private fun initToolbar() {
        setBackground(Color.WHITE)
        setTitleText("请假申请")
        setTitleTextColor(R.color.color_333333)
        setRightIcon2Visible(View.INVISIBLE)
        setRightIconVisible(View.VISIBLE)
        setRightIconSrc(R.mipmap.qingjia_xinzeng)
    }

    override fun rightIconOnClick() {
        ARouter.getInstance().build(ARouterPath.Workbench.ADD_LEAVE_APPLY).navigation()
    }



    /**
     * 排序按钮
     */
    var onOrderClick = BindingCommand<Any>(BindingAction {
        OrderLiveData.value=""
    })

    /**
     * 筛选按钮
     */
    var onChooseClick = BindingCommand<Any>(BindingAction {
        ChooseLiveData.value=""
    })
    /**
     * 分页 差分器
     */
//    var diff = createDiff()

    /**
     * 分页 数据监听
     */
//    var pageList = createPagedList()

    /**
     * 分页 数据源
     */
//    var dataSource : DataSource<Int, ItemViewModel<*>>?= null

//    val observableList: ObservableList<ItemViewModel<*>> = ObservableArrayList()

//    override fun createDataSource(): DataSource<Int, ItemViewModel<*>> {
//    }
//    override fun createDataSource(): DataSource<Int, ItemViewModel<*>> {
//        dataSource = object : PageKeyedDataSource<Int, ItemViewModel<*>>(){
//            override fun loadInitial(
//                    params: LoadInitialParams<Int>,
//                    callback: LoadInitialCallback<Int, ItemViewModel<*>>
//            ) {
//
//                addSubscribe(
//                        model.getDailyListRequest()
//                                .compose(RxUtils.exceptionTransformer())
//                                .compose(RxUtils.schedulersTransformer())
//                                .subscribeWith(object : AppDisposableObserver<AppResponse<DialyListBean>>() {
//                                    override fun onSuccess(t: AppResponse<DialyListBean>) {
//                                        refreshCompleteLiveData.value = true
//
//                                        t.data?.let {
//                                            val observableList: ObservableList<ItemViewModel<*>> = ObservableArrayList()
//
//                                            it.forEach{
//                                                var dailyListDate = DailyListDateViewModel(this@LeaveListViewModel, it.date)
//                                                dailyListDate.multiItemType(ConstantGlobal.DAILY_LIST_DATE)
//                                                observableList.add(dailyListDate)
//
//                                                it.record.forEach{
//                                                    var dailyListContent = DailyListContentViewModel(this@LeaveListViewModel, it)
//                                                    dailyListContent.multiItemType(ConstantGlobal.DAILY_LIST_CONTENT)
//                                                    observableList.add(dailyListContent)
//                                                }
//                                            }
//                                            callback.onResult(observableList, ConstantGlobal.INITIAL_PAGE, ConstantGlobal.INITIAL_PAGE+1)
//                                        }
//                                    }
//
//                                    override fun onFail(e: Throwable) {
//                                        super.onFail(e)
//                                        refreshCompleteLiveData.value = false
//                                    }
//
//                                })
//                )
//            }
//
//            override fun loadAfter(
//                    params: LoadParams<Int>,
//                    callback: LoadCallback<Int, ItemViewModel<*>>
//            ) {
//                addSubscribe(
//                        model.getDailyListRequest(
//                                        page = params.key
//                                )
//                                .compose(RxUtils.exceptionTransformer())
//                                .compose(RxUtils.schedulersTransformer())
//                                .subscribeWith(object : AppDisposableObserver<AppResponse<DialyListBean>>() {
//                                    override fun onSuccess(t: AppResponse<DialyListBean>) {
//                                        t.data?.let {
//                                            val observableList: ObservableList<ItemViewModel<*>> = ObservableArrayList()
//
//                                            it.forEach{
//                                                var dailyListDate = DailyListDateViewModel(this@LeaveListViewModel, it.date)
//                                                dailyListDate.multiItemType(ConstantGlobal.DAILY_LIST_DATE)
//                                                observableList.add(dailyListDate)
//
//                                                it.record.forEach{
//                                                    var dailyListContent = DailyListContentViewModel(this@LeaveListViewModel, it)
//                                                    dailyListContent.multiItemType(ConstantGlobal.DAILY_LIST_CONTENT)
//                                                    observableList.add(dailyListContent)
//                                                }
//                                            }
//
//                                            callback.onResult(observableList,params.key+1)
//                                        }
//                                    }
//
//                                })
//                )
//
//            }
//
//            override fun loadBefore(
//                    params: LoadParams<Int>,
//                    callback: LoadCallback<Int, ItemViewModel<*>>
//            ) {
//            }
//        }
//        return dataSource!!
//    }
}

