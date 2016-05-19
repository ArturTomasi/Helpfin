package com.pa.helpfin.panes.reports;

import com.pa.helpfin.control.reports.PostingCategoryReport;
import com.pa.helpfin.model.ApplicationUtilities;
import com.pa.helpfin.model.data.DefaultFilter;
import com.pa.helpfin.model.data.PostingCategory;
import com.pa.helpfin.model.data.PostingCategoryFilter;
import com.pa.helpfin.view.editor.FilterEditor;
import com.pa.helpfin.view.tables.DefaultTable;
import com.pa.helpfin.view.tables.PostingCategoryTable;
import com.pa.helpfin.view.util.EditorCallback;
import java.io.File;
import javafx.event.Event;

/**
 * @author artur
 */
public class PostingCategoryReportController
    extends 
        ReportController<PostingCategory>
{
    @Override
    public void configure() throws Exception 
    {
        new FilterEditor( new EditorCallback<DefaultFilter>( filter ) 
        {
            @Override
            public void handle( Event t )
            {
                filter = (PostingCategoryFilter)source;
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
                                                .getPostingCategoryManager()
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
        PostingCategoryReport report = new PostingCategoryReport();
        
        report.setSource(  com.pa.helpfin.model.ModuleContext
                                            .getInstance()
                                            .getPostingCategoryManager()
                                            .getValues( filter ) );
        
        report.generatePDF( file );
    }
    

    @Override
    public String getReportName() 
    {
        return "Relatório de Tipos de Finalização";
    }

    @Override
    public DefaultTable<PostingCategory> getTable() 
    {
        return table;
    }
    
    private PostingCategoryTable table   = new PostingCategoryTable();
    private PostingCategoryFilter filter = new PostingCategoryFilter();
}
