package sessionmon.report;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.Iterator;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import sessionmon.SessionAttribute;
import sessionmon.SessionInfo;

public class XMLReport extends Report {
	private XMLStreamWriter writer = null;
	
	public String generate(SessionInfo info) {
		XMLOutputFactory outputFactory=XMLOutputFactory.newInstance();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		try {
			writer=outputFactory.createXMLStreamWriter(output, "UTF-8");
			writer.writeStartDocument("1.0");
			writer.writeComment("SessionMon Report");
			writer.writeStartElement("report", "session", "http://sessionmon");
			writer.writeNamespace("report", "http://sessionmon");
			writer.writeNamespace("xsi","http://www.w3.org/2001/XMLSchema-instance");
			
			writeElement("server_name", info.getServerName());
			writeElement("server_port", info.getServerPort());
			writeElement("application_url", info.getApplicationURL());
			writeElement("id", info.getId());
			writeElement("total_byte_size", info.getTotalByteSize());
			writeElement("creation_time", info.getCreationTime());
			writeElement("last_accessed_time", info.getLastAccessedTime());
			writeElement("max_inactive_interval_in_seconds", info.getMaxInactiveIntervalInSeconds());
			
			for(Iterator i = info.getAttributes().iterator(); i.hasNext();) {
				SessionAttribute sa = (SessionAttribute)i.next();
				writeSessionAttribute(sa);
			}
			
			writer.writeEndElement();	//report:session

			writer.flush();
			writer.close();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		
		return output.toString();
	}

	private void writeSessionAttribute(SessionAttribute sa) throws XMLStreamException {
		writer.writeStartElement("attribute");
		
		writer.writeStartElement("name");
		writer.writeCharacters(sa.getName());
		writer.writeEndElement();
		
		writer.writeStartElement("object_type");
		writer.writeCharacters(sa.getObjectType());
		writer.writeEndElement();
		
		writer.writeStartElement("to_string");
		writer.writeCharacters(sa.getToString());
		writer.writeEndElement();
		
		writer.writeEndElement();
	}
	
	private void writeElement(String localName, Date data) throws XMLStreamException {
		writeElement(localName, this.format(data));
	}
	
	private void writeElement(String localName, int data) throws XMLStreamException {
		writeElement(localName, Integer.toString(data));
	}
	
	private void writeElement(String localName, String data) throws XMLStreamException {
		writer.writeStartElement(localName);
		writer.writeCharacters(data);
		writer.writeEndElement();
	}
}
