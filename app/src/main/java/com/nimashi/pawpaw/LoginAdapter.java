package com.nimashi.pawpaw;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class LoginAdapter extends FragmentPagerAdapter {
    private Context context;
    int totalTabs;
    private final LoginTabFragment loginTabFragment=new LoginTabFragment();
    private final SignUpTabFragment signUpTabFragment=new SignUpTabFragment();

    public LoginAdapter(FragmentManager fm,Context context,int totalTabs){
        super(fm);
        this.context=context;
        this.totalTabs=totalTabs;

    }

    @Override
    public int getCount() {
        return totalTabs;
    }

    public Fragment getItem(int position)
    {
        switch (position)
        {
            case 0:

                return loginTabFragment;
            case 1:

                return signUpTabFragment;

                default:
                 return null;

        }
    }

//    @Override
//    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.setPrimaryItem(container, position, object);
//
//        if(position ==0)
//        {
//            loginTabFragment.updateUI();
//        }
//    }
}
