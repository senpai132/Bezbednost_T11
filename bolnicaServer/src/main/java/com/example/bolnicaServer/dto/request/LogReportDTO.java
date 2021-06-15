package com.example.bolnicaServer.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class LogReportDTO {
    @javax.validation.constraints.NotNull
    public Date startDate;
    @NotNull
    public Date endDate;

    public LogReportDTO() {}

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
