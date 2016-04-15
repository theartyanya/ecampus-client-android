package com.kpi.campus.ui.adapter;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kpi.campus.R;
import com.kpi.campus.model.Recipient;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * BulletinsRecipientAdapter manages recipient data (profile, subsystem,
 * group) and adapts it to RecyclerView, which is in New/Edit BulletinActivity.
 * This recycler view contains buffer list of recipients which have not added
 * to bulletin's recipient list yet.
 * <p>
 * Created by Administrator on 17.02.2016.
 */
public class BulletinsRecipientAdapter extends RecyclerView
        .Adapter<BulletinsRecipientAdapter.ViewHolder> {

    private final int NOTIFY_DELAY = 500;

    private ArrayList<Recipient> mDataList = new ArrayList<>();

    public void setItems(ArrayList<Recipient> list) {
        mDataList = list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .recyclerview_buffer_recipient_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Recipient item = mDataList.get(position);
        holder.tvWhere.setText(item.getSubdivisionName());
        if (item.getProfileName() != null) {
            holder.tvWhom.setText(item.getProfileName());
        } else if (item.getStudyGroupName() != null) {
            holder.tvWhom.setText(item.getStudyGroupName());
        }
        holder.btnDelete.setOnClickListener(v -> removeItem(position));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public List<Recipient> getData() {
        return mDataList;
    }

    public void addItem(final Recipient item) {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            mDataList.add(item);
            notifyItemInserted(mDataList.size() - 1);
        }, NOTIFY_DELAY);
    }

    public void removeItem(final int position) {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            mDataList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, mDataList.size());
        }, NOTIFY_DELAY);
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_where)
        TextView tvWhere;
        @Bind(R.id.tv_whom)
        TextView tvWhom;
        @Bind(R.id.button_delete_buffer_recipient)
        ImageButton btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
