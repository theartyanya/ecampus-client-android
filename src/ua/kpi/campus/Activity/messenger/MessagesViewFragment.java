package ua.kpi.campus.Activity.messenger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;
import org.json.JSONException;
import ua.kpi.campus.Activity.MainActivity;
import ua.kpi.campus.R;
import ua.kpi.campus.api.CampusApiURL;
import ua.kpi.campus.api.jsonparsers.JSONMessageGetItemParser;
import ua.kpi.campus.api.jsonparsers.message.MessageItem;
import ua.kpi.campus.model.Message;
import ua.kpi.campus.model.dbhelper.DatabaseHelper;
import ua.kpi.campus.utils.pulltorefresh.PullToRefreshBase;
import ua.kpi.campus.utils.pulltorefresh.PullToRefreshListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Class
 *
 * @author Artur Dzidzoiev
 * @version 12/19/13
 */
public class MessagesViewFragment extends ListFragment{
    public final static String TAG = MainActivity.TAG;
    public final static String EXTRA_GROUP_ID = "groupId";

    private BaseAdapter mAdapter;
    private Set<Message> messages;
    private int groupId;
    private String sessionId;

    private Button sendButton;
    private EditText messageInput;
    private PullToRefreshListView mPullToRefreshView;

    private View.OnClickListener sendButtonListener = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            Log.d(MainActivity.TAG, hashCode() + " send click!");
            String input = messageInput.getText().toString();
            messageInput.setText("");
            Log.d(MainActivity.TAG, hashCode() + " sending message: " + input);
            sendMessage(input);
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_messages, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(MainActivity.TAG, hashCode() + " onActivityCreated: fragment " + this.getClass().getName());
        View footer = getLayoutInflater(savedInstanceState).inflate(R.layout.messages_footer, null);
        ListView listView = getListView();
        listView.addFooterView(footer);
        listView.setStackFromBottom(true);

        sendButton = (Button) getActivity().findViewById(R.id.sendButton);
        sendButton.setOnClickListener(sendButtonListener);
        messageInput = (EditText) getActivity().findViewById(R.id.messageText);

        //loading progress bar
        ProgressBar progressBar = new ProgressBar(getActivity());
        progressBar.setLayoutParams(new DrawerLayout.LayoutParams(DrawerLayout.LayoutParams.WRAP_CONTENT,
                DrawerLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        progressBar.setIndeterminate(true);
        getListView().setEmptyView(progressBar);

        //loading stored data
        try (DatabaseHelper db = new DatabaseHelper(getActivity().getApplicationContext())) {
            sessionId = db.getSessionId();
        }
        Intent intent = getActivity().getIntent();
        groupId = (int) intent.getExtras().get(EXTRA_GROUP_ID);

        // Set a listener to be invoked when the list should be refreshed.
        mPullToRefreshView = (PullToRefreshListView) getActivity().findViewById(R.id.pull_to_refresh_listview);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                loadDataToDB();
                messages.addAll(getFromDB());
            }
        });

        messages = getFromDB();
        if (messages.isEmpty()) {
            loadDataToDB();
            messages.addAll(getFromDB());
        }
        mAdapter = new MessagesViewAdapter(getActivity(), messages);
        setListAdapter(mAdapter);
    }

    private Set<Message> getFromDB() {
        Set<Message> messageSet;
        try (DatabaseHelper db = new DatabaseHelper(getActivity().getApplicationContext())) {
            messageSet = db.getLastMessages(groupId);
        }
        mPullToRefreshView.onRefreshComplete();
        return messageSet;
    }

    private void loadDataToDB() {
        Log.d(this.getClass().getName(), hashCode() + " starting  AsyncHttpClient");
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(CampusApiURL.getConversation(sessionId, groupId, 1, getResources().getInteger(R.integer.messages_size_page)),
                new TextHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, org.apache.http.Header[] headers, java.lang.String responseBody) {
                        Log.d(MainActivity.TAG, hashCode() + " received.");
                        ArrayList<MessageItem> userConversationDatas = parseConversation(responseBody);
                        Set<Message> messageSet = new HashSet<>();
                        for (MessageItem messageItem : userConversationDatas) {
                            messageSet.add(new Message(messageItem));
                        }
                        try (DatabaseHelper db = new DatabaseHelper(getActivity().getApplicationContext())) {
                            db.addAllMessages(messageSet,groupId);
                        }
                    }
                });
    }

    private void sendMessage(String input) {
        input = input.replaceAll("\\s+", "%20");
        if (!input.isEmpty()) {
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(CampusApiURL.sendMessage(sessionId, groupId, input, ""), new AsyncHttpResponseHandler() {


                @Override
                public void onSuccess(String response) {
                    Log.d(MainActivity.TAG, hashCode() + " sent... ");
                }
            });
        }
    }

    private ArrayList<MessageItem> parseConversation(String JsonConversation) {
        try {
            return JSONMessageGetItemParser.parse(JsonConversation).getData();
        } catch (JSONException e) {
            Log.e(this.getClass().getName(), hashCode() + getResources().getString(R.string.login_activity_json_error));
        }
        return new ArrayList<>();
    }

}