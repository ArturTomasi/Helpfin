package com.pa.helpfin.panes.reports;

import com.pa.helpfin.control.reports.EntityReport;
import com.pa.helpfin.model.ApplicationUtilities;
import com.pa.helpfin.model.data.DefaultFilter;
import com.pa.helpfin.model.data.Entity;
import com.pa.helpfin.model.data.EntityFilter;
import com.pa.helpfin.view.editor.FilterEditor;
import com.pa.helpfin.view.tables.DefaultTable;
import com.pa.helpfin.view.tables.EntityTable;
import com.pa.helpfin.view.util.EditorCallback;
import java.io.File;
import javafx.event.Event;

/**
 * @author artur
 */
public class EntityReportController
    extends 
        ReportController<Entity>
{
    @Override
    public void configure() throws Exception 
    {
        new FilterEditor( new EditorCallback<DefaultFilter>( filter ) 
        {
            @Override
            public void handle( Event t )
            {
                filter = (EntityFilter)source;
                refresh();
            }
        } ).open();
    }

    
    
    @Override
    public void refresh()
    {
        try
        {
            table.setItems( com.pa.helpfin.model.ModuleContext
                                                .getInstance()
                                                .getEntityManager()
                                                .getValues( filter ) );
        }
        catch ( Exception e )
        {
            ApplicationUtilities.logException( e );
        }            
    }

    
    
    @Override
    public void print( File file ) throws Exception 
    {
        EntityReport report = new EntityReport();
        report.setSource( com.pa.helpfin.model.ModuleContext
                                                .getInstance()
                                                .getEntityManager()
                                                .getValues( filter ) );
        report.generatePDF( file );
    }

    
    
    @Override
    public String getReportName() 
    {
        return "Relatório de Entidades";
    }
    
    

    @Override
    public DefaultTable<Entity> getTable() 
    {
        return table;
    }
    
    private EntityTable table   = new EntityTable();
    private EntityFilter filter = new EntityFilter();
}
