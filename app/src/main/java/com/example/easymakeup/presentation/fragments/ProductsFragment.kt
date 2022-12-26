package com.example.easymakeup.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.easymakeup.R
import com.example.easymakeup.databinding.FragmentProductsBinding
import com.example.easymakeup.domain.model.Product
import com.example.easymakeup.presentation.adapters.ProductsAdapter
import com.example.easymakeup.presentation.products.ProductEvent
import com.example.easymakeup.presentation.view_models.ProductsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProductsFragment : Fragment() {

    private lateinit var binding: FragmentProductsBinding

    private val productsViewModel: ProductsViewModel by viewModels()

    private var cacheView: View? = null
    private var checkedProduct: Product? = null

    @Inject
    lateinit var productsAdapter: ProductsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.includeMessageLayout.ivClose.setOnClickListener {
            binding.includeMessageLayout.root.visibility = View.GONE
        }

        binding.includeMessageLayout.tvContent.text = resources.getString(R.string.use_this_product)

        val args: ProductsFragmentArgs by navArgs()
        val selectedColor = args.selectedColor

        binding.includeStepLayout.customStep.setStep(3)
        productsViewModel.setStateChangeListener { state ->
            productsAdapter.differ.submitList(state.products)
        }
        productsViewModel.triggerEvent(ProductEvent.Init)
        productsAdapter.setClickListener { itemView, product ->
            cacheView?.setBackgroundResource(0)
            itemView.setBackgroundResource(R.drawable.border)
            checkedProduct = product
            cacheView = itemView
        }
        binding.run {
            rvProducts.run {
                adapter = productsAdapter
                layoutManager = LinearLayoutManager(activity)
            }
            includeBottomMenu.run {
                tv1.run {
                    text = resources.getString(R.string.back)
                    setOnClickListener {
                        findNavController().popBackStack()
                    }
                }
                tv2.visibility = View.INVISIBLE
            }
            btnBuy.setOnClickListener {
                checkedProduct?.let {
                    Toast.makeText(
                        activity,
                        "${resources.getString(R.string.bought_successfully_1)} ${checkedProduct?.title} ${
                            resources.getString(R.string.bought_successfully_2)
                        }",
                        Toast.LENGTH_SHORT
                    ).show()
                    cacheView?.setBackgroundResource(0)
                    cacheView = null
                    checkedProduct = null
                } ?: Toast.makeText(
                    activity,
                    resources.getString(R.string.choose_product),
                    Toast.LENGTH_SHORT
                ).show()

            }
        }
    }

}