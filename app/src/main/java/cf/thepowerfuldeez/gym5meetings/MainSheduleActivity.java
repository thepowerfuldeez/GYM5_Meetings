package cf.thepowerfuldeez.gym5meetings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.Firebase;

public class MainSheduleActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    static SharedPreferences sPref;
    static String day_of_the_week;
    static Firebase ref;
    public static Context context;
    static String[][] subjects_array = {{"","","","","","","","","",""},{"","","","","","","","","",""},{"","","","","","","","","",""},{"","","","","","","","","",""},{"","","","","","","","","",""},{"","","","","","","","","",""}};

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main_schedule);
        Firebase.setAndroidContext(this);
        ref = new Firebase("https://gym5meetings.firebaseio.com");
        getSubject_array_for_all_days_from_rom();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        getSubject_array_for_all_days_from_rom();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_shedule, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main_schedule, container, false);
            ListView subjectsList = (ListView) rootView.findViewById(R.id.subjectsList);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            int number = getArguments().getInt(ARG_SECTION_NUMBER);
            switch (number) {
                case 1:
                    day_of_the_week = "ПОНЕДЕЛЬНИК";
                    break;
                case 2:
                    day_of_the_week = "ВТОРНИК";
                    break;
                case 3:
                    day_of_the_week = "СРЕДА";
                    break;
                case 4:
                    day_of_the_week = "ЧЕТВЕРГ";
                    break;
                case 5:
                    day_of_the_week = "ПЯТНИЦА";
                    break;
                case 6:
                    day_of_the_week = "СУББОТА";
                    break;
                default:
                    day_of_the_week = "???????";
                    break;
            }
            textView.setText(day_of_the_week);

            int index = 0;
            switch (day_of_the_week) {
                case "ПОНЕДЕЛЬНИК":
                    index = 0;
                    break;
                case "ВТОРНИК":
                    index = 1;
                    break;
                case "СРЕДА":
                    index = 2;
                    break;
                case "ЧЕТВЕРГ":
                    index = 3;
                    break;
                case "ПЯТНИЦА":
                    index = 4;
                    break;
                case "СУББОТА":
                    index = 5;
                    break;
            }


            System.out.println("Requesting subjects_array from method below. Length - " + subjects_array.length + ", index - " + index);
            subjectsList.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, subjects_array[index]));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 6 total pages.
            return 6;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
                case 3:
                    return "SECTION 4";
                case 4:
                    return "SECTION 5";
                case 5:
                    return "SECTION 6";

            }
            return null;
        }
    }

    public static void getSubject_array_for_all_days_from_rom() {
        sPref = context.getSharedPreferences("Credentials", MODE_PRIVATE);
        String[] loaded_main_schedule = sPref.getString("main_schedule", "").split("@");
        System.out.println(loaded_main_schedule.length);
        for (int i = 0; i < 6; i++) {
            System.out.println("Week day num. " + i);
            String[] s = loaded_main_schedule[i].split(";");
            for (int j = 0; j < s.length; j++) {
                System.out.println("Subj. is " + s[j]);
                subjects_array[i][j] = s[j];
            }
        }
    }
}
