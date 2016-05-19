package com.pa.helpfin.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * @author artur
 */
public class ConfigurationManager 
{
    private static ConfigurationManager instance;
    
    private static Properties properties;

    private ConfigurationManager()
    {
        loadProperties();
    }
    
    public static ConfigurationManager getInstance()
    {
        if( instance == null )
        {
            instance = new ConfigurationManager();
        }
        
        return instance;
    }
    
    private void loadProperties() 
    {
        try
        {
            if( properties == null )
            {
                properties = new Properties();

                InputStream fileInputStream = getClass().getClassLoader().getResourceAsStream( "config" + File.separator + "application.properties" );     

                properties.load( fileInputStream );
                
                fileInputStream.close();

                File file = new File( "application.properties" );
                
                if( file.exists() )
                {
                    fileInputStream = new FileInputStream( file );

                    properties.load( fileInputStream );
                
                    fileInputStream.close();
                }
            }
                
        }
        
        catch( Exception e )
        {
            ApplicationUtilities.logException( e );
        }
    }
    
    public String getProperty( String key ) 
    {
        String prop = "";
        
        try
        {
            prop = getProperty( key, "" );
        }
        
        catch ( Exception e )
        {
            ApplicationUtilities.logException( e );
        }
        
        return  prop;
    }
    
    public String getProperty( String key, String defaultValue )
    {
        return properties.getProperty( key, defaultValue );
    }
    
    
    public NodeList loadXML( String path, String tag ) throws Exception
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse( path );
        
        return  document.getElementsByTagName( tag );
    }
    
}
