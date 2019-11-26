/*
 * Team-Name: AAC-Tech

 */
package aac_tech.automotiveui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.vidyo.VidyoClient.Connector.ConnectorPkg;
import com.vidyo.VidyoClient.Connector.Connector;
import com.vidyo.VidyoClient.Connector.Connector.IConnect;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

public class videoChat_Activity extends AppCompatActivity implements Connector.IConnect {
    private Connector vc;
    private FrameLayout videoFrame;
    private IConnect ic;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private static final int MY_INTERNET_REQUEST_CODE = 200;
    private static final int MY_AUDIO_REQUEST_CODE = 300;
    private DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_layout);

        ConnectorPkg.setApplicationUIContext(this);
        ConnectorPkg.initialize();
        videoFrame = (FrameLayout)findViewById((R.id.videoFrame));

        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        }

        if (checkSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.INTERNET}, MY_INTERNET_REQUEST_CODE);
        }

        if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, MY_AUDIO_REQUEST_CODE);
        }

       // Toolbar toolbar = findViewById(R.id.toolbar);
      //  setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);





        mDrawerLayout = findViewById(R.id.drawer_layout);

         NavigationView navigationView = findViewById(R.id.nav_view);
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
                          Toast.makeText(getApplicationContext(), menuItem.getTitle(),
                                Toast.LENGTH_LONG).show();


                        if(menuItem.getTitle().equals("Go to Maps")){
                            Intent intent = new Intent(videoChat_Activity.this,MapsActivity.class);
                            startActivity(intent);
                        }

                        if(menuItem.getTitle().equals("Send Your Info")){
                            Intent intent = new Intent(videoChat_Activity.this,Client_Address_Info.class);
                            startActivity(intent);
                        }

                        if(menuItem.getTitle().equals("Update Your Info")){
                            Intent intent = new Intent(videoChat_Activity.this,Client_Address_Info.class);
                            startActivity(intent);
                        }

                        if(menuItem.getTitle().equals("Terms and Conditionds")){
                            Intent intent = new Intent(videoChat_Activity.this,policy.class);
                            startActivity(intent);
                        }





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
        );






    }



    public void Start(View v){



        vc = new Connector(videoFrame, Connector.ConnectorViewStyle.VIDYO_CONNECTORVIEWSTYLE_Default,
                15, "","",0);
        vc.showViewAt(videoFrame,0,0,videoFrame.getWidth(),videoFrame.getHeight());



    }

    public void Connect(View v){

        String token = "cHJvdmlzaW9uAHVzZXIxQGI4YzcwMS52aWR5by5pbwA2MzczOTY4NzEyOAAAZTBjYTEyODcyZTJhYTQ1MTI1YjQ2YThmMGRhZGU5ODMxNjAwOTAyZGQ2ZGJiZDYwOGE4YTY5MzZkZGQ0MWUzZjE3ODI1Zjk5YmQ0ZDFiNGE5YjJlNWI0YzRkOWU0YWY4";
        vc.connect("prod.vidyo.io",token,"DemoUser","DemoRoom",this);
    }

    public void Disconnect(View v){
        vc.disconnect();
    }

    public void onSuccess(){}

    public void onFailure(Connector.ConnectorFailReason reason){}

    public void onDisconnected(Connector.ConnectorDisconnectReason reason){}

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == MY_INTERNET_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == MY_AUDIO_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }

    }

       @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
