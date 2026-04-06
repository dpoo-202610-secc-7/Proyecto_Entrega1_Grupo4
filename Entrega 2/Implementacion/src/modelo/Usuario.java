package modelo;

public abstract class Usuario
{
	private String documentoIdentidad;
    private String nombre;
    private String correoElectronico;
    private String login;
    private String password;

    public Usuario(String documentoIdentidad, String nombre, String correoElectronico, String login, String password)
    {
        this.documentoIdentidad = documentoIdentidad;
        this.nombre = nombre;
        this.correoElectronico = correoElectronico;
        this.login = login;
        this.password = password;
    }

    public boolean iniciarSesion(String login, String password)
    {
        return this.login.equals(login) && validarPassword(password);
    }

    public void cerrarSesion()
    {
        // Se maneja después desde SistemaDulcesDados
    }

    public boolean validarPassword(String password)
    {
        return this.password.equals(password);
    }

    public String getDocumentoIdentidad()
    {
        return documentoIdentidad;
    }

    public void setDocumentoIdentidad(String documentoIdentidad)
    {
        this.documentoIdentidad = documentoIdentidad;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getCorreoElectronico()
    {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico)
    {
        this.correoElectronico = correoElectronico;
    }

    public String getLogin()
    {
        return login;
    }

    public void setLogin(String login)
    {
        this.login = login;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}