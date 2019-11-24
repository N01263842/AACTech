/*
 * Team-Name: AAC-Tech

 */
package aac_tech.automotiveui.ui.notifications;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.vidyo.VidyoClient.Connector.Connector;
import com.vidyo.VidyoClient.Connector.ConnectorPkg;

import aac_tech.automotiveui.R;

public class NotificationsFragment extends Fragment implements Connector.IConnect {

    private NotificationsViewModel notificationsViewModel;
    private Connector vc;
    private FrameLayout videoFrame;
    private Connector.IConnect ic;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private static final int MY_INTERNET_REQUEST_CODE = 200;
    private static final int MY_AUDIO_REQUEST_CODE = 300;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        ConnectorPkg.setApplicationUIContext(getActivity());
        ConnectorPkg.initialize();
        videoFrame = (FrameLayout)root.findViewById((R.id.videoFrame));

        if (ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        }

        if (ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.INTERNET}, MY_INTERNET_REQUEST_CODE);
        }

        if (ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, MY_AUDIO_REQUEST_CODE);
        }
      //  final TextView textView = root.findViewById(R.id.text_notifications);

       /* notificationsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return root;
    }

    public void Start(View v){



        vc = new Connector(videoFrame, Connector.ConnectorViewStyle.VIDYO_CONNECTORVIEWSTYLE_Default,
                15, "","",0);
        vc.showViewAt(videoFrame,0,0,videoFrame.getWidth(),videoFrame.getHeight());



    }

    public void Connect(View v){

        String token = "cHJvdmlzaW9uAHVzZXIxQGI4YzcwMS52aWR5by5pbwA2MzczOTY4NzEyOAAAZTBjYTEyODcyZTJhYTQ1MTI1YjQ2YThmMGRhZGU5ODMxNjAwOTAyZGQ2ZGJiZDYwOGE4YTY5MzZkZGQ0MWUzZjE3ODI1Zjk5YmQ0ZDFiNGE5YjJlNWI0YzRkOWU0YWY4";
        vc.connect("prod.vidyo.io",token,"DemoUser","DemoRoom", (Connector.IConnect) getActivity());
    }

    public void Disconnect(View v){
        vc.disconnect();
    }

    public void onSuccess(){}

    public void onFailure(Connector.ConnectorFailReason reason){}

    public void onDisconnected(Connector.ConnectorDisconnectReason reason){}


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "camera permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == MY_INTERNET_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "camera permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == MY_AUDIO_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "camera permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }

    }
}