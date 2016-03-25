package com.kpi.campus.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.kpi.campus.R;
import com.kpi.campus.model.pojo.Bulletin;
import com.kpi.campus.ui.view.OnItemClickListener;
import com.kpi.campus.util.CollectionValidator;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * BulletinAdapter manages Bulletin data model and adapts it to RecyclerView, which is in BbActualTabFragment.
 *
 * Created by Administrator on 02.02.2016.
 */
public class BulletinAdapter extends RecyclerView.Adapter<BulletinAdapter.ViewHolder> {

    private List<Bulletin> mList;
    private OnItemClickListener mListener;
    private PopupMenu.OnMenuItemClickListener mMenuListener;
    private boolean mIsModerator = false;

    /** after reorientation test this member
     * or one extra request will be sent after each reorientation*/
    private boolean mAllItemsLoaded;


    public BulletinAdapter(List<Bulletin> list, boolean isModerator) {
        mList = list;
        mIsModerator = isModerator;
    }

    public void addData(List<Bulletin> list) {
        CollectionValidator.validateOnNull(list);

        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void setData(List<Bulletin> list) {
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
        holder.date.setText(bul.getDateCreate());
        holder.theme.setText(bul.getSubject());
        holder.author.setText(bul.getCreatorName());

        if(mIsModerator) {
            holder.btnOverflow.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return (mList != null) ? mList.size() : 0;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public void setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener listener) {
        mMenuListener = listener;
    }

    public boolean isAllItemsLoaded() {
        return mAllItemsLoaded;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.text_view_bulletin_date)
        TextView date;
        @Bind(R.id.text_view_bulletin_theme)
        TextView theme;
        @Bind(R.id.text_view_bulletin_author)
        TextView author;

        @Bind(R.id.button_overflow)
        ImageButton btnOverflow;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.layout_bulletin)
        public void itemClick(View view) {
            if (mListener != null) {
                mListener.onItemClicked(view, getAdapterPosition(), null);
            }
        }

        @OnClick(R.id.button_overflow)
        public void btnOverflowClick(final View view) {
            PopupMenu menu = new PopupMenu(view.getContext(), view);
            menu.inflate(R.menu.menu_popup_bulletin);
            menu.show();
            menu.setOnMenuItemClickListener(mMenuListener);
        }

    }
}
