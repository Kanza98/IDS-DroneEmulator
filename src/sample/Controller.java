package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;


public class Controller {

    /*
    Sets our necessary FXML
     */

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

            case "moveup":
                if (currentY <= 20) {
                    break;
                }
                currentY -= speed;
                break;

            case "movedown":
                if (currentY >= 300) {
                    break;
                }
                currentY += speed;
                break;

            case "moveleft":
                if (currentX <= 30) {
                    break;
                }
                currentX -= speed;
                break;

            case "moveright":
                if (currentX >= 300) {
                    break;
                }
                currentX += speed;
                break;

            case "stop":
                break;

        }
    }




}