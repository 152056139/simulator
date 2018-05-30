package fun.skai.smartcar.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import fun.skai.smartcar.R;
import fun.skai.smartcar.activities.LoginActivity;
import fun.skai.smartcar.activities.RegisterActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationFailedFragment extends Fragment {


    public RegistrationFailedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registration_failed, container, false);
        Button mToLoginButton = view.findViewById(R.id.register_to_login);
        mToLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity activity = (RegisterActivity) getActivity();
                Intent intent = new Intent(activity, LoginActivity.class);
                startActivity(intent);
                if (activity != null) {
                    activity.finish();
                }
            }
        });
        return view;
    }

}
