package com.pa.helpfin.model.db.fetcher;

import com.pa.helpfin.model.data.Attachment;
import java.sql.ResultSet;

/**
 * @author artur
 */
public class AttachmentFetcher 
    implements 
        Fetcher<Attachment>
{
    @Override
    public Attachment fetch( ResultSet resultSet ) throws Exception 
    {
        int i = 0;
        
        Attachment attachment = new Attachment();
        attachment.setId(   resultSet.getInt( ++i ) );
        attachment.setName( resultSet.getString( ++i ) );
        attachment.setInfo( resultSet.getString( ++i ) );
        attachment.setUrl(  resultSet.getString( ++i ) );
        attachment.setPosting( resultSet.getInt( ++i ) );
        
        return attachment;
    }
}
