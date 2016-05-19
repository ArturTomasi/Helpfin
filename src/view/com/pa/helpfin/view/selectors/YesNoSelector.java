package com.pa.helpfin.view.selectors;

import com.pa.helpfin.model.data.Option;

/**
 * @author artur
 */
public class YesNoSelector
    extends 
        ItemSelector<Option>
{
    public YesNoSelector() 
    {
        super( "Seletor de condição" );
        
        setItems( new Option( 1, "Sim" ), new Option( 0, "Não" ) );
    }
}
