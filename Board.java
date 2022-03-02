package practice;

import java.util.ArrayList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox ;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Board extends Parent {

    private VBox rows = new VBox();
    private boolean enemy = false; // Τσεκαρει αν ο πινακας ειναι ο δικος μας η του Υπολογιστη
    //Εάν το πλοίο τα κατάφερε να μπει στον πίνακα
    public int ships = 5; // Αριθμός πλοίων σε κάθε πίνακα. Shows who wins or not.

    public Board(boolean enemy, EventHandler<? super MouseEvent> handler) { //Για να μπορω να κλικαρω στα κουτακια
        this.enemy = enemy;
        for (int y = 0; y < 12; y++){         // Δημιουργια 10χ10 πινακας που κλικαρεται
            HBox row = new HBox();            //Δηλωση σειρας
            for (int x = 0; x < 12; x++){
                Cell c = new Cell(x,y,this);  //Δηλωση του καθε κελιου
                c.setOnMouseClicked(handler); //Να κλικαρεται
                row.getChildren().add(c);     //Προσθετει καθε κελι σε καθε σειρα
            }
            rows.getChildren().add(row);      //Προσθετει καθε σειρα στο VBox rows
        }
        getChildren().add(rows);              //Προσθετω ολο το VBox στο container
        
    }

    
    
    public boolean placeShip(Ship ship, int x, int y){  //Μέθοδος για να μπαίνουν τα πλοία.
        if (canPlaceShip(ship, x, y)){                  //Τσεκάρει την τοποθεσία για να δει άν γίνεται να μπει πλοίο εκεί.
            int length = ship.type;
            boolean vertical = ship.vertical;           //Μια μπούλεαν για κάθετη τοποθέτηση που στην Ship είναι αρχικά true.

            if (vertical){ //if it is vertical
                for (int i = y; i < y+length; i++)
                { //assigns the y values to a ship
                    Cell cell = getCell(x,i);
                    cell.ship = ship;
                    
                    if (!enemy){
                        cell.setFill(Color.DARKGRAY);   //Βάφουμε γκρι.
                    }
                }
            }
            else                                        //Εάν το πλοίο είναι οριζόντια. Βάφει πλοίο οριζόντια αναλόγως το length του ξεκινώντας από το x.
            {
                for(int i = x; i < x + length; i++)
                {
                    Cell cell = getCell(i, y);          //Τρέχων κελί στην επανάληψη.
                    cell.ship = ship;                   //Αναφέρεται στα κελιά που μπήκε το πλοίο.

                    if(!enemy){
                        cell.setFill(Color.DARKGRAY);   //Βάφουμε γκρι.
                    }
                }
            }
        return true;    //Ότι μπήκε το πλοίο
        }
    return false;       //Ότι δεν μπήκε το πλοίο.
    }

  

    
    public Cell getCell(int x, int y) {
        return (Cell)((HBox)rows.getChildren().get(y)).getChildren().get(x);  //returns the cell; get row first as a whole, set as HBox, and then get cell//////////////////////////
    }

    private Cell[] getNeighbors(int x, int y) {
        Point2D[] points = new Point2D[] {
                new Point2D(x - 1, y),
                new Point2D(x + 1, y),
                new Point2D(x, y - 1),
                new Point2D(x, y + 1)
        };

        List<Cell> neighbors = new ArrayList<Cell>();

        for (Point2D p : points) {
            if (isValidPoint(p)) {
                neighbors.add(getCell((int)p.getX(), (int)p.getY()));
            }
        }
        return neighbors.toArray(new Cell[0]);
    }

    /**
     * Method function: return an array of neighboring cells that are within the board
     * Used for BattleshipMain for smart AI
     * @param x
     * @param y
     * @return arraylist of neighboring cells
     */
    public ArrayList getNeighbors2(int x, int y) {
        Point2D[] points = new Point2D[]{     //Πίνακας από points γύρω από το επιλεγμένο κελί.
                new Point2D(x - 1, y),        //Αριστερά
                new Point2D(x + 1, y),        //Δεξιά
                new Point2D(x, y - 1),        //Πάνω
                new Point2D(x, y + 1)         //Κάτω
        };

        //List από γειτονικά τετράγωνα
        ArrayList<Cell> neighbors = new ArrayList<Cell>();

        for (Point2D p : points) {
            if (isValidPoint(p)) {
                neighbors.add(getCell((int) p.getX(), (int) p.getY()));     //Προσθέτουμε τα γειτονικά κελιά στην λίστα εάν είναι έγκυρα σημεία (isValidPoint).
            }
        }

        return neighbors;
    }

    private boolean canPlaceShip(Ship ship, int x, int y) {
        int length = ship.type;                     //Κάνουμε το length τοπική μεταβλητή.

        if (ship.vertical) {                        //Τσεκάρει αν το πλοίο είναι κάθετα.
            for (int i = y; i < y + length; i++) {  //Εάν το πλοίο βγαίνει Out of Bounds κάθετα με βάση το y + length.
                if (!isValidPoint(x, i))
                    return false;

                Cell cell = getCell(x, i);
                if (cell.ship != null)              //Εάν υπάρχει ήδη πλοίο στην θέση αυτή τότε επιστρέφει false.
                    return false;

                for (Cell neighbor : getNeighbors(x, i)) {
                    if (!isValidPoint(x, i))
                        return false;

                    if (neighbor.ship != null)
                        return false;               //Εάν υπάρχει κολλητά κάθετα άλλο πλοίο, επιστρέφει false.
                }
            }
        }
            //Εάν το πλοίο είναι οριζόντιο.
        else {
            for (int i = x; i < x + length; i++) {  //Εάν το πλοίο βγαίνει Out of Bounds οριζόντια με βάση το x + length.
                if (!isValidPoint(i, y))
                    return false;

                Cell cell = getCell(i, y);
                if (cell.ship != null)              //Εάν υπάρχει ήδη πλοίο στην θέση αυτή τότε επιστρέφει false.
                    return false;

                for (Cell neighbor : getNeighbors(i, y)) {
                    if (!isValidPoint(i, y))
                        return false;

                    if (neighbor.ship != null)
                        return false;               //Εάν υπάρχει κολλητά οριζόντια άλλο πλοίο, επιστρέφει false.
                }
            }
        }

        return true;
    }

    public boolean isValidPoint(Point2D point) {
        return isValidPoint(point.getX(), point.getY());
    }

    public boolean isValidPoint(double x, double y) {
        return x >= 0 && x < 12 && y >= 0 && y < 12;
    }

    


 

    public class Cell extends Rectangle { //Κάθε τετράγωνο στον πίνακα να είναι κελί.
    public int x,y;                       //Η θέση του κελιού.
    public Ship ship = null;
    public boolean wasShot = false; //Μπούλεαν για το κάθε κελί εάν έχει ήδη πυροβοληθεί.

    private Board board;

    public Cell(int x , int y, Board board) {       //Η θέση του κελιού στον πίνακα.
        super(40, 40);                              //Μέγεθος pixel των Cells.
        this.x = x;                                 //sets the variables equal to input/////////////////////////////////////////////////////////////////
        this.y = y;
        this.board = board;
        setFill(Color.TURQUOISE);                   //Χρώμα θάλασσας.
        setStroke(Color.BLACK);                     //Χρώμα γραμμών θάλασσας.
    }

    public int xCor()                  //Παίρνω Χ συντεταγμένες
        {
            return x;
        }

        public int yCor()              //Παίρνω Υ συντεταγμένες
        {
            return y;
        }

    public boolean shoot(){
        wasShot = true;                 //Αν το κελί έχει πυροβοληθεί. (εφόσον μπήκε στην shoot())
        if (ship != null) {             //Αν υπάρχει πλοίο εκεί που κλίκαρε ο παίκτης.
            ship.hit();
            setFill(Color.RED);         //Να βαφτεί κόκκινο το Cell που χτυπήθηκε πλοίο.
            
            if (!ship.isAlive()){       //Αν όλο το πλοίο χτυπήθηκε.
                board.ships--;          //Μειώνεται ο αριθμός πλοίων στον πίνακα.
            }
            return true;
        }
        setFill(Color.WHITE);           //Να βαφτεί άσπρο το Cell που χτυπήθηκε και δεν υπήρξε πλοίο.
        return false;
        }
    }
}