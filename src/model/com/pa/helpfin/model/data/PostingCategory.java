package com.pa.helpfin.model.data;

import com.pa.helpfin.model.db.service.CompletionTypeManagerService;
import com.pa.helpfin.model.db.service.PostingManagerService;
import java.util.HashMap;

/**
 * @author artur
 */
public class PostingCategory
    extends 
        Core<PostingCategory>
{
    private String name = "";
    private String info = "";
    private int postingType;
    
    public PostingCategory() 
    {
    }

    public PostingCategory( String name, String info, int postingType )
    {
        this.name = name;
        this.info = info;
        this.postingType = postingType;
    }
    
    public String getName() 
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getInfo() 
    {
        return info;
    }

    public void setInfo( String info )
    {
        this.info = info;
    }

    public int getPostingType() 
    {
        return postingType;
    }

    public void setPostingType( int postingType )
    {
        this.postingType = postingType;
    }

    @Override
    public String toString() 
    {
        return this.name;
    }
    
    public String toJSON()
    {
        try
        {
            PostingType type = com.pa.helpfin.model.ModuleContext
                                        .getInstance()
                                        .getPostingTypeManager()
                                        .getValue( postingType );
            
            PostingManagerService pm = com.pa.helpfin.model.ModuleContext
                                        .getInstance()
                                        .getPostingManager();
           
            HashMap<Integer, Long> map = pm.countPosting( id );
            
            long finished  = map.containsKey( Posting.STATE_FINISHED  ) ? map.get( Posting.STATE_FINISHED  ) : 0;
            long deleted   = map.containsKey( Posting.STATE_DELETED   ) ? map.get( Posting.STATE_DELETED   ) : 0;
            long progress  = map.containsKey( Posting.STATE_PROGRESS  ) ? map.get( Posting.STATE_PROGRESS  ) : 0;
            long registred = map.containsKey( Posting.STATE_REGISTRED ) ? map.get( Posting.STATE_REGISTRED ) : 0;
            
            long sumCount = finished + deleted + progress + registred;
            
            
            return "{ " +
                      "'category_id':           '" + id                                  + "'," +
                      "'category_info':         '" + info.replaceAll( "'", "\"" )        + "'," +
                      "'category_name':         '" + name                                + "'," +
                      "'count_finished':        '" + finished                            + "'," +
                      "'count_deleted':         '" + deleted                             + "'," +
                      "'count_progress':        '" + progress                            + "'," +
                      "'count_registred':       '" + registred                           + "'," +
                      "'sum_count':             '" + sumCount                            + "'," +
                      "'category_type':         '" + type                                + "'"  +
                    "}";
        }
        
        catch ( Exception e )
        {
            e.printStackTrace();
        }
        
        return  null;
    }
}
