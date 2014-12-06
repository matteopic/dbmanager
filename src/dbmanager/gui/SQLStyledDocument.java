package dbmanager.gui;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import dbmanager.Word;

public class SQLStyledDocument extends DefaultStyledDocument{

	/**
     * 
     */
    private static final long serialVersionUID = 2017804069173618561L;
    public SQLStyledDocument(){
		keywords = new ArrayList<String>();
		sfunctions = new ArrayList<String>();
		nfunctions = new ArrayList<String>();
		types = new ArrayList<String>();

		keywords.add("*");
		keywords.add("alter");
		keywords.add("and");
		keywords.add("create");
		keywords.add("default");
		keywords.add("from");
		keywords.add("is");
		keywords.add("like");
		keywords.add("not");
		keywords.add("null");
		keywords.add("select");
		keywords.add("table");
		keywords.add("where");
		keywords.add("group");
		keywords.add("by");
		keywords.add("left");
		keywords.add("right");
		keywords.add("inner");
		keywords.add("outer");
		keywords.add("join");
		keywords.add("order");
		keywords.add("sum");
		keywords.add("count");
		keywords.add("as");



		kas = new SimpleAttributeSet();
		kas.addAttribute(StyleConstants.FontConstants.Bold, new Boolean(true));
		kas.addAttribute(StyleConstants.ColorConstants.Foreground, Color.BLUE);

		nas = new SimpleAttributeSet();
		nas.addAttribute(StyleConstants.FontConstants.Bold, new Boolean(true));
		nas.addAttribute(StyleConstants.ColorConstants.Foreground, new Color(0,128,0));

		fas = new SimpleAttributeSet();
		fas.addAttribute(StyleConstants.FontConstants.Bold, new Boolean(true));
		fas.addAttribute(StyleConstants.ColorConstants.Foreground, new Color(128,0,128));

		type = new SimpleAttributeSet();
		type.addAttribute(StyleConstants.FontConstants.Bold, new Boolean(true));
		type.addAttribute(StyleConstants.ColorConstants.Foreground, Color.ORANGE);

		commento = new SimpleAttributeSet();
		commento.addAttribute(StyleConstants.ColorConstants.Foreground, new Color(0,128,0));

		string = new SimpleAttributeSet();
		string.addAttribute(StyleConstants.ColorConstants.Foreground, new Color(0,128, 128));

		das = new SimpleAttributeSet();
	}


	public void insertString(int offs, String str, AttributeSet a)throws BadLocationException{
		super.insertString(offs, str, a);
		colorize();
	}


	public void colorize()throws BadLocationException{
		Word[]words = getWords(getText(0, getLength()) );
		Word word;
		AttributeSet as;
		String txt;
		int txtLength;

		for(int i = 0; i < words.length; i++){
			word = words[i];
			txt = word.getWord();
			txtLength = txt.length();
			parse(word);
			as = getAttributeSet(word);

			if(as != null){
				setCharacterAttributes(word.getOffset(), txtLength, as, true);
			}
		}

	}

	/*public void replace(int offset, int len, String text, AttributeSet attrs)throws BadLocationException{
		System.out.println("Replace " + offset + " " + len +" "+text);
		int length = getLength();
		if (lineCommentStartIndex > length)lineCommentStartIndex = -1;
		if (lineCommentEndIndex > length)lineCommentEndIndex = length;
		if (multiLineCommentStartIndex > length)multiLineCommentStartIndex = -1;
		if (multiLineCommentEndIndex > length) multiLineCommentEndIndex = length;
		super.remove(offset, len);
		colorize();
	}*/


	public Word[] getWords(String from){
		ArrayList<Word> words = new ArrayList<Word>();

		int offset = 0;
		int length = from.length();

		int wstart, wend;
		String word = null;
		while (offset < length){
			wstart = findWordStart(from, offset);
			wend = findWordEnd(from, wstart+1);

			word = from.substring( wstart, wend );
			words.add( new Word(word, wstart) );
			offset = wend + 1;
		}

		length = words.size();
		Word[]ret = new Word[length];
		words.toArray(ret);
		return ret;
	}


	private int findWordStart(String txt, int offset){
		char[]chars = txt.toCharArray();
		//System.out.println("fws " +chars[offset]);

		if (isSeparator(chars[offset]) && offset+1 < chars.length)return findWordStart(txt, offset+1);

//		int length = chars.length;
		for (int i = offset; i >= 0; i--){
			if (isSeparator( chars[i] ))return i+1;
		}
		return 0;
	}


	private int findWordEnd(String txt, int offset){
		char[]chars = txt.toCharArray();
		//System.out.println("fwe " +chars[offset]);

		int length = chars.length;
		for (int i = offset; i < length; i++){
			if (isSeparator( chars[i] ))return i;
		}
		return length;
	}


	private boolean isSeparator(char chr){
		if (chr == '\n')return true;
		if (chr == '\t')return true;
		if (chr == ' ')return true;
		if (chr == '.')return true;
		if (chr == '=')return true;
		return false;
	}

	private boolean isNumeric(String val){
		try{ Long.parseLong(val); return true; }catch(Exception e){}
		try{ Double.parseDouble(val); return true; }catch(Exception e){}
		return false;
	}

	public AttributeSet getAttributeSet(Word theWord)throws BadLocationException{
		String word = theWord.getWord();
		parse(theWord);
		if (isInComment(theWord))return commento;
/*

		if (word.startsWith(singleLineComment) || word.startsWith(singleLineCommentAlt))lineCommentStartIndex = theWord.getOffset();
		if (word.startsWith("'"))stringStartIndex = theWord.getOffset();
		if (word.startsWith(multiLineCommentStart))multiLineCommentStartIndex = theWord.getOffset();
		if (word.endsWith(multiLineCommentEnd)){
			multiLineCommentStartIndex = -1;
			return commento;
		}

		if (multiLineCommentStartIndex != -1)return commento;
		if (stringStartIndex != -1 && word.endsWith("'")){
			return string;

		}
		if (lineCommentStartIndex != -1){
			//Prendo tutto il testo da dove inizia il commento di inizio riga
			String txt = getText(lineCommentStartIndex, getLength() - lineCommentStartIndex);

			//Prendo il punto in cui finisce la riga
			int index = txt.indexOf("\n") + lineCommentStartIndex;
			if (index == -1)index = getLength() - lineCommentStartIndex;

			if (theWord.getOffset() < index)return commento;
			else lineCommentStartIndex = -1;
		}
*/

		if(keywords.contains(word.toLowerCase()))return kas;
		else if(nfunctions.contains(word.toLowerCase()))return fas;
		else if(sfunctions.contains(word.toLowerCase()))return fas;
		else if(types.contains(word.toLowerCase()))return type;
		else if(isNumeric(word)) return nas;
		return das;
	}

	private boolean isInComment(Word theWord){
		int offset = theWord.getOffset();
		System.out.println(lineCommentStartIndex + " -- " + offset +" -- " +  lineCommentEndIndex);
		if (lineCommentStartIndex <= offset && offset <= lineCommentEndIndex)return true;
		else if (multiLineCommentStartIndex <= offset && offset <= multiLineCommentEndIndex)return true;
		return false;
	}

	private void parse(Word theWord)throws BadLocationException{
		String word = theWord.getWord();

		if (lineCommentStartIndex == -1){
			int slcs = word.indexOf(singleLineComment);
			if (slcs == -1)slcs = word.indexOf(singleLineCommentAlt);

			//Se nella parola ricevuta è contenuto l'inizio del commento su una sola riga cerco la fine della riga
			if (slcs != -1){
				lineCommentStartIndex = theWord.getOffset() + slcs;
				lineCommentEndIndex = getText().substring(lineCommentStartIndex).indexOf("\n");
				if (lineCommentEndIndex == -1)lineCommentEndIndex = getLength();
			}
		}

		if (multiLineCommentStartIndex == -1){
			int mlcs = word.indexOf(multiLineCommentStart);

			//Se nella parola ricevuta è contenuto l'inizio del commento su una sola riga cerco la fine del commento
			if (mlcs != -1){
				multiLineCommentStartIndex = theWord.getOffset() + mlcs;
				multiLineCommentEndIndex = getText().substring(multiLineCommentStartIndex).indexOf("*/");
				if (multiLineCommentEndIndex == -1)multiLineCommentEndIndex = getLength();
			}
		}

		if (stringStartIndex == -1){
			int str = word.indexOf(stringValue);

			//Se nella parola ricevuta è contenuto l'inizio della stringa cerco la fine
			if (str != -1){
				stringStartIndex = theWord.getOffset() + str;
				stringEndIndex = getText().substring(stringStartIndex).indexOf("'");
				if (stringEndIndex == -1)stringEndIndex = getLength();
			}
		}

		int offset = theWord.getOffset();
		if (offset > lineCommentEndIndex){
			lineCommentStartIndex = -1;
			lineCommentEndIndex = -1;
		}

		if (offset > multiLineCommentEndIndex){
			multiLineCommentStartIndex = -1;
			multiLineCommentEndIndex = -1;
		}

	}

	public void addKeyword(String keyword){
		keywords.add(keyword.toLowerCase());
	}

	public void addNumericFunction(String name){
		nfunctions.add(name.toLowerCase());
	}

	public void addStringFunction(String name){
		sfunctions.add(name.toLowerCase());
	}

	public void addDataType(String name){
		types.add(name.toLowerCase());
	}

	private String getText()throws BadLocationException{
		return getText(0, getLength());
	}

	/**

		Imposta la stringa che identifica l'inizio di un commento su una singola riga. "#" � il valore di default

	*/

	public void setSingleLineComment(String singleLineComment){ this.singleLineComment = singleLineComment; }


	/**
		Imposta la stringa che identifica il valore alternativo all'inizio di un commento su una singola riga. "--" � il valore di default
	*/
	public void setSingleLineCommentAlt(String singleLineCommentAlt){ this.singleLineCommentAlt = singleLineCommentAlt; }


	/**
		Imposta la stringa che identifica l'inzio di un commento su pi� righe. "/*" � il valore di default
	*/
	public void setMultiLineCommentStart(String multiLineCommentStart){ this.multiLineCommentStart = multiLineCommentStart; }


	/**
		Imposta la stringa che identifica la fine di un commento su pi� righe.
	*/
	public void setMultiLineCommentEnd(String multiLineCommentEnd){ this.multiLineCommentEnd = multiLineCommentEnd; }


	public String getSingleLineComment(){ return singleLineComment; }


	public String getSingleLineCommentAlt(){ return singleLineCommentAlt; }

	public String getMultiLineCommentStart(){ return multiLineCommentStart; }

	public String getMultiLineCommentEnd(){ return multiLineCommentEnd; }

	private ArrayList<String> keywords, sfunctions, nfunctions, types;
	private SimpleAttributeSet kas, das, nas, fas, commento, string, type;


	private int lineCommentStartIndex = -1;
	private int lineCommentEndIndex = -1;
	private int multiLineCommentStartIndex = -1;
	private int multiLineCommentEndIndex = -1;

	private int stringStartIndex = -1;
	private int stringEndIndex = -1;

	private String singleLineComment = "#";
	private String singleLineCommentAlt = "--";
	private String multiLineCommentStart = "/*";
	private String multiLineCommentEnd = "*/";
	private String stringValue = "'";
}