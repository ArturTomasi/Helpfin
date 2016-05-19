package com.pa.helpfin.view.inspectors;

import com.pa.helpfin.model.ResourceLocator;
import static com.pa.helpfin.model.data.PostingType.TYPE_COST;
import static com.pa.helpfin.model.data.PostingType.TYPE_REVENUE;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.scene.control.Tab;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

/**
 * @author artur
 */
public class DrilldownPostings 
    extends 
        Tab
{
    private String serie;
    private String drilldown;
    
    public DrilldownPostings() 
    {
        initComponents();
    }
    
    public void refreshContent()
    {
        try 
        {
            makeJsonTypes();
            
            engine.executeScript( "setSerie( " + serie + " ); " );
            engine.executeScript( "setDrilldown( " + drilldown +  " );" );
            engine.executeScript( "drilldown();" );
        }

        catch ( Exception e) { e.printStackTrace();/*ignore*/ }
    }
    
    private void makeJsonTypes() throws Exception
    {
        int cost = com.pa.helpfin.model.ModuleContext.getInstance().getPostingManager().countPostingType( TYPE_COST );

        int revenue = com.pa.helpfin.model.ModuleContext.getInstance().getPostingManager().countPostingType( TYPE_REVENUE );

        serie = "[ { name: 'Despesa', y: " +   cost  + ", color: 'red', drilldown: 'cost' }, " +
                "  { name: 'Receita', y: " + revenue + ", color: 'green', drilldown: 'revenue' } ]";
        
        String data = com.pa.helpfin.model.ModuleContext.getInstance().getPostingCategoryManager().getDrilldown( TYPE_COST );
        drilldown = "[ { id: 'cost', name: 'Despesa'," + " data : [ " + data + " ] " + "}";
        
        data = com.pa.helpfin.model.ModuleContext.getInstance().getPostingCategoryManager().getDrilldown( TYPE_REVENUE );
        drilldown += ", { id: 'revenue', name: 'Receita'," + " data : [ " + data + " ] " + "} ";
        
        drilldown += ", " + com.pa.helpfin.model.ModuleContext.getInstance().getPostingManager().getDrilldown() + " ]";
    }
    
    private void initComponents()
    {
        setClosable( false );
        setText( "Resumo" );
        
        engine.load( ResourceLocator.getInstance().getWebResource( "drilldownHelpFin.html" ) );
        engine.setJavaScriptEnabled( true );
        engine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() 
        {  
            @Override 
            public void changed( ObservableValue<? extends Worker.State> ov, Worker.State oldState, Worker.State newState )
            {
              if ( newState == Worker.State.SUCCEEDED )
                ( (JSObject) engine.executeScript( "window" ) ).setMember("application", DrilldownPostings.this );
            }
        } );
        
        engine.documentProperty().addListener( ( prop, oldDoc, newDoc ) -> refreshContent() );
        
        setContent( webView );
    }
    
    private WebView webView = new WebView();
    private WebEngine engine = webView.getEngine();
    
}
