package com.nimashi.pawpaw;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class LoginFragment extends Fragment {

    TabLayout tablayout;
    ViewPager viewPager;
    float v=0;

    SessionManagement sessionManagement;


//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        LoginAdapter pagerAdapter= new LoginAdapter(getChildFragmentManager(),getActivity(), tablayout.getTabCount());
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        getActivity().setTitle("Login");

        //SessionManagement.SessionManagement(getActivity(),"session","true");

        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        String session=sharedPreferences.getString("session1","");
//        sessionManagement =new SessionManagement(getActivity());
//        sessionManagement.checkLogin();
        viewPager = view.findViewById(R.id.view_pager);
        tablayout = view.findViewById(R.id.tab_layout);
        // tablayout.setupWithViewPager(viewPager);

        tablayout.addTab(tablayout.newTab().setText("Login"));
        tablayout.addTab(tablayout.newTab().setText("Sign Up"));
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // final LoginAdapter adapter=new LoginAdapter(getSupportFragmentManager(),this,tablayout.getTabCount());
        // viewPager.setAdapter(adapter);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        final ViewPager viewPager = view.findViewById(R.id.view_pager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
        viewPager.setAdapter(new LoginAdapter(getFragmentManager(), getActivity(), tablayout.getTabCount()));

        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tablayout.setTranslationY(300);
        tablayout.setAlpha(v);
        tablayout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(500).start();


        return view;
    }


    public class LoginAdapter extends FragmentStatePagerAdapter {
        private Context context;
        int totalTabs;

        public LoginAdapter(FragmentManager fm, Context context, int totalTabs) {
            super(fm);
            this.context = context;
            this.totalTabs = totalTabs;

        }

        @Override
        public int getCount() {
            return totalTabs;
        }

        public Fragment getItem(int position) {
            switch (position) {
                case 0:

                    LoginTabFragment loginTabFragment = new LoginTabFragment();
                    return loginTabFragment;
                case 1:
                    SignUpTabFragment signUpTabFragment = new SignUpTabFragment();
                    return signUpTabFragment;

                default:
                    return null;

            }
        }

    }









}
