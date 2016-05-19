package com.pa.helpfin.model.db.service;

import com.pa.helpfin.model.data.Attachment;
import com.pa.helpfin.model.data.Posting;
import com.pa.helpfin.model.db.Database;
import com.pa.helpfin.model.db.transactions.AttachmentManagerTransactions;
import java.util.List;

/**
 * @author artur
 */
public class AttachmentManagerService
    implements 
        Manager<Attachment>
        
{
    private static AttachmentManagerTransactions transactions;
    private static AttachmentManagerService service;
    
    public static AttachmentManagerService getInstance()
    {
        if( service == null )
        {
            service = new AttachmentManagerService();
        }
        
        return  service;
    }
    
    private AttachmentManagerService()
    {
        transactions = new AttachmentManagerTransactions();
    }
    
    @Override
    public void addValue( Attachment value ) throws Exception 
    {
        Database db = Database.getInstance();
        
        try
        {
            transactions.addAttachment( db, value );
        }
        
        finally
        {
            db.release();
        }
    }

    @Override
    public void deleteValue( Attachment value ) throws Exception
    {
        Database db = Database.getInstance();
        
        try
        {
            transactions.deleteAttachment( db, value );
        }
        
        finally
        {
            db.release();
        }
    }

    @Override
    public void updateValue( Attachment value ) throws Exception
    {
        Database db = Database.getInstance();
        
        try
        {
            transactions.updateAttachment( db, value );
        }
        
        finally
        {
            db.release();
        }
    }

    @Override
    public Attachment getValue( int id ) throws Exception
    {
        Database db = Database.getInstance();
        
        try
        {
            return transactions.getAttachment( db, id );
        }
        
        finally
        {
            db.release();
        }
    }

    @Override
    public Attachment getValue( String value ) throws Exception 
    {
        Database db = Database.getInstance();
        
        try
        {
            return transactions.getAttachment( db, value );
        }
        
        finally
        {
            db.release();
        }
    }

    @Override
    public List<Attachment> getValues() throws Exception
    {
        Database db = Database.getInstance();
        
        try
        {
            return transactions.getAttachments( db );
        }
        
        finally
        {
            db.release();
        }
    }

    @Override
    public List<Attachment> getValues( boolean showInactives ) throws Exception 
    {
        Database db = Database.getInstance();
        
        try
        {
            return transactions.getAttachments( db );
        }
        
        finally
        {
            db.release();
        }
    }

    public List<Attachment> getValues( Posting posting ) throws Exception 
    {
        Database db = Database.getInstance();
        
        try
        {
            return transactions.getAttachments( db, posting );
        }
        
        finally
        {
            db.release();
        }
    }

    @Override
    public List<String> isUnique( Attachment value ) throws Exception 
    {
        return null;
    }
}
