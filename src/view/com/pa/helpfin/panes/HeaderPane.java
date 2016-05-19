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
        lbApplication.setFont( Font.font( "cursive", FontWeight.BOLD, FontPosture.ITALIC, 26 ) );
        lbApplication.setCache( true );
        lbApplication.setText( ApplicationUtilities.getInstance().getCompanny() );
        lbApplication.setStyle( "-fx-padding: 10 10 0 0" );
      
        lbModule.setFont( Font.font( "cursive", FontWeight.BOLD, FontPosture.ITALIC, 26 ) );
        lbModule.setCache( true );
        lbModule.setText( "Home" );
        lbModule.setStyle( "-fx-padding: 10 0 0 10" );
        
        setStyle( "-fx-border-color: #415A78; -fx-border-width: 0 0 2 0; -fx-padding: 4 0 4 0" );
        getChildren().addAll( lbModule, lbApplication );
    }
    
    private Label lbApplication = new Label();
    private Label lbModule = new Label();
}
