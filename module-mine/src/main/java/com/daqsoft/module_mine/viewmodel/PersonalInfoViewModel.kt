package com.daqsoft.module_mine.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import com.daqsoft.module_mine.R
import com.daqsoft.module_mine.repository.MineRepository
import com.daqsoft.mvvmfoundation.toolbar.ToolbarViewModel

/**
 * @package name：com.daqsoft.module_mine.viewmodel
 * @date 17/11/2020 下午 2:29
 * @author zp
 * @describe
 */
class PersonalInfoViewModel : ToolbarViewModel<MineRepository> {

    @ViewModelInject constructor(application: Application,mineRepository: MineRepository):super(application, mineRepository)


    override fun onCreate() {
        initToolbar()
    }


    private fun initToolbar(){
        setBackground(com.daqsoft.library_base.R.color.transparent)
        //    setBackIconSrc(R.mipmap.project_top_return_white)
        setTitleTextColor(com.daqsoft.library_base.R.color.white)
        setTitleText(getApplication<Application>().resources.getString(com.daqsoft.library_base.R.string.module_mine_homepage))
    }

//    val employeeInfo by lazy { ObservableField<EmployeeInfo>()}

    val birthday by lazy {  ObservableField<String>() }

//    var avatarLiveData = MutableLiveData<Unit>()
//    /**
//     * 头像点击事件
//     */
//    val avatarOnClick = BindingCommand<Unit>(BindingAction {
//        avatarLiveData.value = null
//    })
//
//    /**
//     * 生日点击事件
//     */
//    val birthdayOnClick = BindingCommand<Unit>(BindingAction {
//        ARouter.getInstance().build(ARouterPath.Mine.PAGER_BIRTHDAY).navigation()
//    })
//
//    /**
//     * 住址点击事件
//     */
//    val addressOnClick = BindingCommand<Unit>(BindingAction {
//        ARouter.getInstance().build(ARouterPath.Mine.PAGER_ADDRESS).navigation()
//    })
//
//    /**
//     * 兴趣点击事件
//     */
//    val interestOnClick = BindingCommand<Unit>(BindingAction {
//        ARouter
//            .getInstance()
//            .build(ARouterPath.Mine.PAGER_INTEREST)
//            .withString("content",employeeInfo.get()?.employeeHobby)
//            .navigation()
//    })
//
//    /**
//     * 登录密码点击事件
//     */
//    val loginPasswordOnClick = BindingCommand<Unit>(BindingAction {
//        ARouter
//            .getInstance()
//            .build(ARouterPath.Mine.PAGER_PASSWORD)
//            .withString("type",getApplication<Application>().resources.getString(R.string.module_mine_login_password))
//            .navigation()
//    })
//
//    /**
//     * 二级密码点击事件
//     */
//    val secondaryPasswordOnClick = BindingCommand<Unit>(BindingAction {
//        ARouter
//            .getInstance()
//            .build(ARouterPath.Mine.PAGER_PASSWORD)
//            .withString("type",getApplication<Application>().resources.getString(R.string.module_mine_secondary_password))
//            .navigation()
//    })
//
//
//
//    val signOutLiveData = MutableLiveData<Unit>()
//    /**
//     * 退出登录点击事件
//     */
//    val signOutOnClick = BindingCommand<Unit>(BindingAction {
//        signOutLiveData.value = null
//    })
//
//
//
//    /**
//     * 获取我的信息
//     */
//    fun getEmployeeInfo(){
//        addSubscribe(
//            model
//                .getEmployeeInfo()
//                .compose(RxUtils.schedulersTransformer())
//                .compose(RxUtils.exceptionTransformer())
//                .subscribeWith(object : AppDisposableObserver<AppResponse<EmployeeInfo>>() {
//                    override fun onSuccess(t: AppResponse<EmployeeInfo>) {
//                        t.data?.let {
//                            employeeInfo.set(it)
//                            if (it.employeeBirthdayType == BirthdayCalendar.LUNAR_CALENDAR.text){
//                                birthday.set("${BirthdayCalendar.LUNAR_CALENDAR.text}-${it.employeeLunarBirthday}")
//                            }else{
//                                birthday.set("${BirthdayCalendar.NATIONAL_CALENDAR.text}-${it.employeeSolarBirthday}")
//                            }
//                        }
//                    }
//                })
//        )
//    }
//
//    /**
//     * 上传头像
//     */
//    fun uploadAvatar(path:String){
//        addSubscribe(
//            Observable.create(ObservableOnSubscribe<MultipartBody.Part> {
//                val bitmap = BitmapFactory.decodeFile(path)
//                val stream = ByteArrayOutputStream()
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
//                var options = 90
//                while (stream.toByteArray().size / 1024 > 1024 * 2 ){
//                    stream.reset()
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, options, stream)
//                    options -= 10
//                }
//                val body = RequestBody.create(MediaType.get("image/png"), stream.toByteArray())
//                val file = MultipartBody.Part.createFormData("file","${System.currentTimeMillis()}.jpg", body)
//                it.onNext(file)
//                bitmap.recycle()
//            })
//                .doOnSubscribe{
//                    Handler(Looper.getMainLooper()).post {
//                        showLoadingDialog()
//                    }
//                }
//                .concatMap(Function<MultipartBody.Part, Observable<AppResponse<List<UploadResult>>>> {
//                    return@Function model.uploadOSS(file = it)
//                })
//                .concatMap(Function<AppResponse<List<UploadResult>>,Observable<AppResponse<Any>>>{
//                    var url = ""
//                    it.data?.let {
//                        if (it.isNotEmpty()){
//                            url = it[0].url
//                        }
//                    }
//                    return@Function model.updateAvatar(url)
//                })
//                .compose(RxUtils.exceptionTransformer())
//                .compose(RxUtils.schedulersTransformer())
//                .doFinally {
//                    // 未执行 ？
////                    dismissLoadingDialog()
//                }
//                .subscribeWith(object: AppDisposableObserver<AppResponse<Any>>(){
//                    override fun onSuccess(t: AppResponse<Any>) {
//                        dismissLoadingDialog()
//                        LiveEventBus.get(LEBKeyGlobal.MODIFY_AVATAR_KEY).post(true.toString())
//                        getEmployeeInfo()
//                    }
//
//                    override fun onFail(e: Throwable) {
//                        super.onFail(e)
//                        dismissLoadingDialog()
//                    }
//                })
//        )
//    }
//
//
//
//    val singOutSucceedLiveData = MutableLiveData<Unit>()
//    /**
//     * 退出登录
//     */
//    fun signOut(){
//        addSubscribe(
//            model
//                .signOut()
//                .compose(RxUtils.schedulersTransformer())
//                .compose(RxUtils.exceptionTransformer())
//                .subscribeWith(object : AppDisposableObserver<AppResponse<Any>>() {
//                    override fun onSuccess(t: AppResponse<Any>) {
//                       singOutSucceedLiveData.value = null
//                    }
//                })
//        )
//    }
}