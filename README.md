此程序主要用来测试串口能够打开的问题，在无法打开的情况下界面上会打印异常。
使用方法：
           （1）打开SerialTest
           （2）输入 ./start.sh "-p PORTNAME -b BAUDRATE"
如：
       ./start.sh "-p /dev/ttyUSB1 -b 9600"
       输出：Error:No such port exception :gnu.io.NoSuchPortException portName:/dev/ttyUSB1

参数说明：
           Proudsamrt Serial Test
             -b,--baudrate <arg>    REQUIRED. Specifies thethe baudrate with the
                        portdefault value: 9600
             -h,--help <arg>        Usage information.
             -p,--port name <arg>   REQUIRED. Specifies the port name you want to open
                        .default vaule: /dev/ttyUSB0
