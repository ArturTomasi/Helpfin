package com.pa.helpfin.model.db.service;

import com.pa.helpfin.model.data.CompletionType;
import com.pa.helpfin.model.data.CompletionTypeFilter;
import com.pa.helpfin.model.db.Database;
import com.pa.helpfin.model.db.transactions.CompletionTypeManagarTransactions;
import java.util.Collections;
import java.util.List;

/**
 * @author artur
 */
public class CompletionTypeManagerService 
    implements 
        Manager<CompletionType>
{
    private static CompletionTypeManagarTransactions transactions;
    private static CompletionTypeManagerService service;
    
    public static CompletionTypeManagerService getInstance()
    {
        if( service == null )
        {
            service = new CompletionTypeManagerService();
        }
        
        return  service;
    }
    
    private CompletionTypeManagerService()
    {
        transactions = new CompletionTypeManagarTransactions();
    }
    
    
    @Override
    public void addValue( CompletionType value ) throws Exception 
    {
        Database db = Database.getInstance();
        
        try
        {
            transactions.addCompletionType( db, value );
        }
        
        finally
        {
            db.release();
        }
    }

    @Override
    public void deleteValue( CompletionType value ) throws Exception 
    {
        Database db = Database.getInstance();
        
        try
        {
            transactions.deleteCompletionType( db, value );
        }
        
        finally
        {
            db.release();
        }
    }

    @Override
    public void updateValue( CompletionType value ) throws Exception 
    {
        Database db = Database.getInstance();
        
        try
        {
            transactions.updateCompletionType( db, value );
        }
        
        finally
        {
            db.release();
        }
    }

    @Override
    public CompletionType getValue( int id ) throws Exception
    {
        Database db = Database.getInstance();
        
        try
        {
            return transactions.getCompletionType( db, id );
        }
        
        finally
        {
            db.release();
        }
    }

    @Override
    public CompletionType getValue( String value ) throws Exception
    {
         Database db = Database.getInstance();
        
        try
        {
            return transactions.getCompletionType( db, value );
        }
        
        finally
        {
            db.release();
        }
    }

    @Override
    public List<CompletionType> getValues() throws Exception 
    {
         Database db = Database.getInstance();
        
        try
        {
            return transactions.getCompletionTypes( db, false );
        }
        
        finally
        {
            db.release();
        }
    }

    public List<CompletionType> getValues( CompletionTypeFilter filter ) throws Exception 
    {
         Database db = Database.getInstance();
        
        try
        {
            return transactions.getCompletionTypes( db, filter );
        }
        
        finally
        {
            db.release();
        }
    }

    @Override
    public List<CompletionType> getValues( boolean showInactives ) throws Exception 
    {
        Database db = Database.getInstance();
        
        try
        {
            return transactions.getCompletionTypes( db, showInactives );
        }
        
        finally
        {
            db.release();
        }
    }

    @Override
    public List<String> isUnique( CompletionType value ) throws Exception 
    {
        return Collections.EMPTY_LIST;
    }
    
}
