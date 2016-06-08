package ua.kpi.campus.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ua.kpi.campus.R;
import ua.kpi.campus.model.Rating;
import ua.kpi.campus.ui.view.OnRatingEventListener;

/**
 * RateAdapter manages Rating data model and adapts it to
 * RecyclerView, which is in RateTeacherActivity.
 * <p>
 * Created by Administrator on 08.06.2016.
 * //
 */
public class RateAdapter extends RecyclerView.Adapter<RateAdapter
        .ViewHolder> {

    private List<Rating> mData = new ArrayList<>();
    private OnRatingEventListener mListener;

    public RateAdapter(List<Rating> data, OnRatingEventListener l) {
mListener = l;
        mData = data;
    }

    @Override
    public RateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int
            viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .list_rating_item, parent, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(RateAdapter.ViewHolder holder,
                                 int position) {
        Rating item = mData.get(position);
        holder.tvCriteria.setText(item.getCriterion());
        holder.ratingBar.setRating(item.getRatingStar());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_criterion)
        TextView tvCriteria;
        @Bind(R.id.rating_bar)
        RatingBar ratingBar;

        public ViewHolder(View itemView, OnRatingEventListener l) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

                @Override
                public void onRatingChanged(RatingBar ratingBar, float
                        rating, boolean fromUser) {
                    if(fromUser)
                        l.onRatingBarChange(mData.get(getLayoutPosition()), rating);
                }
            });
        }
    }
}
