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
import ua.kpi.campus.Activity.MainActivity;
import ua.kpi.campus.R;
import ua.kpi.campus.api.jsonparsers.message.MessageItem;
import ua.kpi.campus.model.User;
import ua.kpi.campus.model.dbhelper.DatabaseHelper;
import ua.kpi.campus.utils.Time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

/**
 * Class
 *
 * @author Artur Dzidzoiev
 * @version 12/23/13
 */
public class MessagesViewAdapter extends ArrayAdapter<MessageItem>{
    public final static String TAG = MessagesViewAdapter.class.getName();
    private final Context context;
    private final ArrayList<MessageItem> values;
    private final int currentUserID;
    private HashMap<Integer,User> users;

    public MessagesViewAdapter(Context context, ArrayList<MessageItem> values) {
        super(context, R.layout.list_item_message, values);
        this.context = context;
        this.values = values;
        this.users = new HashMap<>();
        try (DatabaseHelper db = new DatabaseHelper(getContext())) {
            this.currentUserID = db.getCurrentUser().getId();
            Set<User> userset = db.getAllUsersSet();
            for (User user : userset) {
                users.put(user.getId(),user);
            }
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        MessageItem currentMessage;
        currentMessage = values.get(position);
        User sender = users.get(currentMessage.getSenderUserAccountID());
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.message_sent, parent, false);
            viewHolder.tName = (TextView) convertView.findViewById(R.id.senderName);
            viewHolder.tDate = (TextView) convertView.findViewById(R.id.timeSent);
            viewHolder.tTextMessage = (TextView) convertView.findViewById(R.id.textMessage);
            viewHolder.avatar = (ImageView) convertView.findViewById(R.id.avatar_small);

            if (currentMessage.getSenderUserAccountID() != currentUserID) {

            }
            convertView.setTag(viewHolder);
            Log.d(TAG, hashCode() + " created message view " + position);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tName.setText(sender.getFullname());
        viewHolder.tDate.setText(Time.getShortDependsOnToday(Time.getUnixTimeMessage(currentMessage.getDateSent())));
        viewHolder.tTextMessage.setText(currentMessage.getText());
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(sender.getPhoto(), viewHolder.avatar);

        Log.d(TAG, hashCode() + " shown message view " + position);
        return convertView;
    }

    private String getShortDependsOnToday(MessageItem currentMessage) {
        SimpleDateFormat inputDate = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
        SimpleDateFormat longDate = new SimpleDateFormat("HH:mm:ss E', 'dd");
        SimpleDateFormat shortDate = new SimpleDateFormat("HH:mm:ss");

        Date today = getTodayDate();
        Date newDate = new Date();
        try {
            newDate = inputDate.parse(currentMessage.getDateSent());
        } catch (ParseException e) {
            Log.e(MainActivity.class.getName(), MessagesViewFragment.class.hashCode() + e.toString());
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
    private static class ViewHolder {
        TextView tName;
        TextView tDate;
        TextView tTextMessage;
        ImageView avatar;
    }
}


