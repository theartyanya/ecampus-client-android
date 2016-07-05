package ua.kpi.campus.ui.presenter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import ua.kpi.campus.model.dao.IDataAccessObject;
import ua.kpi.campus.model.dao.VotingDao;
import ua.kpi.campus.model.pojo.Item;
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
        makeStubData();
        setResult();
    }

    private void makeStubData() {
        List<VoteSet> set = new ArrayList<>();
        VoteSet vs = new VoteSet();

        List<VoteTerm> terms = new ArrayList<>();
        terms.add(new VoteTerm("1", "2015-2016", "2015-09-01", "2016-09-01"));
        terms.add(new VoteTerm("2", "2014-2015", "2014-09-01", "2015-09-01"));

        List<Item> criteria = new ArrayList<>();
        criteria.add(new Item(1, "3.97"));
        criteria.add(new Item(2, "4.21"));
        criteria.add(new Item(3, "3.97"));
        criteria.add(new Item(4, "4.21"));
        criteria.add(new Item(5, "3.97"));
        criteria.add(new Item(6, "4.21"));

        List<VoteTeacher> teachers = new ArrayList<>();
        VoteTeacher t = new VoteTeacher("1", "1", "Крилов Євген " +
                "Володимирович", true, "4.0");
        t.setCriteria(criteria);
        teachers.add(t);
        t = new VoteTeacher("1", "2", "Лісовиченко Олег Іванович", true, "4.3");
        t.setCriteria(criteria);
        teachers.add(t);
        t = new VoteTeacher("1", "2", "Мелкумян Катерина Юріївна", false, "4.3");
        t.setCriteria(criteria);
        teachers.add(t);

        vs.setTeachers(teachers);
        vs.setTerms(terms);

        mDataAccess.setData(new ArrayList<VoteSet>() {{
            add(vs);
        }});

    }

    public void setResult() {
        VoteSet voting = getVoting();
        List<VoteTerm> terms = voting.getTerms();

        List<Item> termNames = new ArrayList<>();
        for (VoteTerm t : terms) {
            Item i = new Item(Integer.parseInt(t.getVoteId()), t.getVoteName());
            termNames.add(i);
        }
        mView.setTermsSpinner(termNames);

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

        void setTermsSpinner(List<Item> terms);

        void setVoteInProgressAdapter(List<VoteTeacher> teachers);

        void setVoteEndedAdapter(List<VoteTeacher> teachers);
    }
}
