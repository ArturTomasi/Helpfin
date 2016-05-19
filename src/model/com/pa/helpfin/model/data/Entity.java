package com.pa.helpfin.model.data;

/**
 * @author artur
 */
public class Entity 
    extends
        Core<Entity>
{
    private String name = "";
    private String compannyName = "";
    private String cnpj = "";
    private String phone = "";
    private String mail = "";

    public Entity() 
    {
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getCompannyName() 
    {
        return compannyName;
    }

    public void setCompannyName( String compannyName )
    {
        this.compannyName = compannyName;
    }

    public String getCnpj() 
    {
        return cnpj;
    }

    public void setCnpj( String cnpj )
    {
        this.cnpj = cnpj;
    }

    public String getPhone() 
    {
        return phone;
    }

    public void setPhone( String phone )
    {
        this.phone = phone;
    }

    public String getMail() 
    {
        return mail;
    }

    public void setMail( String mail )
    {
        this.mail = mail;
    }

    @Override
    public String toString() 
    {
        return name;
    }
}
