package br.edu.ifsp.dmos5.app_projetofinal.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import br.edu.ifsp.dmos5.app_projetofinal.R;
import br.edu.ifsp.dmos5.app_projetofinal.mvp.RegisterMVP;
import br.edu.ifsp.dmos5.app_projetofinal.presenter.RegisterPresenter;

public class RegisterActivity extends AppCompatActivity implements RegisterMVP.View {

    private RegisterMVP.Presenter presenter;
    private Button mButton;
    private EditText mNameEditText;
    private EditText mSurnameEditText;
    private EditText mPhoneEditText;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private TextView mLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();

        presenter = new RegisterPresenter(this);

        findById();
        setListener();
    }

    /*@Override
    protected void onDestroy() {
        presenter.detach();
        super.onDestroy();
    }*/

    @Override
    public Context getContext() {
        return this;
    }

    private void findById(){
        mButton = findViewById(R.id.button_register);
        mNameEditText = findViewById(R.id.edittext_name);
        mSurnameEditText = findViewById(R.id.edittext_surname);
        mPhoneEditText = findViewById(R.id.edittext_phone);
        mEmailEditText = findViewById(R.id.edittext_email);
        mPasswordEditText = findViewById(R.id.edittext_password);
        mLogin = findViewById(R.id.text_login);
    }

    private void setListener(){
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogin();
            }
        });
    }

    private void addUser(){
        String name = mNameEditText.getText().toString();
        String surname = mSurnameEditText.getText().toString();
        String phone = mPhoneEditText.getText().toString();
        String email = mEmailEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();

        presenter.registerUser(email, password, name, surname, phone);

    }

    private void openLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}
