package com.pa.helpfin.model.data;

import java.sql.Date;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * @author artur
 */
public class PostingFilter 
    extends 
        DefaultFilter
{
    public static final int NAME             = 0;
    public static final int INFO             = 1;
    public static final int REAL_DATE        = 2;
    public static final int ESTIMATE_DATE    = 3;
    public static final int REAL_VALUE       = 4;
    public static final int ESTIMATE_VALUE   = 5;
    public static final int PORTION          = 6;
    public static final int PORTION_TOTAL    = 7;
    public static final int COMPLETION_AUTO  = 8;
    public static final int REPEAT           = 9;
    public static final int POSTING_CATEGORY = 10;
    public static final int USER             = 11;
    public static final int COMPLETION_TYPE  = 12;
    public static final int ENTITY           = 13;
    public static final int TYPE             = 14;
    public static final int STATE            = 15;
    public static final int DEADLINE         = 16;
    public static final int IN_BUDGET        = 17;

    public void loadDefaultFilter()
    {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis( System.currentTimeMillis() );
        cal.set( Calendar.DAY_OF_MONTH, cal.getActualMinimum( Calendar.DAY_OF_MONTH ) );
        
        Date from = new Date( cal.getTimeInMillis() );
        
        cal.add( Calendar.MONTH, 1 );
        cal.set( Calendar.DAY_OF_MONTH, cal.getActualMaximum( Calendar.DAY_OF_MONTH ) );
        
        Date until = new Date( cal.getTimeInMillis() );
        
        addCondition( PostingFilter.ESTIMATE_DATE, new Date[]{ from, until } );
        addCondition( PostingFilter.STATE, new Option( Posting.STATE_REGISTRED, Posting.STATES[ Posting.STATE_REGISTRED ] ) ); 
        addCondition( PostingFilter.STATE, new Option( Posting.STATE_PROGRESS, Posting.STATES[ Posting.STATE_PROGRESS ] ) ); 
        addCondition( PostingFilter.STATE, new Option( Posting.STATE_FINISHED, Posting.STATES[ Posting.STATE_FINISHED ] ) ); 
    }
    
    @Override
    public List<FilterItem> getComponents() 
    {
        return Arrays.asList( new FilterItem( NAME,             "Nome",                    "com.pa.helpfin.view.util.MaskTextField" ),
                              new FilterItem( INFO,             "Informações",             "com.pa.helpfin.view.util.MaskTextField" ),
                              new FilterItem( REAL_DATE,        "Data Real",               "com.pa.helpfin.view.util.DateBetweenField" ),
                              new FilterItem( ESTIMATE_DATE,    "Data Estimada",           "com.pa.helpfin.view.util.DateBetweenField" ),
                              new FilterItem( REAL_VALUE,       "Valor Real",              "com.pa.helpfin.view.util.NumberTextField" ),
                              new FilterItem( ESTIMATE_VALUE,   "Valor Estimado",          "com.pa.helpfin.view.util.NumberTextField" ),
                              new FilterItem( PORTION,          "Parcela",                 "com.pa.helpfin.view.util.NumberTextField" ),
                              new FilterItem( PORTION_TOTAL,    "Total de Parcela",        "com.pa.helpfin.view.util.NumberTextField" ),
                              new FilterItem( COMPLETION_AUTO,  "Finaliza Automáticamente","com.pa.helpfin.view.selectors.YesNoSelector" ),
                              new FilterItem( REPEAT,           "Repete",                  "com.pa.helpfin.view.selectors.YesNoSelector" ),
                              new FilterItem( POSTING_CATEGORY, "Categoria de Lançamento", "com.pa.helpfin.view.selectors.PostingCategorySelector" ),
                              new FilterItem( USER,             "Responsável",             "com.pa.helpfin.view.selectors.UserSelector" ),
                              new FilterItem( COMPLETION_TYPE,  "Tipo de Finalização",     "com.pa.helpfin.view.selectors.CompletionTypeSelector" ),
                              new FilterItem( ENTITY,           "Entidade",                "com.pa.helpfin.view.selectors.EntitySelector" ),
                              new FilterItem( TYPE,             "Tipo",                    "com.pa.helpfin.view.selectors.PostingTypeSelector" ),
                              new FilterItem( STATE,            "Situação",                "com.pa.helpfin.view.selectors.PostingStateSelector" ),
                              new FilterItem( DEADLINE,         "Dentro do Prazo",         "com.pa.helpfin.view.selectors.YesNoSelector" ),
                              new FilterItem( IN_BUDGET,        "Dentro do Orçamento",     "com.pa.helpfin.view.selectors.YesNoSelector" )
                              );
    }
    
}
