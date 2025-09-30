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

// This is the main screen that shows a list of employees.
// It uses AppCompatActivity to work on older phones too
class EmployeeListActivity : AppCompatActivity() {

    // A tool to connect our layout file (XML) to this code, set up later.
    //--------------------------------------------------------------------------
    private lateinit var binding: ActivityEmployeeListBinding
    //--------------------------------------------------------------------------


    // A helper that manages employee data, set up when the app starts.
    //--------------------------------------------------------------------------
    private lateinit var viewModel: EmployeeViewModel
    //--------------------------------------------------------------------------


    // A tool to show the employee list in a scrollable view, set up later.
    //--------------------------------------------------------------------------
    private lateinit var adapter: EmployeeAdapter
    //--------------------------------------------------------------------------


    // A tool to let users pull down to refresh the list, set up later.
    //--------------------------------------------------------------------------
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    //--------------------------------------------------------------------------


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployeeListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Makes a helper to get employee data from the internet.
        //--------------------------------------------------------------------------
        val repository = EmployeeRepository(this)
        //--------------------------------------------------------------------------

        // Creates a factory to help set up the ViewModel with the repository.
        //--------------------------------------------------------------------------
        val viewModelFactory = EmployeeViewModelFactory(repository)
        //--------------------------------------------------------------------------

        // Sets up the ViewModel to handle employee data.
        //--------------------------------------------------------------------------
        viewModel = ViewModelProvider(this, viewModelFactory)[EmployeeViewModel::class.java]
        //--------------------------------------------------------------------------

        // Sets up the adapter to show employees and handle clicks to see details.
        //--------------------------------------------------------------------------
        adapter = EmployeeAdapter { employee ->
            showEmployeeDetails(employee)
        }
        //--------------------------------------------------------------------------


        // Arranges the employee list to scroll up and down.
        //--------------------------------------------------------------------------
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        //--------------------------------------------------------------------------

        // Sets up the pull-to-refresh tool from the layout.
        // Make sure the layout has a part called swipeRefreshLayout.
        //--------------------------------------------------------------------------
        swipeRefreshLayout =
            findViewById(R.id.swipeRefreshLayout) // Ensure this ID exists in layout
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.fetchEmployees() // Trigger retry on pull
        }
        //--------------------------------------------------------------------------


        // Watches for changes in employee data and updates the screen.
        //--------------------------------------------------------------------------
        viewModel.employees.observe(this) { resource ->
            when (resource) {
                is ApiThreeState.Loading -> {
                    // Shows a spinning circle while data is loading.
                    binding.progressBar.visibility = View.VISIBLE

                    binding.errorText.visibility = View.GONE

                    // Shows the refresh spinning circle.
                    swipeRefreshLayout.isRefreshing = true
                }

                is ApiThreeState.Success -> {
                    // Hides the spinning circle and error message when data loads.
                    binding.progressBar.visibility = View.GONE

                    binding.errorText.visibility = View.GONE

                    // Updates the list with new employee data.
                    adapter.submitList(resource.data.employees)

                    // Stops the refresh spinning circle.
                    swipeRefreshLayout.isRefreshing = false
                }

                is ApiThreeState.Error -> {
                    // Hides the spinning circle and shows an error message.
                    binding.progressBar.visibility = View.GONE
                    binding.errorText.visibility = View.GONE
                    binding.errorText.text = resource.message

                    // Stops the refresh spinning circle.
                    swipeRefreshLayout.isRefreshing = false // Show refresh indicator

                    // This was a popup to show error code, but it’s turned off now.
                    // Toast.makeText(this, "Error Code: ${resource.code}", Toast.LENGTH_SHORT).show()
                    // Shows a popup if there’s an error, with a retry button.
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
        //--------------------------------------------------------------------------


        // Set up OnBackPressedDispatcher
        //--------------------------------------------------------------------------
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                // Checks if there are other screens (fragments) open.
                if (supportFragmentManager.backStackEntryCount > 0) {
                    binding.fragmentContainer.visibility = View.GONE // Hide fragment container
                    binding.recyclerView.visibility = View.VISIBLE // Show list
                    supportFragmentManager.popBackStack() // Pop fragment
                } else {
                    finish() // Default behavior: exit activity
                }
            }
        })
        //--------------------------------------------------------------------------
    }


    // Shows the details of a selected employee on a new screen.
    //--------------------------------------------------------------------------
    private fun showEmployeeDetails(employee: Employee) {
        val fragment = EmployeeDetailFragment.newInstance(employee)
        binding.recyclerView.visibility = View.GONE // Hide list
        binding.fragmentContainer.visibility = View.VISIBLE // Show container

        // Switches to the new screen and remembers how to go back.
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }
    //--------------------------------------------------------------------------

}