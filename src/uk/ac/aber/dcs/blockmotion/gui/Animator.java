package uk.ac.aber.dcs.blockmotion.gui;

/**
 * This is the template Animator to display the animation. You will
 * need to update this file
 *
 * @author Chris Loftus, Chris Machala
 * @version 1.0 (27th March 2017)  (change the version number and date)
 */

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import uk.ac.aber.dcs.blockmotion.Footage;
import uk.ac.aber.dcs.blockmotion.Transform;
import uk.ac.aber.dcs.blockmotion.Transformations.*;
import uk.ac.aber.dcs.blockmotion.model.IFootage;
import uk.ac.aber.dcs.blockmotion.model.IFrame;

import java.io.IOException;
import java.util.Scanner;

public class Animator extends Application {

    private Button[][] gridArray;
    private GridPane grid;
    private Thread animation;
    private boolean doRun;
    private IFootage footage;
    private Scanner in;
    private Stage stage;
    private Scene scene;
    private Transform t;
    private int slideUpChoice, slideDownChoice, slideRightChoice, slideLeftChoice = 0;

    // You will need more instance variables

    public static void main(String[] args) {
        // This is how a javafx class is run.
        // This will cause the start method to run.
        // You don't need to change main.
        launch(args);
    }

    // This is the javafx main starting method. The JVM will run this
    // inside an object of this class (Animator). You do not need to
    // create that object yourself.
    @Override
    public void start(Stage primaryStage) {

        // The Stage is where we place GUI stuff, such as a GridPane (see later).
        // I'll look more at this after Easter, but please read the
        // following comments
        stage = primaryStage;

        // In this version of the app we will drive
        // the app using a command line menu.
        // YOU ARE REQUIRED TO IMPLEMENT THIS METHOD
        runMenu();

        // This is how we can handle a window close event. I will talk
        // about the strange syntax later (lambdas), but essentially it's a Java 8
        // was of providing a callback function: when someone clicks the
        // close window icon then this bit of code is run, passing in the event
        // object that represents the click event.
        primaryStage.setOnCloseRequest((event) -> {
            // Prevent window from closing. We only want quitting via
            // the command line menu.
            event.consume();

            System.out.println("Please quit the application via the menu");
        });
    }

    private void runMenu() {
        in = new Scanner(System.in);
        // The GUI runs in its own thread of control. Here
        // we start by defining the function we want the thread
        // to call using that Java 8 lambda syntax. The Thread
        // constructor takes a Runnable
        Runnable commandLineTask = () -> {

            // YOUR MENU CODE GOES HERE
            String choice;
            String fileName = "scotty.txt";
            do {
                printMenu();
                choice = in.nextLine().toUpperCase();


                switch (choice){

                    case "L": // Load Footage Footage

                        System.out.println( "Please enter the name of the footage to load");
                        fileName = in.nextLine();

                        System.out.println( "Loading Footage File");
                    //TODO allow user to pick their own file
                        footage = new Footage();

                        try {
                            footage.load(fileName);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "S": // Save Footage Footage
                        System.out.println("Saving Footage Footage");

                        try {
                            footage.save(fileName);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        break;
                    case "SA": // Save as Footage Footage
                        System.out.println("Saving as Footage Footage");
                        System.out.println( "Please enter the new name of the footage you wish to save");

                        System.out.println("Saving as Footage " + fileName);
                        fileName = in.nextLine();
                        try {
                            footage.save(fileName);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "A": // Run Footage Footage
                        System.out.println("Running Footage Animation");
                        createGrid();
                        runAnimation();
                        break;
                    case "T": // Stop Footage Footage
                        System.out.println("Stopping Footage Animation");
                        terminateAnimation();

                        break;
                    case "E": // Edit Current Footage Footage (load edit options)
                        System.out.println("Loading Edit Options");
                        runEditMenu(null);
                        break;
                    case "Q": // Quit Application
                        System.out.println("Quitting Application");
                        // You can stop javafx with the command
                        Platform.exit();
                        break;
                    default:
                        System.out.println("not a valid choice");

                }



            }
            while (!choice.equals("Q"));



            // At some point you will call createGrid.
            // Here's an example
            // createGrid();

        };
        Thread commandLineThread = new Thread(commandLineTask);
        // This is how we start the thread.
        // This causes the run method to execute.
        commandLineThread.start();


    }

    private void printMenu() {
        System.out.println("What would you like to do today :\n l - Load footage file\n " +
                "s - Save footage file\n sa - Save as footage file\n a - Run footage animation\n " +
                "t - Stop footage animation\n e - Edit current footage\n q - Quit\nEnter Option: ");
    }
    public void printEditMenu() {

        System.out.println("Please choose an edit option : \n fh - FlipH horizontal\n " +
                "fv - FlipH Vertical\n sl - Slide Left\n sr - Slide Right\n " +
                "su - Slide Up\n sd - Slide Down\n nr - Slide right number. Currently= " + slideRightChoice + "\n" +
                "nl - Slide left number. Currently= " + slideLeftChoice+ "\n nd - Slide down number. Currently= " + slideDownChoice + "\n " +
                "nu - Slide up number. Currently= " + slideUpChoice + "\n r - Repeat last operation\n q - quit editing\n" +
                "Enter Option : ");

    }


        public void runEditMenu(String prevChoice){
            in = new Scanner(System.in);
            Scanner inC = new Scanner(System.in);
            String previousChoice;
            String editChoice;
            printEditMenu();
            previousChoice = new String();


        do {

            if(prevChoice == null) {
                editChoice = in.nextLine().toUpperCase();
            }else {
                editChoice = prevChoice;
                prevChoice = null;
            }



        switch (editChoice) {

            case "FH": // FlipH horizontal
                previousChoice = editChoice;
                System.out.println("Flipping footage Horizontally");
                t = new FlipH();
                footage.transform(t);
                printEditMenu();
                break;

            case "FV": // FlipH Vertical
                previousChoice = editChoice;
                System.out.println("Flipping footage Vertically");
                t = new FlipV();
                footage.transform(t);
                printEditMenu();
                break;

            case "SL": // Slide Left
                previousChoice = editChoice;
                System.out.println("Sliding Footage Left");
                t = new SlideL();
                footage.transform(t);
                printEditMenu();
                break;

            case "SR": // Slide Right
                previousChoice = editChoice;
                System.out.println("Sliding Footage Right");
                t = new SlideR();
                footage.transform(t);
                printEditMenu();
                break;

            case "SU": // Slide Up
                previousChoice = editChoice;
                System.out.println("Sliding Footage Up");
                t = new SlideU();
                footage.transform(t);
                printEditMenu();
                break;

            case "SD": // Slide Down
                previousChoice = editChoice;
                System.out.println("Sliding Footage Down");
                t = new SlideD();
                footage.transform(t);
                printEditMenu();
                break;

            case "NR": // Slide right number
                previousChoice = editChoice;
                System.out.println("How many times would you like to slide right, currently " + slideRightChoice);
                slideRightChoice = inC.nextInt();
                 t = new SlideR();
                 for (int i = 0; i < slideRightChoice; i++){footage.transform(t);}
                System.out.println("Sliding Footage Right");
                printEditMenu();
                break;

            case "NL": // Slide left number
                previousChoice = editChoice;
                System.out.println("How many times would you like to slide Left, currently " + slideLeftChoice);
                slideLeftChoice = inC.nextInt();
                t = new SlideL();
                for (int i = 0; i < slideLeftChoice; i++){footage.transform(t);}
                System.out.println("Sliding Footage Left");
                printEditMenu();
                break;

            case "ND": // Slide down number
                previousChoice = editChoice;
                System.out.println("How many times would you like to slide Down, currently " + slideDownChoice);
                slideDownChoice = inC.nextInt();
                t = new SlideD();
                for (int i = 0; i < slideDownChoice; i++){footage.transform(t);}
                System.out.println("Sliding Footage Down");
                printEditMenu();
                break;

            case "NU": // Slide up number
                previousChoice = editChoice;
                System.out.println("How many times would you like to slide Up, currently " + slideUpChoice);
                slideUpChoice = inC.nextInt();
                t = new SlideU();
                for (int i = 0; i < slideUpChoice; i++){footage.transform(t);}
                System.out.println("Sliding Footage Up");
                printEditMenu();
                break;

            case "R": // Repeat last operation
                System.out.println("Repeating last operation");
                runEditMenu(previousChoice);
                break;
            case "Q": // Quit Editing
                System.out.println("Quitting Editing");
                break;

            default:
                System.out.println("not a valid choice");

        }


    }
        while (!editChoice.equals("Q"));

    }



    // An example method that you might like to call from your
    // menu. Whenever you need to do something in the GUI thread
    // from another thread you have to call Platform.runLater
    // This is a javafx method that queues your code ready for the GUI
    // thread to run when it is ready. We use that strange lambda Java 8 syntax
    // again although this time there are no parameters hence ()
    private void terminateAnimation() {
        doRun = false;
    }

    // Here is another example. This one is useful because it creates
    // the GUI grid. You will need to call this from the menu, e.g. when starting
    // an animation.
    private void createGrid() {
        Platform.runLater(() -> {

            // Update UI here
            //createGrid(numRows);   // E.g. let's create a 20 x 20 grid
            createGrid(footage.getNumRows());
        });
    }

    // I'll give you this method since I haven't done
    // much on javafx. This also creates a scene and adds it to the stage;
    // all very theatrical.
    private void createGrid(int numRows) {
        // Creates a grid so that we can display the animation. We will see
        // other layout panes in the lectures.
        grid = new GridPane();

        // We need to create a 2D array of javafx Buttons that we will
        // add to the grid. The 2D array makes it much easier to change
        // the colour of the buttons in the grid; easy lookup using
        // 2D indicies. Note that we make this read-only for this display
        // onlt grid. If you go for flair marks, then I'd imagine that you
        // could create something similar that allows you to edits frames
        // in the footage.
        gridArray = new Button[numRows][numRows];
        Button displayButton = null;
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numRows; col++) {  // The display is square
                displayButton = new Button();
                gridArray[row][col] = displayButton;
                displayButton.setDisable(true);
                grid.add(displayButton, col, row);
            }
        }

        // Create a scene to hold the grid of buttons
        // The stage will "shrink wrap" around the grid of buttons,
        // so we don't need to set its height and width.
        scene = new Scene(grid);
        stage.setScene(scene);
        scene.getStylesheets().add(Animator.class.getResource("styling.css").toExternalForm());

        // Make it resizable so that the window shrinks to fit the scene grid
        stage.setResizable(true);
        stage.sizeToScene();
        // Raise the curtain on the stage!
        stage.show();
        // Stop the user from resizing the window
        stage.setResizable(false);
    }

    // I'll also give you this one too. This does the animation and sets colours for
    // the grid buttons. You will have to call this from the menu. You should not
    // need to change this code, unless you want to add more colours
    private void runAnimation() {
        if (footage == null) {
            System.out.println("Load the footage first");
        } else {
            Runnable animationToRun = () -> {

                Background yellowBg = new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY));
                Background blackBg = new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY));
                Background blueBg = new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY));
                Background whiteBg = new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY));

                doRun = true;
                int numFrames = footage.getNumFrames();
                int currentFrameIndex = 0;
                Background bck = null;
                while (doRun) {
                    if (currentFrameIndex >= numFrames - 1) currentFrameIndex = 0;
                    IFrame currentFrame = footage.getFrame(currentFrameIndex);
                    // Iterate through the current frame displaying appropriate colour
                    for (int row = 0; row < footage.getNumRows(); row++) {
                        for (int col = 0; col < footage.getNumRows(); col++) {
                            switch (currentFrame.getChar(row, col)) {
                                case 'l':
                                    bck = yellowBg;
                                    break;
                                case 'r':
                                    bck = blackBg;
                                    break;
                                case 'b':
                                    bck = blueBg;
                                    break;
                                default:
                                    bck = whiteBg;
                            }
                            final int theRow = row;
                            final int theCol = col;
                            final Background backgroundColour = bck;
                            // This is another lambda callback. When the Platform
                            // GUI thread is ready it will run this code.
                            Platform.runLater(() -> {

                                // Update UI here
                                // We have to make this request on a queue that the GUI thread
                                // will run when ready.
                                gridArray[theRow][theCol].setBackground(backgroundColour);
                            });

                        }
                    }
                    try {
                        // This is how we delay for 200th of a second
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                    }
                    currentFrameIndex++;
                }
            };
            animation = new Thread(animationToRun);
            animation.start();
        }
    }
}

