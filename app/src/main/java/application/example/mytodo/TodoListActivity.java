package application.example.mytodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import application.example.mytodo.adapters.RecyclerViewAdapter;
import application.example.mytodo.database.DBHelper;
import application.example.mytodo.model.ToDoModel;

public class TodoListActivity extends AppCompatActivity implements TodoDialog.ToDoDialogListener {

    private RecyclerView recyclerView;
    private TextView welcomeText;
    private TextView textDesc;
    private Button buttonAdd;

    RecyclerViewAdapter adapter;

    private ArrayList<ToDoModel> list;

    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        recyclerView = findViewById(R.id.recycler_view);
        welcomeText = findViewById(R.id.welcome_text);
        buttonAdd = findViewById(R.id.button_add);
        textDesc = findViewById(R.id.text_view_desc);


        Intent intent = getIntent();
        userName = intent.getStringExtra("username");
        Log.e("Username", "onCreate: "+ userName );
        welcomeText.setText("Welcome " + userName);

        list = loadToDo(userName);
        adapter =  new RecyclerViewAdapter(this, list, userName);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        Log.e("List size", "onCreate: "+ list.size());
        if (list.isEmpty()) {
            textDesc.setText("Press add button to create your todo list");
        } else {
            textDesc.setText("Here is your TODO List");
        }

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TodoDialog dialog = new TodoDialog();
                dialog.show(getSupportFragmentManager(), "ToDo Dialog");
            }
        });

    }

    @Override
    public void addToDoItem(String toDoItem) {
        ToDoModel todo = new ToDoModel(userName, toDoItem);
        list.add(todo);

        DBHelper helper = new DBHelper(this);
        Log.e("Username", "getToDoItem: "+ userName );

        helper.insertTodo(todo);
        adapter.notifyDataSetChanged();
    }

    public ArrayList<ToDoModel> loadToDo(String userName) {

        DBHelper helper = new DBHelper(this);
        ArrayList<ToDoModel> todoList = helper.getToDoList(userName);
        return todoList;
    }


}
