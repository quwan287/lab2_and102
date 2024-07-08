package fpl.quangnm.lab1.demo2;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import fpl.quangnm.lab1.R;

public class MainActivity extends AppCompatActivity {
    private EditText txtTitle, txtContent, txtDates, txtType;
    private Button btnAdd;
    private RecyclerView recyclerView;
    private TODOAdapter adapter;
    private ToDoDao dao;
    private List<Todo> todoList;
    private Todo currenEdittingTodo = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        txtTitle = findViewById(R.id.demo2TxtTitle);
        txtContent = findViewById(R.id.demo2TxtContent);
        txtDates = findViewById(R.id.demo2TxtDate);
        txtType = findViewById(R.id.demo2TxtType);
        btnAdd = findViewById(R.id.demo2_btnADD);
        recyclerView = findViewById(R.id.demo2_RecyclerView);

        dao = new ToDoDao(this);
        todoList = dao.getAllTodos();
        adapter = new TODOAdapter(todoList);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currenEdittingTodo == null) {
                    addToDo();
                }
                else {
                    updateTodo();
                }
            }
        });
        adapter.setOnItemClickListener(new TODOAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                deleteTodo(position);
            }

            @Override
            public void onEditClick(int position) {
                editTodo(position);
            }

            @Override
            public void onStatusChange(int position, boolean isDone) {
                updateTodoStatus(position, isDone);
            }
        });
    }
    private void updateTodoStatus(int pos, boolean isDone){
        Todo todo= todoList.get(pos);
        todo.setStatus(isDone?1:0);
        dao.updateToDOStatus(todo.getId(), todo.getStatus());
//        adapter.notifyItemChanged(pos);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyItemChanged(pos);
            }
        });
        Toast.makeText(this, "Update thành công", Toast.LENGTH_SHORT).show();
    }
    private void addToDo(){
        String title = txtTitle.getText().toString();
        String content = txtContent.getText().toString();
        String date = txtDates.getText().toString();
        String type = txtType.getText().toString();
        Todo todo = new Todo(0,title, content, date, type, 0);
        dao.addToDo(todo);
        todoList.add(0,todo);
        adapter.notifyItemInserted(0);
        recyclerView.scrollToPosition(0);
        clearFields();
    }
    private void updateTodo(){
        String title = txtTitle.getText().toString();
        String content = txtContent.getText().toString();
        String date = txtDates.getText().toString();
        String type = txtType.getText().toString();
        currenEdittingTodo.setTitle(title);
        currenEdittingTodo.setContent(content);
        currenEdittingTodo.setDate(date);
        currenEdittingTodo.setType(type);
        dao.updataToDO(currenEdittingTodo);

        int pos = todoList.indexOf(currenEdittingTodo);
        adapter.notifyItemChanged(pos);
        currenEdittingTodo = null;
        btnAdd.setText("Add");
        clearFields();
    }
    private void editTodo(int pos){
        currenEdittingTodo = todoList.get(pos);
        txtTitle.setText(currenEdittingTodo.getTitle());
        txtContent.setText(currenEdittingTodo.getContent());
        txtDates.setText(currenEdittingTodo.getDate());
        txtType.setText(currenEdittingTodo.getType());
        btnAdd.setText("Update");
    }
    private void deleteTodo(int pos){
        Todo todo = todoList.get(pos);
        dao.deleteTodo(todo.getId());
        todoList.remove(pos);
        adapter.notifyItemRemoved(pos);
    }
    private void clearFields(){
        txtTitle.setText("");
        txtContent.setText("");
        txtDates.setText("");
        txtType.setText("");
    }
}