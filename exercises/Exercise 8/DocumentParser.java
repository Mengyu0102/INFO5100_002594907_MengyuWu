// You need to install Jackson dependency for JSON part (e.g., Maven or Gradle)
// If you cannot use external libraries, you would need to use org.json or similar,
// or simpler, use Gson, which is often considered simpler for beginners.
// We proceed with Jackson as it is standard in enterprise Java.

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.Arrays;
import java.util.List;

// Jackson Imports
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class DocumentParser {

    private static final String XML_FILE = "books.xml";
    private static final String JSON_FILE = "books.json";

    public static void main(String[] args) {
        // --- Setup: Create the sample files (Assumes they are created successfully) ---
        // In a real IDE, you would place these files in the project root.

        System.out.println("=================================================");
        System.out.println("            XML PARSING AND MODIFICATION         ");
        System.out.println("=================================================");
        try {
            // 1. Initial XML Parse and Print
            System.out.println("\n--- Initial XML Parse ---");
            Document xmlDoc = parseXmlDocument(XML_FILE);
            printXmlBooks(xmlDoc);

            // 2. Programmatically Add New Entry
            addNewBookToXml(xmlDoc, "The Future of AI", 2025, 999, Arrays.asList("Elon Turing"));

            // 3. Re-parse and Print to Verify
            System.out.println("\n--- XML Parse After Adding New Book ---");
            // Optional: Save the modified XML back to file
            saveXmlDocument(xmlDoc, XML_FILE);
            // Re-parse from memory/saved file (for strict verification)
            printXmlBooks(xmlDoc);

        } catch (Exception e) {
            System.err.println("XML Parsing Error: " + e.getMessage());
        }

        System.out.println("\n\n=================================================");
        System.out.println("            JSON PARSING AND MODIFICATION        ");
        System.out.println("=================================================");
        try {
            // 1. Initial JSON Parse and Print
            System.out.println("\n--- Initial JSON Parse ---");
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(new File(JSON_FILE));
            printJsonBooks(rootNode);

            // 2. Programmatically Add New Entry
            addNewBookToJson((ObjectNode) rootNode, mapper, "Quantum Computing 101", 2024, 550, Arrays.asList("Dr. Qubit"));

            // 3. Re-parse and Print to Verify
            System.out.println("\n--- JSON Parse After Adding New Book ---");
            // Optional: Save the modified JSON back to file
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(JSON_FILE), rootNode);
            printJsonBooks(rootNode);

        } catch (Exception e) {
            System.err.println("JSON Parsing Error (Ensure Jackson is configured and " + JSON_FILE + " exists): " + e.getMessage());
        }
    }

    // --- XML UTILITY METHODS ---

    private static Document parseXmlDocument(String fileName) throws ParserConfigurationException, SAXException, java.io.IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new File(fileName));
    }

    private static void printXmlBooks(Document doc) {
        NodeList bookList = doc.getElementsByTagName("Book");
        for (int i = 0; i < bookList.getLength(); i++) {
            Node bookNode = bookList.item(i);
            if (bookNode.getNodeType() == Node.ELEMENT_NODE) {
                Element bookElement = (Element) bookNode;
                String title = getElementValue(bookElement, "title");
                String year = getElementValue(bookElement, "publishedYear");
                String pages = getElementValue(bookElement, "numberOfPages");
                String authors = getAuthorsXml(bookElement);

                System.out.printf("Book %d: Title='%s', Year=%s, Pages=%s, Authors=[%s]%n",
                        i + 1, title, year, pages, authors);
            }
        }
    }

    private static String getElementValue(Element parent, String tagName) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        if (nodeList != null && nodeList.getLength() > 0) {
            Element element = (Element) nodeList.item(0);
            return element.getTextContent();
        }
        return "N/A";
    }

    private static String getAuthorsXml(Element bookElement) {
        NodeList authorNodes = bookElement.getElementsByTagName("author");
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < authorNodes.getLength(); j++) {
            sb.append(authorNodes.item(j).getTextContent());
            if (j < authorNodes.getLength() - 1) {
                sb.append("; ");
            }
        }
        return sb.toString();
    }

    private static void addNewBookToXml(Document doc, String title, int year, int pages, List<String> authors) {
        Element bookShelf = (Element) doc.getElementsByTagName("BookShelf").item(0);

        Element newBook = doc.createElement("Book");

        // title
        Element titleElem = doc.createElement("title");
        titleElem.setTextContent(title);
        newBook.appendChild(titleElem);

        // publishedYear
        Element yearElem = doc.createElement("publishedYear");
        yearElem.setTextContent(String.valueOf(year));
        newBook.appendChild(yearElem);

        // numberOfPages
        Element pagesElem = doc.createElement("numberOfPages");
        pagesElem.setTextContent(String.valueOf(pages));
        newBook.appendChild(pagesElem);

        // authors
        Element authorsElem = doc.createElement("authors");
        for (String authorName : authors) {
            Element authorElem = doc.createElement("author");
            authorElem.setTextContent(authorName);
            authorsElem.appendChild(authorElem);
        }
        newBook.appendChild(authorsElem);

        bookShelf.appendChild(newBook);
        System.out.println("-> Successfully added new book '" + title + "' to XML document in memory.");
    }

    private static void saveXmlDocument(Document doc, String fileName) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        // For pretty output
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(fileName));
        transformer.transform(source, result);
    }

    // --- JSON UTILITY METHODS (Requires Jackson) ---

    private static void printJsonBooks(JsonNode rootNode) {
        JsonNode bookShelf = rootNode.path("BookShelf");
        if (bookShelf.isArray()) {
            ArrayNode bookList = (ArrayNode) bookShelf;
            for (int i = 0; i < bookList.size(); i++) {
                JsonNode book = bookList.get(i);
                String title = book.path("title").asText();
                int year = book.path("publishedYear").asInt();
                int pages = book.path("numberOfPages").asInt();
                String authors = getAuthorsJson(book.path("authors"));

                System.out.printf("Book %d: Title='%s', Year=%d, Pages=%d, Authors=[%s]%n",
                        i + 1, title, year, pages, authors);
            }
        }
    }

    private static String getAuthorsJson(JsonNode authorsNode) {
        if (authorsNode.isArray()) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < authorsNode.size(); i++) {
                sb.append(authorsNode.get(i).asText());
                if (i < authorsNode.size() - 1) {
                    sb.append("; ");
                }
            }
            return sb.toString();
        }
        return "N/A";
    }

    private static void addNewBookToJson(ObjectNode rootNode, ObjectMapper mapper, String title, int year, int pages, List<String> authors) {
        ArrayNode bookShelf = (ArrayNode) rootNode.path("BookShelf");

        ObjectNode newBook = mapper.createObjectNode();
        newBook.put("title", title);
        newBook.put("publishedYear", year);
        newBook.put("numberOfPages", pages);

        ArrayNode authorsArray = mapper.createArrayNode();
        for (String author : authors) {
            authorsArray.add(author);
        }
        newBook.set("authors", authorsArray);

        bookShelf.add(newBook);
        System.out.println("-> Successfully added new book '" + title + "' to JSON document in memory.");
    }
}