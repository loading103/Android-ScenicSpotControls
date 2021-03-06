package com.daqsoft.library_base.utils

import android.app.ActivityManager
import android.app.ActivityManager.RunningAppProcessInfo
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.ACTIVITY_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.webkit.CookieManager
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.daqsoft.library_base.R
import com.daqsoft.library_base.global.ConstantGlobal
import com.daqsoft.mvvmfoundation.utils.ContextUtils
import com.daqsoft.mvvmfoundation.utils.ToastUtils
import java.io.File


object AppUtils {

    /**
     * 跳转系统 浏览器
     * @param url String
     */
    fun jumpBrowser(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            ContextCompat.startActivity(ContextUtils.getContext(), intent, null)
        }catch (e: Exception){
            e.printStackTrace()

        }
    }

    /**
     * 复制
     * @param str String
     * @param needToast Boolean
     */
    fun primaryClip(str: String, needToast: Boolean = true){
        val cm = ContextUtils.getContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val mClipData = ClipData.newPlainText(null, str)
        cm.setPrimaryClip(mClipData)
        if (needToast){
            ToastUtils.showShort("复制成功")
        }
    }

    /**
     * 获取版本号
     * @return String
     */
    fun getVersionName():String{
        return ContextUtils.getContext().packageManager.getPackageInfo(
            ContextUtils.getContext().packageName,
            0
        ).versionName
    }

    /**
     * 获取渠道名
     * @return String
     */
    fun getChannelData(): String {
        var channel = ""
        try {
            ContextUtils.getContext().packageManager?.let {
                it.getApplicationInfo(
                    ContextUtils.getContext().packageName,
                    PackageManager.GET_META_DATA
                )?.let {
                    channel = it.metaData.getString("UMENG_CHANNEL").toString()
                }
            }
        }catch (e: java.lang.Exception){
            e.printStackTrace()
        }
        return channel
    }

    /**
     * 判断 app 是否在运行
     * @param context Context
     * @param pkgName String
     * @return Boolean
     */
    fun isAppRunning(context: Context, pkgName: String):Boolean {
        if (pkgName.isBlank()){
            return false
        }
        val ai = context.applicationInfo
        val uid = ai.uid
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        if (am != null) {
            val taskInfo = am.getRunningTasks(Int.MAX_VALUE)
            if (taskInfo != null && taskInfo.size > 0) {
                for (aInfo in taskInfo) {
                    if (aInfo.baseActivity != null) {
                        if (pkgName == aInfo.baseActivity!!.packageName) {
                            return true
                        }
                    }
                }
            }
            val serviceInfo = am.getRunningServices(Int.MAX_VALUE)
            if (serviceInfo != null && serviceInfo.size > 0) {
                for (aInfo in serviceInfo) {
                    if (uid == aInfo.uid) {
                        return true
                    }
                }
            }
        }
        return false
    }

    /**
     * 判断 app 是否在前台
     * @param context Context
     * @return Boolean
     */
    fun isAppForeground(context: Context):Boolean{
        val activityManager = context.getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val appProcessInfoList = activityManager.runningAppProcesses
        for (appProcessInfo in appProcessInfoList) {
            if (appProcessInfo.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                && appProcessInfo.processName == context.applicationInfo.processName
            ) {
                return true
            }
        }
        return false
    }

    /**
     * 将app 置为前台
     * @param context Context
     */
    fun setTopApp(context: Context){
        val activityManager = context.getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val taskInfoList = activityManager.getRunningTasks(100)
        for (taskInfo in taskInfoList) {
            if (taskInfo.topActivity!!.packageName == context.packageName) {
                activityManager.moveTaskToFront(taskInfo.id, 0)
                break
            }
        }
    }


    /**
     * activity stack 中是是否有 activity
     */
    fun  isExistActivity(context: Context, cls: Class<*>) : Boolean{
        val intent = Intent(context, cls)
        val componentName =  intent.resolveActivity(context.packageManager)
        var flag = false
        if (componentName != null){
            val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val stackInfo = activityManager.getRunningTasks(10)
            stackInfo.forEach{
                if (it.baseActivity != null && it.baseActivity == componentName){
                    flag = true
                }
            }
        }
        return flag
    }

    /**
     * 同步 cookie
     * @param url String
     * @param cookieValue String
     */
    fun syncCookie(url: String, cookieValue: String) {
        try {
            val cookieManager: CookieManager = CookieManager.getInstance()
            cookieManager.setAcceptCookie(true)
            cookieManager.removeSessionCookies(null)
            cookieManager.removeAllCookies(null)
            cookieManager.setCookie(url, cookieValue)
            cookieManager.flush()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 清除 cookie
     */
    fun clearCookie() {
        try {
            val cookieManager: CookieManager = CookieManager.getInstance()
            cookieManager.removeSessionCookies(null)
            cookieManager.removeAllCookies(null)
            cookieManager.flush()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 安装  apk
     * @param context Context
     * @param apk File
     */
    fun installApk(context: Context, apk: File){
        val intent = Intent(Intent.ACTION_VIEW)
        intent.action = Intent.ACTION_VIEW
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        val uri: Uri
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(
                context,
                context.packageName + ConstantGlobal.FILE_PROVIDER,
                apk
            )
        } else {
            uri = Uri.fromFile(apk)
        }
        intent.setDataAndType(uri, "application/vnd.android.package-archive")
        context.startActivity(intent)
    }

}