package com.pa.helpfin.model.data;

import java.util.Arrays;
import java.util.List;

/**
 * @author artur
 */
public class PostingCategoryFilter
    extends 
        DefaultFilter
{
    public static final int NAME         = 0;
    public static final int INFO         = 1;
    public static final int POSTING_TYPE = 2;
    public static final int STATE        = 3;

    @Override
    public List<FilterItem> getComponents() 
    {
        return Arrays.asList(new FilterItem( NAME,          "Nome",               "com.pa.helpfin.view.util.MaskTextField" ),
                              new FilterItem( INFO,         "Informações",        "com.pa.helpfin.view.util.MaskTextField" ),
                              new FilterItem( POSTING_TYPE, "Tipo de Lançamento", "com.pa.helpfin.view.selectors.PostingTypeSelector" ),
                              new FilterItem( STATE,        "Situação",           "com.pa.helpfin.view.selectors.StateSelector" ) );
    }
}
