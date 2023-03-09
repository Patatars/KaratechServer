package server;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


enum Servers{
    A(false, 0, 0), B(false, 0, 0), C(false, 0 , 0), D(false, 0, 0);
    private boolean IsBusy;
    private int scoreWHITE;
    private int scoreRED;
    private boolean reseted = false;
    public static int summRED;
    public static int summWHITE;
    public static int numResetted;
    public static int numJudges = 0;
    public static int getMidRED(){
        if(numJudges == 0) return 0;
        return summRED/numJudges;
    }
     public static int getMidWHITE(){
         if(numJudges == 0) return 0;
         return summWHITE/numJudges;
     }
     public static String getWinner(){
         int white = 0;
         int red = 0;
         for(int i = 0; i< Servers.numJudges;i++){
             if(Servers.values()[i].isBusy()) {
                 if (Servers.values()[i].getScoreWHITE() > Servers.values()[i].getScoreRED()) white++;
                 else if (Servers.values()[i].getScoreRED() > Servers.values()[i].getScoreWHITE()) red++;
             }
         }
         if(white>red)return "Победил белый";
         else if(red>white) return "Победил красный";
         else return "Ничья";
     }
    Servers(boolean busy, int scoreRed, int scoreWhite){
        IsBusy = busy;
        scoreWHITE = scoreWhite;
        scoreRED = scoreRed;
    }

    public String getWinnerPersonal(){
        if(scoreRED > scoreWHITE) return "red";
        if(scoreWHITE > scoreRED) return "white";
        else return "gray";
    }


    public boolean isBusy() {
        return IsBusy;
    }
    public void setBusy(boolean busy){
        IsBusy = busy;
        if(busy)numJudges++;
        else numJudges--;
        Server.controller.ChangeInfo(this);
    }
    public int getScoreWHITE(){
        return scoreWHITE;
    }
    public int getScoreRED(){
        return scoreRED;
    }

    public void setScoreRED(int scoreRED) {
        this.scoreRED = scoreRED;
        summRED = A.getScoreRED() + B.getScoreRED() + C.getScoreRED() + D.getScoreRED();
        Server.controller.ChangeInfo(this);
    }

    public void setScoreWHITE(int scoreWHITE) {
        this.scoreWHITE = scoreWHITE;
        summWHITE = A.getScoreWHITE() + B.getScoreWHITE() + C.getScoreWHITE() + D.getScoreWHITE();
        Server.controller.ChangeInfo(this);
    }

    public boolean isReseted() {
        return reseted;
    }

    public void setReseted(boolean reseted) {
        this.reseted = reseted;
        if(reseted)numResetted++;
        else numResetted--;

    }
}

public class Server {
    public static Controller controller;
    public static String[] ports = new String[] {"25896", "25897", "25898", "25899"};
    public static int RED_points = 0;
    public static int WHITE_points = 0;
    public static boolean Checker;
    static boolean isEnd = false;
    static class ServerA extends Thread {
        @Override
        public void run() {
            while (true) {
                    try {
                        ServerSocket sc = new ServerSocket(25896);
                        Socket s = sc.accept();
                        Scanner scan = new Scanner(s.getInputStream());
                        if(Servers.A.isBusy()) AddPoint(scan.nextLine(), Servers.A);
                        scan.close();
                        s.close();
                        sc.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        }
    }

    static class ServerB extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    ServerSocket sc = new ServerSocket(25897);
                        Socket s = sc.accept();
                        Scanner scan = new Scanner(s.getInputStream());
                        if(Servers.B.isBusy()) AddPoint(scan.nextLine(), Servers.B);
                        scan.close();
                        s.close();
                    sc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class ServerC extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    ServerSocket sc = new ServerSocket(25898);
                        Socket s = sc.accept();
                        Scanner scan = new Scanner(s.getInputStream());
                        if(Servers.C.isBusy())AddPoint(scan.nextLine(), Servers.C);
                        scan.close();
                        s.close();
                    sc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class ServerD extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    ServerSocket sc = new ServerSocket(25899);
                    if(Servers.D.isBusy()) {
                        Socket s = sc.accept();
                        Scanner scan = new Scanner(s.getInputStream());
                        if(Servers.D.isBusy())AddPoint(scan.nextLine(), Servers.D);
                        scan.close();
                        s.close();
                    }
                    sc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    static class ServAdmin extends Thread{
        @Override
        public void run() {
            while (true) {
                try {
                    ServerSocket sc = new ServerSocket(25800);
                    Socket s = sc.accept();
                    Scanner scan = new Scanner(s.getInputStream());
                    PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
                    String command = scan.nextLine();
                    System.out.println(command);
                    if(command.equals("123456pass")){
                        for (int i = 0; i<Servers.values().length; i++){
                            if (!Servers.values()[i].isBusy()){
                                pw.println(ports[i]);
                                System.out.println(ports[i]);
                                Servers.values()[i].setBusy(true);
                                System.out.println("Подключён судья " + Servers.values()[i]);
                                break;
                            }
                        }
                        pw.println("X");
                    }
                    if(command.contains("123456NoPass:")){
                        String port = command.substring(command.indexOf(':') + 1);
                        for(int i = 0; i< ports.length; i++){
                            if(port.equals(ports[i])){
                                Servers.values()[i].setScoreRED(0);
                                Servers.values()[i].setScoreWHITE(0);
                                Servers.values()[i].setReseted(true);
                                Servers.values()[i].setBusy(false);
                                System.out.println("Отключён судья " + Servers.values()[i]);
                                break;
                            }
                        }
                    }
                    if(command.equals("checkerClosed")){
                        Checker = false;
                    }
                    if(command.contains("isEnd")){
                        int port = Integer.parseInt(command.substring(command.indexOf(':') + 1));
                        Servers serv = getServerByPort(port);
                        assert serv!=null;
                        if(isEnd && !serv.isReseted()){
                            pw.println("yep");
                            serv.setReseted(true);
                            System.out.println(Servers.numResetted + " " + Servers.numJudges);
                            if(Servers.numResetted == Servers.numJudges){
                                isEnd = false;
                                for(int i = 0; i<Servers.values().length; i++){
                                    if(Servers.values()[i].isBusy()) Servers.values()[i].setReseted(false);
                                }
                                controller.IsEnd(false);
                            }
                        } else {
                            pw.println("nah");
                        }
                    }
                    pw.close();
                    scan.close();
                    s.close();
                    sc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        public static Servers getServerByPort(int port){
            for (int i=0;i<ports.length;i++){
                if(port == Integer.parseInt(ports[i])){
                    return Servers.values()[i];
                }
            }
            return null;
        }
    }
    public static void ResetData(){
        for(int i=0; i< Servers.values().length; i++){
            Servers.values()[i].setReseted(false);
        }
        Servers.numResetted = 0;
        isEnd = true;
        controller.IsEnd(true);
    }
    public static void DeactivateJudge(Servers judge){
        judge.setScoreWHITE(0);
        judge.setScoreRED(0);
        judge.setBusy(false);
        System.out.println("Отключён судья " + judge);

    }
    static class ServChecker extends Thread{
        @Override
        public void run() {
            while (true) {
                try {
                    ServerSocket sc = new ServerSocket(25801);
                    Socket s = sc.accept();
                    Scanner scan = new Scanner(s.getInputStream());
                    PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
                    String command = scan.nextLine();
                    if(command.equals("getJudges")){
                        Checker = true;
                        while (Checker) {
                            String ans = "";
                            for (int i = 0; i < Servers.values().length; i++) {
                                ans += Servers.values()[i];
                                ans += ":";
                                ans += Servers.values()[i].getScoreWHITE();
                                ans += ",";
                                ans += Servers.values()[i].getScoreRED();
                                ans += ",";
                                ans += Servers.values()[i].isBusy();

                            }
                            ans += ",";
                            ans += isEnd;
                            pw.println(ans);
                        }
                        System.out.println("closed");
                    }
                    pw.close();
                    scan.close();
                    s.close();
                    sc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static void AddPoint(String team, Servers Judje){
        int pointsRED = Integer.parseInt(team.substring(team.indexOf(':')+ 1, team.indexOf(',')));
        int pointsWHITE = Integer.parseInt(team.substring(team.indexOf(',')+ 1));
        Judje.setScoreRED(pointsRED);
        Judje.setScoreWHITE(pointsWHITE);
        WHITE_points = Servers.summWHITE;
        RED_points = Servers.summRED;

        NewScore(Judje);
    }

    static JFrame frame = new JFrame();
    static Container cont = frame.getContentPane();
    static JLabel score = new JLabel("Счёт 0:0 ");
    static JLabel scoreA = new JLabel("Счёт 0:0 судьи А");
    static JLabel scoreB = new JLabel("Счёт 0:0 судьи B");
    static JLabel scoreC = new JLabel("Счёт 0:0 судьи C");
    static JLabel scoreD = new JLabel("Счёт 0:0 судьи D");

    static void NewScore(Servers Judje){
        score.setText("Счёт: " + WHITE_points + ":" + RED_points);
        switch (Judje){
            case A:
                scoreA.setText("Счёт судьи А: " + Judje.getScoreWHITE() + ":" + Judje.getScoreRED());
                break;
            case B:
                scoreB.setText("Счёт судьи B: " + Judje.getScoreWHITE() + ":" + Judje.getScoreRED());
                break;
            case C:
                scoreC.setText("Счёт судьи C: " + Judje.getScoreWHITE() + ":" + Judje.getScoreRED());
                break;
            case D:
                scoreD.setText("Счёт судьи D: " + Judje.getScoreWHITE() + ":" + Judje.getScoreRED());
                break;
        }
        cont.setVisible(false);
        cont.setVisible(true);
    }


    public static void startServers(){
        System.out.println("AAAAAAAAAAAAAAAAAAAvws");
        ServerA A = new ServerA();
        ServerB B = new ServerB();
        ServerC C = new ServerC();
        ServerD D = new ServerD();
        A.start();
        B.start();
        C.start();
        D.start();
        ServAdmin admin = new ServAdmin();
        admin.start();
        frame.setBounds(0, 500, 1000, 500);
        frame.setAlwaysOnTop(true);
        frame.setLayout(new GridLayout());
        cont.add(score);
        cont.add(scoreA);
        cont.add(scoreB);
        cont.add(scoreC);
        cont.add(scoreD);
        //frame.setVisible(true);

    }

}
