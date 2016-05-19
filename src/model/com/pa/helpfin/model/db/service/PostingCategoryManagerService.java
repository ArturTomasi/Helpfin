package com.pa.helpfin.model.db.service;

import com.pa.helpfin.model.data.PostingCategory;
import com.pa.helpfin.model.data.PostingCategoryFilter;
import com.pa.helpfin.model.db.Database;
import com.pa.helpfin.model.db.transactions.PostingCategoryManagerTransactions;
import java.util.List;

/**
 * @author artur
 */
public class PostingCategoryManagerService 
    implements 
        Manager<PostingCategory>
{
    private static PostingCategoryManagerTransactions transactions;
    private static PostingCategoryManagerService service;
    
    public static PostingCategoryManagerService getInstance()
    {
        if( service == null )
        {
            service = new PostingCategoryManagerService();
        }
        
        return  service;
    }
    
    private PostingCategoryManagerService()
    {
        transactions = new PostingCategoryManagerTransactions();
    }
    

    @Override
    public void addValue( PostingCategory postingCategory ) throws Exception 
    {
        Database db = Database.getInstance();
        
        try
        {
            transactions.addPostingCategory( db, postingCategory );
        }
        
        finally
        {
            db.release();
        }
    }

    @Override
    public void updateValue( PostingCategory postingCategory ) throws Exception 
    {
        Database db = Database.getInstance();
        
        try
        {
            transactions.updatePostingCategory( db, postingCategory );
        }
        
        finally
        {
            db.release();
        }
    }

    @Override
    public void deleteValue( PostingCategory postingCategory ) throws Exception 
    {
        Database db = Database.getInstance();
        
        try
        {
            transactions.deletePostingCategory( db, postingCategory );
        }
        
        finally
        {
            db.release();
        }
    }

    @Override
    public PostingCategory getValue( int postingCategoryId ) throws Exception 
    {
        Database db = Database.getInstance();
        
        try
        {
            return transactions.getPostingCategory( db, postingCategoryId );
        }
        
        finally
        {
            db.release();
        }
    }

    @Override
    public PostingCategory getValue( String value ) throws Exception
    {
        Database db = Database.getInstance();
        
        try
        {
            return transactions.getPostingCategory( db, value );
        }
        
        finally
        {
            db.release();
        }
    }

    @Override
    public List<PostingCategory> getValues() throws Exception 
    {
        Database db = Database.getInstance();
        
        try
        {
            return transactions.getPostingCategories( db, false );
        }
        
        finally
        {
            db.release();
        }
    }

    @Override
    public List<PostingCategory> getValues( boolean showInactives ) throws Exception 
    {
        Database db = Database.getInstance();
        
        try
        {
            return transactions.getPostingCategories( db, showInactives );
        }
        
        finally
        {
            db.release();
        }
    }
    
    public List<PostingCategory> getValues( PostingCategoryFilter filter ) throws Exception 
    {
        Database db = Database.getInstance();
        
        try
        {
            return transactions.getPostingCategories( db, filter );
        }
        
        finally
        {
            db.release();
        }
    }
    
    public List<PostingCategory> getValues( int type ) throws Exception 
    {
        Database db = Database.getInstance();
        
        try
        {
            return transactions.getPostingCategories( db, type );
        }
        
        finally
        {
            db.release();
        }
    }

    @Override
    public List<String> isUnique( PostingCategory postingCategory ) throws Exception 
    {
        Database db = Database.getInstance();
        
        try
        {
            return transactions.isUnique( db, postingCategory );
        }
        
        finally
        {
            db.release();
        }
    }

    public boolean isValidReferences( PostingCategory postingCategory ) throws Exception 
    {
        Database db = Database.getInstance();
        
        try
        {
            return transactions.isValidReferences( db, postingCategory );
        }
        
        finally
        {
            db.release();
        }
    }
 
    public String getDrilldown( int type ) throws Exception 
    {
        Database db = Database.getInstance();
        
        try
        {
            return transactions.getDrilldown( db, type );
        }
        
        finally
        {
            db.release();
        }
    }
}