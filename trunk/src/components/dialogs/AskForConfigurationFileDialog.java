/**
 * Copyright 2012-2014 Naeregwen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package components.dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.miginfocom.swing.MigLayout;

import commons.api.Steam;
import components.GamesLibrarian;
import components.containers.BoundedComponent;

/**
 * Custom dialog to select a configuration xml file.
 * <br/>Allow to choose file browsing start between:
 * <ul>
 * <li>base : the application base directory (user.dir)</li>
 * <li>source : the directory based on defaultDirectory and/or defaultFilename</li>
 * </ul>
 * on exit, two members are set:
 * <ul>
 * <li>inputDirectory</li>
 * <li>inputFilename</li>
 * </ul>
 * on cancel, these two members are empty strings
 * 
 * @author Naeregwen
 *
 */
public class AskForConfigurationFileDialog extends JDialog {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6489304569469414830L;
	
	ResourceBundle UITexts;
	
	private JLabel textLabel;
	private JTextField input;
	
	private JButton baseButton;
	private JButton sourceButton;
	private JButton okButton;
	private JButton cancelButton;
	
	private String inputFilename = "";
	private String inputDirectory = "";
	
	private String defaultFilename = GamesLibrarian.class.getSimpleName() + ".xml";
	private String defaultDirectory = System.getProperty("user.dir");
	private String savedDirectory = null;

	/**
	 * Create GUI and show
	 * 
	 * @param frame parent component
	 * @param modal modal or not
	 * @param defaultDirectory directory to check set
	 * @param defaultFilename filename to check and set
	 */
	public AskForConfigurationFileDialog(JFrame frame, boolean modal, String defaultDirectory, String defaultFilename, ImageIcon icon, String title, String message, ResourceBundle UITexts) {
		super(frame, modal);
		this.UITexts = UITexts;
		if (defaultDirectory != null && !defaultDirectory.trim().equals(""))
			this.defaultDirectory = defaultDirectory;
		if (defaultFilename != null && !defaultFilename.trim().equals(""))
			this.defaultFilename = defaultFilename;
		initComponents(icon, title, message);
		pack();
		okButton.requestFocusInWindow();
		setLocationRelativeTo(frame);
		setVisible(true);
	}

	/**
	 * Create GUI and hook listeners
	 */
	private void initComponents(ImageIcon icon, String title, String message) {

		getContentPane().setLayout(new MigLayout("", "[][][][][]", "[][][][]"));

		setTitle(title);
		
		JLabel iconLabel = new JLabel();
		ImageIcon imageIcon = BoundedComponent.resizeAndSetIcon(iconLabel, icon, Steam.dialogIconWidth, Steam.dialogIconHeight);
		iconLabel.setIcon(imageIcon);
		iconLabel.setText(null);
		
		textLabel = new JLabel(message);
		
		input = new JTextField();
		input.setText(defaultFilename);

		baseButton = new JButton(UITexts.getString("askBase"));
		baseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				basePerformed(event);
			}
		});
		
		sourceButton = new JButton(UITexts.getString("askSource"));
		sourceButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				sourcePerformed(event);
			}
		});
		
		okButton = new JButton(UITexts.getString("askOk"));
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				okPerformed(event);
			}
		});
		
		cancelButton = new JButton(UITexts.getString("askCancel"));
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				cancelPerformed(event);
			}
		});
		
		getContentPane().add(iconLabel, "cell 0 1 1 2,alignx trailing");
		getContentPane().add(textLabel, "cell 1 1 4 1");
		getContentPane().add(input, "cell 1 2 4 1,growx");
		getContentPane().add(baseButton, "cell 1 3");
		getContentPane().add(sourceButton, "cell 2 3");
		getContentPane().add(okButton, "cell 3 3");
		getContentPane().add(cancelButton, "cell 4 3");
		
		// Hook VK_ENTER/VK_ESCAPE listeners to rootPane
		rootPane.setDefaultButton(okButton);
		setEscapeAction();
	}

	/**
	 * Hook VK_ESCAPE listener
	 */
	private void setEscapeAction() {
		InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escPressed");
		rootPane.getActionMap().put("escPressed", new AbstractAction("escPressed") {
			private static final long serialVersionUID = 1282000323553661543L;
			public void actionPerformed(ActionEvent actionEvent) {
				cancel();
			}
		});
	}

	/**
	 * Do user confirmation of file choosing
	 */
	private void ok() {
		inputFilename = input.getText();
		if (inputDirectory.equals(""))
			inputDirectory = defaultDirectory.endsWith(System.getProperty("file.separator")) ? defaultDirectory : defaultDirectory + System.getProperty("file.separator");
		this.dispose();
	}
	
	/**
	 * Do user cancellation of file choosing
	 */
	private void cancel() {
		inputFilename = "";
		inputDirectory = "";
		this.dispose();
	}
	
	/**
	 * Try to set a fileChooser defaultDirectory with file passed
	 * 
	 * @param fileChooser the fileChosser to set
	 * @param file the file to help determine new default directory
	 * 
	 * @return true if fileChooser has been set
	 *  
	 * FIXME: difference btw load (canRead) & save (canWrite)
	 */
	private boolean setDirectory(CenteredFileChooser fileChooser, File file) {
		if (file.exists() && file.canRead()) {
			if (file.isDirectory())
				fileChooser.setCurrentDirectory(file);
			else 
				fileChooser.setCurrentDirectory(new File(file.getPath()));
			return true;
		}
		return false;
	}
	
	/**
	 * User event on start file browsing from source directory = the directory based on defaultDirectory or user input content or user.dir (fallback)
	 * 
	 * @param event the source event
	 */
	private void sourcePerformed(ActionEvent event) {
		
		CenteredFileChooser fileChooser = new CenteredFileChooser(this.getRootPane());
		
		File directory = new File(defaultDirectory);
		fileChooser.setCurrentDirectory(directory.isDirectory() ? new File(defaultDirectory) : new File(System.getProperty("user.dir")));
		
		if (input.getText() != null && !input.getText().trim().equals("")) {
			File file = new File(input.getText());			
			if (!file.isDirectory()) {
				file = file.getParentFile();
				if (file != null && file.exists())
					setDirectory(fileChooser, file);
			} else
				setDirectory(fileChooser, file);
		}
		
		ResourceBundle UITexts = ResourceBundle.getBundle("i18n/UiTexts");
		
		String message = UITexts.getString("XMLFilenameFilter");
		FileNameExtensionFilter XMLFileFilter = new FileNameExtensionFilter(message, "xml");
		fileChooser.addChoosableFileFilter(XMLFileFilter);
		fileChooser.setFileFilter(XMLFileFilter);
		
		int userAction = fileChooser.showOpenDialog(this);
		if (userAction == JFileChooser.APPROVE_OPTION) {
			inputFilename = fileChooser.getSelectedFile().getName();
			defaultDirectory = inputDirectory = fileChooser.getSelectedFile().getParent();
			input.setText(inputFilename);
		}
		if (userAction == JFileChooser.CANCEL_OPTION) {
			inputFilename = "";
			inputDirectory = "";
		}
		if (userAction == JFileChooser.ERROR_OPTION) {
			inputFilename = "";
			inputDirectory = "";
		}
		if (savedDirectory != null) {
			defaultDirectory = savedDirectory;
			savedDirectory = null;
		}
	}

	/**
	 * User event on start file browsing from application base directory
	 * 
	 * @param event the source event
	 */
	private void basePerformed(ActionEvent event) {
		savedDirectory = defaultDirectory;
		defaultDirectory = System.getProperty("user.dir");
		sourcePerformed(event);
	}

	/**
	 * User event on confirmation of file choosing
	 * 
	 * @param event the source event
	 */
	private void okPerformed(ActionEvent event) {
		ok();
	}

	/**
	 * User event on cancellation of file choosing
	 * 
	 * @param event the source event
	 */
	private void cancelPerformed(ActionEvent event) {
		cancel();
	}

	/**
	 * @return the partial result of file choosing (filename part)
	 */
	public String getInputFilename() {
		return inputFilename;
	}

	/**
	 * @return the partial result of file choosing (directory part)
	 */
	public String getInputDirectory() {
		return inputDirectory;
	}

	/**
	 * @return the full result of file choosing (directory + filename)
	 */
	public String getFullFilename() {
		return (inputDirectory.endsWith(System.getProperty("file.separator")) ? inputDirectory : inputDirectory + System.getProperty("file.separator")) + inputFilename;
	}

}
