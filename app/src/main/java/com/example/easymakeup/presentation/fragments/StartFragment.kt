@file:Suppress("DEPRECATION")

package com.example.easymakeup.presentation.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.easymakeup.R
import com.example.easymakeup.databinding.FragmentStartBinding
import com.example.easymakeup.presentation.activities.CameraActivity
import com.example.easymakeup.util.BitmapParser
import com.example.easymakeup.util.Resource
import com.karumi.dexter.DexterBuilder
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class StartFragment : Fragment() {

    private lateinit var binding: FragmentStartBinding
    private val imageRequestCode = 100

    @Inject
    lateinit var dexter: DexterBuilder.Permission

    private var job: Job? = null

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

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == imageRequestCode && resultCode == Activity.RESULT_OK) {
            val byteArray = data?.extras?.get("data") as ByteArray
            job?.cancel()
            job = lifecycleScope.launch {
                BitmapParser.parseByteArray(byteArray).collect { result ->
                    when (result) {
                        is Resource.Loading -> Unit
                        is Resource.Success -> {
                            result.data?.let { bitmap ->
                                val bundle = Bundle().apply {
                                    putParcelable("capturedImage", bitmap)
                                }
                                findNavController().navigate(
                                    R.id.action_startFragment_to_captureFragment,
                                    bundle
                                )
                            }
                        }
                        is Resource.Error -> Unit
                    }
                }
            }
        }
    }
}