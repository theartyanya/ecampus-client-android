package ua.kpi.campus.Activity.messenger;

import android.content.Context;
import ua.kpi.campus.Page;
import ua.kpi.campus.R;

/**
 * Created by Serhiy on 17.07.2014.
 */
public class MessagePage extends Page {
    public MessagePage(Context context) {
        super(context);
    }

    @Override
    public String getTitle() {
        return getResources().getString(R.string.message_page_title);
    }
}
