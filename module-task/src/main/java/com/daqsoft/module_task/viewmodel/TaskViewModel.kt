package com.daqsoft.module_task.viewmodel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import com.daqsoft.module_task.repository.TaskRepository
import com.daqsoft.mvvmfoundation.toolbar.ToolbarViewModel

class TaskViewModel : ToolbarViewModel<TaskRepository> {

    @ViewModelInject
    constructor(application: Application,taskRepository : TaskRepository):super(application,taskRepository)
}