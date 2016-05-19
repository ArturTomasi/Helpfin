package com.pa.helpfin.control.tasks;

import com.pa.helpfin.model.ApplicationUtilities;
import com.pa.helpfin.model.data.Posting;
import com.pa.helpfin.model.db.service.PostingManagerService;
import java.util.List;

/**
 * @author artur
 */
public class MainTasks 
{
    public static void main( String[] args )
    {
        MainTasks tasks = new MainTasks();
        tasks.run();
    }
    
    
    public void run()
    {
        completionPostings();
    }
    
    
    private void completionPostings()
    {
        try
        {
            PostingManagerService pm = com.pa.helpfin.model.ModuleContext
                                                        .getInstance()
                                                        .getPostingManager();
            
            List<Posting> postings = pm.getCompletionPosting();
            
            postings.forEach( (posting) -> 
            {
                try
                {
                    posting.setRealDate( posting.getEstimateDate() );
                    posting.setRealValue( posting.getEstimateValue() );
                    posting.setState( Posting.STATE_FINISHED );

                    pm.updateValue( posting );
                    pm.updateNextPortion( posting);
                }
                
                catch ( Exception e )
                {
                   ApplicationUtilities.logException( e );
                }
            } );
            
        }
        
        catch( Exception e )
        {
            ApplicationUtilities.logException( e );
        }
    }
    
}
