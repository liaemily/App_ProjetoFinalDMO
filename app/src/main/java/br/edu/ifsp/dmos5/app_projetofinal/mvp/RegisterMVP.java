package br.edu.ifsp.dmos5.app_projetofinal.mvp;

import android.content.Context;

public interface RegisterMVP {

    interface View{
        Context getContext();
    }

    interface Presenter{
        void detach();
        void startListener();
        void stopListener();
        void registerUser(String email, String password, String name, String surname, String phone);
    }
}
