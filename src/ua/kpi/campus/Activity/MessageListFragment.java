package ua.kpi.campus.Activity;

import android.content.Context;
import android.graphics.Typeface;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import org.json.JSONException;
import ua.kpi.campus.R;
import ua.kpi.campus.Session;
import ua.kpi.campus.api.CampusApiURL;
import ua.kpi.campus.api.jsonparsers.JSONConversationParser;
import ua.kpi.campus.api.jsonparsers.message.UserConversationData;
import ua.kpi.campus.loaders.HttpResponse;
import ua.kpi.campus.loaders.HttpStringLoader;

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
public class MessageListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<HttpResponse>{
    private final static int CONVERSATION_LOADER_ID = 153;
    private LoaderManager.LoaderCallbacks<HttpResponse> mCallbacks;
    private LoaderManager loaderManager;
    private ArrayAdapter mAdapter;

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
    public Loader<HttpResponse> onCreateLoader(int i, Bundle bundle) {
        Log.d(this.getClass().getName(), hashCode() + " load started " + i);
        return new HttpStringLoader(getActivity(), bundle.getString(HttpStringLoader.URL_STRING));
    }

    @Override
    public void onLoadFinished(Loader<HttpResponse> httpResponseLoader, HttpResponse httpResponse) {
        String unparsed = httpResponse.getEntity();
        ArrayList<UserConversationData> conversations = parseConversation(unparsed);
        mAdapter = new ConversationListAdapter(getActivity(), conversations);
        setListAdapter(mAdapter);
    }

    @Override
    public void onLoaderReset(Loader<HttpResponse> httpResponseLoader) {
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

            Log.d(MainActivity.TAG, hashCode() + " created subsystem view " + position);
            final int maxLength = getResources().getInteger(R.integer.message_max_length_string);
            final int maxLengthTheme = getResources().getInteger(R.integer.message_max_length_theme_string);
            UserConversationData currentConversation = values.get(position);
            tSubject.setText(formatString(currentConversation.getSubject(), maxLengthTheme));
            tSubject.setTypeface(null, Typeface.BOLD);
            tLastMessageText.setText(formatString(currentConversation.getLastMessageText(), maxLength));
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
