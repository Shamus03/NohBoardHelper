import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class KeyPanel extends JPanel
{
    private int width;
    private int height;

    public KeyPanel()
    {
        super();
        setLayout(null); // Let the keys arrange themselves
        width = KeyBox.BOX_SIZE;
        height = KeyBox.BOX_SIZE;

        addMouseListener(new KeyPanelMouseListener());

        // Check if the dictionary was properly initialized
        KeyDictionary.getNewCode(1);
    }

    public void setBoxWidth(int width)
    {
        this.width = width;
    }

    public void setBoxHeight(int height)
    {
        this.height = height;
    }

    private class KeyPanelMouseListener extends MouseAdapter
    {
        @Override
        public void mouseClicked(MouseEvent e)
        {
            if (width > 10 && height > 10
                && e.getButton() == MouseEvent.BUTTON3)
            {
                add(new KeyBox(e.getX(), e.getY(), width, height));
                repaint();
            }
        }
    }
}
