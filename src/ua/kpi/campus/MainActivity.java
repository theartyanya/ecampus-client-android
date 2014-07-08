package ua.kpi.campus;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.astuetz.PagerSlidingTabStrip;

import dny.android.Activity;
import dny.android.util.Listener;

public class MainActivity extends Activity {

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		final LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setBackgroundColor(0xccffffff);
		
		statusBarEmptySpaceSetting: {
			final int statusBarHeight = getStatusBarHeight();
			RelativeLayout statusBarEmptySpace = new RelativeLayout(this);
			statusBarEmptySpace.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				statusBarHeight
			));
			statusBarEmptySpace.setBackgroundColor(0xcc224444);
			layout.addView(statusBarEmptySpace);
		}

		final ArrayList<Page> pages = new ArrayList<Page>();
		final BoardPage boardPage = new BoardPage(this);
		pagesSetting: {
			pages.add(new InfoPage(this));
			pages.add(boardPage);
		}
		
		final ViewPager viewPager = new ViewPager(this);
		pagerAdapterSetting: {
			final PagerAdapter pagerAdapter = new PagerAdapter() {
				@Override public int getCount() {
					return 2;
				}
				@Override public boolean isViewFromObject(View p1, Object p2) {
					return p1.equals(p2);
				}
				@Override public Object instantiateItem(View collection, int position){
					View v = pages.get(position);
					((ViewPager)collection).addView(v, 0);
					return v;
				}
				@Override public void destroyItem(View collection, int position, Object view){
					((ViewPager)collection).removeView((View) view);
				}
				@Override public CharSequence getPageTitle(int position) {
					return pages.get(position).getTitle();
				}
			};
			viewPager.setAdapter(pagerAdapter);
		}
		
		titleSetting: {

			final LinearLayout title = new LinearLayout(this);
			final int titleHeight = (int)(Campus.density * 48);
			title.setOrientation(LinearLayout.HORIZONTAL);
			title.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT
			));
			title.setBackgroundColor(0x88448888);

			pagerTabStripSetting: {
				final PagerSlidingTabStrip tabStrip = new PagerSlidingTabStrip(this);
				tabStrip.setViewPager(viewPager);
				tabStrip.setLayoutParams(new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					titleHeight,
					1f
				));
				tabStrip.setTextColor(0xffffffff);
				tabStrip.setTextSize(16);
				tabStrip.setAllCaps(true);
				tabStrip.setIndicatorColor(0xffffffff);
				tabStrip.setDividerColor(0x88ffffff);
				title.addView(tabStrip);
			}

			filterButtonSetting: {
				Button button = new Button(this);
				button.setText("Фильтр");
				title.addView(button);
			}
				
			if (Campus.user.moder) newPostButtonSetting: {
				Button button = new Button(this);
				button.setText("Новое объявление");
				button.setOnClickListener(new Listener(new Runnable() {@Override public void run() {
					open(EditActivity.class, null, boardPage.refreshAction);
				}}));
				title.addView(button);
			}
			
			layout.addView(title);
			
		}
		
		viewPagerSetting: {
			viewPager.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT,
				1f
			));
			layout.addView(viewPager);
		}

		navBarEmptySpaceSetting: {
			final int navBarHeight = getNavBarHeight();
			RelativeLayout navBarEmptySpace = new RelativeLayout(this);
			navBarEmptySpace.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				navBarHeight
			));
			navBarEmptySpace.setBackgroundColor(0x88448888);
			layout.addView(navBarEmptySpace);
		}
		
		setContentView(layout);
		
	}
	
}
