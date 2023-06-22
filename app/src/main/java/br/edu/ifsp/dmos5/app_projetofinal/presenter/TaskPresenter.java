package br.edu.ifsp.dmos5.app_projetofinal.presenter;

import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import br.edu.ifsp.dmos5.app_projetofinal.constant.Constants;
import br.edu.ifsp.dmos5.app_projetofinal.model.Task;
import br.edu.ifsp.dmos5.app_projetofinal.mvp.TaskMVP;

public class TaskPresenter implements TaskMVP.Presenter{

    private TaskMVP.View view;
    private FirebaseFirestore database;
    private String firestoreId = "";

    private String userID = "";

    private Task task = null;

    public TaskPresenter(TaskMVP.View view) {
        this.view = view;
        database = FirebaseFirestore.getInstance();
    }

    @Override
    public void detach() {
        this.view = null;
    }

    @Override
    public void startListener() {

    }

    @Override
    public void stopListener() {

    }

    @Override
    public void saveTask(String name, String date, String type, String obs) {

        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Task task = new Task(name, date, type, obs, userID);

        CollectionReference tasksRef = database.collection(Constants.TASKS_COLLECTION);

        tasksRef.add(task)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(view.getContext(), "Tarefa cadastrada com sucesso!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(view.getContext(), "Erro ao salvar tarefa.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void updateTask(String name, String date, String type, String obs) {

        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Task task = new Task(name, date, type, obs, userID);

        CollectionReference tasksRef = database.collection(Constants.TASKS_COLLECTION);

        tasksRef.document(firestoreId)
                .set(task)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(view.getContext(), "Tarefa atualizada com sucesso!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(view.getContext(), "Erro ao atualizar tarefa.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void deleteTask() {
        if(!firestoreId.isEmpty()) {
            CollectionReference tasksRef = database.collection(Constants.TASKS_COLLECTION);
            tasksRef.document(firestoreId)
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(view.getContext(), "Tarefa deletada com sucesso!", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(view.getContext(), "Erro ao deletar tarefa.", Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }

    @Override
    public void updateUI(Intent intent, EditText name, EditText date, EditText type, EditText obs, ActionBar toolBar) {
        if(intent.hasExtra(Constants.FIRESTORE_DOCUMENT_KEY)){
            firestoreId = intent.getStringExtra(Constants.FIRESTORE_DOCUMENT_KEY);
            database.collection(Constants.TASKS_COLLECTION).document(firestoreId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if(value != null){
                        task = value.toObject(Task.class);
                        if(task != null) {
                            name.setText(task.getNome());
                            date.setText(task.getData());
                            type.setText(task.getTipo());
                            obs.setText(task.getObservacao());

                            toolBar.setTitle(task.getNome().toUpperCase());
                        }
                    }
                }
            });
        }else{
            toolBar.setTitle("Nova Tarefa");
        }
    }

    @Override
    public boolean isNewTask() {
        return firestoreId.isEmpty();
    }

}
