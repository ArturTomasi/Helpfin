package com.pa.helpfin.panes.modules;

import com.pa.helpfin.control.reports.MonthlyBalanceReport;
import com.pa.helpfin.model.ApplicationUtilities;
import com.pa.helpfin.model.ResourceLocator;
import com.pa.helpfin.panes.reports.ReportCenterPane;
import com.pa.helpfin.view.util.ActionButton;
import com.pa.helpfin.view.util.FileUtilities;
import com.pa.helpfin.view.util.Prompts;
import com.sun.javafx.tk.Toolkit;
import java.io.File;
import java.util.Collections;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
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
public class ReportPane 
    extends 
        AbstractModulesPane
{
    public static final int VIEW_EMPTY      = -1;
    public static final int VIEW_POSTING    = 0;
    public static final int VIEW_USERS      = 1;
    public static final int VIEW_ENTITY     = 2;
    public static final int VIEW_COMPLETION = 3;
    public static final int VIEW_CATEGORY   = 4;
    
    private int index = VIEW_EMPTY;
    
    public ReportPane() 
    {
        initComponentes();
    }
    
    
    
    public void printMonthly()
    {
        File file = FileUtilities.saveFile( "Imprimir Relatório", "HelpFin(" + System.currentTimeMillis() +").pdf" );

        if( file != null )
        {
            Prompts.process( "Gerando Relatório " + file.getName() + "..." , new Task<Void>() 
            {
                @Override
                protected Void call() throws Exception 
                {
                    try
                    {
                        MonthlyBalanceReport report = new MonthlyBalanceReport();
                        report.generatePDF( file );
                    }

                    catch ( Exception e )
                    {
                        ApplicationUtilities.logException( e );
                    }

                    return null;
                }
            } );
        }
    }
    
    
    
    public void show( int index )
    {
        this.index = index;
        
        backLabel.setText( index == VIEW_USERS      ? "Relatório de Usuários"                  :
                           index == VIEW_ENTITY     ? "Relatório de Entidades"                 :
                           index == VIEW_POSTING    ? "Relatório de Lançamentos"               :
                           index == VIEW_COMPLETION ? "Relatório de Tipos de Finalizações"     :
                           index == VIEW_CATEGORY   ? "Relatório de Categorias de Lançamentos" : "" );
           
        
        getChildren().clear();
        
        if( index != VIEW_EMPTY )
        {
            backLabel.autosize();
            reportPane.setSelectedReport( index );
            getChildren().addAll( backBar, reportPane  );
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
        return index != VIEW_EMPTY ? reportPane.getActions() : Collections.EMPTY_LIST;
    }

    
    
    @Override
    public void refreshContent()
    {
        if( index != VIEW_EMPTY )
        {
            reportPane.refreshContent();
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
            
            reportPane.setLayoutY( 35 );
            reportPane.resizeComponents( height - 35, width );
        }
        
        requestParentLayout();
    }
    
    
    
    private void initComponentes()
    {
        backLabel.setFont( font );
        backLabel.setStyle( "-fx-padding: 5 0 0 10" );
        
        backBar.setStyle( "-fx-background-color: transparent; -fx-padding: 1 10 0 0;" );
        
        backBar.getChildren().addAll( backLabel, backButton );
        
        engine.load( ResourceLocator.getInstance().getWebResource( "reportList.html" ) );
        
        engine.getLoadWorker().stateProperty().addListener( new ChangeListener<State>() 
        {  
            @Override 
            public void changed( ObservableValue<? extends State> ov, State oldState, State newState )
            {
              if ( newState == State.SUCCEEDED )
                ( (JSObject) engine.executeScript( "window" ) ).setMember( "application", ReportPane.this );
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
    
    private ReportCenterPane reportPane = new ReportCenterPane();
}
