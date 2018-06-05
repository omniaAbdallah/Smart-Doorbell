package com.example.omnia.smartdoorbell;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.omnia.smartdoorbell.block.BlockActivity;
import com.example.omnia.smartdoorbell.history.History_Activity;
import com.example.omnia.smartdoorbell.trusted.MainTrustedActivity;

public class MainActivity extends AppCompatActivity {

    String ip_server,ip_esp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ip = "192.168.43.12:5005";
//        ip="192.168.1.4:5005";


        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_config, null);
        final EditText server = (EditText) mView.findViewById(R.id.ip_server);
        final EditText esp = (EditText) mView.findViewById(R.id.ip_esp);
        Button ok = (Button) mView.findViewById(R.id.dailog_ok);



        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!server.getText().toString().isEmpty() && !esp.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this,
                           "scuess",
                            Toast.LENGTH_SHORT).show();
                     ip_server=server.getText().toString();
                     ip_esp= esp.getText().toString();
                    SharedPreferences sharedPreferences = getSharedPreferences("sharedfile",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    //String ip= from dialog
                    editor.putString("ipServer", ip_server);
                    editor.putString("ipESP", ip_esp);
                    editor.commit();
                    Toast.makeText(MainActivity.this, "saving IP with" + ip_server, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }else{
                    Toast.makeText(MainActivity.this, "please sure to  enter ip ",
                            Toast.LENGTH_SHORT).show();
                }
            }




    });

    }

    public void trusted(View view) {

        Intent intent = new Intent(MainActivity.this, MainTrustedActivity.class);
       // intent.putExtra("ip", ip);
        startActivity(intent);
    }


    public void historry(View view) {
        Intent intent = new Intent(MainActivity.this, History_Activity.class);
       // intent.putExtra("ip", ip);
        startActivity(intent);
    }

    public void block(View view) {
        Intent intent = new Intent(MainActivity.this, BlockActivity.class);
//        intent.putExtra("ip", ip);
        startActivity(intent);
    }

    public void replay(View view) {

        Intent intent =new Intent(MainActivity.this, ReplayActivity.class);
//        intent.putExtra("ip",ip);
        startActivity(intent);
    }
}
