package com.pa.helpfin.model.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author artur
 */
public abstract class DefaultFilter 
{
    private HashMap<Integer, List<Object>> conditions = new HashMap();
    
    public void addCondition( int id, Object value )
    {
        List values = conditions.get( id );
        
        if( values == null )
            values = new ArrayList();
        
        values.add( value );
        
        conditions.put( id, values );
    }
    
    
    public void clearConditions()
    {
        conditions.clear();
    }
        
    
    
    public HashMap<Integer, List<Object>> getConditions()
    {
        return conditions;
    }
    
    

    public abstract List<FilterItem> getComponents();
    
    
    
    public class FilterItem
    {
        private int id;
        private String name;
        private String component;

        public FilterItem( int id, String name, String component )
        {
            this.id = id;
            this.name = name;
            this.component = component;
        }

        public int getId() 
        {
            return id;
        }

        public void setId( int id )
        {
            this.id = id;
        }
        
        public String getName() 
        {
            return name;
        }

        public void setName( String name )
        {
            this.name = name;
        }

        public String getComponent() 
        {
            return component;
        }

        public void setComponent( String component )
        {
            this.component = component;
        }
        
        @Override
        public String toString() 
        {
            return name;
        }
    }
}
