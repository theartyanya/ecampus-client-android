package ua.kpi.campus.ui.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import ua.kpi.campus.R;

/**
 * Created by Давид on 10.03.2015.
 */
public class SocialActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_main);

        Button btn = (Button) findViewById(R.id.newNote);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SocialActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

    }


}
