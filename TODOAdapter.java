package fpl.quangnm.lab1.demo2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fpl.quangnm.lab1.R;

public class TODOAdapter extends RecyclerView.Adapter<TODOAdapter.ToDoViewHolder> {

    private List<Todo> todolist; // list
    private OnItemClickListener listener;
    public interface OnItemClickListener {
        void onDeleteClick(int position);
        void onEditClick(int position);
        void onStatusChange(int position, boolean isDone);
    }

    public  void setOnItemClickListener(OnItemClickListener listenner){
        this.listener = listenner;
    }

    public TODOAdapter(List<Todo> todolist){  // Ham khoi tao
        this.todolist = todolist;
    }


    // Tao View
    @NonNull
    @Override
    public TODOAdapter.ToDoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.demo21_item_view,parent,false);
        return new ToDoViewHolder(view,listener);
    }

    // Gan du lieu cho view
    @Override
    public void onBindViewHolder(@NonNull TODOAdapter.ToDoViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Todo currentTodo = todolist.get(position);  // lay ve vi tri hien tai
        holder.tvToDoName.setText(currentTodo.getTitle()); // dien du lieu vao truong title
        holder.checkBox.setChecked(currentTodo.getStatus()==1); // an vao =1 con de trong la 0
        // xu ly su kien check box
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (listener != null) {
                    listener.onStatusChange(position, isChecked);
                }
            }
        });
    }


    // Lay tong so item
    @Override
    public int getItemCount() {
        return todolist.size();
    }

    class  ToDoViewHolder extends RecyclerView.ViewHolder{
        TextView tvToDoName;
        CheckBox checkBox;
        Button btnEdit, btnDELETE;


        public ToDoViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            // anh xa
            tvToDoName = itemView.findViewById(R.id.demo21_tvName);
            btnEdit = itemView.findViewById(R.id.demo21_btnEDIT);
            btnDELETE = itemView.findViewById(R.id.demo21_btnDELETE);
            checkBox = itemView.findViewById(R.id.demo21_item_CheckBox);

            btnDELETE.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null){
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION){  // lay ve vi tri xoa
                            listener.onDeleteClick(pos);
                        }
                    }
                }
            });

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null){
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION){
                            listener.onEditClick(pos);
                        }
                    }
                }
            });
        }
    }
}
