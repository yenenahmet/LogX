package com.example.logx.view.takeaphoto

import android.Manifest
import android.net.Uri
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.util.Size
import android.view.LayoutInflater
import android.view.Surface
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.logx.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class TakeAPhotoFragment : Fragment() {

    companion object {
        private const val TAG = "CameraXBasic"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    private lateinit var imageCapture: ImageCapture
    private lateinit var outputDirectory: File


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        outputDirectory = getOutputDirectory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_take_a_photo, container, false)
        startCamera(view)

        val rlTakeAPhoto = view.findViewById<RelativeLayout>(R.id.rlTakeAPhoto)
        rlTakeAPhoto.setOnClickListener {
            takePhoto()
        }
        return view
    }


    private fun startCamera(view: View) {
        val viewFinder = view.findViewById<PreviewView>(R.id.viewFinder)
        if(context==null){
            return
        }
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener(Runnable {
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val metrics = DisplayMetrics().also { viewFinder.display.getRealMetrics(it) }
            val screenSize = Size(metrics.widthPixels, metrics.heightPixels)

            val preview = Preview.Builder()
                .setTargetResolution(screenSize)
                .build()
                .also {
                    it.setSurfaceProvider(viewFinder.surfaceProvider)
                }


            imageCapture = ImageCapture.Builder()
                .setTargetResolution(screenSize)
                .setTargetRotation(Surface.ROTATION_0)
                .build()

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, CameraSelector.DEFAULT_BACK_CAMERA, preview, imageCapture
                )

            } catch (exc: Exception) {
                Log.e("Camera X", "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(activity))
    }

    private fun takePhoto() {
        // Get a stable reference of the modifiable image capture use case
        val imageCapture = imageCapture

        // Create time-stamped output file to hold the image
        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(
                FILENAME_FORMAT, Locale.US
            ).format(System.currentTimeMillis()) + ".jpg"
        )

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        // Set up image capture listener, which is triggered after photo has
        // been taken
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(activity),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e("CAmeraX", "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    val msg = "Photo capture succeeded: $savedUri"
                    //Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                    Log.d("CameraX", msg)
                    val bundle = Bundle().apply {
                        putString("URI", savedUri.toString())
                    }
                    findNavController().navigate(
                        R.id.action_takeAPhotoFragment_to_cropFragment,
                        bundle
                    )

                }
            })
    }

    private fun getOutputDirectory(): File {
        val mediaDir = requireActivity().externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else requireActivity().filesDir
    }

}