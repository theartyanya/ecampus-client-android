package dny.text.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class Streams {

	public static String streamToString(InputStream stream) throws IOException {
		byte[] bytes = new byte[stream.available()];
		stream.read(bytes);
		return new String(bytes);
	}

	public static void stringToStream(OutputStream stream, String string) throws IOException {
		stream.write(string.getBytes());
	}

}
