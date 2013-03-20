import ner.StanfordNERThrift;

import org.apache.thrift.TApplicationException;
import org.apache.thrift.TException;

import coref.StanfordCorefThrift;

import parser.StanfordParserThrift;

import CoreNLP.*;

import general.CoreNLPThriftUtil;

import java.util.ArrayList;
import java.util.List;


public class StanfordCoreNLPHandler implements StanfordCoreNLP.Iface 
{
	private StanfordParserThrift parser;
	private StanfordNERThrift ner;
	private StanfordCorefThrift coref;

    public StanfordCoreNLPHandler() 
    {
    	System.err.println("Initializing Parser...");
    	parser = new StanfordParserThrift("");
    	System.err.println("Initializing Named Entity Recognizer...");
    	ner = new StanfordNERThrift();
    	System.err.println("Initializing Coreference Resolver...");
    	coref = new StanfordCorefThrift();
    }

    /* Begin Stanford Parser methods */
    public List<ParseTree> parse_text(String text, List<String> outputFormat) throws TApplicationException
    {
    	if (outputFormat == null)
    	{
    		List<String> oF = new ArrayList<String>();
    		return parser.parse_text(text, oF);
    	}
    	return parser.parse_text(text, outputFormat);
    }

    public ParseTree parse_tokens(List<String> tokens, List<String> outputFormat) throws TApplicationException
    {
    	if (outputFormat == null)
    	{
    		List<String> oF = new ArrayList<String>();
    		return parser.parse_tokens(tokens, oF);
    	}
    	return parser.parse_tokens(tokens, outputFormat);
    }
    /* End Stanford Parser methods */
    
    /* Begin Stanford NER methods */
    public List<NamedEntity> get_entities_from_text(String text) throws TApplicationException
    {
    	//return ner.getNamedEntitiesFromText(text);
    	List<ParseTree> parseTreeObjects = parser.parse_text(text, null);
    	List<String> parseTrees = CoreNLPThriftUtil.ParseTreeObjectsToString(parseTreeObjects);
    	return ner.getNamedEntitiesFromTrees(parseTrees);
    }
    
    public List<NamedEntity> get_entities_from_tokens(List<String> tokens) throws TApplicationException
    {
    	ParseTree parseTreeObject = parser.parse_tokens(tokens, null);
    	List<String> parseTrees = new ArrayList<String>();
    	parseTrees.add(parseTreeObject.tree);
    	return ner.getNamedEntitiesFromTrees(parseTrees);
    }
    
    public List<NamedEntity> get_entities_from_trees(List<String> trees)
    {
    	return ner.getNamedEntitiesFromTrees(trees);
    }
    /* End Stanford NER methods */
    
    /* Begin Stanford Coref methods */
//    public List<String> resolve_coreferences_in_text(String text)
//    {
//    	List<ParseTree> parseTreeObjects = parser.parse_text(text, null);
//    	List<String> parseTrees = CoreNLPThriftUtil.ParseTreeObjectsToString(parseTreeObjects);
//    	return coref.getCoreferencesFromTrees(parseTrees, ner);
//    }
    
    public List<String> resolve_coreferences_in_trees(List<String> trees)
    {
    	return coref.getCoreferencesFromTrees(trees, ner);
    }
    /* End Stanford Coref methods */
    
    public void ping() 
    {
        System.out.println("ping()");
    }

    public void zip() 
    {
        System.out.println("zip()");
    }
}
