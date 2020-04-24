package Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Attachments {
	
	public void copyFile(String source, String destination) throws IOException {
		File From = new File(source);
		File To = new File(destination);
		InputStream Input = null;
		OutputStream Output = null;
		
		Input = new FileInputStream(From);
		Output = new FileOutputStream(To);
		byte[] buffer = new byte[1024];
		int L;
		while ((L = Input.read(buffer)) > 0) {
				Output.write(buffer, 0, L);
			}
		Input.close();
		Output.close();
		
	}
	
	public static void main(String[] args) throws IOException {
		
		String s = "path";
		String d = "path";
		//CopyFile(s, d);
		}
	

}
