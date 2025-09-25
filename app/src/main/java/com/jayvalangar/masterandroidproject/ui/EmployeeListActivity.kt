package com.jayvalangar.masterandroidproject.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.jayvalangar.masterandroidproject.R
import com.jayvalangar.masterandroidproject.adapter.EmployeeAdapter
import com.jayvalangar.masterandroidproject.databinding.ActivityEmployeeListBinding
import com.jayvalangar.masterandroidproject.model.Employee
import com.jayvalangar.masterandroidproject.repository.EmployeeRepository
import com.jayvalangar.masterandroidproject.util.ApiThreeState
import com.jayvalangar.masterandroidproject.viewmodel.employee.EmployeeViewModel
import com.jayvalangar.masterandroidproject.viewmodel.employee.EmployeeViewModelFactory

class EmployeeListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmployeeListBinding
    private lateinit var viewModel: EmployeeViewModel
    private lateinit var adapter: EmployeeAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployeeListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = EmployeeRepository(this)
        val viewModelFactory = EmployeeViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[EmployeeViewModel::class.java]

        adapter = EmployeeAdapter { employee ->
            showEmployeeDetails(employee)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        // Initialize SwipeRefreshLayout
        swipeRefreshLayout =
            findViewById(R.id.swipeRefreshLayout) // Ensure this ID exists in layout
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.fetchEmployees() // Trigger retry on pull
        }
        viewModel.employees.observe(this) { resource ->
            when (resource) {
                is ApiThreeState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.errorText.visibility = View.GONE
                    swipeRefreshLayout.isRefreshing = true // Show refresh indicator
                }

                is ApiThreeState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.errorText.visibility = View.GONE
                    adapter.submitList(resource.data.employees)
                    swipeRefreshLayout.isRefreshing = false // Show refresh indicator
                }

                is ApiThreeState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.errorText.visibility = View.GONE
                    binding.errorText.text = resource.message
                    swipeRefreshLayout.isRefreshing = false // Show refresh indicator
//                    Toast.makeText(this, "Error Code: ${resource.code}", Toast.LENGTH_SHORT).show()
                    CommonDialog.show(
                        context = this,
                        iconRes = R.drawable.ic_launcher_foreground,
                        title = "Error : ${resource.code}",
                        text = "Failed to load employee list. Retry? ${resource.message}",
                        okButtonText = "Retry",
                        cancelButtonText = null,
                        iconBgColor = Color.RED,
                        isCancelable = true,
                        onOkClick = { viewModel.fetchEmployees() },
                        onCancelClick = { }
                    )
                }
            }
        }

        // Set up OnBackPressedDispatcher
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (supportFragmentManager.backStackEntryCount > 0) {
                    binding.fragmentContainer.visibility = View.GONE // Hide fragment container
                    binding.recyclerView.visibility = View.VISIBLE // Show list
                    supportFragmentManager.popBackStack() // Pop fragment
                } else {
                    finish() // Default behavior: exit activity
                }
            }
        })
    }

    private fun showEmployeeDetails(employee: Employee) {
        val fragment = EmployeeDetailFragment.newInstance(employee)
        binding.recyclerView.visibility = View.GONE // Hide list
        binding.fragmentContainer.visibility = View.VISIBLE // Show container
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }
}