package ua.kpi.campus.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import org.json.JSONException;
import ua.kpi.campus.R;
import ua.kpi.campus.Session;
import ua.kpi.campus.api.CampusApiURL;
import ua.kpi.campus.api.jsonparsers.JSONMessageGetItemParser;
import ua.kpi.campus.api.jsonparsers.message.MessageItem;
import ua.kpi.campus.api.jsonparsers.user.UserData;
import ua.kpi.campus.loaders.HttpResponse;
import ua.kpi.campus.loaders.HttpStringLoader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Class
 *
 * @author Artur Dzidzoiev
 * @version 12/19/13
 */
public class ConversationViewFragment extends ListFragment implements LoaderManager.LoaderCallbacks<HttpResponse> {
    public final static String EXTRA_GROUP_ID = "groupId";
    private final static int MESSAGE_ITEMS_LOADER = 23;
    private LoaderManager.LoaderCallbacks<HttpResponse> mCallbacks;
    private LoaderManager loaderManager;
    private ArrayAdapter mAdapter;
    private int currentUserID;
    private int groupId;
    private UserData currentUser;
    private ArrayList<MessageItem> messages;
    private Button sendButton;
    private EditText messageInput;
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
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d(MainActivity.TAG, hashCode() + " onActivityCreated: fragment " + this.getClass().getName());
        super.onActivityCreated(savedInstanceState);
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
        currentUser = Session.getCurrentUser();
        currentUserID = currentUser.getUserAccountID();
        Intent intent = getActivity().getIntent();
        groupId = (int) intent.getExtras().get(EXTRA_GROUP_ID);

        //init loader
        mCallbacks = this;
        loaderManager = getLoaderManager();
        initLoader();
    }

    private void initLoader() {
        Bundle url = new Bundle();
        url.putString(HttpStringLoader.URL_STRING, CampusApiURL.getConversation(Session.getSessionId(), groupId, 1, getResources().getInteger(R.integer.messages_size_page)));
        loaderManager.initLoader(MESSAGE_ITEMS_LOADER, url, mCallbacks).onContentChanged();
    }

    private void sendMessage(String input) {
        input = input.replaceAll("\\s+", "%20");
        if (!input.isEmpty()) {
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(CampusApiURL.sendMessage(Session.getSessionId(), groupId, input, ""), new AsyncHttpResponseHandler() {


                @Override
                public void onSuccess(String response) {
                    Log.d(MainActivity.TAG, hashCode() + " sent... ");

                    //Log.d(MainActivity.TAG, hashCode() + " response: "+ response);
                    initLoader();
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

    @Override
    public Loader<HttpResponse> onCreateLoader(int i, Bundle bundle) {
        Log.d(MainActivity.TAG, hashCode() + " load started " + i + " on URL: " + bundle.getString(HttpStringLoader.URL_STRING));
        return new HttpStringLoader(getActivity(), bundle.getString(HttpStringLoader.URL_STRING));
    }

    @Override
    public void onLoadFinished(Loader<HttpResponse> httpResponseLoader, HttpResponse httpResponse) {
        Log.d(MainActivity.TAG, hashCode() + " load finished");
        //Log.d(MainActivity.TAG, hashCode() + " entity: \n" + httpResponse.getEntity());
        ArrayList<MessageItem> messageItems = parseConversation(httpResponse.getEntity());
        Collections.reverse(messageItems);
        messages = messageItems;
        showMessagesListView();
    }

    private void showMessagesListView() {
        mAdapter = new ConversationListAdapter(getActivity(), messages);
        setListAdapter(mAdapter);
    }

    @Override
    public void onLoaderReset(Loader<HttpResponse> httpResponseLoader) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private class ConversationListAdapter extends ArrayAdapter<MessageItem> {
        private final Context context;
        private final ArrayList<MessageItem> values;

        public ConversationListAdapter(Context context, ArrayList<MessageItem> values) {
            super(context, R.layout.list_item_message, values);
            this.context = context;
            this.values = values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //position = getCount() - position - 1;
            MessageItem currentMessage = values.get(position);
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView;
            if (currentMessage.getSenderUserAccountID() == currentUserID) {
                rowView = inflater.inflate(R.layout.message_sent, parent, false);
            } else {
                rowView = inflater.inflate(R.layout.message_received, parent, false);
            }

            TextView tName = (TextView) rowView.findViewById(R.id.senderName);
            TextView tDate = (TextView) rowView.findViewById(R.id.timeSent);
            TextView tTextMessage = (TextView) rowView.findViewById(R.id.textMessage);
            ImageView avatar = (ImageView) rowView.findViewById(R.id.avatar_small);

            tName.setText(Integer.toString(currentMessage.getSenderUserAccountID()));
            tDate.setText(formatDate(currentMessage));
            tTextMessage.setText(currentMessage.getText());

            Log.d(MainActivity.TAG, hashCode() + " created message view " + position);
            return rowView;
        }

        private String formatString(String original, final int maxLength) {
            if (original.length() > maxLength) {
                original = original.substring(0, maxLength);
                original = original + "...";
            }
            return original;
        }

        private String formatDate(MessageItem currentMessage) {
            SimpleDateFormat inputDate = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
            SimpleDateFormat longDate = new SimpleDateFormat("HH:mm:ss E', 'dd");
            SimpleDateFormat shortDate = new SimpleDateFormat("HH:mm:ss");

            Date today = getTodayDate();
            Date newDate = new Date();
            try {
                newDate = inputDate.parse(currentMessage.getDateSent());
            } catch (ParseException e) {
                Log.e(MainActivity.class.getName(), ConversationListFragment.class.hashCode() + e.toString());
            }

            return today.after(newDate) ? longDate.format(newDate) : shortDate.format(newDate);
        }

        private Date getTodayDate() {
            Date today = new Date();
            today.setHours(0);
            today.setMinutes(0);
            today.setSeconds(0);
            return today;
        }
    }
}
