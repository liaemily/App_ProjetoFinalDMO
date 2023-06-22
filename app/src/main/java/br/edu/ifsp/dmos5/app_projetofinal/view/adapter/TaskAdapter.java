package br.edu.ifsp.dmos5.app_projetofinal.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import br.edu.ifsp.dmos5.app_projetofinal.R;
import br.edu.ifsp.dmos5.app_projetofinal.model.Task;
import br.edu.ifsp.dmos5.app_projetofinal.view.ItemClickListener;

public class TaskAdapter extends FirestoreRecyclerAdapter<Task, TaskAdapter.TaskViewHolder> {

    private ItemClickListener clickListener;

    public TaskAdapter(@NonNull FirestoreRecyclerOptions<Task> options) {
        super(options);
    }

    public void setClickListener(ItemClickListener clickListener){
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_view, parent, false);
        TaskViewHolder holder = new TaskViewHolder(view);
        return holder;
    }

    @Override
    protected void onBindViewHolder(@NonNull TaskViewHolder holder, int position, @NonNull Task model) {
        holder.nomeTextView.setText(model.getNome());
        holder.dateTextView.setText(model.getData());
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView nomeTextView;
        public TextView dateTextView;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeTextView = itemView.findViewById(R.id.textview_name);
            dateTextView = itemView.findViewById(R.id.textview_date);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            clickListener.onClick( getSnapshots().getSnapshot(getBindingAdapterPosition()).getId() );
        }
    }
}
