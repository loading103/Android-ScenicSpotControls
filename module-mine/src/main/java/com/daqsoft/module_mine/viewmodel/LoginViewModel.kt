package com.daqsoft.module_mine.viewmodel

import android.app.Application
import android.view.View
import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.alibaba.android.arouter.launcher.ARouter
import com.daqsoft.library_base.router.ARouterPath
import com.daqsoft.library_common.utils.MyCountDownTimer
import com.daqsoft.module_mine.R
import com.daqsoft.module_mine.repository.MineRepository
import com.daqsoft.mvvmfoundation.base.BaseViewModel
import com.daqsoft.mvvmfoundation.binding.command.BindingAction
import com.daqsoft.mvvmfoundation.binding.command.BindingCommand
import com.daqsoft.mvvmfoundation.binding.command.BindingConsumer
import com.daqsoft.mvvmfoundation.http.NetworkUtil

/**
 * @package name：com.daqsoft.module_mine.viewmodel
 * @date 6/11/2020 下午 2:02
 * @author zp
 * @describe 登录viewModel
 */
class LoginViewModel : BaseViewModel<MineRepository> {

    @ViewModelInject
    constructor(application: Application,mineRepository: MineRepository):super(application,mineRepository)

    /**
     * 账号
     */
    val accountNumber = ObservableField<String>("1234567890")

    /**
     * 密码
     */
    val password = ObservableField<String>("")

    /**
     * 验证码
     */
    val verifyCode = ObservableField<String>("123456")

    /**
     * 验证码可见性
     */
    val verifyCodeVisible =  ObservableField<Int>(View.VISIBLE)

    /**
     * 密码可见性
     */
    val passVisible =  ObservableField<Int>(View.GONE)

    /**
     * 清除账号可见性
     */
    val accountNumberCleanVisible =  ObservableField<Int>(View.GONE)

    /**
     * 清除密码可见性
     */
    val passwordCleanVisible =  ObservableField<Int>(View.GONE)

    /**
     * 登陆失败提示可见性
     */
    val  errorMessageVisible = ObservableField<Int>(View.INVISIBLE)

    /**
     * 登陆失败提示信息
     */
    val errorMessage = ObservableField<String>()

    /**
     * 清除账号点击事件
     */
    val accountNumberCleanOnClick = BindingCommand<Unit>(BindingAction {
        accountNumber.set("")
    })

    /**
     * 清除密码点击事件
     */
    val passwordCleanOnClick = BindingCommand<Unit>(BindingAction {
        password.set("")
    })

    /**
     * 密码可见性
     */
    val passwordVisible = MutableLiveData<Boolean>(false)

    /**
     * 验证码点击
     */
    val onClickYzm = MutableLiveData<Boolean>()

    /**
     * 密码可见ICON
     */
    val passwordVisibleIcon = ObservableField<Int>(R.mipmap.dl_yj)

    /**
     * 是否输入账号
     */
    val accountIconSelected = ObservableField<Boolean>(false)
    /**
     * 是否输入验证码
     */
    val verifyIconSelected = ObservableField<Boolean>(false)

    /**
     * 密码可见点击事件
     */
    val passwordVisibleOnClick = BindingCommand<Unit>(BindingAction {
        passwordVisible.value = !passwordVisible.value!!
        passwordVisibleIcon.set(if (passwordVisible.value!!) R.mipmap.dl_yjk else R.mipmap.dl_yj)
    })

    /**
     * 账号密码是否都有数据
     */
    val bothHaveData = MutableLiveData<Pair<Boolean,Boolean>>(Pair(false,false))
    
    /**
     * 账号输入监听
     */
    var accountNumberChangedListener = BindingCommand<String>(BindingConsumer {
        if (it.isNullOrEmpty()){
            accountIconSelected.set(false)
            accountNumberCleanVisible.set(View.GONE)
            bothHaveData.value = Pair(false,bothHaveData.value!!.second)
        }else{
            accountNumberCleanVisible.set(View.VISIBLE)
            accountIconSelected.set(true)
            bothHaveData.value = Pair(true,bothHaveData.value!!.second)
        }
    })

    /**
     * 密码输入监听（现在是监听验证码）
     */
    var passwordChangedListener = BindingCommand<String>(BindingConsumer {
        if (it.isNullOrEmpty()){
            passwordCleanVisible.set(View.GONE)
            verifyIconSelected.set(false)
            bothHaveData.value = Pair(bothHaveData.value!!.first,false)
        }else{
            passwordCleanVisible.set(View.VISIBLE)
            verifyIconSelected.set(true)
            bothHaveData.value = Pair(bothHaveData.value!!.first,true)
        }
    })

    /**
     * 验证码点击事件
     */
    val verifyCodeOnClick = BindingCommand<Unit>(BindingAction {
        getVerifyCode()
    })

    /**
     * 登录点击事件
     */
    val logInOnClick = BindingCommand<Unit>(BindingAction {
        if(!NetworkUtil.isNetworkAvailable(getApplication())){
            errorMessageVisible.set(View.VISIBLE)
            errorMessage.set(getApplication<Application>().resources.getString(R.string.network_connection_failed))
            return@BindingAction
        }
        logIn()
    })



    /**
     * 获取验证码
     */
    fun getVerifyCode(){
        if(accountNumber.get().isNullOrBlank()){
            errorMessageVisible.set(View.VISIBLE)
            errorMessage.set("请输入手机号或账号")
            return
        }
        if( accountNumber.get()?.length!=11){ //如果不是11位全数字，必须是字母开头
            errorMessageVisible.set(View.VISIBLE)
            errorMessage.set("请输入正确的手机号")
            return;
        }
        errorMessageVisible.set(View.INVISIBLE)
        onClickYzm.value=true
//        addSubscribe(
//            model
//                .getVerifyCode()
//                .compose(RxUtils.schedulersTransformer())
//                .compose(RxUtils.schedulersTransformer())
//                .subscribeWith(object : DisposableObserver<Response<ResponseBody>>(){
//                    override fun onNext(t: Response<ResponseBody>?) {
//                        verifySession = t?.headers()?.get("XQSESSION")?:""
//                        val body = t?.body()?.bytes()
//                        if(body == null || body.isEmpty()){
//                            return
//                        }
//                    }
//
//                    override fun onError(e: Throwable?) {
//                    }
//
//                    override fun onComplete() {
//                    }
//                })
//
//        )
    }

    /**
     * 登录
     */
    private fun logIn(){
        ARouter.getInstance().build(ARouterPath.Main.PAGER_MAIN).navigation()

//        addSubscribe(
//            model
//                .login(accountNumber.get()!!, MD5.hexdigest(MD5.hexdigest(password.get()!!)),verifyCode.get(),verifySession = verifySession)
//                .doOnSubscribe{
//                    android.os.Handler(Looper.getMainLooper()).post {
//                        showLoadingDialog()
//                    }
//                }
//                .compose(RxUtils.schedulersTransformer())
//                .compose(RxUtils.schedulersTransformer())
//                .doFinally {
//                    dismissLoadingDialog()
//                }
//                .subscribeWith(object : AppDisposableObserver<AppResponse<Any>>(){
//                    override fun onSuccess(t: AppResponse<Any>) {
//                        dismissLoadingDialog()
//                        // 报讯用户信息
//                        DataStoreUtils.put(DSKeyGlobal.TOKEN,AesCryptUtils.encrypt(t.token?:""))
//                        DataStoreUtils.put(DSKeyGlobal.ACCOUNT_NUMBER,AesCryptUtils.encrypt(accountNumber.get()!!))
//                        DataStoreUtils.put(DSKeyGlobal.PASSWORD,AesCryptUtils.encrypt(password.get()!!))
//
//                        ARouter
//                            .getInstance()
//                            .build(ARouterPath.Main.PAGER_MAIN)
//                            .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
//                            .navigation()
//                    }
//
//                    override fun onFail(e: Throwable) {
//                        dismissLoadingDialog()
//                        errorMessageVisible.set(View.VISIBLE)
//                        if (e is ResponseThrowable){
//                            errorMessage.set(e.errMessage)
//                        }
////                        errorMessage.set( getApplication<Application>().resources.getString(R.string.module_mine_login_failed_tips))
//                    }
//
//                    override fun onFailT(t: AppResponse<Any>) {
//                        dismissLoadingDialog()
//                        errorMessage.set(t.message)
//
////                        if (verifyCodeFirst){
////                            return
////                        }
//
//                        if (t.data == null || (t.data != null && t.data.toString().toFloat() > 3)){
//                            errorMessage.set(t.message)
//                            verifyCodeVisible.set(View.VISIBLE)
//                            getVerifyCode()
//                            verifyCodeFirst = true
//                        }
//                    }
//                })
//
//        )
    }
}