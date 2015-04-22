import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class KeyBox extends JPanel
{
    private static int DEFAULT_WITDH = 1;
    private static int DEFAULT_HEIGHT = 1;
    private static int BORDER_SIZE = 2;
    public static int BOX_SIZE = 43;
    private static int PADDING = 1;
    private static int MOD = (BOX_SIZE + PADDING) / 2;

    private int screenX = 0;
    private int screenY = 0;
    private int myX = 0;
    private int myY = 0;

    private int xPos;
    private int yPos;
    private int myWidth;
    private int myHeight;

    private String keyString;
    private int keyCode;

    private boolean focused;
    
    public KeyBox()
    {
        this(0, 0, DEFAULT_WITDH, DEFAULT_HEIGHT);
    }

    public KeyBox(int x, int y)
    {
        this(x, y, DEFAULT_WITDH, DEFAULT_HEIGHT);
    }

    public KeyBox(int x, int y, int myWidth, int myHeight)
    {
        this.xPos = x;
        this.yPos = y;
        this.myWidth = myWidth;
        this.myHeight = myHeight;

        keyString = "";
        keyCode = 0;

        setBackground(Color.GRAY);
        setBounds(xPos, yPos, myWidth, myHeight);
        setFocusable(true);

        addMouseListener(new KeyBoxMouseListener());

        addMouseMotionListener(new KeyBoxMouseMotionListener());

        addKeyListener(new KeyBoxKeyListener());
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        g.drawString(keyString, myWidth / 2 - keyString.length() * 3,
            myHeight / 2 + 4);
    }

    public void remove()
    {
        Container parent = getParent();
        parent.remove(this);
        parent.repaint();
    }

    public String toString()
    {
        return "key " + KeyDictionary.getNewCode(keyCode) + " " + xPos + " "
            + yPos + " " + myWidth + " " + myHeight + " "
            + KeyDictionary.getLineEnd(keyCode);
    }

    private class KeyBoxMouseListener extends MouseAdapter
    {
        @Override
        public void mousePressed(MouseEvent e)
        {
            screenX = e.getXOnScreen();
            screenY = e.getYOnScreen();

            myX = getX();
            myY = getY();
        }

        @Override
        public void mouseClicked(MouseEvent e)
        {
            if (e.getButton() == MouseEvent.BUTTON3)
            {
                remove();
            }
        }

        @Override
        public void mouseEntered(MouseEvent e)
        {
            requestFocus();
            focused = true;
        }

        @Override
        public void mouseExited(MouseEvent e)
        {
            focused = false;
        }
    }

    private class KeyBoxMouseMotionListener extends MouseMotionAdapter
    {
        @Override
        public void mouseDragged(MouseEvent e)
        {
            int leftMouse = MouseEvent.BUTTON1_DOWN_MASK;
            if ((e.getModifiersEx() & leftMouse) == leftMouse)
            {
                int deltaX = e.getXOnScreen() - screenX;
                int deltaY = e.getYOnScreen() - screenY;

                xPos = myX + deltaX;
                yPos = myY + deltaY;


                int alt = MouseEvent.ALT_DOWN_MASK;
                if ((e.getModifiersEx() & alt) == 0)
                {
                    xPos -= xPos % MOD;
                    yPos -= yPos % MOD;
                }

                setLocation(xPos, yPos);
            }
        }
    }

    private class KeyBoxKeyListener extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent e)
        {
            if (focused)
            {
                keyCode = e.getKeyCode();

                if (e.getKeyLocation() == KeyEvent.KEY_LOCATION_RIGHT
                    || e.getKeyLocation() == KeyEvent.KEY_LOCATION_NUMPAD)
                    keyCode += 200;

                keyString =
                    KeyDictionary.getLineEnd(keyCode).split(" ", 2)[0];

                if (keyString.matches("%(.*)%"))
                {
                    keyString = keyString.replaceAll("%", "");
                    keyString = keyString.substring(0, 1).toUpperCase()
                        + keyString.substring(1);
                    if (keyString.equals("0"))
                        keyString = "Space";
                }

                repaint();
            }
        }
    }
}
