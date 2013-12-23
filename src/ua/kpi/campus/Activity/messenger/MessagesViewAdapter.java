package ua.kpi.campus.Activity.messenger;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import ua.kpi.campus.Activity.MainActivity;
import ua.kpi.campus.R;
import ua.kpi.campus.api.jsonparsers.message.MessageItem;
import ua.kpi.campus.model.User;
import ua.kpi.campus.model.dbhelper.DatabaseHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Class
 *
 * @author Artur Dzidzoiev
 * @version 12/23/13
 */
public class MessagesViewAdapter extends ArrayAdapter<MessageItem>  implements ImageLoadingListener {
    public final static String TAG = MessagesViewAdapter.class.getName();
    private final Context context;
    private final ArrayList<MessageItem> values;
    private final int currentUserID;

    public MessagesViewAdapter(Context context, ArrayList<MessageItem> values) {
        super(context, R.layout.list_item_message, values);
        this.context = context;
        this.values = values;
        try (DatabaseHelper db = new DatabaseHelper(getContext())) {
            this.currentUserID = db.getCurrentUser().getId();
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        MessageItem currentMessage;
        currentMessage = values.get(position);
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

            try (DatabaseHelper db = new DatabaseHelper(getContext())) {
                viewHolder.user = db.getUser(currentMessage.getSenderUserAccountID());
            }
            convertView.setTag(viewHolder);
            Log.d(TAG, hashCode() + " created message view " + position);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tName.setText(viewHolder.user.getFullname());
        viewHolder.tDate.setText(formatDate(currentMessage));
        viewHolder.tTextMessage.setText(currentMessage.getText());
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(viewHolder.user.getPhoto(), viewHolder.avatar, this);

        Log.d(TAG, hashCode() + " shown message view " + position);
        return convertView;
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
        User user;
    }

    @Override
    public void onLoadingStarted(String s, View view) {
        Log.d(TAG, hashCode() + " image load started.");
    }

    @Override
    public void onLoadingFailed(String s, View view, FailReason failReason) {
        Log.d(TAG, hashCode() + " image load failed.");
    }

    @Override
    public void onLoadingComplete(String s, View view, Bitmap bitmap) {
        Log.d(TAG, hashCode() + " image load completed.");
    }

    @Override
    public void onLoadingCancelled(String s, View view) {
        Log.d(TAG, hashCode() + " image load cancelled.");
    }
}


