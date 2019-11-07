/**This code was created by Carter Mooring and Jackson Lindsay
 * PA6 Gina Sprint
 * CPSC 312
 * This function sets the layout of MainActivity.java
 * MyMainGridActivity.java
 */
package com.cartermooring.notetakingapp;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ListView;

public class MyMainGridLayout extends GridLayout {
    public MyMainGridLayout(final Context context){
        super(context);

        Button addNewNoteButton = new Button (context);    //create button
        addNewNoteButton.setText("ADD NEW NOTE");             //set button text
        ListView listView = new ListView(context);    //create List view


        GridLayout.Spec rowSpec = GridLayout.spec(0,1,(1/2));   //row start index, row span, row weight
        GridLayout.Spec colSpec = GridLayout.spec(0,1,1);
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(rowSpec, colSpec);
        layoutParams.setGravity(Gravity.TOP); //set things to appear at the top of the page

        GridLayout.Spec rowSpec1 = GridLayout.spec(1,1,(1));   //row start index, row span, row weight
        GridLayout.Spec colSpec1 = GridLayout.spec(0,1,1);
        GridLayout.LayoutParams layoutParams1 = new GridLayout.LayoutParams(rowSpec1, colSpec1);
        layoutParams1.setGravity(Gravity.TOP); //set things to appear at the top of the page

        addNewNoteButton.setId(R.id.newNote);   //set id for the button
        addNewNoteButton.setLayoutParams(layoutParams); //have the button use same layout as grid
        listView.setId(R.id.noteList);  //set id for list view
        listView.setLayoutParams(layoutParams1); //list view uses same layout as grid
        addView(addNewNoteButton); //add view button to screen
        addView(listView);  //add list view to grid layout
    }
}
