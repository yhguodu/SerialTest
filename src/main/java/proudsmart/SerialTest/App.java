package proudsmart.SerialTest;

import java.io.PrintStream;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang.StringUtils;

import gnu.io.CommPortIdentifier;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
    	PrintStream ps = new PrintStream(System.out);
    	
    	String portName = "/dev/ttyUSB0";
    	int baudrate = 9600;
    	
    	CommandLine cl = getCommandLine(ps,args);
    	if(cl == null)
    		return;
    	
    	if(cl.hasOption('h')) {
    		usage(ps,args);
    		return;
    	}
    	
    	if(cl.hasOption('p')) {
    		portName = cl.getOptionValue('p');
    	}
    	
    	if(cl.hasOption('b')) {
    		try {
    			baudrate = Integer.parseInt(cl.getOptionValue('b'));
    		}
    		catch(Exception e) {
    			ps.println("baudrate can't parseable with :" + cl.getOptionValue('b'));
    			return;
    		}
    	}
    	
    	try {
    		SerialPortManager.BaudRate br = SerialPortManager.getBaudRate(baudrate);
    		SerialPortConnection spc = SerialPortConnection.newConnection(portName, br);
    		spc.open();
    		spc.sendMessage("hellowrold".getBytes());
    	}
    	catch(Exception e) {
    		ps.println("Error:"+e.getMessage());
    		List<CommPortIdentifier> cpiList   =  null;
    		try {
    				 cpiList = SerialPortManager.getAllSerialPorts();
    				 ps.println("the ports list below at available at present");
    				 for(CommPortIdentifier cpi: cpiList) {
    					 ps.print(cpi.getName() + " ");
    				 }
    				 ps.println("");
    		}
    		catch(Exception ee) {
    			ps.println(ee.getMessage());
    		}
    	}
    }
    
    private static CommandLine getCommandLine(PrintStream out, String[] args) {
        CommandLineParser clp = new GnuParser();
        CommandLine cl;
        try {
            cl = clp.parse(options(), args);
        } catch (ParseException e) {
            usage(out, args);
            return null;
        }
        
        if (cl.getArgList().size() != 0) {
            usage(out, args);
            return null;
        }

        return cl;
    }
    
    private static Options options() {
        Options options = new Options();
        options.addOption("h", "help", true, "Usage information." );
        options.addOption("p", "port name", true,  "REQUIRED. Specifies the " +
                "port name you want to open ."+
        		"default vaule: /dev/ttyUSB0");
        options.addOption("b", "baudrate", true, "REQUIRED. Specifies the"+
                "the baudrate with the port"+
        		"default value: 9600");
        return options;
    }
    
    private static void usage(PrintStream out, String[] args) {
        HelpFormatter hf = new HelpFormatter();
        hf.printHelp("Proudsamrt Serial Test", options());
        out.println("Your arguments were: "+StringUtils.join(args, ' '));
    }
}
