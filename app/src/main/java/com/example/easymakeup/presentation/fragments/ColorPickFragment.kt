package com.example.easymakeup.presentation.fragments

import android.os.Bundle
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

@AndroidEntryPoint
class ColorPickFragment : Fragment() {

    private lateinit var binding: FragmentColorPickBinding

    @Inject
    lateinit var glide: RequestManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentColorPickBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: ColorPickFragmentArgs by navArgs()
        val capturedImage = args.capturedImage
        glide.load(capturedImage).into(binding.ivCapturedImage)

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