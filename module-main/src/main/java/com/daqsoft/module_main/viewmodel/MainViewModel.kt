package com.daqsoft.module_main.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import com.daqsoft.module_main.repository.MainRepository
import com.daqsoft.mvvmfoundation.base.BaseViewModel
import com.daqsoft.mvvmfoundation.binding.command.BindingAction
import com.daqsoft.mvvmfoundation.binding.command.BindingCommand


/**
 * @package name：com.daqsoft.module_main.viewmodel
 * @date 28/10/2020 下午 3:24
 * @author zp
 * @describe
 */

class MainViewModel : BaseViewModel<MainRepository> {

    @ViewModelInject
    constructor(application: Application,mainRepository:MainRepository):super(application,mainRepository)

}