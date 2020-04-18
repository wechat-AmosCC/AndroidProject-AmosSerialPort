package serialport.amos.cem.com.libamosserial;

import android.util.Log;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @ProjectName: AmosSerialPort
 * @Package: serialport.amos.cem.com.libamosserial
 * @ClassName: SerialPort
 * @Description: java类作用描述 --- ---端口操作类 不对外提供操作
 * @Author: Amos
 * @CreateDate: 2020/4/17 17:28
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/4/17 17:28
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class SerialPort {
    private static final String TAG = "SerialPort";
    /*
     * Do not remove or rename the field mFd: it is used by native method
     * close();
     */
    private FileDescriptor mFd;
    private FileInputStream mFileInputStream;
    private FileOutputStream mFileOutputStream;
    public SerialPort() {
    }
    public SerialPort(File device, int baudrate, int flags)
            throws SecurityException, IOException {
        /* Check access permission */
        if (!device.canRead() || !device.canWrite()) {
            try {
                /* Missing read/write permission, trying to chmod the file */
                Process su;
                su = Runtime.getRuntime().exec("/system/bin/su");
                String cmd = "chmod 666 " + device.getAbsolutePath() + "\n"
                        + "exit\n";
                su.getOutputStream().write(cmd.getBytes());
                if ((su.waitFor() != 0) || !device.canRead()
                        || !device.canWrite()) {
                    throw new SecurityException();
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new SecurityException();
            }
        }

        mFd = open(device.getAbsolutePath(), baudrate, flags);
        if (mFd == null) {
            Log.e(TAG, "native open returns null");
            throw new IOException();
        }
        mFileInputStream = new FileInputStream(mFd);
        mFileOutputStream = new FileOutputStream(mFd);
    }
    // Getters and setters
    public InputStream getInputStream() {
        return mFileInputStream;
    }
    public OutputStream getOutputStream() {
        return mFileOutputStream;
    }
    // JNI
    private native static FileDescriptor open(String path, int baudrate, int flags);
    public native void close();
    public native String stringFromJNI();
    static {
        System.loadLibrary("serial_port");
    }
}