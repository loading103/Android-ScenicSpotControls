package com.daqsoft.module_statistics.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.daqsoft.library_base.base.AppBaseFragment
import com.daqsoft.library_base.router.ARouterPath
import com.daqsoft.module_statistics.BR
import com.daqsoft.module_statistics.R
import com.daqsoft.module_statistics.databinding.FragmentStatisticBinding
import com.daqsoft.module_statistics.viewmodel.StatisticsViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * @describe 我的页面
 */
@AndroidEntryPoint
@Route(path = ARouterPath.Statistics.PAGER_DATA)
class StatisticsFragment : AppBaseFragment<FragmentStatisticBinding, StatisticsViewModel>() {

    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int {
        return  R.layout.fragment_statistic
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initViewModel(): StatisticsViewModel? {
        return requireActivity().viewModels<StatisticsViewModel>().value
    }

    override fun initData() {
    }

}
