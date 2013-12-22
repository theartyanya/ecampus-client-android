package ua.kpi.campus.Activity.messanger;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ua.kpi.campus.Activity.MainActivity;
import ua.kpi.campus.R;
import ua.kpi.campus.model.Conversation;
import ua.kpi.campus.utils.Time;

import java.util.List;

/**
 * Class
 *
 * @author Artur Dzidzoiev
 * @version 12/22/13
 */
public class ConversationListAdapter extends ArrayAdapter<Conversation> {
    private final Context context;
    private final List<Conversation> values;
    private final int maxLength = getContext().getResources().getInteger(R.integer.message_max_length_string);
    private final int maxLengthTheme = getContext().getResources().getInteger(R.integer.message_max_length_theme_string);

    public ConversationListAdapter(Context context, List<Conversation> values) {
        super(context, R.layout.list_item_message, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_message, parent, false);
            viewHolder.tSubject = (TextView) convertView.findViewById(R.id.messageListSubject);
            viewHolder.tLastMessageText = (TextView) convertView.findViewById(R.id.messageListLastMessage);
            viewHolder.tLastDateText = (TextView) convertView.findViewById(R.id.messageListLastDate);
            convertView.setTag(viewHolder);
            Log.d(MainActivity.TAG, hashCode() + " created subsystem view " + position);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Conversation currentConversation = values.get(position);
        viewHolder.tSubject.setText(formatString(currentConversation.getSubject(), maxLengthTheme));
        viewHolder.tLastMessageText.setText(formatString(currentConversation.getLastMessageText(), maxLength));
        viewHolder.tLastDateText.setText(Time.getShortWithDate(currentConversation.getLastMessageDate()));

        return convertView;
    }

    private static String formatString(String original, final int maxLength) {
        if (original.length() > maxLength) {
            original = original.substring(0, maxLength);
            original = original + "...";
        }
        return original;
    }

    static class ViewHolder {
        TextView tSubject;
        TextView tLastMessageText;
        TextView tLastDateText;
    }
}
