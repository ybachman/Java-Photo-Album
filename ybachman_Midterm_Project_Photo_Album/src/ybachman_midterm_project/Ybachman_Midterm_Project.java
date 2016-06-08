/*
Yulia Bachman
900301634
10/4/15
Midterm Project - Photo Album
*/

package ybachman_midterm_project;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Ybachman_Midterm_Project extends Application 
{   
    private ArrayList<ImageButton> imageButtons = new ArrayList<>();
    private FlowPane imagePane = new FlowPane();
    private ComboBox<String> deleteOrEditSelector = new ComboBox<>();
    ComboBox<String> sort = new ComboBox<>();
    
    @Override
    public void start(Stage primaryStage) 
    {
        BorderPane border = new BorderPane();
        
        //Create an image flowpane for photos
        imagePane.setPadding(new Insets(5, 5, 5, 5));
        imagePane.setHgap(5);
        imagePane.setVgap(5);
        
        //Add some images to the gallery to start things off.
        initializeGallery();
        
        //Put all thumbnail button images in the grid
        imagePane.getChildren().addAll(imageButtons);
        
        //VBox on the left for menu options: Add and delete image (Buttons), and sort drop down menu
        VBox optionsPane = new VBox(20);
        optionsPane.setPadding(new Insets(5, 5, 5, 5));
        optionsPane.setStyle("-fx-border-color: green");
        optionsPane.setStyle("-fx-border-width: 2px; -fx-border-color: green");
          
        Button addButton = new Button("Add Image");
        
        //Upon clicking the add button, add an image
            addButton.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent event)
                {
                    addImage();
                }
            });
            
        deleteOrEditSelector.getItems().addAll("Edit Image", "Delete Image");
        deleteOrEditSelector.setValue("Edit Image");
        
        sort.getItems().addAll("By Title", "By Date", "By Place");
        sort.setStyle("-fx-color: green");
        sort.setValue("Sort");

        //Upon selecting the sort, do the cooresponding sort
        sort.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                sortImages();
            }
        });

        optionsPane.getChildren().addAll(addButton, deleteOrEditSelector, sort);
        
        //Add gridpane and vbox to Pane 
        border.setLeft(optionsPane);
        border.setCenter(imagePane);
        
        //Add everything to the scene
        Scene scene = new Scene(border, 700, 400);
        
        primaryStage.setTitle("My Photos");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    /**
     * Add an image to the gallery.
     */
    public void addImage()
    {
        HBox newImage = new HBox();
        TextField imageText = new TextField();
        Label newImageLabel = new Label("Enter Image URL: ");
        Button addImage = new Button("Add Image");
        
        newImage.getChildren().addAll(newImageLabel, imageText, addImage);
        Stage addNewImage = new Stage();
        addImage.setOnAction(e -> {
            // Call the createButtonImage() method and add it to the imageButtons array.
            ImageButton addedImageButton = createButtonImage(new ImageObject(imageText.getText()));
            imageButtons.add(addedImageButton);
            
            // Call imagePane.getChildren().clear() method to clear the gallery flowpane.
            imagePane.getChildren().clear();
            
            // Call imagePane.getChildren().addAll(imageButtons) to add all the images, including the new one, to the pane.
            imagePane.getChildren().addAll(imageButtons);
            
            // Call the createEditStage window on that image so the user can edit the metadeta if they wish. This is optional.
            createEditStage(addedImageButton.getImageObjectOutOfThisDamnButton()); 
            
            addNewImage.close();
        });
        
        // Create a new window with an area to get the image URL from the user and an "Add Image" button.
        Scene addScene = new Scene(newImage);
        addNewImage.setTitle("Add Image");
        addNewImage.setScene(addScene);
        addNewImage.show();}
    
    //Sort the images and repopulate the gallery
    public void sortImages()
    {
        //Sort the images.
        imageButtons.sort(null);
        
        //Clear the imagePane.
        imagePane.getChildren().clear();
        
        //Re-add the sorted images to the imagepane
        imagePane.getChildren().addAll(imageButtons);
    }
    
    //Create a button containing the image. This will be the gallery "thumbnail"
    
    public ImageButton createButtonImage(ImageObject image)
    {
        //Alter the image size so it will fit nicely in the button.
        image.setFitHeight(150);
        image.setFitWidth(150);
        
        //Create the button.
        ImageButton imageButton = new ImageButton("", image);
        
        ImageButtonDeleteHandler handler = new ImageButtonDeleteHandler();
        
        // Set the handler.
        imageButton.setOnAction(handler);
        
        return imageButton;
    }

    //Populate the gallery with some images when the user first starts the program.
     
    public void initializeGallery()
    {
        //Create the initial ImageObjects. Size 320 by 320
        ImageObject image1 = new ImageObject("http://i60.tinypic.com/345kgsz.jpg");
        ImageObject image2 = new ImageObject("http://i62.tinypic.com/ih5spl.jpg");
        ImageObject image3 = new ImageObject("http://i58.tinypic.com/2vshwxx.jpg");
        ImageObject image4 = new ImageObject("http://i59.tinypic.com/1zcpfrl.jpg");
        ImageObject image5 = new ImageObject("http://i60.tinypic.com/34rd0u0.jpg");
        
        //Create image buttons and add them to the array.
        imageButtons.add(createButtonImage(image1));
        imageButtons.add(createButtonImage(image2));
        imageButtons.add(createButtonImage(image3));
        imageButtons.add(createButtonImage(image4));
        imageButtons.add(createButtonImage(image5));
    }
  
    //Create the stage that pops up when user wants to edit
    
    public void createEditStage(ImageObject image)
    {
        //Create Stage
        Stage stage = new Stage();

        //Create new Border pane for new stage
        BorderPane pane = new BorderPane();

        //GridPane for Image Data
        GridPane imageData = new GridPane();
        
        FlowPane fPane = new FlowPane();
        
        //Use the parameter of the first image flowpane
        fPane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        fPane.setHgap(5.5);
        fPane.setVgap(5.5);

        //Text fields
        imageData.add(new Label("Title: "), 0, 0);
        TextField titleText = new TextField();
        imageData.add(titleText, 1, 0);

        if (image.getImageTitle() != null )
        {
            titleText.setText(image.getImageTitle());
        }

        imageData.add(new Label("Description: "), 0, 1);
        TextField descText = new TextField();
        imageData.add(descText, 1, 1);

        if (image.getImageDescription() != null )
        {
            descText.setText(image.getImageDescription());
        }

        imageData.add(new Label("Date: "), 0, 2);
        TextField dateText = new TextField();
        imageData.add(dateText, 1, 2);

        if (image.getImageDate() != null )
        {
            dateText.setText(image.getImageDate());
        }

        imageData.add(new Label("Location: "), 0, 3);
        TextField locationText = new TextField();
        imageData.add(locationText, 1, 3);

        if (image.getImageLocation() != null )
        {
           locationText.setText(image.getImageLocation());
        }

        Label space = new Label(" ");
        imageData.add(space, 1, 4);

        //Save Button to save changes
        Button save = new Button("Save Changes");
        imageData.add(save, 1, 5);

        //Action Handler for the Save Changes button
        save.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {  
                image.setImageTitle(titleText.getText());
                image.setImageDescription(descText.getText());
                image.setImageDate(dateText.getText());
                image.setImageLocation(locationText.getText());

                stage.close();
            }
        });

        VBox picture = new VBox(20);
        picture.getChildren().add(new ImageView(image.getImageURL()));
        pane.setRight(picture);

        //Add the data and image to the new window
        pane.setLeft(imageData);
        pane.setCenter(new Label("   "));

        //Create Scene
        Scene scene = new Scene(pane);
        stage.setTitle(image.getImageTitle());
        stage.setScene(scene);
        stage.show();
        }
    
    public class ImageObject extends ImageView
    {
        //Variables
        private final String imageURL;
        private String imageTitle;
        private String imageDescription;
        private String imageDate;
        private String imageLocation;
        
        
        //Constructor
        ImageObject(String imageURL)
        {
             super(imageURL);
             this.imageURL = imageURL;
             this.imageTitle = "";
             this.imageDescription = "";
             this.imageDate = "";
             this.imageLocation = "";
        }
        
        ImageObject(String url, String title, String desc, String date, String location)
        {
            this(url);
            //this.imageURL = url;
            this.imageTitle = title;
            this.imageDescription = desc;
            this.imageDate = date;
            this.imageLocation = location;
        }
        
        //Getters
        public String getImageURL()
        {
            return imageURL;
        }
        
        public String getImageTitle()
        {
            return imageTitle;
        }
        
        public String getImageDescription()
        {
            return imageDescription;
        }
        
        public String getImageDate()
        {
            return imageDate;
        }
        
        public String getImageLocation()
        {
            return imageLocation;
        }
        
        public void setImageTitle(String t)
        {
            this.imageTitle = t;
        }
        
        public void setImageDescription(String desc)
        {
            this.imageDescription = desc;
        }
        
        public void setImageDate(String d)
        {
            this.imageDate = d;
        }
        
        public void setImageLocation(String l)
        {
            this.imageLocation = l;
        }
    }
    
    class ImageButtonDeleteHandler implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event) 
        {
            // Figure out which button was pressed and do stuff.
            ImageButton imageButton = (ImageButton)event.getSource();
            
            // If the user has Delete Image selected, delete the image.
            if ( deleteOrEditSelector.getValue().equals("Delete Image"))
            {
                imageButtons.remove(imageButton);
                imagePane.getChildren().remove(imageButton);
            }
            else if ( deleteOrEditSelector.getValue().equals("Edit Image"))
            {
                createEditStage(imageButton.getImageObjectOutOfThisDamnButton());
            }
        }   
    }
    
    //Sort the images
    public class ImageButton extends Button implements Comparable<ImageButton>
    {
        private final ImageObject imageObj;
        
        public ImageButton(String s, ImageObject image)
        {
            super(s, image);
            imageObj = image;
        }
        
        public ImageObject getImageObjectOutOfThisDamnButton()
        {
            return imageObj;
        }
        
        @Override
        public int compareTo(ImageButton o) 
        {
            // Depending on which sort is selected.
            switch ( sort.getValue() )
            {
                case "By Title":
                    return this.imageObj.imageTitle.compareTo(o.getImageObjectOutOfThisDamnButton().getImageTitle());
                case "By Date":
                    return this.imageObj.imageDate.compareTo(o.getImageObjectOutOfThisDamnButton().getImageDate());
                case "By Place":
                    return this.imageObj.imageLocation.compareTo(o.getImageObjectOutOfThisDamnButton().getImageLocation());
                default:
                    return -1;
            }
        } 
    }
}