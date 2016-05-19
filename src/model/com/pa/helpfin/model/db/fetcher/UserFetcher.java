package com.pa.helpfin.model.db.fetcher;

import com.pa.helpfin.model.data.User;
import java.sql.ResultSet;

/**
 * @author artur
 */
public class UserFetcher
    implements 
        Fetcher<User>
{
    @Override
    public User fetch( ResultSet resultSet ) throws Exception 
    {
        int i = 1;
        
        User user = new User();
        
        user.setId( resultSet.getInt( i++ ) );
        user.setName( resultSet.getString( i++ ) );
        user.setPhone( resultSet.getString( i++ ) );
        user.setCpf( resultSet.getString( i++ ) );
        user.setBirthDate( resultSet.getDate( i++ ) );
        user.setRg( resultSet.getString( i++ ) );
        user.setMail( resultSet.getString( i++ ) );
        user.setGender( resultSet.getInt( i++ ) );
        user.setState( resultSet.getInt( i++ ) );
        user.setPassword( resultSet.getString( i++ ) );
        user.setLogin( resultSet.getString( i++ ) );
        user.setRole( resultSet.getInt( i++ ) );
        
        return  user;
    }
}
