import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MiniTwitter {
    public static void main(String[] args) {
        // set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException e) {
        } catch (ClassNotFoundException e) {
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
        }

        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);

        // feed.printStatuses();
    }
}
