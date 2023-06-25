package br.edu.ifsp.dmos5.app_projetofinal.presenter;

import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import br.edu.ifsp.dmos5.app_projetofinal.R;
import br.edu.ifsp.dmos5.app_projetofinal.constant.Constants;
import br.edu.ifsp.dmos5.app_projetofinal.model.User;
import br.edu.ifsp.dmos5.app_projetofinal.mvp.RegisterMVP;

public class RegisterPresenter implements RegisterMVP.Presenter {
    private RegisterMVP.View view;
    private FirebaseFirestore database;
    private String userID = "";

    public RegisterPresenter(RegisterMVP.View view) {
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
    public void registerUser(String email, String password, String name, String surname, String phone) {

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    saveUser(name, surname, phone);

                   Toast.makeText(view.getContext(), "Usuário cadastrado com sucesso.", Toast.LENGTH_LONG).show();
                } else {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        Toast.makeText(view.getContext(), "A senha informada é fraca.", Toast.LENGTH_SHORT).show();
                    } catch (FirebaseAuthUserCollisionException e) {
                        Toast.makeText(view.getContext(), "O e-mail informado já está sendo utilizado.", Toast.LENGTH_SHORT).show();
                    } catch (FirebaseAuthInvalidCredentialsException e){
                        Toast.makeText(view.getContext(), "A senha informada está incorreta.", Toast.LENGTH_SHORT).show();
                    } catch (FirebaseAuthInvalidUserException e) {
                        Toast.makeText(view.getContext(), "O e-mail informado está incorreto.", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(view.getContext(), "Erro ao cadastrar o usuário.", Toast.LENGTH_SHORT).show();
                    }
                }

                }
        });
    }

    private void saveUser(String name, String surname, String phone) {

        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        User user = new User(name, surname, phone, email);

        DocumentReference documentReference = database.collection(Constants.USERS_COLLECTION).document(userID);

        documentReference.set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("Usuário cadastrado!");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(view.getContext(), "Erro ao salvar contato.", Toast.LENGTH_SHORT).show();
                    }
                });

        FirebaseAuth.getInstance().signOut();

    }
}
