package com.pa.helpfin.model.db.service;

import com.pa.helpfin.model.data.Entity;
import com.pa.helpfin.model.data.EntityFilter;
import com.pa.helpfin.model.db.Database;
import com.pa.helpfin.model.db.transactions.EntityManagerTransactions;
import java.util.List;

/**
 * @author artur
 */
public class EntityManagerService 
    implements 
        Manager<Entity>
{  
    private static EntityManagerTransactions transactions;
    private static EntityManagerService service;
    
    public static EntityManagerService getInstance()
    {
        if( service == null )
        {
            service = new EntityManagerService();
        }
        
        return  service;
    }
    
    private EntityManagerService()
    {
        transactions = new EntityManagerTransactions();
    }
    
    @Override
    public void addValue( Entity entity ) throws Exception 
    {
        Database db = Database.getInstance();
        
        try
        {
            transactions.addEntity( db, entity);
        }
        
        finally
        {
            db.release();
        }
            
    }

    @Override
    public void deleteValue( Entity entity ) throws Exception 
    {
        Database db = Database.getInstance();
        
        try
        {
            transactions.deleteEntity( db, entity );
        }
        
        finally
        {
            db.release();
        }
    }

    @Override
    public void updateValue( Entity entity ) throws Exception 
    {
        Database db = Database.getInstance();
        
        try
        {
            transactions.updateEntity( db, entity );
        }
        
        finally
        {
            db.release();
        }
    }

    @Override
    public Entity getValue( int id ) throws Exception 
    {
        Database db = Database.getInstance();
        
        try
        {
            return  transactions.getEntity( db, id );
        }
        
        finally
        {
            db.release();
        }
    }

    @Override
    public Entity getValue( String name ) throws Exception 
    {
        Database db = Database.getInstance();
        
        try
        {
            return  transactions.getEntity( db, name );
        }
        
        finally
        {
            db.release();
        }
    }

    @Override
    public List<Entity> getValues() throws Exception 
    {
        Database db = Database.getInstance();
        
        try
        {
            return  transactions.getEntities( db, false );
        }
        
        finally
        {
            db.release();
        }
    }

    @Override
    public List<Entity> getValues( boolean showInactives ) throws Exception
    {
        Database db = Database.getInstance();
        
        try
        {
            return  transactions.getEntities( db, showInactives );
        }
        
        finally
        {
            db.release();
        }
    }

    public List<Entity> getValues( EntityFilter filter ) throws Exception
    {
        Database db = Database.getInstance();
        
        try
        {
            return  transactions.getEntities( db, filter );
        }
        
        finally
        {
            db.release();
        }
    }

    @Override
    public List<String> isUnique( Entity entity ) throws Exception 
    {
        Database db = Database.getInstance();
        
        try
        {
            return  transactions.isUnique( db, entity );
        }
        
        finally
        {
            db.release();
        }
    }
}