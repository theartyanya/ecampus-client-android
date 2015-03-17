package ua.kpi.campus.ui.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ua.kpi.campus.R;

/**
 * Created by Давид on 10.03.2015.
 */
public class SocialActivity extends ActionBarActivity {

    private Button newNote;
    private TextView name;
    private TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_main);

        newNote = (Button) findViewById(R.id.newNote);
        name = (TextView) findViewById(R.id.nametxt);
        description = (TextView) findViewById(R.id.descriptiontxt);

        newNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SocialActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        String txtName = getIntent().getStringExtra("notesName");
        String txtDescription = getIntent().getStringExtra("notesDescription");

        name.setText(name.getText().toString() + " " + txtName);
        description.setText(description.getText().toString() + " " + txtDescription);


    }


}
