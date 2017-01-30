package janche.ripaxiety;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by josepena on 11/15/16.
 * Created by: Jose Pena 11/15/16
 * Modified by: Patrick Abbey 12/6/16
 * Modified by: Jose Pena 12/7/16
 */

public class PagerAdapter extends FragmentPagerAdapter
{
    public PagerAdapter (FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int position)
    {
        switch (position)
        {
            case 0:
                return new SignInFragment();

            case 1:
                return new SignUpFragment();

            default:
                break;
        }
        return null;
    }

}






//Bottom portion commented for version control dont delete yet!
/*

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


 * Created by: Jose Pena 11/15/16
 * Modified by: Patrick Abbey 12/6/16
 

public class PagerAdapter extends FragmentPagerAdapter
{
    public PagerAdapter (FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public int getCount()
    {
        return 2;
    }

    @Override
    public Fragment getItem(int position)
    {
        switch (position)
        {
            case 0:
                return new SignInFragment();

            case 1:
                return new SignUpFragment();

            default:
                break;
        }
        return null;
    }

}
*/
