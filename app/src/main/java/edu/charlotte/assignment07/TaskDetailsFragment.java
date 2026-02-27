package edu.charlotte.assignment07;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;

import edu.charlotte.assignment07.databinding.FragmentTaskDetailsBinding;
import edu.charlotte.assignment07.models.Task;


public class TaskDetailsFragment extends Fragment {
    private static final String ARG_PARAM_TASK = "ARG_PARAM_TASK";
    static private Task mTask;

    public TaskDetailsFragment() {
        // Required empty public constructor
    }

    public static TaskDetailsFragment newInstance(Task task) {
        TaskDetailsFragment fragment = new TaskDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_TASK, task);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTask = (Task)getArguments().getSerializable(ARG_PARAM_TASK);
        }
    }

    private String formatDate(Date date) {
        int month = date.getMonth();
        int day = date.getDay();
        int year = date.getYear();

        String monthStr = Integer.toString(month);
        String dayStr = Integer.toString(day);
        String yearStr = Integer.toString(year);

        return monthStr + "/" + dayStr + "/" + yearStr;

    }

    private String getPriority(int p) throws RuntimeException {
        switch(p) {
            case 1:
                return "Low";
            case 2:
                return "Medium";
            case 3:
                return "High";
            default:
                throw new RuntimeException("Invalid priority integer");
        }
    }

    FragmentTaskDetailsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTaskDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Task Details");
    }

}