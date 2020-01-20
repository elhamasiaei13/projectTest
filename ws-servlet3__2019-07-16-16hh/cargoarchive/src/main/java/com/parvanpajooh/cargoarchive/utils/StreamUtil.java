package com.parvanpajooh.cargoarchive.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamUtil {

	private static final int BUF_SIZE = 1024;

	public static long copy(InputStream from, OutputStream to) throws IOException {
		byte[] buf = new byte[BUF_SIZE];
		long total = 0;
		while (true) {
			int r = from.read(buf);
			if (r == -1) {
				break;
			}
			to.write(buf, 0, r);
			total += r;
		}
		return total;
	}

}