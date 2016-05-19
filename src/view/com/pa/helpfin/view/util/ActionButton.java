package com.pa.helpfin.view.util;

import com.pa.helpfin.model.ApplicationUtilities;
import com.pa.helpfin.model.ResourceLocator;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author artur
 */
public class ActionButton
    extends 
        Button
{
    private ActionButton(){}
    
    public ActionButton( String name, String icon, EventHandler handler ) 
    {
        try
        {
            ImageView imageView =  new ImageView( new Image( ResourceLocator.getInstance().getImageResource( icon ) ) );
            
            imageView.setFitHeight( 20 );
            imageView.setFitWidth( 20 );

            setGraphic( imageView );
            setText( name );
            setOnAction( handler );
            setCursor( Cursor.HAND );
            setAlignment( Pos.CENTER_LEFT );
            setMinWidth( 95 );
            setMaxWidth( 95 );
        
            setStyle( "-fx-background-radius: 10; " +
                      "-fx-background-color: linear-gradient( from 0% 0% to 100% 100%, silver 0%, #415A78 100%); "+
                      "-fx-border-color: gray; " +
                      "-fx-border-radius: 10; " +
                      "-fx-border-width: 2;"      +
                      "-fx-font-size: 10;"      +
                      "-fx-font-weight: bold;"  );
        }
        
        catch ( Exception e )
        {
            ApplicationUtilities.logException( e );
        }
    }
    
}
