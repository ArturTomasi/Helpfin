package com.pa.helpfin.panes.modules;

import com.pa.helpfin.model.ApplicationUtilities;
import com.pa.helpfin.model.ResourceLocator;
import com.pa.helpfin.model.data.User;
import com.pa.helpfin.panes.entries.EntrieCenterPane;
import com.pa.helpfin.view.util.ActionButton;
import com.pa.helpfin.view.util.Prompts;
import com.sun.javafx.tk.Toolkit;
import java.util.Collections;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

/**
 * @author artur
 */
public class EntriePane 
    extends 
        AbstractModulesPane
{
    public static final int VIEW_EMPTY      = -1;
    public static final int VIEW_USERS      = 0;
    public static final int VIEW_ENTITY     = 1;
    public static final int VIEW_COMPLETION = 2;
    public static final int VIEW_CATEGORY   = 3;
    public static final int VIEW_TYPE       = 4;
    
    private int index = VIEW_EMPTY;
    
    public EntriePane() 
    {
        initComponentes();
    }
    
    
    
    public void show( int index )
    {
        this.index = index;

        if( ApplicationUtilities.getInstance().getActiveUser().getRole() == User.ROLE_OPERATOR 
                && index == VIEW_USERS )
        {
            Prompts.info( "Acesso Negado", "Você não tem permissão de acesso" );
            return;
        }
            
        backLabel.setText( index == VIEW_USERS      ? "Cadastro de Usuários"                  :
                           index == VIEW_ENTITY     ? "Cadastro de Entidades"                 :
                           index == VIEW_COMPLETION ? "Cadastro de Tipos de Finalizações"     :
                           index == VIEW_CATEGORY   ? "Cadastro de Categorias de Lançamentos" :
                           index == VIEW_TYPE       ? "Cadastro de Tipos de Lançamentos"      : "" );
           
        
        getChildren().clear();
        
        if( index != VIEW_EMPTY )
        {
            backLabel.autosize();
            entriePane.setSelectedEntrie( index );
            getChildren().addAll( backBar, entriePane  );
        }
        
        else 
        {
            getChildren().add( view );
        }        
        
        getMenuItem().getOnAction().handle( new ActionEvent() );
    } 
    
    
    
    @Override
    public List<Button> getActions()
    {
        return index != VIEW_EMPTY ? entriePane.getActions() : Collections.EMPTY_LIST;
    }

    
    
    @Override
    public void refreshContent()
    {
        if( index != VIEW_EMPTY )
        {
            entriePane.refreshContent();
        }
    }

    
    
    @Override
    public void resizeComponents( double height, double width )
    {
        view.setPrefSize( width, height );
        
        if( index != VIEW_EMPTY  )
        {
            backBar.setPrefHeight( 35 );
            
            float labelWidth = Toolkit.getToolkit().getFontLoader().computeStringWidth( backLabel.getText(), font );
            
            backBar.setSpacing( width - labelWidth - backButton.getMaxWidth() - 20 /*padding*/ );
            
            entriePane.setLayoutY( 35 );
            entriePane.resizeComponents( height - 35, width );
        }
        
        requestParentLayout();
    }
    
    
    
    private void initComponentes()
    {
        backLabel.setFont( font );
        backLabel.setStyle( "-fx-padding: 5 0 0 10" );
        
        backBar.setStyle( "-fx-background-color: transparent; -fx-padding: 1 10 0 0;" );
        
        backBar.getChildren().addAll( backLabel, backButton );
        
        engine.load( ResourceLocator.getInstance().getWebResource( "entrieList.html" ) );
        
        engine.getLoadWorker().stateProperty().addListener( new ChangeListener<State>() 
        {  
            @Override 
            public void changed( ObservableValue<? extends State> ov, State oldState, State newState )
            {
              if ( newState == State.SUCCEEDED )
                ( (JSObject) engine.executeScript( "window" ) ).setMember( "application", EntriePane.this );
            }
        } );
        
        getChildren().add( view );
    }
    
    private ActionButton backButton = new ActionButton( "Voltar", "back.png", new EventHandler() 
    {
        @Override
        public void handle( Event t )
        {
            show( VIEW_EMPTY );
        }
    } );
    
    private Label backLabel = new Label();
    
    private Font font = Font.font( "cursive", FontWeight.BOLD, FontPosture.ITALIC, 20 ) ;
    
    private HBox backBar = new HBox();
    private WebView view = new WebView();
    private WebEngine engine = view.getEngine();
    
    private EntrieCenterPane entriePane = new EntrieCenterPane();
}