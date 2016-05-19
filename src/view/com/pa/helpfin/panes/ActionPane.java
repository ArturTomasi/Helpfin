package com.pa.helpfin.panes;

import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * @author artur
 */
public class ActionPane 
    extends 
        VBox
{
    public ActionPane() 
    {
        initComponents();
    }
    
    public void setActions( List<Button> buttons )
    {
        getChildren().clear();
        
        getChildren().addAll( buttons );
    }
    
    private void initComponents()
    {
        setSpacing( 10 );
        
        setStyle( "-fx-padding: 10 4 4 4;" );
    }
}
