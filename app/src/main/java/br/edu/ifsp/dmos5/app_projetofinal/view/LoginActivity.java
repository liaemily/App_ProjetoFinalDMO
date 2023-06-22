package br.edu.ifsp.dmos5.app_projetofinal.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.edu.ifsp.dmos5.app_projetofinal.R;
import br.edu.ifsp.dmos5.app_projetofinal.mvp.LoginMVP;
import br.edu.ifsp.dmos5.app_projetofinal.presenter.LoginPresenter;

public class LoginActivity extends AppCompatActivity implements LoginMVP.View {

    private LoginMVP.Presenter presenter;
    private Button mButton;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private TextView mRegisterUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        presenter = new LoginPresenter(this);

        findById();
        setListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.startListener();
    }

    @Override
    protected void onStop() {
        super.onStop();
       //presenter.stopListener();
    }

    @Override
    protected void onDestroy() {
        presenter.detach();
        super.onDestroy();
    }

    private void findById(){
        mButton = findViewById(R.id.button_login);
        mEmailEditText = findViewById(R.id.edittext_email);
        mPasswordEditText = findViewById(R.id.edittext_password);
        mRegisterUser = findViewById(R.id.text_register);

    }

    private void setListener(){
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        mRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });
    }

    private void addUser(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private void login(){
        String email = mEmailEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();

        if (email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Preencha todos os campos para efetuar o login.", Toast.LENGTH_SHORT).show();
        } else {
            presenter.login(email, password);
        }
    }

    @Override
    public Context getContext() {
        return this;
    }
}