package com.example.bolnicaServer.dto.request;

import java.util.Date;

public class LogReportDTO {
    public Date startDate;
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
