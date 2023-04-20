package com.example.tfgsmartwatch.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DatosRecreo {
    @SerializedName("periodo_id")
    @Expose
    private Integer periodoId;
    @SerializedName("steps")
    @Expose
    private Integer steps;
    @SerializedName("total_movimiento")
    @Expose
    private Integer totalMovimiento;
    @SerializedName("total_no_movimiento")
    @Expose
    private Integer totalNoMovimiento;

    public Integer getPeriodoId() {
        return periodoId;
    }

    public void setPeriodoId(Integer periodoId) {
        this.periodoId = periodoId;
    }

    public Integer getSteps() {
        return steps;
    }

    public void setSteps(Integer steps) {
        this.steps = steps;
    }

    public Integer getTotalMovimiento() {
        return totalMovimiento;
    }

    public void setTotalMovimiento(Integer totalMovimiento) {
        this.totalMovimiento = totalMovimiento;
    }

    public Integer getTotalNoMovimiento() {
        return totalNoMovimiento;
    }

    public void setTotalNoMovimiento(Integer totalNoMovimiento) {
        this.totalNoMovimiento = totalNoMovimiento;
    }
}
