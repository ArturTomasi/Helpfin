package com.pa.helpfin.model.db.transactions;

import com.pa.helpfin.model.data.Attachment;
import com.pa.helpfin.model.data.Posting;
import com.pa.helpfin.model.db.Database;
import com.pa.helpfin.model.db.Schema.Attachments;
import com.pa.helpfin.view.util.FileUtilities;
import java.util.List;

/**
 * @author artur
 */
public class AttachmentManagerTransactions 
{
    public void addAttachment( Database db,  Attachment attachment ) throws Exception
    {
        Attachments A = Attachments.table;
        
        String sql = " insert into " + A.name +
                     "( " +
                     A.columns.NAME + ", " +
                     A.columns.INFO + ", " +
                     A.columns.URL  + ", " +
                     A.columns.REF_POSTING + 
                     " ) values ( " +
                     db.quote( attachment.getName() ) + ", " +
                     db.quote( attachment.getInfo() ) + ", " +
                     db.quote( attachment.getUrl()  ) + ", " +
                     attachment.getPosting()          +
                     " ) ";
        
        db.executeCommand( sql );
    }
    
    

    public void updateAttachment( Database db,  Attachment attachment ) throws Exception
    {
        Attachments A = Attachments.table;
        
        String sql = " update " + A.name    + " set " +
                     A.columns.NAME         + " = " + db.quote( attachment.getName() ) + ", " +
                     A.columns.INFO         + " = " + db.quote( attachment.getInfo() ) + ", " +
                     A.columns.URL          + " = " + db.quote( attachment.getUrl()  ) + ", " +
                     A.columns.REF_POSTING  + " = " + attachment.getPosting()          + 
                     " where " +
                     A.columns.ID   + " = " + attachment.getId();
        
        db.executeCommand( sql );
    }
    
    
    public void deleteAttachment( Database db,  Attachment attachment ) throws Exception
    {
        Attachments A = Attachments.table;
        
        String sql = " delete from " + A.name +
                     " where " +
                     A.columns.ID   + " = " + attachment.getId();
        
        db.executeCommand( sql );
        
        FileUtilities.deleteFile( attachment.getUrl() );
    }
    
    

    public Attachment getAttachment( Database db,  int attachmentId ) throws Exception
    {
        Attachments A = Attachments.table;
        
        String sql = A.select + 
                     " where " +
                     A.columns.ID   + " = " + attachmentId;
        
        return db.fetchOne( sql, A.fetcher );
    }

    
    public Attachment getAttachment( Database db, String name ) throws Exception
    {
        Attachments A = Attachments.table;
       
        String sql = A.select + 
                     " where " +
                     db.upper( A.columns.NAME  ) + " like " + db.upper( db.quote( name ) ) +
                     " or " +
                     db.upper( A.columns.INFO  ) + " like " + db.upper( db.quote( name ) );
       
        return db.fetchOne( sql, A.fetcher );
    }

    
    
    public List<Attachment> getAttachments( Database db ) throws Exception
    {
        Attachments A = Attachments.table;
       
        return db.fetchAll( A.select, A.fetcher );
    }

    
    
    public List<Attachment> getAttachments( Database db, Posting posting ) throws Exception
    {
        Attachments A = Attachments.table;
        
        String sql = A.select  +
                     " where " + 
                     A.columns.REF_POSTING + " = " + posting.getId();
        
        return db.fetchAll( sql, A.fetcher );
    }
    
    
    public List<String> isUnique( Posting posting )
    {
        return null;
    }
}
