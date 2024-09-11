package com.example.Product.service;

import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.example.Product.model.Price;
import com.example.Product.model.Product;
import com.example.Product.repo.PriceRepository;
import com.example.Product.repo.ProductRepository;

import lombok.AllArgsConstructor;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;

@Service
@AllArgsConstructor
public class DataService {

    private final ProductRepository productRepository;
    private final PriceRepository priceRepository;

    public void parseAndStoreXml(InputStream xmlInputStream) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlInputStream);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("row");

            for (int temp = 0; temp < nodeList.getLength(); temp++) {
                Node node = nodeList.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    // Parse XML and store data into entities
                    Product product = new Product();
                    product.setProductName(getTagValue("title", element));
                    product.setProductBrand(getTagValue("brand", element));
                    product.setProductCategory(getTagValue("category", element));
                    product.setProductUrl(getTagValue("url", element));

                    productRepository.save(product);

                    Price price = new Price();
                    price.setProductId(product.getId().toString()); // Link to the product

                    // Assuming that "prices" and "dates" in XML are comma-separated strings
                    String pricesString = getTagValue("prices", element);
                    String datesString = getTagValue("dates", element);

                    // Convert comma-separated strings to lists
                    List<Double> values = Arrays.stream(pricesString.split(","))
                            .map(Double::parseDouble)
                            .collect(Collectors.toList());
                    List<String> dateList = Arrays.asList(datesString.split(","));

                    price.setValues(values);
                    price.setDates(dateList);
                    price.setId(Uuids.timeBased());         //new - set a time based uuid to id before saving so it is unique

                    priceRepository.save(price);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }
    }

    private String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }
}
