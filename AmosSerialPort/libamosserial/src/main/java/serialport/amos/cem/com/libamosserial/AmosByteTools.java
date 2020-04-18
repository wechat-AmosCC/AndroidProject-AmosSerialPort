package serialport.amos.cem.com.libamosserial;

import java.text.DecimalFormat;

/**
 * @ProjectName: AmosSerialPort
 * @Package: serialport.amos.cem.com.libamosserial
 * @ClassName: AmosByteTools
 * @Description: java类作用描述 --- ---Byte操作工具类
 * @Author: Amos
 * @CreateDate: 2020/4/17 17:38
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/4/17 17:38
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class AmosByteTools {

    private static final char[] HEXES = {
            '0', '1', '2', '3',
            '4', '5', '6', '7',
            '8', '9', 'a', 'b',
            'c', 'd', 'e', 'f'
    };

    /**
     * byte数组 转换成 16进制小写字符串
     */
    public static String bytes2Hex(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        StringBuilder hex = new StringBuilder();

        for (byte b : bytes) {
            hex.append(HEXES[(b >> 4) & 0x0F]);
            hex.append(HEXES[b & 0x0F]);
        }
        return hex.toString();
    }
    /**
     * Byte[]数组叠加的函数
     */
    public static byte[] amosByteAdd(byte[] bt1, byte[] bt2) {
        byte[] bt3 = new byte[bt1.length + bt2.length];
        System.arraycopy(bt1, 0, bt3, 0, bt1.length);
        System.arraycopy(bt2, 0, bt3, bt1.length, bt2.length);
        return bt3;
    }

    /**
     * 4位的byte数组转换为整数
     * 第0个byte与上0xff,生成整数,在右移24位，取得一个整数
     * 第1个byte与上0xff,生成整数,在右移16位，取得一个整数
     * 第2个byte与上0xff,生成整数,在右移8位，取得一个整数
     * 第3个byte与上0xff,生成整数
     * 把四个整数做或操作,转换为已整数
     */
    public static int byte4ArrToInt(byte[] arr) {
        for (byte temp : arr) {
            System.out.print(temp + " ");
        }
        int x = ((arr[0] & 0xff) << 24 )|((arr[1]& 0xff) <<16 )|((arr[2] & 0xff)<<8)|(arr[3] & 0xff);
        //int x = ((arr[0] & 0xff) << 16) | ((arr[1] & 0xff) << 8) | (arr[2] & 0xff);
        return x;
    }

    /**
     * 3位的byte数组转换为整数
     * 第0个byte与上0xff,生成整数,在右移24位，取得一个整数
     * 第1个byte与上0xff,生成整数,在右移16位，取得一个整数
     * 第2个byte与上0xff,生成整数,在右移8位，取得一个整数
     * 把四个整数做或操作,转换为已整数
     */
    public static int byte3ArrToInt(byte[] arr) {
        for (byte temp : arr) {
            System.out.print(temp + " ");
        }
        //   int x = ((arr[0] & 0xff) << 24 )|((arr[1]& 0xff) <<16 )|((arr[2] & 0xff)<<8)|(arr[3] & 0xff);
        int x = ((arr[0] & 0xff) << 16) | ((arr[1] & 0xff) << 8) | (arr[2] & 0xff);
        return x;
    }


    /**
     * 7760专用数据解析函数
     * 第0个byte与上0xff,生成整数,在右移16位，取得一个整数
     * 第1个byte与上0xff,生成整数,在右移8位，取得一个整数
     * 第3个byte与上0xff,生成整数
     * 把四个整数做或操作,转换为已整数
     */
    public static String byteArrToIntFor7760(byte[] arr) {
        for (byte temp : arr) {
            System.out.print(temp + " ");
        }
        //   int x = ((arr[0] & 0xff) << 24 )|((arr[1]& 0xff) <<16 )|((arr[2] & 0xff)<<8)|(arr[3] & 0xff);
        int x = ((arr[0] & 0xff) << 16) | ((arr[1] & 0xff) << 8) | (arr[2] & 0xff);

        int int_p4 = (0xff & arr[3]);
        DecimalFormat df4 = new DecimalFormat("#.00");
        if (int_p4 == 0) {
            df4 = new DecimalFormat("#");
        } else if (int_p4 == 1) {
            df4 = new DecimalFormat("#.0");
        } else if (int_p4 == 2) {
            df4 = new DecimalFormat("#.00");
        } else if (int_p4 == 3) {
            df4 = new DecimalFormat("#.000");
        }
        double xxx=x/100;
        return df4.format(xxx);
    }
}
