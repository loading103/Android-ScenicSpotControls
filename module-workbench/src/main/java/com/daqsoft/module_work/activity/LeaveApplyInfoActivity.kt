package com.daqsoft.module_work.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.daqsoft.library_base.base.AppBaseActivity
import com.daqsoft.library_base.router.ARouterPath
import com.daqsoft.library_base.utils.GlideEngine
import com.daqsoft.library_common.adapter.GridImageAdapter
import com.daqsoft.library_common.widget.FullyGridLayoutManager
import com.daqsoft.module_work.BR
import com.daqsoft.module_work.R
import com.daqsoft.module_work.databinding.ActivityLeaveApplyInfoBinding
import com.daqsoft.module_work.viewmodel.LeaveApplyInfoViewModel
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import dagger.hilt.android.AndroidEntryPoint

/**
 * @ClassName    LeaveApplyInfoActivity
 * @Description
 * @Author       yuxc
 * @CreateDate   2021/5/10
 */
@AndroidEntryPoint
@Route(path = ARouterPath.Workbench.ADD_LEAVE_APPLY_INFO)
class LeaveApplyInfoActivity : AppBaseActivity<ActivityLeaveApplyInfoBinding, LeaveApplyInfoViewModel>(){

    var mPicAdapter : GridImageAdapter?=null

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_leave_apply_info
    }

    override fun initViewModel(): LeaveApplyInfoViewModel? {
        return viewModels<LeaveApplyInfoViewModel>().value
    }


    override fun initView() {
        super.initView()
        mPicAdapter= GridImageAdapter(this, null)
        mPicAdapter?.setShowdelicon(false)
        binding.picRecycleView.apply {
            val spanCount = 4
            layoutManager = FullyGridLayoutManager(context, spanCount, GridLayoutManager.VERTICAL, false)
            adapter=mPicAdapter
        }



        var icons:MutableList<String> = mutableListOf(
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fyouimg1.c-ctrip.com%2Ftarget%2Ftg%2F035%2F063%2F726%2F3ea4031f045945e1843ae5156749d64c.jpg&refer=http%3A%2F%2Fyouimg1.c-ctrip.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1623232271&t=40e7a6166bd798dc5b13dc57594fcd08",
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.juimg.com%2Ftuku%2Fyulantu%2F140703%2F330746-140f301555752.jpg&refer=http%3A%2F%2Fimg.juimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1623232271&t=ea9709c204a17c6bdb491ef132cff553"
        )

        val map = icons.map {
            val localMedia = LocalMedia()
            localMedia.path = it
            localMedia.mimeType = PictureMimeType.MIME_TYPE_IMAGE
            return@map localMedia
        }

        mPicAdapter?.setList(map)
        mPicAdapter?.setSelectMax(icons.size)
    }
}