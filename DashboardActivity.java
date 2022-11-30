package com.example.collegeanyware;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class DashboardActivity extends AppCompatActivity {

    TextView fullName;
    private ImageView btnSignOut;
    private GoogleSignInClient mGoogleSignInClient;
    SharedPreferences loggedAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        fullName = (TextView) findViewById(R.id.fullName);


        loggedAccount = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = loggedAccount.edit();
        String name = loggedAccount.getString("name", "Failed");

        fullName.setText(name);

        btnSignOut = (ImageView) findViewById(R.id.settings);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoogleSignInClient.signOut();
                Toast.makeText(DashboardActivity.this, "Logging out", Toast.LENGTH_SHORT).show();
                btnSignOut.setVisibility(View.INVISIBLE);
                editor.putBoolean("status", false);
                editor.apply();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

    }
}
