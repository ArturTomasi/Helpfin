package com.pa.helpfin.model.data;

import java.util.Arrays;
import java.util.List;

/**
 * @author artur
 */
public class EntityFilter
    extends 
        DefaultFilter
{
    public static final int NAME          = 0;
    public static final int CNPJ          = 1;
    public static final int COMPANNY_NAME = 2;
    public static final int PHONE         = 3;
    public static final int MAIL          = 4;
    public static final int STATE         = 5;

    @Override
    public List<FilterItem> getComponents() 
    {
        return Arrays.asList( new FilterItem( NAME,         "Nome",          "com.pa.helpfin.view.util.MaskTextField" ),
                              new FilterItem( CNPJ,         "CNPJ",          "com.pa.helpfin.view.util.MaskTextField" ),
                              new FilterItem( COMPANNY_NAME,"Razão Social",  "com.pa.helpfin.view.util.MaskTextField" ),
                              new FilterItem( PHONE,        "Telefone",      "com.pa.helpfin.view.util.MaskTextField" ),
                              new FilterItem( MAIL,         "Email",         "com.pa.helpfin.view.util.MaskTextField" ),
                              new FilterItem( STATE,        "Situação",      "com.pa.helpfin.view.selectors.StateSelector" ) );
    }
}
