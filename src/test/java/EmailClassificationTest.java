/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import au.com.bytecode.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.deb.classification.Classifier;

import org.deb.emailclassification.EmailClassification;
import org.deb.model.Classified;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author debmalyajash
 */
public class EmailClassificationTest {

    private static final Logger LOGGER = Logger.getLogger(EmailClassificationTest.class);

    private static List<String[]> trainingRecords;

    public EmailClassificationTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        try (CSVReader reader = new CSVReader(new FileReader("./src/test/resource/email-classification-training.csv"))) {
            trainingRecords = reader.readAll();
        } catch (FileNotFoundException ex) {
            LOGGER.error(ex.getMessage(), ex);
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        Assert.assertNotNull(trainingRecords);
        Assert.assertEquals(4890, trainingRecords.size());
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public final void testRemoveXMLTags() {
        EmailClassification classifier = new EmailClassification();
        Assert.assertEquals("", classifier.removeXMLTags("<html><body></body></html>"));
        Assert.assertEquals("", classifier.removeXMLTags("<html><body><p></p></body></html>"));
        Assert.assertEquals("", classifier.removeXMLTags("<html><body></body></html>"));
        Assert.assertEquals("", classifier.removeXMLTags("<html><body>></body></html>"));
        Assert.assertEquals("", classifier.removeXMLTags("<<html><body>></body></html>"));
        Assert.assertEquals("No XML Tag", classifier.removeXMLTags("<html><body><p>No XML Tag</p></body></html>"));
        Assert.assertEquals("No XML Tag", classifier.removeXMLTags("No XML Tag"));
    }

    @Test
    public final void testExample() {
        EmailClassification classifier = new EmailClassification();

        for (int i = 0; i < trainingRecords.size(); i++) {
            classifier.train(0, trainingRecords.get(i));
        }
        LOGGER.debug("Category :" + EmailClassification.getCategory());
    }

    @Test
    public final void testWordFrequency() {
        EmailClassification classifier = new EmailClassification();
        for (int i = 0; i < trainingRecords.size(); i++) {
//            LOGGER.debug("======================================================");
//            LOGGER.debug("Email message :" + trainingRecords.get(i)[2]);
            Map<String, Integer> wordFrequency = classifier.countWordFrequency(trainingRecords.get(i)[2].toLowerCase());
            LOGGER.debug((i + 1)+") " + classifier.removeXMLTags(trainingRecords.get(i)[2]));
            Assert.assertNotNull(wordFrequency);
//            Assert.assertTrue(!wordFrequency.isEmpty());
            Iterator<Map.Entry<String, Integer>> wordSetIterators = wordFrequency.entrySet().iterator();
            while (wordSetIterators.hasNext()) {
                Map.Entry<String, Integer> nextEntry = wordSetIterators.next();
//            LOGGER.debug(nextEntry.getKey()+" " + nextEntry.getValue());
            }
//            LOGGER.debug("======================================================");
        }
    }

    @Test
    public void restClassification() {
        Classifier classifier = new Classifier();
        EmailClassification emailClassifer = new EmailClassification();
        for (int i = 0; i < trainingRecords.size(); i++) {
            String theBody = trainingRecords.get(i)[2].toLowerCase();
            theBody = emailClassifer.removeXMLTags(theBody);
            List<Classified> classificationList = classifier.classify(theBody, 0);
            LOGGER.debug("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            LOGGER.debug(theBody);
            LOGGER.debug(classificationList);
            LOGGER.debug("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
    }
}
