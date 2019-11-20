/**This code was created by Carter Mooring and Jackson Lindsay
 * PA6 Gina Sprint
 * CPSC 312
 * This function is the main code for the Note activity so users
 *      can create a note title, type, and content
 * NoteActivity.java
 */
package com.cartermooring.notetakingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class NoteActivity extends AppCompatActivity {
    static final String TAG = "NoteActivity";

    final int work = 0;
    final int school = 1;
    final int personal = 2;
    final int other = 3;
    int noteIndex;

    @Override
    //This function runs when the program is created
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        NoteActivityGrid gridLayout = new NoteActivityGrid(this);
        setContentView(gridLayout);

        Intent intent = getIntent();
        EditText title = findViewById(R.id.titleEditText);
        EditText content = findViewById(R.id.contentEditText);
        Spinner spinner = findViewById(R.id.mailTypeSpinner);

        String noteTitle = intent.getStringExtra("title");
        String noteContent = intent.getStringExtra("content");
        String noteType = intent.getStringExtra("spinnerChoice");
        noteIndex = intent.getIntExtra("index", -1);

        title.setText(noteTitle);
        spinner.setSelection(spinnerType(noteType));
        content.setText(noteContent);


        Button doneButton = (Button) findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            //This function is the button click listener
            public void onClick(View v) {

                EditText titleEditText = (EditText) findViewById(R.id.titleEditText);
                String title = titleEditText.getText().toString();
                EditText contentEditText = (EditText) findViewById(R.id.contentEditText);
                String content = contentEditText.getText().toString();
                Spinner spinner = (Spinner) findViewById(R.id.mailTypeSpinner);
                String spinnerContent = spinner.getSelectedItem().toString();

                Intent intent = new Intent();
                intent.putExtra("title", title);
                intent.putExtra("content", content);
                intent.putExtra("spinnerChoice", spinnerContent);
                intent.putExtra("index", noteIndex);
                setResult(Activity.RESULT_OK, intent);
                NoteActivity.this.finish();
            }
        });
    }

     //This function sets up the spinner
     public int spinnerType(String stringType){
        if (stringType.equals("WORK")){
            return work;
        }
        else if (stringType.equals("SCHOOL")){
            return school;
        }
        else if (stringType.equals("PERSONAL")){
            return personal;
        }
        else if (stringType.equals("OTHER")){
            return other;
        }
        else{
            return 0;
        }
     }
}
