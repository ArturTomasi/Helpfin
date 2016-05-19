package com.pa.helpfin.panes;

import com.pa.helpfin.panes.modules.AbstractModulesPane;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Button;

/**
 * @author artur
 */
public class ConfigurationPane 
    extends 
        AbstractModulesPane
{
    public ConfigurationPane() 
    {
    }
    
    @Override
    public List<Button> getActions()
    {
        return new ArrayList();
    }

    @Override
    public void refreshContent()
    {
    }

    @Override
    public void resizeComponents( double height, double width )
    {
    }
}
