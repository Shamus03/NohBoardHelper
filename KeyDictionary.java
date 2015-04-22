import java.util.*;
import java.io.*;
import javax.swing.JOptionPane;

public class KeyDictionary
{
    private static final String FILE_NAME = "keytranslations.txt";

    private static KeyDictionary instance = new KeyDictionary(FILE_NAME); 

    private Map<Integer, DictionaryEntry> dictionary;

    private KeyDictionary(String fileName)
    {
        dictionary = new HashMap<Integer, DictionaryEntry>();

        try (Scanner fileScan = new Scanner(getClass().getResourceAsStream(
            "/" + fileName)))
        {
            int keyCode;
            int newCode;
            String lineEnd;

            while (fileScan.hasNext())
            {
                try
                {
                    keyCode = fileScan.nextInt();
                    newCode = fileScan.nextInt();
                    lineEnd = fileScan.nextLine().trim();

                    dictionary.put(keyCode,
                        new DictionaryEntry(newCode, lineEnd));
                }
                // Handles comments and any invalid entries
                catch (Exception e)
                {
                    fileScan.nextLine();
                }
            }
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, "Could not open " + FILE_NAME,
                "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    public static int getNewCode(int keyCode)
    {
        DictionaryEntry entry = instance.dictionary.get(keyCode);
        if (entry != null)
            return entry.getNewCode();
        else
            return 0;
    }

    public static String getLineEnd(int keyCode)
    {
        DictionaryEntry entry = instance.dictionary.get(keyCode);
        if (entry != null)
            return entry.getLineEnd();
        else
            return "Null";
    }

    private class DictionaryEntry
    {
        private int newCode;
        private String lineEnd;

        public DictionaryEntry(int newCode, String lineEnd)
        {
            this.newCode = newCode;
            this.lineEnd = lineEnd;
        }

        public int getNewCode()
        {
            return newCode;
        }

        public String getLineEnd()
        {
            return lineEnd;
        }
    }
}
