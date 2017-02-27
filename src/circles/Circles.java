/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package circles;


import java.util.stream.Stream;
import javafx.animation.Animation;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * A lab exercise to introduce Java 8 lambdas and streams.
 * @author Your name here
 */
public class Circles extends Application {
    
    public static final int ROWS = 4;
    public static final int COLS = 5;
    public static final int CELL_SIZE = 100;
    
    @Override
    public void start(Stage primaryStage) {
        root = new VBox();
        canvas = new Pane();
        starter = new Button("Circles");
        addButtonHandler();
        
//        starter.addEventHandler( MouseEvent.MOUSE_CLICKED, event -> {
//            addButtonHandler();
//         });
        
        root.setAlignment(Pos.CENTER);
        canvas.setPrefSize(COLS * CELL_SIZE, ROWS * CELL_SIZE);

        root.getChildren().addAll(canvas, starter);
        
        primaryStage.setTitle("Java 8 Lab Exercise");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        
        makeRow().forEach(x->System.out.println(x));
        makeAllRows().forEach(r->r.forEach(x->System.out.println(x)));
        
    }
    
    /**
     * This method adds the handler to the button that gives
     * this application its behavior.
     */
    private void addButtonHandler() {
//        starter.setOnAction(e -> addToCanvas(new Circle(CELL_SIZE/4, Color.BLACK)));
        starter.setOnAction(e -> { canvas.getChildren().clear(); 
                                   addAllRowsToCanvas( makeAllRows() ); } );
        
//        Circle circle = new Circle(30 , Color.BLACK );
//        circle.setCenterX(50.0f);
//        circle.setCenterY(50.0f);
//        addToCanvas( circle );
    }
    
    private VBox root;
    private Pane canvas;
    private Button starter;
    private int row;
    private int col;
    
    
    
    private void addToCanvas( Circle circle ) {
        circle.setFill(new Color(Math.random(), Math.random(), Math.random(),1.0));
        double fromX = ( COLS * CELL_SIZE ) - ( CELL_SIZE/4 );
        double fromY = ( ROWS * CELL_SIZE ) - ( CELL_SIZE/4 );
        double toX = (CELL_SIZE/2)+(col*CELL_SIZE);
        double toY = (CELL_SIZE/2)+(row*CELL_SIZE);
                
        circle.setCenterX(fromX);
        circle.setCenterY(fromY);
        canvas.getChildren().add( circle );
        
        TranslateTransition tt = new TranslateTransition(Duration.millis(500));
        tt.setNode(circle);
        tt.setByX(CELL_SIZE/4-toX);
        tt.setByY(CELL_SIZE/4-toY);
        tt.play();
        
        ScaleTransition st = new ScaleTransition(Duration.millis(500+Math.random()*100));
        st.setNode(circle);
        st.setByX( 2 );
        st.setByY( 2 );
        st.setCycleCount(Animation.INDEFINITE);
        st.setAutoReverse(true);
        st.play();
    }
    
    private Stream<Circle> makeRow() {
        Stream<Circle> r = Stream.generate(()-> new Circle(CELL_SIZE/4, Color.BLACK)).limit( COLS );
        return r;
    }
    
    private void addRowToCanvas(Stream<Circle> c1) {
        col = 0;
        c1.forEach( c ->{ 
            addToCanvas( c ); col++;
        } );
    }
        
    private Stream<Stream<Circle>> makeAllRows() {
        Stream<Stream<Circle>> result = Stream.generate(()-> makeRow()).limit( ROWS );
        
        return result;
    }
    
    private void addAllRowsToCanvas(Stream<Stream<Circle>> c2) {
        row = 0;
        c2.forEach (r-> {
            addRowToCanvas(r); row++;
        } );
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}