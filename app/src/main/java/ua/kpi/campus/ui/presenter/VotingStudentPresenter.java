package ua.kpi.campus.ui.presenter;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import ua.kpi.campus.model.dao.IDataAccessObject;
import ua.kpi.campus.model.dao.VotingDao;
import ua.kpi.campus.model.pojo.VoteSet;
import ua.kpi.campus.model.pojo.VoteTeacher;
import ua.kpi.campus.model.pojo.VoteTerm;
import ua.kpi.campus.util.DateUtil;

/**
 * Created by Administrator on 01.06.2016.
 */
public class VotingStudentPresenter extends BasePresenter {

    private IView mView;
    private IDataAccessObject<VoteSet> mDataAccess;

    @Inject
    public VotingStudentPresenter() {
        mDataAccess = new VotingDao();
    }

    public void setView(IView view) {
        mView = view;
    }

    @Override
    public void initializeViewComponent() {
        mView.setViewComponent();
    }

    public void loadVoting() {
        // load

        VoteSet voting = getVoting();
        VoteTerm latestTerm = voting.getTerms().get(0);
        if (isVotePeriod(latestTerm.getDateStop())) {
            // voting adapter
            mView.setVoteInProgressAdapter(voting.getTeachers());
        } else {
            // resulting adapter
            mView.setVoteEndedAdapter(voting.getTeachers());
        }
    }

    public VoteSet getVoting() {
        return mDataAccess.getData().iterator().next();
    }

    private boolean isVotePeriod(String endDate) {
        Date currentDate = DateUtil.convert(DateUtil.getCurrentDate());
        return !currentDate.after(DateUtil.convert(endDate));
    }

    public interface IView {
        void setViewComponent();
        void setVoteInProgressAdapter(List<VoteTeacher> teachers);
        void setVoteEndedAdapter(List<VoteTeacher> teachers);
    }
}
