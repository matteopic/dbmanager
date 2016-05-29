package dbmanager;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Collection;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import dbmanager.chooser.SalvaFile;
import dbmanager.core.DatabaseProperties;
import dbmanager.core.Catalog;
import dbmanager.gui.ChangeConnectionPaneListener;
import dbmanager.gui.ConnectionPane;
import dbmanager.gui.InfoDb;
import dbmanager.gui.StylesheetChooser;
import dbmanager.plugins.Plugin;
import dbmanager.tree.DifferenceTree;

public class Master extends JFrame {

	private static final long serialVersionUID = 7221774008650756104L;
	private Plugin[] plugins;

	public Master() {
		setTitle("DbManager");
		setIconImage(new ImageIcon("img/kexi.png").getImage());
		initGUI();
		controller = new ControllerMaster(this);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				pack();
				// setSize(new java.awt.Dimension(600, 300));
				setLocationRelativeTo(null);
				setVisible(true);
			}
		});
	}

	private void initGUI() {
		new DefaultPopupMenu(jTextArea1);
		new DefaultPopupMenu(urlText);
		new DefaultPopupMenu(usernameText);
		new DefaultPopupMenu(passwordText);

		driverText.setModel(new DefaultComboBoxModel(drivers));
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
		getContentPane().setLayout(new GridBagLayout());

		initNewPaneButton();
		addNewConnectionTab();

		getContentPane().add(connections, new GridBagConstraints(0, 3, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new java.awt.Insets(0, 0, 0, 0), 390, 215));
		getContentPane().add(pulsantiToolBar, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new java.awt.Insets(0, 0, 0, 0), 0, 0));

		initConnectionBar();

		setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		initToolbar();

		// sql.setPreferredSize(new java.awt.Dimension(640,640));

		driverText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cambiaUrl(e);
			}
		});

		jTextArea1.setText("");
		// jTextArea1.setBounds(new java.awt.Rectangle(555,69,53,16));
		jTextArea1.setLineWrap(true);

		initMenu();
	}

	public void addChangeConnectionPaneListener(final ChangeConnectionPaneListener changeConnectionPaneListener) {

		connections.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {

				JTabbedPane tabs = (JTabbedPane) e.getSource();
				int index = tabs.getSelectedIndex();
				int count = tabs.getTabCount();

				// Last pane is new tab button, not a connection pane
				count--;

				if (index < count)
					changeConnectionPaneListener.paneChanging(index);
			}
		});

	}

	private void initConnectionBar() {
		getContentPane().add(connessioneBar, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

		connessioneBar.setFloatable(false);
		connessioneBar.setLayout(new java.awt.GridBagLayout());
		connessioneBar.add(lblUrl, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new java.awt.Insets(0, 5, 0, 5), 0, 0));
		connessioneBar.add(urlText, new GridBagConstraints(3, 0, 1, 1, 2.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new java.awt.Insets(5, 5, 5, 5), 0, 0));
		connessioneBar.add(lblUsername, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new java.awt.Insets(0, 5, 0, 5), 0, 0));
		connessioneBar.add(usernameText, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new java.awt.Insets(0, 5, 0, 5), 0, 0));
		connessioneBar.add(lblPassword, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new java.awt.Insets(0, 5, 0, 5), 0, 0));
		connessioneBar.add(passwordText, new GridBagConstraints(3, 1, 1, 1, 1.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new java.awt.Insets(0, 5, 0, 5), 0, 0));
		connessioneBar.add(lblDriver, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new java.awt.Insets(0, 5, 0, 5), 0, 0));
		connessioneBar.add(driverText, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new java.awt.Insets(0, 5, 0, 5), 0, 0));
	}

	private void initNewPaneButton() {
		// JButton addNewTab = new JButton("+");
		// addNewTab.setBorder(null);
		// addNewTab.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent e) {
		// getController().addNewConnection();
		// }
		// });

		assert connections.getTabCount() == 0;
		connections.addTab("+", null);
		// connections.setTabComponentAt(0, addNewTab);
		connections.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (!isVisible())
					return;

				JTabbedPane tabs = (JTabbedPane) e.getSource();
				int index = tabs.getSelectedIndex();
				int count = tabs.getTabCount();
				if (index == count - 1) {
					connections.removeChangeListener(this);
					getController().addNewConnection();
					connections.addChangeListener(this);
				}
			}
		});
	}

	private void initToolbar() {
		pulsantiToolBar.setFloatable(false);
		pulsantiToolBar.add(connesso);
		pulsantiToolBar.add(nonConnesso);
		pulsantiToolBar.add(esegui);
		pulsantiToolBar.add(esportaXml);
		pulsantiToolBar.add(esportaStruttura);
		pulsantiToolBar.add(reportStruttura);
		pulsantiToolBar.add(infoDB);
		pulsantiToolBar.add(new JSeparator(SwingConstants.VERTICAL));

		pluginsControls.setOpaque(false);
		pulsantiToolBar.add(pluginsControls);
		refreshPluginsControls();

		AbstractAction eseguiListener = new AbstractAction("Esegui") {
			private static final long serialVersionUID = -1735780551247244550L;

			public void actionPerformed(ActionEvent e) {
				esegui(e);
			}
		};

		KeyStroke ksEsegui = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.CTRL_MASK);
		esegui.getActionMap().put(eseguiListener.getValue(Action.NAME), eseguiListener);
		esegui.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(ksEsegui, eseguiListener.getValue(Action.NAME));
		esegui.addActionListener(eseguiListener);

		connesso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connetti(e);
			}
		});
		nonConnesso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				disconnetti(e);
			}
		});

		esportaXml.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				esportaXml(e);
			}
		});

		esportaStruttura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				esportaStruttura(e);
			}
		});

		reportStruttura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reportStruttura(e);
			}
		});

		infoDB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				infoDB(e);
			}
		});

		connesso.setToolTipText("Connetti");
		nonConnesso.setToolTipText("Disconnetti");
		esportaXml.setToolTipText("Esporta in XML");
		esportaXml.setEnabled(false);
		esportaStruttura.setToolTipText("Esporta struttura del database");

		esegui.setToolTipText("Esegui");
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				exit(e);
			}
		});
	}

	public void setPlugins(Plugin... plugins) {
		this.plugins = plugins;
		refreshPluginsControls();
	}

	private void refreshPluginsControls() {
		pluginsControls.removeAll();
		if (plugins == null)
			return;

		for (Plugin plugin : plugins) {
			Component cmp = plugin.getToolbarComponent();
			pluginsControls.add(cmp);
		}
	}

	private void initMenu() {
		JMenuBar mb = new JMenuBar();
		setJMenuBar(mb);

		JMenu menuDB = new JMenu("Database");
		JMenuItem compare = new JMenuItem("Confronta");
		compare.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				getController().startDbCompare();
			}
		});
		menuDB.add(compare);
		mb.add(menuDB);
	}

	public String getUrl() {
		return urlText.getText();
	}

	public void setUrl(String url) {
		urlText.setText(url);
	}

	public String getPassword() {
		return new String(passwordText.getPassword());
	}

	public void setPassword(String password) {
		passwordText.setText(password);
	}

	public String getUsername() {
		return usernameText.getText();
	}

	public void setUsername(String username) {
		usernameText.setText(username);
	}

	public String getDriver() {
		return driverText.getSelectedItem().toString();
	}

	public void setDriver(String driver) {
		driverText.setSelectedItem(driver);
	}

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
		getConnectionPane().setResult(comp);
		if (comp instanceof JTable) {
			esportaXml.setEnabled(true);
		} else {
			esportaXml.setEnabled(false);
		}

		// jScrollPane2.getViewport().setView(comp);
		// jSplitPane1.setBottomComponent(jScrollPane2);
	}

	public String getQuery() {
		return getConnectionPane().getQuery();
	}

	public void setQuery(String query) {
		getConnectionPane().setQuery(query);
	}

	public void esegui(ActionEvent e) {
		getController().esegui();
	}

	public void connetti(ActionEvent e) {
		getController().connetti();
	}

	public void disconnetti(ActionEvent e) {
		getController().disconnetti();
	}

	protected void addNewConnectionTab() {
		int count = connections.getTabCount();
		String title = "Conn " + count;
		ConnectionPane cpane = new ConnectionPane();
		count--;
		connections.insertTab(title, null, cpane, null, count);
		connections.setSelectedIndex(count);

		// ControllerMaster cm = new ControllerMaster(this);
		// controllers.add(cm);
	}

	private ConnectionPane getConnectionPane() {
		return (ConnectionPane) connections.getSelectedComponent();
	}

	private ControllerMaster getController() {
		return controller;
		// int index = connections.getSelectedIndex();
		// return controllers.get(index);
	}

	public void infoDB(ActionEvent e) {
		ControllerMaster controller = getController();
		InfoDb infodb = new InfoDb(controller, this);
		infodb.pack();
		infodb.setLocationRelativeTo(this);
		infodb.setVisible(true);
	}

	public void openCompareWindow(Catalog c1, Catalog c2) {
		DifferenceTree dTree = new DifferenceTree(c1, c2);
		dTree.setVisible(true);

		// try {
		// if(controllers.size() >= 2){
		// DatabaseProperties prop1 =
		// controllers.get(0).getDatabaseProperties();
		// DatabaseProperties prop2 =
		// controllers.get(1).getDatabaseProperties();
		// DifferenceTree dTree = new DifferenceTree(prop1, prop2);
		// dTree.setVisible(true);
		// }
		// } catch (Exception ex) {
		// ex.printStackTrace();
		// }
	}

	public void reportStruttura(ActionEvent e) {
		Thread t = new Thread() {
			public void run() {
				JFrame report = getController().getReportStruttura();
				if (report == null)
					return;
				// JDialog dialog = new JDialog((JFrame)null, true);
				report.pack();
				report.setLocationRelativeTo(null);
				report.setVisible(true);
			}
		};
		t.start();
	}

	public void esportaXml(ActionEvent e) {
		JTable dati = getConnectionPane().getLastResult();
		if (dati == null)
			return;

		File fileToSave = new SalvaFile(this, "xml").getFile();
		if (fileToSave == null)
			return;
		getController().esportaXml(dati.getModel(), fileToSave);
	}

	public void esportaStruttura(ActionEvent e) {
		StylesheetChooser sschooser = new StylesheetChooser(this);
		sschooser.setVisible(true);
		File xsl = sschooser.getSelectedTemplate();
		if (xsl == null && !sschooser.isPureXml())
			return;

		File fileToSave = new SalvaFile(this, "sql").getFile();
		if (fileToSave == null)
			return;
		getController().esportaStruttura(xsl, fileToSave);
	}

	public void cambiaUrl(ActionEvent e) {
		JComboBox<String> drv = (JComboBox<String>) e.getSource();
		int index = drv.getSelectedIndex();
		if (index != -1)
			urlText.setText(urls[index]);
	}

	public void exit(WindowEvent e) {
		controller.shutdown();
	}

	public void appendMessage(String message) {
		getConnectionPane().appendMessage(message);
	}

	public void setKeywords(String keys, String nFunction, String sFunction, Collection<dbmanager.core.Type> types) {
		getConnectionPane().setKeywords(keys, nFunction, sFunction, types);
	}

	/*
	 * private void initCodeCompletition(){ final CodeCompletation cc = new
	 * CodeCompletation(); cc.setInvoker(sql); sql.addKeyListener(new
	 * KeyAdapter(){ public void keyPressed(KeyEvent e){ if(e.isControlDown() &&
	 * e.getKeyCode() == KeyEvent.VK_SPACE){ cc.show(sql, sql.getX(),
	 * sql.getY()); }
	 * 
	 * } }); }
	 */

	// public void setTabelle(Table[] tabelle) {
	// getConnectionPane().setTabelle(tabelle);
	// }

	public void setDatabaseProperties(DatabaseProperties dbProps, String currentCatalog, String currentSchema) {
		getConnectionPane().setDatabaseProperties(dbProps);
		for(Catalog catalog : dbProps.getCatalogs()){
			if(!catalog.getName().equals(currentCatalog))continue;

			getConnectionPane().setTabelle(catalog.getTables());
			break;
		}
	}

	private String[] urls = new String[] { "jdbc:mysql://[HOST][:PORT]/dbname[?param=value[&param2=value2]]",
			"jdbc:derby:jaco;create=true", "jdbc:odbc:[NOME ODBC]", "jdbc:oracle:oci8:@[SID]",
			"jdbc:microsoft:sqlserver://[HOST]:[PORT][;DatabaseName=[DB]]", "jdbc:hsqldb:[file]",
			"jdbc:postgresql://[HOST]:[PORT][?DatabaseName=[DB]]", "jdbc:sqlite:[FILE]" };

	private String[] drivers = new String[] { "com.mysql.jdbc.Driver", "org.apache.derby.jdbc.EmbeddedDriver",
			"sun.jdbc.odbc.JdbcOdbcDriver", "oracle.jdbc.driver.OracleDriver",
			"com.microsoft.jdbc.sqlserver.SQLServerDriver", "org.hsqldb.jdbcDriver", "org.postgresql.Driver",
			"org.sqlite.JDBC" };

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

	private ControllerMaster controller;
	private JToolBar connessioneBar = new JToolBar();
	private JLabel lblPassword = new JLabel();
	private JLabel lblUsername = new JLabel();
	private JTextField usernameText = new JTextField();
	private JComboBox<String> driverText = new JComboBox<String>();
	// private JScrollPane jScrollPane2 = new JScrollPane();
	private JTextArea jTextArea1 = new JTextArea();
	// private BottomPane bottomPane;
	private JTabbedPane connections = new JTabbedPane();
	private JPanel pluginsControls = new JPanel();
	// private List<ControllerMaster> controllers = new
	// ArrayList<ControllerMaster>();

}
