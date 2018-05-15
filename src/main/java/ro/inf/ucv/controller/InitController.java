package ro.inf.ucv.controller;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import ro.inf.ucv.entity.Document;
import ro.inf.ucv.entity.Record;
import ro.inf.ucv.wrapper.SearchModel;

/**
 * Handles requests for the application home page.
 */
@Controller
public class InitController extends BaseController {

	private static final Logger logger = Logger.getLogger(InitController.class);

	protected AtomicInteger pageNumber = new AtomicInteger(0);

	protected AtomicLong extractedRecords = new AtomicLong(0);

	protected int pageSize = 10000;

	@GetMapping("/init")
	public void init() throws Exception {
		if (this.documentService.count() == 0) {
			Long numberOfRecords = this.recordService.count();
			SearchModel searchModel = new SearchModel();
			searchModel.setPageSize(this.pageSize);
			while (this.extractedRecords.get() <= numberOfRecords) {
				System.out.println("Extracted: " + this.extractedRecords.get() + " records from: " + numberOfRecords);
				long startRetrieve = System.currentTimeMillis();
				searchModel.setPageNumber(this.pageNumber.get());
				Page<Record> records = this.recordService.findAll(searchModel);
				System.out.println(System.currentTimeMillis() - startRetrieve + " miliseconds to retrieve.");
				System.out.println("-----------------------------------");
				if (records != null && records.hasContent()) {
					List<Document> documents = new ArrayList<Document>();
					for (Record record : records.getContent()) {
						String recordText = record.getRecord_full();
						try {
							recordText = StringEscapeUtils.unescapeJava(recordText);
							DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
							DocumentBuilder builder = factory.newDocumentBuilder();
							org.w3c.dom.Document xmlDocument = builder
									.parse(new InputSource(new StringReader(recordText)));
							Element rootElement = xmlDocument.getDocumentElement();
							String title = getNodeValue(rootElement, "IDR/TITLE");
							if (title != null && !title.isEmpty()) {
								String contentUrl = getNodeValue(rootElement, "IDR/IDENTIFIER[@scheme='URL']");
								String language = getNodeValue(rootElement, "IDR/LANGUAGE");
								documents.add(new Document(null, title, contentUrl, language));
							}
						} catch (Exception e) {
							logger.error(e);
						}
					}
					this.documentService.saveAll(documents);
					this.extractedRecords.addAndGet(records.getNumberOfElements());
					this.pageNumber.incrementAndGet();
				}
			}
		}
	}

	private static String getNodeValue(Element rootElement, String titleExpression) {
		String value = null;
		try {
			XPath xPath = XPathFactory.newInstance().newXPath();
			Node node = (Node) xPath.compile(titleExpression).evaluate(rootElement, XPathConstants.NODE);
			if (node != null) {
				value = node.getTextContent();
			}
		} catch (Exception e) {
			logger.error(e);
		}

		return value;
	}
}
