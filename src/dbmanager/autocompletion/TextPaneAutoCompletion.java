package dbmanager.autocompletion;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.ListCellRenderer;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.Keymap;

/**
 * <p>
 * This class defines a JTextPane which can popup a window with words to be
 * inserted when a specified key is pressed (by default the char '.'). Example:
 * <br>
 * <br>
 * <code>AutoCompletionModel model = new AutoCompletionModel() <br>
 *    {<br>
 *      public java.util.List getWordList(String word)<br>
 *      {<br>
 *        Random r = new Random();<br>
 *       java.util.List res = new ArrayList();<br>
 *        int len = r.nextInt(10);<br>
 *        for (int i=0; i<len;i++)<br>
 *        {<br>
 *          res.add(word+"_"+i);<br>
 *        }<br>
 *        return res;<br>
 *      }<br>
 *    };<br>
 *    TextPaneAutoCompletion textAuto = new TextPaneAutoCompletion(model);<br>
 *    </code>
 */
public class TextPaneAutoCompletion extends JTextPane {

    private static final long serialVersionUID = 1548557625186329198L;

    private AutoCompletionModel model;

    private char keyChar = '.';

    private int ctrlKeyCode = KeyEvent.VK_SPACE;

    private PopupWindow popup = null;

    private Keymap originalKeyMap;

    private Keymap popupKeyMap;

    private CaretListener caretListener;

    private char[] separators = { ' ', '\t', '\n', '(', ')', '=' };

    /**
     * <p>
     * The costructore takes an AutoCompletionModel from which to take the word
     * list when the specificied key is pressed. The component gives the model
     * the word on the left of the key char
     */
    public TextPaneAutoCompletion(AutoCompletionModel model) {
        this.model = model;
        popup = new PopupWindow(this);
        setMargin(new Insets(0, 10, 0, 0));
        popup.addComponentListener(new ComponentAdapter() {
            public void componentHidden(ComponentEvent e) {
                remap();
            }

            public void componentShown(ComponentEvent e) {
            }
        });

        addPopupKeyMap();
        createCaretListener();

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {

                handleKey(e);
            }
        });

        addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {

            }

            public void focusLost(FocusEvent e) {
                popup.closeWindow();
            }
        });

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

            }

            public void mousePressed(MouseEvent e) {
                popup.closeWindow();
            }

        });

    }

    // / Public Methods

    /**
     * <p>
     * Set the text appearing on the top of the window to the String s passed as
     * parameter. By default the description is "Select an item"
     */
    public void setDescription(String s) {
        popup.desc.setText(s);
    }

    /**
     * <p>
     * Returns the text appering on the top of the popup window.
     * 
     */
    public String getDescription() {
        return popup.desc.getText();
    }

    /**
     * <p>
     * Returns the current activation char, that is the char which activates the
     * popup window when it's pressed. The default char is '.'
     */
    public char getActivationKeyChar() {
        return keyChar;
    }

    /**
     * <p>
     * Sets the activation char, that is the char which activates the popup
     * window when it's pressed. The default char is '.'
     */
    public void setActivationKeyChar(char keyChar) {
        this.keyChar = keyChar;
    }

    /**
     * Returns the AutoCompletionModel associated to this TextPaneAutoCompletion
     */
    public AutoCompletionModel getModel() {
        return model;
    }

    /**
     * Sets the AutoCompletionModel to be associated to this
     * TextPaneAutoCompletion
     */
    public void setModel(AutoCompletionModel model) {
        this.model = model;
    }

    /**
     * <p>
     * Returns the font used for the List in the popup Window
     */
    public Font getPopupListFont() {
        return popup.listChoices.getFont();
    }

    /**
     * <p>
     * Sets the font to be used for the List in the popup Window
     */
    public void setPopupListFont(Font f) {
        popup.listChoices.setFont(f);
    }

    /**
     * <p>
     * Returns the number of rows shown in the List in the popup Window
     */
    public int getPopupVisibleRowCount() {
        return popup.getVisibleRowCount();
    }

    /**
     * <p>
     * Sets the number of rows to be shown in the List in the popup Window
     */
    public void setPopupVisibleRowCount(int n) {
        popup.setVisibleRowCount(n);
    }

    /**
     * <p>
     * This methods returns the array of char used as word separators
     * 
     */
    public char[] getSeparators() {
        return separators;
    }

    /**
     * <p>
     * This methods set the array of char to be used as word separators. By
     * defaults the separators are the following chars:
     * 
     * <li> ' ' (Space)
     * <li> '\t' (Tab)
     * <li> '\n' (New Line)
     * <li> '<'
     * <li> '>'
     * <li> '!'
     * <li> '='
     * <li> '{'
     * <li> '}'
     * <p>
     * This means, for example, that if I press the keyChar when in my textpane
     * I have: <br>
     * <i> value==dummy() </i>
     * <p>
     * in the <i>getWordList</i> method of the AutoCompletionModel the String
     * passed as the parameter will be <i>dummy()</i>.
     * 
     */

    public void setSeparators(char[] separators) {
        this.separators = separators;
    }

    // //// Private Stuff

    private void handleKey(KeyEvent e) {
        char keyPressed = e.getKeyChar();
        if (keyPressed == keyChar) {

            showPopup();
        }
        if (e.getKeyCode() == ctrlKeyCode && e.isControlDown()) {
            String partial = getWord(true);
            showPopup(getPreviousWord(), getCaretPosition()
                    - (partial.length() + 1));

            popup.select(partial);
        }

    }

    private void showPopup() {
        int caretPos = getCaretPosition();
        // Workaround to make it work on MacOs X
        if (System.getProperty("os.name").startsWith("Mac"))
            caretPos--;
        showPopup(getWord(), caretPos);
    }

    private void showPopup(String word, int pos) {

        java.util.List values = model.getWordList(word);
        if (!values.isEmpty()) {
            popup.setValues(values);
            Point p = calcPopupLocation();
            popup.setLocation(p);
            addCaretListener(caretListener);
            popup.showWindow(pos);
            setKeymap(popupKeyMap);
        }

        ListCellRenderer renderer = model.getListCellRenderer();
        if (renderer != null)
            popup.setCellRenderer(renderer);
    }

    private Point calcPopupLocation() {
        try {
            Point p = getLocationOnScreen();

            Rectangle pc = modelToView(getCaretPosition());
            Point res = new Point(p.x + pc.x + pc.width, p.y + pc.y + pc.height);

            Dimension screenBounds = Toolkit.getDefaultToolkit()
                    .getScreenSize();

            if (res.y + popup.getHeight() > screenBounds.height) {
                res.y -= (popup.getHeight() + pc.height);
            }

            if (res.x + popup.getWidth() > screenBounds.width) {
                res.x = screenBounds.width - popup.getWidth();
            }
            return res;
        } catch (Exception e) {
            return new Point(0, 0);
        }
    }

    private void remap() {
        removeCaretListener(caretListener);
        setKeymap(originalKeyMap);
    }

    private void createCaretListener() {
        caretListener = new CaretListener() {
            public void caretUpdate(CaretEvent e) {
                int originalPos = popup.getOriginalPosition();
                if (e.getDot() <= originalPos) {
                    popup.closeWindow();
                    return;
                }
                try {
                    String sub = getDocument().getText(originalPos + 1,
                            e.getDot() - (originalPos + 1));
                    popup.select(sub);
                } catch (Exception ex) {

                }
            }
        };
    }

    private void addPopupKeyMap() {
        originalKeyMap = getKeymap();

        popupKeyMap = addKeymap("popupBindigs", originalKeyMap);
        Action action = new PopupScrollAction(1);
        KeyStroke key = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0);
        popupKeyMap.addActionForKeyStroke(key, action);
        action = new PopupScrollAction(-1);
        key = KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0);
        popupKeyMap.addActionForKeyStroke(key, action);
        action = new PopupScrollAction(-5);
        key = KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_UP, 0);
        popupKeyMap.addActionForKeyStroke(key, action);
        action = new PopupScrollAction(5);
        key = KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_DOWN, 0);
        popupKeyMap.addActionForKeyStroke(key, action);
        action = new PopupCopyTextAction();
        key = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
        popupKeyMap.addActionForKeyStroke(key, action);
        action = new PopupCloseAction();
        key = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        popupKeyMap.addActionForKeyStroke(key, action);

    }

    protected void copyText() {

        String selString = popup.getText();
        popup.closeWindow();
        if (selString != null) {

            int curPos = getEndIndexOfWord();
            setSelectionStart(popup.getOriginalPosition() + 1);
            setSelectionEnd(curPos);
            replaceSelection(selString);
        }
    }

    private String getPreviousWord() {
        try {
            int curPos = getCaretPosition();
            // String allText = getText().substring(0,curPos);
            String allText = getDocument().getText(0, curPos);

            for (int i = allText.length() - 1; i >= 0; i--) {
                char cc = allText.charAt(i);
                if (isWordChar(cc))
                    curPos--;
                else
                    break;
            }

            if (allText.charAt(curPos - 1) == keyChar)
                return getWord(curPos - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private String getWord() {
        return getWord(getCaretPosition(), true);
    }

    private String getWord(boolean includeKeyChar) {
        return getWord(getCaretPosition(), includeKeyChar);
    }

    private String getWord(int curPos) {
        return getWord(curPos, true);
    }

    private String getWord(int curPos, boolean includeKeyChar) {

        String res = "";

        try {
            String allText = getDocument().getText(0, curPos);

            for (int i = allText.length() - 1; i >= 0; i--) {
                char cc = allText.charAt(i);
                if (isWordChar(cc) || (includeKeyChar && cc == keyChar))
                    res = cc + res;
                else
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    private int getEndIndexOfWord() {
        int curPos = getCaretPosition();
        try {
            char cc;
            while (isWordChar(cc = getDocument().getText(curPos, 1).charAt(0))
                    || (cc == keyChar))
                curPos++;
        } catch (Exception e) {
        }
        return curPos;
    }

    private boolean isWordChar(char cc) {

        for (int i = 0; i < separators.length; i++) {
            if (cc == separators[i] || cc == keyChar)
                return false;
        }
        return true;
    }

    private class PopupScrollAction extends AbstractAction {
        private static final long serialVersionUID = 6046911478359012383L;

        int dx = 0;

        public PopupScrollAction(int dx) {
            this.dx = dx;
        }

        public void actionPerformed(ActionEvent e) {
            popup.movePopupList(dx);
        }

    }

    private class PopupCopyTextAction extends AbstractAction {
        private static final long serialVersionUID = -7195076489979862865L;

        public void actionPerformed(ActionEvent e) {
            copyText();
        }

    }

    private class PopupCloseAction extends AbstractAction {
        private static final long serialVersionUID = 1L;

        public void actionPerformed(ActionEvent e) {
            popup.closeWindow();
        }

    }

}