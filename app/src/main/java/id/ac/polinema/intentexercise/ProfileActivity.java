package id.ac.polinema.intentexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private CircleImageView imageProfile;
    private TextView labelAbout, labelFullname, labelEmail, labelHomepage;
    private Button buttonHomepage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        imageProfile = findViewById(R.id.image_profile);
        labelAbout = findViewById(R.id.label_about);
        labelFullname = findViewById(R.id.label_fullname);
        labelEmail = findViewById(R.id.label_email);
        labelHomepage = findViewById(R.id.label_homepage);
        buttonHomepage = findViewById(R.id.button_homepage);

        String about = getIntent().getExtras().getString("about");
        String fullname = getIntent().getExtras().getString("fullname");
        String email = getIntent().getExtras().getString("email");
        final String homepage = getIntent().getExtras().getString("homepage");

        Uri uri = Uri.parse(getIntent().getExtras().getString("image"));
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            imageProfile.setImageBitmap(bitmap);
        } catch (IOException e) {
            Toast.makeText(this, "Failed load images", Toast.LENGTH_SHORT).show();
        }

        labelAbout.setText(about);
        labelFullname.setText(fullname);
        labelEmail.setText(email);
        labelHomepage.setText(homepage);

        buttonHomepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW);
                webIntent.setData(Uri.parse(homepage));
                startActivity(webIntent);
            }
        });

    }
}
