package com.pa.helpfin.model.db.transactions;

import com.pa.helpfin.model.data.Option;
import com.pa.helpfin.model.data.User;
import com.pa.helpfin.model.data.UserFilter;
import com.pa.helpfin.model.db.Database;
import com.pa.helpfin.model.db.Schema.Users.Columns;
import com.pa.helpfin.model.db.Schema.Users;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * @author artur
 */
public class UserManagerTransactions 
{
    public void addUser( Database db, User user ) throws Exception
    {
        if( user == null )
        {
            throw new Exception( "User cannot be null" );
        }
        
        else if ( user.getId() != 0 )
        {
            throw new Exception( "Invalid user id" );
        }
        
        Users U = Users.table;
        
        String sql = "insert into " + U.name + 
                     "( " +
                        U.columns.NAME         + "," +
                        U.columns.PHONE        + "," +
                        U.columns.CPF          + "," +
                        U.columns.BIRTH_DATE   + "," +
                        U.columns.RG           + "," +
                        U.columns.MAIL         + "," +
                        U.columns.GENDER       + "," +
                        U.columns.STATE        + "," +
                        U.columns.PASSWORD     + "," +
                        U.columns.ROLE         + "," +
                        U.columns.LOGIN +
                     ") values ( " +
                        db.quote( user.getName() )       + "," +
                        db.quote( user.getPhone() )      + "," +
                        db.quote( user.getCpf() )        + "," +
                        db.quote( user.getBirthDate() )  + "," +
                        db.quote( user.getRg() )         + "," +
                        db.quote( user.getMail() )       + "," +
                        user.getGender()                 + "," +
                        user.getState()                  + "," +
                        db.quote( user.getPassword() )   + "," +
                        user.getRole()                   + "," +
                        db.quote( user.getLogin() )      +  
                     ") ";
        
        db.executeCommand( sql );
    }
    
    public void deleteUser( Database db, User user ) throws Exception
    {
        if( user == null )
        {
            throw new Exception( "User cannot be null" );
        }
        
        Users U = Users.table;
        
        String sql = " update " + U.name + " set " +
                        U.columns.STATE + " = " + User.STATE_INACTIVE +
                     " where "  +
                        U.columns.ID    + " = " + user.getId();
        
        db.executeCommand( sql );
    }
    
    public void updateUser( Database db, User user ) throws Exception
    {
        if( user == null )
        {
            throw new Exception( "User cannot be null" );
        }
        
        Users U = Users.table;
        
        String sql = " update " + U.name       + " set " +
                        U.columns.NAME         + " = " + db.quote( user.getName() )      + ", " +
                        U.columns.PHONE        + " = " + db.quote( user.getPhone() )     + ", " +
                        U.columns.CPF          + " = " + db.quote( user.getCpf() )       + ", " +
                        U.columns.BIRTH_DATE   + " = " + db.quote( user.getBirthDate() ) + ", " +
                        U.columns.RG           + " = " + db.quote( user.getRg() )        + ", " +
                        U.columns.MAIL         + " = " + db.quote( user.getMail() )      + ", " +
                        U.columns.GENDER       + " = " + user.getGender()                + ", " +
                        U.columns.STATE        + " = " + user.getState()                 + ", " +
                        U.columns.PASSWORD     + " = " + db.quote( user.getPassword() )  + ", " +
                        U.columns.ROLE         + " = " + user.getRole()                  + ", " +
                        U.columns.LOGIN        + " = " + db.quote( user.getLogin() )     +
                     " where " + 
                        U.columns.ID           + " = " + user.getId();
        
        db.executeCommand( sql );
    }
    
    public User getUser( Database db, int userId ) throws Exception
    {
        Users U = Users.table;
        
        String sql = U.select  + 
                     " where " + 
                     U.columns.ID    + " = " + userId +
                     " and "   +
                     U.columns.STATE + " = " + User.STATE_ACTIVE;
        
        return db.fetchOne( sql , U.fetcher );
    }
    
    public List<User> getUsers( Database db, boolean showInactives ) throws Exception
    {
        Users U = Users.table;
        
        String sql = U.select;
        
        if( ! showInactives )
        {
            sql += " where " + U.columns.STATE + " = " + User.STATE_ACTIVE;
        }
         
        sql += " order by " + U.columns.NAME;
        
        return db.fetchAll( sql , U.fetcher );
    }
    
    public List<User> getUsers( Database db, UserFilter filter ) throws Exception
    {
        Users U = Users.table;
        
        StringBuilder sb = new StringBuilder();
        
        sb.append( U.select )
          .append( " where true " );
        
        filter.getConditions().forEach( ( key, values ) ->
        {
            String condition = " and ( ";
            
            for( int i = 0; i < values.size(); i++ )
            {
                switch ( key )
                {
                    case UserFilter.BIRTH_DATE:
                    {
                        if( values.get( i ) instanceof Date[] )
                        {
                            Date[] dates = (Date[]) values.get( i );
                            
                            condition += U.columns.BIRTH_DATE+ 
                                         " between " + 
                                         db.quote( dates[0] ) +
                                         " and " +
                                         db.quote( dates[1] );
                        }
                    }
                    break;
                    
                    case UserFilter.CPF:
                    {
                        condition += U.columns.CPF + " ilike " + db.quote( "%" + values.get( i ) + "%" );
                    }
                    break;
                    
                    case UserFilter.GENDER:
                    {
                        if( values.get( i ) instanceof Option )
                        {
                            Option option = (Option) values.get( i );
                            
                            condition += U.columns.GENDER + " = " + option.getId();
                        }
                    }
                    break;
                    
                    case UserFilter.MAIL:
                    {
                        condition += U.columns.MAIL + " ilike " + db.quote( "%" + values.get( i ) + "%" );
                    }
                    break;
                    
                    case UserFilter.NAME:
                    {
                        condition += U.columns.NAME + " ilike " + db.quote( "%" + values.get( i ) + "%" );
                    }
                    break;
                    
                    case UserFilter.PHONE:
                    {
                        condition += U.columns.PHONE + " ilike " + db.quote( "%" + values.get( i ) + "%" );
                    }
                    break;
                    
                    case UserFilter.RG:
                    {
                        condition += U.columns.RG + " ilike " + db.quote( "%" + values.get( i ) + "%" );
                    }
                    break;
                    
                    case UserFilter.ROLE:
                    {
                        if( values.get( i ) instanceof Option )
                        {
                            Option option = (Option) values.get( i );
                            
                            condition += U.columns.ROLE + " = " + option.getId();
                        }
                    }
                    break;
                    
                    case UserFilter.STATE:
                    {
                        if( values.get( i ) instanceof Option )
                        {
                            Option option = (Option) values.get( i );
                            
                            condition += U.columns.STATE + " = " + option.getId();
                        }
                    }
                    break;
                }
                
                condition += i == values.size() - 1 ? " ) " : " or ";
            }
            
            sb.append( condition );
            
        } );
        
        sb.append( " order by " ).append( U.columns.NAME );
        
        return  db.fetchAll( sb.toString(), U.fetcher );
    }
    
    public User getUserByLogin( Database db, String login, String password ) throws Exception
    {
        Users U = Users.table;
        
        String sql = U.select  + 
                     " where " +
                     U.columns.LOGIN    + " = " + db.quote( login ) +
                     " and "   +
                     U.columns.PASSWORD + " = " + db.quote( password ) +
                     " and "   +
                     U.columns.STATE    + " = " + User.STATE_ACTIVE;
        
        return db.fetchOne( sql, U.fetcher );
    }
    
    public User getUserByName( Database db, String name ) throws Exception
    {
        Users U = Users.table;
        
        String sql = U.select  + 
                     " where " +
                     U.columns.NAME  + " = " + db.quote( name ) +
                     " and "   +
                     U.columns.STATE + " = " + User.STATE_ACTIVE;
        
        return db.fetchOne( sql, U.fetcher );
    }
    
    public List<String> isUnique( Database db, User user ) throws Exception
    {
        List<String> notUnique = new ArrayList();
        
        Columns C = Users.table.columns;
        
        if( ! isUnique( db, user, user.getName(), C.NAME ) )
        {
            notUnique.add( "Nome: " + user.getName() );
        }
        
        if( ! isUnique( db, user, user.getCpf(), C.CPF ) )
        {
            notUnique.add( "Cpf: " + user.getCpf() );
        }
        
        if( ! isUnique( db, user, user.getRg(), C.CPF ) )
        {
            notUnique.add( "Rg: " + user.getRg() );
        }
        
        if( ! isUnique( db, user, user.getMail(), C.MAIL ) )
        {
            notUnique.add( "Email: " + user.getMail() );
        }
        
        if( ! isUnique( db, user, user.getLogin(), C.LOGIN ) )
        {
            notUnique.add( "Login: " + user.getLogin() );
        }
        
        if ( ! notUnique.isEmpty() )
        {
            notUnique.add( 0 , "Não são únicos:" );
        }
        
        return notUnique;
    }
    
    private boolean isUnique( Database db, User user, String value, String column ) throws Exception
    {
        Users U = Users.table;
        
        String sql = " select count( * ) from " + 
                     U.name + 
                     " where " +
                     db.upper( column ) + " = " + db.upper( db.quote( value ) ) +
                     " and "   +
                     U.columns.ID       + " <> " + user.getId();
        
        return db.queryInteger( sql ) == 0;
    }
}
