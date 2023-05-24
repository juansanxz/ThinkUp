package com.project.thinkup.beans;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import com.project.thinkup.service.IdeaService;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.BarChartSeries;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Class that handles CRUD (Create, Read, Update, Delete) operations for users in a database.
 * This class uses the DAO (Data Access Object) design pattern to access the database data
 */

@ManagedBean
@Component
@RequestScoped

public class ChartJsBean {

    private BarChartModel modelArea;
    private BarChartModel modelRol;
    private BarChartModel modelStatus;

    @Autowired
    IdeaService ideaService;

    @PostConstruct
    public void init() {
        this.modelArea = createModelArea();

    }

    private BarChartModel createModelArea(){
        modelArea = new BarChartModel();
        BarChartSeries areas = new BarChartSeries();
        areas.setLabel("Areas");
        Long ideasByStudent = ideaService.countIdeasByUserArea("estudiante");
        Long ideasByAdminis = ideaService.countIdeasByUserArea("administrativo");
        Long ideasByExternos = ideaService.countIdeasByUserArea("externo");
        Long ideasByTeacher = ideaService.countIdeasByUserArea("profesor");


        areas.set("Profesores", ideasByTeacher);
        areas.set("Externos", ideasByExternos);
        areas.set("Administrativos", ideasByAdminis);
        areas.set("Estudiantes", ideasByStudent);

        modelArea.addSeries(areas);

        modelArea.setTitle("Estadísticas por área");
        modelArea.setLegendPosition("ne");
       
        Axis xAxis = modelArea.getAxis(AxisType.X);
        xAxis.setLabel("Areas");
    
        Axis yAxis = modelArea.getAxis(AxisType.Y);
        yAxis.setLabel("Ideas");
        yAxis.setMin(0);
        yAxis.setTickInterval("1");

        modelArea.setExtender(null);


        return modelArea;
    }

    public BarChartModel refreshModelArea() {
        return createModelArea();
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
