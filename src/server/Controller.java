package server;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class Controller {

    public AnchorPane anchor;
    public ImageView tatami;
    public GridPane grid;
    public ImageView chairA;
    public ImageView chairB;
    public ImageView chairC;
    public ImageView chairD;
    public TextFlow scoreA;
    public Text scoreA_WHITE;
    public Text scoreA_RED;
    public TextFlow scoreB;
    public Text scoreB_WHITE;
    public Text scoreB_RED;
    public TextFlow scoreC;
    public Text scoreC_WHITE;
    public Text scoreC_RED;
    public TextFlow scoreD;
    public Text scoreD_WHITE;
    public Text scoreD_RED;
    public TextFlow mid;
    public Text midWHITE;
    public Text midRED;
    public Text roundstat;
    public static int numFight = 1;



    public static void init() {


    }
    void OnWin(){
        for(int i = 0; i<3; i++){
            scoreA.getChildren().get(i).setStyle("-fx-fill: "  + Servers.A.getWinnerPersonal() + ";");
        }
        for(int i = 0; i<3; i++){
            scoreB.getChildren().get(i).setStyle("-fx-fill: "  + Servers.B.getWinnerPersonal() + ";");
        }
        for(int i = 0; i<3; i++){
            scoreC.getChildren().get(i).setStyle("-fx-fill: "  + Servers.C.getWinnerPersonal() + ";");
        }
        for(int i = 0; i<3; i++){
            scoreD.getChildren().get(i).setStyle("-fx-fill: "  + Servers.D.getWinnerPersonal() + ";");
        }
        if(Servers.getMidWHITE() > Servers.getMidRED()){
            for(int i = 0; i<3; i++){
                mid.getChildren().get(i).setStyle("-fx-fill: white;");
            }
        } else if(Servers.getMidRED()> Servers.getMidWHITE()){
            for(int i = 0; i<3; i++){
                mid.getChildren().get(i).setStyle("-fx-fill: red;");
            }
        } else{
            for(int i = 0; i<3; i++){
                mid.getChildren().get(i).setStyle("-fx-fill: gray;");
            }
        }
    }
    void OnClose(){
        for(int i = 0; i<3; i++){
            scoreA.getChildren().get(i).setStyle("");
        }
        for(int i = 0; i<3; i++){
            scoreB.getChildren().get(i).setStyle("");
        }
        for(int i = 0; i<3; i++){
            scoreC.getChildren().get(i).setStyle("");
        }
        for(int i = 0; i<3; i++){
            scoreD.getChildren().get(i).setStyle("");
        }
        for(int i = 0; i<3; i++){
            mid.getChildren().get(i).setStyle("");
        }
    }


    public void contextMenu(ContextMenuEvent contextMenuEvent) {
        ContextMenu dis = new ContextMenu();
        javafx.scene.control.MenuItem A = new javafx.scene.control.MenuItem("Отключить судью А");
        javafx.scene.control.MenuItem B = new javafx.scene.control.MenuItem("Отключить судью B");
        javafx.scene.control.MenuItem C = new javafx.scene.control.MenuItem("Отключить судью C");
        javafx.scene.control.MenuItem D = new javafx.scene.control.MenuItem("Отключить судью D");
        javafx.scene.control.MenuItem test = new javafx.scene.control.MenuItem("test");
        A.setOnAction(event -> requestDisable(Servers.A));
        B.setOnAction(event -> requestDisable(Servers.B));
        C.setOnAction(event -> requestDisable(Servers.C));
        D.setOnAction(event -> requestDisable(Servers.D));
        test.setOnAction(event -> {
            Servers.A.setScoreRED(50);
            Servers.A.setScoreWHITE(50);
        });
        dis.getItems().add(A);
        dis.getItems().add(B);
        dis.getItems().add(C);
        dis.getItems().add(D);
        dis.getItems().add(test);
        dis.show(Main.primaryStage);
    }
    void requestDisable(Servers server){
        Server.DeactivateJudge(server);
    }

    public void results(ActionEvent actionEvent) {
        OnWin();
        Alert al = new Alert(Alert.AlertType.INFORMATION);
        al.setTitle("Раунд окончен");
        String msg = Servers.getWinner();
        if(Servers.getWinner().equals("Ничья")) {
            msg = "Ничья, по среднему значению ";
            if(Servers.getMidWHITE()> Servers.getMidRED()) msg += "победил белый";
            else if(Servers.getMidRED()> Servers.getMidWHITE()) msg += "победил красный";
            else msg+= "всё равно ничья, так что до 1 технического";
        }
        al.setHeaderText(msg);
        al.setContentText("Среднее значение: " + Servers.getMidWHITE() + ":" + Servers.getMidRED());
        al.setOnCloseRequest(event -> {
            Server.ResetData();
            OnClose();
        });
        al.showAndWait();
        numFight++;
//        try {
//            BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
//            ImageIO.write(image, "png", new File("D:\\Users\\Max\\программирование\\IdeaProjects\\test\\FightScreens\\Fight_" + numFight + ".png"));
//            numFight++;
//        } catch (AWTException | IOException awtException) {
//            Alert err = new Alert(Alert.AlertType.ERROR);
//            err.setTitle("ошибка");
//            err.setHeaderText("Ошика при создании скриншота");
//            err.showAndWait();
//        }
    }

    public void ChangeInfo(Servers who){
        switch (who) {
            case A:
                Platform.runLater(()->{
                    scoreA_WHITE.setText(String.valueOf(Servers.A.getScoreWHITE()));
                    scoreA_RED.setText(String.valueOf(Servers.A.getScoreRED()));
                    if (Servers.A.isBusy()) chairA.setImage(new Image("server/img/Judge1.png"));
                    else chairA.setImage(new Image("server/img/Chair1.png"));
                });

                case B:
                    Platform.runLater(()->{
                        scoreB_WHITE.setText(String.valueOf(Servers.B.getScoreWHITE()));
                        scoreB_RED.setText(String.valueOf(Servers.B.getScoreRED()));
                        if (Servers.B.isBusy()) chairB.setImage(new Image("server/img/Judge2.png"));
                        else chairB.setImage(new Image("server/img/Chair2.png"));
                    });
                    case C:
                        Platform.runLater(()->{
                            scoreC_WHITE.setText(String.valueOf(Servers.C.getScoreWHITE()));
                            scoreC_RED.setText(String.valueOf(Servers.C.getScoreRED()));
                            if (Servers.C.isBusy()) chairC.setImage(new Image("server/img/Judge1.png"));
                            else chairC.setImage(new Image("server/img/Chair1.png"));
                        });
                    case D:
                        Platform.runLater(()->{
                            scoreD_WHITE.setText(String.valueOf(Servers.D.getScoreWHITE()));
                            scoreD_RED.setText(String.valueOf(Servers.D.getScoreRED()));
                            if (Servers.D.isBusy()) chairD.setImage(new Image("server/img/Judge2.png"));
                            else chairD.setImage(new Image("server/img/Chair2.png"));
                        });
                }
                Platform.runLater(()->{
                    midWHITE.setText(String.valueOf(Servers.getMidWHITE()));
                    midRED.setText(String.valueOf(Servers.getMidRED()));
                });

            }
//        scoreA_RED.textProperty().bind(taskA.messageProperty());
//        scoreA_WHITE.textProperty().bind(taskB.messageProperty());
//        if(isRed) new Thread(taskA).start();
//        else new Thread(taskB).start();
//        Thread th = new Thread(() -> {

//        });
//        th.start();




    public void IsEnd(boolean IsEnd){
        roundstat.setText(IsEnd ? "ОЖИДАНИЕ СБРОССА СЧЁТА":"НОМЕР БОЯ: " + numFight);
    }

    public void test(MouseEvent mouseEvent) {
    }
}
