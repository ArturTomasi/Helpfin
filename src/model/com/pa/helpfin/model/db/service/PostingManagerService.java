package com.pa.helpfin.model.db.service;

import com.pa.helpfin.model.data.DefaultFilter;
import com.pa.helpfin.model.data.Posting;
import com.pa.helpfin.model.data.PostingCategory;
import com.pa.helpfin.model.data.PostingType;
import com.pa.helpfin.model.data.User;
import com.pa.helpfin.model.db.Database;
import com.pa.helpfin.model.db.transactions.PostingManagerTransactions;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author artur
 */
public class PostingManagerService
    implements 
        Manager<Posting>
{
    private static PostingManagerTransactions transactions;
    private static PostingManagerService service;
    
    public static PostingManagerService getInstance()
    {
        if( service == null )
        {
            service = new PostingManagerService();
        }
        
        return  service;
    }
    
    private PostingManagerService()
    {
        transactions = new PostingManagerTransactions();
    }
    
    @Override
    public void addValue( Posting value ) throws Exception 
    {
        Database db = Database.getInstance();
        
        try 
        {
            transactions.addPosting( db, value );
        } 
        
        finally
        {
            db.release();
        }
    }

    @Override
    public void deleteValue( Posting value ) throws Exception 
    {
        Database db = Database.getInstance();
        
        try 
        {
            transactions.deletePosting( db, value );
        } 
        
        finally
        {
            db.release();
        }
    }

    public void deleteNextPortions( Posting value ) throws Exception 
    {
        Database db = Database.getInstance();
        
        try 
        {
            transactions.deleteNextPortions( db, value );
        } 
        
        finally
        {
            db.release();
        }
    }

    @Override
    public void updateValue( Posting value ) throws Exception
    {
        Database db = Database.getInstance();
        
        try 
        {
            transactions.updatePosting( db, value );
        } 
        
        finally
        {
            db.release();
        }
    }

    @Override
    public Posting getValue( int id ) throws Exception 
    {
        Database db = Database.getInstance();
        
        try 
        {
            return transactions.getPosting( db, id );
        } 
        
        finally
        {
            db.release();
        }
    }

    @Override
    public Posting getValue( String name ) throws Exception
    {
        Database db = Database.getInstance();
        
        try 
        {
            return transactions.getPosting( db, name );
        } 
        
        finally
        {
            db.release();
        }
    }

    @Override
    public List<Posting> getValues() throws Exception
    {
        Database db = Database.getInstance();
        
        try 
        {
            return transactions.getPostings( db, false );
        } 
        
        finally
        {
            db.release();
        }
    }

    @Override
    public List<Posting> getValues( boolean showInactives ) throws Exception 
    {
        Database db = Database.getInstance();
        
        try 
        {
            return transactions.getPostings( db, showInactives );
        } 
        
        finally
        {
            db.release();
        }
    }
    
    public List<Posting> getValues( int category ) throws Exception 
    {
        Database db = Database.getInstance();
        
        try 
        {
            return transactions.getPostings( db, category );
        } 
        
        finally
        {
            db.release();
        }
    }

    public List<Posting> getValues( DefaultFilter defaultFilter ) throws Exception 
    {
        Database db = Database.getInstance();
        
        try 
        {
            return transactions.getPostings( db, defaultFilter );
        } 
        
        finally
        {
            db.release();
        }
    }
    
    public List<Posting> getMonthPostings( int type ) throws Exception 
    {
        Database db = Database.getInstance();
        
        try 
        {
            return transactions.getMonthPostings( db, type );
        } 
        
        finally
        {
            db.release();
        }
    }
    
    public List<Posting> getMonthlyBalance() throws Exception 
    {
        Database db = Database.getInstance();
        
        try 
        {
            return transactions.getMonthlyBalance( db );
        } 
        
        finally
        {
            db.release();
        }
    }
    
    public List<Posting> getCurrentPostings( User user ) throws Exception 
    {
        Database db = Database.getInstance();
        
        try 
        {
            return transactions.getCurrentPostings( db, user );
        } 
        
        finally
        {
            db.release();
        }
    }

    public List<Posting> getNextPostings() throws Exception 
    {
        Database db = Database.getInstance();
        
        try 
        {
            return transactions.getNextPostings( db );
        } 
        
        finally
        {
            db.release();
        }
    }

    public Posting getLastPosting( Posting posting ) throws Exception 
    {
        Database db = Database.getInstance();
        
        try 
        {
            return transactions.getLastPosting( db, posting );
        } 
        
        finally
        {
            db.release();
        }
    }
    
    public void updateNextPortion( Posting posting ) throws Exception 
    {
        Database db = Database.getInstance();
        
        try 
        {
            transactions.updateNextPortion( db, posting );
        } 
        
        finally
        {
            db.release();
        }
    }
    
    public Map postingsOfMonths( int type ) throws Exception
    {
        Database db = Database.getInstance();
        
        try 
        {
            return transactions.postingsOfMonths( db, type );
        } 
        
        finally
        {
            db.release();
        }
    }
    
    public List<Posting> getCompletionPosting() throws Exception
    {
        Database db = Database.getInstance();
        
        try 
        {
            return transactions.getCompletionPosting( db );
        } 
        
        finally
        {
            db.release();
        }
    }
    
    @Override
    public List<String> isUnique( Posting value ) throws Exception 
    {
        return Collections.EMPTY_LIST;
    }
    
    public HashMap<Integer, Long> countPosting( int category ) throws Exception 
    {
         Database db = Database.getInstance();
        
        try 
        {
            return transactions.countPosting( db, category );
        } 
        
        finally
        {
            db.release();
        }
    }
    
    public int countPostingType( int type ) throws Exception 
    {
         Database db = Database.getInstance();
        
        try 
        {
            return transactions.countPostingType( db, type );
        } 
        
        finally
        {
            db.release();
        }
    }

    public String getDrilldown() throws Exception 
    {
         Database db = Database.getInstance();
        
        try 
        {
            return transactions.getDrilldown( db );
        } 
        
        finally
        {
            db.release();
        }
    }
}
