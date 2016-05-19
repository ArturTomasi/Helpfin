package com.pa.helpfin.view.util;

import com.pa.helpfin.model.ApplicationUtilities;
import com.pa.helpfin.model.ResourceLocator;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author artur
 */
public class MenuItem 
    extends 
        Button
{
    public MenuItem( String name, String icon ) 
    {
        try
        {
            imageView =  new ImageView( new Image( ResourceLocator.getInstance().getImageResource( icon ) ) );
            
            this.name = name;
            
            initComponents();
        }
        catch( Exception e )
        {
            ApplicationUtilities.logException( e );
        }
    }

    public String getName() 
    {
        return name;
    }

    public ImageView getImageView() 
    {
        return imageView;
    }
    
    
    private void initComponents()
    {
        imageView.setFitHeight( 60 );
        imageView.setFitWidth( 60 );

        setGraphic( imageView );
        setCursor( Cursor.HAND );
        setAlignment( Pos.CENTER_LEFT );
        setMinWidth( 200 );
        setText( name );

        setStyle( "-fx-background-radius: 10; " +
                  "-fx-background-color: linear-gradient( from 0% 0% to 100% 100%, silver 0%, #415A78 100%); "+
                  "-fx-border-color: gray; " +
                  "-fx-border-radius: 10; " +
                  "-fx-border-width: 2;"      +
                  "-fx-font-weight: bold;"  );
    }
    
    private ImageView imageView; 
    private String name; 
}
