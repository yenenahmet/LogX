package com.example.logx.view.takeaphoto

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.exifinterface.media.ExifInterface
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.logx.R
import com.takusemba.cropme.CropLayout
import com.takusemba.cropme.OnCropListener
import com.yenen.ahmet.basecorelibrary.base.extension.showToast


class CropFragment : Fragment() {

    private lateinit var cropView: CropLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_crop, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cropView = view.findViewById<CropLayout>(R.id.crop_view)
        val uriString = arguments?.getString("URI")
        val uri = Uri.parse(uriString)
        val bitmap = getImage(uri)

        cropView.setBitmap(bitmap)
        cropView.addOnCropListener(cropListener)

        val imgOk = view.findViewById<AppCompatImageView>(R.id.imgOk)
        imgOk.setOnClickListener {
            cropView.crop()
        }

        val imgBack = view.findViewById<AppCompatImageView>(R.id.imgBack)
        imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }


    @SuppressLint("RestrictedApi")
    private fun getImage(uri: Uri):Bitmap {
        val inStreamUri = activity?.contentResolver?.openInputStream(uri)
        val image = BitmapFactory.decodeStream(inStreamUri) // Bitmap

        val exif = ExifInterface(uri.path!!)

        return when(exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)){
            ExifInterface.ORIENTATION_ROTATE_90->{
                rotateImage(image,90)
            }
            ExifInterface.ORIENTATION_ROTATE_180->{
                rotateImage(image,180)
            }
            ExifInterface.ORIENTATION_ROTATE_270->{
                rotateImage(image,270)
            }
            else->{
               return  image
            }
        }
    }

    private fun rotateImage( img :Bitmap,  degree:Int):Bitmap{
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        val rotatedImg =  Bitmap.createBitmap(img, 0, 0, img.width, img.height, matrix, true)
        img.recycle()
        return rotatedImg
    }

    private val cropListener = object : OnCropListener {
        override fun onSuccess(bitmap: Bitmap) {
            val bundle = Bundle().apply {
                putParcelable("BITMAP",bitmap)
            }
            findNavController().navigate(R.id.action_cropFragment_to_takeAPhotoResultFragment,bundle)
        }

        override fun onFailure(e: Exception) {
            showToast(e.toString())
        }
    }

    override fun onDetach() {
        super.onDetach()
        cropView.removeOnCropListener(cropListener)
    }

}