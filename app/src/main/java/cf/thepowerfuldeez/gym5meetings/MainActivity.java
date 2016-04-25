package cf.thepowerfuldeez.gym5meetings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String login;
    SharedPreferences sPref;
    Firebase ref;
    String uch_plan;
    String main_shedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Firebase.setAndroidContext(this);
        sPref = getSharedPreferences("Credentials",MODE_PRIVATE);
        login = sPref.getString("saved_email", "").split("@")[0];


        final TextView nameName = (TextView) findViewById(R.id.nameName);
        TextView loginName = (TextView) findViewById(R.id.loginName);
        if (loginName != null) {
            loginName.setText(login);
        }


        ref = new Firebase("https://gym5meetings.firebaseio.com");
        Firebase refBase = ref.child("users").child(login);

        refBase.child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (nameName != null) {
                    nameName.setText(String.valueOf(snapshot.getValue()));
                }
            }
            @Override public void onCancelled(FirebaseError error) { }
        });


        final TextView class_num = (TextView) findViewById(R.id.class_num);
        final TextView my_groups = (TextView) findViewById(R.id.my_groups);
        refBase.child("uchplan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (my_groups != null) {
                    uch_plan = String.valueOf(snapshot.getValue());
                    String groups = uch_plan.substring(3);
                    if (class_num != null) {
                        class_num.setText(uch_plan.substring(0, 2));
                    }
                    my_groups.setText(groups);
                }
            }
            @Override public void onCancelled(FirebaseError error) { }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();

        Firebase refBase = ref.child("users").child(login);

        refBase.child("uchplan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String class_num = String.valueOf(snapshot.getValue()).substring(0, 2);
                get_and_save_subject_array_for_all_days(class_num);  // Getting all schedule from Firebase and saving it.
            }
            @Override public void onCancelled(FirebaseError error) { }
        });

        System.out.println("After saving");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_main_schedule) {
            Intent main_schedule_intent = new Intent(getApplicationContext(), MainSheduleActivity.class);
            startActivity(main_schedule_intent);
        }

        else if (id == R.id.nav_my_schedule) {
            Intent my_schedule_intent = new Intent(getApplicationContext(), MyScheduleActivity.class);
            my_schedule_intent.putExtra("uch_plan", uch_plan); // Sending with uch_plan
            startActivity(my_schedule_intent);


        }

        else if (id == R.id.nav_slideshow) {

        }

        else if (id == R.id.nav_manage) {
            Intent select_subjects_intent = new Intent(getApplicationContext(), SelectSubjectsActivity.class);
            startActivity(select_subjects_intent);
        }

        else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void get_and_save_subject_array_for_all_days(String class_num) {
        Firebase firebase_ref = ref.child("schedule").child(class_num);
        final ArrayList<String> raw_days = new ArrayList<>();
        for (String day: new String[]{"пн", "вт", "ср", "чт", "пт", "сб"}) {
            System.out.println("Current day is " + day);
            firebase_ref.child(day).child("subjects").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    String s = String.valueOf(snapshot.getValue());
                    raw_days.add(s);

                    if (raw_days.size() == 6) {
                        main_shedule = TextUtils.join("@", raw_days);

                        SharedPreferences.Editor ed = sPref.edit();
                        ed.putString("main_schedule", main_shedule);
                        ed.apply();
                    }
                }
                @Override public void onCancelled(FirebaseError error) { }
            });

        }
    }
}
