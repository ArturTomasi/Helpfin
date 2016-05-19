package com.pa.helpfin.panes.reports;

import com.pa.helpfin.control.reports.CompletionTypeReport;
import com.pa.helpfin.model.ApplicationUtilities;
import com.pa.helpfin.model.data.CompletionType;
import com.pa.helpfin.model.data.CompletionTypeFilter;
import com.pa.helpfin.model.data.DefaultFilter;
import com.pa.helpfin.view.editor.FilterEditor;
import com.pa.helpfin.view.tables.CompletionTypeTable;
import com.pa.helpfin.view.tables.DefaultTable;
import com.pa.helpfin.view.util.EditorCallback;
import java.io.File;
import javafx.event.Event;

/**
 * @author artur
 */
public class CompletionTypeReportController
    extends 
        ReportController<CompletionType>
{
    @Override
    public void configure() throws Exception 
    {
        new FilterEditor( new EditorCallback<DefaultFilter>( filter ) 
        {
            @Override
            public void handle( Event t )
            {
                filter = (CompletionTypeFilter)source;
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
                                                .getCompletionTypeManager()
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
        CompletionTypeReport report = new CompletionTypeReport();
        report.setSource( com.pa.helpfin.model.ModuleContext
                                                .getInstance()
                                                .getCompletionTypeManager()
                                                .getValues( filter ) );
        report.generatePDF( file );
    }

    
    
    
    @Override
    public String getReportName() 
    {
        return "Relatório de Tipos de Finalização";
    }

    @Override
    public DefaultTable<CompletionType> getTable() 
    {
        return table;
    }
    
    private CompletionTypeTable table   = new CompletionTypeTable();
    private CompletionTypeFilter filter = new CompletionTypeFilter();
}
