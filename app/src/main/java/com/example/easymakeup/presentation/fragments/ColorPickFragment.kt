package com.example.easymakeup.presentation.fragments

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.RequestManager
import com.example.easymakeup.R
import com.example.easymakeup.databinding.FragmentColorPickBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ColorPickFragment : Fragment() {

    private lateinit var binding: FragmentColorPickBinding

    private var selectedColor = 0

    @Inject
    lateinit var glide: RequestManager

    private var bitmap: Bitmap? = null
    private var pixelColor = Color.TRANSPARENT

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentColorPickBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility", "SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.includeStepLayout.customStep.setStep(2)
        val args: ColorPickFragmentArgs by navArgs()
        val capturedImage = args.capturedImage
        glide.load(capturedImage).into(binding.ivCapturedImage)

        binding.ivCapturedImage.setOnTouchListener { v, event ->

            val viewX = event.x
            val viewY = event.y

            val viewWidth = binding.ivCapturedImage.width
            val viewHeight = binding.ivCapturedImage.height

            bitmap = (binding.ivCapturedImage.drawable as? BitmapDrawable)?.bitmap

            val bitmapWidth = bitmap?.width
            val bitmapHeight = bitmap?.height

            val imageX = viewX * bitmapWidth!! / viewWidth
            val imageY = viewY * bitmapHeight!! / viewHeight

            pixelColor = bitmap?.getPixel(imageX.toInt(), imageY.toInt()) ?: 0

            binding.colorView.setColor(pixelColor)
            binding.sliderView.setColor(pixelColor)

            binding.sliderView.visibility = View.VISIBLE
            binding.colorView.visibility = View.VISIBLE

            false
        }

        binding.sliderView.setMinusClickListener { color ->
            selectedColor = color
        }

        binding.sliderView.setPlusClickListener { color ->
            selectedColor = color
            Toast.makeText(activity, "working", Toast.LENGTH_SHORT).show()
        }

        binding.includeBottomMenu.run {
            tv1.text = resources.getString(R.string.back)
            tv2.text = "${resources.getString(R.string.next_step)} >"
            tv1.setOnClickListener {
                findNavController().popBackStack()
            }
            tv2.setOnClickListener {
                val bundle = Bundle().apply {
                    putInt("selectedColor", selectedColor)
                }
                findNavController().navigate(
                    R.id.action_colorPickFragment_to_productsFragment,
                    bundle
                )
            }
        }
    }
}