package components.actions;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import commons.BundleManager;
import commons.GamesLibrary;
import components.GamesLibrarian.WindowBuilderMask;

/**
 * http://tips4java.wordpress.com/2009/05/01/closing-an-application/
 * 
 * This class will create and dispatch a WINDOW_CLOSING event to the active
 * frame. As a result a request to close the frame will be made and any
 * WindowListener that handles the windowClosing event will be executed. Since
 * clicking on the "Close" button of the frame or selecting the "Close" option
 * from the system menu also invoke the WindowListener, this will provide a
 * commen exit point for the application.
 */
public class ExitAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5557767388339055126L;
	
	WindowBuilderMask me;

	/**
	 * @param me the WindowBuilderMask to use for creating/managing this instance
	 */
	public ExitAction(WindowBuilderMask me) {
		this.me = me;
		translate();
	}

	/**
	 * Translate using BundleManager
	 */
	public void translate() {
		// Defensive code to avoid NullPointerException in WindowBuilder when data are empty in bundle (Mnemonic and accelerator are not mandatory)
		if (BundleManager.getUITexts(me, "exitMnemonic") != null && !BundleManager.getUITexts(me, "exitMnemonic").equals("")) // WindowBuilder
			putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, "exitMnemonic")).getKeyCode());
		if (BundleManager.getUITexts(me, "exitAccelerator") != null && !BundleManager.getUITexts(me, "exitAccelerator").equals("")) // WindowBuilder
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(BundleManager.getUITexts(me, "exitAccelerator")));
		putValue(NAME, BundleManager.getUITexts(me, "exitMenuLabel"));
		putValue(SMALL_ICON, GamesLibrary.exitIcon);
		putValue(SHORT_DESCRIPTION, BundleManager.getUITexts(me, "exitToolTip"));
	}
	
	/**
	 * Terminate the application
	 * @param frame
	 */
	public void close(Frame frame) {
		if (frame.isActive() && frame == me.getLibrarian().getView())
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// Find the active frame before creating and dispatching the event
		for (Frame frame : Frame.getFrames())
			close(frame);
	}
}
