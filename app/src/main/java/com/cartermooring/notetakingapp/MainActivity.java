/**This code was created by Carter Mooring and Jackson Lindsay
 * PA6 Gina Sprint
 * CPSC 312
 * This function is the main code for the main screen
 * MainActivity.java
 */
package com.cartermooring.notetakingapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static final String TAG = "MainActivity";
    static final int LOGIN_REQUEST_CODE = 1; //identifies request for a result, unique int for each request
    //we use the request code to identify the result in a callback onActivityResult()
    List<Note> myNotes = new ArrayList<>();
    ArrayAdapter<Note> arrayAdapter;

    @Override
    //This function runs when the user clicks done button in NoteActivityGrid
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == LOGIN_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null){
            String title = data.getStringExtra("title"); //gets the title Extra from Note Activity
            String spinnerChoice = data.getStringExtra("spinnerChoice");
            String content = data.getStringExtra("content"); //gets the content of the Note Activity
            int indexEdited = data.getIntExtra("index", -1);

            if(indexEdited > -1){
                myNotes.set(indexEdited, new Note(title,content, spinnerChoice));
                arrayAdapter.notifyDataSetChanged();
            } else {
                ListView listView = findViewById(R.id.noteList);
                myNotes.add(new Note(title, content, spinnerChoice));
                arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, myNotes);
                listView.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    //This function runs when the app opens
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyMainGridLayout myMainGridLayout = new MyMainGridLayout(this); //get the GridLayout for page
        setContentView(myMainGridLayout);   //set the grid layout
        Button addNewNoteButton = (Button) findViewById(R.id.newNote);  //get the button id made in MMGL
        ListView listView = (ListView) findViewById(R.id.noteList); //get the listview id made in MMGL

        //This is the click listener to move to the second screen to create a note
        addNewNoteButton.setOnClickListener(new View.OnClickListener() {    //button listener to move to second screen
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                intent.putExtra("index", -1);
                intent.putExtra("title", "");
                intent.putExtra("spinnerChoice", "");
                intent.putExtra("content", "");
                startActivityForResult(intent, LOGIN_REQUEST_CODE);
            }
        });

        //On click listener for the notes already made
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);

                Note note = myNotes.get(position);
                String title = note.getTitle();
                String spinnerChoice = note.getType();
                String content = note.getContent();
                intent.putExtra("title", title);
                intent.putExtra("spinnerChoice", spinnerChoice);
                intent.putExtra("content", content);
                intent.putExtra("index", position);
                startActivityForResult(intent, LOGIN_REQUEST_CODE);
            }
        });

        //On long click listener for the notes
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                String selection = parent.getItemAtPosition(position).toString();   //be able to reference the spot they long clicked

                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this); //prepare Alert Dialog
                alertBuilder.setTitle("Delete a Note").setMessage("Are you sure you want to delete your " + selection + " note?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                myNotes.remove(position);   //TODO delete the list view and its contents
                                arrayAdapter.notifyDataSetChanged();
                            }
                        }).setNegativeButton("NO", null);
                alertBuilder.show();
                return true;
            }
        });
    }
}
