package br.edu.ifsp.dmos5.app_projetofinal.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import br.edu.ifsp.dmos5.app_projetofinal.R;
import br.edu.ifsp.dmos5.app_projetofinal.mvp.MainMVP;
import br.edu.ifsp.dmos5.app_projetofinal.presenter.MainPresenter;

public class MainActivity extends AppCompatActivity implements MainMVP.View{

    private MainMVP.Presenter presenter;
    private FloatingActionButton mActionButton;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Home");

        findById();
        setListener();
        presenter = new MainPresenter(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.populate(mRecyclerView);
        presenter.startListener();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.stopListener();
    }

    @Override
    protected void onDestroy() {
        presenter.detach();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.open_profile_user:
                openProfileUser();
                break;
            case R.id.open_profile_pet:
                openProfilePet();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openProfileUser(){
        Intent intent = new Intent(this, ProfileActivity.class);
        this.startActivity(intent);
    }

    private void openProfilePet() {

    }

    private void findById(){
        mActionButton = findViewById(R.id.add_new_task);
        mRecyclerView = findViewById(R.id.recycler_view);
    }

    private void setListener(){
        mActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewTask();
            }
        });
    }

    private void openNewTask(){
        Intent intent = new Intent(this, TaskActivity.class);
        startActivity(intent);
    }

    @Override
    public Context getContext() {
        return this;
    }
}
