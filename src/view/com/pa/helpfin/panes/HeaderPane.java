package com.pa.helpfin.panes;

import com.pa.helpfin.model.ApplicationUtilities;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/**
 * @author artur
 */
public class HeaderPane
    extends 
        HBox
{
    public HeaderPane() 
    {
        initComponents();
    }
   
    
    public void resize( BorderPane pane )
    {
        setLayoutX( pane.getWidth() );
        setSpacing( pane.getWidth() - lbApplication.getWidth() - lbModule.getWidth() );
        requestLayout();
    }

    
    public void setApplicationName( String text )
    {
        lbApplication.setText( text );
        lbApplication.autosize();
    }
    
    
    public void setModule( String text )
    {
        lbModule.setText( text );
        lbModule.autosize();
    }
    
    
    private void initComponents()
    {
        lbApplication.setCache( true );
        lbApplication.setText( ApplicationUtilities.getInstance().getCompanny() );
        lbApplication.setStyle( "-fx-padding: 10 10 0 0;" + 
                                "-fx-font-weight: bolder;" +
                                "-fx-font-size: 26pt;" +
                                "-fx-font-family: \"Helvetica, Verdana, sans-serif\";" +
                                "-fx-text-fill: " + ApplicationUtilities.getColor() );
      
        lbModule.setCache( true );
        lbModule.setText( "Home" );
        lbModule.setStyle( "-fx-padding: 10 0 0 10;" +
                           "-fx-font-weight: bolder;" +
                           "-fx-font-size: 26pt;" +
                           "-fx-font-family: \"Helvetica, Verdana, sans-serif\";" +
                           "-fx-text-fill: " + ApplicationUtilities.getColor() );
        
        setStyle( "-fx-border-color:" + ApplicationUtilities.getColor() + "-fx-border-width: 0 0 2 0; -fx-padding: 4 0 4 0;" + ApplicationUtilities.getBackground2() );
        getChildren().addAll( lbModule, lbApplication );
    }
    
    private Label lbApplication = new Label();
    private Label lbModule = new Label();
}
