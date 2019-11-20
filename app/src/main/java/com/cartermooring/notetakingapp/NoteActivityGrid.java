/**This code was created by Carter Mooring and Jackson Lindsay
 * PA6 Gina Sprint
 * CPSC 312
 * This function is the code that sets up the user interface for NoteActivity.java
 * NoteActivityGrid.java
 */

//credits
//<div>Icons made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>
//<div>Icons made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>
//<div>Icons made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>
//<div>Icons made by <a href="https://www.flaticon.com/authors/popcorns-arts" title="Icon Pond">Icon Pond</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>
package com.cartermooring.notetakingapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class NoteActivityGrid extends GridLayout {

    public NoteActivityGrid(final Context context){
        super(context);
        setColumnCount(2);


        //TITLE EDIT TEXT PARAMETERS
        GridLayout.Spec rowSpec = GridLayout.spec(0, 1, 1/2);
        GridLayout.Spec colSpec = GridLayout.spec(0, 1, 1);
        GridLayout.LayoutParams layoutParams = new LayoutParams(rowSpec, colSpec);

        EditText titleEditText = new EditText(context);
        titleEditText.setHint("Title");
        titleEditText.setLayoutParams(layoutParams);
        titleEditText.setId(R.id.titleEditText);
        addView(titleEditText);

        //SPINNER PARAMETERS
        GridLayout.Spec rowSpec3 = GridLayout.spec(0, 1, 1/2);
        GridLayout.Spec colSpec3 = GridLayout.spec(1, 1, 1/2);
        GridLayout.LayoutParams layoutParams3 = new LayoutParams(rowSpec3, colSpec3);

        Spinner spinner = new Spinner(context);
        String[] mailType = new String[4];
        mailType[0] = "WORK";
        mailType[1] = "SCHOOL";
        mailType[2] = "PERSONAL";
        mailType[3] = "OTHER";

        ArrayAdapter<String> mailAdapter = new ArrayAdapter<String>(context, android.R.layout.activity_list_item, android.R.id.text1, mailType){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                //String iconString = mailType[position];
                ImageView imageView = (ImageView) view.findViewById(android.R.id.icon);
                imageView.setImageResource(R.drawable.work);
                switch (position){
                    case 0:
                        imageView.setImageResource(R.drawable.work);
                        break;

                    case 1:
                        imageView.setImageResource(R.drawable.school);
                        break;

                    case 2:
                        imageView.setImageResource(R.drawable.personal);
                        break;

                    case 3:
                        imageView.setImageResource(R.drawable.other);
            }

            return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = super.getDropDownView(position, convertView, parent);
        ImageView imageView = (ImageView) view.findViewById(android.R.id.icon);
        imageView.setImageResource(R.drawable.work);
        switch (position){
            case 0:
                imageView.setImageResource(R.drawable.work);
                break;

            case 1:
                imageView.setImageResource(R.drawable.school);
                break;

            case 2:
                imageView.setImageResource(R.drawable.personal);
                break;

            case 3:
                imageView.setImageResource(R.drawable.other);
        }

        return view;
    }
        };
        spinner.setAdapter(mailAdapter);
        spinner.setGravity(Gravity.RIGHT);
        spinner.setId(R.id.mailTypeSpinner);
        spinner.setLayoutParams(layoutParams3);
        addView(spinner);

        //CONTENT EDIT TEXT PARAMETERS
        GridLayout.Spec rowSpec1 = GridLayout.spec(1, 1, 1);
        GridLayout.Spec colSpec1 = GridLayout.spec(0, 2, 1);
        GridLayout.LayoutParams layoutParams1 = new LayoutParams(rowSpec1, colSpec1);

        EditText contentEditText = new EditText(context);
        contentEditText.setHint("Content");
        contentEditText.setLayoutParams(layoutParams1);
        contentEditText.setGravity(Gravity.TOP);
        contentEditText.setId(R.id.contentEditText);
        addView(contentEditText);

        //BUTTON PARAMETERS
        GridLayout.Spec rowSpec2 = GridLayout.spec(2, 1, 1/2);
        GridLayout.Spec colSpec2 = GridLayout.spec(0, 2, 1);
        GridLayout.LayoutParams layoutParams2 = new LayoutParams(rowSpec2, colSpec2);

        Button doneButton = new Button(context);
        doneButton.setText("Done");
        doneButton.setLayoutParams(layoutParams2);
        doneButton.setId(R.id.doneButton);
        addView(doneButton);

    }
}
