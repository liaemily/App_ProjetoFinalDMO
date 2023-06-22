package br.edu.ifsp.dmos5.app_projetofinal.mvp;

import android.content.Context;

public interface LoginMVP {

    interface View{
        Context getContext();
    }

    interface Presenter{
        void detach();
        void startListener();
        void stopListener();
        void login(String email, String password);
    }
}
