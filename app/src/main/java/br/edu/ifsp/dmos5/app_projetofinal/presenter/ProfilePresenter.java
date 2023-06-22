package br.edu.ifsp.dmos5.app_projetofinal.presenter;

import android.content.Intent;
import android.net.Uri;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.time.Instant;

import br.edu.ifsp.dmos5.app_projetofinal.constant.Constants;
import br.edu.ifsp.dmos5.app_projetofinal.model.User;
import br.edu.ifsp.dmos5.app_projetofinal.mvp.ProfileMVP;

public class ProfilePresenter implements ProfileMVP.Presenter {

    private ProfileMVP.View view;
    private FirebaseFirestore database;
    private String userID = "";
    private User user = null;

    private StorageReference storage;

    public ProfilePresenter(ProfileMVP.View view) {
        this.view = view;
        database = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance().getReference();
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
    public void saveUser(String name, String surname, String phone, String email, String image) {

        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        User user = new User(name, surname, phone, email, image);

        FirebaseAuth.getInstance().getCurrentUser().updateEmail(email);

        CollectionReference usersRef = database.collection(Constants.USERS_COLLECTION);

        usersRef.document(userID)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(view.getContext(), "Perfil atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(view.getContext(), "Erro ao atualizar perfil.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void populate(EditText name, EditText surname, EditText phone, EditText email, ImageView image) {
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference documentReference = database.collection(Constants.USERS_COLLECTION).document(userID);

        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value != null){
                    user = value.toObject(User.class);
                    if(user != null) {
                        name.setText(user.getNome());
                        surname.setText(user.getSobrenome());
                        phone.setText(user.getTelefone());
                        email.setText(user.getEmail());

                        Glide.with(view.getContext()).load(user.getImage()).into(image);

                    }
                }
            }
        });
    }

    @Override
    public void logout() {
        FirebaseAuth.getInstance().signOut();
    }
}
