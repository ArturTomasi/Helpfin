package com.pa.helpfin.model.db.service;

import com.pa.helpfin.model.data.PostingType;
import com.pa.helpfin.model.db.Database;
import com.pa.helpfin.model.db.transactions.PostingTypeManagerTransactions;
import java.util.List;

/**
 * @author artur
 */
public class PostingTypeManagerService 
    implements 
        Manager<PostingType>
{
    private static PostingTypeManagerTransactions transactions;
    private static PostingTypeManagerService service;
    
    public static PostingTypeManagerService getInstance()
    {
        if( service == null )
        {
            service = new PostingTypeManagerService();
        }
        
        return  service;
    }
    
    private PostingTypeManagerService()
    {
        transactions = new PostingTypeManagerTransactions();
    }
    
    @Override
    public void addValue( PostingType postingType ) throws Exception 
    {
        Database db = Database.getInstance();
        
        try
        {
            transactions.addPostingType( db, postingType );
        }
        
        finally
        {
            db.release();
        }
    }

    @Override
    public void deleteValue( PostingType postingType ) throws Exception 
    {
        Database db = Database.getInstance();
        
        try
        {
            transactions.deletePostingType( db, postingType );
        }
        
        finally
        {
            db.release();
        }   
    }

    @Override
    public void updateValue( PostingType postingType ) throws Exception 
    {
        Database db = Database.getInstance();
        
        try
        {
            transactions.updatePostingType( db, postingType );
        }
        
        finally
        {
            db.release();
        }
    }

    @Override
    public PostingType getValue( int postingTypeId ) throws Exception 
    {
        Database db = Database.getInstance();
        
        try
        {
            return transactions.getPostingType( db, postingTypeId );
        }
        
        finally
        {
            db.release();
        }
    }
    
    @Override
    public PostingType getValue( String name ) throws Exception 
    {
        Database db = Database.getInstance();
        
        try
        {
            return transactions.getPostingType( db, name );
        }
        
        finally
        {
            db.release();
        }
        
    }

    @Override
    public List<PostingType> getValues() throws Exception
    {
        Database db = Database.getInstance();
        
        try
        {
            return transactions.getPostingTypes( db, false );
        }
        
        finally
        {
            db.release();
        }
    }

    @Override
    public List<PostingType> getValues( boolean showInactives ) throws Exception 
    {
        Database db = Database.getInstance();
        
        try
        {
            return transactions.getPostingTypes( db, showInactives );
        }
        
        finally
        {
            db.release();
        }
    }
    
    @Override
    public List<String> isUnique( PostingType postingType ) throws Exception 
    {
        Database db = Database.getInstance();
        
        try
        {
            return transactions.isUnique( db, postingType );
        }
        
        finally
        {
            db.release();
        }
    }
    
    public boolean hasPostingCategories( PostingType postingType ) throws Exception
    {
        Database db = Database.getInstance();
        
        try
        {
            return transactions.hasPostingCategories( db, postingType );
        }
        
        finally
        {
            db.release();
        }
    }
}
