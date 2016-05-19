package com.pa.helpfin.model;

import com.pa.helpfin.model.db.service.*;

/**
 * @author artur
 */
public class ModuleContext 
{
    private static ModuleContext moduleContext;
    
    public static ModuleContext getInstance()
    {
        if( moduleContext == null )
        {
            moduleContext = new ModuleContext();
        }
        
        return  moduleContext;
    }
    
    private ModuleContext()
    {
    }
    
    public UserManagerService getUserManager()
    {
        return UserManagerService.getInstance();
    }

    public PostingTypeManagerService getPostingTypeManager()
    {
        return PostingTypeManagerService.getInstance();
    }

    public PostingCategoryManagerService getPostingCategoryManager()
    {
        return PostingCategoryManagerService.getInstance();
    }

    public EntityManagerService getEntityManager()
    {
        return EntityManagerService.getInstance();
    }

    public CompletionTypeManagerService getCompletionTypeManager()
    {
        return CompletionTypeManagerService.getInstance();
    }
    
    public PostingManagerService getPostingManager()
    {
        return PostingManagerService.getInstance();
    }
    
    public AttachmentManagerService getAttachmentManager()
    {
        return AttachmentManagerService.getInstance();
    }
}
