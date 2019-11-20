/**
 * Carter Mooring and Jackson Lindsay
 * PA7 Gina Sprint
 * CPSC 312
 * This function is the main code for the main screen
 * NoteOpenHelper.java
 */
package com.cartermooring.notetakingapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class NoteOpenHelper extends SQLiteOpenHelper {
    static final String TAG = "SQLiteTag";

    static final String DATABASE_NAME = "notesDatabase";
    static final int DATABASE_VERSION = 1;

    static final String TABLE_NOTES = "tableNotes";
    static final String ID = "_id";
    static final String NOTE_TITLE = "noteTitle";
    static final String NOTE_CONTENT = "noteContent";
    static final String NOTE_TYPE = "noteType";

    //what sql needs to open our database
    public NoteOpenHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //this is where we create our database tables
        // this method is only called once... called after
        //  first call to getWritableDatabase()
        //we consruct strings to represent SQL(structured query language)
        // commands/statements

        //CREATE TABLE tableContacts(_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, phoneNumber TEXT, imageResource INTEGER)
        String sqlCreate = "CREATE TABLE " + TABLE_NOTES + "( " +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NOTE_TITLE + " TEXT, " + NOTE_CONTENT + " TEXT, " +
                NOTE_TYPE + " TEXT)";
        Log.d(TAG, "onCreate: " + sqlCreate);
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //helper function to insert notes into SQL database
    public void insertNote(Note note){
        //INSERT INTO tableNotes VALUES(null, 'Title', 'Content', 'Type')
        String sqlInsert = "INSERT INTO " + TABLE_NOTES + " VALUES(null, '" + note.getTitle() + "', '" +
                note.getContent() + "', '" + note.getType() + "')";
        //get a reference to the database that is writable
        SQLiteDatabase db = getWritableDatabase();
        Log.d(TAG, "insertContact: " + sqlInsert + " db: " + db);
        db.execSQL(sqlInsert);
        //always close the writable database
        db.close();
    }

    //helper function to have a cursor move through notes into SQL database
    public Cursor getSelectAllNotesCursor(){
        //SELECT *  FROM  tableNotes
        String sqlSelect = "SELECT * FROM " + TABLE_NOTES;
        //get a reference to a database
        SQLiteDatabase db = getReadableDatabase();
        Log.d(TAG, "getSelectAllNotesCursor: " + sqlSelect);
        //use rawQuery() to execute the select query
        //queries return a Cursor reference
        //we will walk through the query result set using the Cursor
        Cursor cursor = db.rawQuery(sqlSelect, null);
        //dont close the database!!  the cursor needs it open
        return cursor;
    }

    //for debug purposes only!!
    //for PA7, you will use SimpleCursorAdapter
    //to wire a list view to a database
//    public List<Contact> getSelectAllContactsList(){
//        List<Contact> contactList = new ArrayList<>();
//
//        //goal: walk through each record with a select all cursor
//        //create a Contact for the record
//        //add the Contact to the contactLast
//        Cursor cursor = getSelectAllContactsCursor();
//        //cursor starts "before" the record in case there is no first record
//        while (cursor.moveToNext()) {    //return false when there are no more records
//            //extract values
//            int id = cursor.getInt(0);
//            String name = cursor.getString(1);
//            String phoneNumber = cursor.getString(2);
//            int imageResource = cursor.getInt(3);
//            Contact contact = new Contact(id, name, phoneNumber, imageResource);
//            contactList.add(contact);
//        }
//        return contactList;
//    }

    //helper function to update notes into SQL database
    public void updateNoteById(int id, Note newNote){
        //UPDATE tableNotes SET title='title', content='content' WHERE _id=1
        String sqlUpdate = "UPDATE " + TABLE_NOTES + " SET " + NOTE_TITLE + " = '" +
                newNote.getTitle() + "', " + NOTE_TYPE + " = '" +
                newNote.getType() + "', " + NOTE_CONTENT +  " = '" +
                newNote.getContent() + "' WHERE " + ID + " = " + id;
        Log.d(TAG, "updateNotesById " + sqlUpdate);
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlUpdate);
        db.close();
    }

    //helper function to delete notes into SQL database
    public void deleteAllNotes(){
        //DELETE FROM tableNotes
        String sqlDelete = "DELETE FROM " + TABLE_NOTES;
        Log.d(TAG, "deleteAllNotes" + sqlDelete);
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlDelete);
        db.close();
    }

    //helper function to delete selected notes into SQL database
    public void deleteIndividualNote(int id){
        Log.d(TAG, "made it here");
        String sqlDelete = "DELETE FROM " + TABLE_NOTES +
                " WHERE " + ID + " = " + id;
        Log.d(TAG, "made it here2");
        SQLiteDatabase db = getWritableDatabase();
        Log.d(TAG, "made it here3");
        db.execSQL(sqlDelete);
        db.close();
    }
}
