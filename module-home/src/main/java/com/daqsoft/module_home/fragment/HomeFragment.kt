package com.daqsoft.module_home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.daqsoft.library_base.base.AppBaseFragment
import com.daqsoft.library_base.router.ARouterPath
import com.daqsoft.module_home.BR
import com.daqsoft.module_home.R
import com.daqsoft.module_home.databinding.FragmentHomeBinding
import com.daqsoft.module_home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * @describe 任务页面
 */
@AndroidEntryPoint
@Route(path = ARouterPath.Home.PAGER_HOME)
class HomeFragment : AppBaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int {
        return  R.layout.fragment_home
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initViewModel(): HomeViewModel? {
        return requireActivity().viewModels<HomeViewModel>().value
    }

    override fun initData() {
    }

}
