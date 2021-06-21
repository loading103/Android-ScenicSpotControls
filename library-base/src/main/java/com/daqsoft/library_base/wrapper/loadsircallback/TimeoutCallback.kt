package com.daqsoft.library_base.wrapper.loadsircallback

import com.daqsoft.library_base.R
import com.kingja.loadsir.callback.Callback

class TimeoutCallback : Callback() {
    override fun onCreateView(): Int {
        return R.layout.layout_timeout
    }

}