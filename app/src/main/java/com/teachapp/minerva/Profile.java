package com.teachapp.minerva;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class Profile extends ActionBarActivity {
ImageView picture;
TextView textview;
Button Submit,Cancel;
EditText address1,address2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

       picture=(ImageView)findViewById(R.id.profilepic);
        textview=(TextView)findViewById(R.id.textView);
        address1=(EditText)findViewById(R.id.editText);
        address2=(EditText)findViewById(R.id.address2);
        Submit=(Button)findViewById(R.id.btSubmit);
        Cancel=(Button)findViewById(R.id.btCancel);


        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        Submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
               SharedData.writeString(getApplicationContext(),
                       SharedData.ADDRESS1, address1.getText().toString());
                SharedData.writeString(getApplicationContext(),
                        SharedData.ADDRESS2, address2.getText().toString());
               /* SharedData.writeString(getApplicationContext(),
                        SharedData.EMAIL,email.getText().toString());
                SharedData.writeString(getApplicationContext(),
                        SharedData.PASSWORD,password.getText().toString());*/
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);

            }});

        Cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
               /* SharedData.writeString(getApplicationContext(),
                        SharedData.FNAME,fname.getText().toString());
                SharedData.writeString(getApplicationContext(),
                        SharedData.LNAME,lname.getText().toString());
                SharedData.writeString(getApplicationContext(),
                        SharedData.EMAIL,email.getText().toString());
                SharedData.writeString(getApplicationContext(),
                        SharedData.PASSWORD,password.getText().toString());*/
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);

            }
        });
    }


    private void selectImage() {

        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);

                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);

                    picture.setImageBitmap(bitmap);

                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {

                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                Log.w("path of image from gallery......******************.........", picturePath + "");
                picture.setImageBitmap(thumbnail);
            }
        }
    }

}





