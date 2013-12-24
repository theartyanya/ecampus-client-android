package ua.kpi.campus.Activity.messenger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.DrawerLayout.LayoutParams;
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
import ua.kpi.campus.api.jsonparsers.JSONConversationParser;
import ua.kpi.campus.api.jsonparsers.message.UserConversationData;
import ua.kpi.campus.api.jsonparsers.message.UserMessage;
import ua.kpi.campus.model.Conversation;
import ua.kpi.campus.model.dbhelper.DatabaseHelper;
import ua.kpi.campus.utils.pulltorefresh.PullToRefreshBase;
import ua.kpi.campus.utils.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import ua.kpi.campus.utils.pulltorefresh.PullToRefreshListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Message list
 *
 * @author Artur Dzidzoiev
 * @version 12/16/13
 */
public class ConversationListFragment extends ListFragment {
    public final static String TAG = MainActivity.TAG;
    private ArrayAdapter mAdapter;
    private String sessionId;
    private PullToRefreshListView mPullToRefreshView;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_section_conversation_list, container, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, hashCode() + " onCreateView: fragment " + this.getClass().getName());

        ProgressBar progressBar = new ProgressBar(getActivity());
        progressBar.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        progressBar.setIndeterminate(true);
        getListView().setEmptyView(progressBar);

        try (DatabaseHelper db = new DatabaseHelper(getActivity().getApplicationContext())) {
           sessionId = db.getSessionId();
        }

        // Set a listener to be invoked when the list should be refreshed.
        mPullToRefreshView = (PullToRefreshListView) getActivity().findViewById(R.id.pull_to_refresh_listview);
        mPullToRefreshView.setOnRefreshListener(new OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                loadData();
            }
        });
        mContext = getActivity().getApplicationContext();

        List<Conversation> conversations = getFromDB();
        if (conversations.isEmpty()) {
            loadData();
        }
        mAdapter = new ConversationListAdapter(getActivity(), conversations);
        setListAdapter(mAdapter);
    }

    private void loadData() {
        Log.d(TAG, hashCode() + " starting  AsyncHttpClient");
        AsyncHttpClient client = new AsyncHttpClient();
        Log.d(TAG, hashCode() + " load started " + CampusApiURL.getConversations(sessionId));
        client.get(CampusApiURL.getConversations(sessionId),
                new TextHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, org.apache.http.Header[] headers, java.lang.String responseBody) {
                        Log.d(TAG, hashCode() + " received.");
                        ArrayList<UserConversationData> userConversationDatas = parseConversation(responseBody);
                        updateDB(userConversationDatas);
                        List<Conversation> conversations = getFromDB();
                        mAdapter = new ConversationListAdapter(getActivity(), conversations);
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

    private ArrayList<UserConversationData> parseConversation(String JsonConversation) {
        try {
            return JSONConversationParser.parse(JsonConversation).getData();
        } catch (JSONException e) {
            Log.e(this.getClass().getName(), hashCode() + getResources().getString(R.string.login_activity_json_error));
        }
        return new ArrayList<>();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Conversation item = (Conversation) getListAdapter().getItem(position-1);
        Log.d(TAG, hashCode() + " clicked on " + "l:" + l + " " + "v:" + v + " " + "position:" + (position-1)  + " " + "id:" + id + " ");
        Intent intent = new Intent(getActivity(), MessageActivity.class);
        Log.d(TAG, hashCode() + " starting new activity... " + MessageActivity.class.getName());
        intent.putExtra(MessagesViewFragment.EXTRA_GROUP_ID, item.getGroupId());
        startActivity(intent);
    }

    private void updateDB(ArrayList<UserConversationData> conversations) {
        try (DatabaseHelper db = new DatabaseHelper(getActivity().getApplicationContext())) {
            HashSet<UserMessage> users = new HashSet<>();
            db.refreshConversations();
            Log.d(TAG, hashCode() + db.getPath());

            //adding conversations to db and users to set
            for (UserConversationData conversation : conversations) {
                db.createConversation(new Conversation(conversation));
                for (UserMessage user : conversation.getUsers()) {
                    users.add(user);
                }
            }
            //todo fix bug with adding existing users
            db.addAllUsers(users);
        }
    }

    private List<Conversation> getFromDB() {
        try (DatabaseHelper db = new DatabaseHelper(getActivity().getApplicationContext())) {
            return db.getAllConversations();
        }
    }

}
