package cn.hxyac.qingkong;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class CityFragmentPagerAdapter extends FragmentStateAdapter {
    List<Fragment> fragmentList;

    public CityFragmentPagerAdapter(@NonNull FragmentActivity fragmentActivity,List<Fragment> fragmentLis) {
        super(fragmentActivity);
        this.fragmentList = fragmentLis;
    }

    public CityFragmentPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<Fragment> fragmentList) {
        super(fragmentManager, lifecycle);
        this.fragmentList = fragmentList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        System.out.println("创建" + position);
        return fragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        System.out.println("得到数量"+fragmentList.size());
        return fragmentList.size();//Number of fragments displayed
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

}
