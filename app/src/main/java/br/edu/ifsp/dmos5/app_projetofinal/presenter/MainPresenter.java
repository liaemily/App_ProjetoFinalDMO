package br.edu.ifsp.dmos5.app_projetofinal.presenter;

import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import br.edu.ifsp.dmos5.app_projetofinal.constant.Constants;
import br.edu.ifsp.dmos5.app_projetofinal.model.Task;
import br.edu.ifsp.dmos5.app_projetofinal.mvp.MainMVP;
import br.edu.ifsp.dmos5.app_projetofinal.mvp.ProfileMVP;
import br.edu.ifsp.dmos5.app_projetofinal.view.ItemClickListener;
import br.edu.ifsp.dmos5.app_projetofinal.view.TaskActivity;
import br.edu.ifsp.dmos5.app_projetofinal.view.adapter.TaskAdapter;

public class MainPresenter implements MainMVP.Presenter{

    private MainMVP.View view;
    private FirebaseFirestore database;
    private TaskAdapter adapter;

    private String userID = "";

    public MainPresenter(MainMVP.View view) {
        this.view = view;
        database = FirebaseFirestore.getInstance();
    }

    @Override
    public void detach() {
        this.view = null;
    }


    @Override
    public void populate(RecyclerView recyclerView) {

        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Query query = database.collection(Constants.TASKS_COLLECTION).
                orderBy(Constants.ATTR_NAME, Query.Direction.ASCENDING).
                whereEqualTo(Constants.ATTR_TASK_USER, userID);


        FirestoreRecyclerOptions<Task> options = new FirestoreRecyclerOptions.Builder<Task>().setQuery(query, Task.class).build();

        adapter = new TaskAdapter(options);
        adapter.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(String referenceId) {
                openTaskDetails(referenceId);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void startListener() {
        if (adapter != null)
            adapter.startListening();
    }

    @Override
    public void stopListener() {
        if (adapter != null)
            adapter.stopListening();
    }

    private void openTaskDetails(String document) {
        Intent intent = new Intent(view.getContext(), TaskActivity.class);
        intent.putExtra(Constants.FIRESTORE_DOCUMENT_KEY, document);
        view.getContext().startActivity(intent);
    }
}
