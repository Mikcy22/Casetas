package org.ejercicio.cosas;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "casetas")
public class CasetaFeriaList {
    @XmlElement(name="caseta")
    private List<CasetaFeria> casetas;

    public CasetaFeriaList() {

    }

    public CasetaFeriaList(List<CasetaFeria> casetas) {
        this.casetas = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "CasetaFeriaList{" +
                "casetas=" + casetas +
                '}';
    }

    public void setCasetas(List<CasetaFeria> casetas) {
        this.casetas = casetas;
    }
}
