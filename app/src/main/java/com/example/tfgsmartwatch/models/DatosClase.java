package com.example.tfgsmartwatch.models; ;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class DatosClase {

    @SerializedName("periodo_id")
    @Expose
    private Integer periodoId;
    @SerializedName("total_intervalos_movimiento")
    @Expose
    private Integer totalIntervalosMovimiento;
    @SerializedName("total_nervioso_movimiento")
    @Expose
    private Integer totalNerviosoMovimiento;
    @SerializedName("total_calmado_movimiento")
    @Expose
    private Integer totalCalmadoMovimiento;
    @SerializedName("total_intervalos_ritmo")
    @Expose
    private Integer totalIntervalosRitmo;
    @SerializedName("total_nervioso_ritmo")
    @Expose
    private Integer totalNerviosoRitmo;
    @SerializedName("total_calmado_ritmo")
    @Expose
    private Integer totalCalmadoRitmo;

    public Integer getPeriodoId() {
        return periodoId;
    }

    public void setPeriodoId(Integer periodoId) {
        this.periodoId = periodoId;
    }

    public Integer getTotalIntervalosMovimiento() {
        return totalIntervalosMovimiento;
    }

    public void setTotalIntervalosMovimiento(Integer totalIntervalosMovimiento) {
        this.totalIntervalosMovimiento = totalIntervalosMovimiento;
    }

    public Integer getTotalNerviosoMovimiento() {
        return totalNerviosoMovimiento;
    }

    public void setTotalNerviosoMovimiento(Integer totalNerviosoMovimiento) {
        this.totalNerviosoMovimiento = totalNerviosoMovimiento;
    }

    public Integer getTotalCalmadoMovimiento() {
        return totalCalmadoMovimiento;
    }

    public void setTotalCalmadoMovimiento(Integer totalCalmadoMovimiento) {
        this.totalCalmadoMovimiento = totalCalmadoMovimiento;
    }

    public Integer getTotalIntervalosRitmo() {
        return totalIntervalosRitmo;
    }

    public void setTotalIntervalosRitmo(Integer totalIntervalosRitmo) {
        this.totalIntervalosRitmo = totalIntervalosRitmo;
    }

    public Integer getTotalNerviosoRitmo() {
        return totalNerviosoRitmo;
    }

    public void setTotalNerviosoRitmo(Integer totalNerviosoRitmo) {
        this.totalNerviosoRitmo = totalNerviosoRitmo;
    }

    public Integer getTotalCalmadoRitmo() {
        return totalCalmadoRitmo;
    }

    public void setTotalCalmadoRitmo(Integer totalCalmadoRitmo) {
        this.totalCalmadoRitmo = totalCalmadoRitmo;
    }

}