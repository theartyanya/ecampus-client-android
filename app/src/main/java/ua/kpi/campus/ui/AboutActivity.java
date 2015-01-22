package ua.kpi.campus.ui;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.r0adkll.slidr.Slidr;

import java.util.ArrayList;

import ua.kpi.campus.R;
import ua.kpi.campus.model.AboutItem;
import ua.kpi.campus.model.DeveloperItem;
import ua.kpi.campus.ui.adapters.AboutListAdapter;
import ua.kpi.campus.ui.adapters.DevelopersAdapter;
import ua.kpi.campus.util.ConfigUtils;

public class AboutActivity extends ActionBarActivity implements ObservableScrollViewCallbacks {

    private static final float MAX_TEXT_SCALE_DELTA = 0.3f;
    private static final boolean TOOLBAR_IS_STICKY = false;
    private View mToolbar;
    private View mImageView;
    private View mOverlayView;
    private ObservableScrollView mScrollView;
    private TextView mTitleView;
    private View mFab;
    private int mActionBarSize;
    private int mFlexibleSpaceShowFabOffset;
    private int mFlexibleSpaceImageHeight;
    private int mFabMargin;
    private int mToolbarColor;
    private boolean mFabIsShown;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_next);

        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ListView devList = (ListView) findViewById(R.id.dev_list_view);
        
        ArrayList<DeveloperItem> developers = new ArrayList<DeveloperItem>();
        DeveloperItem dnihze = new DeveloperItem();
        DeveloperItem deepnekro = new DeveloperItem();
        dnihze.setOptionName(getResources().getString(R.string.dnihze))
              .setOptionDescription("Development & Design")
              .setLink("https://github.com/doroshartyom")
              .setPhoto(R.drawable.dev_1);
        deepnekro.setOptionName(getResources().getString(R.string.deepnekroz))
                  .setOptionDescription("Development & API")
                  .setLink("https://github.com/Deepnekroz")
                  .setPhoto(R.drawable.dev_2);
        
        developers.add(dnihze);
        developers.add(deepnekro);

        DevelopersAdapter developersAdapter = new DevelopersAdapter(this, R.layout.item_developer, developers);
        devList.setAdapter(developersAdapter);
        
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
        
        mFlexibleSpaceImageHeight = getResources().getDimensionPixelSize(R.dimen.flexible_space_image_height);
        mFlexibleSpaceShowFabOffset = getResources().getDimensionPixelSize(R.dimen.flexible_space_show_fab_offset);
        mActionBarSize = getActionBarSize();
        mToolbarColor = getResources().getColor(R.color.primary);
        mToolbar = findViewById(R.id.toolbar);
        if (!TOOLBAR_IS_STICKY) {
            mToolbar.setBackgroundColor(Color.TRANSPARENT);
        }
        mImageView = findViewById(R.id.image);
        mOverlayView = findViewById(R.id.overlay);
        mScrollView = (ObservableScrollView) findViewById(R.id.scroll);
        mScrollView.setScrollViewCallbacks(this);
        mTitleView = (TextView) findViewById(R.id.title);
        mTitleView.setText(R.string.title_activity_about);
        setTitle(null);
        mFab = findViewById(R.id.fab);
        mFabMargin = getResources().getDimensionPixelSize(R.dimen.keyline_1);
        ViewHelper.setScaleX(mFab, 0);
        ViewHelper.setScaleY(mFab, 0);
        ScrollUtils.addOnGlobalLayoutListener(mScrollView, new Runnable() {
            @Override
            public void run() {
                mScrollView.scrollTo(0, mFlexibleSpaceImageHeight - mActionBarSize);
            }
        });

        //Attaching this activity to Slidr
        Slidr.attach(this);
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

    @Override
    public void onScrollChanged(int scrollY, boolean b, boolean b2) {
        // Translate overlay and image
        float flexibleRange = mFlexibleSpaceImageHeight - mActionBarSize;
        int minOverlayTransitionY = mActionBarSize - mOverlayView.getHeight();
        ViewHelper.setTranslationY(mOverlayView, ScrollUtils.getFloat(-scrollY, minOverlayTransitionY, 0));
        ViewHelper.setTranslationY(mImageView, ScrollUtils.getFloat(-scrollY / 2, minOverlayTransitionY, 0));
// Change alpha of overlay
        ViewHelper.setAlpha(mOverlayView, ScrollUtils.getFloat((float) scrollY / flexibleRange, 0, 1));
// Scale title text
        float scale = 1 + ScrollUtils.getFloat((flexibleRange - scrollY) / flexibleRange, 0, MAX_TEXT_SCALE_DELTA);
        ViewHelper.setPivotX(mTitleView, 0);
        ViewHelper.setPivotY(mTitleView, 0);
        ViewHelper.setScaleX(mTitleView, scale);
        ViewHelper.setScaleY(mTitleView, scale);
// Translate title text
        int maxTitleTranslationY = (int) (mFlexibleSpaceImageHeight - mTitleView.getHeight() * scale);
        int titleTranslationY = maxTitleTranslationY - scrollY;
        if (TOOLBAR_IS_STICKY) {
            titleTranslationY = Math.max(0, titleTranslationY);
        }
        ViewHelper.setTranslationY(mTitleView, titleTranslationY);
// Translate FAB
        int maxFabTranslationY = mFlexibleSpaceImageHeight - mFab.getHeight() / 2;
        float fabTranslationY = ScrollUtils.getFloat(
                -scrollY + mFlexibleSpaceImageHeight - mFab.getHeight() / 2,
                mActionBarSize - mFab.getHeight() / 2,
                maxFabTranslationY);
        ViewHelper.setTranslationX(mFab, mOverlayView.getWidth() - mFabMargin - mFab.getWidth());
        ViewHelper.setTranslationY(mFab, fabTranslationY);
// Show/hide FAB
        if (ViewHelper.getTranslationY(mFab) < mFlexibleSpaceShowFabOffset) {
            hideFab();
        } else {
            showFab();
        }
        if (TOOLBAR_IS_STICKY) {
// Change alpha of toolbar background
            if (-scrollY + mFlexibleSpaceImageHeight <= mActionBarSize) {
                mToolbar.setBackgroundColor(ScrollUtils.getColorWithAlpha(1, mToolbarColor));
            } else {
                mToolbar.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, mToolbarColor));
            }
        } else {
// Translate Toolbar
            if (scrollY < mFlexibleSpaceImageHeight) {
                ViewHelper.setTranslationY(mToolbar, 0);
            } else {
                ViewHelper.setTranslationY(mToolbar, -scrollY);
            }
        }
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }

    private void showFab() {
        if (!mFabIsShown) {
            ViewPropertyAnimator.animate(mFab).cancel();
            ViewPropertyAnimator.animate(mFab).scaleX(1).scaleY(1).setDuration(200).start();
            mFabIsShown = true;
        }
    }
    private void hideFab() {
        if (mFabIsShown) {
            ViewPropertyAnimator.animate(mFab).cancel();
            ViewPropertyAnimator.animate(mFab).scaleX(0).scaleY(0).setDuration(200).start();
            mFabIsShown = false;
        }
    }

    protected int getActionBarSize() {
        TypedValue typedValue = new TypedValue();
        int[] textSizeAttr = new int[]{R.attr.actionBarSize};
        int indexOfAttrTextSize = 0;
        TypedArray a = obtainStyledAttributes(typedValue.data, textSizeAttr);
        int actionBarSize = a.getDimensionPixelSize(indexOfAttrTextSize, -1);
        a.recycle();
        return actionBarSize;
    }
}
