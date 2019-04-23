package com.example.android.gameconsign;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.gameconsign.model.Upload;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class MenuPenjualActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnChoose, btnSave;
    ImageView imageView;
    EditText imageNameEt;
    ProgressBar progressBar;
    Uri imageUri;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    StorageTask uploadTask;

    private static final int IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_penjual);

        databaseReference = FirebaseDatabase.getInstance().getReference("Upload");
        storageReference = FirebaseStorage.getInstance().getReference("Upload");

        btnChoose = findViewById(R.id.choose);
        btnSave = findViewById(R.id.save);
        progressBar = findViewById(R.id.progressbar);
        imageView = findViewById(R.id.image);
        imageNameEt = findViewById(R.id.name);

        btnChoose.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){

            case R.id.choose :
                openFileChooser();
                break;

            case R.id.save:
                if(uploadTask!=null && uploadTask.isInProgress())
                {
                    Toast.makeText(getApplicationContext(),"Upload in progress", Toast.LENGTH_SHORT).show();;
                }else {
                    saveData();
                }
                break;

        }
    }

    void openFileChooser()
    {
        Intent a = new Intent();
        a.setType("image/*");
        a.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(a,IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode,data);

        if(requestCode==IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            imageUri = data.getData();
            Picasso.with(this).load(imageUri).into(imageView);
        }
    }

    public String getFileExtension (Uri imageUri)
    {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imageUri));
    }

    private void saveData()
    {
        final String imageName = imageNameEt.getText().toString().trim();
        if(imageName.isEmpty()){
            imageNameEt.setError("Masukan nama foto");
            imageNameEt.requestFocus();
            return;
        }

        StorageReference ref = storageReference.child(System.currentTimeMillis()+"."+getFileExtension(imageUri));
        ref.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getApplicationContext(),"berhasil",Toast.LENGTH_SHORT).show();

                Upload upload = new Upload(imageName,taskSnapshot.getStorage().getDownloadUrl().toString());

                String uploadId = databaseReference.push().getKey();

                databaseReference.child(uploadId).setValue(upload);
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"gagal",Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
