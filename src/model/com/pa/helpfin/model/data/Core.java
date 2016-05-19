package com.pa.helpfin.model.data;

/**
 * @author artur
 * @param <T>
 */
public abstract class Core<T>
{
    public final static int STATE_ACTIVE   = 0;
    public final static int STATE_INACTIVE = 1;
    
    public final static String STATES []   = 
    {
       "Ativo",
       "Inativo"
    };
    
    public final static int FLAG_FALSE = 0;
    public final static int FLAG_TRUE  = 1;
    
    protected int id;   
    protected int state;
    
    public int getId() 
    {
        return id;
    }

    public void setId( int id ) 
    {
        this.id = id;
    }

    public int getState() 
    {
        return state;
    }

    public void setState( int state )
    {
        this.state = state;
    }

    @Override
    public boolean equals( Object source ) 
    {
        if( source instanceof Core )
        {
            return ( (Core) source ).getId() == this.id;
        }
        
        return false;
    }
}
