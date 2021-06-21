package com.daqsoft.module_mine.viewmodel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import com.alibaba.android.arouter.launcher.ARouter
import com.daqsoft.library_base.R
import com.daqsoft.library_base.router.ARouterPath
import com.daqsoft.module_mine.repository.MineRepository
import com.daqsoft.mvvmfoundation.binding.command.BindingAction
import com.daqsoft.mvvmfoundation.binding.command.BindingCommand
import com.daqsoft.mvvmfoundation.toolbar.ToolbarViewModel

/**
 * @ClassName    MineViewModel
 * @Description
 * @Author       yuxc
 * @CreateDate   2021/5/7
 */
class MineViewModel : ToolbarViewModel<MineRepository> {

    @ViewModelInject
    constructor(application: Application, mineRepository:MineRepository):super(application,mineRepository)

    override fun onCreate() {
        initToolbar()
    }

    private fun initToolbar() {
        setBackground(R.color.transparent)
    //    setBackIconSrc(R.mipmap.project_top_return_white)
        setTitleTextColor(R.color.white)
        setTitleText(getApplication<Application>().resources.getString(R.string.module_mine_homepage))

    }

    /**
     * 设置点击事件
     */
    var personalInfoOnClick = BindingCommand<Unit>(BindingAction {
        ARouter.getInstance().build(ARouterPath.Mine.PAGER_PERSONAL_INFO).navigation()
    })
}