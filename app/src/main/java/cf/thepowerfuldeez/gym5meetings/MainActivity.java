package cf.thepowerfuldeez.gym5meetings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    OtherSubjectsDialog dialog;


    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);

        final TextView nameName = (TextView) findViewById(R.id.nameName);
        TextView loginName = (TextView) findViewById(R.id.loginName);
        String login = getIntent().getStringExtra("eml").split("@")[0];
        if (loginName != null) {
            loginName.setText(login);
        }

        Firebase ref = new Firebase("https://gym5meetings.firebaseio.com");
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


        final Spinner sp0 = (Spinner) findViewById(R.id.spChoosingClass);
        final Spinner sp1 = (Spinner) findViewById(R.id.spChoosingMath);
        final Spinner sp2 = (Spinner) findViewById(R.id.spChoosingRus);
        final Spinner sp3 = (Spinner) findViewById(R.id.spChoosingLiter);
        final Spinner sp4 = (Spinner) findViewById(R.id.spChoosingEng);
        final Spinner sp5 = (Spinner) findViewById(R.id.spChoosingHist);
        final Spinner sp6 = (Spinner) findViewById(R.id.spChoosingPE);

//        ImageView toast_btn = (ImageView) findViewById(R.id.xxx);
//        toast_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String text = sp0.getSelectedItem().toString() + "\n" + sp1.getSelectedItem().toString() + "\n" +sp2.getSelectedItem().toString() + "\n" +sp3.getSelectedItem().toString() + "\n" +sp4.getSelectedItem().toString() + "\n" +sp5.getSelectedItem().toString() + "\n" +sp6.getSelectedItem().toString();
//                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
//            }
//        });

        final Button btnAddSubject = (Button) findViewById(R.id.btnAddSubject);
        dialog = new OtherSubjectsDialog();
        btnAddSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show(getFragmentManager(), "dialog");
            }
        });

    }

//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btnAddSubject:
//                dialog.show(getFragmentManager(), "dialog");
//                break;
//            default:
//                break;
//        }
//    }
}
