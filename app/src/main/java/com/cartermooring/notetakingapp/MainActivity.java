/**This code was created by Carter Mooring and Jackson Lindsay
 * PA7 Gina Sprint
 * CPSC 312
 * This function is the main code for the main screen
 * MainActivity.java
 */
package com.cartermooring.notetakingapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static final String TAG = "MainActivity";
    static final int LOGIN_REQUEST_CODE = 1; //identifies request for a result, unique int for each request
    //we use the request code to identify the result in a callback onActivityResult()
    List<Note> myNotes = new ArrayList<>();
    ArrayAdapter<Note> arrayAdapter;

    NoteOpenHelper openHelper = new NoteOpenHelper(this);   //for SQL link to list
    SimpleCursorAdapter cursorAdapter;

    @Override
    //This function runs when the user clicks done button in NoteActivityGrid
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == LOGIN_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null){
            String title = data.getStringExtra("title"); //gets the title Extra from Note Activity
            String spinnerChoice = data.getStringExtra("spinnerChoice");
            String content = data.getStringExtra("content"); //gets the content of the Note Activity
            int indexEdited = data.getIntExtra("index", -1);
            Cursor cursor = openHelper.getSelectAllNotesCursor();

            if(indexEdited > -1){   //edited note
                myNotes.set(indexEdited, new Note(title,content, spinnerChoice));
                arrayAdapter.notifyDataSetChanged();
                Note newNote = new Note(title, content, spinnerChoice); //sql
                cursorAdapter.changeCursor(cursor);
                openHelper.updateNoteById(indexEdited, newNote);
            } else {    //new note
                ListView listView = findViewById(R.id.noteList);
                Note newNote = new Note(title, content, spinnerChoice);
                myNotes.add(newNote);
                arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, myNotes);
                listView.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();
                 //sql
                cursorAdapter.changeCursor(cursor);
                openHelper.insertNote(newNote);
            }
        }
    }

    //Inflates main_menu.xml for the CAM bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //This function is for the cam menu items when they are selected and the actions that take place afterwards
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Cursor cursor = openHelper.getSelectAllNotesCursor();
        switch(id){
            case R.id.addMenuItem:
                //This CAM button activates the second screen for the user to add a new note
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                intent.putExtra("index", -1);
                intent.putExtra("title", "");
                intent.putExtra("spinnerChoice", "");
                intent.putExtra("content", "");
                cursorAdapter.changeCursor(cursor);
                startActivityForResult(intent, LOGIN_REQUEST_CODE);
                return true;
            case R.id.deleteMenuItem:
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this); //prepare Alert Dialog
                alertBuilder.setTitle("Delete a Note").setMessage("Are you sure you want to delete all your notes?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                myNotes.clear();
                                arrayAdapter.notifyDataSetChanged();
                            }
                        }).setNegativeButton("NO", null);
                openHelper.deleteAllNotes();
                cursorAdapter.changeCursor(cursor);
                alertBuilder.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    //This function runs when the app opens
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyMainGridLayout myMainGridLayout = new MyMainGridLayout(this); //get the GridLayout for page
        setContentView(myMainGridLayout);   //set the grid layout
        //Button addNewNoteButton = (Button) findViewById(R.id.newNote);  //get the button id made in MMGL
        final ListView listView = (ListView) findViewById(R.id.noteList); //get the listview id made in MMGL

        //On click listener for the notes already made
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                Cursor cursor = openHelper.getSelectAllNotesCursor();

                Log.d(TAG, "Made it here");
                Note note = myNotes.get(position);
                Log.d(TAG, "Made it here2");
                String title = note.getTitle();
                Log.d(TAG, "Made it here3");
                String spinnerChoice = note.getType();
                Log.d(TAG, "Made it here4");
                String content = note.getContent();
                Log.d(TAG, "Made it here5");
                intent.putExtra("title", title);
                intent.putExtra("spinnerChoice", spinnerChoice);
                intent.putExtra("content", content);
                intent.putExtra("index", position); //TODO edit of note doesnt change the SQL

                startActivityForResult(intent, LOGIN_REQUEST_CODE);

            }
        });

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            //This function is for when we select mulitple items to display the number selected
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                int numChecked = listView.getCheckedItemCount();
                mode.setTitle(numChecked + " selected");
            }

            @Override
            //this takes place so the user can select multiple items
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater menuInflater = getMenuInflater();
                menuInflater.inflate(R.menu.delete_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            //this function takes place when a item in the list is selected
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch(item.getItemId()){
                    case R.id.deleteMenuItem:
                        Log.d(TAG, "made it here4");
                        SparseBooleanArray check = listView.getCheckedItemPositions();
                        NoteOpenHelper openHelper = new NoteOpenHelper(MainActivity.this);
                        Log.d(TAG, "made it here5");
                        for(int i = 0; i < check.size(); i++){
                            Log.d(TAG, "made it here7");
                            if(check.valueAt(i)){
                                Log.d(TAG, "made it here8" + cursorAdapter.getItemId(check.keyAt(i)));
                                int id = (int) cursorAdapter.getItemId(check.keyAt(i));
                                Log.d(TAG, "made it here9");
                                openHelper.deleteIndividualNote(id);
                            }
                        }
                        Log.d(TAG, "made it here6");
                        Cursor cursor = openHelper.getSelectAllNotesCursor();
                        cursorAdapter.changeCursor(cursor);
                        mode.finish();
                        return true;
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });

//        //On long click listener for the notes
//        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
//                String selection = parent.getItemAtPosition(position).toString();   //be able to reference the spot they long clicked
//                //TODO
//                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this); //prepare Alert Dialog
//                alertBuilder.setTitle("Delete a Note").setMessage("Are you sure you want to delete your " + selection + " note?")
//                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                myNotes.remove(position);
//                                arrayAdapter.notifyDataSetChanged();
//                            }
//                        }).setNegativeButton("NO", null);
//                alertBuilder.show();
//                return true;
//            }
//        });



        //create a simple cursor adapter to wire our list view to our database (e.g. cursor)
        cursorAdapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_activated_1,
                openHelper.getSelectAllNotesCursor(),
                //parallel arrays
                new String[] {NoteOpenHelper.NOTE_TITLE}, //name of  columns to get data from
                new int[] {android.R.id.text1}, //and insert it into the textView with id text1
                0
        );
        listView.setAdapter(cursorAdapter);
    }
}
