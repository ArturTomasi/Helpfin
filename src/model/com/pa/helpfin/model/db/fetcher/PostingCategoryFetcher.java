package com.pa.helpfin.model.db.fetcher;

import com.pa.helpfin.model.data.PostingCategory;
import java.sql.ResultSet;

/**
 * @author artur
 */
public class PostingCategoryFetcher 
    implements 
        Fetcher<PostingCategory>
{
    @Override
    public PostingCategory fetch( ResultSet resultSet ) throws Exception
    {
        int i = 0;
        
        PostingCategory postingCategory = new PostingCategory();
        postingCategory.setId( resultSet.getInt( ++i ) );
        postingCategory.setName( resultSet.getString( ++i ) );
        postingCategory.setInfo( resultSet.getString( ++i ) );
        postingCategory.setState( resultSet.getInt( ++i ) );
        postingCategory.setPostingType( resultSet.getInt( ++i ) );
        
        return postingCategory;
    }
    
}
