package edu.bilkent.findatutor;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import edu.bilkent.findatutor.fragments.MyPostsFragment;
import edu.bilkent.findatutor.fragments.RecentPostsFragment;
import edu.bilkent.findatutor.fragments.RecentRequestedPostsFragment;

public class MainActivity extends BaseActivity {


    private static final String TAG = "MainActivity";

    private FragmentPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
    private String username;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        if(drawer != null) {
            drawer.setDrawerListener(toggle);
            toggle.syncState();
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if(navigationView != null)
            navigationView.setNavigationItemSelectedListener(this);

//        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_main);
//        ImageView imageView = (ImageView)headerView.findViewById(R.id.imageView);
//
//        String url = getPhotoURL();
//        Glide
//                .with(MainActivity.this)
//                .load(url)
//                .transform(new CircleTransform(getBaseContext()))
//                .into(imageView);


        username = getIntent().getStringExtra("username");
        email = getIntent().getStringExtra("email");




        // Create the adapter that will return a fragment for each section
        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            private final Fragment[] mFragments = new Fragment[]{
                    new RecentPostsFragment(),
                    new MyPostsFragment(),
                    new RecentRequestedPostsFragment(),
            };
            private final String[] mFragmentNames = new String[]{
                    getString(R.string.tab_name_1),
                    getString(R.string.tab_name_2),
                    getString(R.string.tab_name_3)
            };

            @Override
            public Fragment getItem(int position) {
                return mFragments[position];
            }

            @Override
            public int getCount() {
                return mFragments.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mFragmentNames[position];
            }
        };
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        // Button launches NewPostActivity
        findViewById(R.id.fab_new_post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewPostActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });

    }

}
