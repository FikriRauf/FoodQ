package com.example.entreprenuershipproject.fragment.tester;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.entreprenuershipproject.R;

public class TesterFragmentParent2 extends Fragment {

    public TesterFragmentParent2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tester_parent_2, container, false);

        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentChanger, new TesterSetValueToDatabase());
        fragmentTransaction.commit();

        return root;
    }

}

