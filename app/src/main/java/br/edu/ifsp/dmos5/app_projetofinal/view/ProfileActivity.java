package br.edu.ifsp.dmos5.app_projetofinal.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.net.URI;

import br.edu.ifsp.dmos5.app_projetofinal.R;
import br.edu.ifsp.dmos5.app_projetofinal.mvp.ProfileMVP;
import br.edu.ifsp.dmos5.app_projetofinal.presenter.ProfilePresenter;

public class ProfileActivity extends AppCompatActivity implements ProfileMVP.View{

    private ProfileMVP.Presenter presenter;
    private EditText mNameEditText;
    private EditText mSurnameEditText;
    private EditText mPhoneEditText;
    private EditText mEmailEditText;
    private ImageView mPhotoImageView;
    private Uri imageUri;

    private String image;
    ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setTitle("Perfil");
        presenter = new ProfilePresenter(this);

        findById();
        setListener();
        checkGalery();

    }

    protected void onStart() {
        super.onStart();

        presenter.populate(mNameEditText, mSurnameEditText, mPhoneEditText, mEmailEditText, mPhotoImageView);
        presenter.startListener();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //presenter.stopListener();
    }

    @Override
    protected void onDestroy() {
        //presenter.detach();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile, menu);

        MenuItem itemSave = menu.findItem(R.id.save_profile);
        MenuItem itemLogout = menu.findItem(R.id.logout);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_profile:
                saveProfile();
                break;
            case R.id.logout:
                logout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void findById(){
        mNameEditText = findViewById(R.id.edittext_name);
        mSurnameEditText = findViewById(R.id.edittext_surname);
        mPhoneEditText = findViewById(R.id.edittext_phone);
        mEmailEditText = findViewById(R.id.edittext_email);
        mPhotoImageView = findViewById(R.id.ic_profile);
    }

    private void saveProfile(){
        String name = mNameEditText.getText().toString();
        String surname = mSurnameEditText.getText().toString();
        String phone = mPhoneEditText.getText().toString();
        String email = mEmailEditText.getText().toString();
        String image = imageUri.toString();

        presenter.saveUser(name, surname, phone, email, image);

        finish();
    }

    private void logout(){
        presenter.logout();
        Intent intent = new Intent(this, LoginActivity.class);
        this.startActivity(intent);
        finish();
    }

    private void setListener(){
        mPhotoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGalery(activityResultLauncher);
            }
        });
    }

    private void checkGalery(){
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            imageUri = data.getData();
                            mPhotoImageView.setImageURI(imageUri);

                        } else {
                            Toast.makeText(ProfileActivity.this, "Erro ao adicionar imagem.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    private void openGalery(ActivityResultLauncher activityResultLauncher){

        Intent photoPicker = new Intent();
        photoPicker.setAction(Intent.ACTION_GET_CONTENT);
        photoPicker.setAction(Intent.ACTION_OPEN_DOCUMENT);
        photoPicker.setType("image/*");
        activityResultLauncher.launch(photoPicker);
    }

    @Override
    public Context getContext() {
        return this;
    }
}
