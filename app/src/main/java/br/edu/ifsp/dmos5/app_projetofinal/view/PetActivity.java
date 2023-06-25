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

import br.edu.ifsp.dmos5.app_projetofinal.R;
import br.edu.ifsp.dmos5.app_projetofinal.mvp.PetMVP;
import br.edu.ifsp.dmos5.app_projetofinal.presenter.MainPresenter;
import br.edu.ifsp.dmos5.app_projetofinal.presenter.PetPresenter;
import br.edu.ifsp.dmos5.app_projetofinal.presenter.ProfilePresenter;

public class PetActivity extends AppCompatActivity implements PetMVP.View{

    private PetMVP.Presenter presenter;
    private EditText nameEditText;
    private EditText animalEditText;
    private EditText ageEditText;
    private EditText pesoEditText;
    private EditText racaEditText;
    private EditText colorEditText;
    private ImageView mPhotoImageView;
    private Uri imageUri;
    ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet);

        getSupportActionBar().setTitle("Pet");
        presenter = new PetPresenter(this);

        findById();
        setListener();
        checkGalery();
    }

    protected void onStart() {
        super.onStart();

        presenter.populate(nameEditText, animalEditText, ageEditText, pesoEditText, racaEditText, colorEditText);
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
        getMenuInflater().inflate(R.menu.menu_pet, menu);

        MenuItem itemSave = menu.findItem(R.id.save_profile);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_profile:
                saveProfile();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void findById(){
        nameEditText = findViewById(R.id.edittext_name);
        animalEditText = findViewById(R.id.edittext_animal);
        ageEditText = findViewById(R.id.edittext_age);
        pesoEditText = findViewById(R.id.edittext_peso);
        racaEditText = findViewById(R.id.edittext_raca);
        colorEditText = findViewById(R.id.edittext_color);
        mPhotoImageView = findViewById(R.id.ic_profile);
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
                            Toast.makeText(PetActivity.this, "Erro ao adicionar imagem.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    private void openGalery(ActivityResultLauncher activityResultLauncher){

        Intent photoPicker = new Intent();
        photoPicker.setAction(Intent.ACTION_GET_CONTENT);
        photoPicker.setType("image/*");
        activityResultLauncher.launch(photoPicker);
    }

    private void saveProfile(){
        String name = nameEditText.getText().toString();
        String animal = nameEditText.getText().toString();
        String age = ageEditText.getText().toString();
        String peso = pesoEditText.getText().toString();
        String raca = racaEditText.getText().toString();
        String color = colorEditText.getText().toString();
        String image = imageUri.toString();

        presenter.savePet(name, animal, age, peso, raca, color);

        finish();
    }

    @Override
    public Context getContext() {
        return this;
    }
}
