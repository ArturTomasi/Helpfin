package com.pa.helpfin.model.data;

/**
 * @author artur
 */
public class Attachment
    extends 
        Core<Attachment>
{
    private String url  = "";
    private String name = "";
    private String info = "";
    private String origin = "";
    private int posting;

    public Attachment() 
    {
    }
    
    public Attachment( String name, String url ) 
    {
        this.url = url;
        this.name = name;
    }

    public String getUrl() 
    {
        return url;
    }

    public void setUrl( String url ) 
    {
        this.url = url;
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

    public String getOrigin()
    {
        return origin;
    }

    public void setOrigin( String origin )
    {
        this.origin = origin;
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
    public Attachment clone()
    {
        Attachment attachment = new Attachment();
        attachment.setName( name ); 
        attachment.setInfo( info ); 
        attachment.setPosting( posting ); 
        attachment.setUrl( url ); 
        attachment.setOrigin( origin ); 
        
        return attachment;
    }
    
    

    
    @Override
    public String toString() 
    {
        return name;
    }
}
