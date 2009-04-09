package sessionmon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class CommandEnum {
	private final String commandName;
	private final int id;
	
	private static final List _VALUES = new ArrayList();
	
	/** unmodifiable types **/
	public static final List VALUES = Collections.unmodifiableList(_VALUES);
	public static final CommandEnum DUMP = new CommandEnum(1, "dump");
	public static final CommandEnum TEST_REPLICATION = new CommandEnum(2, "test-replication");
	public static final CommandEnum INVALIDATE_SESSION = new CommandEnum(3, "invalidate-session");
	
	private CommandEnum(int id, String name) {
		this.commandName = name;
		this.id = id;
		CommandEnum._VALUES.add(this);
	}
	
	public int getId() {
		return id;
	}
	public String toString() {
		return commandName;
	}
	
	public static CommandEnum valueOf(String name) throws IllegalArgumentException {
		if(name == null)
			throw new IllegalArgumentException("Unknown command");
		
		Iterator iter = VALUES.iterator();
		while(iter.hasNext()) {
			Object obj = iter.next();
			if(name.equalsIgnoreCase(obj.toString())) {
				return (CommandEnum)obj;
			}
		}
		throw new IllegalArgumentException("Unknown command");
	}
	
	public boolean equals(Object o) {
		if(o instanceof CommandEnum && o.toString().equals(toString()))
			return true;
		else
			return false;
	}
}
