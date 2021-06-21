package com.daqsoft.module_work.viewmodel

import android.app.Application
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.hilt.lifecycle.ViewModelInject
import com.daqsoft.library_base.net.AppDisposableObserver
import com.daqsoft.library_base.net.AppResponse
import com.daqsoft.module_work.R
import com.daqsoft.module_work.BR
import com.daqsoft.module_work.repository.WorkRepository
import com.daqsoft.module_work.repository.pojo.vo.MenuInfo
import com.daqsoft.module_work.viewmodel.itemviewmodel.WorkBenchItemLabelViewModel
import com.daqsoft.module_work.viewmodel.itemviewmodel.WorkBenchItemViewModel
import com.daqsoft.mvvmfoundation.base.ItemViewModel
import com.daqsoft.mvvmfoundation.toolbar.ToolbarViewModel
import me.tatarka.bindingcollectionadapter2.ItemBinding

class WorkViewModel : ToolbarViewModel<WorkRepository> {

    @ViewModelInject
    constructor(application: Application,workRepository : WorkRepository):super(application,workRepository)


    /**
     * 给RecyclerView添加ObservableList
     */
    var observableList: ObservableList<ItemViewModel<*>> = ObservableArrayList()
    /**
     * 给RecyclerView添加ItemBinding
     */
    var itemBinding: ItemBinding<ItemViewModel<*>> = ItemBinding.of(BR.viewModel, R.layout.recycleview_workbench_item)



    fun createItem(){
        observableList.clear()

        dailyWork()
        officeApplication()

//        getMenus()
    }

    /**
     * 日常工作
     */
    private fun dailyWork (){
        val data = arrayListOf<ItemViewModel<*>>()
        data.add(WorkBenchItemLabelViewModel(this, MenuInfo("考勤","","",-1,-1)))
        data.add(WorkBenchItemLabelViewModel(this,MenuInfo("请假","","",-1,-1)))
        data.add(WorkBenchItemLabelViewModel(this,MenuInfo("审批管理","","",-1,-1)))
        data.add(WorkBenchItemLabelViewModel(this,MenuInfo("补卡申请","","",-1,-1)))
        data.add(WorkBenchItemLabelViewModel(this,MenuInfo("通讯录","","",-1,-1)))
        observableList.add(WorkBenchItemViewModel(this,"",data))
    }

    /**
     * 办公应用
     */
    private fun officeApplication(){
        val data = arrayListOf<ItemViewModel<*>>()
        data.add(WorkBenchItemLabelViewModel(this,MenuInfo("请假审批","","",-1,-1)))
        data.add(WorkBenchItemLabelViewModel(this,MenuInfo("补卡审批","","",-1,-1)))

        observableList.add(WorkBenchItemViewModel(this,"日常办公",data))
    }



    fun createMenu(title:String,list: List<MenuInfo>){
        val data = arrayListOf<ItemViewModel<*>>()
        list.forEach {
            val  itemViewModel = WorkBenchItemLabelViewModel(this,it)
            data.add(itemViewModel)
        }
        observableList.add(WorkBenchItemViewModel(this,title,data))
    }

    /**
     * 获取菜单
     */
    fun getMenus(){
//        addSubscribe(
//            model
//                .getMenus()
//                .compose(RxUtils.exceptionTransformer())
//                .compose(RxUtils.schedulersTransformer())
//                .subscribeWith(object : AppDisposableObserver<AppResponse<Menu>>() {
//                    override fun onSuccess(t: AppResponse<Menu>) {
//                        t.data?.let {
//
//                            observableList.clear()
//
//                            if (!it.daily.isNullOrEmpty()){
//                                createMenu("工作日常",it.daily)
//                            }
//
//                            if(!it.office.isNullOrEmpty()){
//                                createMenu("办公应用",it.office)
//                            }
//
//
//                        }
//                    }
//                })
    }
}