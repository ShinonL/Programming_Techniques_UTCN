package View;

import java.awt.AWTEvent;
import java.awt.AWTEventMulticaster;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

/**
 * Class to define Round Buttons because JavaSwing does not have that feature.
 */
public class RoundButton extends Component {

    ActionListener actionListener;     // Post action events to listeners
    String label;                      // The Button's text
    protected boolean pressed = false; // true if the button is detented.

    /**
     * Constructs a RoundedButton with no label
     */
    public RoundButton() {
        this("");
    }

    /**
     * Constructs a RoundedButton with the specified label
     * @param label the label of the button
     */
    public RoundButton(String label) {
        this.label = label;
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
    }

    /**
     * gets the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * sets the label
     */
    public void setLabel(String label) {
        this.label = label;
        invalidate();
        repaint();
    }

    /**
     * paints the RoundedButton
     */
    @Override
    public void paint(Graphics g) {
        if (pressed) {
            g.setColor(getBackground().darker().darker());
        } else {
            g.setColor(getBackground());
        }
        g.fillOval(0, 0, getWidth(), getHeight());

        Font f = getFont();
        if (f != null) {
            FontMetrics fm = getFontMetrics(getFont());
            g.setColor(getForeground());
            g.drawString(label, getWidth() / 2 - fm.stringWidth(label) / 2, getHeight() / 2 + fm.getMaxDescent());
        }
    }

    /**
     * The preferred size of the button.
     */
    @Override
    public Dimension getPreferredSize() {
        Font f = getFont();
        if (f != null) {
            FontMetrics fm = getFontMetrics(getFont());
            return new Dimension(fm.stringWidth(label) + 40, fm.getHeight() + 40);
        } else {
            return new Dimension(50, 50);
        }
    }

    /**
     * The minimum size of the button.
     */
    @Override
    public Dimension getMinimumSize() {
        return new Dimension(100, 100);
    }

    /**
     * Adds the specified action listener to receive action events from this
     * button.
     * @param listener the action listener
     */
    public void addActionListener(ActionListener listener) {
        actionListener = AWTEventMulticaster.add(actionListener, listener);
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
    }

    /**
     * Removes the specified action listener so it no longer receives action
     * events from this button.
     * @param listener the action listener
     */
    public void removeActionListener(ActionListener listener) {
        actionListener = AWTEventMulticaster.remove(actionListener, listener);
    }

    /**
     * Determine if click was inside round button.
     */
    @Override
    public boolean contains(int x, int y) {
        int mx = getSize().width / 2;
        int my = getSize().height / 2;
        return (((mx - x) * (mx - x) + (my - y) * (my - y)) <= mx * mx);
    }

    /**
     * Paints the button and distribute an action event to all listeners.
     */
    @Override
    public void processMouseEvent(MouseEvent e) {
        Graphics g;
        switch (e.getID()) {
            case MouseEvent.MOUSE_PRESSED:
                pressed = true;
                repaint();
                break;
            case MouseEvent.MOUSE_RELEASED:
                if (actionListener != null) {
                    actionListener.actionPerformed(new ActionEvent(
                            this, ActionEvent.ACTION_PERFORMED, label));
                }
                if (pressed == true) {
                    pressed = false;
                    repaint();
                }
                break;
            case MouseEvent.MOUSE_ENTERED:
                break;
            case MouseEvent.MOUSE_EXITED:
                if (pressed == true) {
                    repaint();
                }
                break;
        }
        super.processMouseEvent(e);
    }
}