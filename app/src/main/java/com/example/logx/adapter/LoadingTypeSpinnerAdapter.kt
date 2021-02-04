package com.example.logx.adapter

import com.example.logx.R
import com.example.logx.databinding.ItemLoadTypeBinding
import com.example.logx.databinding.ItemLoadingTypeBinding
import com.yenen.ahmet.basecorelibrary.base.adapter.BaseSpinnerAdapter

class LoadingTypeSpinnerAdapter :BaseSpinnerAdapter<String,ItemLoadingTypeBinding,ItemLoadingTypeBinding>
    (mutableListOf("","",""), R.layout.item_loading_type,R.layout.item_loading_type){

    override fun onDropDownViewSetModel(binding: ItemLoadingTypeBinding, item: String) {

    }

    override fun onViewSetModel(binding: ItemLoadingTypeBinding, item: String) {

    }
}