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
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import org.deb.emailclassification.EmailClassification;
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
    public final void testExample(){
        EmailClassification classifier = new EmailClassification();
        classifier.train(0, trainingRecords.get(0));
    }

    @Test
    public final void testWordFrequency() {
        EmailClassification classifier = new EmailClassification();
        LOGGER.debug("Email message :" + trainingRecords.get(0)[2]);
        Map<String, Integer> wordFrequency = classifier.countWordFrequence(trainingRecords.get(0)[2]);
        LOGGER.debug("After removing XML tags :" + classifier.removeXMLTags(trainingRecords.get(0)[2]));
        Assert.assertNotNull(wordFrequency);
        Assert.assertTrue(!wordFrequency.isEmpty());
        Iterator<Map.Entry<String, Integer>> wordSetIterators = wordFrequency.entrySet().iterator();
        while (wordSetIterators.hasNext()) {
            Map.Entry<String, Integer> nextEntry = wordSetIterators.next();
            LOGGER.debug(nextEntry.getKey()+" " + nextEntry.getValue());
        }
    }
}
