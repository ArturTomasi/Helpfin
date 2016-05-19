package com.pa.helpfin.model.db;

import com.pa.helpfin.model.ApplicationUtilities;
import com.pa.helpfin.model.ConfigurationManager;
import com.pa.helpfin.model.data.Core;
import com.pa.helpfin.model.data.User;
import com.pa.helpfin.model.db.fetcher.Fetcher;
import com.pa.helpfin.view.util.FileUtilities;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author artur
 */
public class Database
{
    private static final String DB_URL      = ConfigurationManager.getInstance().getProperty( "db.url" );
    private static final String DB_USER     = ConfigurationManager.getInstance().getProperty( "db.user" );
    private static final String DB_PASSWORD = ConfigurationManager.getInstance().getProperty( "db.password" );
    private static final String DB_DRIVER   = ConfigurationManager.getInstance().getProperty( "db.driver" );

    private static Database database;
    private static Connection connection;
    private static Statement statment;
    
    
    //Obrigar a utilizar o getInstance
    private Database() throws Exception
    {
        DriverManager.registerDriver( (Driver) Class.forName( DB_DRIVER ).newInstance() );
    }
    
    public static  Database getInstance()throws Exception
    {
        if( database == null )
        {
            database = new Database();
        }
        
        initConnection();
        
        return database;
    }
    
    
    private static void initConnection() throws Exception
    {
        if( connection == null || connection.isClosed() )
        {
            connection = DriverManager.getConnection( DB_URL, DB_USER, DB_PASSWORD );
        }
    }
    
    
    public void release() throws Exception
    {
        if( connection != null )
        {
            connection.close();
        }
    }
    
    public void executeCommand( String sql ) throws Exception
    {
        FileUtilities.logSQL( sql );
        
        if( connection != null )
        {
            statment = connection.createStatement();
            
            statment.execute( sql );
            
            statment.close();
        }
    }
    
    public <T> T fetchOne( String sql, Fetcher<T> fetcher ) throws Exception, Exception
    {
        FileUtilities.logSQL( sql );
        
        statment = connection.createStatement();
        
        ResultSet resultSet = statment.executeQuery( sql );
        
        T t = null;
        
        if ( resultSet.next() )
        {
            t = fetcher.fetch( resultSet );
        }
        
        resultSet.close();

        statment.close();
        
        return t;
    }
    
    public <T>  List<T> fetchAll( String sql, Fetcher<T> fetcher ) throws Exception
    {
        FileUtilities.logSQL( sql );
        
        statment = connection.createStatement();
        
        ResultSet resultSet = statment.executeQuery( sql );
        
        List<T> list = new ArrayList();
        
        while ( resultSet.next() )
        {
            list.add( fetcher.fetch( resultSet ) );
        }
        
        resultSet.close();
        
        statment.close();

        return list;
    }
    
    public HashMap queryMap( String sql ) throws Exception
    {
        FileUtilities.logSQL( sql );
        
        statment = connection.createStatement();
        
        ResultSet resultSet = statment.executeQuery( sql );
        
        HashMap hash = new HashMap();
        
        while ( resultSet.next() )
        {
            hash.put( resultSet.getInt( 1 ), resultSet.getObject( 2 ) );
        }
        
        resultSet.close();
        
        statment.close();

        return hash;
    }
    
    public Integer queryInteger( String sql ) throws Exception
    {
        FileUtilities.logSQL( sql );
        
        statment = connection.createStatement();
        
        ResultSet resultSet = statment.executeQuery( sql );
        
        Integer integer = null;
                
        if ( resultSet.next() )
        {
           integer = resultSet.getInt( 1 );
        }
        
        resultSet.close();
        
        statment.close();
        
        return integer;
    }
    
    public String queryString( String sql ) throws Exception
    {
        FileUtilities.logSQL( sql );
        
        statment = connection.createStatement();
        
        ResultSet resultSet = statment.executeQuery( sql );
        
        String string = null;
        
        if( resultSet.next() )
        {
            string = resultSet.getString( 1 );
        }
        
        resultSet.close();
        
        statment.close();
        
        return string;
    }
    
    public ResultSet query( String sql ) throws Exception
    {
        FileUtilities.logSQL( sql );
        
        statment = connection.createStatement();
        
        ResultSet resultSet = statment.executeQuery( sql );
        
        return resultSet;
    }
    
    public String restrictions( String column )
    {
        User user = ApplicationUtilities.getInstance().getActiveUser();
        
        return " case when " + 
                user.getRole() + " = " + User.ROLE_OPERATOR + 
                " then " +
                column + " = " + user.getId() + " else true end ";
    }
    
    public String quote( Date date )
    {
        if( date == null )
        {
            return "null";
        }
    
        return "\'" + date.toString()  + "\'";
    }
    
    public String quote( Object object )
    {
        if( object == null )
        {
            return "null";
        }
        
        return quote( object.toString() );
    }
    
    public String quote( String sql )
    {
        if( sql == null )
        {
           return "null";
        }
        
        if( sql.contains( "\'" ) )
        {
            sql = sql.replace( "\'", "\''" );
        }
        
        return "\'" + sql + "\'";
    }
   
    public String upper( String value )
    {
        return " upper( " +  value + " ) ";
    }
    
    public int flag( boolean value )
    {
        return value ? Core.FLAG_TRUE : Core.FLAG_FALSE;
    }
    
    public int nextId( String table ) throws Exception
    {
        String sql = " select nextval ( " + quote( table + "_id_seq" ) + " )";
        
        return queryInteger( sql );
    }
    
    public Date currentDate()
    {
        return new Date( System.currentTimeMillis() );
    }
    
    
    public Statement getStatment()
    {
        return Database.statment;
    }
    
    public Connection getConnection()
    {
        return Database.connection;
    }
}
