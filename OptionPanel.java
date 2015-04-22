import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

public class OptionPanel extends JPanel
{
    private KeyPanel keyPanel;
    private JTextField categoryBox;
    private JTextField fileNameBox;

    public OptionPanel(KeyPanel keyPanel)
    {
        super();
        setPreferredSize(new Dimension(225, 300));
        setBorder(new LineBorder(Color.BLACK, 1));
        this.keyPanel = keyPanel;

        add(new SliderPanel(Mode.WIDTH));

        add(new SliderPanel(Mode.HEIGHT));

        JPanel categoryPanel = new JPanel();
        categoryPanel.add(new JLabel("Category:"));
        categoryBox = new JTextField();
        categoryBox.setPreferredSize(new Dimension(100, 20));
        categoryPanel.add(categoryBox);
        add(categoryPanel);

        JPanel fileNamePanel = new JPanel();
        fileNamePanel.add(new JLabel("File name:"));
        fileNameBox = new JTextField();
        fileNameBox.setPreferredSize(new Dimension(100, 20));
        fileNamePanel.add(fileNameBox);
        add(fileNamePanel);

        JButton saveButton = new JButton("Save Config");
        saveButton.addActionListener(new SaveListener());
        add(saveButton);
    }

    private class SaveListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String category = categoryBox.getText();
            if (category.equals(""))
            {
                JOptionPane.showMessageDialog(OptionPanel.this,
                    "You must specify a category.", "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            String fileName = fileNameBox.getText();
            if (!fileName.toLowerCase().endsWith(".kb"))
                fileName += ".kb";

            try (PrintWriter outFile = new PrintWriter(new FileWriter(
                new File(fileName))))
            {
                outFile.println("KBVersion 3");
                outFile.println("width " + keyPanel.getWidth());
                outFile.println("height " + keyPanel.getHeight());
                outFile.println("category " + category);

                Component[] components = keyPanel.getComponents();
                outFile.println("nKeysDefined " + components.length);

                for (Component c : components)
                {
                    if (c instanceof KeyBox)
                    {
                        outFile.println(c);
                    }
                }

                JOptionPane.showMessageDialog(OptionPanel.this,
                    fileName + " saved.", "Saved",
                    JOptionPane.INFORMATION_MESSAGE);
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(OptionPanel.this,
                    "Could not save the file.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    enum Mode
    {
        WIDTH("Width"), HEIGHT("Height");

        private String text;

        Mode(String text)
        {
            this.text = text;
        }

        public String toString()
        {
            return text;
        }
    }

    private class SliderPanel extends JPanel
    {

        private Mode mode;
        private JSlider slider;

        public SliderPanel(Mode mode)
        {
            super();

            this.mode = mode;
            // Measured in half-keys
            this.slider = new JSlider(0, 14, 2);

            JLabel label = new JLabel("Box " + mode);

            slider.setMajorTickSpacing(2);
            slider.setMinorTickSpacing(1);
            slider.setSnapToTicks(true);
            slider.setPaintTicks(true);
            slider.addChangeListener(new SliderPanelChangeListener());

            setLayout(new GridLayout(2, 1));
            add(label);
            add(slider);
        }

        private class SliderPanelChangeListener implements ChangeListener
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                switch (mode)
                {
                    // half_boxes * BOX_SIZE / 2 + padding pixels
                    case WIDTH:
                        keyPanel.setBoxWidth(slider.getValue()
                            * KeyBox.BOX_SIZE / 2
                            + (slider.getValue() - 1)/ 2);
                        break;
                    case HEIGHT:
                        keyPanel.setBoxHeight(slider.getValue()
                            * KeyBox.BOX_SIZE / 2
                            + (slider.getValue() - 1) / 2);
                }
            }
        }
    }
}
