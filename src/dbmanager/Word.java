package dbmanager;

public class Word{
	public Word(String word, int offset){
		this.word = word;
		this.offset = offset;
	}

	public String getWord(){ return word; }

	public int getOffset(){ return offset; }

	private String word;
	private int offset;
}