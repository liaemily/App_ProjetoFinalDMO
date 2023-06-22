package br.edu.ifsp.dmos5.app_projetofinal.mvp;

import android.content.Context;
import android.content.Intent;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;

public interface TaskMVP {

    interface View{
        Context getContext();
    }

    interface Presenter{
        void detach();
        void startListener();
        void stopListener();
        void saveTask(String name, String date, String type, String obs);
        void updateTask(String name, String date, String type, String obs);
        void deleteTask();
        void updateUI(Intent intent, EditText name, EditText date, EditText type, EditText obs, ActionBar toolBar);
        boolean isNewTask();
    }
}
