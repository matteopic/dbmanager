package dbmanager.autocompletion;
import javax.swing.ListCellRenderer;
/**
 * <p> The interface specifies the method to get a list of words
 * from a word.
 * <p>Title: AutoCompletionModel</p>
 * <p>Author: Marcello Valeri
 */

public interface AutoCompletionModel
{

/**
* <p> Returns the word List to populate the popup list. It takes
* the word currently on the left of the key char (usually a '.')
* and returns a List of words.
*/
  	java.util.List getWordList(String word);

	ListCellRenderer getListCellRenderer();

}