package com.daqsoft.module_work.viewmodel.itemviewmodel

import androidx.databinding.ObservableField
import com.alibaba.android.arouter.launcher.ARouter
import com.daqsoft.library_base.router.ARouterPath
import com.daqsoft.module_work.R
import com.daqsoft.module_work.viewmodel.WorkViewModel
import com.daqsoft.module_work.repository.pojo.vo.MenuInfo
import com.daqsoft.mvvmfoundation.base.MultiItemViewModel
import com.daqsoft.mvvmfoundation.binding.command.BindingAction
import com.daqsoft.mvvmfoundation.binding.command.BindingCommand

/**
 * @package name：com.daqsoft.module_workbench.viewmodel
 * @date 25/11/2020 下午 2:08
 * @author zp
 * @describe 工作台 item label  ViewModel
 */
class WorkBenchItemLabelViewModel (
    private val workBenchViewModel: WorkViewModel,
    val menuInfo: MenuInfo
) : MultiItemViewModel<WorkViewModel>(workBenchViewModel){

    val menuInfoObservable = ObservableField<MenuInfo>()


    val placeholderRes = ObservableField<Int>()

    init {

        menuInfoObservable.set(menuInfo)
        placeholderRes.set(
        when(menuInfo.appMenuName) {
            "考勤" -> {
                R.mipmap.home_icon_kaoqing
            }
            "请假" -> {
                R.mipmap.home_icon_qingjia
            }
            "审批管理" -> {
                R.mipmap.home_icon_spgl
            }
            "补卡申请" -> {
                R.mipmap.home_icon_bkgl
            }
            "通讯录" -> {
                R.mipmap.home_icon_txunlv
            }
            "请假审批" -> {
                R.mipmap.home_icon_qingjia
            }
            else -> {
                R.mipmap.home_icon_kaoqing
            }
        })
    }

    /**
     * item 点击事件
     */
    val itemOnClick = BindingCommand<Unit>(BindingAction {

        when(menuInfo.appMenuName){
            "考勤" ->{
            }

            "请假"->{
                ARouter.getInstance().build( ARouterPath.Workbench.PAGER_LEAVE_LIST).navigation()
            }
            "审批管理" ->{

            }
            "补卡申请" ->{

            }
            "通讯录" -> {

            }
            "请假审批" -> {

            }
            else ->{

            }
        }
    })

}
