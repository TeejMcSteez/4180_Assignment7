package edu.charlotte.assignment07;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import edu.charlotte.assignment07.models.SortSelection;
import edu.charlotte.assignment07.models.Task;

public class MainActivity extends AppCompatActivity implements TasksFragment.TasksListener,
        CreateTaskFragment.CreateTaskListener, SelectTaskDateFragment.SelectTaskDateListener, SortFragment.SortListener, TaskSummaryFragment.tsListener {
    ArrayList<Task> mTasks = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new TasksFragment(), "tasks-fragment")
                .commit();
    }

    @Override
    public void gotoCreateTask() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new CreateTaskFragment(), "create-task-fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoSelectSort() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new SortFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void clearAllTasks() {
        mTasks.clear();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new TasksFragment(), "tasks-fragment")
                .commit();
    }

    @Override
    public void gotoTaskDetails(Task task) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, TaskSummaryFragment.newInstance(task))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public ArrayList<Task> getTasks() {
        return mTasks;
    }

    @Override
    public void deleteTask(Task task) {
        mTasks.remove(task);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new TasksFragment(), "tasks-fragment")
                .commit();
    }

    public void deleteTaskDetails(Task task) {
        mTasks.remove(task);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new TasksFragment(), "tasks-fragment")
                .commit();
    }

    @Override
    public void goBack() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void createTask(Task task) {
        mTasks.add(task);
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void cancelAndPopBackStack() {
        getSupportFragmentManager().popBackStack();
    }
    
    Comparator<Task> taskNameComparator = new Comparator<Task>() {
        @Override
        public int compare(Task o1, Task o2) {
            return o1.name.compareTo(o2.name);
        }
    };

    Comparator<Task> taskDateComparator = new Comparator<Task>() {
        @Override
        public int compare(Task o1, Task o2) {
            return o1.date.compareTo(o2.date);
        }
    };

    Comparator<Task> taskPriorityComparator = new Comparator<Task>() {
        @Override
        public int compare(Task o1, Task o2) {
            return Integer.compare(o1.priority, o2.priority);
        }
    };

    @Override
    public void sendSortSelection(SortSelection sortSelection) {
        switch (sortSelection.getSortAttribute()) {
            case "name":
                if ("ASC".equals(sortSelection.getSortOrder())) {
                    mTasks.sort(taskNameComparator);
                } else {
                    mTasks.sort(taskNameComparator.reversed());
                }
                break;
            case "date":
                if ("ASC".equals(sortSelection.getSortOrder())) {
                    mTasks.sort(taskDateComparator);
                } else {
                    mTasks.sort(taskDateComparator.reversed());
                }
                break;
            case "priority":
                if ("ASC".equals(sortSelection.getSortOrder())) {
                    mTasks.sort(taskPriorityComparator);
                } else {
                    mTasks.sort(taskPriorityComparator.reversed());
                }
                break;
        }
        
        getSupportFragmentManager().popBackStack();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new TasksFragment(), "tasks-fragment")
                .commit();
    }

    @Override
    public void sendSelectedDate(Date date) {
        CreateTaskFragment fragment = (CreateTaskFragment) getSupportFragmentManager().findFragmentByTag("create-task-fragment");
        if (fragment != null) {
            fragment.setSelectedDate(date);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void selectDate() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new SelectTaskDateFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void deleteTaskFromSummary(@NotNull Task task) {
        mTasks.remove(task);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new TasksFragment(), "tasks-fragment")
                .commit();
    }
}
