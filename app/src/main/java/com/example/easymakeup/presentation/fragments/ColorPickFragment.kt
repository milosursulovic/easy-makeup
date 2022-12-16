package com.example.easymakeup.presentation.fragments

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.RequestManager
import com.example.easymakeup.R
import com.example.easymakeup.databinding.FragmentColorPickBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val TAG = "debugTag"

@AndroidEntryPoint
class ColorPickFragment : Fragment() {

    private lateinit var binding: FragmentColorPickBinding

    @Inject
    lateinit var glide: RequestManager

    private var bitmap: Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentColorPickBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.includeStepLayout.customStep.setStep(2)
        val args: ColorPickFragmentArgs by navArgs()
        val capturedImage = args.capturedImage
        glide.load(capturedImage).into(binding.ivCapturedImage)

        binding.ivCapturedImage.setOnTouchListener { v, event ->

            val viewX = event.x;
            val viewY = event.y;

            val viewWidth = binding.ivCapturedImage.width;
            val viewHeight = binding.ivCapturedImage.height;

            bitmap = (binding.ivCapturedImage.drawable as? BitmapDrawable)?.bitmap;

            val bitmapWidth = bitmap?.width;
            val bitmapHeight = bitmap?.height;

            val imageX = viewX * bitmapWidth!! / viewWidth
            val imageY = viewY * bitmapHeight!! / viewHeight

            val pixel = bitmap?.getPixel(imageX.toInt(), imageY.toInt())

            pixel?.let {
                Log.d(TAG, "color red: ${Color.red(it)}")
                Log.d(TAG, "color green: ${Color.green(it)}")
                Log.d(TAG, "color blue: ${Color.blue(it)}")
            }

            false
        }

        binding.includeBottomMenu.run {
            tv1.text = resources.getString(R.string.back)
            tv2.text = "${resources.getString(R.string.next_step)} >"
            tv1.setOnClickListener {
                findNavController().popBackStack()
            }
            tv2.setOnClickListener {
                val bundle = Bundle().apply {
                    putParcelable("capturedImage", capturedImage)
                }
                findNavController().navigate(
                    R.id.action_colorPickFragment_to_productsFragment,
                    bundle
                )
            }
        }
    }
}