package ua.kpi.campus.Activity;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import ua.kpi.campus.R;
import ua.kpi.campus.Session;
import ua.kpi.campus.api.jsonparsers.user.*;
import ua.kpi.campus.loaders.HttpStringLoader;

import java.util.ArrayList;

/**
 * Class
 *
 * @author Artur Dzidzoiev
 * @version 12/18/13
 */
public class MyProfileFragment extends ListFragment {
    private final static int AVATAR_LOADER_ID = 1;
    private static UserData currentUser;
    private ImageView avatar;
    private ArrayAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_section_my_profile, container, false);

        currentUser = Session.getCurrentUser();
        Bundle avatarUrl = new Bundle();
        avatarUrl.putString(HttpStringLoader.URL_STRING, currentUser.getPhoto());


        avatar = (ImageView) rootView.findViewById(R.id.avatar);
        TextView tFullName = (TextView) rootView.findViewById(R.id.FullName);
        TextView tSubdivisionName = (TextView) rootView.findViewById(R.id.SubdivisionName);
        TextView tPosition = (TextView) rootView.findViewById(R.id.Position);
        TextView tAcademicDegree = (TextView) rootView.findViewById(R.id.AcademicDegree);
        TextView tAcademicStatus = (TextView) rootView.findViewById(R.id.AcademicStatus);

        if (currentUser.isEmployee()) {
            Employee currentEmployee = ((UserDataEmployee) currentUser).getEmployees().get(0);
            tFullName.setText(currentUser.getFullName());
            tSubdivisionName.setText(currentEmployee.getSubDivisionName());
            tSubdivisionName.setTypeface(null, Typeface.BOLD);
            tPosition.setText(currentEmployee.getPosition());
            tAcademicDegree.setText(String.format("%s: %s",
                    rootView.getResources().getString(R.string.main_activity_profile_academic_degree),
                    currentEmployee.getAcademicDegree()));
            tAcademicStatus.setText(String.format("%s: %s",
                    rootView.getResources().getString(R.string.main_activity_profile_academic_status),
                    currentEmployee.getAcademicStatus()));
        } else {
            Personality currentPersonality = ((UserDataPersonalities) currentUser).getPersonalities().get(0);
            tFullName.setText(currentUser.getFullName());
            tSubdivisionName.setText(currentPersonality.getSubdivisionName());
            tSubdivisionName.setTypeface(null, Typeface.BOLD);
            tPosition.setText(String.format("%s: %s",
                    rootView.getResources().getString(R.string.main_activity_profile_group),
                    currentPersonality.getStudyGroupName()));
            tAcademicDegree.setText(String.format("%s: %s",
                    rootView.getResources().getString(R.string.main_activity_profile_contract),
                    toString(currentPersonality.isContract())));
            tAcademicStatus.setText(String.format("%s: %s",
                    rootView.getResources().getString(R.string.main_activity_profile_speciality),
                    currentPersonality.getSpeciality()));
        }

        mAdapter = new UserPermissionListAdapter(getActivity(), currentUser.getProfiles());
        setListAdapter(mAdapter);

        return rootView;
    }

    private String toString(boolean var) {
        return var ?
                getResources().getString(R.string.yes) :
                getResources().getString(R.string.no);
    }

    private class UserPermissionListAdapter extends ArrayAdapter<SubsystemData> {
        private final Context context;
        private final ArrayList<SubsystemData> values;


        public UserPermissionListAdapter(Context context, ArrayList<SubsystemData> values) {
            super(context, R.layout.list_item_message, values);
            this.context = context;
            this.values = values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.list_item_subsystem, parent, false);
            SubsystemData currentData = values.get(position);
            TextView tSubsystem = (TextView) rowView.findViewById(R.id.myProfileSybsystem);
            TextView tIsCreateValue = (TextView) rowView.findViewById(R.id.myProfileIsCreateValue);
            TextView tIsReadValue = (TextView) rowView.findViewById(R.id.myProfileIsReadValue);
            TextView tIsUpdateValue = (TextView) rowView.findViewById(R.id.myProfileIsUpdateValue);
            TextView tIsDeleteValue = (TextView) rowView.findViewById(R.id.myProfileIsDeleteValue);

            tSubsystem.setText(currentData.getSubsystemName());
            tIsCreateValue.setText(toString(currentData.isCreate()));
            tIsReadValue.setText(toString(currentData.isRead()));
            tIsUpdateValue.setText(toString(currentData.isUpdate()));
            tIsDeleteValue.setText(toString(currentData.isDelete()));

            return rowView;
        }

        private String toString(boolean var) {
            return var ?
                    getResources().getString(R.string.permission_yes) :
                    getResources().getString(R.string.permission_no);
        }
    }
}
