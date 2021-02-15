package com.example.entreprenuershipproject.fragment.tester;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.entreprenuershipproject.R;

public class TesterFragmentParent extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_tester_parent, container, false);

        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.TesterFragmentChanger, new TesterTimer());
//        fragmentTransaction.replace(R.id.TesterFragmentChanger, new TesterShopMenu());
//        fragmentTransaction.replace(R.id.TesterFragmentChanger, new TesterQueueAssignment());
        fragmentTransaction.commit();

        return root;
    }

}
