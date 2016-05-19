package com.pa.helpfin.view.selectors;

import com.pa.helpfin.model.data.CompletionType;
import com.pa.helpfin.model.data.Option;

/**
 * @author artur
 */
public class TypeCompletionSelector 
    extends 
        ItemSelector<Option>
{
    public TypeCompletionSelector() 
    {
        super( "Selecione uma tipo", 
               new Option( CompletionType.IN_CASH,      CompletionType.TYPES[ CompletionType.IN_CASH      ] ),
               new Option( CompletionType.BILLET,       CompletionType.TYPES[ CompletionType.BILLET       ] ),
               new Option( CompletionType.CARD,         CompletionType.TYPES[ CompletionType.CARD         ] ),
               new Option( CompletionType.BANK_ACCOUNT, CompletionType.TYPES[ CompletionType.BANK_ACCOUNT ] ),
               new Option( CompletionType.OTHER,        CompletionType.TYPES[ CompletionType.OTHER        ] ) );
    }
}
