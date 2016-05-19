package com.pa.helpfin.model.data;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author artur
 */
public class Posting
    extends 
        Core<Posting>
{    
    public final static int STATE_REGISTRED = 0;
    public final static int STATE_PROGRESS  = 1;
    public final static int STATE_FINISHED  = 2;
    public final static int STATE_DELETED   = 3;
    
    @SuppressWarnings("FieldNameHidesFieldInSuperclass")
    public final static String STATES []   = 
    {
       "Cadastrado",
       "Em Andamento",
       "Finalizado",
       "Excluido"
    };
    
    private String  name = "";
    private String  info = "";
    private Date    realDate;
    private Date    estimateDate;
    private double  realValue;
    private double  estimateValue;
    private int     portion = 0;
    private int     portionTotal = 0;
    private boolean completionAuto;
    private boolean repeat;
    
    private int postingCategory;
    private int user;
    private int completionType;
    private int entity;
    private int posting;

    public Posting() 
    {
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

    public Date getRealDate() 
    {
        return realDate;
    }

    public void setRealDate( Date realDate ) 
    {
        this.realDate = realDate;
    }

    public Date getEstimateDate()
    {
        return estimateDate;
    }

    public void setEstimateDate( Date estimateDate ) 
    {
        this.estimateDate = estimateDate;
    }

    public double getRealValue()
    {
        return realValue;
    }

    public void setRealValue( double realValue ) 
    {
        this.realValue = realValue;
    }

    public double getEstimateValue()
    {
        return estimateValue;
    }

    public void setEstimateValue( double estimateValue )
    {
        this.estimateValue = estimateValue;
    }

    public int getPortion()
    {
        return portion;
    }

    public void setPortion( int portion ) 
    {
        this.portion = portion;
    }

    public int getPortionTotal()
    {
        return portionTotal;
    }

    public void setPortionTotal( int portionTotal )
    {
        this.portionTotal = portionTotal;
    }

    public boolean isCompletionAuto() 
    {
        return completionAuto;
    }

    public void setCompletionAuto( boolean completionAuto )
    {
        this.completionAuto = completionAuto;
    }

    public boolean isRepeat() 
    {
        return repeat;
    }

    public void setRepeat( boolean repeat )
    {
        this.repeat = repeat;
    }

    public int getPostingCategory() 
    {
        return postingCategory;
    }

    public void setPostingCategory( int postingCategory )
    {
        this.postingCategory = postingCategory;
    }

    public int getUser() 
    {
        return user;
    }

    public void setUser( int user )
    {
        this.user = user;
    }

    public int getCompletionType() 
    {
        return completionType;
    }

    public void setCompletionType( int completionType ) 
    {
        this.completionType = completionType;
    }

    public int getEntity() 
    {
        return entity;
    }

    public void setEntity( int entity )
    {
        this.entity = entity;
    }

    public int getPosting() 
    {
        return posting;
    }

    public void setPosting( int posting ) 
    {
        this.posting = posting;
    }

    @Override
    public Posting clone() 
    {
       Posting p = new Posting();
       p.setCompletionAuto( completionAuto );
       p.setCompletionType( completionType );
       p.setEntity( entity );
       p.setEstimateDate( estimateDate );
       p.setEstimateValue( estimateValue );
       p.setInfo( info );
       p.setName( name );
       p.setPortion( portion );
       p.setPortionTotal( portionTotal );
       p.setPosting( posting );
       p.setPostingCategory( postingCategory );
       p.setRealDate( realDate );
       p.setRealValue( realValue );
       p.setRepeat( repeat );
       p.setState( state );
       p.setUser( user );
       
       return p;
    }

    public String toJSON()
    {
        try
        {
            DateFormat df = new SimpleDateFormat( "dd/MM/yyyy" );

            String dateReal     = realDate     == null ? "n/d" : df.format( realDate ); 
            String dateEstimate = estimateDate == null ? "n/d" : df.format( estimateDate ); 

            User owner = UserCache.getUser( user );

            PostingCategory category = com.pa.helpfin.model.ModuleContext
                                        .getInstance()
                                        .getPostingCategoryManager()
                                        .getValue( postingCategory );

            CompletionType type = com.pa.helpfin.model.ModuleContext
                                        .getInstance()
                                        .getCompletionTypeManager()
                                        .getValue( completionType );

            Entity ent = com.pa.helpfin.model.ModuleContext
                                        .getInstance()
                                        .getEntityManager()
                                        .getValue( entity );
            
            Posting origin = com.pa.helpfin.model.ModuleContext
                                    .getInstance()
                                    .getPostingManager()
                                    .getValue( posting );

            return "{ " +
                      "'posting_id':           '" + id                                  + "'," +
                      "'posting_info':         '" + info.replaceAll( "'", "\"" )        + "'," +
                      "'posting_name':         '" + name                                + "'," +
                      "'real_date':            '" + dateReal                            + "'," +
                      "'estimate_date':        '" + dateEstimate                        + "'," +
                      "'real_value':           '" + "R$ " + realValue                   + "'," +
                      "'estimate_value':       '" + "R$ " + estimateValue               + "'," +
                      "'fl_completion_auto':   '" + ( completionAuto ? "Sim" : "Não" )  + "'," +
                      "'portion':              '" + portion                             + "'," +
                      "'portion_total':        '" + portionTotal                        + "'," +
                      "'state':                '" + Posting.STATES[ state ]             + "'," +
                      "'ref_posting_category': '" + category                            + "'," +
                      "'ref_user':             '" + owner                               + "'," +
                      "'ref_completion_type':  '" + ( type != null ? type : "n/d" )     + "'," +
                      "'ref_entity':           '" + ent                                 + "'," +
                      "'ref_posting':          '" + ( origin != null ? origin.getName() + "(" + origin.getId() + ")" : "n/d" ) + "'" +
                    "}";
        }
        
        catch ( Exception e )
        {
            System.err.println( e );
        }
        
        return  null;
    }
    
    @Override
    public String toString() 
    {
        return name;
    }
}
