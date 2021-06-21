package com.daqsoft.module_work.activity

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.daqsoft.library_base.base.AppBaseActivity
import com.daqsoft.library_base.router.ARouterPath
import com.daqsoft.module_work.R
import com.daqsoft.module_work.BR
import com.daqsoft.module_work.databinding.ActivityLeaveListBinding
import com.daqsoft.library_common.bean.LeaveType
import com.daqsoft.library_common.xpopup.LeaveChoosePopup
import com.daqsoft.module_work.viewmodel.LeaveListViewModel
import com.daqsoft.mvvmfoundation.utils.dp
import com.lxj.xpopup.XPopup
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/**
 * 请假列表界面
 */
@AndroidEntryPoint
@Route(path = ARouterPath.Workbench.PAGER_LEAVE_LIST)
class LeaveListActivity : AppBaseActivity<ActivityLeaveListBinding, LeaveListViewModel>() {

    val projectTypePopup : LeaveChoosePopup by lazy {
        LeaveChoosePopup(
            this
        )
    }

    val projectOrderPopup : LeaveChoosePopup by lazy {
        LeaveChoosePopup(
            this
        )
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_leave_list
    }

    override fun initViewModel(): LeaveListViewModel? {
        return viewModels<LeaveListViewModel>().value
    }

    override fun initView() {
        super.initView()
        initRefreshLayout()
    }

    override fun initData() {

    }

    override fun initViewObservable() {
        super.initViewObservable()

        viewModel.refreshCompleteLiveData.observe(this, Observer {
            binding.refresh.finishRefresh(it)
        })
        viewModel.ChooseLiveData.observe(this, Observer {
            showChooseTopPopup()
        })

        viewModel.OrderLiveData.observe(this, Observer {
            showOrderTopPopup()
        })
//        viewModel.pageList.observe(this, Observer {
//            binding.recyclerView.executePaging(it, viewModel.diff)
//        })


    }

    /**
     * 初始化下拉刷新
     */
    private fun initRefreshLayout() {
//        binding.refresh.setOnRefreshListener {
//            viewModel.dataSource?.invalidate()
//        }

        binding.recyclerView.apply {
            addItemDecoration(object : RecyclerView.ItemDecoration(){
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    val position = parent.getChildAdapterPosition(view)
                    outRect.bottom = 12.dp
                }
            })
        }
    }


    /**
     * 排序缓存的选项
     */
    var selectOrderTopBean: LeaveType?=null
    var selectOrderButtomBean: LeaveType?=null
    fun showOrderTopPopup(){
        XPopup
            .Builder(this)
            .moveUpToKeyboard(false)
            .asCustom(projectOrderPopup.apply {
                setItemOnClickListener(object : LeaveChoosePopup.ItemOnClickListener {

                    override fun onClick(topbean: LeaveType?, buttombean: LeaveType?) {
                        selectOrderTopBean=topbean;
                        selectOrderButtomBean=buttombean;
                        Timber.e("selectOrderTopBean"+(selectOrderTopBean==null))
                        if(topbean!=null){
                            Timber.e("selectOrderTopBean=${topbean?.name}--selectOrderButtomBean=${buttombean?.name}")
                        }
                    }
                })
                setData(viewModel.OrderStates, viewModel.OrderTypes,selectOrderTopBean,selectOrderButtomBean,"排序","排序方式","排序属性")
            })
            .show()
    }
    /**
     * 筛选缓存的选项
     */
     var selectTopBean: LeaveType?=null
     var selectButtomBean: LeaveType?=null
    fun showChooseTopPopup(){
        XPopup
            .Builder(this)
            .moveUpToKeyboard(false)
            .asCustom(projectTypePopup.apply {
                setItemOnClickListener(object : LeaveChoosePopup.ItemOnClickListener {

                    override fun onClick(topbean: LeaveType?, buttombean: LeaveType?) {
                        selectTopBean=topbean;
                        selectButtomBean=buttombean;
                        Timber.e("selectTopBean"+(topbean==null))
                        if(topbean!=null){
                            Timber.e("selectTopBean=${topbean?.name}--selectButtomBean=${buttombean?.name}")
                        }
                    }
                })
                setData(viewModel.chooseStates, viewModel.chooseTypes,selectTopBean,selectButtomBean,"筛选","审核状态","请假类型")
            })
            .show()
    }
}


