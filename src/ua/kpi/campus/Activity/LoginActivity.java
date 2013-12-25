package ua.kpi.campus.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.apache.http.HttpStatus;
import org.json.JSONException;
import ua.kpi.campus.Mock;
import ua.kpi.campus.R;
import ua.kpi.campus.Session;
import ua.kpi.campus.api.CampusApiURL;
import ua.kpi.campus.api.jsonparsers.JSONAuthorizationParser;
import ua.kpi.campus.api.jsonparsers.JSONBulletinBoardParser;
import ua.kpi.campus.api.jsonparsers.JSONUserDataParser;
import ua.kpi.campus.api.jsonparsers.JsonObject;
import ua.kpi.campus.api.jsonparsers.user.*;
import ua.kpi.campus.loaders.HttpResponse;
import ua.kpi.campus.loaders.asynctask.AsyncTaskManager;
import ua.kpi.campus.loaders.asynctask.HttpLoadTask;
import ua.kpi.campus.loaders.asynctask.OnTaskCompleteListener;
import ua.kpi.campus.model.BulletinBoardSubject;
import ua.kpi.campus.model.CurrentUser;
import ua.kpi.campus.model.User;
import ua.kpi.campus.model.dbhelper.BulletinBoardBase;
import ua.kpi.campus.model.dbhelper.DatabaseHelper;
import ua.kpi.campus.model.dbhelper.EmployeeBase;
import ua.kpi.campus.model.dbhelper.PeronalitiesBase;

import java.util.ArrayList;

public class LoginActivity extends FragmentActivity implements OnTaskCompleteListener {
    public final static String TAG = MainActivity.TAG;
    private final static int AUTH_LOADER_ID = 1;
    private final static int CURRENT_USER_LOADER_ID = 4;
    private EditText mLogin;
    private EditText mPassword;
    private AsyncTaskManager mAsyncTaskManager;
    private String sessionId;
    private OnClickListener loginButtonListener = new OnClickListener() {

        @Override
        public void onClick(View arg0) {
            Log.d(TAG, hashCode() + " click!");
            if (mLogin.getText().length() == 0
                    || mPassword.getText().length() == 0) {
                showToastLong(getResources().getString(R.string.login_activity_fill_warning));
            } else {
                final String login = mLogin.getText().toString();
                final String password = mPassword.getText().toString();
                String url = CampusApiURL.getAuth(login, password);
                Log.d(TAG, hashCode() + " loading " + url);
                mAsyncTaskManager.setupTask(new HttpLoadTask(getResources(), url, R.string.login_activity_auth_work, AUTH_LOADER_ID));
            }
        }
    };
    //TODO test button
    private OnClickListener testButtonListener = new OnClickListener() {

        @Override
        public void onClick(View arg0) {
            Log.d(this.getClass().getName(), hashCode() + " test click!");
            if (mLogin.getText().length() == 0
                    || mPassword.getText().length() == 0) {
                showToastLong(getResources().getString(R.string.login_activity_fill_warning));
            } else {
                final String login = mLogin.getText().toString();
                final String password = mPassword.getText().toString();
                //String Url = CampusApiURL.getAuth(login, password);
                //mAsyncTaskManager.setupTask(new HttpLoadTask(getResources(), Url, R.string.login_activity_auth_work, AUTH_LOADER_ID));
                //startSessionIdLoader(Url);
                try (DatabaseHelper db = new DatabaseHelper(getApplicationContext())) {

                    int accountID = parseUser(Mock.getUSER_EMPLOYEE()).getData().getUserAccountID();
                    CurrentUser user = new CurrentUser(accountID, "4344", login, password, 1);
                    db.onUserChanged(user);
                    try (BulletinBoardBase bulletinBoardBase = BulletinBoardBase.getInstance()) {
                        bulletinBoardBase.addAllBulletins(parseBulletinBoard(Mock.getBulletinBoardActual()));
                    }

                    db.createUser(new User(1818, "Карл Генрих Маркс", "http://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Karl_Marx_001.jpg/225px-Karl_Marx_001.jpg"));
                    db.createUser(new User(12, "Бертран Рассел", "http://upload.wikimedia.org/wikipedia/commons/thumb/9/9b/Honourable_Bertrand_Russell.jpg/225px-Honourable_Bertrand_Russell.jpg"));
                    db.createUser(new User(156, "Иммануил Кант", "http://upload.wikimedia.org/wikipedia/commons/thumb/4/43/Immanuel_Kant_%28painted_portrait%29.jpg/200px-Immanuel_Kant_%28painted_portrait%29.jpg"));
                    db.createUser(new User(1464, "Георг Вильгельм Фридрих Гегель", "http://upload.wikimedia.org/wikipedia/commons/thumb/0/08/Hegel_portrait_by_Schlesinger_1831.jpg/250px-Hegel_portrait_by_Schlesinger_1831.jpg"));
                    db.createUser(new User(145, "Альбер Камю", "http://upload.wikimedia.org/wikipedia/commons/thumb/0/08/Albert_Camus%2C_gagnant_de_prix_Nobel%2C_portrait_en_buste%2C_pos%C3%A9_au_bureau%2C_faisant_face_%C3%A0_gauche%2C_cigarette_de_tabagisme.jpg/220px-Albert_Camus%2C_gagnant_de_prix_Nobel%2C_portrait_en_buste%2C_pos%C3%A9_au_bureau%2C_faisant_face_%C3%A0_gauche%2C_cigarette_de_tabagisme.jpg"));
                    db.createUser(new User(174, "Жан-Поль Сартр", "http://upload.wikimedia.org/wikipedia/commons/thumb/d/d1/Jean-Paul_Sartre_FP.JPG/220px-Jean-Paul_Sartre_FP.JPG"));
                    db.createUser(new User(1364, "Освальд Шпенглер", "http://upload.wikimedia.org/wikipedia/commons/thumb/a/ae/Bundesarchiv_Bild_183-R06610%2C_Oswald_Spengler.jpg/200px-Bundesarchiv_Bild_183-R06610%2C_Oswald_Spengler.jpg"));
                    db.createUser(new User(2, "Артур Шопенгауэр", "http://upload.wikimedia.org/wikipedia/commons/thumb/8/8a/Schopenhauer.jpg/220px-Schopenhauer.jpg"));
                    db.createUser(new User(15, "Платон", "http://upload.wikimedia.org/wikipedia/commons/thumb/7/7d/Head_Platon_Glyptothek_Munich_548.jpg/220px-Head_Platon_Glyptothek_Munich_548.jpg"));
                    db.createUser(new User(523, "Фердина́нд де Соссю́р", "http://upload.wikimedia.org/wikipedia/commons/thumb/d/de/Ferdinand_de_Saussure_by_Jullien.png/200px-Ferdinand_de_Saussure_by_Jullien.png"));
                    db.createUser(new User(236, "Мишель Фуко", "http://upload.wikimedia.org/wikipedia/ru/thumb/1/12/Michel_Foucault2.jpg/220px-Michel_Foucault2.jpg"));
                    db.createUser(new User(11, "Фридрих Ницше", "http://upload.wikimedia.org/wikipedia/commons/thumb/1/1b/Nietzsche187a.jpg/220px-Nietzsche187a.jpg"));
                    //db.createUser(new User(236,"Мишель Фуко", ""));

                    try (EmployeeBase employeeBase = EmployeeBase.getInstance()) {
                        employeeBase.createEmployee(new Employee(1, "Базельский университет, Кафедра классической филологии", "Преподаватель", "Профессор классической филологии", "Профессор"), 11);
                    }
                }
                Session.setCurrentUser(parseUser(Mock.getUSER_EMPLOYEE()).getData());
                //Intent intent = new Intent(LoginActivity.this, MessageActivity.class);
                //intent.putExtra(MessagesViewFragment.EXTRA_GROUP_ID, 1);
                //Log.d(MainActivity.TAG, hashCode() + " starting new activity... " + MessageActivity.class.getName());
                //startActivity(intent);
                startMainActivity();
            }
        }
    };

    private ArrayList<BulletinBoardSubject> parseBulletinBoard(String JsonConversation) {
        try {
            return JSONBulletinBoardParser.parse(JsonConversation).getData();
        } catch (JSONException e) {
            Log.e(this.getClass().getName(), hashCode() + getResources().getString(R.string.login_activity_json_error));
        }
        return new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLogin = (EditText) findViewById(R.id.firstNumberEdit);
        mPassword = (EditText) findViewById(R.id.secondNumberEdit);
        Button sumButton = (Button) findViewById(R.id.sumButton);
        sumButton.setOnClickListener(loginButtonListener);
        Button testButton = (Button) findViewById(R.id.testButton);
        testButton.setOnClickListener(testButtonListener);

        mAsyncTaskManager = new AsyncTaskManager(this, this);
        mAsyncTaskManager.handleRetainedTask(getLastCustomNonConfigurationInstance());
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        // Delegate task retain to manager
        return mAsyncTaskManager.retainTask();
    }

    @Override
    public void onTaskComplete(HttpLoadTask task) {
        int currentLoaderId = task.getId();
        Log.d(TAG, hashCode() + " load finished, loader " + currentLoaderId);

        if (task.isCancelled()) {
            // Report about cancel
            Toast.makeText(this, R.string.task_cancelled, Toast.LENGTH_LONG)
                    .show();
        } else {
            // Get result
            HttpResponse httpResponse = null;
            try {
                httpResponse = task.get();
            } catch (Exception e) {
                Log.e(TAG, hashCode() + " task problem", e);
            }

            switch (currentLoaderId) {
                case AUTH_LOADER_ID:
                    checkAuth(httpResponse);
                    break;
                case CURRENT_USER_LOADER_ID:
                    updateUser(httpResponse);
                    break;
            }
        }
    }

    private void updateUser(HttpResponse httpResponse) {
        String userDataStr = httpResponse.getEntity();
        if (httpResponse.getStatusCode() == HttpStatus.SC_OK) {
            //TODO delete Session class
            Session.setCurrentUser(parseUser(userDataStr).getData());
            try (DatabaseHelper db = new DatabaseHelper(getApplicationContext())) {
                final String login = mLogin.getText().toString();
                final String password = mPassword.getText().toString();
                UserData userData = parseUser(userDataStr).getData();
                int accountID = userData.getUserAccountID();
                CurrentUser currentUser = new CurrentUser(accountID, sessionId, login, password, userData.isEmployee());
                User user = new User(accountID, userData.getFullName(), userData.getPhoto());

                if (currentUser.equals(db.getCurrentUser())) {
                    db.updateCurrentUser(currentUser);
                } else {
                    db.onUserChanged(currentUser);
                    db.createUser(user);

                    if (userData.isEmployee()) {
                        try (EmployeeBase employeeBase = EmployeeBase.getInstance()) {
                            Employee currentEmployee = ((UserDataEmployee) userData).getEmployees().get(0);
                            employeeBase.createEmployee(currentEmployee, accountID);
                        }
                    } else {
                        try (PeronalitiesBase peronalitiesBase = PeronalitiesBase.getInstance()) {
                            Personality personality = ((UserDataPersonalities) userData).getPersonalities().get(0);
                            peronalitiesBase.createPersonality(personality, accountID);
                        }
                    }
                }
            }
            startMainActivity();
        } else {
            showToastLong(getResources().getString(R.string.access_denied));
        }
    }

    private void checkAuth(HttpResponse httpResponse) {
        final int statusCode = httpResponse.getStatusCode();
        final String response = httpResponse.getEntity();
        if (statusCode == HttpStatus.SC_OK) {
            try {
                // Create new manager and set this activity as context and listener
                mAsyncTaskManager = new AsyncTaskManager(this, this);
                // Handle task that can be retained before
                mAsyncTaskManager.handleRetainedTask(getLastCustomNonConfigurationInstance());
                Session.setSessionId(JSONAuthorizationParser.parse(response).getData());
                sessionId = JSONAuthorizationParser.parse(response).getData();
                String Url = CampusApiURL.getCurrentUser(Session.getSessionId());
                mAsyncTaskManager.setupTask(new HttpLoadTask(getResources(), Url, R.string.login_activity_getuser_work, CURRENT_USER_LOADER_ID));
            } catch (JSONException e) {
                showToastLong(getResources().getString(R.string.login_activity_json_error));
                Log.e(this.getClass().getName(), hashCode() + getResources().getString(R.string.login_activity_json_error));
            }
        } else {
            showToastLong(getResources().getString(R.string.login_activity_auth_fail));
        }
    }

    private JsonObject<UserData> parseUser(String userDataStr) {
        try {
            return JSONUserDataParser.parse(userDataStr);
        } catch (JSONException e) {
            showToastLong(getResources().getString(R.string.login_activity_json_error));
            Log.e(this.getClass().getName(), hashCode() + getResources().getString(R.string.login_activity_json_error));
        }
        //it`s ok because of checking for null further
        return null;
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        Log.d(TAG, hashCode() + " starting new activity... " + MainActivity.class.getName());
        startActivity(intent);
    }

    private void showToastLong(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
        Log.d(TAG, hashCode() + " ToastLong:" + text);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
