package cf.thepowerfuldeez.gym5meetings;

import android.annotation.TargetApi;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;


public class OtherSubjectsDialog extends DialogFragment implements View.OnClickListener {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Title!");
        View v = inflater.inflate(R.layout.dialog_layout, null);
        v.findViewById(R.id.btn_inf).setOnClickListener(this);
        v.findViewById(R.id.btn_phys).setOnClickListener(this);
        v.findViewById(R.id.btn_soc).setOnClickListener(this);
        v.findViewById(R.id.btn_chem).setOnClickListener(this);
        v.findViewById(R.id.btn_biol).setOnClickListener(this);
        return v;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void onClick(View v) {
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.alignWithParent = true;
        Spinner spinner = new Spinner(super.getContext());
        ArrayAdapter<?> adapter = null;
        TextView textView = new TextView(super.getContext());
        switch (v.getId()) {
            case R.id.btn_inf:
                adapter = ArrayAdapter.createFromResource(super.getContext(), R.array.inf_groups_array, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                textView.setText("Группа по информатике:");
                break;
            case R.id.btn_phys:
                adapter = ArrayAdapter.createFromResource(super.getContext(), R.array.phys_groups_array, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                textView.setText("Группа по физике:");
                break;
            case R.id.btn_soc:
                adapter = ArrayAdapter.createFromResource(super.getContext(), R.array.soc_groups_array, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                textView.setText("Группа по обществознанию:");
                break;
            case R.id.btn_chem:
                adapter = ArrayAdapter.createFromResource(super.getContext(), R.array.chem_groups_array, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                textView.setText("Группа по химии:");
                break;
            case R.id.btn_biol:
                adapter = ArrayAdapter.createFromResource(super.getContext(), R.array.biol_groups_array, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                textView.setText("Группа по биологии:");
                break;
        }
        spinner.setAdapter(adapter);
        super.getActivity().addContentView(textView, lp);
        super.getActivity().addContentView(spinner, lp);
        dismiss();
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }
}
