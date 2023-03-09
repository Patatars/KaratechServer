package clientJudge;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class JudjeFrame  {
    JFrame Frame = new JFrame();
    Container cont = Frame.getContentPane();
    JButton redBut = new JButton("Красный");
    JButton whiteBut = new JButton("Белый");
    int port = 0;

    public JudjeFrame(){
        Frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        Frame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {

                try {
                    Socket s2 = new Socket();
                    s2.connect(new InetSocketAddress("localhost", 25800));
                    PrintWriter pw = new PrintWriter(s2.getOutputStream(), true);
                    pw.println("123456NoPass:" + port);
                    s2.close();
                    pw.close();
                    System.exit(0);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
        try {
            Socket s1 = new Socket();
            s1.connect(new InetSocketAddress("5.19.233.233", 25800));
            Scanner scan = new Scanner(s1.getInputStream());
            PrintWriter pw = new PrintWriter(s1.getOutputStream(), true);
            pw.println("123456pass");
            port = Integer.parseInt(scan.nextLine());
            s1.close();
            scan.close();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Frame.setBounds(500, 500, 1000, 500);
        Frame.setLayout(new GridLayout());
        redBut.addActionListener(e -> {
            try {
                Socket s = new Socket();
                s.connect(new InetSocketAddress("5.19.233.233", port));
                PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
                pw.println("red");
                pw.close();
                s.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        whiteBut.addActionListener(e -> {
            try {
                Socket s = new Socket();
                s.connect(new InetSocketAddress("5.19.233.233", port));
                PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
                pw.println("white");
                pw.close();
                s.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        cont.add(whiteBut);
        cont.add(redBut);

    }
    public void Start(){
        Frame.setVisible(true);
    }


}
