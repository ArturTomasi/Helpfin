package com.pa.helpfin.model.db.transactions;

import com.pa.helpfin.model.data.PostingCategory;
import com.pa.helpfin.model.data.PostingType;
import com.pa.helpfin.model.db.Database;
import com.pa.helpfin.model.db.Schema.PostingCategories;
import com.pa.helpfin.model.db.Schema.PostingTypes;
import java.util.ArrayList;
import java.util.List;

/**
 * @author artur
 */
public class PostingTypeManagerTransactions 
{
    public void addPostingType( Database db, PostingType postingType ) throws Exception
    {
        if( postingType == null )
        {
            throw new Exception( "posting type cannot be null" );
        }
        
        PostingTypes P = PostingTypes.table;
        
        String sql = "insert into " + P.name +
                     "( " +
                     P.columns.NAME   + "," +
                     P.columns.INFO   + "," +
                     P.columns.STATE + 
                     " ) values ( " +
                     db.quote( postingType.getName() ) + ", " +
                     db.quote( postingType.getInfo() ) + ", " +
                     postingType.getState()           +
                     " )";
        
        db.executeCommand( sql );
    }
    
    public void deletePostingType( Database db, PostingType postingType ) throws Exception
    {
        PostingTypes P = PostingTypes.table;
        
        String sql = "update " + P.name + " set " +
                     P.columns.STATE + " = " + PostingType.STATE_INACTIVE +
                     " where " +
                     P.columns.ID     + " = " + postingType.getId();
        
        db.executeCommand( sql );
        
        //delete categories if exists 
        
        PostingCategories C = PostingCategories.table;
        
        sql = "update " + C.name + " set " +
              C.columns.STATE           + " = " + PostingCategory.STATE_INACTIVE +
              " where " +
              C.columns.REF_POSTING_TYPE + " = " + postingType.getId();
        
        db.executeCommand( sql );
    }
    
    public void updatePostingType( Database db, PostingType postingType ) throws Exception
    {
        PostingTypes P = PostingTypes.table;
        
        String sql = "update " + P.name + " set " +
                     P.columns.STATE + " = " + postingType.getState()           + ", " +
                     P.columns.NAME   + " = " + db.quote( postingType.getName() ) + ", " +
                     P.columns.INFO   + " = " + db.quote( postingType.getInfo() ) + 
                     " where " +
                     P.columns.ID     + " = " + postingType.getId();
        
        db.executeCommand( sql );
    }
    
    public PostingType getPostingType( Database db, int postingTypeId ) throws Exception
    {
        if( postingTypeId < 0 )
        {
            throw new Exception( "Posting Type ID cannot be 0" );
        }
        
        PostingTypes P = PostingTypes.table;
        
        String sql = P.select + 
                     " where " +
                     P.columns.ID     + " = " + postingTypeId +
                     " and " +
                     P.columns.STATE + " = " + PostingType.STATE_ACTIVE ; 
                     
        return db.fetchOne( sql, P.fetcher );
    }
    
    public PostingType getPostingType( Database db, String name ) throws Exception
    {
        PostingTypes P = PostingTypes.table;
        
        String sql = P.select + 
                     " where " +
                     P.columns.STATE + " = " + PostingType.STATE_ACTIVE +
                     " and " +
                     P.columns.NAME   + " like " + db.quote( name );
                     
        return db.fetchOne( sql, P.fetcher );
    }
   
    public List<PostingType> getPostingTypes( Database db, boolean showInactives ) throws Exception
    {
        PostingTypes P = PostingTypes.table;
        
        String sql = P.select;
        
        if( ! showInactives )
        {
            sql +=  " where " +
                    P.columns.STATE + " = " + PostingType.STATE_ACTIVE ;
        }
        
        sql += " order by " + P.columns.NAME;
                     
        return db.fetchAll( sql, P.fetcher );
    }
    
    public List<String> isUnique( Database db, PostingType postingType ) throws Exception
    {
        List<String> notUnique = new ArrayList();
        
        PostingTypes P = PostingTypes.table;
        
        String sql = " select count( * ) from " + P.name + 
                     " where " +
                     db.upper( P.columns.NAME ) + " = " + db.upper( db.quote( postingType.getName() ) ) +
                     " and " +
                     P.columns.ID               + " <> " + postingType.getId();
        
        if( db.queryInteger( sql ) != 0 )
        {
            notUnique.add( "\nNão são únicos:" );
            notUnique.add( postingType.getName() );
        }
        
        return notUnique;
    }
    
    public boolean hasPostingCategories( Database db, PostingType postingType ) throws Exception
    {
        PostingCategories C = PostingCategories.table;
        
        String sql = " select count( * ) from " + C.name +
                     " where " +
                     C.columns.REF_POSTING_TYPE + " = " + postingType.getId();
        
        return db.queryInteger( sql ) == 0;
    }
}
