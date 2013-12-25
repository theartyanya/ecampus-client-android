package ua.kpi.campus.Activity.bulletinboard;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;

/**
 * Class
 *
 * @author Artur Dzidzoiev
 * @version 12/19/13
 */
public class BulletinActivity extends FragmentActivity {
    private static final int CONTENT_VIEW_ID = 102;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout frame = new LinearLayout(this);
        frame.setId(CONTENT_VIEW_ID);
        setContentView(frame, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        BulletinFragment bulletinFragment = new BulletinFragment();
        fragmentTransaction.add(CONTENT_VIEW_ID, bulletinFragment);
        //fragmentTransaction.add(CONTENT_VIEW_ID, keyboardFragment);
        fragmentTransaction.commit();
    }


}
