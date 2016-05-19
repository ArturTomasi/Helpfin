package com.pa.helpfin.panes.reports;

import com.pa.helpfin.control.reports.UserReport;
import com.pa.helpfin.model.ApplicationUtilities;
import com.pa.helpfin.model.data.DefaultFilter;
import com.pa.helpfin.model.data.User;
import com.pa.helpfin.model.data.UserFilter;
import com.pa.helpfin.view.editor.FilterEditor;
import com.pa.helpfin.view.tables.DefaultTable;
import com.pa.helpfin.view.tables.UserTable;
import com.pa.helpfin.view.util.EditorCallback;
import java.io.File;
import javafx.event.Event;

/**
 * @author artur
 */
public class UserReportController
    extends 
        ReportController<User>
{
    @Override
    public void configure() throws Exception 
    {
        new FilterEditor( new EditorCallback<DefaultFilter>( filter ) 
        {
            @Override
            public void handle( Event t )
            {
                filter = (UserFilter)source;
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
                                                .getUserManager()
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
        UserReport report = new UserReport();
        report.setSource( com.pa.helpfin.model.ModuleContext
                                                .getInstance()
                                                .getUserManager()
                                                .getValues( filter ) );
        report.generatePDF( file );
    }
    
    
    

    @Override
    public String getReportName() 
    {
        return "Relatório de Usuários";
    }

    @Override
    public DefaultTable<User> getTable() 
    {
        return table;
    }
    
    private UserTable table   = new UserTable();
    private UserFilter filter = new UserFilter();
}
