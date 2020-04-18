package serialport.amos.cem.com.amosserialport;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import serialport.amos.cem.com.libamosserial.Commad;
import serialport.amos.cem.com.libamosserial.OnSerialPortDataListener;
import serialport.amos.cem.com.libamosserial.SerialPortHelp;

public class MainActivity extends AppCompatActivity {
    private boolean isOpen=false;
    private Button btn_send,btn_open;
    private SerialPortHelp serialHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        serialHelper=new SerialPortHelp();
        serialHelper.setOnSerialPortDataListener(new OnSerialPortDataListener() {
            @Override
            public void onDataReceived(byte[] bytes) {
                Log.e("上层收到的数据：","长度为："+bytes.length);
            }
        });
        initUIView();
    }

    private void initUIView(){
        btn_send=findViewById(R.id.btn_send);
        btn_open=findViewById(R.id.btn_open);
        btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isOpen= serialHelper.openSerialPort("/dev/ttyS1",115200);
                if(isOpen)
                {
                    Toast.makeText(MainActivity.this,"打开成功",Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(MainActivity.this,"打开失败",Toast.LENGTH_SHORT).show();
                }
            }
        });


        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isOpen)
                {
                    boolean bb=serialHelper.sendData(Commad.bytesE010);
                    if(bb)
                    {
                        Toast.makeText(MainActivity.this,"发送数据成功",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}
