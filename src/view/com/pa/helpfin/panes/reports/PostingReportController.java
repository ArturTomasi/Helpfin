package com.pa.helpfin.panes.reports;

import com.pa.helpfin.control.reports.PostingReport;
import com.pa.helpfin.model.ApplicationUtilities;
import com.pa.helpfin.model.data.DefaultFilter;
import com.pa.helpfin.model.data.Posting;
import com.pa.helpfin.model.data.PostingFilter;
import com.pa.helpfin.view.editor.FilterEditor;
import com.pa.helpfin.view.tables.DefaultTable;
import com.pa.helpfin.view.tables.PostingTable;
import com.pa.helpfin.view.util.EditorCallback;
import java.io.File;
import javafx.event.Event;

/**
 * @author artur
 */
public class PostingReportController
    extends 
        ReportController<Posting>
{
    @Override
    public void configure() throws Exception 
    {
        new FilterEditor( new EditorCallback<DefaultFilter>( filter ) 
        {
            @Override
            public void handle( Event t )
            {
                filter = (PostingFilter)source;
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
                                                .getPostingManager()
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
       PostingReport report = new PostingReport();
       report.setSource( com.pa.helpfin.model.ModuleContext.getInstance().getPostingManager().getValues( filter ) );
       report.generatePDF( file );
    }
    
    

    @Override
    public String getReportName() 
    {
        return "Relatório de Lançamentos";
    }

    @Override
    public DefaultTable<Posting> getTable() 
    {
        return table;
    }
    
    private PostingTable table   = new PostingTable();
    private PostingFilter filter = new PostingFilter();
}
