package com.example.logx.custom

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.SupportMapFragment

class CustomGoogleMapFragment  : SupportMapFragment() {
    private var mListener: OnTouchListener? = null

    override fun onCreateView(p0: LayoutInflater, p1: ViewGroup?, p2: Bundle?): View? {
        val layout = super.onCreateView(layoutInflater, p1, p2)

        val frameLayout = TouchableWrapper(requireActivity())

        frameLayout.setBackgroundColor(ContextCompat.getColor(requireActivity(),android.R.color.transparent))

        (layout as ViewGroup).addView(
            frameLayout,
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )

        return layout
    }

    inner class TouchableWrapper(context: Context) : FrameLayout(context) {
        override fun dispatchTouchEvent(event: MotionEvent): Boolean {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> mListener?.onTouch()
                MotionEvent.ACTION_UP -> mListener?.onTouch()
            }
            return super.dispatchTouchEvent(event)
        }
    }

    interface OnTouchListener {
        fun onTouch()
    }

    fun setListener(listener: OnTouchListener) {
        this.mListener = listener
    }

    fun unBind() {
        mListener = null
    }
}
