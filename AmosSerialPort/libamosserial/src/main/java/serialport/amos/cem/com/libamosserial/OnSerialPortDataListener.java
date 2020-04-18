package serialport.amos.cem.com.libamosserial;

import java.util.List;

/**
 * @ProjectName: AmosSerialPort
 * @Package: serialport.amos.cem.com.libamosserial
 * @ClassName: OnSerialPortDataListener
 * @Description: java类作用描述 --- ---回调数据接口
 * @Author: Amos
 * @CreateDate: 2020/4/18 10:42
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/4/18 10:42
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public interface OnSerialPortDataListener {
    /**
     * 数据接收
     *
     * @param bytes 接收到的数据
     */
    void onDataReceived(byte[] bytes);
}
