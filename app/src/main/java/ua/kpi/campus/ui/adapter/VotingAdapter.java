package ua.kpi.campus.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ua.kpi.campus.R;
import ua.kpi.campus.model.pojo.VoteTeacher;

/**
 * Created by Administrator on 01.06.2016.
 */
public class VotingAdapter extends RecyclerView
        .Adapter<VotingAdapter.ViewHolder> {

    private List<VoteTeacher> mData = new ArrayList<>();

    public void setItems(List<VoteTeacher> list) {
        mData = list;
        notifyDataSetChanged();
    }

    public List<VoteTeacher> getItems() {
        return mData;
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
        VoteTeacher teacher = mData.get(position);
        holder.tvTeacherName.setText(teacher.getTeacherName());
        if (!teacher.isVoted())
            holder.imageVoted.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_teacher_name)
        TextView tvTeacherName;
        @Bind(R.id.image_voted)
        TextView imageVoted;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
