package com.pa.helpfin.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author artur
 */
public class ResourceLocator 
{
    private static ResourceLocator instance;
    private String configurationPath;
    private String webPath;
    private String imagesPath;
        
    private ResourceLocator()
    {
        load();
    }
    
    
    
    public static ResourceLocator getInstance()
    {
        if( instance == null )
        {
            instance = new ResourceLocator();
        }
        
        return instance;
    }    
    
    
    
    private void load()
    {
        this.webPath = getClass().getClassLoader().getResource( "web" ).toString() + File.separator;
        
        this.configurationPath = getClass().getClassLoader().getResource( "config" ).getPath() + File.separator;
        
        this.imagesPath = getClass().getClassLoader().getResource( "images" ).toString() + File.separator;
    }
    
    
    
    public String getConfigurationPath()
    {
        return configurationPath;
    }
   
    
    
    public String getWebPath()
    {
        return webPath;
    }
    
    
    
    public String getWebResource( String archive )
    {
        return getWebPath() + archive;
    }
    
    
    
    public String getImagesPath()
    {
        return imagesPath;
    }
    
    
    
    public InputStream getImageStream( String name )
    {
        try
        {
            String path = getImagesPath() + name;

            return new FileInputStream( path );
        }
        
        catch( Exception e )
        {
            ApplicationUtilities.logException( e );
        }
        
        return null;
    }
    
    
    
    public String getImageResource( String name )
    {
        return getImagesPath() + name;
    }
}
