package com.daqsoft.module_task.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.daqsoft.library_base.base.AppBaseFragment
import com.daqsoft.library_base.router.ARouterPath
import com.daqsoft.module_task.BR
import com.daqsoft.module_task.R
import com.daqsoft.module_task.databinding.FragmentTaskBinding
import com.daqsoft.module_task.viewmodel.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * @describe 任务页面
 */
@AndroidEntryPoint
@Route(path = ARouterPath.Task.PAGER_TASK)
class TaskFragment : AppBaseFragment<FragmentTaskBinding, TaskViewModel>() {

    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int {
        return  R.layout.fragment_task
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initViewModel(): TaskViewModel? {
        return requireActivity().viewModels<TaskViewModel>().value
    }

    override fun initData() {
    }

}
