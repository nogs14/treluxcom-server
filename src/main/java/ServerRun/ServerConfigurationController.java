package ServerRun;

import com.treluxcom.dao.AdministrateurHomeImpl;
import com.treluxcom.dao.BoutiqueHomeImpl;
import com.treluxcom.dao.CaissierHomeImpl;
import com.treluxcom.dao.ClientHomeImpl;
import com.treluxcom.dao.CommandeHomeImpl;
import com.treluxcom.dao.DevisHomeImpl;
import com.treluxcom.dao.EmployeHomeImpl;
import com.treluxcom.dao.FamilleHomeImpl;
import com.treluxcom.dao.FileTransferImpl;
import com.treluxcom.dao.FournisseurHomeImpl;
import com.treluxcom.dao.GerantHomeImpl;
import com.treluxcom.dao.LignecommandeHomeImpl;
import com.treluxcom.dao.LignedevisHomeImpl;
import com.treluxcom.dao.LivreurHomeImpl;
import com.treluxcom.dao.PanierHomeImpl;
import com.treluxcom.dao.PaniercaissierHomeImpl;
import com.treluxcom.dao.PanierclientHomeImpl;
import com.treluxcom.dao.PersonneHomeImpl;
import com.treluxcom.dao.ProduitHomeImpl;
import com.treluxcom.dao.StatistiquesImpl;
import com.treluxcom.dao.StockHomeImpl;
import com.treluxcom.service.Statistiques;
import static java.lang.System.out;
import java.net.URL;
import java.rmi.AlreadyBoundException;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class ServerConfigurationController implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;
    @FXML
    private TextField adresseip;
    @FXML
    private AnchorPane parent;
    @FXML
    private TextField port;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            makeStageDrageable();

        } catch (Exception ex) {
            Logger.getLogger(ServerConfigurationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void makeStageDrageable() {
        parent.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        parent.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ServerRun.stage.setX(event.getScreenX() - xOffset);
                ServerRun.stage.setY(event.getScreenY() - yOffset);
                ServerRun.stage.setOpacity(0.7f);
            }
        });
        parent.setOnDragDone((e) -> {
            ServerRun.stage.setOpacity(1.0f);
        });
        parent.setOnMouseReleased((e) -> {
            ServerRun.stage.setOpacity(1.0f);
        });

    }
    Registry registry;

    @FXML
    private void btnStop(ActionEvent event) {
        try {
            UnicastRemoteObject.unexportObject(registry, false);
        } catch (NoSuchObjectException noSuchObjectException) {
            Notification.notificationErr("Erreur", "Aucun objet n'est publie...");
        }
        System.exit(0);
    }

    @FXML
    private void btnDemarrer(ActionEvent event) {
        if (adresseip.getText().isEmpty()) {
            Notification.notificationErr("Erreur", "Renseigner l'adresse ip du Serveur");
        } else if (port.getText().isEmpty()) {
            Notification.notificationErr("Erreur", "Renseigner le numero de port du Serveur");
        } else {
            try {
                out.println("Demarrage serveur a l'adresse " + adresseip.getText() + "...");
                System.setProperty("java.rmi.server.hostname", adresseip.getText());
                AdministrateurHomeImpl admin = new AdministrateurHomeImpl();
                out.println("Demarrage serveur...");
                Statistiques stats = new StatistiquesImpl();
                registry = LocateRegistry.createRegistry(Integer.parseInt(port.getText()));
                registry.bind("rmiPersonneHome", new PersonneHomeImpl());
                registry.bind("rmiAdministrateurHome", new AdministrateurHomeImpl());
                registry.bind("rmiEmployeHome", new EmployeHomeImpl());
                registry.bind("rmiClientHome", new ClientHomeImpl());
                registry.bind("rmiGerantHome", new GerantHomeImpl());
                registry.bind("rmiCaissierHome", new CaissierHomeImpl());
                registry.bind("rmiLivreurHome", new LivreurHomeImpl());
                registry.bind("rmiFournisseurHome", new FournisseurHomeImpl());
                registry.bind("rmiFamilleHome", new FamilleHomeImpl());
                registry.bind("rmiCommandeHome", new CommandeHomeImpl());
                registry.bind("rmiDevisHome", new DevisHomeImpl());
                registry.bind("rmiLignecommandeHome", new LignecommandeHomeImpl());
                registry.bind("rmiLignedevisHome", new LignedevisHomeImpl());
                registry.bind("rmiBoutiqueHome", new BoutiqueHomeImpl());
                registry.bind("rmiStockHome", new StockHomeImpl());
                registry.bind("rmiProduitHome", new ProduitHomeImpl());
                registry.bind("rmiPanierHome", new PanierHomeImpl());
                registry.bind("rmiPaniercaissierHome", new PaniercaissierHomeImpl());
                registry.bind("rmiPanierclientHome", new PanierclientHomeImpl());
                registry.bind("rmiFileTransfer", new FileTransferImpl());
                registry.bind("rmiStatistiques", stats);
                
                out.println("serveur pret");
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (ExportException se) {
                Notification.notificationErr("Erreur", "Les objets ont ete deja publies...");
            } catch (RemoteException ex) {
                Logger.getLogger(ServerConfigurationController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (AlreadyBoundException ex) {
                Logger.getLogger(ServerConfigurationController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
