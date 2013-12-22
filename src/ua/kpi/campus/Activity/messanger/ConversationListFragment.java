package ua.kpi.campus.Activity.messanger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.DrawerLayout.LayoutParams;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import org.json.JSONException;
import ua.kpi.campus.Activity.MainActivity;
import ua.kpi.campus.R;
import ua.kpi.campus.Session;
import ua.kpi.campus.api.CampusApiURL;
import ua.kpi.campus.api.jsonparsers.JSONConversationParser;
import ua.kpi.campus.api.jsonparsers.message.UserConversationData;
import ua.kpi.campus.api.jsonparsers.message.UserMessage;
import ua.kpi.campus.loaders.HttpResponse;
import ua.kpi.campus.loaders.HttpStringLoader;
import ua.kpi.campus.loaders.HttpStringSupportLoader;
import ua.kpi.campus.model.Conversation;
import ua.kpi.campus.model.dbhelper.DatabaseHelper;
import ua.kpi.campus.utils.pulltorefresh.PullToRefreshBase;
import ua.kpi.campus.utils.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import ua.kpi.campus.utils.pulltorefresh.PullToRefreshListView;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Message list
 *
 * @author Artur Dzidzoiev
 * @version 12/16/13
 */
public class ConversationListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<HttpResponse> {
    public final static String TAG = MainActivity.TAG;
    private final static int CONVERSATION_LOADER_ID = 153;
    private LoaderManager.LoaderCallbacks<HttpResponse> mCallbacks;
    private LoaderManager loaderManager;
    private ArrayAdapter mAdapter;

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
                LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        progressBar.setIndeterminate(true);
        getListView().setEmptyView(progressBar);

        Log.d(MainActivity.TAG, hashCode() + " onCreateView: fragment " + this.getClass().getName());
        mCallbacks = this;
        loaderManager = getLoaderManager();
        Bundle url = new Bundle();
        url.putString(HttpStringLoader.URL_STRING, CampusApiURL.getConversations(Session.getSessionId()));
        loaderManager.initLoader(CONVERSATION_LOADER_ID, url, mCallbacks).onContentChanged();

        // Set a listener to be invoked when the list should be refreshed.
        PullToRefreshListView pullToRefreshView = (PullToRefreshListView) getActivity().findViewById(R.id.pull_to_refresh_listview);
        pullToRefreshView.setOnRefreshListener(new OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                Log.d(MainActivity.TAG, hashCode() + " refreshing.");
            }
        });
    }



    private ArrayList<UserConversationData> parseConversation (String JsonConversation) {
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
        Log.d(MainActivity.TAG, hashCode() + " clicked on "+ "l:" + l +" "+ "v:" + v +" "+ "position:" + position +" "+ "id:" + id +" ");
        Intent intent = new Intent(getActivity(), MessageActivity.class);
        Log.d(MainActivity.TAG, hashCode() + " starting new activity... " + MessageActivity.class.getName());
        intent.putExtra(MessagesViewFragment.EXTRA_GROUP_ID, item.getGroupId());
        startActivity(intent);
    }

    @Override
    public Loader<HttpResponse> onCreateLoader(int i, Bundle bundle) {
        Log.d(this.getClass().getName(), hashCode() + " load started " + i);
        return new HttpStringSupportLoader(getActivity(), bundle.getString(HttpStringLoader.URL_STRING));
    }

    @Override
    public void onLoadFinished(Loader<HttpResponse> httpResponseLoader, HttpResponse httpResponse) {
        String unparsed = httpResponse.getEntity();
        ArrayList<UserConversationData> conversations = parseConversation(unparsed);
        updateDB(conversations);
        mAdapter = new ConversationListAdapter(getActivity(), conversations);
        setListAdapter(mAdapter);
    }

    private void updateDB(ArrayList<UserConversationData> conversations) {
        DatabaseHelper db = new DatabaseHelper(getActivity().getApplicationContext());
        HashSet<UserMessage> users = new HashSet<>();
        db.refreshConversations();
        Log.d(TAG, hashCode() + db.getPath());

        //adding conversations to db and users to set
        for(UserConversationData conversation : conversations) {
            db.createConversation(new Conversation(conversation));
            for(UserMessage user : conversation.getUsers()){
                users.add(user);
            }
        }
        db.addAllUsers(users);
        try{
        Log.d(TAG, hashCode() + "User DB\n" + db.getUser(11).toString());
        } catch (Throwable e) {
            Log.d(TAG, hashCode() + " no such element");
        }
        Log.d(TAG, hashCode() + "User DB\n" + db.getAllUsersSet().toString());
        Log.d(TAG, hashCode() + "Conv DB\n" + db.getAllConversations().toString());
        db.close();
    }


    @Override
    public void onLoaderReset(Loader<HttpResponse> httpResponseLoader) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
