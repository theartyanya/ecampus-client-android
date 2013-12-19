package ua.kpi.campus.Activity;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import ua.kpi.campus.R;

/**
 * Class
 *
 * @author Artur Dzidzoiev
 * @version 12/19/13
 */
public class MessageActivity extends FragmentActivity {
    private static final int MESSAGES_FRAGMENT = 1;
    private static final int CONTENT_VIEW_ID = 100;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MessagesFragment fragment = new MessagesFragment();
        fragmentTransaction.add(R.layout.activity_message, fragment);
        fragmentTransaction.commit();
    }

}
