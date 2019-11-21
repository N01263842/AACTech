/*
 * Team-Name: AAC-Tech

 */
package aac_tech.automotiveui;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class optionsNavigation extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_navigation);
        BottomNavigationView navView = findViewById(R.id.nav_view);

      //  View child = getLayoutInflater().inflate(R.layout.navigation,null);




        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
       // AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
       //         R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
         //       .build();
         AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                 R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
              .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
       NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

     //  Toolbar toolbar = findViewById(R.id.toolbar);
      //  setSupportActionBar(toolbar);

      /*  ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);*/



        mDrawerLayout = findViewById(R.id.drawer_layout);

        /*NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();


                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here
                        //display in short period of time
                        //  Toast.makeText(getApplicationContext(), menuItem.getTitle(),
                        //        Toast.LENGTH_LONG).show();

                        return true;
                    }
                });


        mDrawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );*/


    }

 /*   @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }*/



}
