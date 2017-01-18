package edu.smu.yl.parser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.PTBTokenizer;

/**Token sentences in a file or a String sentences
 * @author liuyang
 * @mail yangliuyx@gmail.com
 */
public class StanfordTokenizer {

	  public static File tokenizeFile(File file) throws IOException {
		  String tokenizedFileName = file.getAbsolutePath() + "_tokenized";
	      FileWriter tokenizedFileWriter = new FileWriter(tokenizedFileName);
	      DocumentPreprocessor dp = new DocumentPreprocessor(file.getAbsolutePath());
	      int CurrentSentIndex = 1;
	      int tokenizedSentCounter = 1;
	      for (List sentence : dp) {
	    	  for(int i = 0; i < sentence.size(); i++){
	    		  if(i == 0){
	    			  Pattern p = Pattern.compile("[0-9]+");  
					  if(p.matcher(sentence.get(i).toString()).matches()){
			    		  tokenizedFileWriter.append(tokenizedSentCounter + "\t" + sentence.get(i) + "\t");
			    		  CurrentSentIndex = Integer.valueOf(sentence.get(i).toString());
					  } else {
						  System.out.println(tokenizedSentCounter + "\t" + CurrentSentIndex +"\t" + sentence.get(i) + " ");
			    		  tokenizedFileWriter.append(tokenizedSentCounter + "\t" + CurrentSentIndex +"\t" + sentence.get(i) + " ");
					  }
	    		  } else {
	        		  tokenizedFileWriter.append(sentence.get(i) + " ");
	    		  } 
	    	 }
	    	tokenizedSentCounter++;
	        tokenizedFileWriter.append("\n");
	        tokenizedFileWriter.flush();
	      }
	      return new File(tokenizedFileName);
	  }
	  
	  public static List<String> tokenizeSents(String sents){
		  Reader reader = new StringReader(sents);
		  DocumentPreprocessor dp = new DocumentPreprocessor(reader);

		  List<String> sentenceList = new LinkedList<String>();
		  Iterator<List<HasWord>> it = dp.iterator();
		  while (it.hasNext()) {
		     StringBuilder sentenceSb = new StringBuilder();
		     List<HasWord> sentence = it.next();
		     for (HasWord token : sentence) {
		        if(sentenceSb.length()>1) {
		           sentenceSb.append(" ");
		        }
		        sentenceSb.append(token);
		     }
		     sentenceList.add(sentenceSb.toString());
		  }
		  return sentenceList;
	  }
}