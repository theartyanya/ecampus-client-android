package com.kpi.campus.ui.adapter;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kpi.campus.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * BulletinsRecipientAdapter manages recipient data (profile, subsystem, group) and adapts it to RecyclerView, which is in New/Edit BulletinActivity.
 * This recycler view contains buffer list of recipients which have not added to bulletin's recipient list yet.
 * <p/>
 * Created by Administrator on 17.02.2016.
 */
public class BulletinsRecipientAdapter extends RecyclerView.Adapter<BulletinsRecipientAdapter.ViewHolder> {

    private final int NOTIFY_DELAY = 500;

    private ArrayList<String> mDataList = new ArrayList<>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_buffer_recipient_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        String data = mDataList.get(position);
        holder.textView.setText(data);
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void addItem(final List<String> list) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mDataList.addAll(list);
                notifyItemRangeInserted(mDataList.size()-1, list.size());
            }
        }, NOTIFY_DELAY);
    }

    public void removeItem(final int position) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mDataList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mDataList.size());
            }
        }, NOTIFY_DELAY);
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.text_view_buffer_recipient)
        TextView textView;
        @Bind(R.id.button_delete_buffer_recipient)
        ImageButton btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
