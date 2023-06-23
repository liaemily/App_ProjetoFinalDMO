package br.edu.ifsp.dmos5.app_projetofinal.mvp;

import android.content.Context;
import android.net.Uri;
import android.widget.EditText;
import android.widget.ImageView;

public interface PetMVP {

    interface View{
        Context getContext();
    }

    interface Presenter{
        void detach();
        void startListener();
        void stopListener();
        void savePet(String name, String animal, String date, String peso, String race, String color);
        void populate(EditText name, EditText animal, EditText date, EditText peso, EditText race, EditText color);
    }
}
