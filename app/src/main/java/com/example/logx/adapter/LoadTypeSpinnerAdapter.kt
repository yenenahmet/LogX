package com.example.logx.adapter

import com.example.logx.R
import com.example.logx.databinding.ItemLoadTypeBinding
import com.yenen.ahmet.basecorelibrary.base.adapter.BaseSpinnerAdapter

class LoadTypeSpinnerAdapter:BaseSpinnerAdapter<String,ItemLoadTypeBinding,ItemLoadTypeBinding>(
    mutableListOf("","",""), R.layout.item_load_type,R.layout.item_load_type
) {
    override fun onDropDownViewSetModel(binding: ItemLoadTypeBinding, item: String) {

    }

    override fun onViewSetModel(binding: ItemLoadTypeBinding, item: String) {

    }

}