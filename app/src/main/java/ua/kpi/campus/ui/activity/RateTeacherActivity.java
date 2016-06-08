package ua.kpi.campus.ui.activity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import ua.kpi.campus.R;
import ua.kpi.campus.di.UIModule;

/**
 * Created by Administrator on 08.06.2016.
 */
public class RateTeacherActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_teacher);
        bindViews();
    }

    @Override
    protected List<Object> getModules() {
        List<Object> modules = new ArrayList<>();
        modules.add(new UIModule());
        return modules;
    }
}
