package com.pa.helpfin.view.selectors;

import com.pa.helpfin.model.data.Option;
import com.pa.helpfin.model.data.User;

/**
 * @author artur
 */
public class RoleSelector 
    extends 
        ItemSelector<Option>
{
    public RoleSelector() 
    {
        super( "Selecione uma Categoria",
                new Option( User.ROLE_ADMIN,    User.ROLE[ User.ROLE_ADMIN ] ),
                new Option( User.ROLE_OPERATOR, User.ROLE[ User.ROLE_OPERATOR ] ) );
    }
}
