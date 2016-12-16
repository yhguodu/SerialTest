package proudsmart.SerialTest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.TooManyListenersException;

import java.util.logging.Logger;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEventListener;
import proudsmart.SerialTest.SerialPortManager.BaudRate;
import proudsmart.SerialTest.utils.Converter;
import proudsmart.SerialTest.utils.PortConnection;
import proudsmart.SerialTest.utils.PortNotAvailableException;



/**
 * The library that establishes connection through the serial port.
 * 
 */
public class SerialPortConnection implements PortConnection {

	// private static final Logger log =
	// Logger.getLogger(SerialPortConnection.class);
	private final static Logger logger = Logger.getLogger(SerialPortConnection.class.getName());
	private final String portName;

	protected SerialPort hostPort = null;
	protected SerialPortManager.BaudRate br;
	
	/**
	 * How long to wait, in ms, between retries of send.
	 */
	protected static final long RETRY_WAIT_INTERVAL = 5000L;
	
	public static final int DEFAULT_QUEUE_SIZE = 50;

	private OutputStream out = null;
		
	
	public static SerialPortConnection newConnection(String portName,SerialPortManager.BaudRate br) {
		return new SerialPortConnection(portName,br);
	}


	public void open()  throws PortNotAvailableException,TooManyListenersException,Exception {
		// Get the port to listen from.
		
		CommPortIdentifier portId = SerialPortManager.getSerialPort(portName);

		// Open that port. We use baud rate 57600.
		hostPort = SerialPortManager.openPort(portId, br);
		out = hostPort.getOutputStream();
		// Create event listener on port.
		// When new data comes in, event listener will process the data.
		InputStream inputStr = hostPort.getInputStream();
		hostPort.notifyOnDataAvailable(true);

		try {
			hostPort.addEventListener(
					(SerialPortEventListener) new SerialPortEventListenerImpl(inputStr, this));
		} catch (Exception e) {
			// log.error("Could not add event listener.", e);
			throw new PortNotAvailableException("Could not add event listener."+e.getMessage());
		}
	}

	public void close() throws Exception {
		if (hostPort != null) {
			hostPort.getOutputStream().close();
			hostPort.getInputStream().close();
			hostPort.removeEventListener();
			if (hostPort != null)
				hostPort.close();

			hostPort = null;
		}
	}
	
	public void sendMessage(byte[] data) throws IOException {
		logger.info("send message:"+new String(data));
		if(out != null)
			out.write(data);
	}
	
	public void sendRawDataToSerialPort(byte[] data) throws IOException  {
		if(out == null)
			out = hostPort.getOutputStream();
		out.write(data);
		//Thread.sleep(50);
		logger.info("finally data to serial port:"+ Converter.ByteArrayToHexString(data));
	}
	
	private SerialPortConnection(String portName,SerialPortManager.BaudRate br) {
		this.portName = portName;
		this.br = br;
	}

	public void readMessage(byte[] message) {
		
	}

}
