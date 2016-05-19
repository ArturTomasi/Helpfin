package com.pa.helpfin.view.util;


import com.pa.helpfin.model.data.PostingCategory;
import com.pa.helpfin.model.data.PostingType;
import com.pa.helpfin.panes.PostingTabPane;
import com.pa.helpfin.view.inspectors.PostingCategoryDetails;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class TESTE
    extends 
        Application
{
    public static void main(String[] args) {
        launch(args);
    }
    
    PostingTree tree = new PostingTree();
    PostingCategoryDetails details = new PostingCategoryDetails();
    PostingTabPane postingPane = new PostingTabPane();
        
    private void refreshContent()
    {
        Object source = tree.getSelectedNode();
        
        if( source instanceof PostingCategory)
        {
            PostingCategory category = (PostingCategory) source;
            
            details.setSource( category );
            
            postingPane.setPostings( category );
        }
      
        if( source instanceof PostingType)
        {
            PostingType type = (PostingType) source;
            
            postingPane.setPostings( type );
        }
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        TabPane pane = new TabPane( details, postingPane );

        BorderPane borderPane = new BorderPane();
        borderPane.setLeft( tree );
        borderPane.setCenter( pane );


        tree.addEventFilter( PostingTree.Events.ON_SELECT, new EventHandler<Event>() 
        {
            @Override
            public void handle(Event t) 
            {
                refreshContent();
            }
        } );

        primaryStage.setScene(new Scene( borderPane ));
        primaryStage.show();
    }
  
}