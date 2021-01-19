package Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class FragmentAdapter extends FragmentStatePagerAdapter {
    static final int NUM_ITEMS = 3;
    private final FragmentManager mFragmentManager;
    private Fragment mFragmentAtPos0;


    public void addFragmentList(Fragment fragmentList) {
        this.fragmentList.add(fragmentList);
    }

    private List<Fragment> fragmentList = new ArrayList<>();



    public FragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
        mFragmentManager = fm;
    }
    public void changeFragment(Fragment fragment, int id, ViewPager viewPager){
        fragmentList.remove(id);
        fragmentList.add(id,fragment);
super.notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position)
    {
     return fragmentList.get(position);
    }

    @Override
    public int getCount()
    {
        return fragmentList.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}

