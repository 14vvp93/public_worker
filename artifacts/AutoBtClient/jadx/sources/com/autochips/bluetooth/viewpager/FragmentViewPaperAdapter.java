package com.autochips.bluetooth.viewpager;

import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class FragmentViewPaperAdapter extends FragmentPagerAdapter {
    int currentposition;
    private List<Fragment> fragments;

    @Override // androidx.fragment.app.FragmentPagerAdapter, androidx.viewpager.widget.PagerAdapter
    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
    }

    public FragmentViewPaperAdapter(FragmentManager fragmentManager, List<Fragment> list) {
        super(fragmentManager);
        this.currentposition = -1;
        this.fragments = list;
    }

    @Override // androidx.fragment.app.FragmentPagerAdapter
    public long getItemId(int i) {
        return super.getItemId(i);
    }

    @Override // androidx.fragment.app.FragmentPagerAdapter, androidx.viewpager.widget.PagerAdapter
    public void setPrimaryItem(ViewGroup viewGroup, int i, Object obj) {
        setCurrentPosition(i);
        super.setPrimaryItem(viewGroup, i, obj);
    }

    @Override // androidx.fragment.app.FragmentPagerAdapter
    public Fragment getItem(int i) {
        return this.fragments.get(i);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getCount() {
        return this.fragments.size();
    }

    public void setCurrentPosition(int i) {
        this.currentposition = i;
    }

    public int getCurrentPosition() {
        return this.currentposition;
    }
}
