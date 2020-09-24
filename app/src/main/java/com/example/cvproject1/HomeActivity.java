package com.example.cvproject1;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreateDrawer();
        super.updateView(R.layout.activity_home);
        activityName = "Shop";

        ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Downloading");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (MealsHandler.getPizza().size() == 0 || MealsHandler.getPasta().size() == 0 || MealsHandler.getRice().size() == 0){
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        setupViewPager();
                        progress.dismiss();
                    }
                });
            }
        }).start();
    }


    @Override
    protected void onResume() {
        super.onResume();
        updateCartQuantity();
        setupViewPager();
    }

    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new PizzaFragment(), getString(R.string.pizza));
        adapter.addFragment(new PastaFragment(), getString(R.string.pasta));
        adapter.addFragment(new RiceFragment(), getString(R.string.rice));
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

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}