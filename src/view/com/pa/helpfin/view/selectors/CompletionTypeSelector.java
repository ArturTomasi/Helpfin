package com.pa.helpfin.view.selectors;

import com.pa.helpfin.model.ApplicationUtilities;
import com.pa.helpfin.model.data.CompletionType;

/**
 * @author artur
 */
public class CompletionTypeSelector
    extends 
        ItemSelector<CompletionType>
{
    public CompletionTypeSelector() 
    {
        super( "Tipo Finalização" );
        
        try
        {
            setItems( com.pa.helpfin.model.ModuleContext
                            .getInstance()
                            .getCompletionTypeManager()
                            .getValues() );
        }
        
        catch( Exception e )
        {
            ApplicationUtilities.logException( e );
        }
    }
}
