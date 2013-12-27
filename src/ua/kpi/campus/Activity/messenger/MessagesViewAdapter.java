package ua.kpi.campus.Activity.messenger;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import ua.kpi.campus.R;
import ua.kpi.campus.model.Message;
import ua.kpi.campus.model.User;
import ua.kpi.campus.model.dbhelper.DatabaseHelper;
import ua.kpi.campus.utils.Time;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Class
 *
 * @author Artur Dzidzoiev
 * @version 12/23/13
 */
public class MessagesViewAdapter extends ArrayAdapter<Message> {
    public final static String TAG = MessagesViewAdapter.class.getName();
    private final Context context;
    private final List<Message> values;
    private final int currentUserID;
    private int mNextUserId;
    private HashMap<Integer, User> users;

    public MessagesViewAdapter(Context context, List<Message> values) {
        super(context, R.layout.list_item_message, values);
        this.context = context;
        this.values = values;
        this.users = new HashMap<>();
        try (DatabaseHelper db = DatabaseHelper.getInstance()) {
            this.currentUserID = db.getCurrentUser().getId();
            Set<User> userset = db.getAllUsersSet();
            for (User user : userset) {
                users.put(user.getId(), user);
            }
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        Message currentMessage;
        currentMessage = values.get(position);
        findNextUserId(position);
        User sender = users.get(currentMessage.getSenderId());
        if (convertView == null) {
            LayoutInflater inflater;
            viewHolder = new ViewHolder();
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (sender.getId() == mNextUserId) {
                convertView = inflater.inflate(R.layout.message_sent_continuous, parent, false);
            } else {
                convertView = inflater.inflate(R.layout.message_sent, parent, false);
            }
            viewHolder.tName = (TextView) convertView.findViewById(R.id.senderName);
            viewHolder.tDate = (TextView) convertView.findViewById(R.id.timeSent);
            viewHolder.tTextMessage = (TextView) convertView.findViewById(R.id.textMessage);
            viewHolder.avatar = (ImageView) convertView.findViewById(R.id.avatar_small);

            if (currentMessage.getSenderId() != currentUserID) {
                //TODO another layout params for another user
            }
            convertView.setTag(viewHolder);
            Log.d(TAG, hashCode() + " created message view " + position +  " + " + sender.getFullname() + " - " + currentMessage.getText());

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tName.setText(sender.getFullname());
        viewHolder.tDate.setText(Time.getShortDependsOnToday(currentMessage.getTimeSent()));
        viewHolder.tTextMessage.setText(currentMessage.getText());
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(sender.getPhoto(), viewHolder.avatar);

        Log.d(TAG, hashCode() + " shown message view " + sender.getFullname() + " - " + currentMessage.getText());
        return convertView;
    }

    private void findNextUserId(int position) {
        if (position < (getCount()) && position != 0) {
            mNextUserId = getItem(position - 1).getSenderId();
        } else {
            mNextUserId = -1;
        }
    }

    private static class ViewHolder {
        TextView tName;
        TextView tDate;
        TextView tTextMessage;
        ImageView avatar;
    }
}


