package com.pa.helpfin.view.selectors;

import com.pa.helpfin.model.ApplicationUtilities;
import com.pa.helpfin.model.data.PostingCategory;

/**
 * @author artur
 */
public class PostingCategorySelector 
   extends 
        ItemSelector<PostingCategory>
{
    public PostingCategorySelector() 
    {
        super( "Categoira do Lançamento" );
        
        try
        {
            setItems( com.pa.helpfin.model.ModuleContext
                            .getInstance()
                            .getPostingCategoryManager()
                            .getValues() );
        }
        
        catch( Exception e )
        {
            ApplicationUtilities.logException( e );
        }
    }
}
