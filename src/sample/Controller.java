package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import Knapsack.Knapsack;
import Knapsack.BigInt;


import java.io.File;
import java.util.List;
import java.util.Optional;

public class Controller {

    private List<BigInt> privateKey;
    private List<BigInt> publicKey;

    private FileChooser fileChooser = new FileChooser();

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        fileChooser.setTitle("Wybierz plik");
    }

    @FXML
    private Button decryptTextButton;

    @FXML
    private Label fileNameLabel;

    @FXML
    private TextField decryptNameLabel;

    @FXML
    private Button generatePrivateButton;

    @FXML
    private TextField cryptNameLabel;

    @FXML
    private Button chooseFileButton;

    @FXML
    private TextField publicKeyText;

    @FXML
    private TextArea cipherTextArea;

    @FXML
    private Button encryptTextButton;

    @FXML
    private Button generatePublicButton;

    @FXML
    private Button decryptFileButton;

    @FXML
    private TextArea plainTextArea;

    @FXML
    private TextField privateKeyText;

    @FXML
    private Button cryptFileButton;

    public void chooseFile(){

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            fileNameLabel.setText(file.getPath());
            cryptNameLabel.setText(file.getName() + ".enc");
            decryptNameLabel.setText(file.getName() + ".dec");
        }
        //if(fileChooser.ge)
    }

    public void generatePrivate(){
        TextInputDialog dialog = new TextInputDialog("walter");
        dialog.setTitle("Text Input Dialog");
        dialog.setHeaderText("Look, a Text Input Dialog");
        dialog.setContentText("Please enter your name:");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()){

            try{
                int count = Integer.parseInt(result.get());
                BigInt k = new BigInt(1);
                this.privateKey = Knapsack.generateSuperIncreasingSeq(k, count);
                privateKeyText.setText(privateKey.toString());
            }
            catch (NumberFormatException e){
                System.out.println("Niepoprawna liczba");
            }
        }
    }


}
