package ua.kpi.campus.Activity.messenger;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import ua.kpi.campus.R;
import ua.kpi.campus.model.Message;
import ua.kpi.campus.model.User;
import ua.kpi.campus.model.dbhelper.DatabaseHelper;
import ua.kpi.campus.utils.Time;

import java.util.HashMap;
import java.util.Set;

/**
 * Class
 *
 * @author Artur Dzidzoiev
 * @version 12/23/13
 */
public class MessagesViewAdapter extends BaseAdapter {
    public final static String TAG = MessagesViewAdapter.class.getName();
    private final Context context;
    private final Set<Message> values;
    private final int currentUserID;
    private HashMap<Integer,User> users;

    public MessagesViewAdapter(Context context, Set<Message> values) {
        super();
        this.context = context;
        this.values = values;
        this.users = new HashMap<>();
        try (DatabaseHelper db = new DatabaseHelper(context)) {
            this.currentUserID = db.getCurrentUser().getId();
            Set<User> userset = db.getAllUsersSet();
            for (User user : userset) {
                users.put(user.getId(),user);
            }
        }
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public Object getItem(int i) {
        return values.toArray()[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        Message currentMessage;
        currentMessage = (Message) values.toArray()[position];;
        User sender = users.get(currentMessage.getSenderId());
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.message_sent, parent, false);
            viewHolder.tName = (TextView) convertView.findViewById(R.id.senderName);
            viewHolder.tDate = (TextView) convertView.findViewById(R.id.timeSent);
            viewHolder.tTextMessage = (TextView) convertView.findViewById(R.id.textMessage);
            viewHolder.avatar = (ImageView) convertView.findViewById(R.id.avatar_small);

            if (currentMessage.getSenderId() != currentUserID) {

            }
            convertView.setTag(viewHolder);
            Log.d(TAG, hashCode() + " created message view " + position);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tName.setText(sender.getFullname());
        viewHolder.tDate.setText(Time.getShortDependsOnToday(currentMessage.getTimeSent()));
        viewHolder.tTextMessage.setText(currentMessage.getText());
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(sender.getPhoto(), viewHolder.avatar);

        Log.d(TAG, hashCode() + " shown message view " + position);
        return convertView;
    }

    private static class ViewHolder {
        TextView tName;
        TextView tDate;
        TextView tTextMessage;
        ImageView avatar;
    }
}


