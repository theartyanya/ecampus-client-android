package com.kpi.campus.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.kpi.campus.R;
import com.kpi.campus.model.Bulletin;
import com.kpi.campus.ui.view.OnItemClickListener;
import com.kpi.campus.util.CollectionValidator;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 02.02.2016.
 */
public class BulletinAdapter extends RecyclerView.Adapter<BulletinAdapter.ViewHolder> {

    private List<Bulletin> mList;
    private OnItemClickListener mListener;

    public BulletinAdapter(List<Bulletin> list) {
        mList = list;
    }

    public void setData(List<Bulletin> list) {
        CollectionValidator.validateOnNull(list);
        mList = list;
        notifyDataSetChanged();
    }

    public List<Bulletin> getData() {
        return mList;
    }

    @Override
    public BulletinAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_bulletin, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BulletinAdapter.ViewHolder holder, int position) {
        Bulletin bul = mList.get(position);
        holder.date.setText(bul.getDate());
        holder.theme.setText(bul.getTheme());
        holder.author.setText(bul.getAuthor());
    }

    @Override
    public int getItemCount() {
        return (mList != null) ? mList.size() : 0;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.text_view_bulletin_date)
        TextView date;
        @Bind(R.id.text_view_bulletin_theme)
        TextView theme;
        @Bind(R.id.text_view_bulletin_author)
        TextView author;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.layout_bulletin)
        public void itemClick(View view) {
            if (mListener != null) {
                mListener.onItemClicked(view, getAdapterPosition());
            }
        }
    }
}
