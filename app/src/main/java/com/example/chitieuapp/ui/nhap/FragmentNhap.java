package com.example.chitieuapp.ui.nhap;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chitieuapp.R;
import com.example.chitieuapp.ui.ViewNhapAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentNhap#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentNhap extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    ViewPager2 viewNhap;
    TabLayout tabLayout;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentNhap() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentNhap.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentNhap newInstance(String param1, String param2) {
        FragmentNhap fragment = new FragmentNhap();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nhap, container, false);
        viewNhap = view.findViewById(R.id.ViewChiTieu);
        tabLayout = view.findViewById(R.id.TabChiTieu);
        ViewNhapAdapter adapter = new ViewNhapAdapter(this);
        viewNhap.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewNhap,
                (tab, position) -> {
                    if (position == 0) {
                        tab.setText("Chi tiêu");
                    } else {
                        tab.setText("Thu nhập");
                    }
                }).attach();

        return view;
    }
}