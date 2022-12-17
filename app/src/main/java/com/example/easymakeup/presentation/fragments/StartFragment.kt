package com.example.easymakeup.presentation.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.easymakeup.R
import com.example.easymakeup.databinding.FragmentStartBinding
import com.example.easymakeup.presentation.activities.CameraActivity
import com.karumi.dexter.DexterBuilder
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class StartFragment : Fragment() {

    private lateinit var binding: FragmentStartBinding
    private val imageRequestCode = 100

    @Inject
    lateinit var dexter: DexterBuilder.Permission

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.includeStepLayout.customStep.setStep(0)
        binding.btnTakeSelfie.setOnClickListener {
            dexter.withPermission(Manifest.permission.CAMERA)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                        val cameraIntent = Intent(activity, CameraActivity::class.java)
                        startActivityForResult(cameraIntent, imageRequestCode)
                    }

                    override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        p0: PermissionRequest?,
                        p1: PermissionToken?
                    ) {
                    }
                })
                .check()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == imageRequestCode && resultCode == Activity.RESULT_OK) {
            val byteArray = data?.extras?.get("data") as ByteArray

            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            val rawBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
            bitmap.recycle()

            val matrix = Matrix()
            matrix.postRotate(90f)
            val rotatedBitmap = Bitmap.createBitmap(
                rawBitmap,
                0,
                0,
                rawBitmap.width,
                rawBitmap.height,
                matrix,
                true
            )
            rawBitmap.recycle()

            val bundle = Bundle().apply {
                putParcelable("capturedImage", rotatedBitmap)
            }
            findNavController().navigate(R.id.action_startFragment_to_captureFragment, bundle)
        }
    }
}