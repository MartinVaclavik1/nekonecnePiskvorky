/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ticktacktoe;

import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.BiConsumer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.util.Pair;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private GridPane grid;
    private int counter = 0;
    private LinkedList<Pair<Integer, Integer>> pozice = new LinkedList<>();
    private boolean prvniIterace = true;
    private int maxPocet = 6;
    private boolean prvniKontrola = true;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        grid.setAlignment(Pos.CENTER);
        grid.getCellBounds(10, 10);

    }

    @FXML
    private void onClick(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            zjistiOddil(x, y);
        }
    }

    private void zjistiOddil(int x, int y) {
        int sirka = 0;
        int vyska = 0;
        if (x < grid.getWidth() / 3) {
//            System.out.println("levo");
            sirka = 0;
        } else if (x > 2 * grid.getWidth() / 3) {
//            System.out.println("pravo");
            sirka = 2;
        } else {
//            System.out.println("střed");
            sirka = 1;
        }

        if (y < grid.getHeight() / 3) {
//            System.out.println("nahoře");
            vyska = 0;
        } else if (y > 2 * grid.getHeight() / 3) {
//            System.out.println("dole");
            vyska = 2;
        } else {
//            System.out.println("střed");
            vyska = 1;
        }

        vlozTvar(sirka, vyska);
        if (pozice.size() >= maxPocet - 1) {
            kontrolaVyhry();
        }
    }

    private void vlozTvar(int sirka, int vyska) {

        if (pozice.contains(new Pair(sirka, vyska))) {
            System.out.println("plné pole");
            return;
        }

        if (pozice.size() == maxPocet - 1) {
            Node node = grid.getChildren().get(1);
            grid.getChildren().remove(1);
            if (node instanceof Circle) {
                vlozKruh(Color.RED, pozice.getFirst().getKey(), pozice.getFirst().getValue());
            } else {
                vlozKriz(Color.RED, pozice.getFirst().getKey(), pozice.getFirst().getValue());
            }
        }

        if (pozice.size() == maxPocet) {

            grid.getChildren().remove(maxPocet - 1);

            pozice.remove(pozice.getFirst());

            grid.getChildren().remove(1);
            if (grid.getChildren().get(2) instanceof Circle) {
                vlozKruh(Color.RED, pozice.getFirst().getKey(), pozice.getFirst().getValue());
            } else {
                vlozKriz(Color.RED, pozice.getFirst().getKey(), pozice.getFirst().getValue());
            }
        }

        pozice.add(new Pair(sirka, vyska));

        System.out.println(pozice);

        if (counter % 2 == 0) {

            vlozKruh(Color.BLACK, sirka, vyska);

        } else {

            vlozKriz(Color.BLACK, sirka, vyska);

        }
        counter++;

    }

    private void vlozKruh(Color barva, int sirka, int vyska) {
        Circle kruh = new Circle(50);
        kruh.setFill(Color.TRANSPARENT);
        kruh.setStroke(barva);
        kruh.setStrokeWidth(10);
        grid.setHalignment(kruh, HPos.CENTER);
        grid.setValignment(kruh, VPos.CENTER);
        grid.add(kruh, sirka, vyska);
    }

    private void vlozKriz(Color barva, int sirka, int vyska) {
        Line line1 = new Line(grid.getWidth() / 6, grid.getHeight() / 6, grid.getWidth() / 3, grid.getHeight() / 3);
        Line line2 = new Line(grid.getWidth() / 6, grid.getHeight() / 3, grid.getWidth() / 3, grid.getHeight() / 6);

        line1.setStrokeWidth(10);
        line2.setStrokeWidth(10);
        Shape shape = Shape.union(line1, line2);
        shape.setFill(barva);
//            grid.setHalignment(line1, HPos.CENTER);
//            grid.setValignment(line1, VPos.CENTER);
//            grid.setHalignment(line2, HPos.CENTER);
//            grid.setValignment(line2, VPos.CENTER);
        grid.setHalignment(shape, HPos.CENTER);
        grid.setValignment(shape, VPos.CENTER);
//            grid.add(line1, sirka, vyska);
//            grid.add(line2, sirka, vyska);
        grid.add(shape, sirka, vyska);
    }

    //poslední vložený tvar - vzít lokaci, zkontrolovat, zda v sousedních buňkách
    //je stejný tvar a když jo, tak podle lokací těhle 2 zjistit, kde by měl být
    //třetí tvar (buď u posledního vloženého, nebo teď kontrolovaného)
    //když je tam, tak se hra ukončí s popupem o tom kdo vyhrál
    private void kontrolaVyhry() {
        Iterator iterator = pozice.iterator();
        int rozdelovac = 0;
        Pair<Integer, Integer>[] data = new Pair[3];
        int posledni = 0;

        while (iterator.hasNext()) {
            if (rozdelovac % 2 == 0) {

                //kontrola jednoho hráče - nutné jen při 5 prvcích
                if (prvniKontrola) {    //TODO
                    data[posledni++] = (Pair<Integer, Integer>) iterator.next();
                } else {
                    iterator.next();
                }

            } else {//TODO
                //kontrola hráče, když je 6 prvků
                if (prvniKontrola) {
                    iterator.next();
                } else {
                    data[posledni++] = (Pair<Integer, Integer>) iterator.next();
                }
            }
            rozdelovac++;
        }
        prvniKontrola = false;

        zkontrolujVstup(data);

    }

    private void zkontrolujVstup(Pair<Integer, Integer>[] data) {

        int rozdilKey1 = data[2].getKey() - data[1].getKey(); //0 = stejná pozice, 1/-1 = vedlejší pozice, 2/-2 = nesousedící pozice
        int rozdilValue1 = data[2].getValue() - data[1].getValue();

        int rozdilKey0 = data[2].getKey() - data[0].getKey();
        int rozdilValue0 = data[2].getValue() - data[0].getValue();

        //odečíst od data[1] rozdilkey1 a value pro získání lokace data[0]
        //přičíst k data[2] - same výsledek
        if (Math.abs(rozdilKey1) <= 1 && Math.abs(rozdilValue1) <= 1) {
            System.out.println("vedle sebe");

            if (data[1].getKey() - rozdilKey1 == data[0].getKey() && data[1].getValue() - rozdilValue1 == data[0].getValue()) {
                vyhra();
            } else if (data[2].getKey() + rozdilKey1 == data[0].getKey() && data[2].getValue() + rozdilValue1 == data[0].getValue()) {
                vyhra();
            }

        } else if (Math.abs(rozdilKey0) <= 1 && Math.abs(rozdilValue0) <= 1) {
            System.out.println("taky vedle sebe, ale ne s předposledním prvkem (lame)");

            if (data[0].getKey() - rozdilKey0 == data[1].getKey() && data[0].getValue() - rozdilValue0 == data[1].getValue()) {
                vyhra();

            } else if (data[2].getKey() + rozdilKey0 == data[1].getKey() && data[2].getValue() + rozdilValue0 == data[1].getValue()) {
                vyhra();
            }
        }

    }

    private void vyhra() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Konec hry. Chcete hrát znovu?");
        
        Alert promptNaVypnuti = new Alert(Alert.AlertType.CONFIRMATION);
        promptNaVypnuti.setHeaderText("Chcete vypnout aplikaci?");
        
        Optional vysledek = alert.showAndWait();
        if (vysledek != null) {
            if (vysledek.get() == ButtonType.OK) {

                for (int i = 0; i < 6; i++) {
                    try {
                        grid.getChildren().remove(1);
                    } catch (Exception x) {
                        System.out.println("out of bounds");
                        break;
                    }
                }
                pozice.clear();
                prvniKontrola = true;
                prvniIterace = true;
                counter = 0;
                
            } else if (vysledek.get() == ButtonType.CANCEL) {
                
                Optional vypnuti = promptNaVypnuti.showAndWait();
                if(vypnuti!= null){
                    if(vypnuti.get() == ButtonType.OK)
                        System.exit(0);
                }
                
            }
        }
    }

}
