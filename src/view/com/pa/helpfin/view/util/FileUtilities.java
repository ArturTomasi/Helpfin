package com.pa.helpfin.view.util;

import com.pa.helpfin.model.ApplicationUtilities;
import com.pa.helpfin.model.ConfigurationManager;
import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javafx.application.Platform;
import javafx.stage.FileChooser;

/**
 * @author artur
 */
public class FileUtilities 
{
    private final static String home;
    
    static 
    {
        String homePath = ConfigurationManager.getInstance().getProperty( "helpFin.home" );
        
        if( homePath != null && ! homePath.isEmpty() )
        {
            if( ! homePath.endsWith( File.separator ) )
            {
                homePath += File.separator;
            }

            if( ! homePath.startsWith( File.separator ) )
            {
                homePath = File.separator + homePath;
            }

            home = homePath;
        }
        
        else
        {
            home = null;
        }
    }

    
    public static String formatURL( String url )
    {
        if( url.startsWith( File.separator ) )
            url = url.replaceFirst( File.separator, "" );
        
        return home + "attachments/" + url;
    }
    
    
    
    public static File getAttachment( String url ) throws Exception
    {
        File file = new File( formatURL( url ) );
        
        validadeFile( file );
        
        return file;
    }

    
    public static void generateBackup()
    {
        try
        {
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis( System.currentTimeMillis() );
            
            File file = new File(  home + "backup/"+ c.get( Calendar.DAY_OF_MONTH ) +"/dump.sql" );

            if( ! file.exists() )
            {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            validadeFile( file );
            
            String password = ConfigurationManager.getInstance().getProperty( "db.password" );
            String user     = ConfigurationManager.getInstance().getProperty( "db.user" );
            String url      = ConfigurationManager.getInstance().getProperty( "db.url" );
            String psql     = System.getProperty( "os.name", "linux" ).startsWith( "win" ) ? 
                              ConfigurationManager.getInstance().getProperty( "postgres.url" ) :
                              "pg_dump";
            
            if( url != null )
            {
                String host = url.substring( url.indexOf( "//" ) + 2, url.lastIndexOf( "/" ) );
                String name = url.substring( url.lastIndexOf( "/" ) + 1, url.lastIndexOf( ":" ) > url.lastIndexOf( "/" ) ?   url.lastIndexOf( ":" ) : url.length()  );
                String port = url.length() - url.lastIndexOf( ":" ) > 4 ? "5432" : url.substring( url.lastIndexOf( ":" ) + 1, url.length() );
                
                //create dump in file
                ProcessBuilder pb = new ProcessBuilder
                (
                    psql,
                    "-i", 
                    "-p", port,
                    "-h", host,
                    "-U", user,
                    "-F",
                    "c",
                    "-b",
                    "-v",
                    "-f", file.getAbsolutePath(),
                    name
                );  


                pb.environment().put( "pgpassword", password );  
                pb.redirectErrorStream( true );  
                
                pb.start();
            }

            //copy attachments
            com.pa.helpfin.model.ModuleContext.getInstance().getAttachmentManager().getValues().forEach( (attachment) -> 
            {
                //validar url
                download( attachment.getUrl(), file.getParent() + File.separator + attachment.getUrl(), false );
            } );
        }
        
        catch( Exception ex )
        {
            logException( ex );
        }
    }
    
    
    
    public static void logException( Throwable e )
    {
        try
        {
            if( Boolean.valueOf( ConfigurationManager.getInstance().getProperty( "showLog" ) ) && home != null && ! home.isEmpty() )
            {
                File file = new File(  home + "logs/log.log" );

                if( ! file.exists() )
                {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                }   

                validadeFile( file );

                FileWriter fw = new FileWriter( file.getAbsoluteFile(), true) ; 
                BufferedWriter bw = new BufferedWriter( fw ); 

                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter( sw );

                e.printStackTrace( pw );

                DateFormat df = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss" );

                String message = "\n***************************************************************************************************************\n" +
                                 df.format( new Date( System.currentTimeMillis() ) ) + " - " + ApplicationUtilities.getInstance().getActiveUserName() + ": " + e.getMessage() +
                                  "\n***************************************************************************************************************\n" +
                                 sw.toString();

                bw.write( message ); 

                bw.close(); 
                pw.close(); 
            }
        }
        
        catch( Exception ex )
        {
            System.err.println( ex.getMessage() );
        }
        
    }
    
    
    public static void logSQL( String sql )
    {
        try
        {
            if( Boolean.valueOf( ConfigurationManager.getInstance().getProperty( "showSql" ) ) && home != null && ! home.isEmpty() )
            {
                File file = new File(  home + "logs/sql.log" );

                if( ! file.exists() )
                {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                }

                validadeFile( file );

                FileWriter fw = new FileWriter( file.getAbsoluteFile(), true) ; 
                BufferedWriter bw = new BufferedWriter( fw ); 

                DateFormat df = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss" );

                String message = "\n***************************************************************************************************************\n" +
                                 df.format( new Date( System.currentTimeMillis() ) ) + " - " + ApplicationUtilities.getInstance().getActiveUserName() + "\n" + sql +
                                  "\n***************************************************************************************************************\n";

                bw.write( message ); 

                bw.close(); 
            }
        }
        
        catch( Exception ex )
        {
            ApplicationUtilities.logException( ex );
        }
    }
    
    
    public static void open( String path ) throws Exception
    {
        open( new File( formatURL( path ) ) );
    }
            
    
    public static void open( final File file ) throws Exception
    {
        validadeFile( file );

        new Thread( new Runnable() 
        {
            @Override
            public void run() 
            {
                try 
                {
                    if( Desktop.isDesktopSupported() )
                        Desktop.getDesktop().open( file );
                } 
                catch ( Exception e )
                {
                    ApplicationUtilities.logException( e );
                }
            }
        } ).start();
    }


    
    public static void deleteFile( String path ) throws Exception
    {
        validadeFile( new File( formatURL( path ) ) );
        
        Files.deleteIfExists( Paths.get( formatURL( path ) ) );
    }
    
    
    
    public static void copyFile( String path, String nameTarget )
    {
        Platform.runLater( new Runnable() 
        {
            @Override
            public void run()
            {
                try
                {
                    File file = new File(  home + "attachments/" );

                    if( ! file.exists() )
                    {
                        file.getParentFile().mkdirs();
                        file.mkdir();
                    }

                    if( ! file.canWrite() )
                        return;


                    if( path == null || path.lastIndexOf( File.separator ) == -1 )
                    {
                        throw new Exception( "URL invalid: " + path );
                    }

                    Path source =  Paths.get( path );

                    Path target =  Paths.get( formatURL( nameTarget ) );

                    Files.copy( source, target );
                }

                catch( Exception e )
                {
                    ApplicationUtilities.logException( e );
                }
            }
        } );
    }
    
    
    public static void download( String path, String target )
    {
        download( path, target, true );
    }
    
    
    
    public static void download( String path, String target, boolean open )
    {
        Platform.runLater( new Runnable() 
        {
            @Override
            public void run() 
            {
                try
                {
                    Path attachment =  Paths.get( formatURL( path ) );

                    Files.copy( attachment, Paths.get( target ), StandardCopyOption.REPLACE_EXISTING );
                    
                    if( open )
                    {
                        open( new File( target ) );
                    }
                }

                catch( Exception e )
                {
                    ApplicationUtilities.logException( e );
                }
            }
        } );
    }
    
    
    
    private static void validadeFile( File file ) throws Exception
    {
        if( ! file.exists() )
            throw new Exception( "File not found: " + file.getAbsolutePath() );
        
        if( ! file.canRead() )
            throw new Exception( "File can not read: " + file.getAbsolutePath() );
        
        if( ! file.canWrite() )
            throw new Exception( "File can not writer: " + file.getAbsolutePath() );
    }
    
    
    
    public static File saveFile( String title, String fileName )
    {
        FileChooser  fileChooser = new FileChooser();

        fileChooser.setTitle( title );
        fileChooser.setInitialDirectory( new File( System.getProperty( "user.home" ) ) );                 
        fileChooser.setInitialFileName( fileName );

        return fileChooser.showSaveDialog( ApplicationUtilities.getInstance().getWindow() );
    }
    
    
    
    public static File choiceFile( String title )
    {
        FileChooser  fileChooser = new FileChooser();

        fileChooser.setTitle( title );
        fileChooser.setInitialDirectory( new File( System.getProperty( "user.home" ) ) );                 

        return fileChooser.showOpenDialog( ApplicationUtilities.getInstance().getWindow() );
    }
}
