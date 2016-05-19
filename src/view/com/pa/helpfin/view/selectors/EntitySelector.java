package com.pa.helpfin.view.selectors;

import com.pa.helpfin.model.ApplicationUtilities;
import com.pa.helpfin.model.data.Entity;

/**
 * @author artur
 */
public class EntitySelector 
    extends 
        ItemSelector<Entity>
{
    public EntitySelector() 
    {
        super( "Entidade do Lançamento" );
        
        try
        {
            setItems( com.pa.helpfin.model.ModuleContext
                            .getInstance()
                            .getEntityManager()
                            .getValues() );
        }
        
        catch( Exception e )
        {
            ApplicationUtilities.logException( e );
        }
    }
}
