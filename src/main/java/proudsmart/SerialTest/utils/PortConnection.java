package proudsmart.SerialTest.utils;

import java.io.IOException;
import java.util.TooManyListenersException;



/**
 * Specifies the public APIs of the port connection.
 * 
 */
public interface PortConnection  {
	
	/**
 	* 	open the connection
 	* @throws PortNotAvailableException,TooManyListenersException,Exception
 	*/
	public void open() throws PortNotAvailableException,TooManyListenersException,Exception;
	
	
	/**
	 * Close port for clean up.
	 * @throws Exception
	 */
	public void close() throws Exception;
	
	/**
	 * read msg from port
	 * @param message
	 */
	public void readMessage(byte[] message);
	
	
	/**
	 * send data to port
	 * @param data
	 */
	public void sendMessage(byte[] data) throws IOException ;

}