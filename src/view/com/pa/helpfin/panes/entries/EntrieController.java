package com.pa.helpfin.panes.entries;

import com.pa.helpfin.model.data.Core;
import com.pa.helpfin.view.tables.DefaultTable;

/**
 * @author artur
 * @param <T>
 */
public abstract class EntrieController <T extends Core>
{
    public T getSelectedItem()
    {
        return getTable().getSelectedItem();
    }
    
    public abstract void addItem() throws Exception;
    public abstract void editItem( T item ) throws Exception;
    public abstract void filterItem() throws Exception;
    public abstract void deleteItem( T item ) throws Exception;
    public abstract void refresh();
    
    public abstract String getEntrieName();

    public abstract DefaultTable<T> getTable();
}
