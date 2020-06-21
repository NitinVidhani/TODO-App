package application.example.mytodo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import application.example.mytodo.R;
import application.example.mytodo.database.DBHelper;
import application.example.mytodo.model.ToDoModel;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    ArrayList<ToDoModel> toDoList;
    String userName;
    Context context;

    public RecyclerViewAdapter(Context context, ArrayList<ToDoModel> toDoList, String userName) {
        this.context = context;
        this.toDoList = toDoList;
        this.userName = userName;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_list_item_layout, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final int currentPosition = position+1;
        holder.textView.setText( currentPosition + ". " + toDoList.get(position).getTodoItem());

        holder.buttonCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper helper = new DBHelper(context);
                if(helper.deleteTodo(toDoList.get(currentPosition - 1))) {
                    Toast.makeText(context, "Item Deleted", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(context, "Item can't be deleted", Toast.LENGTH_SHORT).show();
                }
                
            }
        });

    }

    @Override
    public int getItemCount() {
        return toDoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        Button buttonCompleted;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.todo_text_view);
            buttonCompleted = itemView.findViewById(R.id.button_completed);

        }
    }

}
