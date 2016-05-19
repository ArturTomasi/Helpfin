package com.pa.helpfin.model.db.transactions;

import com.pa.helpfin.model.data.CompletionType;
import com.pa.helpfin.model.data.CompletionTypeFilter;
import com.pa.helpfin.model.data.Option;
import com.pa.helpfin.model.db.Database;
import com.pa.helpfin.model.db.Schema.CompletionTypes;
import java.util.List;

/**
 * @author artur
 */
public class CompletionTypeManagarTransactions 
{
    public void addCompletionType( Database db, CompletionType completionType ) throws Exception
    {
        CompletionTypes T = CompletionTypes.table;
        
        String sql = " insert into " + T.name +
                     " ( " +
                     T.columns.NAME             + ", " +
                     T.columns.INFO             + ", " +
                     T.columns.STATE            + ", " +
                     T.columns.TYPE             + 
                     " ) values ( "             +
                     db.quote( completionType.getName() )   + ", " +
                     db.quote( completionType.getInfo() )   + ", " +
                     completionType.getState()              + ", " +
                     completionType.getType()               + 
                     " ) ";
        
        db.executeCommand( sql );
    }
    
    public void updateCompletionType( Database db, CompletionType completionType ) throws Exception
    {
        CompletionTypes T = CompletionTypes.table;
        
        String sql = " update " + T.name + " set " +
                     T.columns.NAME                + " = " + db.quote( completionType.getName() )   + ", " +
                     T.columns.INFO                + " = " + db.quote( completionType.getInfo() )   + ", " +
                     T.columns.STATE               + " = " + completionType.getState()              + ", " +
                     T.columns.TYPE                + " = " + completionType.getType()               +
                     " where  "                    +
                     T.columns.ID                  + " = " + completionType.getId();
        
        db.executeCommand( sql );
    }
    
    public void deleteCompletionType( Database db, CompletionType completionType ) throws Exception
    {
        CompletionTypes T = CompletionTypes.table;
        
        String sql = " update " + T.name + " set " +
                     T.columns.STATE               + " = " + CompletionType.STATE_INACTIVE + 
                     " where  "                    +
                     T.columns.ID                  + " = " + completionType.getId();
        
        db.executeCommand( sql );
    }
    
    public CompletionType getCompletionType( Database db, int completionTypeId ) throws Exception
    {
        CompletionTypes T = CompletionTypes.table;
        
        String sql = T.select  + 
                     " where " +
                     T.columns.STATE + " = " + CompletionType.STATE_ACTIVE + 
                     " and "   +
                     T.columns.ID    + " = " + completionTypeId;
        
        return db.fetchOne( sql, T.fetcher );
    }
    
    public CompletionType getCompletionType( Database db, String completionTypeName ) throws Exception
    {
        CompletionTypes T = CompletionTypes.table;
        
        String sql = T.select  + 
                     " where " +
                     T.columns.STATE + " = " + CompletionType.STATE_ACTIVE + 
                     " and "   +
                     db.upper( T.columns.ID ) + " like " + db.upper( db.quote( completionTypeName ) );
        
        return db.fetchOne( sql, T.fetcher );
    }
    
    public List<CompletionType> getCompletionTypes( Database db, boolean showInactives ) throws Exception
    {
        CompletionTypes T = CompletionTypes.table;
        
        String sql = T.select;
        
        if( ! showInactives )
        {
            sql +=  " where " +
                     T.columns.STATE + " = " + CompletionType.STATE_ACTIVE;
        }
        
        sql += " order by " + T.columns.NAME;
        
        return db.fetchAll( sql, T.fetcher );
    }
    
    
    public List<CompletionType> getCompletionTypes( Database db, CompletionTypeFilter filter ) throws Exception
    {
        CompletionTypes T = CompletionTypes.table;
        
        StringBuilder sb = new StringBuilder();
        
        sb.append( T.select ).append( " where true " );
        
        filter.getConditions().forEach( ( key, values ) ->
        {
            String condition = " and ( ";
            
            for( int i = 0; i < values.size(); i++ )
            {
                switch ( key )
                {
                    case CompletionTypeFilter.INFO:
                    {
                        condition += T.columns.INFO + " ilike " + db.quote( "%" + values.get( i ) + "%" );
                    }
                    break;
                   
                    case CompletionTypeFilter.NAME:
                    {
                        condition += T.columns.NAME + " ilike " + db.quote( "%" + values.get( i ) + "%" );
                    }
                    break;
                        
                    case CompletionTypeFilter.TYPE:
                    {
                        if( values.get( i ) instanceof Option )
                        {
                            Option option = (Option) values.get( i );
                            
                            condition += T.columns.TYPE + " = " + option.getId();
                        }
                    }
                    break;
                        
                    case CompletionTypeFilter.STATE:
                    {
                        if( values.get( i ) instanceof Option )
                        {
                            Option option = (Option) values.get( i );
                            
                            condition += T.columns.STATE + " = " + option.getId();
                        }
                    }
                    break;
                }
                
                condition += i == values.size() - 1 ? " ) " : " or ";
            }
            
            sb.append( condition );
            
        } );
        
        sb.append( " order by " ).append( T.columns.NAME );
        
        return db.fetchAll( sb.toString(), T.fetcher );
    }
}
