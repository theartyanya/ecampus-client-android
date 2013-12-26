package ua.kpi.campus.Activity.messenger;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import org.json.JSONException;
import ua.kpi.campus.Activity.MainActivity;
import ua.kpi.campus.R;
import ua.kpi.campus.api.CampusApiURL;
import ua.kpi.campus.api.jsonparsers.JsonBasicParser;
import ua.kpi.campus.model.User;
import ua.kpi.campus.model.dbhelper.DatabaseHelper;

import java.util.HashSet;

/**
 * Class
 *
 * @author Artur Dzidzoiev
 * @version 12/26/13
 */
public class CreateConversationFragment extends Fragment{
    public static final String TAG = MainActivity.TAG;
    private Context mContext;
    private EditText mSubject;
    private EditText mText;
    private String mSessionId;
    private int[] userIds;
    private AsyncHttpClient mClient;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_conversation_create, container, false);
        mContext = getActivity();
        HashSet<User> mUsers = new HashSet<>();
        Intent intent = getActivity().getIntent();
        userIds = intent.getIntArrayExtra(CreateConversationActivity.EXTRA_USERS);
        try (DatabaseHelper db = DatabaseHelper.getInstance()) {
            mSessionId = db.getSessionId();
            for(int i: userIds){
                mUsers.add(db.getUser(i));
            }
        }

        LinearLayout userListLayout = (LinearLayout) rootView.findViewById(R.id.create_conversation_user_list);
        mSubject = (EditText) rootView.findViewById(R.id.create_conversation_subject_edit);
        mText = (EditText) rootView.findViewById(R.id.create_conversation_text_edit);
        Button sendButton = (Button) rootView.findViewById(R.id.create_conversatioin_button_send);
        for (User user : mUsers) {
            TextView username = new TextView(mContext);
            username.setText(user.getFullname());
            userListLayout.addView(username);
        }

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isEmpty(mText)){
                    mText.requestFocus();
                    Toast.makeText(mContext, mContext.getResources().getString(R.string.create_conversation_fill_text), Toast.LENGTH_SHORT).show();
                } else if(isEmpty(mSubject)) {
                    mText.requestFocus();
                    Toast.makeText(mContext, mContext.getResources().getString(R.string.create_conversation_fill_subject), Toast.LENGTH_SHORT).show();
                } else {
                    createConversation(userIds);
                }
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, hashCode() + " starting  AsyncHttpClient");
        mClient = new AsyncHttpClient();
    }

    private void createConversation(int[] userIds) {
        String name = Uri.encode(mSubject.getText().toString());
        String url = CampusApiURL.createGroup(mSessionId, userIds, name);
        Log.d(TAG, hashCode() + " load started " + url);
        mClient.get(url,
                new TextHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, org.apache.http.Header[] headers, java.lang.String responseBody) {
                        Log.d(TAG, hashCode() + " received.");
                        try {
                            int groupId = Integer.valueOf(JsonBasicParser.parse(responseBody).getData());
                            createMessage(groupId);
                        } catch (JSONException e) {
                            Log.e(TAG, hashCode() + " json error!");
                            Toast.makeText(mContext, mContext.getString(R.string.create_conversation_fail, statusCode), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, org.apache.http.Header[] headers, java.lang.String responseBody, java.lang.Throwable error) {
                        Log.d(TAG, hashCode() + " fail " + statusCode);
                        Toast.makeText(mContext, mContext.getString(R.string.create_conversation_fail, statusCode), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void createMessage(final int groupId) {
        String text = Uri.encode(mText.getText().toString());
        String name = Uri.encode(mSubject.getText().toString());
        final String url = CampusApiURL.sendMessage(mSessionId, groupId, text, name);
        Log.d(TAG, hashCode() + " load started " + url);
        mClient.get(url,
                new TextHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, org.apache.http.Header[] headers, java.lang.String responseBody) {
                        Log.d(TAG, hashCode() + " received.");
                            createMessageView(groupId);
                    }

                    @Override
                    public void onFailure(int statusCode, org.apache.http.Header[] headers, java.lang.String responseBody, java.lang.Throwable error) {
                        if(statusCode!=0){
                        Log.d(TAG, hashCode() + " fail " + statusCode);
                        Toast.makeText(mContext, mContext.getString(R.string.create_conversation_fail, statusCode), Toast.LENGTH_SHORT).show();
                        } else {
                            createMessageView(groupId);
                        }
                    }
                });
    }

    private void createMessageView(int groupId) {
        Intent intent = new Intent(getActivity(), MessageActivity.class);
        Log.d(TAG, hashCode() + " starting new activity... " + MessageActivity.class.getName());
        intent.putExtra(MessagesViewFragment.EXTRA_GROUP_ID, groupId);
        startActivity(intent);
    }

    private boolean isEmpty(EditText eText) {
        return eText.getText().toString().trim().length() <= 0;
    }
}
