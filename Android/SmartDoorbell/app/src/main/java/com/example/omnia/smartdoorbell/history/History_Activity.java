package com.example.omnia.smartdoorbell.history;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.omnia.smartdoorbell.R;

import java.util.ArrayList;
import java.util.List;

public class History_Activity extends AppCompatActivity {
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private ViewPager viewPager;
    String ip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_);

//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        Intent intent=getIntent();
        ip=intent.getStringExtra("ip");
        Bundle b=new Bundle();
        b.putString("ip",ip);

        allFragment all=new allFragment();
        all.setArguments(b);

        TrustedFragment t=new TrustedFragment();
        t.setArguments(b);

        LockFragment l=new LockFragment();
        l.setArguments(b);

        BlockFragment blockFragment=new BlockFragment();
        blockFragment.setArguments(b);

        UnkownFragment unkownFragment=new UnkownFragment();
        unkownFragment.setArguments(b);

        UnlockFragment unlockFragment=new UnlockFragment();
        unlockFragment.setArguments(b);





    }
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(R.drawable.all_ist_32);
        tabLayout.getTabAt(1).setIcon(R.drawable.apply_32);
        tabLayout.getTabAt(2).setIcon(R.drawable.denied_32);
        tabLayout.getTabAt(3).setIcon(R.drawable.lock_2_32);
        tabLayout.getTabAt(4).setIcon(R.drawable.unlock__32);
        tabLayout.getTabAt(5).setIcon(R.drawable.unkown_32);
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new allFragment(), "ALL");
        adapter.addFrag(new TrustedFragment(), "TRUSTED");
        adapter.addFrag(new BlockFragment(), "BLOCK");
        adapter.addFrag(new LockFragment(), "LOCK");
        adapter.addFrag(new UnlockFragment(), "UNLOCK");
        adapter.addFrag(new UnkownFragment(), "UNKOWN");
//        adapter.addFrag(new SevenFragment(), "SEVEN");
//        adapter.addFrag(new EightFragment(), "EIGHT");
//        adapter.addFrag(new NineFragment(), "NINE");
//        adapter.addFrag(new TenFragment(), "TEN");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
//            mFragmentTitleList.get(position)
            return null;
        }
    }
}
