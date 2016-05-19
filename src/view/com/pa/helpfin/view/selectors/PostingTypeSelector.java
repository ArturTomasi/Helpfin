package com.pa.helpfin.view.selectors;

import com.pa.helpfin.model.ApplicationUtilities;
import com.pa.helpfin.model.data.PostingType;

/**
 * @author artur
 */
public class PostingTypeSelector
    extends 
        ItemSelector<PostingType>
{
    public PostingTypeSelector()
    {
        super( "Tipo de Lançamento" );
        
        try
        {
            setItems( com.pa.helpfin.model.ModuleContext
                            .getInstance()
                            .getPostingTypeManager()
                            .getValues() );
        }
        
        catch( Exception e )
        {
            ApplicationUtilities.logException( e );
        }
    }
}
