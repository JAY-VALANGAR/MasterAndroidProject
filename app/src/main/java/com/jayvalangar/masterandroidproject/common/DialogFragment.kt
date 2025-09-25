package com.jayvalangar.masterandroidproject.ui

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.jayvalangar.masterandroidproject.R
import com.jayvalangar.masterandroidproject.databinding.FragmentDialogBinding

class CommonDialog : androidx.fragment.app.DialogFragment() {

    private var _binding: FragmentDialogBinding? = null
    private val binding get() = _binding!!
    private var onOkClickListener: (() -> Unit)? = null
    private var onCancelClickListener: (() -> Unit)? = null

    companion object {
        private const val ARG_ICON = "icon"
        private const val ARG_TITLE = "title"
        private const val ARG_TEXT = "text"
        private const val ARG_OK_BUTTON = "ok_button"
        private const val ARG_CANCEL_BUTTON = "cancel_button"
        private const val ARG_ICON_BG_COLOR = "icon_bg_color"
        private const val ARG_IS_CANCELABLE = "is_cancelable"

        fun show(
            context: Context,
            iconRes: Int = R.drawable.ic_launcher_foreground,
            title: String = "Information",
            text: String = "This is a default message.",
            okButtonText: String? = "OK",
            cancelButtonText: String? = "Cancel",
            iconBgColor: Int = Color.GRAY,
            isCancelable: Boolean = true,
            onOkClick: (() -> Unit)? = null,
            onCancelClick: (() -> Unit)? = null
        ) {
            val dialog = CommonDialog().apply {
                arguments = Bundle().apply {
                    putInt(ARG_ICON, iconRes)
                    putString(ARG_TITLE, title)
                    putString(ARG_TEXT, text)
                    putString(ARG_OK_BUTTON, okButtonText ?: "")
                    putString(ARG_CANCEL_BUTTON, cancelButtonText ?: "")
                    putInt(ARG_ICON_BG_COLOR, iconBgColor)
                    putBoolean(ARG_IS_CANCELABLE, isCancelable)
                }
                onOkClick?.let { setOnOkClickListener(it) }
                onCancelClick?.let { setOnCancelClickListener(it) }
            }
            when (context) {
                is Activity -> dialog.show(
                    (context as AppCompatActivity).supportFragmentManager,
                    "CommonDialog"
                )

                is Fragment -> dialog.show(context.parentFragmentManager, "CommonDialog")
                else -> throw IllegalArgumentException("Context must be an Activity or Fragment")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getBoolean(ARG_IS_CANCELABLE, true)?.let { isCancelable = it }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        _binding = FragmentDialogBinding.inflate(LayoutInflater.from(context))
        builder.setView(binding.root)
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            binding.icon.setImageResource(it.getInt(ARG_ICON))
            binding.title.text = it.getString(ARG_TITLE)
            binding.text.text = it.getString(ARG_TEXT)
            val okText = it.getString(ARG_OK_BUTTON)
            val cancelText = it.getString(ARG_CANCEL_BUTTON)
            binding.okButton.text = okText
            binding.cancelButton.text = cancelText

            // Hide unused button if text is empty or null
            if (okText.isNullOrEmpty()) {
                binding.okButton.visibility = View.GONE
            }
            if (cancelText.isNullOrEmpty()) {
                binding.cancelButton.visibility = View.GONE
            }

            val iconBgColor = it.getInt(ARG_ICON_BG_COLOR)
            binding.iconBackground.setCardBackgroundColor(iconBgColor)
        }

        binding.okButton.setOnClickListener {
            onOkClickListener?.invoke()
            dismiss()
        }

        binding.cancelButton.setOnClickListener {
            onCancelClickListener?.invoke()
            dismiss()
        }

        dialog?.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.9).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    fun setOnOkClickListener(listener: () -> Unit) {
        onOkClickListener = listener
    }

    fun setOnCancelClickListener(listener: () -> Unit) {
        onCancelClickListener = listener
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}