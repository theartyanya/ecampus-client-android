package ua.kpi.campus.ui.fragments;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ua.kpi.campus.R;
import ua.kpi.campus.api.SyncScheduleTeacher;
import ua.kpi.campus.ui.TeacherScheduleActivity;

/**
 * Created by doroshartyom on 21.01.2015.
 */
public class TeacherFragment extends ListFragment implements SwipeRefreshLayout.OnRefreshListener, SyncScheduleTeacher.CallBacks {

    private String mContentDescription = null;
    private View mRoot = null;

    public SwipeRefreshLayout refreshLayout;



    public interface Listener {
        public void onFragmentViewCreated(ListFragment fragment);
        public void onFragmentAttached(TeacherFragment fragment);
        public void onFragmentDetached(TeacherFragment fragment);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_my_schedule, container, false);

        mRoot.setContentDescription(mContentDescription);
        refreshLayout = (SwipeRefreshLayout) mRoot.findViewById(R.id.swipe_refresh);

        return mRoot;
    }

    public void setContentDescription(String desc) {
        mContentDescription = desc;
        if (mRoot != null) {
            mRoot.setContentDescription(mContentDescription);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() instanceof Listener) {
            ((Listener) getActivity()).onFragmentViewCreated(this);

            refreshLayout.setColorScheme(R.color.green,
                    R.color.red,
                    R.color.blue,
                    R.color.orange);

            refreshLayout.setOnRefreshListener(this);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (getActivity() instanceof Listener) {
            ((Listener) getActivity()).onFragmentAttached(this);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (getActivity() instanceof Listener) {
            ((Listener) getActivity()).onFragmentDetached(this);
        }
    }

    @Override
    public void onRefresh() {
        //SyncSchedule sync = SyncSchedule.getSyncSchedule(PrefUtils.getPrefStudyGroupName(getActivity().getApplicationContext()), getActivity().getApplicationContext());

        //SyncSchedule.Connect connect = new SyncSchedule.Connect(this);
        //connect.execute(getActivity());
    }

    @Override
    public Context getContext() {
        return getActivity().getApplicationContext();
    }

    @Override
    public void taskCompleted(boolean completed) {

        refreshLayout.setRefreshing(false);
        ((TeacherScheduleActivity)getActivity()).notifyAdapters();
    }
    
    @Override
    public void taskStarted(boolean started){ 
        //do nothing
    }
}
