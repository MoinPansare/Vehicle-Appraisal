package vehicleappraisal.com.vehicleappraisal;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import vehicleappraisal.com.vehicleappraisal.Tabs.*;


public class Home extends AppCompatActivity {

    private CustomPager mPager;
    private SlidingTabLayout myTabLayout;
    private Fragment1 frg1;
    private Fragment2 frg2;
    private Fragment3 frg3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mPager = (CustomPager)findViewById(R.id.viewPagerHome);
        myTabLayout = (SlidingTabLayout)findViewById(R.id.slidingtabLayout);
        mPager.setPagingEnabled(false);
        mPager.setScrollDurationFactor(3);

        frg1 = Fragment1.getInstance();
        frg2 = Fragment2.getInstance();
        frg3 = Fragment3.getInstance();
        frg3.setMyContext(this);

        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        myTabLayout.setDistributeEvenly(true);
        myTabLayout.setViewPager(mPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        String[] tabs ;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            tabs = getResources().getStringArray(R.array.tabs);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position) {

                case 0: return frg1;
                case 1:return frg2;
                case 2: return frg3;
                default: return frg1;
            }

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
