package com.sameera.displaysocial.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageButton
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.camera.core.*
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.example.xploreexercise.fragments.search.CameraViewModel
import com.sameera.displaysocial.activity.R
import com.sameera.displaysocial.utils.FileUtils
import kotlinx.android.synthetic.main.fragment_camera.view.*
import java.io.File


/**
 * A placeholder fragment containing a simple view.
 */
class CameraFragment : Fragment() {

    private lateinit var cameraViewModel: CameraViewModel
    private lateinit var viewFinder: TextureView
    private lateinit var captureButton: ImageButton
    private lateinit var recordButton: ImageButton
    private lateinit var videoCapture: VideoCapture
    private lateinit var imageCapture: ImageCapture
    private lateinit var preview : Preview

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cameraViewModel = ViewModelProvider(this).get(CameraViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_camera, container, false)
        viewFinder = root.view_finder
        captureButton = root.capture_button
        recordButton = root.record_button
        if (!isPermissionGranted()) {
            makePermissionRequest()
        }
        viewFinder.post { startCameraForVideo() }
        processVideoCapture()
        processImageClick()
        return root
    }

    @SuppressLint("RestrictedApi", "ClickableViewAccessibility")
    private fun processVideoCapture() {
        val file = FileUtils.getVideoPath(requireContext())
        recordButton.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                startCameraForVideo()
                videoCapture.startRecording(file, object : VideoCapture.OnVideoSavedListener {
                    override fun onVideoSaved(file: File?) {
                        Log.i(TAG, "Video File : $file")
                        Toast.makeText(requireContext(), "video saved at : $file" ,Toast.LENGTH_SHORT).show()                    }

                    override fun onError(
                        useCaseError: VideoCapture.UseCaseError?,
                        message: String?,
                        cause: Throwable?
                    ) {
                        Log.i(TAG, "Video Error: $message")
                    }
                })

            } else if (event.action == MotionEvent.ACTION_UP) {
                videoCapture.stopRecording()
                Log.i(TAG, "Video File stopped")
            }
            false
        }
    }

    private fun processImageClick() {
        captureButton.setOnClickListener {
            startCameraForImage()
            val file = FileUtils.getImagePath(requireContext())
            imageCapture.takePicture(file, object : ImageCapture.OnImageSavedListener {
                override fun onImageSaved(file: File) {
                    val msg = "Pic captured at $file"
                    Log.i(TAG, msg)
                    Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
                }

                override fun onError(
                    useCaseError: ImageCapture.UseCaseError,
                    message: String,
                    @Nullable cause: Throwable?
                ) {
                    val msg = "Pic capture failed : $message"
                    Log.i(TAG, msg)
                    Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
                    cause?.printStackTrace()
                }
            })
        }
    }

    private fun isPermissionGranted(): Boolean {
        for (permission in REQUIRED_PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(), permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    private fun makePermissionRequest() {
        ActivityCompat.requestPermissions(
            requireActivity(), REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
        )
    }

    private fun startCameraForImage() {
        CameraX.unbindAll()
        val previewConfig = PreviewConfig.Builder().build()
        preview = Preview(previewConfig)
        setImageCaptureConfig()
        preview.setOnPreviewOutputUpdateListener {
            viewFinder.surfaceTexture = it.surfaceTexture
        }
        // Bind use cases to lifecycle
        CameraX.bindToLifecycle(this, preview, imageCapture)
    }
    private fun startCameraForVideo() {
        CameraX.unbindAll()
        val previewConfig = PreviewConfig.Builder().build()
        preview = Preview(previewConfig)
        setVideoConfigCapture()
        preview.setOnPreviewOutputUpdateListener {
            viewFinder.surfaceTexture = it.surfaceTexture
        }
        // Bind use cases to lifecycle
        CameraX.bindToLifecycle(this, preview, videoCapture)
    }

    private fun setImageCaptureConfig() {
        val imageCaptureConfig =
            ImageCaptureConfig.Builder().setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
                .setTargetRotation(
                    requireActivity().getWindowManager().getDefaultDisplay().getRotation()
                ).build()
        imageCapture = ImageCapture(imageCaptureConfig)
    }

    @SuppressLint("RestrictedApi")
    private fun setVideoConfigCapture() {
        val videoCaptureConfig = VideoCaptureConfig.Builder().apply {
            setTargetRotation(viewFinder.display.rotation)
        }.build()
        videoCapture = VideoCapture(videoCaptureConfig)
        Log.e(TAG, "video capture initialized")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (isPermissionGranted()) {
                viewFinder.post { startCameraForImage() }
            } else {
                Toast.makeText(
                    requireContext(), "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //startCamera()
        } else {
            // fragment is no longer visible
        }
    }

    companion object {
        private const val REQUEST_CODE_PERMISSIONS: Int = 10
        private val REQUIRED_PERMISSIONS =
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        private val TAG = CameraFragment::class.java.simpleName

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): CameraFragment {
            return CameraFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}