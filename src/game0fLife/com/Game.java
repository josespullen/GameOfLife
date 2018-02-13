package game0fLife.com;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
public class Game extends Application {

	int cell[][];
	Rectangle[][] rect;
	int G_counter =0;
	int L_counter =0;
	int x;
	int y;
	Random Rand = new Random();
	Color Alive = Color.BLACK;
	Color Dead = Color.WHITE;
	
	Button btn_start = new Button("Start");
	Button btn_stop = new Button("Stop");
	ChoiceBox<Color>  C_D = new ChoiceBox<Color>();
	
	ChoiceBox<Color>  C_A = new ChoiceBox<Color>();
	Label Generation = new Label("Generation");
	Label Live_cell = new Label("Live Cell");
	TextField txt_generation = new TextField();
	TextField txt_liveCell = new TextField();
	
	BorderPane Root = new BorderPane();
	HBox controls = new HBox();
	VBox Blocks = new VBox();
	Scene scene;
	
	 private void setupCell() {
		 cell = new int[x/4][y/4];
		 rect = new Rectangle[x/4][y/4];
	        for(int i=0; i< cell.length; i++){
	        	HBox Row =new HBox();
	        	for(int j =0; j< cell[i].length; j++){
	        		int r = Rand.nextInt(10);
	        		rect[i][j] = new Rectangle(4,4,4,4);
	        		rect[i][j].setArcHeight(2);
	        		rect[i][j].setArcWidth(2);
	        		rect[i][j].setFill(Dead);
	        		Row.getChildren().add(rect[i][j]);
	        		if(r==1){
	        			cell[i][j] = 1;
	        			rect[i][j].setFill(Alive);
	        		}else{
	        			cell[i][j] = 0;
	        			rect[i][j].setFill(Dead);
	        		}
	        		
	        	}
	        	Blocks.getChildren().add(Row);
	        }
	    }
	 public void NextGeneration() {
	        for (int i = 0; i < cell.length; i++) {
	            for (int j = 0; j < cell[i].length; j++) {
	                implementRules(i, j);
	            }
	        }
	        G_counter = G_counter + 1;
	       
	       
	    }
	 private void implementRules(int i, int j) {
	        int left = 0, right = 0, up = 0, down = 0;
	        int dUpperLeft = 0, dUpperRight = 0, dLowerLeft = 0, dLowerRight = 0;

	        if (j < cell.length - 1) {
	            right =cell[i][j + 1];
	            if(i>0)
	                dUpperRight = cell[i - 1][j + 1];
	            if (i < cell.length - 1)
	                dLowerRight = cell[i + 1][j + 1];
	        }

	        if (j > 0) {
	            left = cell[i][j - 1];
	            if (i > 0)
	                dUpperLeft = cell[i - 1][j - 1];
	            if (i< cell.length-1)
	                dLowerLeft = cell[i + 1][j - 1];
	        }

	        if (i > 0)
	            up = cell[i - 1][j];
	        if (i < cell.length - 1)
	            down = cell[i + 1][j];

	        int sum = left + right + up + down + dUpperLeft + dUpperRight
	                + dLowerLeft
	                + dLowerRight;

	        if (cell[i][j] == 1) {
	            if (sum < 2){
	            	 cell[i][j] = 0;
	 	            rect[i][j].setFill(Dead);
	            }
	               
	            if (sum > 3){
	            	cell[i][j] = 0;
		            rect[i][j].setFill(Dead);
	            }
	            
	            if ((sum == 3) || (sum ==2)){
	            	cell[i][j] = 1;
		            rect[i][j].setFill(Alive);
		            L_counter++;
	            }
	                
	        }

	        else {
	            if (sum == 3){
	            	 cell[i][j] = 1;
	 	            rect[i][j].setFill(Alive);
	 	           L_counter++;
	            }
	               
	        }

	    }
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		x=800;
		y=800;
		C_D.getItems().add(Color.WHITE);
		C_D.getItems().add(Color.BLACK);
		C_D.getItems().add(Color.BLUE);
		C_D.getItems().add(Color.CYAN);
		C_A.getItems().add(Color.RED);
		C_A.getItems().add(Color.AQUA);
		C_A.getItems().add(Color.YELLOW);
		C_A.getItems().add(Color.CHOCOLATE);
		
		controls.getChildren().addAll(btn_start, btn_stop, C_D, C_A, Generation, txt_generation, Live_cell, txt_liveCell);
		setupCell();
		Root.setTop(controls);
		Root.setCenter(Blocks);
		scene = new Scene(Root,x,y);
		
		primaryStage.setTitle("RD Browser");
		primaryStage.setScene(scene);
		primaryStage.show();
		txt_generation.setText(String.valueOf(G_counter));

		C_A.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				Alive =C_A.getValue();
				
			}
			
		});
		C_D.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				Dead =C_D.getValue();
				
			}
			
		});
		Timer timer = new java.util.Timer();

		timer.schedule(new TimerTask() {
		    public void run() {
		         Platform.runLater(new Runnable() {
		            public void run() {
		                NextGeneration();
		                txt_generation.setText(String.valueOf(G_counter));
		               
		                txt_liveCell.setText(String.valueOf(L_counter));
		            }
		        });
		    }
		}, 10, 100);
		//timer.cancel();
		

		 
		 
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
}
