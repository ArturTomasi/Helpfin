package com.pa.helpfin.model.db.service;

import com.pa.helpfin.model.data.Core;
import java.util.List;

/**
 * @author artur
 * @param <T>
 */
public interface Manager<T extends Core>
{
    public void addValue( T value ) throws Exception;
    
    public void deleteValue( T value ) throws Exception;
    
    public void updateValue( T value ) throws Exception;
    
    public T getValue( int id ) throws Exception;
    
    public T getValue( String value ) throws Exception;
    
    public List<T> getValues() throws Exception;
    
    public List<T> getValues( boolean showInactives ) throws Exception;
    
    /**
     * isUnique( T Value )
     * 
     * valida se os valores a serem incluidos são unicos
     * retorna uma lista dos atributos que não são unicos
     * 
     * @param value
     * @return List
     * @throws Exception 
     */
    public List<String> isUnique( T value ) throws Exception;
}
