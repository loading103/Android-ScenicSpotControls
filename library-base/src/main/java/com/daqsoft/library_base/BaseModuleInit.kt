package com.daqsoft.library_base

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.webkit.WebView
import androidx.annotation.RequiresApi
import com.alibaba.android.arouter.launcher.ARouter
import com.daqsoft.library_base.init.IModuleInit
import com.daqsoft.library_base.widget.ChrysanthemumFooter
import com.daqsoft.library_base.widget.ChrysanthemumHeader
import com.daqsoft.library_base.wrapper.loadsircallback.EmptyCallback
import com.daqsoft.library_base.wrapper.loadsircallback.ErrorCallback
import com.daqsoft.library_base.wrapper.loadsircallback.LoadingCallback
import com.daqsoft.library_base.wrapper.loadsircallback.TimeoutCallback
import com.daqsoft.library_base.BuildConfig
import com.daqsoft.mvvmfoundation.crash.CaocConfig
import com.jeremyliao.liveeventbus.LiveEventBus
import com.kingja.loadsir.core.LoadSir
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import timber.log.Timber


class BaseModuleInit : IModuleInit{

    companion object{
        var oaid:String = ""
    }



    @SuppressLint("DefaultLocale")
    override fun onInitAhead(application: Application): Boolean {
        Timber.e("基础层初始化 -- onInitAhead")
        return false
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onInitLow(application: Application): Boolean {
        Timber.e("基础层初始化 -- onInitLow")

        initARouter(application)
        initLiveEventBus(application)
        initSmartRefreshLayout()
        initLoadSir()
        initTimber()
        initCaocConfig()
        webViewSetPath(application)
        return false
    }


    /**
     *  初始化阿里路由框架
     */
    private fun initARouter(application: Application){
        if (BuildConfig.BUILD_TYPE != "release" ) {
            // 打印日志
            ARouter.openLog()
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openDebug()
        }
        // 尽可能早，推荐在Application中初始化
        ARouter.init(application)
    }


    /**
     * 配置LiveEventBus
     */
    private fun initLiveEventBus(application: Application){
        LiveEventBus.config().setContext(application)
    }


    /**
     * 初始化 下拉刷新
     */
    private fun initSmartRefreshLayout(){
        // 全局配置
        SmartRefreshLayout.setDefaultRefreshInitializer { context, layout ->
            // 关闭自动加载更多
            layout.setEnableAutoLoadMore(false)
            // 关闭加载更多（在需要的页面打开）
            layout.setEnableLoadMore(false)
        }

        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            ChrysanthemumHeader(context)
        }

        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
            ChrysanthemumFooter(context)
        }

    }

    /**
     * 初始化反馈页管理
     */
    private fun initLoadSir(){
        LoadSir
            .beginBuilder()
            .addCallback(ErrorCallback())
            .addCallback(EmptyCallback())
            .addCallback(LoadingCallback())
            .addCallback(TimeoutCallback())
            .setDefaultCallback(LoadingCallback::class.java)
            .commit()
    }



    /**
     *  日志打印
     */
    fun initTimber(){
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    /**
     *   配置全局异常崩溃操作
     */
    fun  initCaocConfig(){
        CaocConfig.Builder.create()
            //是否启动全局异常捕获
            .enabled(BuildConfig.BUILD_TYPE != "release")
            //是否显示错误详细信息
            .showErrorDetails(BuildConfig.BUILD_TYPE != "release")
            //是否显示重启按钮
            .showRestartButton(BuildConfig.BUILD_TYPE != "release")
            //是否跟踪Activity
            .trackActivities(BuildConfig.BUILD_TYPE != "release")
            //崩溃的间隔时间(毫秒)
            .minTimeBetweenCrashesMs(2000)
            .apply()
    }


    //Android P 以及之后版本不支持同时从多个进程使用具有相同数据目录的WebView
    //为其它进程webView设置目录
    @RequiresApi(Build.VERSION_CODES.P)
    fun webViewSetPath(context: Context?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val processName = getProcessName(context)
            if ("com.daqsoft.scenicareamanagement"!= processName) {
                WebView.setDataDirectorySuffix(processName?:"com.daqsoft.scenicareamanagement")
            }
        }
    }

    private fun getProcessName(context: Context?): String? {
        if (context == null) return null
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val processInfo = manager.runningAppProcesses
        processInfo.forEach {
            if (it.pid == android.os.Process.myPid()) {
                return it.processName
            }
        }
        return null
    }

}


