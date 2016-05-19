package com.pa.helpfin.model.db.fetcher;

import com.pa.helpfin.model.data.CompletionType;
import java.sql.ResultSet;

/**
 * @author artur
 */
public class CompletionTypeFetcher
    implements 
        Fetcher<CompletionType>
{
    @Override
    public CompletionType fetch( ResultSet resultSet ) throws Exception 
    {
        int i = 0;
        
        CompletionType completionType = new CompletionType();
        
        completionType.setId( resultSet.getInt( ++i ) );
        completionType.setName( resultSet.getString( ++i ) );
        completionType.setInfo( resultSet.getString( ++i ) );
        completionType.setType( resultSet.getInt( ++i ) );
        completionType.setState( resultSet.getInt( ++i ) );
        
        return completionType;
    }   
}
