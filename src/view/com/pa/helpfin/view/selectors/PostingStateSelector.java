package com.pa.helpfin.view.selectors;

import com.pa.helpfin.model.data.Option;
import com.pa.helpfin.model.data.Posting;

/**
 * @author artur
 */
public class PostingStateSelector 
    extends 
        ItemSelector<Option>
{
    public PostingStateSelector() 
    {
        super( "Selecione uma situação", 
                new Option( Posting.STATE_REGISTRED, Posting.STATES[ Posting.STATE_REGISTRED ] ), 
                new Option( Posting.STATE_PROGRESS,  Posting.STATES[ Posting.STATE_PROGRESS ]  ), 
                new Option( Posting.STATE_FINISHED,  Posting.STATES[ Posting.STATE_FINISHED ]  ), 
                new Option( Posting.STATE_DELETED,   Posting.STATES[ Posting.STATE_DELETED ]   ) );
    }
}
