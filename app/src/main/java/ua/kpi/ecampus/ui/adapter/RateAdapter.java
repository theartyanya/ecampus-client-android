package ua.kpi.ecampus.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ua.kpi.ecampus.R;
import ua.kpi.ecampus.model.Rating;

/**
 * RateAdapter manages Rating data model and adapts it to
 * list, which is in RateTeacherActivity.
 * <p>
 * Created by Administrator on 08.06.2016.
 * //
 */
public class RateAdapter extends ArrayAdapter<Rating> {

    private List<Rating> mData = new ArrayList<>();
    private Context mContext;
    private int mResourceId;

    public RateAdapter(Context context, int resource, List<Rating> objects) {
        super(context, resource, objects);
        mContext = context;
        mResourceId = resource;
        mData = objects;
    }

    private RatingBar.OnRatingBarChangeListener listener(
            final ViewHolder holder, final int position) {
        return (ratingBar, v, b) -> {
            Rating item = getItem(position);
            item.setRatingStar(v);
        };
    }

    @Override
    public Rating getItem(int position) {
        return mData.get(position);
    }

    @Override
    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(mResourceId, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.ratingBar.setOnRatingBarChangeListener(listener
                (holder, position));
        holder.ratingBar.setTag(position);
        holder.ratingBar.setRating(getItem(position).getRatingStar());
        holder.tvCriterion.setText(getItem(position).getCriterion());
        return convertView;
    }

    /**
     * Check whether teacher is rated by all criteria.
     *
     * @return true if rated completely, false otherwise
     */
    public boolean teacherIsRated() {
        for (Rating r : mData)
            if (Float.compare(r.getRatingStar(), 1) < 0)
                return false;
        return true;
    }

    public List<Rating> getData() {
        return mData;
    }

    protected class ViewHolder {

        @BindView(R.id.tv_criterion)
        TextView tvCriterion;
        @BindView(R.id.rating_bar)
        RatingBar ratingBar;

        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }
}
