package com.pa.helpfin.view;

import com.pa.helpfin.model.ApplicationUtilities;
import com.pa.helpfin.model.ResourceLocator;
import com.pa.helpfin.panes.modules.AbstractModulesPane;
import com.pa.helpfin.panes.ActionPane; 
import com.pa.helpfin.panes.FooterPane;
import com.pa.helpfin.panes.HeaderPane;
import com.pa.helpfin.panes.MenuPane;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * @author artur
 */
public class HomeView 
    extends 
        Application
    implements 
        ApplicationView
{
    @Override
    public void start( Stage stage ) throws Exception 
    {
        initComponents();
        
        resize();
        
        initListeners();
        
        stage.setMaximized( true );
        stage.setTitle( "HelpFin Application" );
        stage.setScene( scene );
        stage.getIcons().add( new Image( ResourceLocator.getInstance().getImageResource( "helpFin.png") ) );
        stage.show();

        ApplicationUtilities.getInstance().setRootComponent( pane );
        ApplicationUtilities.getInstance().setWindow( this.stage = stage );
    }

    @Override
    public void resize() 
    {
        footerPane.resize( pane );
        headerPane.resize( pane );

        if( centerPane != null && menuPane != null )
        {
            actionPane.setPrefWidth( centerPane.getActions().isEmpty() ? 0 : 105 );
            
            double width = pane.getWidth() - menuPane.getWidth() - actionPane.getPrefWidth();
            double height = pane.getHeight() - footerPane.getHeight() - headerPane.getHeight() ;

            centerPane.resizeComponents( height, width );
        }
    }
   
    @Override
    public void initComponents() throws Exception 
    {
        pane.setBottom( footerPane );
        pane.setTop( headerPane );
        pane.setLeft( menuPane );
        pane.setRight( actionPane );

        actionPane.setStyle( "-fx-padding: 10 4 4 4; -fx-border-color: #415A78; -fx-border-width: 0 0 0 2;" );
        pane.setStyle( "-fx-background-color: linear-gradient( from 0% 0% to 100% 100%, #415A78 0%, silver 100%);" );
    }

    @Override
    public void initListeners() 
    {
        scene.widthProperty().addListener( new ChangeListener<Number>() 
        {
            @Override 
            public void changed( ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth )
            {
               resize();
            }
        });
        
        scene.heightProperty().addListener(new ChangeListener<Number>() 
        {
            @Override 
            public void changed( ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) 
            {
                resize();
            }
        });
        
        footerPane.addEventHandler( FooterPane.Events.ON_LOGOUT, new EventHandler()
        {
            @Override
            public void handle( Event t ) 
            {
                logout();
            }
        } );
    }
    
    private void logout()
    {
        try
        {
            new LoginView().start( new Stage() );
            ApplicationUtilities.getInstance().logout();
            stage.close();
        }
        catch ( Exception e )
        {
            ApplicationUtilities.logException( e );
        }
    }
    
    private void paintCenter( Object value )
    {
        if( value instanceof AbstractModulesPane )
        {
            centerPane = (AbstractModulesPane) value;
            
            pane.setCenter( centerPane );
            
            headerPane.setModule( centerPane.getName() );
            actionPane.setActions( centerPane.getActions() );
            
            resize();
            pane.requestLayout();
        }
    }
    
    private Stage stage;
    
    private BorderPane pane = new BorderPane();
    private Scene scene = new Scene( pane );
    
    private FooterPane footerPane = new FooterPane();
    private HeaderPane headerPane = new HeaderPane();
    private ActionPane actionPane = new ActionPane();
    private MenuPane menuPane = new MenuPane( new EventHandler() 
    {
        @Override
        public void handle( Event t ) 
        {
            paintCenter( t.getSource() );
        }
    } );
    
    private AbstractModulesPane centerPane;
}
