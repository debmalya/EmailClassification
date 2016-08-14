/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.deb.emailclassification;

import java.util.ArrayList;
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

    private static Set<String> stopWords = new HashSet<>();
    
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
    public Map<String, Integer> countWordFrequence(final String xmlString) {
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
                    if (!stopWords.contains(word)) {
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
        return frequencyMap;
    }

    public EmailClassification() {
        init();
    }

    private static void init() {
        stopWords.add("&amp;");
    }

}
