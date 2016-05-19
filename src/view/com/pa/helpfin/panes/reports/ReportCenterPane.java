package com.pa.helpfin.panes.reports;

import com.pa.helpfin.model.ApplicationUtilities;
import com.pa.helpfin.model.data.Core;
import com.pa.helpfin.model.data.Posting;
import com.pa.helpfin.panes.LegendPane;
import com.pa.helpfin.panes.modules.AbstractModulesPane;
import com.pa.helpfin.panes.modules.ReportPane;
import com.pa.helpfin.view.util.ActionButton;
import com.pa.helpfin.view.util.FileUtilities;
import com.pa.helpfin.view.util.Prompts;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/**
 * @author artur
 */
public class ReportCenterPane 
    extends 
        AbstractModulesPane
{
    public ReportCenterPane() 
    {
        initComponents();
    }
    
    
    public void setSelectedReport( int index )
    {
        switch( index )
        {
            case ReportPane.VIEW_USERS:
            {
                controller = userReportController;
            }
            break;
                
            case ReportPane.VIEW_ENTITY:
            {
                controller = entityReportController;
            }
            break;
                
            case ReportPane.VIEW_COMPLETION:
            {
                controller = completionTypeReportController;
            }
            break;
                
            case ReportPane.VIEW_CATEGORY:
            {
                controller = postingTypeReportController;
            }
            break;
                
            case ReportPane.VIEW_POSTING:
            {
                controller = postingReportController;
                
            }
            break;
        }
        
        legendPane.getChildren().clear();
        
        if( index == ReportPane.VIEW_POSTING )
        {
            legendPane.addItems( new LegendPane.LegendItem( Posting.STATES[ Posting.STATE_REGISTRED ], "new.png"    ),
                                 new LegendPane.LegendItem( Posting.STATES[ Posting.STATE_PROGRESS ],  "play.png"   ),
                                 new LegendPane.LegendItem( Posting.STATES[ Posting.STATE_FINISHED ],  "finish.png" ),
                                 new LegendPane.LegendItem( Posting.STATES[ Posting.STATE_DELETED ],   "delete.png" ) );
        }
        else
        {
            legendPane.addItems( new LegendPane.LegendItem( Core.STATES[ Core.STATE_ACTIVE ],   "finish.png" ),
                                 new LegendPane.LegendItem( Core.STATES[ Core.STATE_INACTIVE ], "delete.png" ) );
        }
            
    }
    
    
    @Override
    public List<Button> getActions() 
    {
        return Arrays.asList( configureItem, printItem );
    }

    
    
    @Override
    public void refreshContent() 
    {
        try
        {
            controller.refresh();
        }
        catch( Exception e )
        {
            ApplicationUtilities.logException( e );
        }
    }

    
    
    @Override
    public void resizeComponents( double height, double width )
    {
        getChildren().clear();
        getChildren().addAll( controller.getTable(), legendPane );
        
        legendPane.setPrefWidth( width );
        legendPane.setLayoutY( height - legendPane.getHeight() );
        
        controller.getTable().setPrefSize( width, height - legendPane.getHeight() );
        
        requestLayout();
    }
    
    
    
    private void configure()
    {
        try
        {
            controller.configure();
        }
        
        catch ( Exception e )
        {
            ApplicationUtilities.logException( e );
        }
    }
    
    
    
    private void print()
    {
        try
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
                            controller.print( file );
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
        legendPane.addItems( new LegendPane.LegendItem( Core.STATES[ Core.STATE_ACTIVE ],   "finish.png" ),
                             new LegendPane.LegendItem( Core.STATES[ Core.STATE_INACTIVE ], "delete.png" ) );
        
        getChildren().addAll( controller.getTable(), legendPane );
    }
    
    
    
    private ActionButton configureItem = new ActionButton( "Filtro", "search.png", new EventHandler<Event>() 
    {
        @Override
        public void handle( Event t ) 
        {
            configure();
        }
    } );
    
    private ActionButton printItem = new ActionButton( "Imprimir", "pdf.png", new EventHandler<Event>() 
    {
        @Override
        public void handle( Event t ) 
        {
            print();
        }
    } );
    
    private LegendPane legendPane =  new LegendPane();
    
    private PostingReportController postingReportController               = new PostingReportController();
    private UserReportController userReportController                     = new UserReportController();
    private EntityReportController entityReportController                 = new EntityReportController();
    private CompletionTypeReportController completionTypeReportController = new CompletionTypeReportController();
    private PostingCategoryReportController postingTypeReportController       = new PostingCategoryReportController();
    
    private ReportController controller = postingReportController;
}
