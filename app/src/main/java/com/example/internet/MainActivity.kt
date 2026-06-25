package com.example.internet

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.databinding.DataBindingUtil
import com.example.internet.databinding.ActivityMainBinding

import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.example.internet.ui.ProductGridScreen
import androidx.compose.material3.MaterialTheme

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: ProductViewModel by viewModels { ProductViewModel.Factory }
    private val advancedAdapter = AdvancedAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Menggunakan DataBindingUtil sesuai saran untuk stabilitas
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setupRecyclerView()
        setupComposeView()
        observeProducts()
    }

    private fun setupComposeView() {
        binding.composeView.apply {
            // Dispose the Composition when the view's LifecycleOwner is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    // Observe LiveData as State
                    val productsState = viewModel.products.observeAsState(initial = emptyList())
                    val statusState = viewModel.status.observeAsState(initial = ProductApiStatus.LOADING)
                    
                    ProductGridScreen(
                        products = productsState.value,
                        status = statusState.value,
                        retryAction = viewModel::retry
                    )
                }
            }
        }
    }

    private fun setupRecyclerView() {
        val gridLayoutManager = GridLayoutManager(this, 2)
        
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (advancedAdapter.getItemViewType(position)) {
                    AdvancedAdapter.TYPE_HEADER -> 2
                    else -> 1
                }
            }
        }

        binding.recyclerView.apply {
            layoutManager = gridLayoutManager
            adapter = advancedAdapter
        }
    }

    private fun observeProducts() {
        viewModel.products.observe(this) { products ->
            val listItems = mutableListOf<ListItem>()
            if (products.isNotEmpty()) {
                listItems.add(ListItem.Header("Produk Terpopuler"))
                listItems.addAll(products.map { ListItem.ProductItem(it) })
            }
            advancedAdapter.submitList(listItems)
        }
    }
}
