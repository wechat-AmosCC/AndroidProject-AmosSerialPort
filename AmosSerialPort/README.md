AmosSerialPort
=========

Android串口通讯


How to compile library  
=========


### API
SerialPortHelp
========================================
关闭串口
----------------------------------------
closeSerialPort


打开串口
----------------------------------------
openSerialPort


向串口发送数据
----------------------------------------
sendData(byte[] bb)


Activity获取数据
----------------------------------------
serialHelper=new SerialPortHelp();
	serialHelper.setOnSerialPortDataListener(new OnSerialPortDataListener() {
        @Override
        public void onDataReceived(byte[] bytes) {
             Log.e("上层收到的数据：","长度为："+bytes.length);
        }
});