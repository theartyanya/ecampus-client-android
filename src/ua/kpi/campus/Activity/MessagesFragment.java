package ua.kpi.campus.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import org.json.JSONException;
import ua.kpi.campus.Mock;
import ua.kpi.campus.R;
import ua.kpi.campus.Session;
import ua.kpi.campus.api.jsonparsers.JSONMessageGetItemParser;
import ua.kpi.campus.api.jsonparsers.message.MessageItem;
import ua.kpi.campus.api.jsonparsers.user.UserData;
import ua.kpi.campus.loaders.HttpResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Class
 *
 * @author Artur Dzidzoiev
 * @version 12/19/13
 */
public class MessagesFragment extends ListFragment {
    private final static int MESSAGE_ITEMS_LOADER = 23;
    private LoaderManager.LoaderCallbacks<HttpResponse> mCallbacks;
    private LoaderManager loaderManager;
    private ArrayAdapter mAdapter;
    private int currentUserID;
    private UserData currentUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_messages, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d(MainActivity.TAG, hashCode() + " onActivityCreated: fragment " + this.getClass().getName());
        super.onActivityCreated(savedInstanceState);
        currentUser = Session.getCurrentUser();
        currentUserID = currentUser.getUserAccountID();

        ArrayList<MessageItem> messageItems = parseConversation(Mock.getMessageItem());


        mAdapter = new ConversationListAdapter(getActivity(), messageItems);
        setListAdapter(mAdapter);
    }

    private ArrayList<MessageItem> parseConversation (String JsonConversation) {
        try {
            return JSONMessageGetItemParser.parse(JsonConversation).getData();
        } catch (JSONException e) {
            Log.e(this.getClass().getName(), hashCode() + getResources().getString(R.string.login_activity_json_error));
        }
        return new ArrayList<>();
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
            MessageItem currentMessage = values.get(position);
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView;
            if(currentMessage.getSendlerUserAccountID() == currentUserID) {
                rowView = inflater.inflate(R.layout.message_sent, parent, false);
            } else {
                rowView = inflater.inflate(R.layout.message_received, parent, false);
            }

            TextView tName = (TextView) rowView.findViewById(R.id.senderName);
            TextView tDate = (TextView) rowView.findViewById(R.id.timeSent);
            TextView tTextMessage = (TextView) rowView.findViewById(R.id.textMessage);
            ImageView avatar = (ImageView) rowView.findViewById(R.id.avatar_small);

            tName.setText(currentMessage.getSendlerUserAccountID());
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
            SimpleDateFormat inputDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            SimpleDateFormat longDate = new SimpleDateFormat("HH:mm E', 'dd");
            Date lastDate = new Date();
            try {
                lastDate = inputDate.parse(currentMessage.getDateSent());
            } catch (ParseException e) {
                Log.e(MainActivity.class.getName(), MessageListFragment.class.hashCode() + e.toString());
            }
            return longDate.format(lastDate);
        }
    }
}
