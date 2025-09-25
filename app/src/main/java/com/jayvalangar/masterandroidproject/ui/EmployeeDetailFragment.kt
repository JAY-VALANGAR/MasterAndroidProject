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

class EmployeeDetailFragment : Fragment() {

    private var _binding: FragmentEmployeeDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var employee: Employee
    private lateinit var employeeViewModel: EmployeeViewModel
    private lateinit var commentViewModel: CommentViewModel
    private lateinit var commentAdapter: CommentAdapter
    private lateinit var swipeRefreshLayout: androidx.swiperefreshlayout.widget.SwipeRefreshLayout

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            employee = it.getParcelable(ARG_EMPLOYEE)!!
        }
        val employeeRepository = EmployeeRepository(requireContext())
        val commentRepository = CommentRepository(requireContext())
        val employeeViewModelFactory = EmployeeViewModelFactory(employeeRepository)
        val commentViewModelFactory = CommentViewModelFactory(commentRepository)
        employeeViewModel =
            ViewModelProvider(this, employeeViewModelFactory)[EmployeeViewModel::class.java]
        commentViewModel =
            ViewModelProvider(this, commentViewModelFactory)[CommentViewModel::class.java]
    }

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

        commentAdapter = CommentAdapter()
        binding.commentsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.commentsRecyclerView.adapter = commentAdapter

        // Initialize SwipeRefreshLayout
        swipeRefreshLayout = binding.root.findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {
            commentViewModel.fetchComments(employee.id) // Trigger refresh
        }

        commentViewModel.comments.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is ApiThreeState.Loading -> {
                    binding.commentsProgressBar.visibility = View.VISIBLE
                    binding.commentsErrorText.visibility = View.GONE
                    swipeRefreshLayout.isRefreshing = true // Show refresh indicator
                }

                is ApiThreeState.Success -> {
                    binding.commentsProgressBar.visibility = View.GONE
                    binding.commentsErrorText.visibility = View.GONE
                    commentAdapter.submitList(resource.data)
                    swipeRefreshLayout.isRefreshing = false // Stop refresh indicator
                }

                is ApiThreeState.Error -> {
                    binding.commentsProgressBar.visibility = View.GONE
                    binding.commentsErrorText.visibility = View.VISIBLE
                    binding.commentsErrorText.text = resource.message
//                    Toast.makeText(context, "Error Code: ${resource.code}", Toast.LENGTH_SHORT).show()
                    CommonDialog.show(
                        context = requireContext(),
                        iconRes = R.drawable.ic_launcher_foreground,
                        title = "Error : ${resource.code}",
                        text = "Failed to load comments. Retry? ${resource.message}",
                        okButtonText = "Retry",
                        cancelButtonText = null, // Single button example
                        iconBgColor = Color.RED,
                        isCancelable = false,
                        onOkClick = { commentViewModel.fetchComments(employee.id) },
                        onCancelClick = null
                    )
                    swipeRefreshLayout.isRefreshing = false // Stop refresh indicator
                }
            }
        }

        binding.closeButton.setOnClickListener {
            // Corrected to use parentFragmentManager.popBackStack()
            parentFragmentManager.popBackStack()
        }

        commentViewModel.fetchComments(employee.id)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}