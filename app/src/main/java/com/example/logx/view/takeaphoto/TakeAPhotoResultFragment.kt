package com.example.logx.view.takeaphoto

import android.app.Activity
import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.logx.R
import com.example.logx.view.MainActivity
import com.yenen.ahmet.basecorelibrary.base.extension.showToast
import java.io.File

class TakeAPhotoResultFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_take_a_photo_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val llSend = view.findViewById<LinearLayout>(R.id.llSend)
        llSend.setOnClickListener {
            activity?.setResult(Activity.RESULT_OK)
            activity?.finish()
        }
        val cvClose = view.findViewById<CardView>(R.id.cvClose)
        cvClose.setOnClickListener {
            activity?.setResult(Activity.RESULT_CANCELED)
            activity?.finish()
        }
        val llRefresh = view.findViewById<LinearLayout>(R.id.llRefresh)
        llRefresh.setOnClickListener {
            findNavController().popBackStack()
            findNavController().popBackStack()
        }
        val img = view.findViewById<AppCompatImageView>(R.id.img)
        //val bitmap = arguments?.getParcelable<Bitmap>("BITMAP")
        Glide.with(this).load(MainActivity.tempFile)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(img)
    }





}