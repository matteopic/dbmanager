package dbmanager;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.StringTokenizer;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

import dbmanager.autocompletion.SqlCompletionModel;
import dbmanager.autocompletion.TextPaneAutoCompletion;
import dbmanager.chooser.SalvaFile;
import dbmanager.core.Table;
import dbmanager.core.Type;
import dbmanager.gui.BottomPane;
import dbmanager.gui.InfoDb;
import dbmanager.gui.NumeroRighe;
import dbmanager.gui.SQLStyledDocument;
import dbmanager.gui.StylesheetChooser;

public class Master extends JFrame {

    private static final long serialVersionUID = 7221774008650756104L;
    public Master() {
		setTitle("DbManager");
		setIconImage(new ImageIcon("img/kexi.png").getImage());
        initGUI();
        pack();
        //setSize(new java.awt.Dimension(600, 300));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initGUI() {
    	new DefaultPopupMenu(jTextArea1);
    	new DefaultPopupMenu(urlText);
    	new DefaultPopupMenu(usernameText);
    	new DefaultPopupMenu(passwordText);

		sql.setText("");
		NumeroRighe numeroRighe = new NumeroRighe(sql);
		jScrollPane1.setRowHeaderView(numeroRighe);

        driverText.setModel( new DefaultComboBoxModel(drivers) );
        lblDriver.setText("Driver:");
        lblPassword.setText("Password:");
        lblPassword.setToolTipText("");
        urlText.setText(urls[0]);
        urlText.setColumns(30);
        lblUrl.setText("Url:");
        lblUrl.setToolTipText("");
        passwordText.setText("");
        passwordText.setColumns(10);
        usernameText.setText("");
        usernameText.setColumns(10);
        lblUsername.setText("Username:");
        lblUsername.setToolTipText("");
        getContentPane().setLayout(new java.awt.GridBagLayout());
        getContentPane().add(jSplitPane1,
            new java.awt.GridBagConstraints(0, 3, 1, 1, 1.0, 1.0, java.awt.GridBagConstraints.CENTER, java.awt.GridBagConstraints.BOTH,
            new java.awt.Insets(0, 0, 0, 0), 390, 215));
        getContentPane().add(pulsantiToolBar,
        new java.awt.GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, java.awt.GridBagConstraints.CENTER, java.awt.GridBagConstraints.HORIZONTAL,
        new java.awt.Insets(0, 0, 0, 0), 0, 0));
        getContentPane().add(connessioneBar,
        new java.awt.GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, java.awt.GridBagConstraints.CENTER, java.awt.GridBagConstraints.HORIZONTAL,
        new java.awt.Insets(0, 0, 0, 0), 0, 0));
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.add(jScrollPane1, javax.swing.JSplitPane.TOP);

        bottomPane = new BottomPane();
        jSplitPane1.add(bottomPane, javax.swing.JSplitPane.BOTTOM);

        setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        pulsantiToolBar.setFloatable(false);
        pulsantiToolBar.add(connesso);
        pulsantiToolBar.add(nonConnesso);
        pulsantiToolBar.add(esegui);
        pulsantiToolBar.add(esportaXml);
        pulsantiToolBar.add(esportaStruttura);
        pulsantiToolBar.add(reportStruttura);
        pulsantiToolBar.add(infoDB);

        //sql.setPreferredSize(new java.awt.Dimension(640,640));
        AbstractAction eseguiListener = new AbstractAction("Esegui") {
			private static final long serialVersionUID = -1735780551247244550L;

			public void actionPerformed(ActionEvent e) { esegui(e); }
        };

        KeyStroke ksEsegui = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.CTRL_MASK);
        esegui.getActionMap().put(eseguiListener.getValue(Action.NAME), eseguiListener);
        esegui.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(ksEsegui, eseguiListener.getValue(Action.NAME));
        esegui.addActionListener( eseguiListener );
        
        
        connesso.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) { connetti(e); }
            });
        nonConnesso.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) { disconnetti(e); }
            });

        esportaXml.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) { esportaXml(e); }
            });

        esportaStruttura.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) { esportaStruttura(e); }
            });

        reportStruttura.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) { reportStruttura(e); }
            });
        
        infoDB.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) { infoDB(e); }
        });

		connesso.setToolTipText("Connetti");
		nonConnesso.setToolTipText("Disconnetti");
		esportaXml.setToolTipText("Esporta in XML");
		esportaXml.setEnabled(false);
		esportaStruttura.setToolTipText("Esporta struttura del database");

		esegui.setToolTipText("Esegui");
        addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent e){exit(e);}});
        connessioneBar.setFloatable(false);
        connessioneBar.setLayout(new java.awt.GridBagLayout());
        connessioneBar.add(lblUrl,
        new java.awt.GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, java.awt.GridBagConstraints.WEST, java.awt.GridBagConstraints.NONE,
        new java.awt.Insets(0, 5, 0, 5), 0, 0));
        connessioneBar.add(urlText,
        new java.awt.GridBagConstraints(3, 0, 1, 1, 2.0, 0.0, java.awt.GridBagConstraints.WEST, java.awt.GridBagConstraints.HORIZONTAL,
        new java.awt.Insets(5, 5, 5, 5), 0, 0));
        connessioneBar.add(lblUsername,
        new java.awt.GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, java.awt.GridBagConstraints.WEST, java.awt.GridBagConstraints.HORIZONTAL,
        new java.awt.Insets(0, 5, 0, 5), 0, 0));
        connessioneBar.add(usernameText,
        new java.awt.GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, java.awt.GridBagConstraints.WEST, java.awt.GridBagConstraints.HORIZONTAL,
        new java.awt.Insets(0, 5, 0, 5), 0, 0));
        connessioneBar.add(lblPassword,
        new java.awt.GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, java.awt.GridBagConstraints.WEST, java.awt.GridBagConstraints.HORIZONTAL,
        new java.awt.Insets(0, 5, 0, 5), 0, 0));
        connessioneBar.add(passwordText,
        new java.awt.GridBagConstraints(3, 1, 1, 1, 1.0, 0.0, java.awt.GridBagConstraints.WEST, java.awt.GridBagConstraints.HORIZONTAL,
        new java.awt.Insets(0, 5, 0, 5), 0, 0));
        connessioneBar.add(lblDriver,
        new java.awt.GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, java.awt.GridBagConstraints.WEST, java.awt.GridBagConstraints.HORIZONTAL,
        new java.awt.Insets(0, 5, 0, 5), 0, 0));
        connessioneBar.add(driverText,
        new java.awt.GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, java.awt.GridBagConstraints.WEST, java.awt.GridBagConstraints.HORIZONTAL,
        new java.awt.Insets(0, 5, 0, 5), 0, 0));
        driverText.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){cambiaUrl(e);}});
        jTextArea1.setText("");
        //jTextArea1.setBounds(new java.awt.Rectangle(555,69,53,16));
        jTextArea1.setLineWrap(true);
    }

    public String getUrl() { return urlText.getText(); }

    public void setUrl(String url) { urlText.setText(url); }

    public String getPassword() { return new String(passwordText.getPassword()); }

    public void setPassword(String password) { passwordText.setText(password); }

    public String getUsername() { return usernameText.getText(); }

    public void setUsername(String username) { usernameText.setText(username); }

    public String getDriver() { return driverText.getSelectedItem().toString(); }

    public void setDriver(String driver) { driverText.setSelectedItem(driver); }

    public void setConnesso(boolean isConnected) {
        connesso.setEnabled(!isConnected);
        nonConnesso.setEnabled(isConnected);
        esegui.setEnabled(isConnected);
        esportaStruttura.setEnabled(isConnected);
        reportStruttura.setEnabled(isConnected);
        infoDB.setEnabled(isConnected);
  
        urlText.setEnabled(!isConnected);
        usernameText.setEnabled(!isConnected);
        passwordText.setEnabled(!isConnected);
        driverText.setEnabled(!isConnected);      
    }


    public void setResult(Component comp) {

		if (comp instanceof JTable){
        	bottomPane.setText(null);
        	bottomPane.setRecords((JTable)comp);
        	esportaXml.setEnabled(true);
		}else{
        	bottomPane.setText((JTextArea)comp);
        	bottomPane.setRecords(null);
        	esportaXml.setEnabled(false);
		}


		//jScrollPane2.getViewport().setView(comp);
        //jSplitPane1.setBottomComponent(jScrollPane2);
    }

    public String getQuery() { return sql.getText(); }

    public void setQuery(String query) { sql.setText(query); }

    public void esegui(ActionEvent e) { controller.esegui(); }

    public void connetti(ActionEvent e) { controller.connetti(); }

    public void disconnetti(ActionEvent e) { controller.disconnetti(); }
    
    public void infoDB(ActionEvent e) {
		InfoDb infodb = new InfoDb(controller, this);
		infodb.pack();
		infodb.setLocationRelativeTo(this);
		infodb.setVisible(true);
    }
    
    

	public void reportStruttura(ActionEvent e) {
		Thread t = new Thread(){
			public void run(){
				JFrame report = controller.getReportStruttura();
				if (report == null)return;
				//JDialog dialog = new JDialog((JFrame)null, true);
				report.pack();
				report.setLocationRelativeTo(null);
				report.setVisible(true);
			}
		};
		t.start();
	}

    public void esportaXml(ActionEvent e) {
		JTable dati = bottomPane.getRecords();
		if (dati == null)return;


		File fileToSave = new SalvaFile(this, "xml").getFile();
		if (fileToSave == null)return;
		controller.esportaXml(dati.getModel(), fileToSave);
	}

    public void esportaStruttura(ActionEvent e) {
		StylesheetChooser sschooser = new StylesheetChooser(this);
		sschooser.setVisible(true);
		File xsl = sschooser.getSelectedTemplate();
		if(xsl == null && !sschooser.isPureXml())return;


		File fileToSave = new SalvaFile(this, "sql").getFile();
		if (fileToSave == null)return;
		controller.esportaStruttura(xsl, fileToSave);
	}




    public void cambiaUrl(ActionEvent e) {
		JComboBox drv = (JComboBox)e.getSource();
        int index = drv.getSelectedIndex();
        if (index != -1)urlText.setText( urls[index] );
    }

    public ControllerMaster getController() { return controller; }

    public void setController(ControllerMaster controller) { this.controller = controller; }

    public void exit(WindowEvent e) {
        if (controller != null)controller.disconnetti();
    }

    public void appendMessage(String message){
        jTextArea1.append(message);
        bottomPane.setText(jTextArea1);
        bottomPane.setRecords(null);
        //jScrollPane2.getViewport().setView(jTextArea1);
		//jSplitPane1.setBottomComponent(jScrollPane2);
    }

    public void setKeywords(String keys, String nFunction, String sFunction, dbmanager.core.Type[]types){
		//System.out.println("Parole Chiave: " + keys +"\nFunzioni Numeriche: "+nFunction+"\nFunzioni Stringa "+sFunction);


		SQLStyledDocument ssd = new SQLStyledDocument();
		StringTokenizer st = new StringTokenizer(keys,",");
		String token;
		while (st.hasMoreTokens()){
			token = st.nextToken();
			ssd.addKeyword( token );
			model.addKeyword( token );
		}

		st = new StringTokenizer(nFunction,",");
		while (st.hasMoreTokens()){
			token = st.nextToken();
			ssd.addNumericFunction( token );
			model.addNumericFunction( token );
		}

		st = new StringTokenizer(sFunction,",");
		while (st.hasMoreTokens()){
			token = st.nextToken();
			ssd.addStringFunction( token );
			model.addStringFunction( token );
		}

		for(int i = 0; i< types.length; i++){
			ssd.addDataType( types[i].getName());
		}

		sql.setStyledDocument(ssd);

		NumeroRighe numeroRighe = new NumeroRighe(sql);
		jScrollPane1.setRowHeaderView(numeroRighe);
	}

	/*private void initCodeCompletition(){
		final CodeCompletation cc = new CodeCompletation();
		cc.setInvoker(sql);
		sql.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_SPACE){
					cc.show(sql, sql.getX(), sql.getY());
				}

			}
		});
	}*/


	public void setTabelle(Table[]tabelle){
		model = new SqlCompletionModel();
		model.setTabelle(tabelle);
		sql = new TextPaneAutoCompletion(model);
		jScrollPane1.getViewport().setView(sql);
	}

    private String[] urls = new String[] {
    	"jdbc:derby:jaco;create=true",
        "jdbc:odbc:[NOME ODBC]",
        "jdbc:mysql://[HOST][:PORT]/dbname[?param=value[&param2=value2]]",
        "jdbc:oracle:oci8:@[SID]",
        "jdbc:microsoft:sqlserver://[HOST]:[PORT][;DatabaseName=[DB]]",
        "jdbc:hsqldb:[file]",
        "jdbc:postgresql://[HOST]:[PORT][?DatabaseName=[DB]]"
    };
    
    private String[] drivers = new String[] {
    	"org.apache.derby.jdbc.EmbeddedDriver",
        "sun.jdbc.odbc.JdbcOdbcDriver",
        "com.mysql.jdbc.Driver",
        "oracle.jdbc.driver.OracleDriver",
        "com.microsoft.jdbc.sqlserver.SQLServerDriver",
        "org.hsqldb.jdbcDriver",
        "org.postgresql.Driver"
    };

    private JSplitPane jSplitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,  null, null);
    private JToolBar pulsantiToolBar = new JToolBar();
    private JLabel lblDriver = new JLabel();
    private JPasswordField passwordText = new JPasswordField();
    private JTextField urlText = new JTextField();
    private JLabel lblUrl = new JLabel();
    private JButton connesso = new JButton(new ImageIcon("img/connesso.png"));
    private JButton nonConnesso = new JButton(new ImageIcon("img/nonconnesso.png"));
    private JButton esegui = new JButton(new ImageIcon("img/run.png"));
    private JButton esportaXml = new JButton(new ImageIcon("img/xml.png"));
    private JButton esportaStruttura = new JButton(new ImageIcon("img/esporta.png"));
    private JButton reportStruttura = new JButton(new ImageIcon("img/report.png"));
    private JButton infoDB = new JButton(new ImageIcon("img/info.png"));
    private JTextPane sql = new JTextPane();
    private JScrollPane jScrollPane1 = new JScrollPane(sql, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    private ControllerMaster controller;
    private JToolBar connessioneBar = new JToolBar();
    private JLabel lblPassword = new JLabel();
    private JLabel lblUsername = new JLabel();
    private JTextField usernameText = new JTextField();
    private JComboBox driverText = new JComboBox();
    //private JScrollPane jScrollPane2 = new JScrollPane();
    private JTextArea jTextArea1 = new JTextArea();
    private SqlCompletionModel model;
    private BottomPane bottomPane;
}
