package transformation;

import java.util.Iterator;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Attribute;
import org.jdom.Namespace;
import org.jdom.filter.ElementFilter;

/**
 * @author Ralf Lämmel
 * A coupled transformation element-name renaming.
 * The transformation can be applied at three levels:
 * - the instance level for XML documents.
 * - the type level for XML schemas.
 * - the program level for XSLT programs.
 */
public class RenameElementName {

	/**
	 * Element-name renaming at the instance level.
	 * @param ns namespace of old and new element name
	 * @param oldName old element name
	 * @param newName new element name
	 * @param doc XML document
	 */
	public static void instanceLevel(Namespace ns, String oldName, String newName, Document doc) {

		// Iterate over all elements with the old name
		Iterator<?> elems = 
				doc.getDescendants(new ElementFilter(oldName, ns));

		while (elems.hasNext()) {
			Element elem = (Element)(elems.next());
			
			// Rename to new name
			elem.setName(newName);
		}
	}

	/**
	 * Element-name renaming at the schema level.
	 * @param ns namespace of old and new element name
	 * @param oldName old element name
	 * @param newName new element name
	 * @param doc XML document (XSD)
	 */
	public static void typeLevel(Namespace ns, String oldName, String newName, Document doc) {

		// Interact with the XML Schema namespace
		Namespace xs = Namespace.getNamespace("http://www.w3.org/2001/XMLSchema");

		// Iterate over all xs:element elements
		Iterator<?> elems = 
				doc.getDescendants(new ElementFilter("element", xs));

		while (elems.hasNext()) {
			
			Element elem = (Element)(elems.next());
			
			// Handle global element declarations
			Attribute name = elem.getAttribute("name");
			Element parent = (Element)elem.getParent();
			if (parent.getName().equals("schema")
			&& parent.getNamespaceURI().equals(xs.getURI())
			&& name != null 
			&& name.getValue().equals(oldName)) {
				elem.setAttribute("name", newName);
				continue;
			}
			
			// Handle element references			
			Attribute ref = elem.getAttribute("ref");
			if (ref != null 
			&& ref.getValue().equals(oldName)) {
				elem.setAttribute("ref", newName);
				continue;
			}
		}
	}

	/**
	 * Element-name renaming at the program level.
	 * @param ns namespace of old and new element name
	 * @param oldName old element name
	 * @param newName new element name
	 * @param doc XML document (XSLT)
	 */
	public static void programLevel(Namespace ns, String oldName, String newName, Document doc) {
		
		// Lookup prefix for instance namespace
		for (Object o : doc.getRootElement().getAdditionalNamespaces()) {
			Namespace nsx = (Namespace)o;
			if (nsx.getURI().equals(ns.getURI())) {
				ns = nsx;
				break;
			}
		}
				
		// Interact with the XSL(T) namespace
		Namespace xsl = Namespace.getNamespace("http://www.w3.org/1999/XSL/Transform");
				
		// Iterate over all XSL(T) elements		
		Iterator<?> elems = 
				doc.getDescendants(new ElementFilter(xsl));
		
		while (elems.hasNext()) {
			
			Element elem = (Element)(elems.next());
			
			// Handle matches for the old element
			Attribute match = elem.getAttribute("match");
			if (match != null
			&& match.getValue().equals(ns.getPrefix() + ":" + oldName)) {
				elem.setAttribute("match", ns.getPrefix() + ":"+ newName);
				continue;
			}

			// Handle selections that refers to the old element
			Attribute select = elem.getAttribute("select");
			if (select != null) {
				String pattern = ns.getPrefix() + ":" + oldName;
				String oldValue = select.getValue();
				String newValue = oldValue.replaceAll(pattern, ns.getPrefix() + ":"+ newName);
				if (!newValue.equals(oldValue))
					elem.setAttribute("select", newValue);					
				continue;				
			}
		}
	}
}
