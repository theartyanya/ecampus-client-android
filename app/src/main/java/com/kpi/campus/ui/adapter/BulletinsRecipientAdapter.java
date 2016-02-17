package com.kpi.campus.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * BulletinsRecipientAdapter manages recipient data (profile, subsystem, group) and adapts it to RecyclerView, which is in New/Edit BulletinActivity.
 * This recycler view contains buffer list of recipients which have not added to bulletin's recipient list yet.
 *
 * Created by Administrator on 17.02.2016.
 */
public class BulletinsRecipientAdapter extends RecyclerView.Adapter<BulletinsRecipientAdapter.ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
