package com.pa.helpfin.model.db.fetcher;

import com.pa.helpfin.model.data.Posting;
import java.sql.ResultSet;

/**
 * @author artur
 */
public class PostingFetcher 
    implements 
        Fetcher<Posting>
{
    @Override
    public Posting fetch( ResultSet resultSet ) throws Exception 
    {
        int i = 0;
        
        Posting postings = new Posting();
        postings.setId( resultSet.getInt( ++i ) );
        postings.setName( resultSet.getString( ++i ) );
        postings.setInfo( resultSet.getString( ++i ) );
        postings.setRealDate( resultSet.getDate( ++i ) );
        postings.setEstimateDate( resultSet.getDate( ++i ) );
        postings.setCompletionAuto(resultSet.getInt( ++i ) == Posting.FLAG_TRUE );
        postings.setRealValue( resultSet.getDouble( ++i ) );
        postings.setEstimateValue( resultSet.getDouble( ++i ) );
        postings.setPortion( resultSet.getInt( ++i ) );
        postings.setPortionTotal( resultSet.getInt( ++i ) );
        postings.setRepeat(resultSet.getInt( ++i ) == Posting.FLAG_TRUE );
        postings.setState( resultSet.getInt( ++i ) );
        postings.setPostingCategory( resultSet.getInt( ++i ) );
        postings.setUser( resultSet.getInt( ++i ) );
        postings.setCompletionType( resultSet.getInt( ++i ) );
        postings.setEntity( resultSet.getInt( ++i ) );
        postings.setPosting( resultSet.getInt( ++i ) );
        
        return postings;
    }
}
