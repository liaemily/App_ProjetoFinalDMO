package br.edu.ifsp.dmos5.app_projetofinal.presenter;

import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.firestore.FirebaseFirestore;

import br.edu.ifsp.dmos5.app_projetofinal.mvp.LoginMVP;
import br.edu.ifsp.dmos5.app_projetofinal.view.MainActivity;
import br.edu.ifsp.dmos5.app_projetofinal.view.ProfileActivity;

public class LoginPresenter implements LoginMVP.Presenter {

    private LoginMVP.View view;
    private FirebaseFirestore database;

    public LoginPresenter(LoginMVP.View view) {
        this.view = view;
        database = FirebaseFirestore.getInstance();
    }

    @Override
    public void detach () {
        this.view = null;
    }

    @Override
    public void startListener () {
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            openMain();
        }
    }

    @Override
    public void stopListener () {

    }

    @Override
    public void login(String email, String password) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    openMain();
                } else {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        Toast.makeText(view.getContext(), "Credenciais inválidas.", Toast.LENGTH_SHORT).show();
                    } catch (FirebaseAuthInvalidUserException e){
                        Toast.makeText(view.getContext(), "O usuário informado não foi encontrado.", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    public void openMain(){
        Intent intent = new Intent(view.getContext(), MainActivity.class);
        view.getContext().startActivity(intent);
    }
}
