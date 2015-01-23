package ua.kpi.campus.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ua.kpi.campus.R;
import ua.kpi.campus.model.AboutItem;
import ua.kpi.campus.model.DeveloperItem;
import ua.kpi.campus.ui.widgets.BezelImageView;

/**
 * Created by Admin on 22.01.2015.
 */
public class DevelopersAdapter extends ArrayAdapter<DeveloperItem> {

    private static class ViewHolder {
        private TextView itemName;
        private TextView itemSubtitle;
        private ImageView imageView;
    }

    public DevelopersAdapter(Context context, int textViewResourceId, ArrayList<DeveloperItem> items) {
        super(context, textViewResourceId, items);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.item_developer, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.itemName = (TextView) convertView.findViewById(R.id.developer_name);
            viewHolder.itemSubtitle = (TextView) convertView.findViewById(R.id.developer_work);
            viewHolder.imageView = (BezelImageView) convertView.findViewById(R.id.photo);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final DeveloperItem item = getItem(position);
        if (item != null) {
            viewHolder.itemName.setText(item.getOptionName());
            viewHolder.itemSubtitle.setText(item.getOptionDescription());
            viewHolder.imageView.setImageResource(item.getPhoto());
        }
        
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(item.getLink()));
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }
}