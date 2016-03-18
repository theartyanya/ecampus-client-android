package dny.text.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;

public abstract class Streams {
	
	private static final int bufferSize = 256; 

	public static String streamToString(InputStream stream) throws IOException {
		final char[] buffer = new char[bufferSize];
		final StringBuilder out = new StringBuilder();
		final Reader reader = new InputStreamReader(stream, "UTF-8");
		try {
			while (true) {
				final int count = reader.read(buffer, 0, buffer.length);
				if (count < 0) return out.toString();
				out.append(buffer, 0, count);
			}
		}
		finally {
			reader.close();
		}
	}

	public static void stringToStream(OutputStream stream, String string) throws IOException {
		stream.write(string.getBytes());
	}

}
