package com.pa.helpfin.model.data;

import java.util.Arrays;
import java.util.List;

/**
 * @author artur
 */
public class UserFilter 
    extends 
        DefaultFilter
{
    public static final int NAME       = 0;
    public static final int CPF        = 1;
    public static final int ROLE       = 2;
    public static final int PHONE      = 3;
    public static final int MAIL       = 4;
    public static final int RG         = 5;
    public static final int GENDER     = 6;
    public static final int BIRTH_DATE = 7;
    public static final int STATE      = 8;

    @Override
    public List<FilterItem> getComponents() 
    {
        return Arrays.asList( new FilterItem( NAME,       "Nome",              "com.pa.helpfin.view.util.MaskTextField" ),
                              new FilterItem( CPF,        "CPF",               "com.pa.helpfin.view.util.MaskTextField" ),
                              new FilterItem( ROLE,       "Categoria",         "com.pa.helpfin.view.selectors.RoleSelector" ),
                              new FilterItem( PHONE,      "Telefone",          "com.pa.helpfin.view.util.MaskTextField" ),
                              new FilterItem( MAIL,       "Email",             "com.pa.helpfin.view.util.MaskTextField" ),
                              new FilterItem( RG,         "RG",                "com.pa.helpfin.view.util.MaskTextField" ),
                              new FilterItem( GENDER,     "Sexo",              "com.pa.helpfin.view.selectors.GenderSelector" ),
                              new FilterItem( BIRTH_DATE, "Ano de Nascimento", "com.pa.helpfin.view.util.DateBetweenField" ),
                              new FilterItem( STATE,      "Situação",          "com.pa.helpfin.view.selectors.StateSelector" ) );
    }
}
