package com.example.logx.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatImageView
import com.ablanco.zoomy.Zoomy
import com.bumptech.glide.Glide
import com.example.logx.R

class ImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        val img = findViewById<AppCompatImageView>(R.id.img)
        Glide.with(this).load(R.mipmap.ppp).into(img)

        val builder = Zoomy.Builder(this).target(img)
        builder.register()

        val imgClose = findViewById<AppCompatImageView>(R.id.imgClose)
        imgClose.setOnClickListener {
            finish()
        }
    }
}