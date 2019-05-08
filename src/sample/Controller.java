package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import Knapsack.Knapsack;
import Knapsack.BigInt;


import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
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

    private List<BigInt> createListFromString(String str){
        str = str.replace("[", "");
        str = str.replace("]", "");
        List<String> numbers = Arrays.asList(str.split("\\s*,\\s*"));
        List<BigInt> result = new LinkedList<>();

        for (String num : numbers ) {
            try{
                long bigInt = Long.parseLong(num);
                result.add(new BigInt(bigInt));
            }
            catch (NumberFormatException e){
                System.out.println("Niepoprawna liczba");
                return null;
            }
        }
        return result;
    }

    @FXML
    private Button decryptTextButton;

    @FXML
    private Label fileNameLabel;

    @FXML
    private TextField mText;

    @FXML
    private TextField nText;

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
        TextInputDialog dialog = new TextInputDialog("15");
        dialog.setTitle("Podaj ilość elementów klucza");
        dialog.setHeaderText("Podaj:");
        dialog.setContentText("Ile");

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

    public void generatePublic(){
        if(mText.getText().trim().isEmpty()){
            System.out.println("Brak parametru M");
            return;
        }

        if(nText.getText().trim().isEmpty()){
            System.out.println("Brak parametru N");
            return;
        }

        if(privateKeyText.getText().trim().isEmpty()){
            System.out.println("Klucz prywatny pusty");
            return;
        }

        if(createListFromString(privateKeyText.getText()) == null){
            return;
        }
        privateKey = createListFromString(privateKeyText.getText());

        try{
            BigInt m = new BigInt(Long.parseLong(mText.getText()));
            BigInt n = new BigInt(Long.parseLong(nText.getText()));
            publicKey = Knapsack.createPublicKey(privateKey, n, m);
        }

        catch (NumberFormatException e){
            System.out.println("Błędny parametr M lub N");
            return;
        }

        catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            return;
        }

        publicKeyText.setText(publicKey.toString());

    System.out.println(Integer.toBinaryString('s'));
        System.out.println(Integer.toBinaryString('1'));
        System.out.println(Integer.toBinaryString('a'));
    }
}
