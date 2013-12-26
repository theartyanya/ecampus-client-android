package ua.kpi.campus.Activity.messenger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import ua.kpi.campus.R;
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
    private HashSet<User> mUsers;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_conversation_create, container, false);
        mContext = getActivity();
        Intent intent = getActivity().getIntent();
        int[] userIds = (int[]) intent.getExtras().get(CreateConversationActivity.EXTRA_USERS);
        try (DatabaseHelper db = new DatabaseHelper(mContext)) {
            for(int i: userIds){
                mUsers.add(db.getUser(i));
            }
        }

        LinearLayout userListLayout = (LinearLayout) rootView.findViewById(R.id.create_conversation_user_list);
        EditText subject = (EditText) rootView.findViewById(R.id.create_conversation_subject_edit);
        EditText text = (EditText) rootView.findViewById(R.id.create_conversation_text_edit);
        Button sendButton = (Button) rootView.findViewById(R.id.create_conversatioin_button_send);
        for (User user : mUsers) {
            TextView username = new TextView(mContext);
            username.setText(user.getFullname());
            userListLayout.addView(username);
        }
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return rootView;
    }
}
