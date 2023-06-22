package br.edu.ifsp.dmos5.app_projetofinal.view;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import br.edu.ifsp.dmos5.app_projetofinal.R;
import br.edu.ifsp.dmos5.app_projetofinal.mvp.TaskMVP;
import br.edu.ifsp.dmos5.app_projetofinal.presenter.TaskPresenter;

public class TaskActivity extends AppCompatActivity implements TaskMVP.View{

    private TaskMVP.Presenter presenter;
    private EditText nameEditText, dateEditText, typeEditText, obsEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        findById();
        setListener();

        presenter = new TaskPresenter(this);
        presenter.updateUI(getIntent(), nameEditText, dateEditText, typeEditText, obsEditText, getSupportActionBar());

    }

    @Override
    protected void onDestroy() {
        presenter.detach();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_task, menu);

        if(presenter.isNewTask()){
            MenuItem item = menu.findItem(R.id.delete_task);
            item.setVisible(false);

        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_task:
                saveTask();
                break;
            case R.id.delete_task:
                presenter.deleteTask();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Context getContext() {
        return this;
    }

    private void findById(){
        nameEditText = findViewById(R.id.edittext_name);
        dateEditText = findViewById(R.id.edittext_date);
        typeEditText = findViewById(R.id.edittext_type);
        obsEditText = findViewById(R.id.edittext_obs);
    }

    private void setListener(){
        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(TaskActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dateEditText.setBackground(null);
                                String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year ;
                                dateEditText.setText(date);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });
    }

    private void saveTask(){

        String name = nameEditText.getText().toString();
        String date = dateEditText.getText().toString();
        String type = typeEditText.getText().toString();
        String obs = obsEditText.getText().toString();

        if(presenter.isNewTask()){
            presenter.saveTask(name, date, type, obs);
        }else{
            presenter.updateTask(name, date, type, obs);
        }
        finish();
    }

}
