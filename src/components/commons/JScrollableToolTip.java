package components.commons;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolTip;
import javax.swing.LookAndFeel;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;

import components.commons.adapters.JScrollableToolTipMouseAdapter;

/**
 * https://forums.oracle.com/thread/2130796
 */
public class JScrollableToolTip extends JToolTip {

    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7294931179347650717L;
	private final MouseListener mouseListener;
    private final JEditorPane tipArea;

    public JScrollableToolTip(final int width, final int height) {
    	
        setLayout(new BorderLayout());
        
        tipArea = new JEditorPane();
        tipArea.setPreferredSize(new Dimension(width, height));
        tipArea.setContentType("text/html");
        tipArea.setEditable(false);
        
        LookAndFeel.installColorsAndFont(tipArea, "ToolTip.background", "ToolTip.foreground", "ToolTip.font");
        
        JScrollPane scrollpane = new JScrollPane(tipArea);
        scrollpane.setBorder(null);
        scrollpane.getViewport().setOpaque(false);
        add(scrollpane);
        
        mouseListener = new JScrollableToolTipMouseAdapter(this, tipArea);
        addMouseListener(mouseListener);
    }

    @Override
    public void addNotify() {
        super.addNotify();
        JComponent component = getComponent();
        if (component != null)
            component.addMouseListener(mouseListener);
    }

    @Override
    public void removeNotify() {
        JComponent component = getComponent();
        if (component != null)
            component.removeMouseListener(mouseListener);
        super.removeNotify();
    }

    @Override
    public void setTipText(final String tipText) {
    	
        String oldValue = this.tipArea.getText();
        
        tipArea.setText(tipText);
        tipArea.setCaretPosition(0);
        
        firePropertyChange("tiptext", oldValue, tipText);
    }

    @Override
    public String getTipText() {
        return tipArea == null ? "" : tipArea.getText();
    }

    @Override
    public void setComponent(JComponent c) {
        JComponent component = getComponent();
        if (component != null)
            component.removeMouseListener(mouseListener);
        super.setComponent(c);
    }

    @Override
    public Dimension getPreferredSize() {
        return getComponent(0).getPreferredSize();
    }

    @Override
    protected String paramString() {
        String tipTextString = (tipArea.getText() != null ? tipArea.getText() : "");
        return super.paramString() + ",tipText=" + tipTextString;
    }

   //for testing only:
    public static void main(final String args[]) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                JFrame f = new JFrame("JScrollableToolTip");
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.setSize(300, 200);
                f.setLocationRelativeTo(null);
                ToolTipManager.sharedInstance().setDismissDelay(10000);
                JTable table = new JTable(50, 4) {
                    /**
					 * serialVersionUID
					 */
					private static final long serialVersionUID = -6346145421991556424L;

					@Override
                    public JToolTip createToolTip() {
                        JScrollableToolTip tip = new JScrollableToolTip(3, 20);
                        tip.setComponent(this);
                        return tip;
                    }
                };
                table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                    /**
					 * serialVersionUID
					 */
					private static final long serialVersionUID = -9132589550006907481L;

					@Override
                    public Component getTableCellRendererComponent(JTable table, Object value,
                            boolean isSelected, boolean hasFocus, int row, int column) {
                        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                        setToolTipText("Row " + row + " Column " + column
                                + "\nUsed to display a 'Tip' for a Component. "
                                + "Typically components provide api to automate the process of "
                                + "using ToolTips. For example, any Swing component can use the "
                                + "JComponent  setToolTipText method to specify the text for a standard tooltip.");

                        return this;
                    }
                });
                f.add(new JScrollPane(table));
                f.setVisible(true);
            }
        });
    }
}