package com.pa.helpfin.view.inspectors;

import com.pa.helpfin.model.ResourceLocator;
import com.pa.helpfin.model.data.PostingCategory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.scene.control.Tab;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

/**
 * @author artur
 */
public class PostingCategoryDetails 
    extends 
        Tab
{
    private PostingCategory category;
    
    public PostingCategoryDetails() 
    {
        initComponents();
    }
    
    public void setSource( PostingCategory category )
    {
        this.category = category;
        refreshContent();
    }
    
    private void refreshContent()
    {
        try 
        {
            if( category != null )
            {
                engine.executeScript( "setCategory( " + category.toJSON() + " )" );
            }
        }

        catch ( Exception e) { /*ignore*/ }
                
    }
    
    private void initComponents()
    {
        setClosable( false );
        setText( "Resumo" );
        
        engine.load( ResourceLocator.getInstance().getWebResource( "resumeCategory.html" ) );
        engine.setJavaScriptEnabled( true );
        engine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() 
        {  
            @Override 
            public void changed( ObservableValue<? extends State> ov, State oldState, State newState )
            {
              if ( newState == State.SUCCEEDED )
                ( (JSObject) engine.executeScript( "window" ) ).setMember("application", PostingCategoryDetails.this );
            }
        } );
        
        engine.documentProperty().addListener( ( prop, oldDoc, newDoc ) -> refreshContent() );
        
        setContent( webView );
    }
    
    private WebView webView = new WebView();
    private WebEngine engine = webView.getEngine();
}
