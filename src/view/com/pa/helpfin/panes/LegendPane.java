package com.pa.helpfin.panes;

import com.pa.helpfin.model.ApplicationUtilities;
import com.pa.helpfin.model.ResourceLocator;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * @author artur
 */
public class LegendPane
    extends 
        HBox
{
    public LegendPane() 
    {
        setSpacing( 10 );
        setHeight( 25 );
    }
    
    public void addItems( LegendItem ... items )
    {
        getChildren().addAll( items );
    }
            
    public static class LegendItem
        extends 
            Button
    {
        public LegendItem( String name, String icon ) 
        {
            setText( name );
            
            ImageView imageView = new ImageView( new Image( ResourceLocator.getInstance().getImageResource( icon ) ) );
            imageView.setFitHeight( 20 );
            imageView.setFitWidth( 20 );

            setGraphic( imageView );
            setText( name );
            setAlignment( Pos.CENTER_LEFT );
                    
            setStyle( "-fx-background-radius: 10; " +
                       "-fx-background-color: transparent; "+
                       "-fx-text-fill:" + ApplicationUtilities.getColor2() +
                       "-fx-font-family: \"Helvetica, Verdana, sans-serif\";" +
                       "-fx-border-radius: 10px; " +
                       "-fx-border-width: 2px;"      +
                       "-fx-font-size: 12px;"      +
                       "-fx-font-weight: bolder;"  );
        }
    }
    
}
