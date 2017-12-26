package com.github.yantzu.yuava;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

public class DigestedInputStreamTest {
	@Test
	public void testMd5() throws IOException {
		String content = "TO_TEST_MD5_OF_DIGESTED_INPUT_STREAM";
		DigestedInputStream dis = new DigestedInputStream(new ByteArrayInputStream(content.getBytes()),
				DigestUtils.getMd5Digest());

		Assert.assertEquals(content, IOUtils.toString(dis));

		Assert.assertEquals(DigestUtils.md5Hex(content), Hex.encodeHexString(dis.digest()));
	}

	@Test
	public void testSkipMd5() throws IOException {
		String content = "TO_TEST_MD5_OF_DIGESTED_INPUT_STREAM";
		DigestedInputStream dis = new DigestedInputStream(new ByteArrayInputStream(content.getBytes()),
				DigestUtils.getMd5Digest());

		IOUtils.skip(dis, Long.MAX_VALUE);

		Assert.assertEquals(DigestUtils.md5Hex(content), Hex.encodeHexString(dis.digest()));
	}

	@Test
	public void testReadByteMd5() throws IOException {
		String content = "TO_TEST_MD5_OF_DIGESTED_INPUT_STREAM";
		DigestedInputStream dis = new DigestedInputStream(new ByteArrayInputStream(content.getBytes()),
				DigestUtils.getMd5Digest());

		while (dis.read() >= 0) {
		}

		Assert.assertEquals(DigestUtils.md5Hex(content), Hex.encodeHexString(dis.digest()));
		dis.close();
	}
}
