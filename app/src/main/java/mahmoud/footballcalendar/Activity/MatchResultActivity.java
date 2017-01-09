package mahmoud.footballcalendar.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import mahmoud.footballcalendar.Adapter.ViewPagerAdapter;
import mahmoud.footballcalendar.Fragment.Fragment1;
import mahmoud.footballcalendar.Fragment.Fragment2;
import mahmoud.footballcalendar.R;

public class MatchResultActivity extends AppCompatActivity {

    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    String TeamName;
    public  static String teamName_static;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_result);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.setTitle("");

        init();
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        TabLayout.Tab tab = tabLayout.getTabAt(1);
        tab.select();


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
    }

    private void init() {
        this.TeamName = getIntent().getExtras().getString("team_name");
        setTeamName_static(TeamName);
        // TODO :  set toolbar name
        switch (TeamName){
            case "chelsea":

                break;
            case "manutd":

                break;
            case "arsenal":

                break;
            case "barcelona":

                break;
            case "realmadrid":

                break;
            case "bayern":

                break;
        }
    }

    public static void setTeamName_static(String team_name){
        teamName_static = team_name;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Fragment2(), "fixtures");
        adapter.addFragment(new Fragment1(),"done");
        viewPager.setAdapter(adapter);
    }
}
