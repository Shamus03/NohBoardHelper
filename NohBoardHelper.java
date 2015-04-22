import javax.swing.*;
import java.awt.*;

public class NohBoardHelper extends JFrame
{
    private static final int WIDTH = 800;
    private static final int HEIGHT = 300;

    public static void main(String[] args)
    {
        JFrame window = new NohBoardHelper();
        window.setVisible(true);
    }

    public NohBoardHelper()
    {
        super("NohBoard Helper");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        KeyPanel keyPanel = new KeyPanel();
        add(keyPanel);

        OptionPanel optionPanel = new OptionPanel(keyPanel);
        add(optionPanel, BorderLayout.EAST);
    }
}
