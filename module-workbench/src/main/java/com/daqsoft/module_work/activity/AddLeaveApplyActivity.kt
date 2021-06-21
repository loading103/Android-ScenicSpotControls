package com.daqsoft.module_work.activity

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.TimePickerView
import com.daqsoft.library_base.base.AppBaseActivity
import com.daqsoft.library_base.router.ARouterPath
import com.daqsoft.library_base.utils.GlideEngine
import com.daqsoft.library_common.adapter.GridImageAdapter
import com.daqsoft.library_common.bean.LeaveType
import com.daqsoft.library_common.utils.CalculateHours
import com.daqsoft.library_common.widget.FullyGridLayoutManager
import com.daqsoft.library_common.xpopup.LeaveTypePopup
import com.daqsoft.module_work.BR
import com.daqsoft.module_work.R
import com.daqsoft.module_work.databinding.ActivityAddLeaveApplyBinding
import com.daqsoft.module_work.viewmodel.AddLeaveApplyViewModel
import com.daqsoft.mvvmfoundation.utils.ToastUtils
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.lxj.xpopup.XPopup
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

/**
 * @ClassName    AskLeaveActivity
 * @Description
 * @Author       yuxc
 * @CreateDate   2021/5/7
 */
@AndroidEntryPoint
@Route(path = ARouterPath.Workbench.ADD_LEAVE_APPLY)
class AddLeaveApplyActivity : AppBaseActivity<ActivityAddLeaveApplyBinding, AddLeaveApplyViewModel>(),
    GridImageAdapter.onAddPicClickListener {

    lateinit var timePicker : TimePickerView
    /**
     * 图片个数
     */
    val picMaxNumber : Int=1

    var mPicAdapter : GridImageAdapter?=null


    val leaveTypePopup : LeaveTypePopup by lazy {
        LeaveTypePopup(this)
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_add_leave_apply
    }

    override fun initViewModel(): AddLeaveApplyViewModel? {
        return viewModels<AddLeaveApplyViewModel>().value
    }

    override fun initView() {
        super.initView()
        mPicAdapter= GridImageAdapter(this, this)
        mPicAdapter?.setSelectMax(picMaxNumber)
        binding.picRecycleView.apply {
            val spanCount = 4
            layoutManager = FullyGridLayoutManager(context, spanCount, GridLayoutManager.VERTICAL, false)
            adapter=mPicAdapter
        }
    }

    override fun initViewObservable() {
        super.initViewObservable()

        viewModel.LeaveTypeLiveData.observe(this, androidx.lifecycle.Observer {
            showChooseTypePopup()
        })

        viewModel.StartTimeLiveData.observe(this, androidx.lifecycle.Observer {
            showDatePicker(true)
        })

        viewModel.EndTimeLiveData.observe(this, androidx.lifecycle.Observer {
            showDatePicker(false)
        })


        // 头像
        viewModel.avatarLiveData.observe(this, androidx.lifecycle.Observer {
            requestPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA,
                callback = { jumpAlbum() } )
        })

    }



    /**
     * 请假类型
     */
    var selectBean: LeaveType?=null
    fun showChooseTypePopup(){
        XPopup
            .Builder(this)
            .moveUpToKeyboard(false)
            .asCustom(leaveTypePopup.apply {
                setItemOnClickListener(object : LeaveTypePopup.ItemOnClickListener {
                    override fun onClick(bean: LeaveType?) {
                        selectBean=bean
                        binding?.tvChooseType.text=bean?.name
                    }
                })
                setData(viewModel.LeaveTypeChoose, selectBean)
            })
            .show()
    }

    /**
     * 时间选择器
     * isStartTime是不是开始时间
     * isDay =true 年月日 =false 年月日时分
     */
    var endtime:Long=0L
    var starttime:Long=0L
    fun showDatePicker(isStartTime :Boolean) {
        val startDate = Calendar.getInstance()
        val endDate = Calendar.getInstance()
        // 系统当前时间
        val selectedDate = Calendar.getInstance()
        // 正确设置方式 原因：注意事项有说明
        startDate[1970, 1] = 1
        endDate[2030, 12] = 31
        timePicker = TimePickerBuilder(this,
            OnTimeSelectListener { date, v ->

                val stampToTime = stampToTime(date.time.toString())
                if(isStartTime){
                    if(endtime>0 && endtime<=date.time){
                        ToastUtils.showLong("开始时间必须小于结束时间")
                        return@OnTimeSelectListener
                    }
                    starttime=date.time
                    binding.tvStartTime.text=stampToTime
                    if(!binding.tvEndTime.text.toString().isNullOrBlank()){
                        val ch = CalculateHours()
                        val time = ch.calculateHours( stampToTime,binding.tvEndTime.text.toString())
                        binding.tvTime.text=String.format("%.1f",time)
                    }
                }else{
                    if(starttime>0 && starttime>=date.time){
                        ToastUtils.showLong("开始时间必须小于结束时间")
                        return@OnTimeSelectListener
                    }
                    endtime=date.time
                    binding.tvEndTime.text=stampToTime
                    if(!binding.tvStartTime.text.toString().isNullOrBlank()){
                        val ch = CalculateHours()
                        val time = ch.calculateHours(  binding.tvStartTime.text.toString(), stampToTime)
                        binding.tvTime.text=String.format("%.1f",time)
                    }

                }
            }).setTimeSelectChangeListener { }
            .addOnCancelClickListener { }
            .setType(booleanArrayOf(true, true, true, viewModel.haveHour?.value?:false,viewModel.haveHour?.value?:false, false))
            .isDialog(true)
            // 默认设置false ，内部实现将DecorView 作为它的父控件。
            .setItemVisibleCount(5)
            // 若设置偶数，实际值会加1（比如设置6，则最大可见条目为7）
            .setLineSpacingMultiplier(2.0f)
            .isAlphaGradient(true)
            .setCancelText("取消")
            // 取消按钮文字
            .setSubmitText("确认")
            .setDate(selectedDate)
            // 确认按钮文字
            .setTitleSize(20)
            // 标题文字大小
            .setOutSideCancelable(true)
            // 点击屏幕，点在控件外部范围时，是否取消显示
            .isCyclic(true)
            // 是否循环滚动
            .setTitleBgColor(-0xa0a0b)
            // 标题背景颜色 Night mode
            .setSubmitColor(
                resources.getColor(R.color.color_59abff)
            ) //确定按钮文字颜色
            .setCancelColor(
                resources.getColor(R.color.color_333333)
            )
            // 取消按钮文字颜色
            .setRangDate(startDate, endDate)
            // 起始终止年月日设定
            // 是否只显示中间选中项的label文字，false则每项item全部都带有label。
            .isCenterLabel(false)
            .build()
        val mDialog = timePicker.dialog
        if (mDialog != null) {
            val params = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                Gravity.BOTTOM
            )
            params.leftMargin = 0
            params.rightMargin = 0
            timePicker.dialogContainerLayout.layoutParams = params
            val dialogWindow = mDialog.window
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(R.style.picker_view_slide_anim)
                dialogWindow.setGravity(Gravity.BOTTOM)
                dialogWindow.setDimAmount(0.3f)
            }
        }
        timePicker?.show()
    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                PictureConfig.CHOOSE_REQUEST ->{
                    val selectList = PictureSelector.obtainMultipleResult(data)
                    if (!selectList.isNullOrEmpty()){
                        val localMedia = selectList[0]
                        val filePath = if(localMedia.realPath.isNullOrBlank()) localMedia.path else localMedia.realPath
                        viewModel.picUrl.set(filePath)
                        if(mPicAdapter?.data.isNullOrEmpty()){
                            mPicAdapter?.setList(selectList)
                        }else{
                            mPicAdapter?.data?.addAll(selectList)
                        }
                        mPicAdapter?.notifyDataSetChanged()
                    }
                }
                else -> {
                }
            }
        }
    }


    fun stampToTime(stamp: String): String {
        var  type=""
        if(viewModel.haveHour?.value !!){
            type = "yyyy-MM-dd HH:mm"
        }else{
            type = "yyyy-MM-dd"
        }
        val simpleDateFormat = SimpleDateFormat(type)
        val lt: Long = stamp.toLong()
        val date = Date(lt)
        return simpleDateFormat.format(date)
    }

    /**
     * 点击添加图片
     */
    override fun onAddPicClick() {
        jumpAlbum()
    }

    /**
     * 跳转相册
     */
    private fun jumpAlbum() {
        PictureSelector
            .create(this)
            // 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
            .openGallery(PictureMimeType.ofImage())
            // 外部传入图片加载引擎，必传项
            .imageEngine(GlideEngine.createGlideEngine())
            // 最大图片选择数量
            .maxSelectNum(picMaxNumber)
            // 最小选择数量
            .minSelectNum(1)
            // 每行显示个数
            .imageSpanCount(4)
            // 设置相册Activity方向，不设置默认使用系统
            //            .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            // 是否显示原图控制按钮，如果设置为true则用户可以自由选择是否使用原图，压缩、裁剪功能将会失效
            .isOriginalImageControl(true)
            // 多选 or 单选
            .selectionMode(PictureConfig.MULTIPLE)
            // 单选模式下是否直接返回，PictureConfig.SINGLE模式下有效
            .isSingleDirectReturn(false)
            // 是否可预览图片
            .isPreviewImage(true)
            // 是否可预览视频
            .isPreviewVideo(true)
            // 是否可播放音频
            .enablePreviewAudio(true)
            // 是否显示拍照按钮
            .isCamera(true)
            // 图片列表点击 缩放效果 默认true
            .isZoomAnim(true)
            // 图片压缩后输出质量 0~ 100
            .compressQuality(80)
            //同步false或异步true 压缩 默认同步
            .synOrAsy(true)
            // 是否显示gif图片
            .isGif(false)
            // 裁剪输出质量 默认100
            .cutOutQuality(90)
            // 是否裁剪
            //            .isEnableCrop(true)
            // 小于100kb的图片不压缩
            .minimumCompressSize(100)
            // 是否传入已选图片
            .selectionData(null)
            //结果回调onActivityResult code
            .forResult(PictureConfig.CHOOSE_REQUEST)
    }
}