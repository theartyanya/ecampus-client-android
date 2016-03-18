package ua.kpi.campus;

import java.util.ArrayList;

import android.graphics.BitmapFactory;
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
import dny.android.util.ListenerAdapter;
import dny.android.widgets.FlatButton;
import ua.kpi.campus.activity.messenger.MessagePage;

public class MainActivity extends Activity {
	
	private Filter filter = new Filter();

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		final LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setBackgroundColor(0xf0ffffff);
		
		statusBarEmptySpaceSetting: {
			final int statusBarHeight = getStatusBarHeight();
			RelativeLayout statusBarEmptySpace = new RelativeLayout(this);
			statusBarEmptySpace.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				statusBarHeight
			));
			statusBarEmptySpace.setBackgroundColor(0xff336666);
			layout.addView(statusBarEmptySpace);
		}

		final ArrayList<Page> pages = new ArrayList<Page>();
		final BoardPage boardPage = new BoardPage(this, filter);
		pagesSetting: {
			pages.add(new InfoPage(this));
			pages.add(boardPage);
            pages.add(new MessagePage(this));
		}
		
		final ViewPager viewPager = new ViewPager(this);
		pagerAdapterSetting: {
			final PagerAdapter pagerAdapter = new PagerAdapter() {
				@Override public int getCount() {
					return 3;
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
			final int titleHeight = (int)(ThisApp.density * 48);
			title.setOrientation(LinearLayout.HORIZONTAL);
			title.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT
			));
			title.setBackgroundColor(0xff448888);

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
				FlatButton button = new FlatButton(
					BitmapFactory.decodeResource(getResources(), R.drawable.search),
					new Runnable() {@Override public void run() {
						open(
							FilterActivity.class, 
							boardPage.filter, 
							new Runnable() {@Override public void run() {
								boardPage.refreshPage();
							}}
						);
					}},
					this
				);
				title.addView(button);
			}
				
			if (CampusAPI.user.moder) newPostButtonSetting: {
				FlatButton button = new FlatButton(
					BitmapFactory.decodeResource(getResources(), R.drawable.new_post),
					new Runnable() {@Override public void run() {
						open(EditActivity.class, null, boardPage);
					}},
					this
				);
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
			navBarEmptySpace.setBackgroundColor(0xff448888);
			layout.addView(navBarEmptySpace);
		}
		
		setContentView(layout);
		
	}
	
}
