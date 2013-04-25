/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package TigerControlPanel;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.KeyStroke;

public class KeybindingController {
    GUI window = null;

    private int leftThrottle = 0;
    private int rightThrottle = 0;

    private static final int SPEED_INCREMENT = 5;

    private static char leftAccel = 'q';
    private static char leftDecel = 'a';
    private static char rightAccel = 'e';
    private static char rightDecel = 'd';
    private static char bothAccel = 'w';
    private static char bothDecel = 's';

    public KeybindingController(GUI window)
    {
        this.window = window;
    }

    public void bindKeys()
    {
        //set input maps so that the program can read key bindings
        //putting something in the input map means to assign a key to an action name
        //action name is associated with a method in the action map
        window.btnLeftAccel.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(leftAccel), "accelerateLeft");
        window.btnLeftAccel.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(Character.toUpperCase(leftAccel)), "accelerateLeft");
        window.btnLeftAccel.getActionMap().put("accelerateLeft", accelerateLeft);

        window.btnLeftDecel.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(leftDecel), "decelerateLeft");
        window.btnLeftDecel.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(Character.toUpperCase(leftDecel)), "decelerateLeft");
        window.btnLeftDecel.getActionMap().put("decelerateLeft", decelerateLeft);

        window.btnRightAccel.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(rightAccel), "accelerateRight");
        window.btnRightAccel.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(Character.toUpperCase(rightAccel)), "accelerateRight");
        window.btnRightAccel.getActionMap().put("accelerateRight", accelerateRight);

        window.btnRightDecel.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(rightDecel), "decelerateRight");
        window.btnRightDecel.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(Character.toUpperCase(rightDecel)), "decelerateRight");
        window.btnRightDecel.getActionMap().put("decelerateRight", decelerateRight);

        //just attach to the left button, since theres no button for both
        window.btnLeftAccel.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(bothAccel), "accelerateBoth");
        window.btnLeftAccel.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(Character.toUpperCase(bothAccel)), "accelerateBoth");
        window.btnLeftAccel.getActionMap().put("accelerateBoth", accelerateBoth);

        //just attach to the left button, since theres no button for both
        window.btnLeftDecel.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(bothDecel), "decelerateBoth");
        window.btnLeftDecel.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(Character.toUpperCase(bothDecel)), "decelerateBoth");
        window.btnLeftDecel.getActionMap().put("decelerateBoth", decelerateBoth);
    }

    public void toggleControls()
    {
        if (window.communicator.getConnected() == true)
        {
            window.btnLeftAccel.setEnabled(true);
            window.btnLeftDecel.setEnabled(true);
            window.btnRightAccel.setEnabled(true);
            window.btnRightDecel.setEnabled(true);

            window.btnDisconnect.setEnabled(true);
            window.btnConnect.setEnabled(false);
            window.cboxPorts.setEnabled(false);
        }
        else
        {
            window.btnLeftAccel.setEnabled(false);
            window.btnLeftDecel.setEnabled(false);
            window.btnRightAccel.setEnabled(false);
            window.btnRightDecel.setEnabled(false);

            window.btnDisconnect.setEnabled(false);
            window.btnConnect.setEnabled(true);
            window.cboxPorts.setEnabled(true);
        }
    }

    //defining the action
    Action accelerateLeft = new AbstractAction()
    {
        public void actionPerformed(ActionEvent evt)
        {
            leftThrottle = accelerate(leftThrottle);
            updateLabels();
        }
    };

    Action decelerateLeft = new AbstractAction()
    {
        public void actionPerformed(ActionEvent evt)
        {
            leftThrottle = decelerate(leftThrottle);
            updateLabels();
        }
    };

    Action accelerateRight = new AbstractAction()
    {
        public void actionPerformed(ActionEvent evt)
        {
            rightThrottle = accelerate(rightThrottle);
            updateLabels();
        }
    };

    Action decelerateRight = new AbstractAction()
    {
        public void actionPerformed(ActionEvent evt)
        {
            rightThrottle = decelerate(rightThrottle);
            updateLabels();
        }
    };

    Action accelerateBoth = new AbstractAction()
    {
        public void actionPerformed(ActionEvent evt)
        {
            leftThrottle = accelerate(leftThrottle);
            rightThrottle = accelerate(rightThrottle);
            updateLabels();
        }
    };

    Action decelerateBoth = new AbstractAction()
    {
        public void actionPerformed(ActionEvent evt)
        {
            leftThrottle = decelerate(leftThrottle);
            rightThrottle = decelerate(rightThrottle);
            updateLabels();
        }
    };

    public void updateLabels()
    {
        window.lblLeft.setText(String.valueOf(leftThrottle));
        window.lblRight.setText(String.valueOf(rightThrottle));

        window.communicator.writeData(leftThrottle, rightThrottle);
    }

    public int accelerate(int throttle)
    {
        if (throttle < 100)
        {
            throttle += SPEED_INCREMENT;
        }
        return throttle;
    }

    public int decelerate(int throttle)
    {
        if (throttle > 0)
        {
            throttle -= SPEED_INCREMENT;
        }
        return throttle;
    }

    final public int getLeftThrottle()
    {
        return leftThrottle;
    }

    public void setLeftThrottle(int value)
    {
        leftThrottle = value;
    }

    final public int getRightThrottle()
    {
        return rightThrottle;
    }

    public void setRightThrottle(int value)
    {
        rightThrottle = value;
    }
}
