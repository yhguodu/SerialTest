package proudsmart.SerialTest;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
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
    	    	
    	CommandLine cl = getCommandLine(ps,args);
    	if(cl == null)
    		return;
    	
    	if(cl.hasOption('h')) {
    		usage(ps,args);
    		return;
    	}
    	
    	if(cl.hasOption('v')) {
    		List<CommPortIdentifier> cpiList   =  null;
    		try {
    				 cpiList = SerialPortManager.getAllSerialPorts();
    				 ps.println("all the available ports at present:");
    				 for(CommPortIdentifier cpi: cpiList) {
    					 ps.print(cpi.getName() + " ");
    				 }
    				 ps.println("");
    		}
    		catch(Exception ee) {
    			ps.println(ee.getMessage());
    		}
    		return;
    	}
    	
    	String portName = "/dev/ttyUSB0";
    	int baudrate = 9600;
    	int counter = 1;
    	List<String> portList = new ArrayList<String>();
    	
    	if(cl.hasOption('p')) {
    		portName = cl.getOptionValue('p');
    		portList.add(portName);
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
      
    	if(cl.hasOption('n')) {
    		try {
    			counter = Integer.parseInt(cl.getOptionValue('n'));
    		}
    		catch(Exception e) {
    			ps.println("invalid -n argument");
    			return;
    		}
    	}
    	
    	if(cl.hasOption("l")) {
    		String ports = cl.getOptionValue('l');
    		String[] tmp = ports.trim().split(",");
    		for(String port: tmp)
    			portList.add(port.trim());
    	}
    	
    	if(cl.hasOption("all")) {
    		List<CommPortIdentifier> cpiList   =  null;
    		try {
    				 cpiList = SerialPortManager.getAllSerialPorts();
    				 for(CommPortIdentifier cpi: cpiList) {
    					 portList.add(cpi.getName());
    				 }
    		}
    		catch(Exception ee) {
    			ps.println(ee.getMessage());
    		}
    	}
    	
    	
    	if(cl.hasOption('p') == false && cl.hasOption('l') == false && cl.hasOption("all") == false) 
    		portList.add(portName);
    	
    		SerialPortManager.BaudRate br = SerialPortManager.getBaudRate(baudrate);
    		int n =0;
    		while(n< counter) {
    			try {
	    			for(String port: portList) {
	    				SerialPortConnection spc = SerialPortConnection.newConnection(port, br);
	    	    		spc.open();
	    	    		String wel = "hello,"+port;
	    	    		spc.sendMessage(wel.getBytes());
	    	    		System.out.println("port open successful:"+ port+ " with time:"+(n+1));
	    	    		spc.close();
	    			}
	    			n++;
	    			Thread.currentThread().sleep(100);
    			}
    		  	catch(Exception e) {
    	    		ps.println("Error:"+e.getMessage());
    	    		return;
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
        options.addOption("h", "help", false, "Usage information." );
        options.addOption("p", "port name", true,  "REQUIRED. Specifies the " +
                "port name you want to open .\n"+
        		"default vaule: /dev/ttyUSB0");
        options.addOption("b", "baudrate", true, "REQUIRED. Specifies the"+
                "baudrate with the port.\n"+
        		"default value: 9600");
        options.addOption("v", "availableports", false, "Usage information.\n"+
                "list all the available ports at present");
        options.addOption("l", "ports need to be tested", true, "REQUIRED."+
                "test the required ports in the parameters,ports separated by comma");
        options.addOption("n", "test counter", true, "REQUIRED.Specifies the times for the test");
        options.addOption("all", "test all the ports", false, "Usage information."+
                "test all the ports list in the gnu.io.properties file");
        return options;
    }
    
    private static void usage(PrintStream out, String[] args) {
        HelpFormatter hf = new HelpFormatter();
        hf.printHelp("Proudsamrt Serial Test", options());
        out.println("Your arguments were: "+StringUtils.join(args, ' '));
    }
}
