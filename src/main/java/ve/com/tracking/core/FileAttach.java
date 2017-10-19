package ve.com.tracking.core;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;

public class FileAttach {

	private byte[] data;

	private String fileName;

	private String contentType;

	public FileAttach(byte[] data, String fileName, String contentType) {
		super();
		this.data = data;
		this.fileName = fileName;
		this.contentType = contentType;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public InputStreamSource getInputStreamSource() {
		return new ByteArrayResource(data);
	}

}
