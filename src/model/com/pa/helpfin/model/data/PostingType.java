package com.pa.helpfin.model.data;

/**
 * @author artur
 */
public class PostingType 
    extends 
        Core<PostingType>
{
    public final static int TYPE_COST    = 1;
    public final static int TYPE_REVENUE = 2;

    private String info;
    private String name;
    
    public PostingType() 
    {
    }
    
    public PostingType( String name ) 
    {
        this.name = name;
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

    @Override
    public String toString() 
    {
        return name;
    }
}
