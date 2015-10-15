package ua.kpi.campus.ui.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ua.kpi.campus.R;

/**
 * Created by Давид on 17.03.2015.
 */
public class SecondActivity extends ActionBarActivity {

    private Button create;
    private Button cancel;
    private EditText name;
    private EditText description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_new);

        create = (Button) findViewById(R.id.okBtn);
        cancel = (Button) findViewById(R.id.cancelBtn);
        name = (EditText) findViewById(R.id.notesNameText);
        description = (EditText) findViewById(R.id.notesDescriptionText);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, SocialActivity.class);

                intent.putExtra("notesName", name.getText().toString());
                intent.putExtra("notesDescription", description.getText().toString());

                startActivity(intent);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, SocialActivity.class);
                startActivity(intent);
            }
        });
    }
}
