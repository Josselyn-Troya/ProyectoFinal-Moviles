package com.example.socialmedia.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.socialmedia.R;
import com.example.socialmedia.models.Post;
import com.example.socialmedia.providers.AuthProvider;
import com.example.socialmedia.providers.ImageProvider;
import com.example.socialmedia.providers.PostProvider;
import com.example.socialmedia.utils.FileUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;

public class PostActivity extends AppCompatActivity {

    ImageView mImageViewPost1;
    ImageView mImageViewPost2;
    File mImageFile;
    File mImageFile2;
    Button mButtonPost;
    ImageProvider mImageProvider;
    PostProvider mPostProvider;
    AuthProvider mAuthProvider;

    TextInputEditText mTextInputTitle;
    TextInputEditText mTextInputDescription;
    ImageView mImageViewJuegos;
    ImageView mImageViewComida;
    ImageView mImageViewModa;
    ImageView mImageViewTecnologia;
    ImageView mImageViewOtros;
    ImageView mImageViewDeporte;
    ImageView mImageViewAnimales;
    ImageView mImageViewMusica;

    CircleImageView mCircleImageBack;
    String mCategory = "";

    String mTitle="";
    String mDescription="";

    AlertDialog mDialog;
    TextView mTextViewCategory;

    AlertDialog.Builder mBuilderSelector;
    CharSequence options[];
    private final int GALLERY_REQUEST_CODE = 1;
    private final int GALLERY_REQUEST_CODE_2 = 2;

    private final int PHOTO_REQUEST_CODE = 3;
    private final int PHOTO_REQUEST_CODE_2 = 4;


    //foto 1
    String mAbsolutePhotoPath;
    String mPhotoPath;
    File mPhotoFile;

    //foto 2
    String mAbsolutePhotoPath2;
    String mPhotoPath2;
    File mPhotoFile2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        mImageProvider = new ImageProvider();
        mPostProvider = new PostProvider();

        mAuthProvider = new AuthProvider();

        mDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Espere un momento")
                .setCancelable(false)
                .build();

        mBuilderSelector = new AlertDialog.Builder(this);
        mBuilderSelector.setTitle("Selecciona una opción");
        options = new CharSequence[] {"Imagen de galeria", "Tomar una foto"};

        mImageViewPost1 = findViewById(R.id.imageViewPost1);
        mImageViewPost2 = findViewById(R.id.imageViewPost2);
        mButtonPost = findViewById(R.id.btnPost);
        mTextInputTitle = findViewById(R.id.textInputVideoGame);
        mTextInputDescription = findViewById(R.id.textInputDescription);
        mImageViewJuegos = findViewById(R.id.imageViewJuegos);
        mImageViewComida = findViewById(R.id.imageViewComida);
        mImageViewModa = findViewById(R.id.imageViewModa);
        mImageViewTecnologia = findViewById(R.id.imageViewTecnologia);
        mImageViewOtros = findViewById(R.id.imageViewOtros);
        mImageViewDeporte = findViewById(R.id.imageViewDeporte);
        mImageViewAnimales = findViewById(R.id.imageViewAnimales);
        mImageViewMusica = findViewById(R.id.imageViewMusica);
        mTextViewCategory = findViewById(R.id.textViewCategory);
        mCircleImageBack = findViewById(R.id.circleImageBack);


        mCircleImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        mButtonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickPost();
            }
        });


        mImageViewPost1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //selectOptionImage(1);
                openGallery(GALLERY_REQUEST_CODE);
            }
        });
        mImageViewPost2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery(GALLERY_REQUEST_CODE_2);
            }
        });



        mImageViewJuegos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCategory = "Juegos";
                mTextViewCategory.setText(mCategory);
            }
        });

        mImageViewComida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCategory = "Comida";
                mTextViewCategory.setText(mCategory);
            }
        });

        mImageViewModa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCategory = "Moda";
                mTextViewCategory.setText(mCategory);
            }
        });

        mImageViewTecnologia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCategory = "Tecnología";
                mTextViewCategory.setText(mCategory);
            }
        });

        mImageViewOtros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCategory = "Otros";
                mTextViewCategory.setText(mCategory);
            }
        });

        mImageViewDeporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCategory = "Deporte";
                mTextViewCategory.setText(mCategory);
            }
        });

        mImageViewAnimales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCategory = "Animales";
                mTextViewCategory.setText(mCategory);
            }
        });

        mImageViewMusica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCategory = "Musica";
                mTextViewCategory.setText(mCategory);
            }
        });

    }

   /** private void selectOptionImage(final int numberImage) {

        mBuilderSelector.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (i == 0){
                    if(numberImage == 1) {
                        openGallery(GALLERY_REQUEST_CODE);
                    }else if (numberImage == 2){
                        openGallery(GALLERY_REQUEST_CODE_2);
                    }
                }else if (i == 1){
                    if(numberImage == 1) {
                        takePhoto(PHOTO_REQUEST_CODE);
                    }else if (numberImage == 2){
                        takePhoto(PHOTO_REQUEST_CODE_2);
                    }
                }
            }
        });

        mBuilderSelector.show();

    } **/

    private void takePhoto(int requestCode) {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null){
            File photoFile = null;
            try{
                photoFile = createPhotoFile(requestCode);
            }catch (Exception e){
                Toast.makeText(this, "Hubo un error en el archivo" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            if (photoFile != null){
                Uri photoUri = FileProvider.getUriForFile(PostActivity.this, "com.example.socialmedia", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePictureIntent, requestCode);
            }
        }

    }

    private File createPhotoFile(int requestCode) throws IOException {
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File photoFile = File.createTempFile(
                new Date() + "_photo",
                ".jpg",
                storageDir
        );
        if (requestCode == PHOTO_REQUEST_CODE){
            mPhotoPath = "file:" + photoFile.getAbsolutePath();
            mAbsolutePhotoPath = photoFile.getAbsolutePath();
        }else if(requestCode == PHOTO_REQUEST_CODE_2){
            mPhotoPath2 = "file:" + photoFile.getAbsolutePath();
            mAbsolutePhotoPath2 = photoFile.getAbsolutePath();
        }
        return photoFile;
    }


    private void clickPost() {
        mTitle = mTextInputTitle.getText().toString();
        mDescription = mTextInputDescription.getText().toString();

        if(!mTitle.isEmpty() && !mDescription.isEmpty() && !mCategory.isEmpty()){
            //Selecciono ambas imagenes de la galeria
            if(mImageFile !=null && mImageFile2 != null){
                saveImage(mImageFile, mImageFile2);
            }else
                //las dos fotos fueron tomadas desde la camara
                if (mPhotoFile != null && mPhotoFile2 != null){
                    saveImage(mPhotoFile, mPhotoFile2);
                } else
                     if (mImageFile != null && mPhotoFile2 != null){
                            saveImage(mImageFile, mPhotoFile2);
                     }else
                     if (mPhotoFile != null && mImageFile2 != null){
                            saveImage(mPhotoFile, mImageFile2);
                     }
                     else{
                Toast.makeText(this, "Debe seleccionar al menos una imagen", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
        }

    }

    private void saveImage(File imageFile1, File imageFile2) {

        mDialog.show();

        mImageProvider.save(PostActivity.this, imageFile1).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){
                    mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                           final String url = uri.toString();

                            mImageProvider.save(PostActivity.this, imageFile2).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> taskImage2) {
                                    if (taskImage2.isSuccessful()){
                                        mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri2) {
                                                String url2 = uri2.toString();

                                                Post post = new Post();
                                                post.setImage1(url);
                                                post.setImage2(url2);
                                                post.setTitle(mTitle);
                                                post.setDescription(mDescription);
                                                post.setCategory(mCategory);
                                                post.setIdUser(mAuthProvider.getUid());
                                                post.setTimestamp(new Date().getTime());

                                                mPostProvider.save(post).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> taskSave) {
                                                        mDialog.dismiss();
                                                        if(taskSave.isSuccessful()){
                                                            clearForm();
                                                            Toast.makeText(PostActivity.this, "Se almaceno correctamente", Toast.LENGTH_SHORT).show();
                                                        }else{
                                                            Toast.makeText(PostActivity.this, "No se pudo almacenar la información", Toast.LENGTH_SHORT).show();
                                                        }

                                                    }
                                                });


                                            }
                                        });
                                    }else{
                                        mDialog.dismiss();
                                        Toast.makeText(PostActivity.this, "La imagen 2 no se pudo guardar", Toast.LENGTH_SHORT).show();
                                    }
 
                                }
                            });

                        }
                    });


                }else{
                    mDialog.dismiss();
                    Toast.makeText(PostActivity.this, "Error al almacenar la imagen", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void clearForm() {
        mTextInputTitle.setText("");
        mTextInputDescription.setText("");
        mTextViewCategory.setText("");
        mImageViewPost1.setImageResource(R.drawable.camara);
        mImageViewPost2.setImageResource(R.drawable.camara);
        mTitle = "";
        mDescription = "";
        mCategory = "";
        mImageFile = null;
        mImageFile2 = null;

    }

    private void openGallery(int requestCode) {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * SELECCION DE IMAGEN DESDE GALERIA
         **/
        if(requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK){
            try {
                mPhotoFile = null;
                mImageFile = FileUtil.from(this, data.getData());
                mImageViewPost1.setImageBitmap(BitmapFactory.decodeFile(mImageFile.getAbsolutePath()));
            }catch (Exception e){
                Log.d("ERROR", "Se produjo un error" + e.getMessage());
                Toast.makeText(this, "Se produjo un error" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        if(requestCode == GALLERY_REQUEST_CODE_2 && resultCode == RESULT_OK){
            try {
                mPhotoFile2 = null;
                mImageFile2 = FileUtil.from(this, data.getData());
                mImageViewPost2.setImageBitmap(BitmapFactory.decodeFile(mImageFile2.getAbsolutePath()));
            }catch (Exception e){
                Log.d("ERROR", "Se produjo un error" + e.getMessage());
                Toast.makeText(this, "Se produjo un error" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        /**
         * SELECCION DE IMAGEN DESDE LA CAMARA
         **/
        if (requestCode == PHOTO_REQUEST_CODE && resultCode == RESULT_OK) {
            mImageFile = null;
            mPhotoFile = new File(mAbsolutePhotoPath);
            Picasso.with(PostActivity.this).load(mPhotoPath).into(mImageViewPost1);
        }
        /**
         * SELECCION DE IMAGEN DESDE LA CAMARA
         **/
        if (requestCode == PHOTO_REQUEST_CODE_2 && resultCode == RESULT_OK) {
            mImageFile = null;
            mPhotoFile2 = new File(mAbsolutePhotoPath2);
            Picasso.with(PostActivity.this).load(mPhotoPath2).into(mImageViewPost2);
        }

    }
}