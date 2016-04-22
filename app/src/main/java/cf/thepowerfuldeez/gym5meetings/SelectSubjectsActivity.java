package cf.thepowerfuldeez.gym5meetings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Spinner;

import com.firebase.client.Firebase;

public class SelectSubjectsActivity extends AppCompatActivity {

    SharedPreferences sPref;
    Firebase ref;
    String login;
    String UCH_PLAN = "uch_plan";


    Spinner sp0, sp1, sp2, sp3, sp4, sp5, sp6, sp7, sp8, sp9, sp10, sp11, sp12, sp13, sp14;
    String sp0_cached, sp1_cached, sp2_cached, sp3_cached, sp4_cached, sp5_cached, sp6_cached, sp7_cached, sp8_cached, sp9_cached, sp10_cached, sp11_cached, sp12_cached, sp13_cached, sp14_cached;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.select_subjects_layout0);
        Firebase.setAndroidContext(this);
        ref = new Firebase("https://gym5meetings.firebaseio.com");

        sPref = getSharedPreferences("Credentials", MODE_PRIVATE);
        login = sPref.getString("saved_email", "").split("@")[0];

    }

    public void step1(View v) {
        setContentView(R.layout.select_subjects_layout1);
    }

    public void step2(View v) {
        sp0 = (Spinner) findViewById(R.id.spChoosingClass);
        sp1 = (Spinner) findViewById(R.id.spChoosingMath);
        sp2 = (Spinner) findViewById(R.id.spChoosingRus);
        sp3 = (Spinner) findViewById(R.id.spChoosingLiter);
        sp4 = (Spinner) findViewById(R.id.spChoosingEng);
        sp5 = (Spinner) findViewById(R.id.spChoosingHist);
        sp6 = (Spinner) findViewById(R.id.spChoosingPE);


        sp0_cached = sp0.getSelectedItem().toString();
        sp1_cached = sp1.getSelectedItem().toString();
        sp2_cached = sp2.getSelectedItem().toString();
        sp3_cached = sp3.getSelectedItem().toString();
        sp4_cached = sp4.getSelectedItem().toString();
        sp5_cached = sp5.getSelectedItem().toString();
        sp6_cached = sp6.getSelectedItem().toString();
        setContentView(R.layout.select_subjects_layout2);
    }

    public void step3(View v) {
        sp7 = (Spinner) findViewById(R.id.spChoosingInf);
        sp8 = (Spinner) findViewById(R.id.spChoosingPhys);
        sp9 = (Spinner) findViewById(R.id.spChoosingSoc);
        sp10 = (Spinner) findViewById(R.id.spChoosingChem);
        sp11 = (Spinner) findViewById(R.id.spChoosingBiol);
        sp12 = (Spinner) findViewById(R.id.spChoosingGeogr);
        sp13 = (Spinner) findViewById(R.id.spChoosingAstr);
        sp14 = (Spinner) findViewById(R.id.spChoosingPiEG);


        sp7_cached = sp7.getSelectedItem().toString();
        sp8_cached = sp8.getSelectedItem().toString();
        sp9_cached = sp9.getSelectedItem().toString();
        sp10_cached = sp10.getSelectedItem().toString();
        sp11_cached = sp11.getSelectedItem().toString();
        sp12_cached = sp12.getSelectedItem().toString();
        sp13_cached = sp13.getSelectedItem().toString();
        sp14_cached = sp14.getSelectedItem().toString();

        String uchplan = String.format("%S %S %S %S %S %S %S %S %S %S %S %S %S %S %S", sp0_cached, sp1_cached, sp2_cached, sp3_cached, sp4_cached, sp5_cached, sp6_cached, sp7_cached, sp8_cached, sp9_cached, sp10_cached, sp11_cached, sp12_cached, sp13_cached, sp14_cached);
        ref.child("users").child(login).child("uchplan").setValue(uchplan);
        SharedPreferences.Editor ed = getSharedPreferences("UchPlan", MODE_PRIVATE).edit();
        ed.putString(UCH_PLAN, uchplan);
        ed.commit();  // Writes study plan to Firebase database

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);  // Back to MainActivity
    }
}
