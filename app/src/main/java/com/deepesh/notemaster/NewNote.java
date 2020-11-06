package com.deepesh.notemaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class NewNote extends AppCompatActivity {

    public static final String USER_DATA_KEY = "user_data";
    Toolbar toolbar2;
   EditText userData_EditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_note);

        toolbar2=findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar2);
        userData_EditText = findViewById(R.id.userinputData_editText);

        Intent intent = getIntent();
        if (intent.hasExtra(MainActivity.ID_EXTRA)){
            toolbar2.setTitle("Edit Note");
            userData_EditText.setText(intent.getStringExtra(MainActivity.DATA_EXTRA));

        }else{
            toolbar2.setTitle("Add Note");
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.save_note_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==R.id.save_note_option){

            String data = userData_EditText.getText().toString();
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra(USER_DATA_KEY,data);

            int id = getIntent().getIntExtra(MainActivity.ID_EXTRA,-1);

            if (id!=-1){
                intent.putExtra(MainActivity.ID_EXTRA,id);
            }

            // Goes back to MainActivity
            setResult(RESULT_OK,intent);
            finish();
            Toast.makeText(this, "NOTE SAVED", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
















