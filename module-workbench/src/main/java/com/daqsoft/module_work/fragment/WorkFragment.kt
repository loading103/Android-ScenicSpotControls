package com.daqsoft.module_work.fragment

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.daqsoft.library_base.base.AppBaseFragment
import com.daqsoft.library_base.router.ARouterPath
import com.daqsoft.module_work.BR
import com.daqsoft.module_work.R
import com.daqsoft.module_work.adapter.WorkBenchAdapter
import com.daqsoft.module_work.databinding.FragmentWorkBinding
import com.daqsoft.module_work.viewmodel.WorkViewModel
import com.daqsoft.mvvmfoundation.utils.dp
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * @describe 工作台页面
 */
@AndroidEntryPoint
@Route(path = ARouterPath.Workbench.PAGER_WORKBENCH)
class WorkFragment : AppBaseFragment<FragmentWorkBinding, WorkViewModel>() {

    @Inject
    lateinit var workBenchAdapter: WorkBenchAdapter

    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int {
        return  R.layout.fragment_work
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initViewModel(): WorkViewModel? {
        return requireActivity().viewModels<WorkViewModel>().value
    }

    override fun initView() {
        super.initView()
        initRecycleView()
    }

    override fun initData() {
        super.initData()
        viewModel.createItem()
    }

    /**
     * 初始化 recycleView
     */
    private fun initRecycleView() {
        binding.recyclerView.apply {
            adapter = workBenchAdapter
            addItemDecoration(object : RecyclerView.ItemDecoration(){
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    val position = parent.getChildAdapterPosition(view)
                    if (position == 0){
                        outRect.top - 12.dp
                    }
                    outRect.left = 12.dp
                    outRect.right = 12.dp
                    outRect.bottom = 12.dp
                }
            })

        }
    }
}
