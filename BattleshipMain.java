package practice;


import java.util.ArrayList;             //Μεθόδοι Arraylist.
import java.util.Random;                //Τυχαία νούμερα.
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;             //Για να μπορώ να παίξω με τις θέσεις μέσα στα layouts.
import javafx.scene.Node;
import javafx.scene.Parent;             //Κλάση Parent.
import javafx.scene.Scene;              //Για την κάθε σκηνή.
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;  //Για να παίρνει δεξί-αριστερό κλικ.
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import practice.Board.Cell;    //Να ξέρει την Cell από την κλάση Board.
import static practice.FirstFrame.PlayerName;


public class BattleshipMain extends Application {

    public boolean shipGotIntoSea1=false;
    private boolean running = false;      //Εάν ο παίκτης έχει βάλει τα πλοία και παίζει (=true).
    private Board enemyBoard, playerBoard,playerBoard1; //Δήλωση πινάκων.
    private Board1 Board12;                             //Δήλωση αριστερού πινακακιού πλοίων προς τοποθέτηση.
    public int shipsToPlace = 5;           //Δήλωση μεταβλητής max πλοίων στον πίνακα.
    private int temp = 0;
    private boolean enemyTurn = false;     //Εάν είναι η σειρά του αντιπάλου.
    private Random random = new Random();  //Δήλωση math.random().
    public boolean shipGotIntoSea=true;    //Boolean με σκοπό εάν έχει τοποθετηθεί το πλοίο στον πίνακα του παίκτη να ασπρίσει η θέση του στον αριστερό πίνακα Board12.


    private boolean findShip = false;      //Boolean (false = Ψάχνει τυχαία τετράγωνα εφόσον δεν έχει βρεθεί πλοίο).
    private boolean contHit = false;       //Boolean (true = Βρέθηκε πλοίο και ψάχνει γειτονικά).
    
    public boolean placedShip1 = false;    //Εάν μπήκε το 1ο πλοίο.
    public boolean placedShip2 = false;    //Εάν μπήκε το 2ο πλοίο.
    public boolean placedShip3 = false;    //Εάν μπήκε το 3ο πλοίο.
    public boolean placedShip4 = false;    //Εάν μπήκε το 4ο πλοίο.
    public boolean placedShip5 = false;    //Εάν μπήκε το 5ο πλοίο.


    private ArrayList<Cell> neighborsLeft = new ArrayList<Cell>();     //ArrayList για γειτονικά τετράγωνα που έμειναν για τσεκάρισμα.
    private int n = 0;        //Τρέχων αριθμός γειτονικών πλοίων στην Arraylist κατά την διάρκεια της επανάληψης.
    private int currentX;     //Τρέχων X συντεταγμένες.
    private int currentY;     //Τρέχων Y συντεταγμένες.
    private int nextX;        //X συντεταγμένες του επόμενου Cell που θα χτυπηθεί.
    private int nextY;        //Y συντεταγμένες του επόμενου Cell που θα χτυπηθεί.
    String x = PlayerName;

    Stage window;                                   //Αρχικοποίηση σκηνών
    Scene scene1, scene2;                           //Αρχικοποίηση σκηνών
    Scene scene3 = new Scene(createContent());      //Αρχικοποίηση σκηνών

    MouseButton rotate;
    Button buttons = new Button("Εκκίνηση Παιχνιδιού");
    Button RotateButton = new Button("Κάθετη Περιστροφή");
    
    private int temp2=0;
    
    @Override
    public void start(Stage primaryStage)
    {
        window = primaryStage;
        
        Board12 = new Board1(event ->{
        });

        Node center = null;


        
        
        Label label1 = new Label("Επιλέξτε τα πλοία από αριστερά και τοποθετήστε τα στην θάλασσα!");
        Button button1 = new Button("Οδηγίες");
        button1.setOnAction(e -> window.setScene(scene2));
        Button ResetButton = new Button("Διαγραφή πλοίων");
        
        ResetButton.setOnAction( e->{
                if(placedShip1 == true ||placedShip2 == true ||placedShip3 == true ||placedShip4 == true ||placedShip5 == true)
                {
                System.out.println( "Τα πλοία ξαναμπήκαν στην θέση τους!" );
                primaryStage.close();
                Platform.runLater( () -> new BattleshipMain().start( new Stage() ) );
                }
                else System.out.println("Δεν υπάρχει πλοίο για διαγραφή!");
            });


        RotateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //RotateButton.getStylesheets().add(BattleshipMain.class.getResource("Hover.css").toExternalForm());
               if(temp2==0)
                {
                    temp2=1;
                    rotate = MouseButton.PRIMARY;
                    RotateButton.setText("Οριζόντια Περιστροφή");
                    System.out.println("Το πλοίο θα μπει κάθετα!");
                }
               else
                {
                    temp2=0;
                    rotate = MouseButton.SECONDARY;
                    RotateButton.setText("Κάθετη Περιστροφή");
                    System.out.println("Το πλοίο θα μπει οριζόντια!");
                }
            }
        });

        VBox right = getscene1Right();
        VBox left =  getscene1Left();
        
        BorderPane.setMargin(left, new Insets(200, 0, 0, 10));  //(200 = y ,10 = x)
        HBox top = new HBox(label1);
        BorderPane.setMargin(top, new Insets(10, 0, 0, 200));   //(10 = y ,200 = x)
        
        Region spacer=new Region();
        HBox bottom = new HBox(button1,RotateButton,ResetButton,spacer,buttons);
        
        BorderPane.setMargin(bottom, new Insets(10, 0, 0, 0));
        BorderPane.setMargin(right, new Insets(100, 100, 0, 0));
        
        BorderPane root1 = new BorderPane(center, top, right, bottom, left);
        
        bottom.setHgrow(spacer,Priority.ALWAYS);                                    //Βάζει κενό στα κουμπιά το κάτω μέρος του root1.

        scene1 = new Scene(root1, 800, 700);

        //Button 2
        Label label2 = new Label("Οδηγίες \n \n Είναι ένα τυπικό παιχνίδι όπου ο σκοπός είναι"
                + "\n να βυθίσετε τα πλοία του αντιπάλου"+
                "\n πριν βυθίσει αυτός τα δικά σας."+
                "\n \n Τοποθετήστε τα πλοία σας στον πίνακα!" +
                "\n  Όταν τα έχετε τοποθετήσει όλα, πιέστε" +
                "\n ''Εκκίνηση Παιχνιδιού''." +
                "\n \n Στην συγκεκριμένη έκδοση Ναυμαχίας όταν, " +
                "\n ο παίκτης χτυπήσει ένα πλοίο, ξαναπαίζει.");

        Button button2 = new Button("Πίσω");
        button2.setOnAction(e -> window.setScene(scene1));

        //Layout 2
        VBox layout2 = new VBox(20);
        
        layout2.getChildren().addAll(label2, button2);

        scene2 = new Scene(layout2, 450, 400);

        window.setTitle("Ναυμαχία");       
        window.setScene(scene1);               //Σαν default βάζουμε την scene1.
        window.setResizable(true);
        
        window.setMinWidth(800);
        window.setMinHeight(750);
        window.centerOnScreen();
        
        window.show();
        
        primaryStage.setOnCloseRequest(e->{
        Platform.exit();
        System.exit(0);
});
    }
    
    /**
     * Μέθοδος shipAlreadyPlaced.
     * Ελέγχει εάν τα πλοία μπήκαν στην θάλασσα έτσι ώστε να χρωματίσει αναλόγως
     * τον αριστερό πίνακα των πλοίων με άσπρο χρώμα.
     */
public void shipAlreadyPlaced(){
        
        if(shipGotIntoSea == true)
        {
            if(Board12.getShipLength() == 5 && placedShip1 == false){ 
                placedShip1 = true;

                    Board12.ksecolorShip();
                    Board12.EgineAspro1 = true;
                }
            else if(Board12.getShipLength() == 4 && placedShip2 == false){ 
                placedShip2 = true;

                    Board12.ksecolorShip();
                    Board12.EgineAspro2 = true;
                }
            else if(Board12.getShipLength() == 3 && Board12.shipId() == 3 && placedShip3 == false){ 
                placedShip3 = true;

                    Board12.ksecolorShip();
                    Board12.EgineAspro3 = true;
                }
            else if(Board12.getShipLength() == 3 && Board12.shipId() == 4 && placedShip4 == false){
                placedShip4 = true;

                    Board12.ksecolorShip();
                    Board12.EgineAspro4 = true;
                }
            else if(Board12.getShipLength() == 2 && placedShip5 == false){ 
                placedShip5 = true;

                    Board12.ksecolorShip();
                    Board12.EgineAspro5 = true;
                }
            else 
                shipGotIntoSea = false;
            }
        }

    private VBox getscene1Right() {
    Label label4 = new Label("                                 Πίνακας του/της: " + PlayerName);
    VBox layout1 = new VBox(label4,playerBoard1);
    return layout1;
  }

    private VBox getscene1Left() {
    Label label3 = new Label("              Τα πλοία σας");
    VBox layout2 = new VBox(label3,Board12);
    return layout2;
  }
    
    /**
     * Parent Μέθοδος createContent() που: 
     * Φτιάχνει τα layout με τα Board, 
     * και αποφασίζει πως θα ανταποκρίνεται το πρόγραμμα όσο το παιχνίδι προχωράει.
     * Τέλος, ελέγχει κάθε φορά για τον νικητή και κάνει έξοδο από το πρόγραμμα εμφανίζοντας κατάλληλο μήνυμα.
     **/
    
    private Parent createContent(){
        enemyBoard = new Board(true, event -> {    //Ορίζω 3 πίνακες (enemyBoard,playerBoard,playerBoard1) ο κάθε ένας με άλλα handlers.
            
            if(!running)
            {
                return;                 //Δεν επιστρέφει τίποτα εφόσον δεν έχει ξεκινήσει ακόμα το παιχνίδι.
            }
            
            Cell cell = (Cell) event.getSource();  //Παίρνω το Cell που πάτησε ο παίκτης.
            
            if(cell.wasShot)                       //Εάν είναι true για το συγκεκριμένο Cell (εάν έχει ήδη χτυπηθεί,)
            {
                return;                 //Δεν επιστρέφει τίποτα (Αλλιώς θα μετρούσε σαν κίνηση και θα έπαιζε ο επόμενος).
            }

            enemyTurn = !cell.shoot();  //Αν ο παίκτης χτυπήσει εχθρικό πλοίο, το enemyTurn γίνεται !true (false στην Board.shoot()) και συνεχίζει να παίζει ο παίκτης.

            
            if(enemyBoard.ships == 0)   //Αν δεν έμεινε κανένα εχθρικό πλοίο, εμφανίζει το σχετικό μήνυμα της νίκης.
            {
                closeProgram("Νικήσατε!");
            }

            if(enemyTurn)               //Εάν είναι η σειρά του Υπολογιστή τρέχει την μέθοδο για το έξυπνο AI.
            {
                enemyMove();
            }
        });

        playerBoard = new Board(false, event -> 
        {
            if(running)
                return;       //Δεν κάνει return κατι, έτσι και αλλιώς δεν γίνεται να πατήσουμε τον πίνακα μας στο παιχνίδι.
        });
        
        playerBoard1 = new Board(false, event ->
        {
            if(running)
                return;       //Δεν κάνει return κατι, έτσι και αλλιώς δεν γίνεται να πατήσουμε τον πίνακα μας στο παιχνίδι.
            
            Cell cell = (Cell) event.getSource();  //Παίρνουμε το κελί που πάτησε ο παίκτης
            
             if(Board12.getShipLength() > 0 )
                {
                    if(shipGotIntoSea=(playerBoard1.placeShip(new Ship(Board12.getShipLength(), event.getButton() == rotate),
                    cell.x, cell.y)) == true)
                        {
                    if(playerBoard.placeShip(new Ship(Board12.getShipLength(), event.getButton() == rotate),
                    cell.x, cell.y))
                        {
                            shipAlreadyPlaced();
                            Board12.setShipLength();

                            shipsToPlace--;         //Εάν μπήκε το πλοίο στην θάλασσα, μειώνω τον συνολικό αριθμό πλοίων.
                
                            if(shipsToPlace == 0)   //Εάν έχουν τοποθετηθεί όλα τα πλοία στην θάλασσα.
                                {
                                    buttons.setOnAction( e->{window.setScene(scene3);  //Πάει στο scene3.
                                    startGame();                                       //Καλεί την startGame όπου το ΑΙ βάζει τυχαία τα πλοία του και ξεκινάει το παιχνίδι.
                                    System.out.println("Καλή επιτυχία!");
                                    });
                                }
                        }        
                }  }         
        });
        
        Node  center = null; 
        Node  top = null;
        Node  bottom = null;

        VBox right = getscene3Right();
        VBox left = getscene3Left();
        
        BorderPane.setMargin(left, new Insets(-20, 0, 0, 80));
        BorderPane.setMargin(right, new Insets(-20, 80, 0, 80));
        
        BorderPane root = new BorderPane(center, top, right, bottom, left);         //Βάζω σαν κέντρο του BorderPane, τα Board.
        return root;
    }

    private VBox getscene3Right() {
        Label label41 = new Label("Πίνακας του Υπολογιστή");
    VBox layout11 = new VBox(label41,enemyBoard);
    layout11.setAlignment(Pos.CENTER);
    return layout11;
    }

    private VBox getscene3Left() {
        Label label42 = new Label("Πίνακας του/της: " + PlayerName);
    VBox layout12 = new VBox(label42,playerBoard);
    layout12.setAlignment(Pos.CENTER);
    return layout12;
    }
    
    
    /**
     * Μέθοδος startGame για να βάλει τα πλοία του ΑΙ τυχαία και να ξεκινήσει το παιχνίδι.
     */
    private void startGame()
    {
        int type = 5;             //Συνολικός αριθμός πλοίων.
        while(type > 1)
            {

                //Τυχαίες συντεταγμένες από το 0 μέχρι το 9.
                int x = random.nextInt(10);
                int y = random.nextInt(10);

                if(enemyBoard.placeShip(new Ship(type, Math.random() < 0.5), x, y))
                    {
                        //Εάν η Math.random βγάλει μικρότερο απο 0.5 το πλοίο θα μπει κάθετα.
                        if(temp==0 && type==3)
                        {
                            type=4;
                            temp=5;
                        }
                        type--;
                    }
            }
        running = true;       //Ξεκινάει το παιχνίδι.
    }    
    
    /**
     *  Μέθοδος enemyMove για έξυπνες κινήσεις του AΙ.
     *  Ψάχνει τυχαίες θέσεις για πλοία.
     *  Άν βρει, ψάχνει γειτονικά τετράγωνα μέχρι να βρεθεί κάποιο.
     *  Όταν βρεθεί κάποιο, συνεχίζει την αναζήτηση προς την πλευρά που βρέθηκε.
     */

    private void enemyMove()
    {
        while(enemyTurn)   //Όσο είναι σειρά του Υπολογιστή.
        {
            if(findShip)   //Εάν βρέθηκε πλοίο.
            {
                while(n < neighborsLeft.size())   //Περνάει από όλα τα γειτονικά τετράγωνα του τρέχοντος Cell.
                {
                    if(neighborsLeft.get(n).wasShot)    //Εάν έχουν ήδη χτυπηθεί τα γειτονικά κελιά.
                    {
                        n++;                            //Αυξάνω το n για να πάει στο επόμενο γειτονικό Cell.
                        if(n < neighborsLeft.size())    //Εάν υπάρχουν κιάλλα γειτονικά τετράγωνα.
                        {
                            continue;            //Εάν ναι, ξαναπάει πάνω & πυροβολάει το επόμενο Cell.
                        }
                        else                     //Άν έχουν τσεκαριστεί όλα τα γειτονικά κελιά
                        {
                            n = 0;               //Κάνουμε reset το n.
                            findShip = false;    //Κάνουμε το findShip false ώστε να μπει ξανά σε τυχαία αναζήτηση.
                            break;               //Φεύγει από την While.
                        }
                    }
                    enemyTurn = neighborsLeft.get(n).shoot();     //Πυροβολάει και επιστρέφει απο την Shoot() false στην σειρά του ΑΙ.

                    if(!enemyTurn)      //Εάν το ΑΙ αστόχησε (σειρά του παίκτη).
                    {
                        n++;                           //Αυξάνω το n για να πάει στο επόμενο γειτονικό Cell.
                        if(n < neighborsLeft.size())   //Εάν υπάρχουν κιάλλα γειτονικά τετράγωνα.
                        {
                            break;        //Έξοδος χωρίς να αλλάξει το findShip, συνεχίζει το τσεκάρισμα με το ίδιο κελί.
                        }
                        else              //Άν έχουν τσεκαριστεί όλα τα γειτονικά κελιά.
                        {
                            n = 0;               //Κάνουμε reset το n.
                            findShip = false;    //Κάνουμε το findShip false ώστε να μπει ξανά σε τυχαία αναζήτηση.
                            break;               //Φεύγει από την While.
                        }
                    }
                    else                //Άν το ΑΙ πέτυχε πλοίο (κι άλλο κελί στην σειρά).
                    {
                        nextX = neighborsLeft.get(n).xCor();    //Βάζουμε το επόμενο κελί να είναι το κελί που χτυπήθηκε.
                        nextY = neighborsLeft.get(n).yCor();
                        contHit = true;                         //contHit - Χτυπάει με βάση την κατεύθυνση του πλοίου.
                        n = 0;                                  //Κάνουμε reset το n.
                        break;                                  //Φεύγει από την While.
                    }
                }
                
                while(contHit)    //Συνεχίζει να βαράει Cells αναλόγως την κατεύθυνση του πλοίου.
                {
                    //get next cell along the length of the ship - figure out proper direction///////////////////////////////////////////////////////////
                    int hitX = nextX + nextX - currentX;
                    int hitY = nextY + nextY - currentY;
                    if(playerBoard.isValidPoint((double) hitX,(double) hitY))      //Τσεκάρουμε με την Board.ValidPoint (boolean) άν γίνεται να υπάρχει εκεί πλοίο.
                    {
                        Board.Cell cellTest = playerBoard.getCell(hitX, hitY);           //Παίρνει το κελί.
                        enemyTurn = cellTest.shoot();                                    //Το πυροβολάει.
                        if(enemyTurn)    //Εάν χτύπησε και το επόμενο Cell.
                        {
                            //Αυξάνουμε το κάθε κελί προς την κατεύθυνση του πλοίου που βρέθηκε.
                            currentX = nextX;
                            currentY = nextY;
                            nextX = cellTest.xCor();    //Βάζουμε το επόμενο κελί να είναι το κελί που χτυπήθηκε.
                            nextY = cellTest.yCor();
                        }
                        else     //Εάν το ΑΙ αστόχησε (σειρά του παίκτη).
                        {
                            contHit = false;           //Δεν χρειάζεται πλέον η contHit.
                            findShip = false;          //Κάνουμε το findShip false ώστε να μπει ξανά σε τυχαία αναζήτηση.
                            break;                     //Φεύγει από την While.
                        }
                    }
                    else   //Εάν δεν είναι έγκυρο κελί (ξεφεύγει απ'τα bounds του πίνακα.
                    {
                        contHit = false;        //Δεν χρειάζεται πλέον η contHit.
                        findShip = false;       //Ούτε το γειτονικό ψάξιμο χρειάζεται.
                        break;                  //Φεύγει από την While.
                    }
                }
            }
            else           //Τυχαία αναζήτηση
            {
                //Τυχαίες συντεταγμένες από το 0 μέχρι το 9.
                int x = random.nextInt(10);
                int y = random.nextInt(10);

                Board.Cell cell = playerBoard.getCell(x, y);    //Παίρκει το κελί του παίκτη στις χ ψ συντεταγμένες.
                if(cell.wasShot)                                //Εάν έχει ήδη χτυπηθεί.
                {
                    continue;       //Ξαναπάει πάνω στην while.
                }
                enemyTurn = cell.shoot();                 //Ελέγχει απο την Board αν χτυπήθηκε πλοίο.

                if(enemyTurn)
                {
                    findShip = true;                      //Αν ναι, τότε ξεκινάει γειτονική αναζήτηση.
                    currentX = x;                         //Τωρινές συντεταγμένες του κελιού.
                    currentY = y;
                    neighborsLeft = playerBoard.getNeighbors2(x, y);    //Παίρνει τα γειτονικά τετράγωνα του κελιού που επιστράφηκαν από την getNeighbor2.
                }
                else                                                    //Αλλιώς (άν είναι η σειρά μας).
                {
                    findShip = false;                     //Πάει να πει ότι δεν βρέθηκε πλοίο και δεν χρειάζεται γειτονική αναζήτηση.
                }
            }
        }
        if(playerBoard.ships == 0)                //Άν δεν έμεινε κανένα πλοίο του παίκτη (Έλεγχος από την int ships στην Board που μειώνεται κάθε φορά).
        {
            closeProgram("Χάσατε.");              //Εμφανίζει σχετικό μήνυμα της ήττας.
        }
    }

    private void closeProgram(String status){
        Boolean answer = ConfirmBox.display(status, "Αντίο " + PlayerName + "!");
        if(answer==true)
            System.exit(0);
    }
}