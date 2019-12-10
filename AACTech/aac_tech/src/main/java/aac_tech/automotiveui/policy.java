/*
 * Team-Name: AAC-Tech

 */

package aac_tech.automotiveui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class policy extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy);

        textView = (TextView)findViewById(R.id.textView);

        String policy = new String();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        policy = "Terms and Conditions (\"Terms\")\n" +
                "\n" +
                "\n" +
                "Last updated: December 10, 2019\n" +
                "\n" +
                "\n" +
                "Please read these Terms and Conditions (\"Terms\", \"Terms and Conditions\") carefully before using the Empty website (the \"Service\") operated by Empty (\"us\", \"we\", or \"our\").\n" +
                "\n" +
                "Your access to and use of the Service is conditioned on your acceptance of and compliance with these Terms. These Terms apply to all visitors, users and others who access or use the Service.\n" +
                "\n" +
                "By accessing or using the Service you agree to be bound by these Terms. If you disagree with any part of the terms then you may not access the Service. \n" +
                "\n" +
                "\n" +
                "Termination\n" +
                "\n" +
                "We may terminate or suspend access to our Service immediately, without prior notice or liability, for any reason whatsoever, including without limitation if you breach the Terms.\n" +
                "\n" +
                "All provisions of the Terms which by their nature should survive termination shall survive termination, including, without limitation, ownership provisions, warranty disclaimers, indemnity and limitations of liability.\n" +
                "\n" +
                "\n" +
                "We reserve the right, at our sole discretion, to modify or replace these Terms at any time. If a revision is a material we will try to provide at least 15 days' notice prior to any new terms taking effect. What constitutes a material change will be determined at our sole discretion.\n" +
                "\n" +
                "By continuing to access or use our Service after those revisions become effective, you agree to be bound by the revised terms. If you do not agree to the new terms, please stop using the Service.\n" +
                "\n" +
                "\n" +
                "Contact Us\n" +
                "\n" +
                "If you have any questions about these Terms, please contact us.\n" +
                "ACC-teh@humber.ca";

        textView.setText(policy );


    }
}
