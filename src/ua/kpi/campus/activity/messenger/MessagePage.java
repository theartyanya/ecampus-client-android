package ua.kpi.campus.activity.messenger;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.TextureView;
import android.widget.TextView;
import ua.kpi.campus.Page;
import ua.kpi.campus.R;

/**
 * Created by Serhiy on 17.07.2014.
 */
public class MessagePage extends Page {
    private TextView status;
    public MessagePage(Context context) {
        super(context);
        prepare(context);
    }

    private void prepare(Context context) {
        status = new TextView(context);
        status.setText("Connection status");
        addView(status);
    }

    @Override
    public String getTitle() {
        return getResources().getString(R.string.message_page_title);
    }
}
