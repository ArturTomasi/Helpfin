package com.pa.helpfin.view.util;

import com.pa.helpfin.model.ResourceLocator;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author artur
 */
public class ContextMenuItem 
    extends 
        javafx.scene.control.MenuItem
{

    public ContextMenuItem( String text, String icon, EventHandler eventHandler ) 
    {
        setOnAction( eventHandler );
    
        ImageView imageView =  new ImageView( new Image( ResourceLocator.getInstance().getImageResource( icon ) ) );
        
        imageView.setFitHeight( 20 );
        imageView.setFitWidth( 20 );

        setGraphic( imageView );
        
        setText( text );
        setStyle( "-fx-background-color: transparent;"+
                  "-fx-border-color: gray; " +
                  "-fx-border-width: 1;"  );
    }
    
}
