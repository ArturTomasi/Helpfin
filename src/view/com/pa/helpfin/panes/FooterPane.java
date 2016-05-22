package com.pa.helpfin.panes;

import com.pa.helpfin.model.ApplicationUtilities;
import com.pa.helpfin.view.util.ContextMenuItem;
import com.pa.helpfin.view.util.FileUtilities;
import com.pa.helpfin.view.util.Prompts;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Side;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * @author artur
 */
public class FooterPane 
    extends 
        HBox
{
    public static class Events 
    {
        public static final EventType ON_LOGOUT = new EventType( "onLogout" );
    }
        
    public FooterPane() 
    {
        initComponents();
    }
    
    public void resize( BorderPane pane )
    {
        setLayoutX( pane.getWidth() );
        setSpacing( pane.getWidth() - btSystem.getWidth() - lbUser.getWidth() );
    }
    
    private void logout()
    {
        fireEvent( new Event( Events.ON_LOGOUT ) );
    }
    
    
    private void backup()
    {
        Prompts.process( "Gerando backup...", new Task<Void>() 
        {
            @Override
            protected Void call() throws Exception 
            {
                FileUtilities.generateBackup();
                return null;
            }
        } );
    }
    
    private void initComponents()
    {
        lbUser.setCache( true );
        lbUser.setText( "   " + ApplicationUtilities.getInstance().getActiveUserName() );
        lbUser.setStyle( "-fx-padding: 4 0 0 0;" + 
                         "-fx-font-size: 14pt;" +
                         "-fx-font-family: \"Helvetica, Verdana, sans-serif\";" +
                         "-fx-text-fill:" + ApplicationUtilities.getColor());
        
        btSystem.setCache( true );
        btSystem.setCursor( Cursor.HAND );
        btSystem.setStyle( "-fx-background-color: transparent; " + 
                           "-fx-font-size: 10pt;" +
                           "-fx-border-color:" + ApplicationUtilities.getColor() +
                           "-fx-text-fill:" + ApplicationUtilities.getColor() +
                           "-fx-font-family: \"Helvetica, Verdana, sans-serif\";" +
                           "-fx-border-width: 1; " + 
                           "-fx-border-radius: 10;" );
       
        btSystem.setText( "Sistema" );
        
        setStyle( "-fx-border-color: " + ApplicationUtilities.getColor() + 
                  "-fx-border-width: 2 0 0 0;" + 
                  "-fx-padding: 3 0 4 0; " + 
                  ApplicationUtilities.getBackground2() +
                  "-fx-text-fill:" + ApplicationUtilities.getColor() );
        
        menu.setStyle( ApplicationUtilities.getBackground2() );
        getChildren().addAll( lbUser, btSystem );
        
        btSystem.setContextMenu( menu );
        
        btSystem.setOnAction( new EventHandler<ActionEvent>()
        {
            @Override
            public void handle( ActionEvent t )
            {
                menu.show( btSystem, Side.TOP, 0, 0 );
            }
        } );
    }
    
    private Button btSystem = new Button();
    private Label lbUser = new Label();
    
    private ContextMenu menu = new ContextMenu
    ( 
        new ContextMenuItem( "Backup", "backup.png", new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle( ActionEvent t ) 
            {
                backup();
            }
        } ),
                    
        new ContextMenuItem( "Logout", "logout.png", new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle( ActionEvent t) 
            {
                logout();
            }
        } ) );
}
