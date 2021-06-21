package com.daqsoft.module_main.repository

import com.daqsoft.mvvmfoundation.base.BaseModel
import javax.inject.Inject


/**
 * @package name：com.daqsoft.module_main.repository
 * @date 5/11/2020 下午 5:25
 * @author zp
 * @describe
 */
class MainRepository @Inject constructor(private val mainApiService: MainApiService) : BaseModel(),MainApiService {

}