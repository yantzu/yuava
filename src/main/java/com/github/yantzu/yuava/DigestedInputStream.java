package com.github.yantzu.yuava;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

public class DigestedInputStream extends FilterInputStream {

	private MessageDigest messageDigest;

	public DigestedInputStream(InputStream in, MessageDigest messageDigest) {
		super(in);
		this.messageDigest = messageDigest;
	}

	public int read() throws IOException {
		int b = in.read();
		if (b != -1) {
			messageDigest.update((byte) b);
		}
		return b;
	}

	public int read(byte[] buf, int off, int len) throws IOException {
		len = in.read(buf, off, len);
		if (len != -1) {
			messageDigest.update(buf, off, len);
		}
		return len;
	}

	public long skip(long n) throws IOException {
		byte[] buf = new byte[512];
		long total = 0;
		while (total < n) {
			long len = n - total;
			len = read(buf, 0, len < buf.length ? (int) len : buf.length);
			if (len == -1) {
				return total;
			}
			total += len;
		}
		return total;
	}

	public byte[] digest() {
		return messageDigest.digest();
	}
}
