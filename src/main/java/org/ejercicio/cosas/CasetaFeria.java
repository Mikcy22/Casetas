package org.ejercicio.cosas;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "caseta")
public class CasetaFeria {
    @XmlElement(name="id")
    private int id;
    @XmlElement (name="nombre")
    private String nombre;
    @XmlElement(name="titular")
    private String titular;
    @XmlElement(name="aforo")
    private int aforo;
    @XmlElement(name="tipoCaseta")
    private String tipoCaseta;

    // Constructor vacío necesario para JAXB
    public CasetaFeria() {}

    public CasetaFeria(int id, String nombre, String titular, int aforo, String tipoCaseta) {
        this.id = id;
        this.nombre = nombre;
        this.titular = titular;
        this.aforo = aforo;
        this.tipoCaseta = tipoCaseta;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return "CasetaFeria{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", titular='" + titular + '\'' +
                ", aforo=" + aforo +
                ", tipoCaseta='" + tipoCaseta + '\'' +
                '}';
    }


    public String getTitular() {
        return titular;
    }

    public int getAforo() {
        return aforo;
    }


    public String getTipoCaseta() {
        return tipoCaseta;
    }

}
