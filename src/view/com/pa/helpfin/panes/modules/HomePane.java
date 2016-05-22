package com.pa.helpfin.panes.modules;

import com.pa.helpfin.model.ResourceLocator;
import com.pa.helpfin.model.data.PostingType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.scene.control.Button;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

/**
 * @author artur
 */
public class HomePane 
    extends 
        AbstractModulesPane
{
    public HomePane() 
    {
        initComponents();
    }

    @Override
    public List<Button> getActions() 
    {
        return Collections.EMPTY_LIST;
    }

    @Override
    public void refreshContent()
    {
        loadData();
    }

    @Override
    public void resizeComponents(double height, double width) 
    {
        view.setPrefSize( width, height );
    }
    
    private void loadData()
    {
        try
        {
            Map<Integer, BigDecimal> map;
            double sum = 0d;
            List<Double> values = new ArrayList();
            List<String> categories = new ArrayList();

            map = com.pa.helpfin.model.ModuleContext.getInstance().getPostingManager().postingsOfMonths( PostingType.TYPE_REVENUE );
            
            SortedSet<Integer> keys = new TreeSet( map.keySet());
            
            for ( Integer key : keys )
            { 
                categories.add( "'Dia: " + key + "'" );
                values.add( sum += ( (BigDecimal)map.get( key ) ).doubleValue() );
            }
            
            engine.executeScript( "setRevenueData( " + values.toString() +" );" ) ;
            
            map = com.pa.helpfin.model.ModuleContext.getInstance().getPostingManager().postingsOfMonths( PostingType.TYPE_COST );
            sum = 0d;
            values.clear();
            
            keys = new TreeSet( map.keySet());
            
            if( categories.size() < keys.size() )
                categories.clear();
            
            for ( Integer key : keys )
            { 
                if( categories.size() < keys.size() )
                        categories.add( "'Dia: " + key + "'" );
                
                values.add( sum += ( (BigDecimal)map.get( key ) ).doubleValue() );
            }
            
            engine.executeScript( "setCostData( " + values.toString() +" );" ) ;
            engine.executeScript( "setCategories( " +  categories.toString() +" );" ) ;
            engine.executeScript( "refreshGraphic();" ) ;
        }
        catch ( Exception e ) { /*ignore*/}
        
    }
    
    private void initComponents()
    {
        engine.load( ResourceLocator.getInstance().getWebResource( "chart.html" ) );
        engine.setJavaScriptEnabled( true );
        engine.getLoadWorker().stateProperty().addListener( new ChangeListener<State>() 
        {  
            @Override 
            public void changed( ObservableValue<? extends State> ov, State oldState, State newState )
            {
              if ( newState == State.SUCCEEDED )
                ( (JSObject) engine.executeScript( "window" ) ).setMember( "application", HomePane.this );
            }
        } );
        
        engine.documentProperty().addListener( ( prop, oldDoc, newDoc ) -> loadData() );
        
        getChildren().add( view );
    }
   
    private WebView view = new WebView();
    private WebEngine engine = view.getEngine();
}
