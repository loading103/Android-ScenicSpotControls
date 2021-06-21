package com.daqsoft.library_base.net

import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.alibaba.android.arouter.launcher.ARouter
import com.daqsoft.library_base.global.LEBKeyGlobal
import com.daqsoft.library_base.router.ARouterPath
import com.daqsoft.mvvmfoundation.http.BaseResponse
import com.daqsoft.mvvmfoundation.http.NetworkUtil
import com.daqsoft.mvvmfoundation.http.ResponseThrowable
import com.daqsoft.mvvmfoundation.utils.ContextUtils
import com.daqsoft.mvvmfoundation.utils.ToastUtils
import com.jeremyliao.liveeventbus.LiveEventBus
import io.reactivex.rxjava3.observers.DisposableObserver


/**
 * @package name：com.daqsoft.mvvmfoundation.http
 * @date 26/10/2020 下午 3:38
 * @author zp
 * @describe
 */
abstract class AppDisposableObserver<T> : DisposableObserver<T>() {

    open fun onSuccessToast():Boolean = false

    abstract fun onSuccess(t:T)

    open fun onFailToast():Boolean = true
    
    open fun onFail(e: Throwable) {}

    open fun onFailT(t:T){}

    override fun onStart() {
        super.onStart()
        if (!NetworkUtil.isNetworkAvailable(ContextUtils.getContext())) {
            onFail(Throwable("网络连接不可用，请检查网络设置"))
        }
    }

    override fun onNext(t: T) {
        if (t is BaseResponse<*>){
            when(t.code){
                CodeRule.CODE_0 -> {
                    onSuccess(t)
                    if (onSuccessToast()){
                        ToastUtils.showShortSafe(t.message)
                    }
                }
                else->{
                    onFailT(t)
                    if (onFailToast()){
                        ToastUtils.showShortSafe(t.message)
                    }
                }
            }
        }
    }


    override fun onError(e: Throwable) {
        e.printStackTrace()
        onFail(e)
        if (e is ResponseThrowable){
            if (onFailToast()){
                ToastUtils.showShortSafe(e.errMessage)
            }
            when(e.code){

            }
            return
        }
    }

    override fun onComplete() {
    }

    object CodeRule {
        //请求成功, 正确的操作方式
        internal const val CODE_0 = 0
    }

}