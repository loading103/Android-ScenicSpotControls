package com.daqsoft.module_mine.repository

import com.daqsoft.mvvmfoundation.base.BaseModel
import javax.inject.Inject

/**
 * @ClassName    MineRepository
 * @Description
 * @Author       yuxc
 * @CreateDate   2021/5/7
 */
class MineRepository  @Inject constructor(private val mineApiService: MineApiService) : BaseModel(),MineApiService {
}