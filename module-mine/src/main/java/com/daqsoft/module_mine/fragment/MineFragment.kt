package com.daqsoft.module_mine.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.daqsoft.library_base.base.AppBaseFragment
import com.daqsoft.library_base.router.ARouterPath
import com.daqsoft.module_mine.R
import com.daqsoft.module_mine.BR
import com.daqsoft.module_mine.databinding.FragmentMineBinding
import com.daqsoft.module_mine.viewmodel.MineViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * @ClassName    MineFragment
 * @Description
 * @Author       yuxc
 * @CreateDate   2021/5/7
 */

@AndroidEntryPoint
@Route(path = ARouterPath.Mine.PAGER_MINE)
class MineFragment : AppBaseFragment<FragmentMineBinding, MineViewModel>() {
    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int {
        return R.layout.fragment_mine
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initViewModel(): MineViewModel? {
        return requireActivity().viewModels<MineViewModel>().value
    }

    override fun initData() {
    }
}
