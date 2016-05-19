package com.pa.helpfin.view.util;

import javafx.scene.control.Label;

/**
 * @author artur
 */
public class LabelField 
    extends 
        Label
{
    public LabelField( String text )
    {
        this( text, false );
    }
    
    public LabelField( String text, boolean requeried )
    {
        setText( text );
        
         if( requeried )
            setStyle( "-fx-font: bold italic 10pt 'Arial'; -fx-text-fill: black;" );
    }
    
    
}
