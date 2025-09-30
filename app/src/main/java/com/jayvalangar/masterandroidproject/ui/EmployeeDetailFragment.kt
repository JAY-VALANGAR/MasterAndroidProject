package com.jayvalangar.masterandroidproject.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.jayvalangar.masterandroidproject.R
import com.jayvalangar.masterandroidproject.adapter.CommentAdapter
import com.jayvalangar.masterandroidproject.databinding.FragmentEmployeeDetailBinding
import com.jayvalangar.masterandroidproject.model.Employee
import com.jayvalangar.masterandroidproject.repository.CommentRepository
import com.jayvalangar.masterandroidproject.repository.EmployeeRepository
import com.jayvalangar.masterandroidproject.util.ApiThreeState
import com.jayvalangar.masterandroidproject.viewmodel.comment.CommentViewModel
import com.jayvalangar.masterandroidproject.viewmodel.comment.CommentViewModelFactory
import com.jayvalangar.masterandroidproject.viewmodel.employee.EmployeeViewModel
import com.jayvalangar.masterandroidproject.viewmodel.employee.EmployeeViewModelFactory


// This is a small screen inside the app to show one employee’s details and comments.
class EmployeeDetailFragment : Fragment() {

    // A tool to connect the layout file, set to null when not needed.
    //--------------------------------------------------------------------------
    private var _binding: FragmentEmployeeDetailBinding? = null
    //--------------------------------------------------------------------------

    // A safe way to use the layout tool, only when the screen is active.
    //--------------------------------------------------------------------------
    private val binding get() = _binding!!
    //--------------------------------------------------------------------------


    //--------------------------------------------------------------------------
    private lateinit var employee: Employee
    //--------------------------------------------------------------------------


    //--------------------------------------------------------------------------
    private lateinit var employeeViewModel: EmployeeViewModel
    //--------------------------------------------------------------------------


    //--------------------------------------------------------------------------
    private lateinit var commentViewModel: CommentViewModel
    //--------------------------------------------------------------------------


    //--------------------------------------------------------------------------
    private lateinit var commentAdapter: CommentAdapter
    //--------------------------------------------------------------------------


    //--------------------------------------------------------------------------
    private lateinit var swipeRefreshLayout: androidx.swiperefreshlayout.widget.SwipeRefreshLayout
    //--------------------------------------------------------------------------


    //--------------------------------------------------------------------------
    companion object {
        private const val ARG_EMPLOYEE = "employee"

        fun newInstance(employee: Employee): EmployeeDetailFragment {
            val fragment = EmployeeDetailFragment()
            val args = Bundle()
            args.putParcelable(ARG_EMPLOYEE, employee)
            fragment.arguments = args
            return fragment
        }
    }
    //--------------------------------------------------------------------------


    //--------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Gets the employee info from the bag, if there is one.
        arguments?.let {
            employee = it.getParcelable(ARG_EMPLOYEE)!!
        }
        val employeeRepository = EmployeeRepository(requireContext())
        val commentRepository = CommentRepository(requireContext())
        val employeeViewModelFactory = EmployeeViewModelFactory(employeeRepository)
        val commentViewModelFactory = CommentViewModelFactory(commentRepository)

        // Gets the employee data helper ready.
        employeeViewModel =
            ViewModelProvider(this, employeeViewModelFactory)[EmployeeViewModel::class.java]
        // Gets the comment data helper ready.
        commentViewModel =
            ViewModelProvider(this, commentViewModelFactory)[CommentViewModel::class.java]
    }
    //--------------------------------------------------------------------------



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmployeeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.profileImage.load(employee.profile)
        binding.nameText.text = "Name: ${employee.name}"
        binding.emailText.text = "Email: ${employee.email}"
        binding.phoneText.text = "Phone: ${employee.phone}"
        binding.departmentText.text = "Department: ${employee.department}"
        binding.designationText.text = "Designation: ${employee.designation}"
        binding.salaryText.text = "Salary: ${employee.salary}"

        // Sets up the tool to show comments in a list.
        commentAdapter = CommentAdapter()

        // Arranges the comment list to scroll up and down.
        binding.commentsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.commentsRecyclerView.adapter = commentAdapter

        // Initialize SwipeRefreshLayout
        swipeRefreshLayout = binding.root.findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {
            commentViewModel.fetchComments(employee.id) // Trigger refresh
        }


        // Watches for changes in comment data and updates the screen.
        //--------------------------------------------------------------------------
        commentViewModel.comments.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is ApiThreeState.Loading -> {
                    // Shows a spinning circle while comments load.
                    binding.commentsProgressBar.visibility = View.VISIBLE
                    binding.commentsErrorText.visibility = View.GONE
                    swipeRefreshLayout.isRefreshing = true // Show refresh indicator
                }

                is ApiThreeState.Success -> {
                    // Hides the spinning circle and error message when comments load.
                    binding.commentsProgressBar.visibility = View.GONE
                    binding.commentsErrorText.visibility = View.GONE
                    // Updates the comment list with new data
                    commentAdapter.submitList(resource.data)
                    // Stops the refresh spinning circle.
                    swipeRefreshLayout.isRefreshing = false
                }

                is ApiThreeState.Error -> {
                    // Hides the spinning circle and shows an error message.
                    binding.commentsProgressBar.visibility = View.GONE
                    binding.commentsErrorText.visibility = View.VISIBLE
                    binding.commentsErrorText.text = resource.message
                    // This was a popup to show error code, but it’s turned off now.
                    // Toast.makeText(context, "Error Code: ${resource.code}", Toast.LENGTH_SHORT).show()
                    // Shows a popup if there’s an error, with a retry button.
                    CommonDialog.show(
                        context = requireContext(),
                        iconRes = R.drawable.ic_launcher_foreground,
                        title = "Error : ${resource.code}",
                        text = "Failed to load comments. Retry? ${resource.message}",
                        okButtonText = "Retry",
                        cancelButtonText = null, // Single button example
                        iconBgColor = Color.RED, // Makes the icon background red.
                        isCancelable = false, // Stops users from closing by tapping outside.
                        onOkClick = {
                            // Tries to load comments again.
                            commentViewModel.fetchComments(employee.id)
                        },
                        onCancelClick = null // Does nothing if canceled (not shown).
                    )

                    // Stops the refresh spinning circle.
                    swipeRefreshLayout.isRefreshing = false
                }
            }
        }
        //--------------------------------------------------------------------------


        // Closes this screen and goes back when the close button is clicked.
        //--------------------------------------------------------------------------
        binding.closeButton.setOnClickListener {
            // Corrected to use parentFragmentManager.popBackStack()
            parentFragmentManager.popBackStack()
        }
        //--------------------------------------------------------------------------


        // Gets the comments for this employee when the screen opens.
        commentViewModel.fetchComments(employee.id)
    }


    //--------------------------------------------------------------------------
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //--------------------------------------------------------------------------
}