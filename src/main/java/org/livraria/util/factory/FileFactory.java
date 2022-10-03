package org.livraria.util.factory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public final class FileFactory {
	private static final String TEMP_FOLDER = System.getProperty("java.io.tmpdir");
	
	private FileFactory() {}
	
	public static File getTempFile(InputStream in) throws IOException {
		File temp = new File(TEMP_FOLDER + "/" + UUID.randomUUID());
		OutputStream out = new FileOutputStream(temp);
		
		for (int byteInt ; (byteInt = in.read()) > -1 ;) {
			out.write(byteInt);
		}
		
		out.close();
		
		return temp;
	}
}
