package com.example.tfgsmartwatch.models; ;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Data {

    @SerializedName("configuracion_id")
    @Expose
    private Integer configuracionId;
    @SerializedName("datos_clase")
    @Expose
    private List<DatosClase> datosClase;
    @SerializedName("fecha")
    @Expose
    private String fecha;
    @SerializedName("puntuacion")
    @Expose
    private Integer puntuacion;
    @SerializedName("student_id")
    @Expose
    private Integer studentId;



    public Integer getConfiguracionId() {
        return configuracionId;
    }

    public void setConfiguracionId(Integer configuracionId) {
        this.configuracionId = configuracionId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Integer getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Integer puntuacion) {
        this.puntuacion = puntuacion;
    }

    public List<DatosClase> getDatosClase() {
        return datosClase;
    }

    public void setDatosClase(List<DatosClase> datosClase) {
        this.datosClase = datosClase;
    }

}