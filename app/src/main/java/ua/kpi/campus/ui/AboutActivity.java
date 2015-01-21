package ua.kpi.campus.ui;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

import ua.kpi.campus.R;
import ua.kpi.campus.model.AboutItem;
import ua.kpi.campus.ui.adapters.AboutListAdapter;
import ua.kpi.campus.util.ConfigUtils;

public class AboutActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ListView listView = (ListView) findViewById(R.id.about_list_view);
        
        ArrayList<AboutItem> about = new ArrayList<AboutItem>();
        AboutItem nameItem = new AboutItem();
        AboutItem versionItem = new AboutItem();
        AboutItem buildItem = new AboutItem();
        AboutItem typeItem = new AboutItem();
        AboutItem dateItem = new AboutItem();
        
        nameItem.setOptionName(getResources().getString(R.string.application_name))
            .setOptionDescription(getResources().getString(R.string.app_name));
        about.add(nameItem);

        versionItem.setOptionName(getResources().getString(R.string.build_version))
                .setOptionDescription(ConfigUtils.getVersionBuild(getApplicationContext()));
        about.add(versionItem);

        buildItem.setOptionName(getResources().getString(R.string.build_name))
                .setOptionDescription(ConfigUtils.VERSION_NAME);
        about.add(buildItem);

        typeItem.setOptionName(getResources().getString(R.string.build_type))
                .setOptionDescription(ConfigUtils.BUILD_TYPE);
        about.add(typeItem);

        dateItem.setOptionName(getResources().getString(R.string.build_date))
                .setOptionDescription(ConfigUtils.LATEST_BUILD);
        about.add(dateItem);
        
        AboutListAdapter adapter = new AboutListAdapter(getApplicationContext(), R.layout.item_about, about);
        listView.setAdapter(adapter);
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
