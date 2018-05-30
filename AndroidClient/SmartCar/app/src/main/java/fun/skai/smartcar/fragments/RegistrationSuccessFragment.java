package fun.skai.smartcar.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import fun.skai.smartcar.R;
import fun.skai.smartcar.activities.RegisterActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationSuccessFragment extends Fragment {

    private static final String TAG = "RegisterSuccessFragment";

    public RegistrationSuccessFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_registration_success, container, false);

        Button mToCompleteInfoView = view.findViewById(R.id.complete_info_link);
        mToCompleteInfoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity activity = (RegisterActivity) getActivity();
                Log.d(TAG, "onClick: launch FillInfoFragment");
                activity.replaceFragment(new FillInfoFragment(), false);
//                activity.replaceFragment(new RegistrationFailedFragment(), false);
            }
        });
        return view;
    }

}
