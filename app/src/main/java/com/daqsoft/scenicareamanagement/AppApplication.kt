package com.daqsoft.scenicareamanagement

import android.app.ActivityManager
import android.content.Context
import android.os.Build
import android.webkit.WebView
import androidx.annotation.RequiresApi
import com.daqsoft.library_base.config.ModuleLifecycleConfig
import com.daqsoft.mvvmfoundation.base.BaseApplication
import com.daqsoft.mvvmfoundation.crash.CaocConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * @package name：com.daqsoft.scenicareamanagement
 * @date 22/4/2021 下午 3:01
 * @author zp
 * @describe
 */
@HiltAndroidApp
class AppApplication : BaseApplication() {

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate() {
        super.onCreate()
        //初始化组件(靠后)
        ModuleLifecycleConfig.initModuleLow(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        //初始化组件(靠前)
        ModuleLifecycleConfig.initModuleAhead(this)
    }
}