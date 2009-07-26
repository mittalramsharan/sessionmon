package sessionmon;

import java.io.Serializable;

public class SessionAttribute implements Serializable {
	private String server = null;
	private String name = null;
	private String objectType = null;
	private String toStringValue = null;
	private int objectGraphSizeInBytes = 0;
	private int objectSerializedSizeInBytes = 0;
	private boolean isSerializable = false;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getObjectType() {
		return objectType;
	}
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	public int getObjectGraphSizeInBytes() {
		return objectGraphSizeInBytes;
	}
	public void setObjectGraphSizeInBytes(int objectGraphSizeInBytes) {
		this.objectGraphSizeInBytes = objectGraphSizeInBytes;
	}
	public int getObjectSerializedSizeInBytes() {
		return objectSerializedSizeInBytes;
	}
	public void setObjectSerializedSizeInBytes(int objectSerializedSizeInBytes) {
		this.objectSerializedSizeInBytes = objectSerializedSizeInBytes;
	}
	public String getToStringValue() {
		return toStringValue;
	}
	public void setToStringValue(String toStringValue) {
		this.toStringValue = toStringValue;
	}
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public boolean isSerializable() {
		return isSerializable;
	}
	public void setSerializable(boolean isSerializable) {
		this.isSerializable = isSerializable;
	}

}
