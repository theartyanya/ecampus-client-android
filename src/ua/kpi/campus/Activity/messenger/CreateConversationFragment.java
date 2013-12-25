package ua.kpi.campus.Activity.messenger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ua.kpi.campus.R;
import ua.kpi.campus.model.BulletinBoardSubject;
import ua.kpi.campus.model.dbhelper.BulletinBoardBase;
import ua.kpi.campus.utils.Time;

/**
 * Class
 *
 * @author Artur Dzidzoiev
 * @version 12/26/13
 */
public class CreateConversationFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bulletin, container, false);
        Intent intent = getActivity().getIntent();
        int id = (int) intent.getExtras().get(EXTRA_BULLETIN_ID);
        BulletinBoardSubject currentBulletin;
        try (BulletinBoardBase db = BulletinBoardBase.getInstance()) {
            currentBulletin =  db.getBulletin(id);
        }

        TextView tDateCreated = (TextView) rootView.findViewById(R.id.bulletin_date_create);
        TextView tText = (TextView) rootView.findViewById(R.id.bulletin_text);
        TextView tAuthor = (TextView) rootView.findViewById(R.id.bulletin_author);
        TextView tSubject = (TextView) rootView.findViewById(R.id.bulletin_subject);

        tDateCreated.setText(Time.getDateOnly(currentBulletin.getDateCreate()));
        tText.setText(currentBulletin.getText());
        tAuthor.setText(currentBulletin.getCreatorUserAccountFullname());
        tSubject.setText(currentBulletin.getSubject());
        return rootView;
    }
}
