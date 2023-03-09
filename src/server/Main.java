package server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {
    public static Controller controller;
    public static Stage primaryStage;
    public static void n(String[] args) {
        Server.startServers();
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        Main.primaryStage = primaryStage;
        controller = loader.getController();
        Server.controller = controller;
        primaryStage.setTitle("Karatech Judge edition");
        primaryStage.setScene(new Scene(root, 800,800));
        //primaryStage.getIcons().add(new Image("server/img/icon.ico"));
        primaryStage.setOnShown(ee-> Controller.init());
        primaryStage.getIcons().add(0, new Image("server/icon.ico"));
        primaryStage.show();
        primaryStage.setOnCloseRequest(e->{System.exit(0);});
//        primaryStage.setOnCloseRequest(event -> {
//            try {
//                Socket s = new Socket("5.19.233.233", 25800);
//                PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
//                pw.println("checkerClosed");
//                pw.close();
//                s.close();
//            } catch (IOException ee) {
//                ee.printStackTrace();
//            }
//            finally {
//                System.exit(0);
//            }
//        });
    }
}

