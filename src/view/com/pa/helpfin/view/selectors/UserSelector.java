package com.pa.helpfin.view.selectors;

import com.pa.helpfin.model.ApplicationUtilities;
import com.pa.helpfin.model.data.User;

/**
 * @author artur
 */
public class UserSelector
   extends 
        ItemSelector<User>
{
    public UserSelector() 
    {
        super( "Responsável do Lançamento" );
        
        try
        {
            setItems( com.pa.helpfin.model.ModuleContext
                            .getInstance()
                            .getUserManager()
                            .getValues() );
        }
        
        catch( Exception e )
        {
            ApplicationUtilities.logException( e );
        }
    }
}
