package clientJudge;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class JudjeMain {
    public static void main(String[] args){
//        JudjeFrame frame = new JudjeFrame();
//        frame.Start();
        try {
            Socket s2 = new Socket();
            s2.connect(new InetSocketAddress("localhost", 25801));
            PrintWriter pw = new PrintWriter(s2.getOutputStream(), true);
            Scanner scanner = new Scanner(s2.getInputStream());
            pw.println("getJudges");
            System.out.println(scanner.nextLine());
            s2.close();
            pw.close();
            System.exit(0);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }
    }

