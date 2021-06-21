package com.daqsoft.module_statistics.viewmodel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import com.daqsoft.module_statistics.repository.StatisticsRepository
import com.daqsoft.mvvmfoundation.toolbar.ToolbarViewModel

class StatisticsViewModel : ToolbarViewModel<StatisticsRepository> {

    @ViewModelInject
    constructor(application: Application, statisticsRepository : StatisticsRepository):super(application,statisticsRepository)
}