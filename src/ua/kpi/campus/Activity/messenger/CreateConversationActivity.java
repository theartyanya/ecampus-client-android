package ua.kpi.campus.Activity.messenger;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Class
 *
 * @author Artur Dzidzoiev
 * @version 12/25/13
 */
public class CreateConversationActivity extends FragmentActivity {
    public final static String EXTRA_USERS = "23q4";
    private static final int CONTENT_VIEW_ID = 1035;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout frame = new LinearLayout(this);
        frame.setId(CONTENT_VIEW_ID);
        setContentView(frame, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CreateConversationFragment messagesViewFragment = new CreateConversationFragment();
        fragmentTransaction.add(CONTENT_VIEW_ID, messagesViewFragment);
        //fragmentTransaction.add(CONTENT_VIEW_ID, keyboardFragment);
        fragmentTransaction.commit();
    }
}
