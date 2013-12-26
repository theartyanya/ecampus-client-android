package ua.kpi.campus.Activity.userlist;

/**
 * Class
 *
 * @author Artur Dzidzoiev
 * @version 12/25/13
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.nostra13.universalimageloader.core.ImageLoader;
import ua.kpi.campus.R;

import java.util.List;

public class InteractiveUserListAdapter extends ArrayAdapter<UserModel> {

    private final List<UserModel> list;
    private final Activity context;

    public InteractiveUserListAdapter(Activity context, List<UserModel> list) {
        super(context, R.layout.userlist_rowbutton, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.userlist_rowbutton, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.text = (TextView) view.findViewById(R.id.userlist_name);
            viewHolder.avatar = (ImageView) view.findViewById(R.id.userlist_avatar);
            viewHolder.checkbox = (CheckBox) view.findViewById(R.id.userlist_check);
            viewHolder.checkbox
                    .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(CompoundButton buttonView,
                                                     boolean isChecked) {
                            UserModel element = (UserModel) viewHolder.checkbox
                                    .getTag();
                            element.setSelected(buttonView.isChecked());

                        }
                    });
            view.setTag(viewHolder);
            viewHolder.checkbox.setTag(list.get(position));
        } else {
            view = convertView;
            ((ViewHolder) view.getTag()).checkbox.setTag(list.get(position));
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.text.setText(list.get(position).getName());
        holder.checkbox.setChecked(list.get(position).isSelected());
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(list.get(position).getPhoto(), holder.avatar);
        return view;
    }

    private static class ViewHolder {
        protected TextView text;
        protected CheckBox checkbox;
        protected ImageView avatar;
    }
} 