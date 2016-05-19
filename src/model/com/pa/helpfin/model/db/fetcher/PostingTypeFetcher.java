package com.pa.helpfin.model.db.fetcher;

import com.pa.helpfin.model.data.PostingType;
import java.sql.ResultSet;

/**
 * @author artur
 */
public class PostingTypeFetcher
    implements 
        Fetcher<PostingType>
{

    @Override
    public PostingType fetch( ResultSet resultSet ) throws Exception 
    {
        int i = 0;
        
        PostingType postingType = new PostingType();
        postingType.setId( resultSet.getInt( ++i ) );
        postingType.setName( resultSet.getString( ++i ) );
        postingType.setState( resultSet.getInt( ++i ) );
        postingType.setInfo( resultSet.getString( ++i ) );
        
        return postingType;
    }
}
