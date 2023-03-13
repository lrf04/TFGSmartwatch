package com.example.tfgsmartwatch.models; ;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ConfigurationStudent {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("lower_heart_rate")
    @Expose
    private Integer lowerHeartRate;
    @SerializedName("higher_heart_rate")
    @Expose
    private Integer higherHeartRate;
    @SerializedName("movement_monitoring_time")
    @Expose
    private Integer movementMonitoringTime;
    @SerializedName("movement_percentage")
    @Expose
    private Integer movementPercentage;
    @SerializedName("no_movement_monitoring_time")
    @Expose
    private Integer noMovementMonitoringTime;
    @SerializedName("no_movement_percentage")
    @Expose
    private Integer noMovementPercentage;
    @SerializedName("higher_rate")
    @Expose
    private String higherRate;
    @SerializedName("lower_rate")
    @Expose
    private String lowerRate;
    @SerializedName("lower_movement")
    @Expose
    private String lowerMovement;
    @SerializedName("higher_movement")
    @Expose
    private String higherMovement;
    @SerializedName("higher_rate_movement")
    @Expose
    private String higherRateMovement;
    @SerializedName("lower_rate_movement")
    @Expose
    private String lowerRateMovement;
    @SerializedName("higher_rate_lower_movement")
    @Expose
    private String higherRateLowerMovement;
    @SerializedName("lower_rate_higher_movement")
    @Expose
    private String lowerRateHigherMovement;
    @SerializedName("no_movement")
    @Expose
    private String noMovement;
    @SerializedName("proximity")
    @Expose
    private Integer proximity;
    @SerializedName("time")
    @Expose
    private Integer time;
    @SerializedName("lower_proximity_higher_rate")
    @Expose
    private String lowerProximityHigherRate;
    @SerializedName("lower_proximity_lower_rate")
    @Expose
    private String lowerProximityLowerRate;
    @SerializedName("higher_proximity_lower_time")
    @Expose
    private String higherProximityLowerTime;
    @SerializedName("student_id")
    @Expose
    private Integer studentId;
    @SerializedName("activar")
    @Expose
    private String activar;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLowerHeartRate() {
        return lowerHeartRate;
    }

    public void setLowerHeartRate(Integer lowerHeartRate) {
        this.lowerHeartRate = lowerHeartRate;
    }

    public Integer getHigherHeartRate() {
        return higherHeartRate;
    }

    public void setHigherHeartRate(Integer higherHeartRate) {
        this.higherHeartRate = higherHeartRate;
    }

    public Integer getMovementMonitoringTime() {
        return movementMonitoringTime;
    }

    public void setMovementMonitoringTime(Integer movementMonitoringTime) {
        this.movementMonitoringTime = movementMonitoringTime;
    }

    public Integer getMovementPercentage() {
        return movementPercentage;
    }

    public void setMovementPercentage(Integer movementPercentage) {
        this.movementPercentage = movementPercentage;
    }

    public Integer getNoMovementMonitoringTime() {
        return noMovementMonitoringTime;
    }

    public void setNoMovementMonitoringTime(Integer noMovementMonitoringTime) {
        this.noMovementMonitoringTime = noMovementMonitoringTime;
    }

    public Integer getNoMovementPercentage() {
        return noMovementPercentage;
    }

    public void setNoMovementPercentage(Integer noMovementPercentage) {
        this.noMovementPercentage = noMovementPercentage;
    }

    public String getHigherRate() {
        return higherRate;
    }

    public void setHigherRate(String higherRate) {
        this.higherRate = higherRate;
    }

    public String getLowerRate() {
        return lowerRate;
    }

    public void setLowerRate(String lowerRate) {
        this.lowerRate = lowerRate;
    }

    public String getLowerMovement() {
        return lowerMovement;
    }

    public void setLowerMovement(String lowerMovement) {
        this.lowerMovement = lowerMovement;
    }

    public String getHigherMovement() {
        return higherMovement;
    }

    public void setHigherMovement(String higherMovement) {
        this.higherMovement = higherMovement;
    }

    public String getHigherRateMovement() {
        return higherRateMovement;
    }

    public void setHigherRateMovement(String higherRateMovement) {
        this.higherRateMovement = higherRateMovement;
    }

    public String getLowerRateMovement() {
        return lowerRateMovement;
    }

    public void setLowerRateMovement(String lowerRateMovement) {
        this.lowerRateMovement = lowerRateMovement;
    }

    public String getHigherRateLowerMovement() {
        return higherRateLowerMovement;
    }

    public void setHigherRateLowerMovement(String higherRateLowerMovement) {
        this.higherRateLowerMovement = higherRateLowerMovement;
    }

    public String getLowerRateHigherMovement() {
        return lowerRateHigherMovement;
    }

    public void setLowerRateHigherMovement(String lowerRateHigherMovement) {
        this.lowerRateHigherMovement = lowerRateHigherMovement;
    }

    public String getNoMovement() {
        return noMovement;
    }

    public void setNoMovement(String noMovement) {
        this.noMovement = noMovement;
    }

    public Integer getProximity() {
        return proximity;
    }

    public void setProximity(Integer proximity) {
        this.proximity = proximity;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getLowerProximityHigherRate() {
        return lowerProximityHigherRate;
    }

    public void setLowerProximityHigherRate(String lowerProximityHigherRate) {
        this.lowerProximityHigherRate = lowerProximityHigherRate;
    }

    public String getLowerProximityLowerRate() {
        return lowerProximityLowerRate;
    }

    public void setLowerProximityLowerRate(String lowerProximityLowerRate) {
        this.lowerProximityLowerRate = lowerProximityLowerRate;
    }

    public String getHigherProximityLowerTime() {
        return higherProximityLowerTime;
    }

    public void setHigherProximityLowerTime(String higherProximityLowerTime) {
        this.higherProximityLowerTime = higherProximityLowerTime;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getActivar() {
        return activar;
    }

    public void setActivar(String activar) {
        this.activar = activar;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}