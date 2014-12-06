package dbmanager.autocompletion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JWindow;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;

public class PopupWindow extends JWindow {

    private static final long serialVersionUID = 3656476456486533930L;

    private TextPaneAutoCompletion txtArea;

    protected JList listChoices;

    private int originalPos;

    protected JLabel desc, lblInfo, about;

    // private JWindow aboutBox;
    private int visibleRows;

    static private int DEFAULT_VISIBLE_ROWS = 6;

    public PopupWindow(TextPaneAutoCompletion txtArea) {
        super();

        this.txtArea = txtArea;

        originalPos = this.txtArea.getCaretPosition();
        listChoices = new JList();
        JScrollPane scrollList = new JScrollPane(listChoices);
        scrollList
                .setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        getContentPane().add(BorderLayout.CENTER, scrollList);
        desc = new JLabel("Select an item", JLabel.CENTER);
        Font f = desc.getFont();
        desc.setFont(new Font(f.getName(), Font.ITALIC, f.getSize() - 2));
        desc.setOpaque(true);
        desc.setBackground(new Color(79, 137, 255));
        /*
         * lblInfo = new JLabel(" i "); lblInfo.setFont(new
         * Font(f.getName(),Font.BOLD,f.getSize()-2)); lblInfo.setOpaque(true);
         * lblInfo.setForeground(new Color(200,200,0));
         * lblInfo.setBackground(new Color(0,0,0));
         * 
         * lblInfo.addMouseListener(new MouseAdapter() {
         * 
         * public void mouseEntered(MouseEvent e) { showAbout(); }
         * 
         * public void mouseExited(MouseEvent e) { closeAbout(); }
         * 
         * });
         * 
         */

        JPanel pnlLabel = new JPanel(new BorderLayout());
        pnlLabel.add(BorderLayout.CENTER, desc);
        // pnlLabel.add(BorderLayout.EAST,lblInfo);

        getContentPane().add(BorderLayout.NORTH, pnlLabel);
        // listChoices.setBackground(new Color(193,213,255));
        listChoices.setFixedCellWidth(200);

        visibleRows = DEFAULT_VISIBLE_ROWS;

        listChoices.setVisibleRowCount(visibleRows);
        pack();

        listChoices.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() >= 2)
                    PopupWindow.this.txtArea.copyText();
            }
        });
        // createAbout();

    }

    /*
     * protected void createAbout() { aboutBox = new JWindow();
     * 
     * about = new JLabel("<HTML><FONT size=-1><CENTER><b>AutoCompletion 1.0
     * </b><br>"+ "<i>by Marcello Valeri<br>Rome 2002</i></CENTER></FONT></HTML>");
     * 
     * Font f = about.getFont();
     * aboutBox.getContentPane().add(BorderLayout.CENTER,about);
     * 
     * about.setOpaque(false);
     * aboutBox.getContentPane().setBackground(listChoices.getBackground());
     * 
     *  }
     * 
     * protected void closeAbout() { aboutBox.setVisible(false); }
     * 
     * protected void showAbout() { aboutBox.pack(); Point loc =
     * lblInfo.getLocationOnScreen(); aboutBox.setLocation(new
     * Point(loc.x+lblInfo.getWidth()+1,loc.y)); aboutBox.setVisible(true); }
     */
    protected void setValues(java.util.List values) {
        // Collections.sort(values);
        listChoices.setListData(new Vector(values));
        if (listChoices.getModel().getSize() < visibleRows)
            listChoices.setVisibleRowCount(listChoices.getModel().getSize());
        else
            listChoices.setVisibleRowCount(visibleRows);
        pack();
    }

    protected void showWindow() {
        showWindow(txtArea.getCaretPosition());
    }

    protected void showWindow(int pos) {
        originalPos = pos;
        pack();
        setVisible(true);
        listChoices.clearSelection();
        listChoices.ensureIndexIsVisible(0);

    }

    protected void closeWindow() {
        // closeAbout();
        setVisible(false);

    }

    protected String getText() {
        return (String) listChoices.getSelectedValue();
    }

    protected void movePopupList(int dx) {

        int selIndex = listChoices.getSelectedIndex() + dx;

        if (selIndex < 0)
            selIndex = 0;
        if (selIndex >= listChoices.getModel().getSize())
            selIndex = listChoices.getModel().getSize() - 1;
        listChoices.setSelectedIndex(selIndex);
        listChoices.ensureIndexIsVisible(selIndex);

    }

    protected int getOriginalPosition() {
        return originalPos;
    }

    protected void select(String sub) {
        ListModel model = listChoices.getModel();

        if (sub.length() > 0) {
            for (int i = 0; i < model.getSize(); i++) {
                String choice = (String) model.getElementAt(i);
                if (choice.startsWith(sub)) {
                    listChoices.setSelectedIndex(i);
                    listChoices.ensureIndexIsVisible(i);
                    return;
                }

            }
        }
        listChoices.clearSelection();
        listChoices.ensureIndexIsVisible(0);
    }

    protected void setVisibleRowCount(int k) {
        visibleRows = k;
    }

    protected int getVisibleRowCount() {
        return visibleRows;
    }

    public void setCellRenderer(ListCellRenderer renderer) {
        listChoices.setCellRenderer(renderer);
    }

}