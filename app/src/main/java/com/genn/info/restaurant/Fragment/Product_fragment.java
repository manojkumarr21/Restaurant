package com.genn.info.restaurant.Fragment;


import android.os.Build;
import android.os.Bundle;

import com.genn.info.restaurant.MainActivity;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.genn.info.restaurant.Adapter.GridAdapter;
import com.genn.info.restaurant.Model.TabDetails;
import com.genn.info.restaurant.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Product_fragment extends Fragment {

    private SectionsPagerAdapter mSectionsPagerAdapter;
View view;


    static String[] web = new String[4];
    static String[] cash = new String[4];
    static int[] imageId = new int[4];

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    public static ViewPager mViewPager;
    private TabLayout mTabLayout;
    static String Tabsname;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_product_fragment, container, false);
//        GridView gv = view.findViewById(R.id.gv);

 /*       Toolbar toolbar = view.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
      ActionBar mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mTabLayout =view.findViewById(R.id.tabs);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getActivity().getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = view.findViewById(R.id.container);
        populateViewPager();
        return view;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void populateViewPager() {
        TabDetails tab;

        String[] Tittle= new String[7];
        Tittle[0] = "ALL";
        Tittle[1] = "FAVOURITES";
        Tittle[2] = "DESSERTS";
        Tittle[3] = "DRINKS";
        Tittle[4] = "MAIN COURSE";
        Tittle[5] = "SNACKS";
        Tittle[6] = "TIFFIN";


        for (int i = 0; i < Tittle.length; i++) {
            tab = new TabDetails(Tittle[i], PlaceholderFragment.newInstance(R.layout.fragment_main, i));
            mSectionsPagerAdapter.addFragment(tab);
        }
        mViewPager.setAdapter(mSectionsPagerAdapter);


        //Make tabs scrollable
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

     /*   mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
        //mViewPager.getTransitionName();
          Log.e("fragementtttttttt",""+ mViewPager.getAdapter().getPageTitle(mViewPager.getCurrentItem()));
        //Tabsname=""+ mViewPager.getAdapter().getPageTitle(mViewPager.getCurrentItem());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

//                Log.e("rrrrrrrrrrrrrrrrrrrr",""+ mViewPager.getAdapter().getPageTitle(mViewPager.getCurrentItem()));
            }
        });
*/
        mTabLayout.setupWithViewPager(mViewPager);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_LAYOUT = "layout";
        private static final String ARG_COUNTER = "counter";

//        String[] web;
//        String[] cash;
//        int[] imageId;


        public PlaceholderFragment() {
        }
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int layout, Integer counter) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_LAYOUT, layout);
            args.putInt(ARG_COUNTER, counter);
            fragment.setArguments(args);
            return fragment;
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
//            GridView gv = null;
             final View rootView = inflater.inflate(getArguments().getInt(ARG_LAYOUT), container, false);
            final Integer counter = getArguments().getInt(ARG_COUNTER);



        Log.e("placrrrcogggggggggg",""+ mViewPager.getAdapter().getPageTitle(mViewPager.getCurrentItem()));
//        Log.e("ounnyyyyyyyyynnnnnnnnnn",""+counter);
//        Log.e("ounnyyyyynhghghghnnnnnn",""+isInLayout());

            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    Log.e("111111111111111",""+ mViewPager.getAdapter().getPageTitle(position));

                    Tabsname=""+ mViewPager.getAdapter().getPageTitle(position);

                    GridView gv = null;
                    if (Tabsname.equals("DESSERTS")){

                        web = new String[]{
                                "Google",
                                "Github",
                                "Instagram",
                                "Facebook",
                                "Flickr",
                        };
                        cash = new String[]{
                                "200",
                                "100",
                                "300",
                                "100",
                                "200",
                        };
                        imageId = new int[]{
                                R.drawable.ic_launcher_background,
                                R.drawable.ic_launcher_background,
                                R.drawable.ic_launcher_background,
                                R.drawable.ic_launcher_background,
                                R.drawable.ic_launcher_background,
                        };

                        gv = rootView. findViewById(R.id.gv);

                        GridAdapter adapter = new GridAdapter(getActivity(),web,imageId,cash);

                        // Data bind GridView with ArrayAdapter (String Array elements)
                        gv.setAdapter(adapter);

                        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view,
                                                    int position, long id) {

                                Log.e("wwwwwwwwwwwwwwwwww",""+web.toString());
//                                Toast.makeText(getActivity(), "You Clicked at " + web.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    } else if (Tabsname.equals("ALL")){

                        web = new String[]{
                                "Google",
                                "Github",
                                "Instagram",
                                "Facebook",
                                "Flickr",
                                "Instagram",
                                "Facebook",
                                "Flickr",
                        };
                        cash = new String[]{
                                "200",
                                "100",
                                "300",
                                "100",
                                "200",
                                "300",
                                "100",
                                "200",
                        };
                        imageId = new int[]{
                                R.drawable.ic_launcher_background,
                                R.drawable.ic_launcher_background,
                                R.drawable.ic_launcher_background,
                                R.drawable.ic_launcher_background,
                                R.drawable.ic_launcher_background,
                                R.drawable.ic_launcher_background,
                                R.drawable.ic_launcher_background,
                                R.drawable.ic_launcher_background,
                        };

                        gv = rootView. findViewById(R.id.gv);

                        GridAdapter adapter = new GridAdapter(getActivity(),web,imageId,cash);

                        // Data bind GridView with ArrayAdapter (String Array elements)
                        gv.setAdapter(adapter);

                    /*    gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view,
                                                    int position, long id) {
                                Log.e("wwwwwwwwwwwwwwwwww",""+web.toString());
                                Toast.makeText(getActivity(), "You Clicked at " + web[position], Toast.LENGTH_SHORT).show();
                                Toast.makeText(getActivity(),  ""+cash[position], Toast.LENGTH_SHORT).show();
                                Toast.makeText(getActivity(),  ""+imageId[position], Toast.LENGTH_SHORT).show();
                            }
                        });*/

                    } else {

                        web = new String[]{ };
                        cash = new String[]{ };
                        imageId = new int[]{ };

                        gv = rootView. findViewById(R.id.gv);

                        GridAdapter adapter = new GridAdapter(getActivity(),web,imageId,cash);

                        // Data bind GridView with ArrayAdapter (String Array elements)
                        gv.setAdapter(adapter);

                        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view,
                                                    int position, long id) {
                                Toast.makeText(getActivity(), "You Clicked at " + position, Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                }

                @Override
                public void onPageSelected(int position) {



                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });


            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private final List<TabDetails> tabs = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return tabs.get(position).getFragment();
        }

        @Override
        public int getCount() {

            return tabs.size();
        }

        private void addFragment(TabDetails tab) {

            tabs.add(tab);
        }

        @Override
        public CharSequence getPageTitle(int position) {


           //   Log.e("ddddddddddd",""+tabs.get(position).getTabName());
            return tabs.get(position).getTabName();


        }


    }



}
