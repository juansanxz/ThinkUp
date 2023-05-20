package com.project.thinkup.beans;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import com.project.thinkup.service.IdeaService;

import org.primefaces.model.chart.BarChartModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Class that handles CRUD (Create, Read, Update, Delete) operations for users in a database.
 * This class uses the DAO (Data Access Object) design pattern to access the database data
 */

@ManagedBean
@Component
@ApplicationScoped

public class ChartJsBean {

    private BarChartModel modelArea;
    private BarChartModel modelRol;
    private BarChartModel modelStatus;

    @Autowired
    IdeaService ideaService;


    private void createModelArea(){
        modelArea = new BarChartModel();

    }


    public BarChartModel getModelArea() {
        return modelArea;
    }

    public BarChartModel getModelRol() {
        return modelRol;
    }

    public BarChartModel getModelStatus() {
        return modelStatus;
    }

    
}
