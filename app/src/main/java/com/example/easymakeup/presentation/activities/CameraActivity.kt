package com.example.easymakeup.presentation.activities

import android.app.Activity
import android.content.Intent
import android.hardware.Camera
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.appcompat.app.AppCompatActivity
import com.example.easymakeup.databinding.ActivityCameraBinding
import java.io.IOException

@Suppress("DEPRECATION")
class CameraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraBinding
    private lateinit var surfaceHolder: SurfaceHolder
    private lateinit var surfaceView: SurfaceView
    private lateinit var camera: Camera

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        surfaceView = binding.surfaceView
        surfaceHolder = surfaceView.holder
        surfaceHolder.addCallback(surfaceCallback)
        binding.btnCapture.setOnClickListener {
            camera.takePicture(null, null, cameraCallback)
        }
    }

    private val surfaceCallback = object : SurfaceHolder.Callback {
        override fun surfaceCreated(holder: SurfaceHolder) {
            binding.surfaceView.setWillNotDraw(false)
            camera = Camera.open()
            camera.setDisplayOrientation(90)
            try {
                camera.setPreviewDisplay(surfaceHolder)
                camera.startPreview()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
            if (surfaceHolder.surface == null) return
            camera.stopPreview()
            try {
                camera.setPreviewDisplay(surfaceHolder)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            camera.startPreview()
        }

        override fun surfaceDestroyed(holder: SurfaceHolder) {
            camera.stopPreview()
            camera.release()
        }
    }

    private val cameraCallback = Camera.PictureCallback { data, camera ->
        val resultIntent = Intent().apply {
            putExtra("data", data)
        }
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }
}