package de.wmgruppe.ListQueueReader;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;

import javax.resource.ResourceException;

import com.ibm.vse.connector.VSEConnectionSpec;
import com.ibm.vse.connector.VSEConsole;
import com.ibm.vse.connector.VSESystem;

public class ConsolenExample {
	public static void main(String[] args) {
		VSEConsole cons;
		VSEConnectionSpec spec;
		VSESystem system;
		
		try {
			spec = new VSEConnectionSpec(InetAddress.getByName("172.20.160.54"),2893,"b617","gkuehne");
			spec.setMaxConnections(5);
			spec.setLogonMode(true);
			system = new VSESystem(spec);
		    final InputStreamReader in = new InputStreamReader(System.in);
		    final StringReader sr = new StringReader(
		    		
"* $$ JOB JNM=GKUE,CLASS=0,SYSID=3\n"+    
"* $$ LST V\n"+                           
"// JOB GKUE\n"+
"// LIBDEF PHASE,SEARCH=USRWMT.BATCH\n"+  
"// EXEC GKTEST7\n"+                      
"12IOPP\n"+                               
"/*\n"+                                   
"// EXEC LISTLOG\n"+                      
"/&\n"+                                   
"* $$ EOJ\n");
		    OutputStreamWriter out = new OutputStreamWriter(System.out);
			system.getVSEPower().executeJob(new Reader() {
				
				@Override
				public int read(char[] cbuf, int off, int len) throws IOException {
					Arrays.fill(cbuf, '\0');
					int r = sr.read(cbuf, off, len);
					if (r == -2) {
						r = in.read(cbuf, off, len);
						}
					System.out.println(cbuf);
					return r;
				}
				
				@Override
				public void close() throws IOException {
					// TODO Auto-generated method stub
					
				}
			}, out);
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ResourceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
