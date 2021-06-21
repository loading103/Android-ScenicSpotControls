package com.daqsoft.library_base.router

/**
 * @package name：com.daqsoft.library_base.router
 * @date 28/10/2020 下午 3:19
 * @author zp
 * @describe
 */
class ARouterPath {

    /**
     * 全局
     */
    object Global{

    }

    /**
     * 主业务组件
     */
    object Main {
        private const val MAIN = "/main"
        /**主业务界面*/
        const val PAGER_MAIN = "$MAIN/Main"

    }


    /**
     * 任务组件
     */
    object Task {
        private const val TASK = "/tasj"
        /**任务页面*/
        const val PAGER_TASK = "$TASK/Home"
    }

    /**
     * 工作台
     */
    object Workbench {
        private const val WORKBENCH = "/workbench"
        /**工作台页面*/
        const val PAGER_WORKBENCH= "${Workbench.WORKBENCH}/Workbench"
        /**请假列表页面*/
        const val PAGER_LEAVE_LIST= "${Workbench.WORKBENCH}/LeaveList"
        /**发起请假申请页面*/
        const val ADD_LEAVE_APPLY= "${Workbench.WORKBENCH}/AddLeaveApply"
        /**请假申请详情页面*/
        const val ADD_LEAVE_APPLY_INFO= "${Workbench.WORKBENCH}/AddLeaveApplyInfo"
    }

    /**
     * 聚合组件
     */
    object Home {
        private const val HOME = "/home"
        /**聚合首页*/
        const val PAGER_HOME= "$HOME/Home"
    }
    /**
     * 数据组件
     */
    object Statistics {
        private const val DATA = "/statistics"
        /**数据页面*/
        const val PAGER_DATA = "$DATA/Statistics"
    }

    /**
     * 用户组件
     */
    object Mine {
        private const val MINE = "/mine"
        /**我的页面*/
        const val PAGER_MINE = "$MINE/Mine"
        /**个人信息*/
        const val PAGER_PERSONAL_INFO = "$MINE/PersonalInfo"
        /**登录界面*/
        const val PAGER_LOGIN = "${MINE}/Login"
    }

}
