package practice;


import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox ;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Board1 extends Parent {
    private VBox rows = new VBox();
    
    public int shipsToPlace = 3;
    public int board1length = 5;
    private int shipLength = 0;
    public int shipID = 0;
    
    
    public boolean EgineAspro1;
    public boolean EgineAspro2;
    public boolean EgineAspro3;
    public boolean EgineAspro4;
    public boolean EgineAspro5;
    
    
    public Board1(EventHandler<? super MouseEvent> handler) //Να κλικάρονται τα cells.
        { 
            HBox ship1 = new HBox();                         // Φτιάχνουμε 5 σειρές (HBox) σε 1 VBox όπου κάθε σειράς τα Cells είναι c1,c2, κλπ.
            HBox ship2 = new HBox();
            HBox ship3 = new HBox();
            HBox ship4 = new HBox();
            HBox ship5 = new HBox();

            for (int x = 0; x < board1length; x++)
                {
                    Cell c1 = new Cell(x,1,this);
                    Cell c2 = new Cell(x,1,this);
                    Cell c3 = new Cell(x,1,this);
                    Cell c4 = new Cell(x,1,this);
                    Cell c5 = new Cell(x,1,this);
                    
                    c1.setFill(Color.DARKGRAY);
                    
                    if(x<4){
                        c2.setFill(Color.DARKGRAY);
                    }
                    
                    if(x<3)
                        {
                            c3.setFill(Color.DARKGRAY);
                            c4.setFill(Color.DARKGRAY);
                        }
                    
                    if(x<2){
                        c5.setFill(Color.DARKGRAY);
                    }
                    
                    ship1.getChildren().add(c1);
                    ship2.getChildren().add(c2);
                    ship3.getChildren().add(c3);
                    ship4.getChildren().add(c4);
                    ship5.getChildren().add(c5);
                }
            
            rows.getChildren().add(ship1);
            rows.getChildren().add(ship2);
            rows.getChildren().add(ship3);
            rows.getChildren().add(ship4);
            rows.getChildren().add(ship5);
            
            
            rows.setSpacing(8);                                     //ΚΕΝA
            
            getChildren().add(rows);
        
  
        ship1.setOnMouseClicked(new EventHandler<MouseEvent>()
                { 
                    @Override
                    public void handle (MouseEvent me) {
                        if (EgineAspro1 == false){
                            System.out.println("Επιλέξατε το μεγαλύτερο πλοίο!");
                            shipLength = 5;
                            shipID = 1;
                           colorShip(shipLength);
                    }
                        }
                });


            ship2.setOnMouseClicked(new EventHandler<MouseEvent>() 
                {
                    @Override
                    public void handle(MouseEvent me) {
                        if(EgineAspro2 == false){
                            System.out.println("Επιλέξατε το 4άρι πλοίο!");
                            shipLength = 4;
                            shipID = 2;
                            colorShip(shipLength);
                        }
                        }
                });
   
            ship3.setOnMouseClicked(new EventHandler<MouseEvent>() 
                {
                    @Override
                    public void handle(MouseEvent me) {if(EgineAspro3 == false){
                            System.out.println("Επιλέξατε το 3άρι πλοίο!");
                            shipLength = 3;
                            shipID = 3;
                          colorShip(shipLength);
                    }
                        }
                });
            
            ship4.setOnMouseClicked(new EventHandler<MouseEvent>() 
                {
                    @Override
                    public void handle(MouseEvent me) {
                        if(EgineAspro4 == false){
                            System.out.println("Επιλέξατε το δευτερο 3άρι πλοίο!");
                            shipLength = 3;
                            shipID = 4;
                          colorShip(shipLength);
                        }
                        }
                });
            
            ship5.setOnMouseClicked(new EventHandler<MouseEvent>() 
                {
                    @Override
                    public void handle(MouseEvent me) {
                        if(EgineAspro5 == false){
                            System.out.println("Επιλέξατε το μικρότερο πλοίο!");
                            shipLength = 2;
                            shipID = 5;
                            colorShip(shipLength);
                        }
                        }
                });
    }
    public int shipId() {
        return shipID;
    }
     
    public int getShipLength() {
            return shipLength;
        }
    
    public void setShipLength(){
        shipLength = 0;
    }

    
    public Board1.Cell getCell(int x, int y) 
        {
            return (Board1.Cell)((HBox)rows.getChildren().get(y)).getChildren().get(x); //Επιστρέφει το κελί. 
            //Παίρνω την γραμμή πρώτα σαν ολόκληρη, το βάζω σαν Hbox, και τέλος παίρνω το κελί
        }
    
     public void colorShip(int shipLength){
            int length = shipLength;
            int y;

                for(int i = 0; i <  length; i++)
                {
                    if(length == 5){   
                        y=0;
                        Cell c1 = getCell(i, y);
                        c1.setFill(Color.YELLOW);
                    }
                  if(length == 4){
                        y=1;
                        Cell c2 = getCell(i, y);                         
                        c2.setFill(Color.YELLOW);
                    }
                   if(length == 3 && shipID==3){  
                        y=2;
                        Cell c3 = getCell(i, y);                    
                        c3.setFill(Color.YELLOW);
                    }
                    if(length == 3 && shipID==4){  
                        y=3;
                        Cell c4 = getCell(i, y);                     
                        c4.setFill(Color.YELLOW);
                    }
                     if(length == 2){   
                         y=4;
                         Cell c5 = getCell(i, y);                      
                         c5.setFill(Color.YELLOW);
                    }
                }
                
              if ( EgineAspro1 == false && (shipID==2 ||shipID==3  ||shipID==4 ||shipID==5))
              {
                   for(int i = 0; i <  5; i++)
                                        
                    {   int y1=0;
                        Cell c1 = getCell(i, y1);
                        c1.setFill(Color.DARKGRAY); //Εάν πατήθηκε κάποιο άλλο πλοίο να ξαναχρωματιστεί γκρί αυτό.
                    }       
                   
              }
              
              if ( EgineAspro2 == false && (shipID==1 ||shipID==3  ||shipID==4 ||shipID==5))
              {
                   for(int i = 0; i <  4; i++)
                                        
                    {   int y2=1;
                        Cell c2 = getCell(i, y2);
                        c2.setFill(Color.DARKGRAY); //Εάν πατήθηκε κάποιο άλλο πλοίο να ξαναχρωματιστεί γκρί αυτό.
                        
                    }       
                   
              }
              
              if ( EgineAspro3 == false && (shipID==1 ||shipID==2  ||shipID==4 ||shipID==5))
              {
                   for(int i = 0; i <  3; i++)
                                        
                    {   int y3=2;
                        Cell c3 = getCell(i, y3);
                        c3.setFill(Color.DARKGRAY); //Εάν πατήθηκε κάποιο άλλο πλοίο να ξαναχρωματιστεί γκρί αυτό.
                        
                    }       
                   
              }
              
              if ( EgineAspro4 == false && (shipID==1 ||shipID==3  ||shipID==2 ||shipID==5))
              {
                   for(int i = 0; i <  3; i++)
                                        
                    {   int y4=3;
                        Cell c4 = getCell(i, y4);
                        c4.setFill(Color.DARKGRAY); //Εάν πατήθηκε κάποιο άλλο πλοίο να ξαναχρωματιστεί γκρί αυτό.
                        
                    }       
                   
              }
              
              if ( EgineAspro5 == false && (shipID==2 ||shipID==3  ||shipID==4 ||shipID==1))
              {
                   for(int i = 0; i <  2; i++)
                                        
                    {   int y5=4;
                        Cell c5 = getCell(i, y5);
                        c5.setFill(Color.DARKGRAY); //Εάν πατήθηκε κάποιο άλλο πλοίο να ξαναχρωματιστεί γκρί αυτό.
                        
                    }       
                   
              }
              
        }
    
    
  
    public void ksecolorShip()
    {
        int length = shipLength;
            int y;

                for(int i = 0; i < length; i++)
                {
                    if(length == 5)
                    {  
                        y=0;
                        Cell c1 = getCell(i, y);
                        c1.setFill(Color.WHITE); //Εάν μπήκε το πλοίο στην θάλασσα τότε να ασπρίσει στον αριστερό πίνακα.
                    }
                  if(length == 4)
                    {   y=1;
                        Cell c2 = getCell(i, y);
                        c2.setFill(Color.WHITE); //Εάν μπήκε το πλοίο στην θάλασσα τότε να ασπρίσει στον αριστερό πίνακα.
                    }

                   if(length == 3 && shipID==3)
                    {   y=2;
                        Cell c3 = getCell(i, y);
                        c3.setFill(Color.WHITE); //Εάν μπήκε το πλοίο στην θάλασσα τότε να ασπρίσει στον αριστερό πίνακα.
                    }

                    if(length == 3 && shipID==4)
                    {   y=3;
                        Cell c4 = getCell(i, y);
                        c4.setFill(Color.WHITE); //Εάν μπήκε το πλοίο στην θάλασσα τότε να ασπρίσει στον αριστερό πίνακα.
                    }

                     if(length == 2)
                    {   y=4;
                        Cell c5 = getCell(i, y);
                        c5.setFill(Color.WHITE); //Εάν μπήκε το πλοίο στην θάλασσα τότε να ασπρίσει στον αριστερό πίνακα.
                    }
                }
    }
    
   
    
    public class Cell extends Rectangle {        //Ώστε κάθε τετράγωνο των πινάκων να είναι Cell.
    public int x;                                //Η θέση του κελιού.
    
    

    public Cell(int x , int y, Board1 board)     // Η θέση του κελιού στον πίνακα.
        { 
            super(40, 40);                       //Μέγεθος πίνακα.
            this.x = x;                          // sets the variables equal to input ///////////////////////////////////////////////////////////////////////////////
            setFill(Color.WHITE);                //Γέμισμα κουτακιών πίνακα.
            setStroke(Color.BLACK);
        }
    }
}