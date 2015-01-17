package ua.kpi.campus.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ua.kpi.campus.R;
import ua.kpi.campus.model.AboutItem;

/**
 * Created by doroshartyom on 17.01.2015.
 */
public class AboutListAdapter extends ArrayAdapter<AboutItem> {

    private static class ViewHolder {
        private TextView itemName;
        private TextView itemSubtitle;
    }

    public AboutListAdapter(Context context, int textViewResourceId, ArrayList<AboutItem> items) {
        super(context, textViewResourceId, items);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.item_about, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.itemName = (TextView) convertView.findViewById(R.id.text1);
            viewHolder.itemSubtitle = (TextView) convertView.findViewById(R.id.text2);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        AboutItem item = getItem(position);
        if (item != null) {
            viewHolder.itemName.setText(item.getOptionName());
            viewHolder.itemSubtitle.setText(item.getOptionDescription());
        }

        return convertView;
    }
}