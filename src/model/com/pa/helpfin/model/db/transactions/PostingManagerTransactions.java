package com.pa.helpfin.model.db.transactions;

import com.pa.helpfin.model.data.Attachment;
import com.pa.helpfin.model.data.CompletionType;
import com.pa.helpfin.model.data.DefaultFilter;
import com.pa.helpfin.model.data.Entity;
import com.pa.helpfin.model.data.Option;
import com.pa.helpfin.model.data.Posting;
import com.pa.helpfin.model.data.PostingCategory;
import com.pa.helpfin.model.data.PostingFilter;
import com.pa.helpfin.model.data.PostingType;
import com.pa.helpfin.model.data.User;
import com.pa.helpfin.model.db.Database;
import com.pa.helpfin.model.db.Schema.PostingCategories;
import com.pa.helpfin.model.db.Schema.PostingTypes;
import com.pa.helpfin.model.db.Schema.Postings;
import com.pa.helpfin.model.db.service.AttachmentManagerService;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author artur
 */
public class PostingManagerTransactions
{
    public void addPosting( Database db, Posting posting ) throws Exception
    {
        Postings P = Postings.table;
        
        posting.setId( db.nextId( P.name ) );
        
        String sql = " insert into " + P.name +
                     " ( " +
                     P.columns.ID                    + ", " +
                     P.columns.ESTIMATE_DATE         + ", " +
                     P.columns.ESTIMATE_VALUE        + ", " +
                     P.columns.FL_COMPLETION_AUTO    + ", " +
                     P.columns.FL_REPEAT             + ", " +
                     P.columns.INFO                  + ", " +
                     P.columns.NAME                  + ", " +
                     P.columns.PORTION               + ", " +
                     P.columns.PORTION_TOTAL         + ", " +
                     P.columns.REAL_DATE             + ", " +
                     P.columns.REAL_VALUE            + ", " +
                     P.columns.REF_COMPLETION_TYPE   + ", " +
                     P.columns.REF_ENTITY            + ", " +
                     P.columns.REF_POSTING           + ", " +
                     P.columns.REF_POSTING_CATEGORY  + ", " +
                     P.columns.REF_USER              + ", " +
                     P.columns.STATE                 +  
                    " ) values ( " +
                    posting.getId()                           + ", " +
                    db.quote( posting.getEstimateDate() )     + ", " +
                    posting.getEstimateValue()                + ", " +
                    db.flag( posting.isCompletionAuto() )     + ", " +
                    db.flag( posting.isRepeat() )             + ", " +
                    db.quote( posting.getInfo() )             + ", " +
                    db.quote( posting.getName() )             + ", " +
                    posting.getPortion()                      + ", " +
                    posting.getPortionTotal()                 + ", " +
                    db.quote( posting.getRealDate() )         + ", " +
                    posting.getRealValue()                    + ", " +
                    foreingKey( posting.getCompletionType() ) + ", " +
                    posting.getEntity()                       + ", " +
                    foreingKey( posting.getPosting() )        + ", " +
                    posting.getPostingCategory()              + ", " +
                    posting.getUser()                         + ", " +
                    posting.getState()                        +
                    " ) ";
        
        db.executeCommand( sql );
    }
    
    public void updatePosting( Database db, Posting posting ) throws Exception
    {
        Postings P = Postings.table;
        
        String sql = " update " + P.name + " set "   +
                     P.columns.ESTIMATE_DATE         + " = " + db.quote( posting.getEstimateDate() )      + ", " +
                     P.columns.ESTIMATE_VALUE        + " = " + posting.getEstimateValue()                 + ", " +
                     P.columns.FL_COMPLETION_AUTO    + " = " + db.flag( posting.isCompletionAuto() )      + ", " +
                     P.columns.FL_REPEAT             + " = " + db.flag( posting.isRepeat() )              + ", " +
                     P.columns.INFO                  + " = " + db.quote( posting.getInfo() )              + ", " +
                     P.columns.NAME                  + " = " + db.quote( posting.getName() )              + ", " +
                     P.columns.PORTION               + " = " + posting.getPortion()                       + ", " +
                     P.columns.PORTION_TOTAL         + " = " + posting.getPortionTotal()                  + ", " +
                     P.columns.REAL_DATE             + " = " + db.quote( posting.getRealDate() )          + ", " +
                     P.columns.REAL_VALUE            + " = " + posting.getRealValue()                     + ", " +
                     P.columns.REF_COMPLETION_TYPE   + " = " + foreingKey( posting.getCompletionType() )  + ", " +
                     P.columns.REF_ENTITY            + " = " + posting.getEntity()                        + ", " +
                     P.columns.REF_POSTING           + " = " + foreingKey( posting.getPosting()   )       + ", " +
                     P.columns.REF_POSTING_CATEGORY  + " = " + posting.getPostingCategory()               + ", " +
                     P.columns.REF_USER              + " = " + posting.getUser()                          + ", " +
                     P.columns.STATE                 + " = " + posting.getState()                         +
                    " where " +
                    P.columns.ID                     + " = " + posting.getId();
        
        db.executeCommand( sql );
    }
    
    public void deletePosting( Database db, Posting posting ) throws Exception
    {
        Postings P = Postings.table;
        
        String sql = " update " + P.name + " set "   +
                     P.columns.STATE + " = " + Posting.STATE_DELETED +
                     " where " +
                     P.columns.ID    + " = " + posting.getId();
        
        db.executeCommand( sql );
        
        AttachmentManagerService am = com.pa.helpfin.model.ModuleContext
                                                            .getInstance()
                                                            .getAttachmentManager();
        
        for( Attachment a : am.getValues( posting ) )
        {
            am.deleteValue( a );
        }
    }
    
    public void deleteNextPortions( Database db, Posting posting ) throws Exception
    {
        Postings P = Postings.table;
        
        String sql = P.select   +
                     " where "  +
                     P.columns.REF_POSTING + " = " + ( posting.getPortion() == 1 ? posting.getId() : posting.getPosting() ) +
                     " and "    +
                     P.columns.PORTION     + " > " + posting.getPortion();
        
        List<Posting> postings = db.fetchAll( sql, P.fetcher );
        
        for ( Posting p  : postings )
        {
            deletePosting( db, p );
        }
    }
    
    public Posting getPosting( Database db, int postingId ) throws Exception
    {
        Postings P = Postings.table;
        
        String sql = P.select +
                     " where " +
                     P.columns.STATE + " <> " + Posting.STATE_DELETED +
                     " and "  +
                     P.columns.ID     + " = " + postingId;
        
        return  db.fetchOne( sql, P.fetcher );
    }
    
    
    public Posting getPosting( Database db, String postingName ) throws Exception
    {
        Postings P = Postings.table;
        
        String sql = P.select +
                     " where " +
                     P.columns.STATE + " <> " + Posting.STATE_DELETED +
                     " and "  +
                     db.upper( P.columns.ID ) + " like " + db.upper( db.quote( postingName ) );
        
        return  db.fetchOne( sql, P.fetcher );
    }
    
    public List<Posting> getPostings( Database db, boolean showInactives ) throws Exception
    {
        Postings P = Postings.table;
        
        String sql = P.select;
        
        if( ! showInactives )
        {
            sql += " where " +
                    P.columns.STATE + " <> " + Posting.STATE_DELETED;
        }
        
        sql += " order by " + P.columns.NAME;
        
        return  db.fetchAll( sql, P.fetcher );
    }
    
    public List<Posting> getPostings( Database db, int category ) throws Exception
    {
        Postings P = Postings.table;
        
        String sql = P.select +
                     " where " +
                     P.columns.STATE + " <> " + Posting.STATE_DELETED +
                     " and " +
                     P.columns.REF_POSTING_CATEGORY + " = " + category +
                     " order by " + P.columns.NAME;
        
        return  db.fetchAll( sql, P.fetcher );
    }
    
    public List<Posting> getPostings( Database db, DefaultFilter filter ) throws Exception
    {
        Postings P          = Postings.alias( "P" );
        PostingTypes T      = PostingTypes.alias( "T" );
        PostingCategories C = PostingCategories.alias( "C" );
        
        StringBuilder sb = new StringBuilder();
        
        sb.append( P.select ).append( ", " ).append( T.name ).append( ", " ).append( C.name )
          .append( " where " )
          .append( P.columns.REF_POSTING_CATEGORY ).append( " = " ).append( C.columns.ID )
          .append( " and "  )
          .append( C.columns.REF_POSTING_TYPE     ).append( " = " ).append( T.columns.ID );
        
        filter.getConditions().forEach( ( key, values ) ->
        {
            String condition = " and ( ";
            
            for( int i = 0; i < values.size(); i++ )
            {
                switch ( key )
                {
                    case PostingFilter.NAME:
                    {
                        condition += P.columns.NAME + " ilike " + db.quote( "%" + values.get( i ) + "%" );
                    }
                    break;
                    
                    case PostingFilter.INFO:
                    {
                        condition += P.columns.INFO + " ilike " + db.quote( "%" + values.get( i ) + "%" );
                    }
                    break;
                    
                    case PostingFilter.REAL_DATE:
                    {
                        if( values.get( i ) instanceof Date[] )
                        {
                            Date[] dates = (Date[]) values.get( i );
                            
                            condition += P.columns.REAL_DATE + 
                                         " between " + 
                                         db.quote( dates[0] ) +
                                         " and " +
                                         db.quote( dates[1] );
                        }
                    }
                    break;
                    
                    case PostingFilter.ESTIMATE_DATE:
                    {
                        if( values.get( i ) instanceof Date[] )
                        {
                            Date[] dates = (Date[]) values.get( i );
                            
                            condition += P.columns.ESTIMATE_DATE + 
                                         " between " + 
                                         db.quote( dates[0] ) +
                                         " and " +
                                         db.quote( dates[1] );
                        }
                    }
                    break;
                    
                    case PostingFilter.ESTIMATE_VALUE:
                    {
                        if( values.get( i ) instanceof Double )
                        {
                            Double value = (Double) values.get( i );
                            
                            condition += P.columns.ESTIMATE_VALUE + " = " + value;
                        }
                    }
                    break;
                    
                    case PostingFilter.REAL_VALUE:
                    {
                        if( values.get( i ) instanceof Double )
                        {
                            Double value = (Double) values.get( i );
                            
                            condition += P.columns.REAL_VALUE + " = " + value;
                        }
                    }
                    break;
                    
                    case PostingFilter.PORTION:
                    {
                        condition += P.columns.PORTION + " = " + values.get( i );
                    }
                    break;
                    
                    case PostingFilter.PORTION_TOTAL:
                    {
                        condition += P.columns.PORTION_TOTAL + " = " + values.get( i );
                    }
                    break;
                    
                    case PostingFilter.COMPLETION_AUTO:
                    {
                        if( values.get( i ) instanceof Option )
                        {
                            Option value = (Option) values.get( i );
                            
                            condition += P.columns.FL_COMPLETION_AUTO + " = " + value.getId();
                        }
                    }
                    break;
                    
                    case PostingFilter.REPEAT:
                    {
                        if( values.get( i ) instanceof Option )
                        {
                            Option value = (Option) values.get( i );
                            
                            condition += P.columns.FL_REPEAT + " = " + value.getId();
                        }
                    }
                    break;
                    
                    case PostingFilter.POSTING_CATEGORY:
                    {
                        if( values.get( i ) instanceof PostingCategory )
                        {
                            PostingCategory category = (PostingCategory) values.get( i );
                            
                            condition += P.columns.REF_POSTING_CATEGORY + " = " + category.getId();
                        }
                    }
                    break;
                    
                    case PostingFilter.USER:
                    {
                        if( values.get( i ) instanceof User )
                        {
                            User user = (User) values.get( i );
                            
                            condition += P.columns.REF_USER + " = " + user.getId();
                        }
                    }
                    break;
                    
                    case PostingFilter.COMPLETION_TYPE:
                    {
                        if( values.get( i ) instanceof CompletionType )
                        {
                            CompletionType completionType = (CompletionType) values.get( i );
                            
                            condition += P.columns.REF_COMPLETION_TYPE + " = " + completionType.getId();
                        }
                    }
                    break;
                    
                    case PostingFilter.ENTITY:
                    {
                        if( values.get( i ) instanceof Entity )
                        {
                            Entity entity = (Entity) values.get( i );
                            
                            condition += P.columns.REF_ENTITY + " = " + entity.getId();
                        }
                    }
                    break;
                    
                    case PostingFilter.TYPE:
                    {
                        if( values.get( i ) instanceof PostingType )
                        {
                            PostingType type = (PostingType) values.get( i );
                            
                            condition += T.columns.ID + " = " + type.getId();
                        }
                    }
                    break;
                    
                    case PostingFilter.STATE:
                    {
                        if( values.get( i ) instanceof Option )
                        {
                            Option option = (Option) values.get( i );
                            
                            condition += P.columns.STATE + " = " + option.getId();
                        }
                    }
                    break;
                    
                    case PostingFilter.DEADLINE:
                    {
                        if( values.get( i ) instanceof Option )
                        {
                            Option option = (Option) values.get( i );
                            
                            if( option.getId() == Posting.FLAG_TRUE )
                            {
                                condition += P.columns.ESTIMATE_DATE + " >= " + P.columns.REAL_DATE +
                                             " or ( " +
                                             P.columns.REAL_DATE + " is null " + 
                                             " and " +
                                             P.columns.ESTIMATE_DATE + " >= " + db.quote( db.currentDate() ) + " )";
                            }
                            
                            else if ( option.getId() == Posting.FLAG_FALSE )
                            {
                                condition += P.columns.ESTIMATE_DATE + " < " + P.columns.REAL_DATE +
                                             " or ( " +
                                             P.columns.REAL_DATE + " is null " + 
                                             " and " +
                                             P.columns.ESTIMATE_DATE + " < " + db.quote( db.currentDate() ) + " )";
                            }
                        }
                    }
                    break;
                    
                    case PostingFilter.IN_BUDGET:
                    {
                        if( values.get( i ) instanceof Option )
                        {
                            Option option = (Option) values.get( i );
                            
                            if( option.getId() == Posting.FLAG_TRUE )
                            {
                                condition += P.columns.ESTIMATE_VALUE + " >= " + P.columns.REAL_VALUE +
                                             " or ( " +
                                             P.columns.STATE  + " <> " + Posting.STATE_FINISHED +
                                             " or " +
                                             P.columns.REAL_VALUE + " = " + 0 + " )";
                            }
                            
                            else if ( option.getId() == Posting.FLAG_FALSE )
                            {
                                condition += P.columns.ESTIMATE_VALUE + " < " + P.columns.REAL_VALUE +
                                             " and  " +
                                             P.columns.REAL_VALUE + " <> " + 0 ;
                            }
                        }
                    }
                    break;
                }
                
                condition += i == values.size() - 1 ? " ) " : " or ";
            }
            
            sb.append( condition );
            
        } );
        
        sb.append( " order by " ).append( P.columns.NAME );
        
        return  db.fetchAll( sb.toString(), P.fetcher );
    }
    
    
    
    public List<Posting> getMonthPostings( Database db, int type ) throws Exception
    {
        Postings P = Postings.table;
        PostingCategories C = PostingCategories.table;
        
        String sql = P.select  +
                     " where " +
                     P.columns.REF_POSTING_CATEGORY   + " = " + C.columns.ID +
                     " and "   +
                     P.columns.STATE                  + " = " + Posting.STATE_FINISHED +
                     " and "   +
                     C.columns.REF_POSTING_TYPE       + " = " + type +
                     " and "   +
                     P.columns.REAL_DATE              + " is not null "  +
                     " and "   +
                     "month( " +  P.columns.REAL_DATE + " ) = " + "month( current_date )" +
                     " order by " +
                     P.columns.REAL_DATE;
        
        return  db.fetchAll( sql, P.fetcher );
    }
    
    
    
    public List<Posting> getCurrentPostings( Database db, User user ) throws Exception
    {
        Postings P = Postings.table;
        
        String sql = P.select  +
                     " where " +
                     P.columns.STATE  + " = " + Posting.STATE_PROGRESS +
                     " and "   +
                     db.restrictions( P.columns.REF_USER ) +
                    " order by " +
                     P.columns.ESTIMATE_DATE;
        
        return  db.fetchAll( sql, P.fetcher );
    }

    
    public List<Posting> getNextPostings( Database db ) throws Exception
    {
        Postings P = Postings.table;
        
        String sql = P.select  +
                     " where " +
                     P.columns.STATE  + " = " + Posting.STATE_PROGRESS +
                     " or ( "  +
                     " date_part( 'month', " + P.columns.ESTIMATE_DATE + " ) " + 
                     " between " +  
                     " date_part( 'month', current_date ) - 1 " +
                     " and " + 
                     " date_part( 'month', current_date ) + 1 " +
                     " and " +
                     P.columns.STATE + " <> " + Posting.STATE_DELETED +
                     " ) order by " +
                     P.columns.ESTIMATE_DATE;
        
        return  db.fetchAll( sql, P.fetcher );
    }
    
    
    
    public Posting getLastPosting( Database db, Posting posting ) throws Exception
    {
        Postings P = Postings.table;
        
        String sql = P.select  +
                     " where " +
                     P.columns.STATE + " <> " + Posting.STATE_DELETED +
                     " and ( "   +
                        " case when " + 
                        P.columns.PORTION     + " = " + 2 + 
                        " then " +
                        P.columns.ID          + " = " + posting.getPosting() +
                        " else " +
                        P.columns.REF_POSTING + " = " + posting.getPosting() + 
                        " and " +
                        P.columns.PORTION     + " = " + ( posting.getPortion() - 1 ) + 
                        " end )";
        
        return  db.fetchOne( sql, P.fetcher );
    }
    
    
    
    public void updateNextPortion( Database db, Posting posting ) throws Exception
    {
        if( posting == null )
            throw new Exception( "Posting cannot be null" );
        
        int state = posting.getState() == Posting.STATE_FINISHED ?
                    Posting.STATE_PROGRESS :
                    Posting.STATE_REGISTRED;
        
        Postings P = Postings.table;
        
        String sql = "update " + P.name + " set " +
                     P.columns.STATE       + " = "  + state + ", " +
                     P.columns.REAL_VALUE  + " = "  + 0d    + ", " +
                     P.columns.REAL_DATE   + " = "  + " null "   +
                     " where " +
                     P.columns.REF_POSTING + " = "  + ( posting.getPortion() == 1 ? posting.getId() : posting.getPosting() ) +
                     " and "   +
                     P.columns.STATE       + " <> " + Posting.STATE_DELETED +
                     " and "   +
                     P.columns.PORTION     + " = "  + ( posting.getPortion() + 1 );
                     
        db.executeCommand( sql );
    }
    
    
    
    public Map postingsOfMonths( Database db, int type ) throws Exception
    {
        Postings P          = Postings.table;
        PostingCategories C = PostingCategories.table;
        
        String sql = "select " + 
                     "date_part( 'day', " + P.columns.REAL_DATE +" )" + ", sum ( " + P.columns.REAL_VALUE + " ) " +
                     " from "  + 
                     P.name    + 
                     " where " +
                     P.columns.REAL_DATE  + " is not null "  +
                     " and "   + 
                     P.columns.REAL_VALUE + " is not null " +
                     " and "   +
                     "date_part( 'month'," + P.columns.REAL_DATE + " ) =  date_part( 'month', current_date )" +
                     " and "   +
                     P.columns.REF_POSTING_CATEGORY + " in ( select " + 
                                                               C.columns.ID + " from " + C.name + 
                                                               " where " +
                                                               C.columns.REF_POSTING_TYPE + " = " + type + " ) " +
                    " and " +
                    P.columns.STATE + " <> " + Posting.STATE_DELETED + 
                    " group by 1";
        
        
        return db.queryMap( sql );
    }
        
    
    public List<Posting> getMonthlyBalance( Database db ) throws Exception
    {
        Postings P = Postings.table;
        
        String sql = P.select + 
                     " where " +
                     db.restrictions( P.columns.REF_USER ) +
                     " and " +
                     "( " +
                     "date_part( 'month'," + P.columns.REAL_DATE + " ) =  date_part( 'month', current_date )" +
                     " or " +
                     "date_part( 'month'," + P.columns.ESTIMATE_DATE + " ) =  date_part( 'month', current_date ) " +
                     " ) " +
                     " and " +
                     P.columns.STATE + " <> " + Posting.STATE_DELETED +
                     " order by " +
                     P.columns.ESTIMATE_DATE;
                
        
        return db.fetchAll( sql, P.fetcher );
    }
    
    
    
    public List<Posting> getCompletionPosting( Database db ) throws Exception
    {
        Postings P = Postings.table;
        
        String sql = P.select +
                     " where "  +
                     P.columns.ID + " in ( " +
                                " select "   + 
                                P.columns.ID + 
                                " from "     +
                                P.name       +
                                " where " +
                                P.columns.FL_COMPLETION_AUTO + " = " + Posting.FLAG_TRUE +
                                " and "   +
                                P.columns.STATE + " <> " + Posting.STATE_DELETED +
                                " and ( "   + 
                                P.columns.REAL_VALUE + " is null " +
                                "or "     +
                                P.columns.REAL_VALUE + " = " + 0.0 + 
                                " ) and  "  +
                                P.columns.ESTIMATE_DATE + " <= " + db.quote( db.currentDate() ) + " ) " +
                     " and " +
                     P.columns.REF_COMPLETION_TYPE + " <> 0";
        
        return db.fetchAll( sql, P.fetcher );
    }
    
    public HashMap countPosting( Database db, int category ) throws Exception
    {
        Postings P = Postings.table;
        
        String sql = "select " + P.columns.STATE + ", count(*) from " + P.name;
                     
        if( category != -1 )
        {
            sql += " where " +
                   P.columns.REF_POSTING_CATEGORY + " = " + category;
        }
        
        sql += " group by " + P.columns.STATE;
        
        return db.queryMap( sql );
    }
    
    
    public int countPostingType( Database db, int type ) throws Exception
    {
        Postings P = Postings.alias( "P" );
        PostingCategories C = PostingCategories.alias( "C" );
        
        String sql = " select count(*) " +
                     " from "   + P.name + ", " + C.name +
                     " where "  +
                     P.columns.REF_POSTING_CATEGORY + " = " + C.columns.ID +
                     " and " +
                     C.columns.REF_POSTING_TYPE     + " = " + type;
        
        return db.queryInteger( sql );
    }
    
    public String getDrilldown( Database db ) throws Exception
    {
        PostingCategories C = PostingCategories.alias( "C" );
        PostingTypes T = PostingTypes.alias( "T" );
        Postings P = Postings.alias( "P" );

        String sql = " select " + C.columns.ID + ", " + C.columns.NAME +
                     " from "   + C.name       + ", " + P.name  + ", " + T.name +
                     " where "  +
                     P.columns.REF_POSTING_CATEGORY + " = " + C.columns.ID +
                     " and " +
                     C.columns.REF_POSTING_TYPE     + " = " + T.columns.ID +
                     " group by " +
                     C.columns.ID + ", " + C.columns.NAME;
        
        ResultSet set = db.query( sql );
        
        List<String> data = new ArrayList();
        
        while( set.next() )
        {
            int id  = set.getInt( 1 ) ;
            String name  = set.getString( 2 ) ;
            
            HashMap<Integer, Long> map = countPosting( db, id );
            
            long finished  = map.containsKey( Posting.STATE_FINISHED  ) ? map.get( Posting.STATE_FINISHED  ) : 0;
            long deleted   = map.containsKey( Posting.STATE_DELETED   ) ? map.get( Posting.STATE_DELETED   ) : 0;
            long progress  = map.containsKey( Posting.STATE_PROGRESS  ) ? map.get( Posting.STATE_PROGRESS  ) : 0;
            long registred = map.containsKey( Posting.STATE_REGISTRED ) ? map.get( Posting.STATE_REGISTRED ) : 0;
            
            data.add( "{ id: '" + id + "', name: '" + name + "', data : " +
                      "[" +
                           "{ name: 'Cadastrados',  y: " + registred + ", color: '#3364c8', drilldown: null }," + 
                           "{ name: 'Em Andamento', y: " + progress  + ", color: '#ded604', drilldown: null }," + 
                           "{ name: 'Excluidos',    y: " + deleted   + ", color: '#d82027', drilldown: null }," + 
                           "{ name: 'Finalizados',  y: " + finished  + ", color: '#408c1b', drilldown: null }" + 
                      "] }" );
        }
        
        set.close();
        
        db.getStatment().close();
       
        String values = "";
        
        if( ! data.isEmpty() )
        {
            values = data.toString().substring( 1, data.toString().length() - 1 );
        }
        
        return values;
    }
    
    public String foreingKey( int id )
    {
        String value = String.valueOf( id );
        
        if( id <= 0 )
        {
            value = "null";
        }
        
        return value;
    }
}
