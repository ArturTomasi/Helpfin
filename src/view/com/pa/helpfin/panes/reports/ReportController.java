package com.pa.helpfin.panes.reports;

import com.pa.helpfin.view.tables.DefaultTable;
import java.io.File;

/**
 * @author artur
 * @param <T>
 */
public abstract class ReportController<T>
{
    public T getSelectedItem()
    {
        return getTable().getSelectedItem();
    }
    
    public abstract void configure() throws Exception;
    public abstract void print( File file ) throws Exception;
    public abstract void refresh() throws Exception;
    
    public abstract String getReportName();

    public abstract DefaultTable<T> getTable();    
}
