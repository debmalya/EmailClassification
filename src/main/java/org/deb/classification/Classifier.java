/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.deb.classification;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.deb.model.Classified;
import org.deb.model.EmbeddedToken;

/**
 *
 * @author debmalyajash
 */
public class Classifier {

    static Properties props = new Properties();

    static {
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse, ner");
    }
    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
    
    
    public List<Classified> classify(String text, int rating) {
		Annotation annotation = pipeline.process(text);
		List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
		List<Classified> classifiedList = new ArrayList<>();
		for (CoreMap sentence : sentences) {
			String sentiment = sentence.get(SentimentCoreAnnotations.SentimentClass.class);

			// Processing tokens
			String prevNeToken = "O";
			String currNeToken = "O";
			boolean newToken = true;
			List<CoreLabel> tokenList = sentence.get(TokensAnnotation.class);
			StringBuilder sb = new StringBuilder();
			List<EmbeddedToken> tokens = new ArrayList<>();
			for (CoreLabel token : tokenList) {
				currNeToken = token.get(NamedEntityTagAnnotation.class);
				String word = token.get(TextAnnotation.class);
				// Strip out "O"s completely, makes code below easier to
				// understand
				if (currNeToken != null && currNeToken.equals("O")) {
					// LOG.debug("Skipping '{}' classified as {}", word,
					// currNeToken);
					if (!prevNeToken.equals("O") && (sb.length() > 0)) {
						handleEntity(prevNeToken, sb, tokens);
						newToken = true;
					}
					continue;
				}

				if (newToken) {
					prevNeToken = currNeToken;
					newToken = false;
					sb.append(word);
					continue;
				}

				if (currNeToken != null && currNeToken.equals(prevNeToken)) {
					sb.append(" " + word);
				} else {
					// We're done with the current entity - print it out and
					// reset
					// TODO save this token into an appropriate ADT to return
					// for useful processing..
					handleEntity(prevNeToken, sb, tokens);
					newToken = true;
				}
				prevNeToken = currNeToken;

			}

			classifiedList.add(new Classified(sentiment, sentence, tokens, rating));

		}
		return classifiedList;

	}

    
    
    private void handleEntity(String inKey, StringBuilder inSb, List<EmbeddedToken> inTokens) {
		inTokens.add(new EmbeddedToken(inKey, inSb.toString()));
		inSb.setLength(0);
	}

}
