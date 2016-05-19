package com.pa.helpfin.model.data;

import java.sql.Date;

/**
 * @author artur
 */
public class User 
    extends 
        Core<User>
{
    public final static int FEMALE        = 0;
    public final static int MALE          = 1;
    
    public final static String GENDER []  = { "Feminino", "Masculino" };
    
    public final static int ROLE_ADMIN    = 0;
    public final static int ROLE_OPERATOR = 1;
    
    public final static String ROLE []    = { "Adminstrador", "Operador" };
    
    private String name;
    private String cpf;
    private Date   birthDate;
    private String rg;
    private String mail;
    private String phone;
    private int    gender;
    private int    role;
    private String login;
    private String password;

    public User() 
    {
    }

    public User( String name, String login, String password ) 
    {
        this.name     = name;
        this.login    = login;
        this.password = password;
    }

    public void setLogin( String login )
    {
        this.login = login;
    }

    public String getLogin() 
    {
        return login;
    }

    public String getPassword() 
    {
        return password;
    }

    public void setPassword( String password )
    {
        this.password = password;
    }

    public String getName() 
    {
        return name;
    }

    public void setName( String name ) 
    {
        this.name = name;
    }

    public String getCpf()
    {
        return cpf;
    }

    public void setCpf( String cpf )
    {
        this.cpf = cpf;
    }

    public Date getBirthDate() 
    {
        return birthDate;
    }

    public void setBirthDate( Date birthDate ) 
    {
        this.birthDate = birthDate;
    }

    public String getRg()
    {
        return rg;
    }

    public void setRg( String rg )
    {
        this.rg = rg;
    }

    public String getMail()
    {
        return mail;
    }

    public void setMail( String mail )
    {
        this.mail = mail;
    }

    public String getPhone() 
    {
        return phone;
    }

    public void setPhone( String phone ) 
    {
        this.phone = phone;
    }

    public int getGender() 
    {
        return gender;
    }

    public void setGender( int gender )
    {
        this.gender = gender;
    }

    public int getRole() 
    {
        return role;
    }

    public void setRole( int role )
    {
        this.role = role;
    }

    @Override
    public String toString() 
    {
        return name;
    }
}
