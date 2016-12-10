package proudsmart.SerialTest;

import java.io.IOException;
import java.io.InputStream;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import proudsmart.SerialTest.utils.PortConnection;

import java.util.logging.Logger;

public class SerialPortEventListenerImpl implements SerialPortEventListener {

	// private static final Logger log =
	// Logger.getLogger(SerialPortEventListenerImpl.class);
	private final static Logger logger = Logger.getLogger(SerialPortEventListenerImpl.class.getName());

	private boolean logAllRawInput = false; // enable to true if we want to log
											// all raw input history
	private InputStream byteDataStream = null;

	private PortConnection portConnection;

	/**
	 * @param byteDataStream
	 *            Incoming byte data.
	 * @param byteDataHandler
	 *            Handler that reads the byte data.
	 */
	public SerialPortEventListenerImpl(InputStream byteDataStream, 
			SerialPortConnection serialPortConnection) {
		this.byteDataStream = byteDataStream;
		this.portConnection = serialPortConnection;
	}

	public void serialEvent(SerialPortEvent arg0) {
		// We're here if new data came in.

		try {
			// log.debug("Reading bytes ...");
			logger.info("Reading bytes ...");

			int available = byteDataStream.available();

			byte[] data = new byte[available];
			byteDataStream.read(data);
			// Log the raw data stream
		
			processData(data);
				
			// log.debug("... done reading bytes.");

		} catch (IOException e) {
			// log.error("Error during serial port listen event.", e);
			logger.info(e.getMessage());
			try {
				portConnection.close();
			} 
			catch(Exception ex) {
				logger.info(ex.getMessage());
			}

		}
	}


	private void processData(byte[] moreData) {
	
	}

}
