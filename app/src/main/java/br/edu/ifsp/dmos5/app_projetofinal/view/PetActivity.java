package br.edu.ifsp.dmos5.app_projetofinal.view;

import android.content.Context;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import br.edu.ifsp.dmos5.app_projetofinal.mvp.PetMVP;

public class PetActivity extends AppCompatActivity implements PetMVP.View{

    private PetMVP.Presenter presenter;
    private EditText nameEditText;
    private EditText animalEditText;
    private EditText ageEditText;
    private EditText pesoEditText;
    private EditText racaEditText;
    private EditText colorEditText;

    @Override
    public Context getContext() {
        return null;
    }
}
