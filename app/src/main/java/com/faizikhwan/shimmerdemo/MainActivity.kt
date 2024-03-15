package com.faizikhwan.shimmerdemo

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.faizikhwan.shimmerdemo.databinding.ActivityMainBinding
import com.faizikhwan.shimmerdemo.ui.theme.ShimmerDemoTheme

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private var adapter: UserListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindView()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        initRecyclerView()
        observeViewModel()
        viewModel.getUserList()
    }

    private fun observeViewModel() {
        viewModel.apply {
            userListObserver.observe(this@MainActivity, Observer { status ->
                when (status) {
                    is Resource.Loading -> {
                        binding.shimmerLayout.startShimmer()
                    }
                    is Resource.Success -> {
                        status.data?.let {
                            adapter?.swapData(it)
                            showRecyclerView()
                        }
                    }
                    else -> {

                    }
                }
            })
        }
    }

    private fun initRecyclerView() {
        adapter = UserListAdapter(
            this@MainActivity,
            emptyList()
        )
        binding.userListRecyclerview.layoutManager = LinearLayoutManager(
            this@MainActivity,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.userListRecyclerview.adapter = adapter
        binding.userListRecyclerview.setHasFixedSize(true)
    }

    private fun bindView() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun showRecyclerView() {
        binding.shimmerLayout.apply {
            stopShimmer()
            visibility = View.GONE
        }
        binding.userListRecyclerview.visibility = View.VISIBLE
    }

}