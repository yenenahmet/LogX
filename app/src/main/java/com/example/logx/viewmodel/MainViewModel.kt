package com.example.logx.viewmodel

import com.example.logx.adapter.LoadTypeSpinnerAdapter
import com.example.logx.adapter.LoadingTypeSpinnerAdapter
import com.yenen.ahmet.basecorelibrary.base.viewmodel.BaseViewModel

class MainViewModel : BaseViewModel() {

    val loadTypeSpinnerAdapter = LoadTypeSpinnerAdapter()
    val loadingTypeSpinnerAdapter = LoadingTypeSpinnerAdapter()

}