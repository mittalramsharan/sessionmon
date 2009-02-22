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
	public static final CommandEnum REPORT = new CommandEnum(1, "report");
	public static final CommandEnum ADD_SESSION_PARAMETERS = new CommandEnum(2, "addSessionParams");
	public static final CommandEnum REMOVE_SESSION_PARAMETERS = new CommandEnum(3, "removeSessionParams");
	public static final CommandEnum INVALIDATE_SESSION = new CommandEnum(4, "invalidateSession");
	
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
