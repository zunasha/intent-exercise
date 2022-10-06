package id.ac.polinema.intentexercise;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout layoutFullname, layoutEmail, layoutPassword, layoutConfirmPassword, layoutHomepage, layoutAbout;
    private TextInputEditText textFullname, textEmail, textPassword,textConfirmPassword, textHomepage, textAbout;
    private Button buttonOk;
    private ImageView imageView;
    private boolean change_img = false;
    private Uri imgUri = null;
    private Bitmap bitmap;
    private CircleImageView Avatar, changeAvatar;

    private static final String TAG = RegisterActivity.class.getCanonicalName();
    private static final int GALLERY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        layoutFullname = findViewById(R.id.layout_fullname);
        layoutEmail = findViewById(R.id.layout_email);
        layoutPassword = findViewById(R.id.layout_password);
        layoutConfirmPassword = findViewById(R.id.layout_confirm_password);
        layoutHomepage = findViewById(R.id.layout_homepage);
        layoutAbout = findViewById(R.id.layout_about);

        textFullname = findViewById(R.id.text_fullname);
        textEmail = findViewById(R.id.text_email);
        textPassword = findViewById(R.id.text_password);
        textConfirmPassword = findViewById(R.id.text_confirm_password);
        textHomepage = findViewById(R.id.text_homepage);
        textAbout = findViewById(R.id.text_about);

        buttonOk = findViewById(R.id.button_ok);
        imageView = findViewById(R.id.imageView);

        Avatar = findViewById(R.id.image_profile);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), GALLERY_REQUEST_CODE);
            }
        });

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullname = textFullname.getText().toString();
                String email = textEmail.getText().toString();
                String password = textPassword.getText().toString();
                String confirmpassword = textConfirmPassword.getText().toString();
                String homepage = textHomepage.getText().toString();
                String about = textAbout.getText().toString();

                Intent move = new Intent(RegisterActivity.this, ProfileActivity.class);

                if(!change_img){
                    Toast.makeText(RegisterActivity.this, "Image must be change", Toast.LENGTH_LONG).show();
                }else if(fullname.isEmpty()){
                    textFullname.setError("Fullname Required!");
                }else if(email.isEmpty()){
                    textEmail.setError("Email Required!");
                }else if(password.isEmpty()){
                    textPassword.setError("Password Required!");
                }else if(confirmpassword.isEmpty()) {
                    textConfirmPassword.setError("Confirm Password Required!");
                }else if(homepage.isEmpty()) {
                    textHomepage.setError("Homepage Required!");
                }else if(about.isEmpty()) {
                    textAbout.setError("About Required!");
                }else if (!password.equals(confirmpassword)) {
                    Toast.makeText(RegisterActivity.this, "Confirm password is not correct", Toast.LENGTH_SHORT).show();
                } else {
                    String image = imgUri.toString();
                    move.putExtra("image", image);
                    move.putExtra("fullname", fullname);
                    move.putExtra("email", email);
                    move.putExtra("password", password);
                    move.putExtra("con_password", confirmpassword);
                    move.putExtra("homepage", homepage);
                    move.putExtra("about", about);
                    startActivity(move);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED){
            Toast.makeText(this, "Cancel input image", Toast.LENGTH_SHORT).show();
            return;
        }else{
            if (requestCode == GALLERY_REQUEST_CODE){
                if (data != null){
                    try{
                        change_img = true;
                        imgUri = data.getData();
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imgUri);
                        Avatar.setImageBitmap(bitmap);
                    }catch (IOException e){
                        Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, e.getMessage());
                    }
                }
            }
        }
    }

}
