package janche.ripaxiety;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

/**
 * Created by: Jose Pena 11/19/16
 * Modified by: Patrick Abbey 12/6/16
 * Modified by: Jose Pena 12/7/16
 *
 * This is the main activity. It is not a regular activity. it is fragment activity. Don't treated it like a regular activity
 * This fragment main activity allows the user to swipe left or right in the first activity to sign in, or sign up
 *
 */



public class MainActivity extends FragmentActivity
{
    ViewPager viewpager;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //fragment manager
        viewpager = (ViewPager) findViewById(R.id.pager);
        PagerAdapter padapter = new PagerAdapter(getSupportFragmentManager());
        viewpager.setAdapter(padapter);
    }
}




//The content below this comment was commented for version control/ back up just incase my merging messes up everything.
//If the top works and runs, than the bottom may be deleted

/*
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;


 * This is the main activity. It is not a regular activity. it is fragment activity. Don't treated it like a regular activity
 * This fragment main activity allows the user to swipe left or right in the first activity to sign in, or sign up
 *
 * Created by: Jose Pena 11/19/16
 * Modified by: Patrick Abbey 12/6/16
 



public class MainActivity extends FragmentActivity
{
    ViewPager viewpager;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //fragment manager
        viewpager = (ViewPager) findViewById(R.id.pager);
        PagerAdapter padapter = new PagerAdapter(getSupportFragmentManager());
        viewpager.setAdapter(padapter);
    }
}
*/

