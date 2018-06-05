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
import android.widget.ImageView;
import android.widget.TextView;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InsertTrustedActivity extends AppCompatActivity {
    String url1, ip, image;
    TextView namee, img, relation;
    Bitmap bitmap;
    ImageView imageView;
    List bitmaplist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_trusted);


        namee = (TextView) findViewById(R.id.name);
        // img = (TextView) findViewById(R.id.Trusted_imageView_insert);
        relation = (TextView) findViewById(R.id.relation);
        imageView = (ImageView) findViewById(R.id.Trusted_imageView_insert);
        SharedPreferences sharedPreferences = getSharedPreferences("sharedfile", MODE_PRIVATE);
        ip = sharedPreferences.getString("ipServer", "nulllllllll");
        Intent intent = getIntent();


        if (intent.getStringExtra("image").equals("null")) {
        }
//insert from  activity history  if has image
        else {
            image = intent.getStringExtra("image");

            String path = "http://" + ip + "/" + image.trim();
            Log.e("ip from detail : ", path);

            Picasso.with(this).load(path).into(imageView);
            bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        }

    }

    public void insert(final Bitmap bitmap, final String nam, final String relation) {
        RequestQueue queue = Volley.newRequestQueue(this);
        // Start the queue
        queue.start();
        System.out.println("Start the queue");
        url1 = "http://" + ip + "/insert_trusted".trim();
        System.out.println(" url :" + url1);
        Log.e(" url log ", url1 + "");

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url1,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject object = new JSONObject(new String(response.data));
                            if (object.getString("state").equals("ok")) {
                                Toast.makeText(InsertTrustedActivity.this, "success", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getBaseContext(), ShowTrustedActivity.class);
                                intent.putExtra("ip", ip);
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
                Toast.makeText(InsertTrustedActivity.this, "no internetConnection" + error, Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("name", nam);
                params.put("relation", relation);
                // params.put("img", img);
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

        // Adding request to request queue
        queue.add(volleyMultipartRequest);

    }

    public void insert_trusted(View view) {

        String name = namee.getText().toString();
        String rlation = relation.getText().toString();
//        String imgg = img.getText().toString();
        insert(bitmap, name, rlation);


    }

    public void choose(View view) {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(i, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {

            //getting the image Uri
            Uri imageUri = data.getData();
//            if (bitmaplist ==null){
//                bitmaplist=new ArrayList<Bitmap>();
//            }

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



