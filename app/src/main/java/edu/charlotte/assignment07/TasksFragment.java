package edu.charlotte.assignment07;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

import edu.charlotte.assignment07.databinding.FragmentTasksBinding;
import edu.charlotte.assignment07.models.SortSelection;
import edu.charlotte.assignment07.models.Task;


public class TasksFragment extends Fragment {
    public TasksFragment() {
        // Required empty public constructor
    }

    public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskViewHolder> {
        ArrayList<Task> data;

        public TasksAdapter(ArrayList<Task> data) {
            this.data = data;
        }

        public class TaskViewHolder extends RecyclerView.ViewHolder {
            TextView taskName, taskDate, taskPriority;
            ImageButton trash;
            View rootView;

            public TaskViewHolder(@NonNull View itemView) {
                super(itemView);
                rootView = itemView;
                taskName = itemView.findViewById(R.id.taskName);
                taskDate = itemView.findViewById(R.id.taskDate);
                taskPriority = itemView.findViewById(R.id.taskPriority);
                trash = itemView.findViewById(R.id.trashButton);
            }
        }

        @NonNull
        @Override
        public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_task_details, parent, false);
            return new TaskViewHolder(view);
        }

        private String getPriority(int p) {
            switch(p) {
                case 1:
                    return "Low";
                case 2:
                    return "Medium";
                case 3:
                    return "High";
                default:
                    return "Unknown";
            }
        }

        private String formatDate(Date date) {
            if (date == null) return "";
            int month = date.getMonth() + 1;
            int day = date.getDate();
            int year = date.getYear() + 1900;

            return month + "/" + day + "/" + year;
        }

        @Override
        public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
            Task task = data.get(position);
            holder.taskName.setText(task.name);
            holder.taskDate.setText(formatDate(task.date));
            holder.taskPriority.setText(getPriority(task.priority));
            holder.trash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.deleteTask(task);
                }
            });

            holder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.gotoTaskDetails(task);
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    FragmentTasksBinding binding;
    TasksAdapter tasksAdapter;
    ArrayList<Task> mTasks = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTasksBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Tasks");
        
        mTasks = mListener.getTasks();
        
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        tasksAdapter = new TasksAdapter(mTasks);
        binding.recyclerView.setAdapter(tasksAdapter);


        binding.buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.clearAllTasks();
            }
        });

        binding.buttonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.gotoCreateTask();
            }
        });

        binding.buttonSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.gotoSelectSort();
            }
        });
    }

    TasksListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof TasksListener) {
            mListener = (TasksListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement TasksListener");
        }
    }

    interface TasksListener {
        void gotoCreateTask();
        void gotoSelectSort();
        void clearAllTasks();
        void gotoTaskDetails(Task task);
        ArrayList<Task> getTasks();
        void deleteTask(Task task);
    }
}
