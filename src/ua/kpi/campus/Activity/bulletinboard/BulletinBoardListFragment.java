package ua.kpi.campus.Activity.bulletinboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import org.json.JSONException;
import ua.kpi.campus.Activity.MainActivity;
import ua.kpi.campus.R;
import ua.kpi.campus.api.CampusApiURL;
import ua.kpi.campus.api.jsonparsers.JSONBulletinBoardParser;
import ua.kpi.campus.model.BulletinBoardSubject;
import ua.kpi.campus.model.dbhelper.BulletinBoardBase;
import ua.kpi.campus.model.dbhelper.DatabaseHelper;
import ua.kpi.campus.utils.pulltorefresh.PullToRefreshBase;
import ua.kpi.campus.utils.pulltorefresh.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Class
 *
 * @author Artur Dzidzoiev
 * @version 12/25/13
 */
public class BulletinBoardListFragment extends ListFragment {
    public final static String TAG = MainActivity.TAG;
    private ArrayAdapter mAdapter;
    private String sessionId;
    private PullToRefreshListView mPullToRefreshView;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_section_bulletin_board, container, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, hashCode() + " onCreateView: fragment " + this.getClass().getName());

        ProgressBar progressBar = new ProgressBar(getActivity());
        progressBar.setLayoutParams(new DrawerLayout.LayoutParams(DrawerLayout.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        progressBar.setIndeterminate(true);
        getListView().setEmptyView(progressBar);

        try (DatabaseHelper db = DatabaseHelper.getInstance()) {
            sessionId = db.getSessionId();
        }

        // Set a listener to be invoked when the list should be refreshed.
        mPullToRefreshView = (PullToRefreshListView) getActivity().findViewById(R.id.pull_to_refresh_listview);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                loadData();
            }
        });
        mContext = getActivity().getApplicationContext();

        List<BulletinBoardSubject> bulletinBoardSubjects = getFromDB();
        if (bulletinBoardSubjects.isEmpty()) {
            loadData();
        }
        mAdapter = new BulletinBoardListAdapter(getActivity(), bulletinBoardSubjects);
        setListAdapter(mAdapter);
    }

    private void loadData() {
        Log.d(TAG, hashCode() + " starting  AsyncHttpClient");
        AsyncHttpClient client = new AsyncHttpClient();
        Log.d(TAG, hashCode() + " load started " + CampusApiURL.getActual(sessionId));
        client.get(CampusApiURL.getActual(sessionId),
                new TextHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, org.apache.http.Header[] headers, java.lang.String responseBody) {
                        Log.d(TAG, hashCode() + " received.");
                        ArrayList<BulletinBoardSubject> boardSubjects = parseBulletinBoard(responseBody);
                        updateDB(boardSubjects);
                        List<BulletinBoardSubject> subjects = getFromDB();
                        mAdapter = new BulletinBoardListAdapter(getActivity(), subjects);
                        setListAdapter(mAdapter);
                        mPullToRefreshView.onRefreshComplete();
                    }

                    @Override
                    public void onFailure(int statusCode, org.apache.http.Header[] headers, java.lang.String responseBody, java.lang.Throwable error) {
                        Log.d(TAG, hashCode() + " fail " + statusCode);
                        mPullToRefreshView.onRefreshComplete();
                        Toast.makeText(mContext, mContext.getString(R.string.access_denied_code, statusCode), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private ArrayList<BulletinBoardSubject> parseBulletinBoard(String JsonConversation) {
        try {
            return JSONBulletinBoardParser.parse(JsonConversation).getData();
        } catch (JSONException e) {
            Log.e(this.getClass().getName(), hashCode() + getResources().getString(R.string.login_activity_json_error));
        }
        return new ArrayList<>();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.d(TAG, hashCode() + " clicked on " + "l:" + l + " " + "v:" + v + " " + "position:" + (position-1)  + " " + "id:" + id + " ");
        Intent intent = new Intent(getActivity(), BulletinActivity.class);
        Log.d(TAG, hashCode() + " starting new activity... " + BulletinActivity.class.getName());
        intent.putExtra(BulletinFragment.EXTRA_BULLETIN_ID,((BulletinBoardSubject) l.getItemAtPosition(position)).getBulletinBoardId());
        startActivity(intent);
    }

    private void updateDB(ArrayList<BulletinBoardSubject> subjects) {
        try (BulletinBoardBase db = BulletinBoardBase.getInstance()) {
            db.addAllBulletins(subjects);
        }
    }

    private List<BulletinBoardSubject> getFromDB() {
        try (BulletinBoardBase db = BulletinBoardBase.getInstance()) {
            return db.getActualBulletins();
        }
    }


}
