package com.pa.helpfin.view.selectors;

import com.pa.helpfin.model.data.Option;
import com.pa.helpfin.model.data.User;

/**
 * @author artur
 */
public class GenderSelector
    extends 
        ItemSelector<Option>
{
    public GenderSelector() 
    {
        super( "Selecione um Sexo",
                new Option( User.MALE,   User.GENDER[ User.MALE ] ),
                new Option( User.FEMALE, User.GENDER[ User.FEMALE ] ) );
    }
}
