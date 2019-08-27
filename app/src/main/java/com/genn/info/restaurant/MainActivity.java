package com.genn.info.restaurant;

import android.annotation.SuppressLint;
import android.content.Intent;
import androidx.annotation.NonNull;

import com.genn.info.restaurant.Activity.Bussinessmaster;
import com.genn.info.restaurant.Activity.Customer;
import com.genn.info.restaurant.Activity.CustomerMaster;
import com.genn.info.restaurant.Activity.Department;
import com.genn.info.restaurant.Activity.Designation;
import com.genn.info.restaurant.Activity.Empcategory;
import com.genn.info.restaurant.Activity.Employee;
import com.genn.info.restaurant.Activity.Finyear;
import com.genn.info.restaurant.Activity.Ingredient;
import com.genn.info.restaurant.Activity.IngredientMaster;
import com.genn.info.restaurant.Activity.ItemCategory;
import com.genn.info.restaurant.Activity.ItemSubcatg;
import com.genn.info.restaurant.Activity.Ledger;
import com.genn.info.restaurant.Activity.Ledgergroup;
import com.genn.info.restaurant.Activity.Ledgersubgrp;
import com.genn.info.restaurant.Activity.Login;
import com.genn.info.restaurant.Activity.OrderType;
import com.genn.info.restaurant.Activity.PayTerm;
import com.genn.info.restaurant.Activity.Payment;
import com.genn.info.restaurant.Activity.Permission_Template;
import com.genn.info.restaurant.Activity.Plantmaster;
import com.genn.info.restaurant.Activity.PoCancelReson;
import com.genn.info.restaurant.Activity.Recipe;
import com.genn.info.restaurant.Activity.Report;
import com.genn.info.restaurant.Activity.SettingsActivity;
import com.genn.info.restaurant.Activity.Store;
import com.genn.info.restaurant.Activity.Subdepartment;
import com.genn.info.restaurant.Activity.Templatemaster;
import com.genn.info.restaurant.Activity.Templatemodulemaster;
import com.genn.info.restaurant.Activity.Terms;
import com.genn.info.restaurant.Activity.Uom;
import com.genn.info.restaurant.Activity.User;
import com.genn.info.restaurant.Activity.Usergroup;
import com.genn.info.restaurant.Activity.Varientmaster;
import com.genn.info.restaurant.Activity.Vendor;
import com.genn.info.restaurant.Activity.WasteType;
import com.genn.info.restaurant.Activity.Workdivision;
import com.genn.info.restaurant.Connection.DBHelper;
import com.genn.info.restaurant.Model.Pocancelmodel;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.viewpager.widget.ViewPager;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.genn.info.restaurant.Activity.Admin;
import com.genn.info.restaurant.Activity.Fooditem_search;
import com.genn.info.restaurant.Activity.Product;
import com.genn.info.restaurant.Activity.Userview;
import com.genn.info.restaurant.Fragment.Orderlist;
import com.genn.info.restaurant.Fragment.Product_fragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
Toolbar toolbar;

    private DrawerLayout mdrawerlayout;
    private ActionBarDrawerToggle mToggle;

    LinearLayout salary_details_linear,salary_detail_Linearlabel;
    Animation bounce,re_bounce;
    boolean a=true;
//tabview
    private TabLayout tabLayout;
    private ViewPager viewPager;
Button pay;
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper=new DBHelper(this);
        mdrawerlayout=(DrawerLayout)findViewById(R.id.drawerlayout);
        mToggle=new ActionBarDrawerToggle(this,mdrawerlayout,R.string.open,R.string.close);
        mdrawerlayout.addDrawerListener(mToggle);
        mToggle.syncState();

       getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        NavigationView navigationView=findViewById(R.id.nav_view);

// visible

      /*  Menu menuNav=navigationView.getMenu();
        MenuItem nav_item2 = menuNav.findItem(R.id.nav_signout);
        nav_item2.setVisible(false);*/



        pay=findViewById(R.id.pay);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this, Payment.class);
                startActivity(i);
            }
        });


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int id = menuItem.getItemId();

                //creating fragment object
                Fragment fragment = null;

                if (id == R.id.nav_profile) {

                    startActivity(new Intent(MainActivity.this, Admin.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                } else if (id == R.id.nav_dashboard) {


                    startActivity(new Intent(MainActivity.this, Userview.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.product) {


                    startActivity(new Intent(MainActivity.this, Product.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.nav_settings) {


                    startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.nav_policy) {


                    startActivity(new Intent(MainActivity.this, Customer.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.nav_earningDeatils) {


                    startActivity(new Intent(MainActivity.this, Ingredient.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }
                else if (id == R.id.nav_claims) {
                    startActivity(new Intent(MainActivity.this, Report.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/
                }
                else if (id == R.id.nav_signout) {
                    dbHelper.deleteAll();
                    startActivity(new Intent(MainActivity.this, Login.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
                else if (id == R.id.bussiness) {
                    startActivity(new Intent(MainActivity.this, Bussinessmaster.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
                else if (id == R.id.plant) {
                    startActivity(new Intent(MainActivity.this, Plantmaster.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
                else if (id == R.id.uom) {
                    startActivity(new Intent(MainActivity.this, Uom.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
                else if (id == R.id.Varient) {
                    startActivity(new Intent(MainActivity.this, Varientmaster.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
                else if (id == R.id.usergroup) {
                    startActivity(new Intent(MainActivity.this, Usergroup.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
                else if (id == R.id.user) {
                    startActivity(new Intent(MainActivity.this, User.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
                else if (id == R.id.template) {
                    startActivity(new Intent(MainActivity.this, Templatemaster.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
                else if (id == R.id.templatemodule) {
                    startActivity(new Intent(MainActivity.this, Templatemodulemaster.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
                else if (id == R.id.permissiontemplate) {
                    startActivity(new Intent(MainActivity.this, Permission_Template.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
                else if (id == R.id.store) {
                    startActivity(new Intent(MainActivity.this, Store.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
                else if (id == R.id.department) {
                    startActivity(new Intent(MainActivity.this, Department.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
              else if (id == R.id.design) {
                    startActivity(new Intent(MainActivity.this, Designation.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
              else if (id == R.id.empcategory) {
                    startActivity(new Intent(MainActivity.this, Empcategory.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
              else if (id == R.id.subdep) {
                    startActivity(new Intent(MainActivity.this, Subdepartment.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
              else if (id == R.id.workdiv) {
                    startActivity(new Intent(MainActivity.this, Workdivision.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
              else if (id == R.id.Employeee) {
                    startActivity(new Intent(MainActivity.this, Employee.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
              else if (id == R.id.ledgergroup) {
                    startActivity(new Intent(MainActivity.this, Ledgergroup.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
              else if (id == R.id.ledgerdubgroup) {
                    startActivity(new Intent(MainActivity.this, Ledgersubgrp.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
              else if (id == R.id.ledger) {
                    startActivity(new Intent(MainActivity.this, Ledger.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
              else if (id == R.id.finyear) {
                    startActivity(new Intent(MainActivity.this, Finyear.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
              else if (id == R.id.itemcatgory) {
                    startActivity(new Intent(MainActivity.this, ItemCategory .class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
              else if (id == R.id.itemsubcategory) {
                    startActivity(new Intent(MainActivity.this, ItemSubcatg.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
              else if (id == R.id.recipe) {
                    startActivity(new Intent(MainActivity.this, Recipe.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
              else if (id == R.id.terms) {
                    startActivity(new Intent(MainActivity.this, Terms.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
              else if (id == R.id.payterms) {
                    startActivity(new Intent(MainActivity.this, PayTerm.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
              else if (id == R.id.ordertype) {
                    startActivity(new Intent(MainActivity.this, OrderType.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
             else if (id == R.id.pocancel) {
                    startActivity(new Intent(MainActivity.this, PoCancelReson.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
             else if (id == R.id.wastetype) {
                    startActivity(new Intent(MainActivity.this, WasteType.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
             else if (id == R.id.customer) {
                    startActivity(new Intent(MainActivity.this, CustomerMaster.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
              else if (id == R.id.vendor) {
                    startActivity(new Intent(MainActivity.this, Vendor.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
    else if (id == R.id.Ingredient) {
                    startActivity(new Intent(MainActivity.this, IngredientMaster.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }



                //replacing the fragment
                if (fragment != null) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.contain_frame, fragment);
                    ft.commit();
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerlayout);
                drawer.closeDrawer(GravityCompat.START);
                return false;
            }
        });

//        ActionBar actionBar =getSupportActionBar();
/*
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.na);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
*/







        createLayoutDynamically(10);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        salary_details_linear=findViewById(R.id.salary_details_linear);
        salary_detail_Linearlabel=findViewById(R.id.salary_detail_Linearlabel);

        bounce= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce);
        re_bounce=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.revese_bounce);

        salary_detail_Linearlabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (a){
                    salary_details_linear.setVisibility(View.VISIBLE);
                    salary_details_linear.setAnimation(bounce);

                    a=false;
                }else {
                    salary_details_linear.setVisibility(View.GONE);
                    salary_details_linear.setAnimation(re_bounce);

                    a=true;
                }

            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true ;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }

        //noinspection SimplifiableIfStatement

        if (id == R.id.menu_search) {

            Intent intent = new Intent(this,Fooditem_search.class);
            this.startActivity(intent);
            return true;
        }

        if (id == R.id.menu_printer)  {
            Toast.makeText(this, "Android Menu is Clicked", Toast.LENGTH_LONG).show();
            return true;
        }

        if (id == R.id.action_settings) {
            Toast.makeText(this, "Android Menu is Clicked", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Product_fragment(), "PRODUCTS");
        adapter.addFragment(new Orderlist(), "ORDERLIST");

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

    @SuppressLint("ResourceAsColor")
    private void createLayoutDynamically(int n) {

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(10, 0, 0, 0);

        for (int i = 0; i < n; i++) {
            final Button myButton = new Button(this);
            myButton.setText("Tab "+"\n"+(i+1));
//            myButton.setBackgroundResource(R.drawable.family_time);
            myButton.setLayoutParams(params);
            myButton.setId(i);
            final int id_ = myButton.getId();
            LinearLayout layout=findViewById(R.id.dynamicLinear);
            layout.addView(myButton);
            myButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Toast.makeText(MainActivity.this, "Page = " + (id_+1), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }



}
