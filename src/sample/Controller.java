package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;

import java.awt.*;

public class Controller {

    //UdpBroadcastServer udpBroadcastServer;
    
    @FXML
    ListView<String> viewVerticesList;

    @FXML
    TableView<Message> inputLogTable;

    @FXML
    Canvas canvas;

    @FXML
    ToggleButton ClearTable;

    @FXML
    ToggleButton toggleBtn;


    GraphicsContext graphicsContext;
    private UdpReceiver udpReceiver;
    private double currentX = 200;
    private double currentY = 200;
    private double speed = 30;


    public void initialize() {
        graphicsContext = canvas.getGraphicsContext2D();
   //     udpBroadcastServer = new UdpBroadcastServer(); //an instance of the udpbroadcastserver
   //     new Thread(udpBroadcastServer).start(); //start broadcast server in thread
    }

    public Controller() {
        udpReceiver = new UdpReceiver(this);
        new Thread(udpReceiver).start();
    }

    public void toggleBtnDrone(ActionEvent actionEvent) {

        System.out.println("Toggle Drone button");

        if (udpReceiver.isReceiveMessages()) {
            udpReceiver.setReceiveMessages(false);
            toggleBtn.setText("OFF");
            System.out.println("OFF");

        } else {

            udpReceiver.setReceiveMessages(true);
            new Thread(udpReceiver).start();
            toggleBtn.setText("ON");
            System.out.println("ON");
        }

    }

    public void clearTable() {
        inputLogTable.getItems().clear();
    }


    public void handleMessage(Message message) {
        if (inputLogTable != null) {
            inputLogTable.getItems().add(0, message);
        }

       String command = message.getCommand();

        switch (command) {

            case "init":
                String x = message.getParam1();
                String y = message.getParam2();
              //  currentX = Double.parseDouble(x);
              //  currentY = Double.parseDouble(y);
                drawCircle();
                break;

            case "moveup":
                clearCircle();
                currentY -= speed;
                drawCircle();
                break;

            case "movedown":
                clearCircle();
                currentY += speed;
                drawCircle();
                break;

            case "moveleft":
                clearCircle();
                currentX -= speed;
                drawCircle();
                break;

            case "moveright":
                clearCircle();
                currentX += speed;
                drawCircle();
                break;
        }
    }

    private void drawCircle () {
        if (graphicsContext != null) {
            graphicsContext.strokeOval(currentX - 30, currentY - 30, 30, 30);
        }
        if (viewVerticesList != null) {
            viewVerticesList.getItems().add("Vertex {x=" + currentX + ", y= " + currentY + "}");
        }
    }

    private void clearCircle () {
        if (graphicsContext != null) {
            graphicsContext.clearRect(currentX - 30, currentY - 30, 40, 40);
        }
    }



    public void receivePacket(Message message) {
    }
}