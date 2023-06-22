package br.edu.ifsp.dmos5.app_projetofinal.mvp;

import android.content.Context;
import android.widget.EditText;

public interface ProfileMVP {

    interface View{
        Context getContext();
    }

    interface Presenter{
        void detach();
        void startListener();
        void stopListener();
        void saveUser(String name, String surname, String phone, String email);
        void populate(EditText name, EditText surname, EditText phone, EditText email);
        void logout();
    }
}
