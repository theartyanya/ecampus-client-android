package ua.kpi.campus.Activity;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

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
        FrameLayout frame = new FrameLayout(this);
        frame.setId(CONTENT_VIEW_ID);
        setContentView(frame, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ConversationViewFragment conversationViewFragment = new ConversationViewFragment();
        fragmentTransaction.add(CONTENT_VIEW_ID, conversationViewFragment);
        //fragmentTransaction.add(CONTENT_VIEW_ID, keyboardFragment);
        fragmentTransaction.commit();
    }


}
