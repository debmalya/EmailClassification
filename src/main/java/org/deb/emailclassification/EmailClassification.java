/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.deb.emailclassification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 *
 * @author debmalyajash
 */
public class EmailClassification {

    private static final Set<String> STOP_WORDS = new HashSet<>();

    private static final Set<String> CLASSIFICATION = new HashSet<>();

    private static Set<String> category = new HashSet<>();

    public static Set<String> getCategory() {
        return category;
    }

    public static Map<String, List<String>> getCategoryMapping() {
        return categoryMapping;
    }

    public static Map<String, List<String>> getSubjectMapping() {
        return subjectMapping;
    }
//    Category is the key and correspondging keywords, tokens.
    private static Map<String, List<String>> categoryMapping = new HashMap<>();
//    Each subject token is the key and  category list is the value.
    private static Map<String, List<String>> subjectMapping = new HashMap<>();
    
    private String theBody;

    static {
        init();
    }

    /**
     * This function will be called only once per test case, and any integer
     * value can be returned.
     *
     * @param testType - 0, 1, or 2, to indicate Example, Provisional, or System
     * test.
     * @param trainingData - each string contains a single record, and has 4 or
     * more tokens, comma-separated.that character fields could contain commas
     * and, to avoid incorrect interpretation, the content of these columns
     * starts and ends with double quotes.
     *
     * Column 1 - ID. Column 2 - Subject. Column 3 - Body. Column 4 - Category.
     *
     *
     * @return any integer value can be returned.
     */
    public int train(int testType, String[] trainingData) {
        init();
        switch (testType) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            default:
                break;
        }
        if (trainingData.length > 3) {
            category.add(trainingData[3]);
            
        }
        return 0;
    }

    /**
     * Receives a string msgData with data from a single message and timeLeft in
     * milliseconds. This method will be called multiple times, one for each
     * testing message.
     *
     * @param msgData The format of msgData is the same as a single item of
     * trainingData, except that it contains only the first 3 columns (ID,
     * subject, body).
     * @param timeLeft - in milli seconds. To help in time control. Program must
     * complete within this time limit.
     * @return String[] should contain 6 or more elements. The first 6 should be
     * 3 pairs of categories and the confidence level of each option. Then for
     * each extracted data field found by your solution, it should return a pair
     * of strings with the field name and the field value. So the expected
     * returned should looks like this:
     * "CAT1","CONF1","CAT2","CONF2","CAT3","CONF3"[,"FIELD NAME 1","FIELD VALUE
     * 1"]...[,"FIELD NAME N","FIELD VALUE N"] Confidence should be numeric
     * values in the range between 0 and 1 inclusive.
     */
    public String[] classify(String msgData, int timeLeft) {
        long startTime = System.currentTimeMillis();
        List<String> classificationList = new ArrayList<>();
        String modifiedData = removeXMLTags(msgData);
        return classificationList.toArray(new String[0]);
    }

    /**
     * To remove XML tags.
     *
     * @param xmlString
     * @return string without XML tag.
     */
    public String removeXMLTags(final String xmlString) {
        boolean isXMLTag = false;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < xmlString.length(); i++) {
            char c = xmlString.charAt(i);
            if (c == '<') {
                isXMLTag = true;
            }
            if (!isXMLTag && c != '>') {
                sb.append(c);
            }
            if (c == '>') {
                isXMLTag = false;
                sb.append(" ");
            }
        }
        return sb.toString().trim();
    }

    /**
     * To remove XML tags.
     *
     * @param xmlString
     * @return string without XML tag.
     */
    public Map<String, Integer> countWordFrequency(final String xmlString) {
        boolean isXMLTag = false;
        SortedMap<String, Integer> frequencyMap = new TreeMap<>();
        StringBuilder sb = new StringBuilder();
        StringBuilder eachWord = new StringBuilder();
        for (int i = 0; i < xmlString.length(); i++) {
            char c = xmlString.charAt(i);
            if (c == '<') {
                isXMLTag = true;
            }
            if (!isXMLTag && c != '>') {
                sb.append(c);
                if (c == ' ') {
//                    End of a word
                    String word = eachWord.toString();
                    if (!STOP_WORDS.contains(word)) {
                        Integer count = frequencyMap.get(word);
                        if (count == null) {
                            count = 0;
                        }
                        count++;
                        frequencyMap.put(word, count);
                    }
                    eachWord.delete(0, eachWord.length());
                } else {
                    eachWord.append(c);
                }
            }
            if (c == '>') {
                isXMLTag = false;
            }
        }
        theBody = sb.toString();
        return frequencyMap;
    }

    public EmailClassification() {
        init();
    }

    private static void init() {
        STOP_WORDS.add("&amp;");
        STOP_WORDS.add("&gt;");
        STOP_WORDS.add("&lt;");
        STOP_WORDS.add("of");
        STOP_WORDS.add("an");
        STOP_WORDS.add("the");
        STOP_WORDS.add("into");
        STOP_WORDS.add("at");
        STOP_WORDS.add("a");
        STOP_WORDS.add("and");

        CLASSIFICATION.add("AP processing errors");
        CLASSIFICATION.add("Audit request");
        CLASSIFICATION.add("Bank queries");
        CLASSIFICATION.add("Bank rejections");
        CLASSIFICATION.add("Bank rejections");
        CLASSIFICATION.add("BMG");
        CLASSIFICATION.add("CBCP escalation");
        CLASSIFICATION.add("CBCP request");
        CLASSIFICATION.add("Claim status");
        CLASSIFICATION.add("Concur issues");
        CLASSIFICATION.add("Contract Related");
        CLASSIFICATION.add("Credit Card Maintenance");
        CLASSIFICATION.add("FYI");
        CLASSIFICATION.add("GL requests/ PC uplifts");
        CLASSIFICATION.add("Invoice Status");
        CLASSIFICATION.add("Invoices for scan");
        CLASSIFICATION.add("JBA/SMF Maintenance");
        CLASSIFICATION.add("Manual payment request");
        CLASSIFICATION.add("Matching Report");
        CLASSIFICATION.add("Missing documents/ supporting documentation");
        CLASSIFICATION.add("Non AP documents Non PO Invoices (coding information, VAT)");
        CLASSIFICATION.add("OIR");
        CLASSIFICATION.add("Open Item reports");
        CLASSIFICATION.add("Other, out of office messages");
        CLASSIFICATION.add("Payment confirmation");
        CLASSIFICATION.add("Payment reminder");
        CLASSIFICATION.add("Payments (MPR, urgent payment requests, BMG)");
        CLASSIFICATION.add("PO related");
        CLASSIFICATION.add("Process updates");
        CLASSIFICATION.add("Proof of payment");
        CLASSIFICATION.add("Recall");
        CLASSIFICATION.add("Two Way match report");
        CLASSIFICATION.add("Urgent payment request");
        CLASSIFICATION.add("Vendor master data");
        CLASSIFICATION.add("Vendor statement");
        CLASSIFICATION.add("Web Ex Trainings");
    }

}
