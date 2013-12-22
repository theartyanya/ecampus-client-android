package ua.kpi.campus.Activity.messanger;

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
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import org.json.JSONException;
import ua.kpi.campus.Activity.MainActivity;
import ua.kpi.campus.R;
import ua.kpi.campus.Session;
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
    private PullToRefreshListView mPullToRefreshView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_section_conversation_list, container, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ProgressBar progressBar = new ProgressBar(getActivity());
        progressBar.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        progressBar.setIndeterminate(true);
        getListView().setEmptyView(progressBar);

        Log.d(MainActivity.TAG, hashCode() + " onCreateView: fragment " + this.getClass().getName());
        List<Conversation> conversations = getFromDB();
        mAdapter = new ConversationListAdapter(getActivity(), conversations);
        setListAdapter(mAdapter);

        // Set a listener to be invoked when the list should be refreshed.
        mPullToRefreshView = (PullToRefreshListView) getActivity().findViewById(R.id.pull_to_refresh_listview);
        mPullToRefreshView.setOnRefreshListener(new OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                AsyncHttpClient client = new AsyncHttpClient();
                Log.d(this.getClass().getName(), hashCode() + " load started ");
                client.get(CampusApiURL.getConversations(Session.getSessionId()),
                        new TextHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, org.apache.http.Header[] headers, java.lang.String responseBody) {
                                Log.d(MainActivity.TAG, hashCode() + " received.");
                                ArrayList<UserConversationData> userConversationDatas = parseConversation(responseBody);
                                updateDB(userConversationDatas);
                                List<Conversation> conversations = getFromDB();
                                mAdapter = new ConversationListAdapter(getActivity(), conversations);
                                setListAdapter(mAdapter);
                                mPullToRefreshView.onRefreshComplete();
                            }
                        });
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
        UserConversationData item = (UserConversationData) getListAdapter().getItem(position);
        Log.d(MainActivity.TAG, hashCode() + " clicked on " + "l:" + l + " " + "v:" + v + " " + "position:" + position + " " + "id:" + id + " ");
        Intent intent = new Intent(getActivity(), MessageActivity.class);
        Log.d(MainActivity.TAG, hashCode() + " starting new activity... " + MessageActivity.class.getName());
        intent.putExtra(MessagesViewFragment.EXTRA_GROUP_ID, item.getGroupId());
        startActivity(intent);
    }

    private void updateDB(ArrayList<UserConversationData> conversations) {
        DatabaseHelper db = new DatabaseHelper(getActivity().getApplicationContext());
        try {
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
            db.addAllUsers(users);
        } finally {
            db.close();
        }
    }

    private List<Conversation> getFromDB() {
        DatabaseHelper db = new DatabaseHelper(getActivity().getApplicationContext());
        try {
            return db.getAllConversations();
        } finally {
            db.close();
        }
    }

}
