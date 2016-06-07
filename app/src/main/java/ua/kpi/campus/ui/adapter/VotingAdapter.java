package ua.kpi.campus.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ua.kpi.campus.R;
import ua.kpi.campus.model.pojo.VoteTeacher;
import ua.kpi.campus.ui.view.OnItemClickListener;

import static ua.kpi.campus.util.TermPredicates.filter;
import static ua.kpi.campus.util.TermPredicates.isMatchesId;

/**
 * Created by Administrator on 01.06.2016.
 */
public class VotingAdapter extends RecyclerView
        .Adapter<VotingAdapter.ViewHolder> {

    private OnItemClickListener mListener;
    private List<VoteTeacher> mAllData = new ArrayList<>();
    private List<VoteTeacher> mCurrentData = new ArrayList<>();

    public void setAllItems(List<VoteTeacher> list) {
        mAllData = list;
        notifyDataSetChanged();
    }

    public List<VoteTeacher> getAllItems() {
        return mAllData;
    }

    public void filterByTerm(Integer termId) {
        mCurrentData.clear();
        mCurrentData.addAll(filter(mAllData, isMatchesId(termId)));
        notifyDataSetChanged();
    }

    @Override
    public VotingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int
            viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .recyclerview_vote_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VotingAdapter.ViewHolder holder,
                                 int position) {
        VoteTeacher teacher = mCurrentData.get(position);
        holder.tvTeacherName.setText(teacher.getTeacherName());
        if (!teacher.isVoted())
            holder.imageVoted.setVisibility(View.INVISIBLE);
        else
            holder.imageVoted.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return mCurrentData.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_teacher_name)
        TextView tvTeacherName;
        @Bind(R.id.image_voted)
        ImageView imageVoted;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.tv_feedback)
        public void itemClick(View view) {
            if (mListener != null) {
                int pos = getAdapterPosition();
                mListener.onItemClicked(view, pos, mCurrentData.get(pos));
            }
        }
    }
}
