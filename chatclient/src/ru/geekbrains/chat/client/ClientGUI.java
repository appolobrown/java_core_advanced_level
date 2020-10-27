package ru.geekbrains.chat.client;

import ru.geekbrains.chat.client.core.Client;
import ru.geekbrains.chat.client.core.ClientListener;
import ru.geekbrains.common.Library;
import ru.geekbrains.network.SocketThread;
import ru.geekbrains.network.SocketThreadListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static ru.geekbrains.common.Library.*;


public class ClientGUI extends JFrame implements ActionListener,
        Thread.UncaughtExceptionHandler, ClientListener {

    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;
    private final String WELCOME_STRING = "Welcome,";
    private final String AUTH_FAILED_STRING = "Auth failed";
    public static final String PATTERN = "MM-dd HH:mm:ss";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN);

    private final JTextArea log = new JTextArea();
    private final JPanel panelTop = new JPanel(new GridLayout(2, 3));
    private final JTextField tfIPAddress = new JTextField("127.0.0.1");
    private final JTextField tfPort = new JTextField("8189");
    private final JCheckBox cbAlwaysOnTop = new JCheckBox("Always on top");
    private final JTextField tfLogin = new JTextField("ivan");
    private final JPasswordField tfPassword = new JPasswordField("123");
    private final JButton btnLogin = new JButton("Login");

    private final JPanel panelBottom = new JPanel(new BorderLayout());
    private final JButton btnDisconnect = new JButton("<html><b>Disconnect</b></html>");
    private final JTextField tfMessage = new JTextField();
    private final JButton btnSend = new JButton("Send");

    private final JList<String> userList = new JList<>();
    private boolean shownIoErrors = false;
    private Client client;

    private ClientGUI() {
        client = new Client(this);
        Thread.setDefaultUncaughtExceptionHandler(this);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(WIDTH, HEIGHT);
        log.setEditable(false);
        log.setLineWrap(true);
        JScrollPane scrollLog = new JScrollPane(log);
        JScrollPane scrollUser = new JScrollPane(userList);

        userList.setListData(client.getUsers());
        scrollUser.setPreferredSize(new Dimension(100, 0));
        cbAlwaysOnTop.addActionListener(this);
        btnSend.addActionListener(this);
        tfMessage.addActionListener(this);
        btnLogin.addActionListener(this);
        btnDisconnect.addActionListener(this);

        panelTop.add(tfIPAddress);
        panelTop.add(tfPort);
        panelTop.add(cbAlwaysOnTop);
        panelTop.add(tfLogin);
        panelTop.add(tfPassword);
        panelTop.add(btnLogin);
        panelBottom.add(btnDisconnect, BorderLayout.WEST);
        panelBottom.add(tfMessage, BorderLayout.CENTER);
        panelBottom.add(btnSend, BorderLayout.EAST);
        panelBottom.setVisible(false);

        add(scrollLog, BorderLayout.CENTER);
        add(scrollUser, BorderLayout.EAST);
        add(panelTop, BorderLayout.NORTH);
        add(panelBottom, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ClientGUI::new);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == cbAlwaysOnTop) {
            setAlwaysOnTop(cbAlwaysOnTop.isSelected());
        } else if (src == btnSend || src == tfMessage) {
            sendMessage();
        } else if (src == btnLogin) {
            connect();
        } else if (src == btnDisconnect) {
            client.disconnect();
        } else {
            showException(Thread.currentThread(), new RuntimeException("Unknown action source: " + src));
        }
    }

    private void connect() {
        try {
            client.connect(tfIPAddress.getText(),
                    tfPort.getText(),
                    tfLogin.getText(),
                    new String(tfPassword.getPassword()));
        } catch (IOException e) {
            showException(Thread.currentThread(), e);
        }
    }

    private void sendMessage() {
        String msg = tfMessage.getText();
        String username = tfLogin.getText();
        if ("".equals(msg)) return;
        tfMessage.setText(null);
        tfMessage.grabFocus();
        client.sendMessage(msg);
    }

    private void wrtMsgToLogFile(String msg, String username) {
        try (FileWriter out = new FileWriter("log.txt", true)) {
            out.write(username + ": " + msg + "\n");
            out.flush();
        } catch (IOException e) {
            if (!shownIoErrors) {
                shownIoErrors = true;
                showException(Thread.currentThread(), e);
            }
        }
    }

    private void putLog(String msg) {
        if ("".equals(msg)) return;

        SwingUtilities.invokeLater(() -> {
            log.append(parseMessage(msg) + "\n");
            log.setCaretPosition(log.getDocument().getLength());
        });
    }

    private String parseMessage(String msg) {
        String[] arr = msg.split(DELIMITER);
        StringBuilder parsed = new StringBuilder();
        if (arr.length == 0) return parsed.toString();
        switch (arr[0]) {
            case AUTH_ACCEPT:
                parsed.append(WELCOME_STRING).append(arr[1]);
                break;
            case AUTH_DENIED:
                parsed.append(AUTH_FAILED_STRING);
                break;
            case TYPE_BROADCAST:
                parsed.append(String.format("%s %s : %s",
                        getTimeFormatted(arr[1]), arr[2], arr[3]));
                break;
            default:
                parsed.append(msg);
                break;
        }
        return parsed.toString();
    }

    private String getTimeFormatted(String s) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(Long.parseLong(s));
        return simpleDateFormat.format(cal.getTime());
    }

    private void showException(Thread t, Throwable e) {
        String msg;
        StackTraceElement[] ste = e.getStackTrace();
        if (ste.length == 0)
            msg = "Empty Stacktrace";
        else {
            msg = String.format("Exception in \"%s\" %s: %s\n\tat %s",
                    t.getName(), e.getClass().getCanonicalName(), e.getMessage(), ste[0]);
            JOptionPane.showMessageDialog(this, msg, "Exception", JOptionPane.ERROR_MESSAGE);
        }
        JOptionPane.showMessageDialog(null, msg, "Exception", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        e.printStackTrace();
        showException(t, e);
        System.exit(1);
    }

    @Override
    public void onClientReady() {
        panelBottom.setVisible(true);
        panelTop.setVisible(false);
    }

    @Override
    public void onDisconnect() {
        panelBottom.setVisible(false);
        panelTop.setVisible(true);
    }

    @Override
    public void onMessage(String message) {
        putLog(message);
    }

    @Override
    public void onClientException(SocketThread thread, Exception exception) {

    }
}
