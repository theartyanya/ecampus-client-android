package ua.kpi.campus.Activity.bulletinboard;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ua.kpi.campus.Activity.MainActivity;
import ua.kpi.campus.R;
import ua.kpi.campus.model.BulletinBoardSubject;
import ua.kpi.campus.utils.Formatter;

import java.util.List;

/**
 * Class
 *
 * @author Artur Dzidzoiev
 * @version 12/22/13
 */
public class BulletinBoardListAdapter extends ArrayAdapter<BulletinBoardSubject> {
    private final Context context;
    private final List<BulletinBoardSubject> values;
    private final static int MAX_LENGTH = 150;

    public BulletinBoardListAdapter(Context context, List<BulletinBoardSubject> values) {
        super(context, R.layout.bulletin_item, values);
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
            convertView = inflater.inflate(R.layout.bulletin_item, parent, false);
            viewHolder.tSubject = (TextView) convertView.findViewById(R.id.bulletin_board_subject);
            viewHolder.tLastMessageText = (TextView) convertView.findViewById(R.id.bulletin_board_text);
            convertView.setTag(viewHolder);
            Log.d(MainActivity.TAG, hashCode() + " created subsystem view " + position);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        BulletinBoardSubject bulletinBoardSubject = values.get(position);
        viewHolder.tSubject.setText(Formatter.get(bulletinBoardSubject.getSubject(),MAX_LENGTH));
        viewHolder.tLastMessageText.setText(Formatter.get(bulletinBoardSubject.getText(),MAX_LENGTH));

        return convertView;
    }

    static class ViewHolder {
        TextView tSubject;
        TextView tLastMessageText;
    }
}
