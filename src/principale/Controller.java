package principale;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class Controller{
    private int untitledFileNumber;
    private HashMap<String,String> FichiersEnregistrer;
    private String cheminDefaut = new File("").getAbsolutePath();
    @FXML
    private MenuItem btn_Copier;
    @FXML
    private MenuItem btn_Enregistrer;
    @FXML
    private MenuItem btn_Quitter;
    @FXML
    private MenuItem btn_Importer;

    @FXML
    private MenuItem btn_SelectionnerCopier;
    @FXML
    private Slider s;
    @FXML
    private TextArea code_Source;
    @FXML
    private MenuItem btn_creerFichier;
    @FXML
    private TabPane tabbedEditors;

    public Controller() {
    }

    @FXML
    public void onClick_btn_creerFichier(ActionEvent e){

        Tab nouveauOnglet = new Tab();
        this.untitledFileNumber +=1;
        tabbedEditors.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        nouveauOnglet.setClosable(true);
        TextArea code = new TextArea();
        code.setId("untitled "+this.untitledFileNumber);

        code.setText("algorithme 'nomAlgorithme' \n" +
                "\n" +
                "debut\n" +
                "\n" +
                "fin");

        nouveauOnglet.setText("untitled "+this.untitledFileNumber+".algo");
        nouveauOnglet.setId("untitled "+this.untitledFileNumber);
        nouveauOnglet.setContent(code);
        tabbedEditors.getTabs().add(nouveauOnglet);
        SingleSelectionModel<Tab> selection = tabbedEditors.getSelectionModel();
        selection.select(nouveauOnglet);
    }

    @FXML
    public void onClick_btn_Copier(ActionEvent e){

        String texte = code_Source.getSelectedText();
        final Clipboard presse_Papier = Clipboard.getSystemClipboard();
        final ClipboardContent Contenu_Presse_Papier = new ClipboardContent();

        Contenu_Presse_Papier.putString(texte);
        presse_Papier.setContent(Contenu_Presse_Papier);
    }
    @FXML
    public void onClick_Enregistrer(ActionEvent e) throws IOException {

        File repertoireSelectionner=null;

        FileChooser repertoireChoisi = new FileChooser();
        String nomNouveauFichier = tabbedEditors.getSelectionModel().getSelectedItem().getText();
        repertoireChoisi.setInitialFileName(nomNouveauFichier);

        if(new File(new File("").getAbsolutePath()+"/"+nomNouveauFichier).exists()){
            System.out.println("oui oui");
            code_Source = (TextArea) tabbedEditors.getSelectionModel().getSelectedItem().getContent();
            System.out.println(code_Source.getText());
            String chemin = this.cheminDefaut;

            BufferedWriter BR = new BufferedWriter(new FileWriter(cheminDefaut+"/"+nomNouveauFichier));
            StringBuilder texte = new StringBuilder(code_Source.getText());
            BR.write(texte.toString());
            BR.close();
        }
        else {
            FileChooser.ExtensionFilter fichierExtension =
                    new FileChooser.ExtensionFilter("fichiers algo (*.algo)", "*.algo");
            repertoireChoisi.getExtensionFilters().add(fichierExtension);
            repertoireSelectionner = repertoireChoisi.showSaveDialog(null);
            code_Source = (TextArea) tabbedEditors.getSelectionModel().getSelectedItem().getContent();
            if(repertoireSelectionner!=null){

                String chemin = repertoireSelectionner.getAbsolutePath();
                BufferedWriter BR = new BufferedWriter(new FileWriter(chemin));
                BR.write(code_Source.getText());
                BR.close();
                System.out.println("cooll");
            }
        }

        System.out.println(tabbedEditors.getSelectionModel().getSelectedItem().getText());
    }

    @FXML
    public void onClick_Importer(ActionEvent e) throws IOException {

        Tab nouveauOnglet = new Tab();
        TextArea code = new TextArea();
        Stage scene = new Stage();
        FileChooser fichierChoisi = new FileChooser();
        fichierChoisi.setTitle("Fichier ouvert");
        File fichierSelectionner = fichierChoisi.showOpenDialog(scene);

        code.setId(fichierSelectionner.getName());
        nouveauOnglet.setText(fichierSelectionner.getName());
        nouveauOnglet.setId(fichierSelectionner.getName());

        if (fichierSelectionner!=null)
        {
            FileReader FR = new FileReader(fichierSelectionner.getAbsolutePath());
            BufferedReader BR = new BufferedReader(FR);
            StringBuilder sb = new StringBuilder();
            String texte ="";
            while ((texte = BR.readLine())!= null){
                sb.append(texte +"\n");
            }
            code.setText(sb.toString());
            nouveauOnglet.setContent(code);
            tabbedEditors.getTabs().add(nouveauOnglet);
            SingleSelectionModel<Tab> selection = tabbedEditors.getSelectionModel();
            selection.select(nouveauOnglet);
        }
    }
    public void onClick_Quitter(ActionEvent e){
        System.out.println("exit");
        Platform.exit();
    }
    @FXML
    public void onClick_SelectionnerCopier(ActionEvent e){
        String texte = code_Source.getSelectedText();
        final Clipboard presse_Papier = Clipboard.getSystemClipboard();
        final ClipboardContent Contenu_Presse_Papier = new ClipboardContent();

        Contenu_Presse_Papier.putString(texte);
        presse_Papier.setContent(Contenu_Presse_Papier);
    }

}
