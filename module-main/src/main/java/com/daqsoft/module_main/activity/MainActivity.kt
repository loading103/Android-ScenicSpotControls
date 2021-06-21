package com.daqsoft.module_main.activity

import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.daqsoft.library_base.base.AppBaseActivity
import com.daqsoft.library_base.router.ARouterPath
import com.daqsoft.module_main.BR
import com.daqsoft.module_main.R
import com.daqsoft.module_main.databinding.ActivityMainBinding
import com.daqsoft.module_main.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem
import kotlin.properties.Delegates


@AndroidEntryPoint
@Route(path = ARouterPath.Main.PAGER_MAIN)
class MainActivity  : AppBaseActivity<ActivityMainBinding, MainViewModel>(){

    private var mFragments: ArrayList<Fragment> = arrayListOf()

    var currentIndex by Delegates.notNull<Int>()

    var lastIndex =0

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_main
    }

    override fun initViewModel(): MainViewModel {
        return viewModels<MainViewModel>().value
    }

    override fun initView() {
        super.initView()
        initBottomTab()
        initFragment()
    }


    private fun initBottomTab() {
        (binding.tabs.getChildAt(0) as RadioButton).isChecked=true

        binding.tabs.setOnCheckedChangeListener {
                p0, index ->
            binding.ivJh.isSelected = index==3

            try {
                currentIndex = index-1
                val previousFragment = mFragments[lastIndex]
                val currentFragment = mFragments[currentIndex]
                val transaction = supportFragmentManager.beginTransaction()
                transaction
                    .hide(previousFragment)
                if (!currentFragment.isAdded) {
                    transaction
                        .add(R.id.frame_layout, currentFragment)
                        .commit()
                } else {
                    transaction
                        .show(currentFragment)
                        .setMaxLifecycle(currentFragment, Lifecycle.State.RESUMED)
                        .commit()
                }
                lastIndex=index-1
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * 初始化页面
     */
    private fun initFragment() {
        if (mFragments.isNotEmpty()){
            return
        }
        //这里需要通过ARouter获取，不能直接new,因为在组件独立运行时，宿主app是没有依赖其他组件，所以new不到其他组件的Fragment)
        val homeFragment = ARouter.getInstance().build(ARouterPath.Task.PAGER_TASK).navigation() as Fragment
        mFragments.add(homeFragment)
        val taskFragment = ARouter.getInstance().build(ARouterPath.Workbench.PAGER_WORKBENCH).navigation() as Fragment
        mFragments.add(taskFragment)
        val projectFragment = ARouter.getInstance().build(ARouterPath.Home.PAGER_HOME).navigation() as Fragment
        mFragments.add(projectFragment)
        val workbenchFragment = ARouter.getInstance().build(ARouterPath.Statistics.PAGER_DATA).navigation() as Fragment
        mFragments.add(workbenchFragment)
        val mineFragment = ARouter.getInstance().build(ARouterPath.Mine.PAGER_MINE).navigation() as Fragment
        mFragments.add(mineFragment)
        // 默认选中第一个
        supportFragmentManager.beginTransaction().apply {
            add(R.id.frame_layout, mFragments[0])
            commit()
            currentIndex = 0
        }
    }

}