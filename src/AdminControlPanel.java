import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/*
 * Class: Manages the admin control panel
 * Design pattern: Singleton
 */

public class AdminControlPanel extends JPanel implements ActionListener, MouseListener {

    // singleton instance
    private static final AdminControlPanel adminControl = new AdminControlPanel();
    private static boolean initialized = false;

    // text fields
    private JTextField userIdTextField;
    private JTextField groupIdTextField;

    // buttons
    private JButton addUserButton;
    private JButton addGroupButton;
    private JButton openUserViewButton;
    private JButton showUserTotalButton;
    private JButton showGroupTotalButton;
    private JButton showMessageTotalButton;
    private JButton showPositivePercentageButton;
    private JButton idVerificationButton;
    private JButton lastUpdatedUserButton;

    // panels
    private JPanel bottomRightPanel;

    // labels
    private JLabel userIdLabel;
    private JLabel groupIdLabel;

    // class objects
    private static MainFrame mainFrame;
    private User user;
    private TreeView treeView;
    private UserViewPanel userView;
    private UserTotal userTotal;
    private GroupTotal groupTotal;
    private UserVisitor visitor;
    private MessageTotal messageTotal;
    private PositiveMessage positiveMessage;

    // array lists
    private ArrayList<User> userList;
    private ArrayList<UserGroup> groupList;
    private ArrayList<String> tweetList;
    private ArrayList<String> positiveWordsList;
    private ArrayList<UserViewPanel> userPanels;
    private ArrayList<User> lastUpdatedUser;

    // tree components
    private DefaultMutableTreeNode selectedNode;

    public AdminControlPanel() {
    }

    // singleton get instance method
    public static synchronized AdminControlPanel getInstance(MainFrame mainFrame) {

        setMainFrame(mainFrame);

        if (initialized) {
            return adminControl;
        } else {
            adminControl.initComponents();
            initialized = true;
            return adminControl;
        }

    }

    private void initComponents() {

        // init admin control panel
        this.setBackground(Color.lightGray);
        this.setBounds(0, 0, 600, 400);
        this.setLayout(null);

        // init class objects
        userTotal = new UserTotal();
        groupTotal = new GroupTotal();
        messageTotal = new MessageTotal();
        positiveMessage = new PositiveMessage(messageTotal);
        visitor = new UserVisitor();
        user = new User();

        // init array lists
        userList = new ArrayList<User>();
        groupList = new ArrayList<UserGroup>();
        tweetList = new ArrayList<String>();
        positiveWordsList = new ArrayList<String>();
        userPanels = new ArrayList<>();
        lastUpdatedUser = new ArrayList<User>();

        // init text areas
        userIdTextField = new JTextField();
        groupIdTextField = new JTextField();

        // init labels
        userIdLabel = new JLabel("User ID");
        groupIdLabel = new JLabel("Group ID");

        // init buttons
        addUserButton = new JButton("Add User");
        addUserButton.addActionListener(this);

        addGroupButton = new JButton("Add Group");
        addGroupButton.addActionListener(this);

        openUserViewButton = new JButton("Open User View");
        openUserViewButton.addActionListener(this);

        showUserTotalButton = new JButton("Show User Total");
        showGroupTotalButton = new JButton("Show Group Total");
        showMessageTotalButton = new JButton("Show Message Total");
        showPositivePercentageButton = new JButton("Show Positive Percentage");

        idVerificationButton = new JButton("ID Verification");
        idVerificationButton.addActionListener(this);

        lastUpdatedUserButton = new JButton("Last update");
        lastUpdatedUserButton.addActionListener(this);

        // init tree compnents

        treeView = new TreeView();
        treeView.setEditable(true);
        treeView.setBounds(0, 0, 150, 400);
        treeView.addMouseListener(this);

        this.add(treeView);

        // panels
        bottomRightPanel = new JPanel();
        addBottomRightPanel();

        // labels
        userIdLabel.setBounds(250, 15, 75, 15);
        groupIdLabel.setBounds(250, 75, 75, 15);

        // text fields
        userIdTextField.setBounds(250, 30, 100, 25);
        groupIdTextField.setBounds(250, 90, 100, 25);

        // buttons
        addUserButton.setBounds(400, 30, 100, 25);
        addGroupButton.setBounds(400, 90, 100, 25);
        openUserViewButton.setBounds(250, 150, 250, 25);
        openUserViewButton.setEnabled(false);
        idVerificationButton.setBounds(250, 180, 250, 25);
        lastUpdatedUserButton.setBounds(250, 210, 250, 25);

        // add components
        this.add(userIdTextField);
        this.add(groupIdTextField);
        this.add(userIdLabel);
        this.add(groupIdLabel);
        this.add(addUserButton);
        this.add(addGroupButton);
        this.add(openUserViewButton);
        this.add(idVerificationButton);
        this.add(lastUpdatedUserButton);

    }

    public void addBottomRightPanel() {

        bottomRightPanel.setBackground(Color.gray);
        bottomRightPanel.setBounds(150, 250, 450, 125);
        bottomRightPanel.setLayout(null);

        showUserTotalButton.setBounds(25, 20, 175, 35);
        showUserTotalButton.addActionListener(this);

        showGroupTotalButton.setBounds(250, 20, 175, 35);
        showGroupTotalButton.addActionListener(this);

        showMessageTotalButton.setBounds(25, 70, 175, 35);
        showMessageTotalButton.addActionListener(this);

        showPositivePercentageButton.setBounds(250, 70, 175, 35);
        showPositivePercentageButton.addActionListener(this);

        // init buttons
        bottomRightPanel.add(showUserTotalButton);
        bottomRightPanel.add(showGroupTotalButton);
        bottomRightPanel.add(showMessageTotalButton);
        bottomRightPanel.add(showPositivePercentageButton);

        // add bottom right panel to admin control panel
        this.add(bottomRightPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == openUserViewButton) {

            // opens user view
            openUserView();

        } else if (e.getSource() == addUserButton) {

            if (selectedNode == null) {
                String message = "Choose node to add user/group";
                String labelString = "Error: ";
                createDialogBox(message, labelString);
            } else {
                // adds user
                createUser(userIdTextField.getText());
                System.out.println("Added user: " + userIdTextField.getText());
            }

            userIdTextField.setText("");

        } else if (e.getSource() == addGroupButton) {

            // creates group, adds it to the tree, and adds to group total
            createGroup();
            groupIdTextField.setText("");

        } else if (e.getSource() == showUserTotalButton) {

            String message = visitor.visit(userTotal);
            String labelString = "Total Users: ";
            createDialogBox(message, labelString);

        } else if (e.getSource() == showGroupTotalButton) {

            String message = visitor.visit(groupTotal);
            String labelString = "Total Groups: ";
            createDialogBox(message, labelString);

        } else if (e.getSource() == showMessageTotalButton) {

            String message = visitor.visit(messageTotal);
            String labeString = "Total Messages: ";
            createDialogBox(message, labeString);

        } else if (e.getSource() == showPositivePercentageButton) {

            positiveMessage.findPositiveWords(messageTotal.getMessageList());
            String message = visitor.visit(positiveMessage);
            String labelString = "Positive Message %: ";
            createDialogBox(message, labelString);

        } else if (e.getSource() == idVerificationButton) {

            String invalidMessage = "User IDs are invalid";
            String validMessage = "User IDs are valid";
            String errorLabel = "Error: ";
            String messageLabel = "Message: ";
            userList = userTotal.getUserList();

            if (checkIdDuplicates(userList) && checkIdSpaces(userList)) {
                createDialogBox(validMessage, messageLabel);
            } else {
                createDialogBox(invalidMessage, errorLabel);
            }
        } else if (e.getSource() == lastUpdatedUserButton) {
            String message = lastUpdatedUser.get(lastUpdatedUser.size() - 1).getUsername();
            String labelString = "Last update: ";
            createDialogBox(message, labelString);
        }
    }

    public ArrayList<User> getLastUpdatedUser() {
        return lastUpdatedUser;
    }

    private boolean checkIdSpaces(ArrayList<User> userList) {
        for (User user : userList) {
            for (int j = 0; j < user.getUsername().length(); j++) {
                if (user.getUsername().charAt(j) == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkIdDuplicates(ArrayList<User> userList) {
        for (int i = 0; i < userList.size() - 1; i++) {
            if (userList.size() > 1 && userList.get(i).getUsername().equals(userList.get(i + 1).getUsername())) {
                return false;
            }
        }
        return true;
    }

    private void createGroup() {
        UserGroup newGroup = new UserGroup(groupIdTextField.getText());
        DefaultMutableTreeNode newGroupNode = new DefaultMutableTreeNode(newGroup.getGroupName());
        groupTotal.getUserGroupList().add(newGroup);

        treeView.getTreeModel().insertNodeInto(newGroupNode,
                selectedNode, 0);
    }

    private void createDialogBox(String message, String labelString) {

        JDialog dialog = new JDialog(mainFrame, "Message");
        JLabel label = new JLabel(labelString + message);
        dialog.setBounds(100, 100, 250, 100);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        dialog.add(label);
        dialog.setVisible(true);
    }

    // opens user view with selected user from tree
    private void openUserView() {
        String username = selectedNode.getUserObject().toString();
        user = findUser(username);
        mainFrame.addUserViewPanel(user);

        System.out.println("Opened user view for: " + user.getUsername());
    }

    // creates user, adds it to the tree, and adds to user total
    private void createUser(String username) {

        int index;
        user = new User("@" + username);
        DefaultMutableTreeNode newUserNode = new DefaultMutableTreeNode(user.getUsername());

        userTotal.getUserList().add(user);
        index = selectedNode.getChildCount();
        selectedNode.insert(newUserNode, index);
        treeView.getTreeModel().insertNodeInto(newUserNode,
                selectedNode, 0);

    }

    // method to find users
    public User findUser(String username) {

        User user = new User();
        Boolean found = false;
        userList = userTotal.getUserList();

        System.out.println("User list: ");

        for (int i = 0; i < userList.size(); i++) {
            System.out.println(userList.get(i).getUsername());
        }

        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUsername().equals(username)) {
                user = userList.get(i);
                System.out.println("Found: " + user.getUsername());
                found = true;
                break;
            }
        }

        if (found) {
            System.out.println("Found user");
            return user;
        } else {
            System.out.println("Did not find user");
            return null;
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == treeView) {
            selectedNode = (DefaultMutableTreeNode) treeView.getSelectionPath().getLastPathComponent();
            openUserViewButton.setEnabled(true);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    public JButton getOpenUserViewButton() {
        return openUserViewButton;
    }

    public void setOpenUserViewButton(JButton openUserViewButton) {
        this.openUserViewButton = openUserViewButton;
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public static void setMainFrame(MainFrame newMainFrame) {
        mainFrame = newMainFrame;
    }

    public ArrayList<User> getUserList() {
        return userList;
    }

    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
    }

    public ArrayList<UserGroup> getGroupList() {
        return groupList;
    }

    public void setGroupList(ArrayList<UserGroup> groupList) {
        this.groupList = groupList;
    }

    public ArrayList<String> getTweetList() {
        return tweetList;
    }

    public void setTweetList(ArrayList<String> tweetList) {
        this.tweetList = tweetList;
    }

    public ArrayList<String> getPositiveWordsList() {
        return positiveWordsList;
    }

    public void setPositiveWordsList(ArrayList<String> positiveWordsList) {
        this.positiveWordsList = positiveWordsList;
    }

    public UserViewPanel getUserView() {
        return userView;
    }

    public void setUserView(UserViewPanel userView) {
        this.userView = userView;
        userPanels.add(userView);
    }

    public MessageTotal getMessageTotal() {
        return messageTotal;
    }

    public void setMessageTotal(MessageTotal messageTotal) {
        this.messageTotal = messageTotal;
    }

    public ArrayList<UserViewPanel> getUserPanels() {
        return userPanels;
    }

    public void setUserPanels(ArrayList<UserViewPanel> userPanels) {
        this.userPanels = userPanels;
    }

}
