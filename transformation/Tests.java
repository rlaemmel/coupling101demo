package transformation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.junit.Test;

public class Tests {
	
	private static Document load(String ifile) throws JDOMException, IOException {
		return new SAXBuilder().build(new File(ifile));
	}
	
	private static void saveAs(Document doc, String ofile) throws IOException {
		FileWriter writer = new FileWriter(ofile);
        new XMLOutputter().output(doc, writer);
        writer.flush();
        writer.close();		
	}			
	
	private static final Namespace ns = 
			Namespace.getNamespace("http://www.softlang.org/company.xsd");
	
	@Test
	public void testInstanceLevel() throws JDOMException, IOException {
		Document doc = load("input" + File.separator + "sampleCompany.xml");
		RenameElementName.instanceLevel(ns, "salary", "income", doc);
		saveAs(doc, "output" + File.separator + "sampleCompany.xml");
	}

	@Test
	public void testTypeLevel() throws JDOMException, IOException {
		Document doc = load("input" + File.separator + "Company.xsd");
		RenameElementName.typeLevel(ns, "salary", "income", doc);
		saveAs(doc, "output" + File.separator + "Company.xsd");
	}

	@Test
	public void testProgramLevel1() throws JDOMException, IOException {
		Document doc = load("input" + File.separator + "cut.xslt");
		RenameElementName.programLevel(ns, "salary", "income", doc);
		saveAs(doc, "output" + File.separator + "cut.xslt");
	}

	@Test
	public void testProgramLevel2() throws JDOMException, IOException {
		Document doc = load("input" + File.separator + "total.xslt");
		RenameElementName.programLevel(ns, "salary", "income", doc);
		saveAs(doc, "output" + File.separator + "total.xslt");
	}

	@Test
	public void testProgramLevel3() throws JDOMException, IOException {
		Document doc = load("input" + File.separator + "depth.xslt");
		RenameElementName.programLevel(ns, "salary", "income", doc);
		saveAs(doc, "output" + File.separator + "depth.xslt");
	}	
}
