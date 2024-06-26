package com.example.poppylove;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class UpdateUserActivity extends AppCompatActivity {

    EditText nameInput;
    EditText bioInput;
    String Name;
    String Bio;
    Button submitButton;

    ImageController imageController;
    ImageView imageView;
    Uri uri;

    FloatingActionButton button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);


        button = findViewById(R.id.floatingActionButton);
        imageView = findViewById(R.id.profileID);
        imageController = new ImageController(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(UpdateUserActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });

        Log.d("STARTED", "Started UpdateUser");

        Bundle bundle = getIntent().getExtras();
        String phoneID = bundle.getString("PhoneID");


        nameInput = findViewById(R.id.NameInput);
        bioInput = findViewById(R.id.bioInput);
        submitButton = findViewById(R.id.SubmitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Name = nameInput.getText().toString();
                Bio = bioInput.getText().toString();

                if(Name.isEmpty()){
                    return;
                }

                if(Bio.isEmpty()){
                    return;
                }

                if(uri.equals(null)){
                    return;
                }
                FirebaseController.CreateProfile(phoneID, Name, Bio);
                imageController.uploadProfileImage(phoneID,uri);

                Bundle bundle = new Bundle();
                bundle.putString("PhoneID",phoneID);

                Intent intent = new Intent(UpdateUserActivity.this,UpdateDogActivity.class);
                intent.putExtras(bundle);

                startActivity(intent);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uri = data.getData();
        imageView.setImageURI(uri);
    }
}