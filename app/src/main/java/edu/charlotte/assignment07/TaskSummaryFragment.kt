package edu.charlotte.assignment07

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import edu.charlotte.assignment07.databinding.FragmentTaskSummaryBinding
import edu.charlotte.assignment07.models.Task
import java.util.Date

private const val ARG_PARAM1 = "param1"

class TaskSummaryFragment : Fragment() {
    private var param1: Task? = null
    lateinit var binding: FragmentTaskSummaryBinding
    lateinit var mListener: tsListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is tsListener) {
            mListener = context
        } else {
            throw RuntimeException("$context must implement tsListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as Task?
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskSummaryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun getPriority(p: Int?): String {
        return when (p) {
            1 -> "Low"
            2 -> "Medium"
            3 -> "High"
            else -> "Unknown"
        }
    }

    private fun formatDate(date: Date?): String {
        if (date == null) return ""
        val month = date.month + 1
        val day = date.date
        val year = date.year + 1900

        return "$month/$day/$year"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = "Task Summary"
        
        binding.taskNameDisplay.text = param1?.name
        binding.taskDateDisplay.text = formatDate(param1?.date)
        binding.taskPriorityDisplay.text = getPriority(param1?.priority)
        
        binding.taskDeleteButton.setOnClickListener {
            param1?.let { task ->
                mListener.deleteTaskFromSummary(task)
            }
        }
        
        binding.taskbackButton.setOnClickListener {
            mListener.goBack()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Task) =
            TaskSummaryFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)
                }
            }
    }

    interface tsListener {
        fun deleteTaskFromSummary(task: Task)
        fun goBack()
    }
}
