package com.pa.helpfin.control;

import com.pa.helpfin.control.tasks.MainTasks;
import com.pa.helpfin.model.ApplicationUtilities;
import com.pa.helpfin.model.data.Encryption;
import com.pa.helpfin.model.data.User;

/**
 * @author artur
 */
public class LoginController 
{
    private static LoginController instance;
    
    private LoginController(){}
    
    public static LoginController getInstance()
    {
        if( instance == null )
        {
            instance = new LoginController();
        }
        
        return instance;
    }
    
    public User login( String login, String password )
    {
        User user = null;
        
        try
        {
            user = com.pa.helpfin.model.ModuleContext
                                    .getInstance()
                                    .getUserManager()
                                    .getUserByLogin( login, Encryption.hash( password ) );
                      
            if( user != null )
            {
                ApplicationUtilities.getInstance().setActiveUser( user );
                
                new MainTasks().run();
            }
        }
        
        catch( Exception e )
        {
            ApplicationUtilities.logException( e );
        }
        
        return user;
    }
}
