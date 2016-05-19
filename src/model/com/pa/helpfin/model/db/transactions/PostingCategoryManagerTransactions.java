package com.pa.helpfin.model.db.transactions;

import com.pa.helpfin.model.data.Option;
import com.pa.helpfin.model.data.PostingCategory;
import com.pa.helpfin.model.data.PostingCategoryFilter;
import com.pa.helpfin.model.data.PostingType;
import com.pa.helpfin.model.db.Database;
import com.pa.helpfin.model.db.Schema;
import com.pa.helpfin.model.db.Schema.PostingCategories;
import com.pa.helpfin.model.db.Schema.PostingTypes;
import com.pa.helpfin.model.db.Schema.Postings;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author artur
 */
public class PostingCategoryManagerTransactions 
{
    public void addPostingCategory( Database db, PostingCategory postingCategory ) throws Exception 
    {
        if( postingCategory == null )
        {
            throw new Exception( "Posting Category cannot be null" );
        } 

        PostingCategories C = PostingCategories.table;

        String sql = "insert into " + C.name +
                     "( " +
                     C.columns.NAME             + ", " +
                     C.columns.INFO             + ", " +
                     C.columns.STATE            + ", " +
                     C.columns.REF_POSTING_TYPE +
                     " ) values ( " +
                     db.quote( postingCategory.getName() ) + ", " +
                     db.quote( postingCategory.getInfo() ) + ", " +
                     postingCategory.getState()            + ", " +
                     postingCategory.getPostingType()      +
                     " )";
        
        db.executeCommand( sql );
    }
    
    public void updatePostingCategory( Database db, PostingCategory postingCategory ) throws Exception 
    {
        if( postingCategory == null )
        {
            throw new Exception( "Posting Category cannot be null" );
        } 

        PostingCategories C = PostingCategories.table;

        String sql = "update " + C.name + " set " +
                     C.columns.NAME             + " = " + db.quote( postingCategory.getName() ) + ", " +
                     C.columns.INFO             + " = " + db.quote( postingCategory.getInfo() ) + ", " +
                     C.columns.STATE            + " = " + postingCategory.getState()            + ", " +
                     C.columns.REF_POSTING_TYPE + " = " + postingCategory.getPostingType()      + 
                     " where " +
                     C.columns.ID               + " = " + postingCategory.getId();
        
        db.executeCommand( sql );
    }
    
    public void deletePostingCategory( Database db, PostingCategory postingCategory ) throws Exception 
    {
        if( postingCategory == null )
        {
            throw new Exception( "Posting Category cannot be null" );
        } 

        PostingCategories C = PostingCategories.table;

        String sql = "update " + C.name + " set " +
                     C.columns.STATE  + " = " + PostingCategory.STATE_INACTIVE +
                     " where " +
                     C.columns.ID     + " = " + postingCategory.getId();
        
        db.executeCommand( sql );
    }
    
    public PostingCategory getPostingCategory( Database db, int postingCategoryId ) throws Exception 
    {
        PostingCategories C = PostingCategories.table;

        String sql = C.select  +
                     " where " +
                     C.columns.STATE + " = " + PostingCategory.STATE_ACTIVE +
                     " and "   +
                     C.columns.ID    + " = " + postingCategoryId;
        
        return db.fetchOne( sql, C.fetcher );
    }
    
    public PostingCategory getPostingCategory( Database db, String name ) throws Exception 
    {
        PostingCategories C = PostingCategories.table;

        String sql = C.select  +
                     " where " +
                     C.columns.STATE + " = " + PostingCategory.STATE_ACTIVE +
                     " and "   +
                     db.upper( C.columns.NAME ) + " like " + db.upper( db.quote( name ) ) ;
        
        return db.fetchOne( sql, C.fetcher );
    }
    
    public List<PostingCategory> getPostingCategories( Database db, boolean showInactives ) throws Exception 
    {
        PostingCategories C = PostingCategories.table;

        String sql = C.select ;
        
        if( ! showInactives )
        {
            sql += " where " +
                    C.columns.STATE + " = " + PostingCategory.STATE_ACTIVE;
        }
        
        sql += " order by " + C.columns.NAME;
        
        return db.fetchAll( sql, C.fetcher );
    }
    
    public List<PostingCategory> getPostingCategories( Database db, int type ) throws Exception 
    {
        PostingCategories C = PostingCategories.table;

        String sql = C.select +
                     " where " +
                     C.columns.STATE + " = " + PostingCategory.STATE_ACTIVE +
                     " and " +
                     C.columns.REF_POSTING_TYPE + " = " + type +
                     " order by " + C.columns.NAME;
        
        return db.fetchAll( sql, C.fetcher );
    }
    
    public List<PostingCategory> getPostingCategories( Database db, PostingCategoryFilter filter ) throws Exception 
    {
        PostingCategories C = PostingCategories.table;
        
        StringBuilder sb = new StringBuilder();
        
        sb.append( C.select ).append( " where true " );
        
        filter.getConditions().forEach( ( key, values ) ->
        {
            String condition = " and ( ";
            
            for( int i = 0; i < values.size(); i++ )
            {
                switch ( key )
                {
                    case PostingCategoryFilter.INFO:
                    {
                        condition += C.columns.INFO + " ilike " + db.quote( "%" + values.get( i ) + "%" );
                    }
                    break;
                   
                    case PostingCategoryFilter.NAME:
                    {
                        condition += C.columns.NAME + " ilike " + db.quote( "%" + values.get( i ) + "%" );
                    }
                    break;
                        
                    case PostingCategoryFilter.POSTING_TYPE:
                    {
                        if( values.get( i ) instanceof PostingType )
                        {
                            PostingType postingType = (PostingType) values.get( i );
                            
                            condition += C.columns.REF_POSTING_TYPE + " = " + postingType.getId();
                        }
                    }
                    break;
                        
                    case PostingCategoryFilter.STATE:
                    {
                        if( values.get( i ) instanceof Option )
                        {
                            Option option = (Option) values.get( i );
                            
                            condition += C.columns.STATE + " = " + option.getId();
                        }
                    }
                    break;
                }
                
                condition += i == values.size() - 1 ? " ) " : " or ";
            }
            
            sb.append( condition );
            
        } );
        
        sb.append( " order by " ).append( C.columns.NAME );
        
        return db.fetchAll( sb.toString(), C.fetcher );
    }
    
    public List<String> isUnique( Database db, PostingCategory postingCategory ) throws Exception 
    {
        List<String> notUnique = new ArrayList();
        
        PostingCategories C = PostingCategories.table;

        String sql = " select count( * ) from "  + C.name +
                     " where " +
                     db.upper( C.columns.NAME ) + " like " + db.upper( db.quote( postingCategory.getName() ) ) +
                     " and " +
                     C.columns.REF_POSTING_TYPE +   " = "  + postingCategory.getPostingType() +
                     " and "  +
                     C.columns.ID               +  " <> "  + postingCategory.getId();
         
        if( db.queryInteger( sql ) != 0 )
        {
            notUnique.add( "\nNão são únicos:" );
            notUnique.add( postingCategory.getName() );
        }
        
        return notUnique;
    }
    
    public boolean isValidReferences( Database db, PostingCategory postingCategory ) throws Exception 
    {
        PostingTypes T = Schema.PostingTypes.table;

        String sql = " select count( * ) " +
                     " from "  + T.name    + 
                     " where " +
                     T.columns.ID    + " = " + postingCategory.getPostingType() +
                     " and " +
                     T.columns.STATE + " = " + PostingType.STATE_ACTIVE;
        
        return db.queryInteger( sql ) > 0;
    }
    
    public String getDrilldown( Database db, int type ) throws Exception 
    {
        PostingCategories C = PostingCategories.alias( "C" );
        PostingTypes T = PostingTypes.alias( "T" );
        Postings P = Postings.alias( "P" );

        String sql = " select " + C.columns.ID + ", count(*), " + C.columns.NAME +
                     " from "   + C.name       + ", " + P.name  + ", " + T.name +
                     " where "  +
                     P.columns.REF_POSTING_CATEGORY + " = " + C.columns.ID +
                     " and " +
                     C.columns.REF_POSTING_TYPE     + " = " + T.columns.ID +
                     " and " +
                     T.columns.ID + " = " + type +
                     " group by " +
                     C.columns.ID + ", " + C.columns.NAME;
        
        ResultSet set = db.query( sql );
        
        List<String> data = new ArrayList();
        
        while( set.next() )
        {
            int id  = set.getInt( 1 ) ;
            int count  = set.getInt( 2 ) ;
            String name  = set.getString( 3 ) ;
            
            data.add( "{ name: '" + name +  "', y: " + count + " , drilldown: '" + id + "' }" );
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
    
    
}
