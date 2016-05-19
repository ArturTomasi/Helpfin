package com.pa.helpfin.model.db.fetcher;

import com.pa.helpfin.model.data.Entity;
import java.sql.ResultSet;

/**
 * @author artur
 */
public class EntityFetcher 
    implements 
        Fetcher<Entity>
{
    @Override
    public Entity fetch( ResultSet resultSet ) throws Exception 
    {
        int i = 0;
        
        Entity entity = new Entity();
        
        entity.setId( resultSet.getInt( ++i ) );
        entity.setName( resultSet.getString( ++i ) );
        entity.setCompannyName( resultSet.getString( ++i ) );
        entity.setCnpj( resultSet.getString( ++i ) );
        entity.setPhone( resultSet.getString( ++i ) );
        entity.setMail( resultSet.getString( ++i ) );
        entity.setState( resultSet.getInt( ++i ) );
        
        return entity;
    }
}
