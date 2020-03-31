/*
 * Team-Name: AAC-Tech

 */
package aac_tech.automotiveui;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.opentok.android.Session;
import com.opentok.android.Stream;
import com.opentok.android.Publisher;
import com.opentok.android.PublisherKit;
import com.opentok.android.Subscriber;
import com.opentok.android.OpentokError;
import androidx.annotation.NonNull;
import android.Manifest;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import android.widget.FrameLayout;
import android.opengl.GLSurfaceView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ParaVideoChat extends AppCompatActivity
        implements Session.SessionListener,PublisherKit.PublisherListener {
    private static String API_KEY = "46476002";
    private static String SESSION_ID = "1_MX40NjQ3NjAwMn5-MTU4MDc4MTYyMjU3Nn5YbzZvV2pmRDBpZ3c1MS9uNmVyOGRRZFN-fg";
    private static String TOKEN = "T1==cGFydG5lcl9pZD00NjQ3NjAwMiZzaWc9MmM4MGFjOWM5MTdlOTE4M2YyMTNlMTcwZWI1OTJkZDQ3OWIxNzkwNjpzZXNzaW9uX2lkPTFfTVg0ME5qUTNOakF3TW41LU1UVTRNRGM0TVRZeU1qVTNObjVZYnpadlYycG1SREJwWjNjMU1TOXVObVZ5T0dSUlpGTi1mZyZjcmVhdGVfdGltZT0xNTgwNzgxNjQ2Jm5vbmNlPTAuMDU3ODE4NDEyMzY2ODkyNTYmcm9sZT1wdWJsaXNoZXImZXhwaXJlX3RpbWU9MTU4MDc4NTI0NCZpbml0aWFsX2xheW91dF9jbGFzc19saXN0PQ==";
    private static final String LOG_TAG = videoChat_Activity.class.getSimpleName();
    private static final int RC_SETTINGS_SCREEN_PERM = 123;
    private static final int RC_VIDEO_APP_PERM = 124;
    private Session mSession;
    private FrameLayout mPublisherViewContainer;
    private FrameLayout mSubscriberViewContainer;
    private Publisher mPublisher;
    private Subscriber mSubscriber;
    //boolean subscriberCheck = false;
    private Button disconnectSubscriber;
    private DatabaseReference database;

    private DrawerLayout mDrawerLayout;
    //  Button start, connect, disconnect;
    //   Intent myInt;
    String para_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_para_video_chat);

        database = FirebaseDatabase.getInstance().getReference().child("paramedics");
        //UpdateInfo upd = new UpdateInfo();


        requestPermissions();



    }

    private void disconnectDialog(final ArrayList<String>info){

        AlertDialog.Builder builder = new AlertDialog.Builder(ParaVideoChat.this);
        builder.setMessage("Disconnecting with Patient. Click 'OK' to continue");
        builder.setCancelable(false);
        builder.setPositiveButton(getResources().getString(R.string.OK), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();
                mSession.unsubscribe(mSubscriber); // Disconnects the patient
                mSession.unpublish(mPublisher);
                mPublisherViewContainer.removeAllViews();
                mSubscriberViewContainer.removeAllViews();
                database.child(info.get(5)).child("video").setValue("no");
                Intent intent = new Intent(getApplicationContext(),ParamedMaps.class);
                intent.putStringArrayListExtra("info2", info);

                startActivity(intent);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(RC_VIDEO_APP_PERM)
    private void requestPermissions() {
        String[] perms = { Manifest.permission.INTERNET, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO };
        if (EasyPermissions.hasPermissions(this, perms)) {
            // initialize view objects from your layout
            mPublisherViewContainer = (FrameLayout)findViewById(R.id.publisher_container1);
            mSubscriberViewContainer = (FrameLayout)findViewById(R.id.subscriber_container1);





            // initialize and connect to the session
            // mSession = new Session.Builder(this, API_KEY, SESSION_ID).build();
            // mSession.setSessionListener(this);
            // mSession.connect(TOKEN);
            fetchSessionConnectionData();


        } else {
            EasyPermissions.requestPermissions(this, "This app needs access to your camera and mic to make video calls", RC_VIDEO_APP_PERM, perms);
        }
    }

    // SessionListener methods

    @Override
    public void onConnected(Session session) {
        Log.i(LOG_TAG, "Session Connected");


        mPublisher = new Publisher.Builder(this).build();
        mPublisher.setPublisherListener(this);

        mPublisherViewContainer.addView(mPublisher.getView());

        if (mPublisher.getView() instanceof GLSurfaceView) {
            ((GLSurfaceView) mPublisher.getView()).setZOrderOnTop(true);
        }

        mSession.publish(mPublisher);
    }



    @Override
    public void onDisconnected(Session session) {
        Log.i(LOG_TAG, "Session Disconnected");
    }

    @Override
    public void onStreamReceived(Session session, Stream stream) {
        Log.i(LOG_TAG, "Stream Received");
        //mSubscriberViewContainer.removeAllViews();
      //  disconnectSubscriber.setVisibility(View.VISIBLE);

        if (mSubscriber == null) {
            mSubscriber = new Subscriber.Builder(this, stream).build();
            mSession.subscribe(mSubscriber);
            mSubscriberViewContainer.addView(mSubscriber.getView());
        }

    }

    @Override
    public void onStreamDropped(Session session, Stream stream) {
        Log.i(LOG_TAG, "Stream Dropped");

        if (mSubscriber != null) {
            mSubscriber = null;
            mSubscriberViewContainer.removeAllViews();
            // subscriberCheck = false;

        }

       /* mPublisherViewContainer.removeAllViews();

        if(mPublisherViewContainer.getVisibility() == View.VISIBLE) mPublisherViewContainer.setVisibility(View.GONE);

        mSubscriberViewContainer.addView(mPublisher.getView());

        if (mPublisher.getView() instanceof GLSurfaceView) {
            ((GLSurfaceView) mPublisher.getView()).setZOrderOnTop(true);
        }*/


    }

    @Override
    public void onError(Session session, OpentokError opentokError) {
        Log.e(LOG_TAG, "Session error: " + opentokError.getMessage());
    }

    // PublisherListener methods

    @Override
    public void onStreamCreated(PublisherKit publisherKit, Stream stream) {
        Log.i(LOG_TAG, "Publisher onStreamCreated");
    }

    @Override
    public void onStreamDestroyed(PublisherKit publisherKit, Stream stream) {
        Log.i(LOG_TAG, "Publisher onStreamDestroyed");
    }

    @Override
    public void onError(PublisherKit publisherKit, OpentokError opentokError) {
        Log.e(LOG_TAG, "Publisher error: " + opentokError.getMessage());
    }

    public void fetchSessionConnectionData() {
        RequestQueue reqQueue = Volley.newRequestQueue(this);
        reqQueue.add(new JsonObjectRequest(Request.Method.GET,
                "https://aactech.herokuapp.com" + "/session",
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    API_KEY = response.getString("apiKey");
                    SESSION_ID = response.getString("sessionId");
                    TOKEN = response.getString("token");

                    Log.i(LOG_TAG, "API_KEY: " + API_KEY);
                    Log.i(LOG_TAG, "SESSION_ID: " + SESSION_ID);
                    Log.i(LOG_TAG, "TOKEN: " + TOKEN);

                    mSession = new Session.Builder(ParaVideoChat.this, API_KEY, SESSION_ID).build();
                    mSession.setSessionListener(ParaVideoChat.this);
                    mSession.connect(TOKEN);

                } catch (JSONException error) {
                    Log.e(LOG_TAG, "Web Service error: " + error.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(LOG_TAG, "Web Service error: " + error.getMessage());
            }
        }));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.disconnect3:
                Intent intent = getIntent();
                ArrayList<String> paraInfo = intent.getStringArrayListExtra("par_info");
                disconnectDialog(paraInfo);
                break;

        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.disc_menu, menu);

        return true;
    }



}
