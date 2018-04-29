package com.example.omnia.smartdoorbell.block;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
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

public class InsertBlockActivity extends AppCompatActivity {
    String url1, ip,image;
    TextView namee;
    Bitmap bitmap;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_block);
        namee = (TextView) findViewById(R.id.block_insert_name);
        imageView=(ImageView)findViewById(R.id.block_imageView_insert);

        Intent intent = getIntent();

        if (intent.getStringExtra("image") .equals("null")){
        }else{
            image=intent.getStringExtra("image");
            ip=intent.getStringExtra("ip");
            String path="http://"+ip+"/"+image.trim();
            Log.e("ip from detail : ",path);

            Picasso.with(this).load(path).into(imageView);
            bitmap=((BitmapDrawable)imageView.getDrawable()).getBitmap();
        }




    }

    public void insert(final Bitmap bitmap, final String nam) {
        RequestQueue queue = Volley.newRequestQueue(this);
        // Start the queue
        queue.start();
        System.out.println("Start the queue");
        Intent intent = getIntent();
        ip = intent.getStringExtra("ip");
        url1 = "http://" + ip + "/insert_block".trim();
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
                                Toast.makeText(InsertBlockActivity.this, "success", Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(getBaseContext(),ShowListBlockActivity.class);
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
                Toast.makeText(InsertBlockActivity.this, "no internetConnection" + error, Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("name", nam);
                params.put("filename", getFileDataFromDrawable(bitmap).toString());
                return params;
            }

            @Override
            protected Map<String, VolleyMultipartRequest.DataPart> getByteData() {
                Map<String, VolleyMultipartRequest.DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                //String imagename = img;
                params.put("pic", new VolleyMultipartRequest.DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }


        };
        volleyMultipartRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {

                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        // Adding request to request queue
        queue.add(volleyMultipartRequest);

    }

    public void insert_block(View view) {

        String name = namee.getText().toString();
        insert(bitmap, name);


    }

    public void choose(View view) {
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
                imageView.setImageBitmap(bitmap);

                //calling the method uploadBitmap to upload image
                // uploadBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
    * The method is taking Bitmap as an argument
    * then it will return the byte[] array for the given bitmap
    * and we will send this array to the server
    * here we are using PNG Compression with 80% quality
    * you can give quality between 0 to 100
    * 0 means worse quality
    * 100 means best quality
    * */
    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}



