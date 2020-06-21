package application.example.mytodo;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import application.example.mytodo.adapters.MyPagerAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = findViewById(R.id.tab_layout);

        ViewPager pager = findViewById(R.id.viewPager);
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(this, getSupportFragmentManager());
        pagerAdapter.addFragment(new RegisterFragment(), "Register");
        pagerAdapter.addFragment(new LoginFragment(), "Login");

        pager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(pager);

    }

}
