package modelo;

public class Turno {
	private DiaSemana dia;
    private String horaInicio;
    private String horaFin;

    public Turno(DiaSemana dia, String horaInicio, String horaFin)
    {
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public boolean coincideConDia(DiaSemana dia)
    {
        return this.dia == dia;
    }

    public boolean solapaCon(Turno otroTurno)
    {
        if (otroTurno == null)
        {
            return false;
        }

        if (dia != otroTurno.getDia())
        {
            return false;
        }

        return !(horaFin.compareTo(otroTurno.getHoraInicio()) <= 0 || horaInicio.compareTo(otroTurno.getHoraFin()) >= 0);
    }

    public boolean estaActivoEn(DiaSemana dia, String hora)
    {
        return this.dia == dia && hora.compareTo(horaInicio) >= 0 && hora.compareTo(horaFin) <= 0;
    }

    public DiaSemana getDia()
    {
        return dia;
    }

    public void setDia(DiaSemana dia)
    {
        this.dia = dia;
    }

    public String getHoraInicio()
    {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio)
    {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin()
    {
        return horaFin;
    }

    public void setHoraFin(String horaFin)
    {
        this.horaFin = horaFin;
    }
}
