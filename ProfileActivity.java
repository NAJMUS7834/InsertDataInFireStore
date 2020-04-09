package getways.solution.authenticationapp;

import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.material.textfield.TextInputEditText;

public class ProfileActivity extends AppCompatActivity {

    //define views
        CircleImageView circleImageView;
        TextInputEditText displayNameEditText;
        Button updateProfileButton;
        ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //finding Views with id
        circleImageView = findViewById(R.id.circleImageView);
        displayNameEditText = findViewById(R.id.displayNameEditText);
        updateProfileButton = findViewById(R.id.updateProfileButton);
        progressBar = findViewById(R.id.progressBar);
    }

    public void updateProfile(View view) {
    }
}
