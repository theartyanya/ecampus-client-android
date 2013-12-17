package ua.kpi.campus.Activity;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import org.json.JSONException;
import ua.kpi.campus.Mock;
import ua.kpi.campus.R;
import ua.kpi.campus.api.jsonparsers.JSONConversationParser;
import ua.kpi.campus.api.jsonparsers.message.UserConversationData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Message list
 *
 * @author Artur Dzidzoiev
 * @version 12/16/13
 */
public class MessageListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<String>{
    private final static int CONVERSATION_LOADER_ID = 153;
    private LoaderManager.LoaderCallbacks<String> mCallbacks;
    private LoaderManager loaderManager;
    private ArrayAdapter mAdapter;
    private ListView messagesList;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayList<UserConversationData> conversations = parseConversation(Mock.getUSER_CONVERSATION());
        mAdapter = new ConversationListAdapter(getActivity(), conversations);
        setListAdapter(mAdapter);
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
    public Loader<String> onCreateLoader(int i, Bundle bundle) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onLoadFinished(Loader<String> stringLoader, String s) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onLoaderReset(Loader<String> stringLoader) {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    private class ConversationListAdapter extends ArrayAdapter<UserConversationData> {
        private final Context context;
        private final ArrayList<UserConversationData> values;


        public ConversationListAdapter(Context context, ArrayList<UserConversationData> values) {
            super(context, R.layout.list_item_message, values);
            this.context = context;
            this.values = values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.list_item_message, parent, false);
            TextView tSubject = (TextView) rowView.findViewById(R.id.messageListSubject);
            TextView tLastMessageText = (TextView) rowView.findViewById(R.id.messageListLastMessage);
            TextView tLastDateText = (TextView) rowView.findViewById(R.id.messageListLastDate);

            final int maxLength = getResources().getInteger(R.integer.message_max_length_string);
            final int maxLengthTheme = getResources().getInteger(R.integer.message_max_length_theme_string);
            UserConversationData currentConversation = values.get(position);
            tSubject.setText(formatString(currentConversation.getSubject(),maxLengthTheme));
            tSubject.setTypeface(null, Typeface.BOLD);
            tLastMessageText.setText(formatString(currentConversation.getLastMessageText(),maxLength));
            tLastDateText.setText(formatDate(currentConversation));

            return rowView;
        }

        private String formatString(String original, final int maxLength) {
            if (original.length() > maxLength) {
                original = original.substring(0, maxLength);
                original = original + "...";
            }
            return original;
        }

        private String formatDate(UserConversationData currentConversation) {
            SimpleDateFormat inputDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            SimpleDateFormat parsedDate = new SimpleDateFormat("HH:mm E', 'dd");
            Date lastDate = new Date();
            try {
                lastDate = inputDate.parse(currentConversation.getLastMessageDate());
            } catch (ParseException e) {
                Log.e(MainActivity.class.getName(), MessageListFragment.class.hashCode() + e.toString());
            }
            return parsedDate.format(lastDate);
        }
    }
}
