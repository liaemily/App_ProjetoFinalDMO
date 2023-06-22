package br.edu.ifsp.dmos5.app_projetofinal.mvp;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

public interface MainMVP {

    interface View{
        Context getContext();
    }

    interface Presenter{
        void detach();
        void startListener();
        void stopListener();
        void populate(RecyclerView recyclerView);
    }
}
