package com.pa.helpfin.control;

import com.pa.helpfin.model.ApplicationUtilities;
import com.pa.helpfin.model.data.Attachment;
import com.pa.helpfin.model.data.Posting;
import com.pa.helpfin.model.data.User;
import java.util.List;
import java.util.Properties;

/**
 * @author artur
 */
public class PostingController 
{
    private static PostingController instance;
    
    private PostingController(){}
    
    public static PostingController getInstance()
    {
        if( instance == null )
        {
            instance = new PostingController();
        }
        
        return instance;
    }
    
    public void addPosting( Posting posting, Properties properties )
    {
        try
        {
            List<Posting> portions = (List)properties.get( "Postings" );
            
            if( posting.getId() == 0 )
            {
                com.pa.helpfin.model.ModuleContext.getInstance().getPostingManager().addValue( posting );
                
                if( posting.isRepeat() )
                {
                    for ( Posting p : portions )
                    {
                        p.setPosting( posting.getId() );
                     
                        com.pa.helpfin.model.ModuleContext.getInstance().getPostingManager().addValue( p );
                    }
                }
            }
            
            List<Attachment> attachments = (List)properties.get( "Attachments" );
            
            for ( Attachment attachment : attachments )
            {
                attachment.setPosting( posting.getId() );
                
                com.pa.helpfin.model.ModuleContext
                                        .getInstance()
                                        .getAttachmentManager()
                                        .addValue( attachment );
            }
        }
        
        catch ( Exception e )
        {
            ApplicationUtilities.logException( e );
        }
    }
    
    
    
    public void editPosting( Posting posting, Properties properties )
    {
        try
        {
            com.pa.helpfin.model.ModuleContext.getInstance()
                                            .getPostingManager()
                                            .updateValue( posting );
            
            List<Attachment> attachments = (List)properties.get( "Attachments" );
            
            for ( Attachment attachment : attachments )
            {
                attachment.setPosting( posting.getId() );
                
                if( attachment.getId() == 0 )
                {
                    com.pa.helpfin.model.ModuleContext
                                        .getInstance()
                                        .getAttachmentManager()
                                        .addValue( attachment );
                }
                else
                {
                    com.pa.helpfin.model.ModuleContext
                                        .getInstance()
                                        .getAttachmentManager()
                                        .updateValue( attachment );
                }
            }
            
            if( posting.isRepeat() && posting.getPortion() < posting.getPortionTotal() &&
                    posting.getState() == Posting.STATE_FINISHED )
            {
                com.pa.helpfin.model.ModuleContext
                        .getInstance()
                        .getPostingManager()
                        .updateNextPortion( posting );
            }
        }
        
        catch ( Exception e )
        {
            ApplicationUtilities.logException( e );
        }
    }
    
    
    public void finishPosting( Posting posting )
    {
        try
        {
            com.pa.helpfin.model.ModuleContext.getInstance()
                                            .getPostingManager()
                                            .updateValue( posting );
            
            if( posting.isRepeat() && posting.getPortion() < posting.getPortionTotal() )
            {
                com.pa.helpfin.model.ModuleContext
                        .getInstance()
                        .getPostingManager()
                        .updateNextPortion( posting );
            }
            
        }
        
        catch ( Exception e )
        {
            ApplicationUtilities.logException( e );
        }
    }
    
    
    
    public void deletePosting( Posting posting )
    {
        try
        {
            com.pa.helpfin.model.ModuleContext.getInstance()
                                            .getPostingManager()
                                            .deleteValue( posting );
            
            if( posting.isRepeat() && posting.getPortion() < posting.getPortionTotal() )
            {
               com.pa.helpfin.model.ModuleContext.getInstance()
                                            .getPostingManager()
                                            .deleteNextPortions( posting );
            }
        }
        
        catch ( Exception e )
        {
            ApplicationUtilities.logException( e );
        }
    }

    
    public void reversePosting( Posting posting )
    {
        try
        {
            if( posting.getState() == Posting.STATE_FINISHED )
            {
                posting.setState( Posting.STATE_PROGRESS );
                posting.setRealDate( null );
                posting.setRealValue( 0d );
                
                if( posting.isCompletionAuto() )
                        posting.setCompletionType( 0 );
                    
                com.pa.helpfin.model.ModuleContext.getInstance().getPostingManager().updateValue( posting );

                if( posting.isRepeat() && posting.getPortion() < posting.getPortionTotal() )
                {
                    com.pa.helpfin.model.ModuleContext.getInstance().getPostingManager().updateNextPortion( posting );
                }
            }
        }
        
        catch( Exception e )
        {
            ApplicationUtilities.logException( e );
        }
    }
    
    
    
    public int makeState( Posting posting )
    {
        int newState = Posting.STATE_REGISTRED;
        
        try
        {
            if( posting.getPortion() > 1 )
            {
                Posting last = com.pa.helpfin.model.ModuleContext.getInstance().getPostingManager().getLastPosting( posting );
                
                if( last != null && last.getState() == Posting.STATE_FINISHED )
                {
                    newState = Posting.STATE_PROGRESS;
                }
            }

            else
            {
                newState = posting.getRealDate() != null ? Posting.STATE_FINISHED : Posting.STATE_PROGRESS;
            }
        }
        
        catch ( Exception e )
        {
            ApplicationUtilities.logException( e );
        }
        
        return newState;
    }
    
    
    public String validateFinish( Posting posting )
    {
        if( posting == null )
            return "Selecione um Lançamento para finalizar !";
        
        if( posting.getState() == Posting.STATE_DELETED )
            return "Lançamento está deletado,\n não é possivel finaliza-lo !";
        
        if( posting.getState() == Posting.STATE_FINISHED )
            return "Lançamento está finalizado,\n não é possivel finaliza-lo duas vezes !";
        
        if( posting.getState() != Posting.STATE_PROGRESS && posting.getPortionTotal() > 1 )
            return "Lançamento não está correte,\n não é possivel finaliza-lo antes das outras parcelas!";
        
        return null;
    }
    
    
    
    public String validateEdit( Posting posting )
    {
        if( posting == null )
            return "Selecione um Lançamento para editar !";
        
        if( posting.getState() == Posting.STATE_DELETED )
            return "Lançamento está deletado,\n não é possivel edita-lo !";
        
        if( posting.getState() == Posting.STATE_FINISHED )
            return "Lançamento está finalizado,\n não é possivel edita-lo após finalizado!";
        
        return null;
    }
    
    
    
    public String validateDelete( Posting posting )
    {
        if( posting == null )
            return "Selecione um Lançamento para excluir !";
        
        if( posting.getState() == Posting.STATE_DELETED )
            return "Lançamento está excluido,\n não é possivel exclui-lo duas vezes !";
        
        if( posting.getState() == Posting.STATE_FINISHED )
            return "Lançamento está finalizado,\n não é possivel excluir após finalizado!";
        
        if( ApplicationUtilities.getInstance().getActiveUser().getRole() == User.ROLE_OPERATOR 
                && ApplicationUtilities.getInstance().getActiveUser().getId() != posting.getUser() )
            return "Você não tem permissão para excluir esse lançamento";
        
        
        return null;
    }
    
    
    
    public String validateReserve( Posting posting )
    {
        if( posting == null )
            return "Selecione um Lançamento para extornar!";
        
        if( posting.getState() == Posting.STATE_DELETED )
            return "Lançamento está excluido,\n não é possivel extorná-lo!";
        
        if( posting.getState() != Posting.STATE_FINISHED )
            return "Lançamento deve estár finalizado para extornar";
        
        if( ! ApplicationUtilities.getInstance().hasPermission() )
            return "Você não tem permissão para extornar esse lançamento";
        
        return null;
    }
}
