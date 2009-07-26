package sessionmon.util;

import java.io.ByteArrayOutputStream;
import java.io.NotSerializableException;
import java.io.ObjectOutputStream;

import com.vladium.utils.IObjectProfileNode;
import com.vladium.utils.ObjectProfiler;

public class JavaObjectProfiler {
	private Object obj;
	private ByteArrayOutputStream bs;
	private ObjectOutputStream os;
	
	private String objectType;
	private int objectGraphSizeInBytes;
	private int objectSerializedSizeInBytes;
	private String toStringValue;
	private boolean isSerializable;
	
	public JavaObjectProfiler(Object o) throws IllegalArgumentException {
		if(o == null)
			throw new IllegalArgumentException();
		set(o);
	}
	
	public void set(Object o) {
		clear();
		this.obj = o;
		init();
	}
	
	public void clear() {
		this.obj = null;
		this.bs = null;
		this.os = null;
		this.objectType = null;
		this.objectGraphSizeInBytes = 0;
		this.objectSerializedSizeInBytes = 0;
		this.toStringValue = null;
	}
	
	private void init() {
		isSerializable = true;
		try {
			bs = new ByteArrayOutputStream();
			os = new ObjectOutputStream(bs);
			os.writeObject(obj);
			objectSerializedSizeInBytes = bs.size();
		} catch(NotSerializableException e) {
			isSerializable = false;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		objectType = obj.getClass().getName();
		toStringValue = obj.toString();
		
		try {
			IObjectProfileNode profile = ObjectProfiler.profile (obj);
			objectGraphSizeInBytes = profile.size();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public String getObjectType() {
		return objectType;
	}

	public int getObjectGraphSizeInBytes() {
		return objectGraphSizeInBytes;
	}

	public int getObjectSerializedSizeInBytes() {
		return objectSerializedSizeInBytes;
	}

	public String getToStringValue() {
		return toStringValue;
	}

	public boolean isSerializable() {
		return isSerializable;
	}
}
