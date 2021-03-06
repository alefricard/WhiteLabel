package br.com.ricardo.whitelabel.ui.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.ricardo.whitelabel.R
import br.com.ricardo.whitelabel.databinding.FragmentProductsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsFragment : Fragment() {

    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProductsViewModel by viewModels()

    private val productsAdapter = ProductsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        setListeners()
        observeVMEvents()
        viewModel.getProducts()
    }

    private fun setRecyclerView(){
        binding.recyclerProduct.run {
            setHasFixedSize(true)
            adapter = productsAdapter
        }
    }

    private fun setListeners(){
        binding.fabAdd.setOnClickListener {
        findNavController().navigate(R.id.action_productsFragment_to_addProductFragment)
        }
    }

    private fun observeVMEvents(){
        viewModel.productsData.observe(viewLifecycleOwner){ products ->
            productsAdapter.submitList(products)
        }

        viewModel.addButtonVisibilityData.observe(viewLifecycleOwner){ visibility ->
            binding.fabAdd.visibility = visibility
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()

    }
}