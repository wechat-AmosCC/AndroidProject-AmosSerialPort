package serialport.amos.cem.com.libamosserial;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: AmosSerialPort
 * @Package: serialport.amos.cem.com.libamosserial
 * @ClassName: SerialPortHelp
 * @Description: java类作用描述 --- ---串口操作类 对外提供 打开 关闭 数据回调等函数功能
 * @Author: Amos
 * @CreateDate: 2020/4/17 17:32
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/4/17 17:32
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class SerialPortHelp {
    public static final String TAG = "AmosLibSerialPortHelp";
    public boolean isRead = true;
    public byte[] revBuffer;
    public SerialPort mSerialPort = null;
    public FileOutputStream mOutputStream = null;
    public ReadThread mReadThread = null;
    private OnSerialPortDataListener mOnSerialPortDataListener;

    /**
     * 关闭串口
     */
    public void closeSerialPort() {
        isRead = false;
        if (mSerialPort != null) {
            mSerialPort.close();
            mSerialPort = null;
        }
        try {
            mOutputStream.close();
            mOutputStream = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        mReadThread = null;
    }

    /**
     * 打开串口
     * @param device
     * @param baudrate
     * @return
     */
    public boolean openSerialPort(String device, int baudrate){
        boolean isSuccess=false;
        try {
            if (mSerialPort == null) {
                mSerialPort = new SerialPort(new File(device), baudrate, 0);
                mOutputStream = (FileOutputStream) mSerialPort.getOutputStream();
                isRead = true;
                isSuccess=true;
                mReadThread = new ReadThread(mSerialPort, device);
                mReadThread.start();
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    /**
     * 发送串口数据
     * @param bytes
     * @return
     */
    public boolean sendData(byte[] bytes)
    {
        boolean isSuccess=false;
        try {
            if (mOutputStream != null) {
                mOutputStream.write(bytes);
                isSuccess=true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }
    /**
     * 添加数据通信监听
     *
     * @param listener listener
     * @return SerialPortManager
     */
    public SerialPortHelp setOnSerialPortDataListener(OnSerialPortDataListener listener) {
        mOnSerialPortDataListener = listener;
        return this;
    }

    class ReadThread extends Thread {
        SerialPort serialPort = null;
        String device = null;
        FileInputStream inputStream = null;
        ReadThread(SerialPort port, String device) {
            serialPort = port;
            device = device;
            inputStream = (FileInputStream) serialPort.getInputStream();
        }
        @Override
        public void run() {
            super.run();
            while (isRead) {
                readSerial();
            }
        }
        void readSerial() {
            int size;
            try {
                if (inputStream == null) {
                    Log.e(TAG, "inputStream is null");
                    return;
                }
                byte[] buffer = new byte[256];
                size = inputStream.read(buffer);
                if (size > 0) {
                    if (revBuffer == null) {
                        //Log.e(TAG, "-------revBuffer:" + "revBuffer 初始化");
                        revBuffer = new byte[0];
                    }
                    // Log.e(TAG, "-------size:" + size);
                    byte[] bufferNew = new byte[size];
                    System.arraycopy(buffer, 0, bufferNew, 0, size);
                    String str = AmosByteTools.bytes2Hex(bufferNew);
                    if (revBuffer.length == 0) {
                        if (str.indexOf("aae0") == 0) {
                            // Log.e(TAG, "-------revBuffer:" + "revBuffer 第一次添加数据");
                            //第一个为AAE0则表示，数据正确，然后再把缓存放进去，否则直接弃用
                            revBuffer = AmosByteTools.amosByteAdd(revBuffer, bufferNew);
                            return;
                        } else {
                            return;
                        }
                    }
                    revBuffer = AmosByteTools.amosByteAdd(revBuffer, bufferNew);
                    //我们这第四位是完整的包长度，用缓存的数据去比较是否大于等于完整包长度
                    if (revBuffer.length >= (0xff & revBuffer[4])) {
                        String strzzz = AmosByteTools.bytes2Hex(revBuffer);
                        //这是一包完整的数据了，在这里要做解析动作
                        //校准数据
                        if ((revBuffer[0] == (byte) 0xaa) && (revBuffer[1] == (byte) 0xe0) && (revBuffer[revBuffer.length - 1] == (byte) 0xff)) {
                            mOnSerialPortDataListener.onDataReceived(revBuffer);
                        }
                        //解析完成后清除buff
                        revBuffer = null;
                        return;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }
}
