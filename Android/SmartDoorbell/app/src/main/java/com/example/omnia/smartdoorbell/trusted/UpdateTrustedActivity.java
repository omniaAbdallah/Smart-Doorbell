package com.example.omnia.smartdoorbell.trusted;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.omnia.smartdoorbell.R;
import com.example.omnia.smartdoorbell.models.VolleyMultipartRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UpdateTrustedActivity extends AppCompatActivity {
EditText edit_name,edit_relation;
ImageView edit_image;
    String id,ip,url1,image;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_trusted);


        edit_name=(EditText) findViewById(R.id.trusted_edit_name);
        edit_relation=(EditText) findViewById(R.id.trusted_edit_relation);
        edit_image=(ImageView) findViewById(R.id.Trusted_imageView);
//
//
        Intent intent = getIntent();
        id=intent.getStringExtra("id");
        edit_name.setText(intent.getStringExtra("name"));
        edit_relation.setText(intent.getStringExtra("relation"));
        image=intent.getStringExtra("image");

        SharedPreferences sharedPreferences = getSharedPreferences("sharedfile",MODE_PRIVATE);
        ip = sharedPreferences.getString("ipServer", "nulllllllll");
        String path="http://"+ip+"/"+image.trim();
        Log.e("ip from detail : ",path);

        Picasso.with(this).load(path).into(edit_image);
        bitmap=((BitmapDrawable)edit_image.getDrawable()).getBitmap();

//

    }

    public void save(View view) {
String name ,relation ,imag,idd;
name=edit_name.getText().toString();
relation=edit_relation.getText().toString();
idd=id;

update(bitmap,name,relation,idd);




    }

    public void edit_image(View view) {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 100);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {

            //getting the image Uri
            Uri imageUri = data.getData();
            try {
                //getting bitmap object from uri
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

                //displaying selected image to imageview
                 edit_image.setImageBitmap(bitmap);

                //calling the method uploadBitmap to upload image
                // uploadBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }


    public void update(final Bitmap bitmap, final String nam, final String relation, final String id) {
        RequestQueue queue = Volley.newRequestQueue(this);
        // Start the queue
        queue.start();
        System.out.println("Start the queue");
//        Intent intent = getIntent();
//        ip = intent.getStringExtra("ip");
        url1 = "http://" + ip + "/uptate_trusted".trim();
        System.out.println(" url :" + url1);
        Log.e(" url log ", url1 + "");
        //  url1="http://192.168.43.115:5005/insert_trusted";
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url1,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject object = new JSONObject(new String(response.data));
                            if (object.getString("state").equals("ok")) {
                                Toast.makeText(UpdateTrustedActivity.this, "success", Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(getBaseContext(),ShowTrustedActivity.class);
                                intent.putExtra("ip",ip);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(" error :" + error);
                Log.e(" error ", error + "");
                Toast.makeText(UpdateTrustedActivity.this, "no internetConnection" + error, Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("name", nam);
                params.put("relation", relation);
                params.put("id", id);
                params.put("filename", getFileDataFromDrawable(bitmap).toString());
                return params;
            }

            @Override
            protected Map<String, VolleyMultipartRequest.DataPart> getByteData() {
                Map<String, VolleyMultipartRequest.DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
               // String imagename = img;
                params.put("pic", new VolleyMultipartRequest.DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }


        };
//
        // Adding request to request queue
       queue.add(volleyMultipartRequest);

    }

}
