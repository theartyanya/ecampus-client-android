package ua.kpi.campus.Activity.userlist;

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
public class UserListActivity  extends FragmentActivity {
    private static final int CONTENT_VIEW_ID = 105;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout frame = new LinearLayout(this);
        frame.setId(CONTENT_VIEW_ID);
        setContentView(frame, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        UserListFragment userListFragment = new UserListFragment();
        fragmentTransaction.add(CONTENT_VIEW_ID, userListFragment);
        fragmentTransaction.commit();
    }
}
