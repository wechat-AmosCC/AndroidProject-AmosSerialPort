AmosSerialPort
=========

Android����ͨѶ


How to compile library  
=========


### API
SerialPortHelp
========================================
�رմ���
----------------------------------------
closeSerialPort


�򿪴���
----------------------------------------
openSerialPort


�򴮿ڷ�������
----------------------------------------
sendData(byte[] bb)


Activity��ȡ����
----------------------------------------
serialHelper=new SerialPortHelp();
	serialHelper.setOnSerialPortDataListener(new OnSerialPortDataListener() {
        @Override
        public void onDataReceived(byte[] bytes) {
             Log.e("�ϲ��յ������ݣ�","����Ϊ��"+bytes.length);
        }
});