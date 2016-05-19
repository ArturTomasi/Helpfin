package com.pa.helpfin.view.inspectors;

import com.pa.helpfin.control.reports.PostingDetailsReport;
import com.pa.helpfin.model.ApplicationUtilities;
import com.pa.helpfin.model.ResourceLocator;
import com.pa.helpfin.model.data.Posting;
import com.pa.helpfin.view.util.FileUtilities;
import com.pa.helpfin.view.util.Prompts;
import java.io.File;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.Worker.State;
import javafx.scene.control.Tab;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

/**
 * @author artur
 */
public class PostingDetails 
    extends 
        Tab
{
    private Posting posting;
    
    public PostingDetails() 
    {
        initComponents();
    }
    
    public void setSource( Posting posting )
    {
        this.posting = posting;
        refreshContent();
    }
    
    private void refreshContent()
    {
        try 
        {
            if( posting != null )
            {
                engine.executeScript( "setPosting( " + posting.toJSON() + " )" );
            }
        }

        catch ( Exception e) { /*ignore*/ }
                
    }
    
    public void print( String html )
    {
        try
        {
            File file = FileUtilities.saveFile( "Imprimir Relatório", "HelpFin(" + System.currentTimeMillis() +").pdf" );

            if( file != null && ! file.exists() )
            {
                Prompts.process("Gerando Relatório " + file.getName() + "..." , new Task<Void>() 
                {
                    @Override
                    protected Void call() throws Exception 
                    {
                        try
                        {
                            PostingDetailsReport report = new PostingDetailsReport();
                            report.setSource( posting );
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
        
        catch ( Exception e )
        {
            ApplicationUtilities.logException( e );
        }
    }
    
    private void initComponents()
    {
        setClosable( false );
        setText( "Detalhes" );
        
        engine.load( ResourceLocator.getInstance().getWebResource( "inspect.html" ) );
        engine.setJavaScriptEnabled( true );
        engine.getLoadWorker().stateProperty().addListener( new ChangeListener<State>() 
        {  
            @Override 
            public void changed( ObservableValue<? extends State> ov, State oldState, State newState )
            {
              if ( newState == State.SUCCEEDED )
                ( (JSObject) engine.executeScript( "window" ) ).setMember( "application", PostingDetails.this );
            }
        } );
        
        engine.documentProperty().addListener( ( prop, oldDoc, newDoc ) -> refreshContent() );
        
        setContent( webView );
    }
    
    private WebView webView = new WebView();
    private WebEngine engine = webView.getEngine();
}
