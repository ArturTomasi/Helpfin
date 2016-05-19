package com.pa.helpfin.panes.modules;

import com.pa.helpfin.view.util.MenuItem;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * @author artur
 */
public abstract class AbstractModulesPane 
    extends 
        AnchorPane
{
    public abstract List<Button> getActions();
    public abstract void refreshContent();
    public abstract void resizeComponents( double height, double width );
    
    public void setMenuItem( MenuItem item ) 
    {
        this.item = item;
    }
    
    public MenuItem getMenuItem()
    {
        return item;
    }
    
    public String getName()
    {
        return item.getName();
    }
    
    public ImageView getImageView()
    {
        if( item == null )
        {
            return null;
        }
        
        return item.getImageView();
    }
    
    private MenuItem item;
}
