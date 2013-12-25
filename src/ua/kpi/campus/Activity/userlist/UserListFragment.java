package ua.kpi.campus.Activity.userlist;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import ua.kpi.campus.Activity.MainActivity;
import ua.kpi.campus.R;
import ua.kpi.campus.model.User;
import ua.kpi.campus.model.dbhelper.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Class
 *
 * @author Artur Dzidzoiev
 * @version 12/25/13
 */
public class UserListFragment extends ListFragment {
    public final static String TAG = MainActivity.TAG;
    private ArrayAdapter mAdapter;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_userlist, container, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, hashCode() + " onCreateView: fragment " + this.getClass().getName());

        mContext = getActivity().getApplicationContext();

        List<UserModel> userModels = getFromDB();
        mAdapter = new InteractiveUserListAdapter(getActivity(), userModels);
        setListAdapter(mAdapter);
    }
/*
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.d(TAG, hashCode() + " clicked on " + "l:" + l + " " + "v:" + v + " " + "position:" + (position-1)  + " " + "id:" + id + " ");
        Intent intent = new Intent(getActivity(), BulletinActivity.class);
        Log.d(TAG, hashCode() + " starting new activity... " + BulletinActivity.class.getName());
        intent.putExtra(BulletinFragment.EXTRA_BULLETIN_ID,((BulletinBoardSubject) l.getItemAtPosition(position)).getBulletinBoardId());
        startActivity(intent);
    }*/

    private List<UserModel> getFromDB() {
        try (DatabaseHelper db = new DatabaseHelper(mContext)) {
            List<UserModel> model = new ArrayList<UserModel>();
            for(User user : db.getAllUsersSet()){
                model.add(new UserModel(user.getId(),user.getFullname(),user.getPhoto()));
            }
            return model;
        }
    }


}
