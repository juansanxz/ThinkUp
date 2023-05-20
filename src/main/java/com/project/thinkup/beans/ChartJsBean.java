package com.project.thinkup.beans;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import com.project.thinkup.service.IdeaService;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
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
        createModelArea();

    }

    private void createModelArea(){
        modelArea = new BarChartModel();
        ChartSeries student = new ChartSeries();
        student.setLabel("Estudiantes");
        Long ideasByStudent = ideaService.countIdeasByUserArea("estudiante");
        student.set("Estudiantes", ideasByStudent);

        ChartSeries teacher = new ChartSeries();
        teacher.setLabel("Profesores");
        Long ideasByTeacher = ideaService.countIdeasByUserArea("profesor");
        teacher.set("Profesores", ideasByTeacher);

        ChartSeries adminis = new ChartSeries();
        adminis.setLabel("Administrativos");
        Long ideasByAdminis = ideaService.countIdeasByUserArea("administrativo");
        adminis.set("Profesores", ideasByAdminis);

        ChartSeries externos = new ChartSeries();
        externos.setLabel("Externo");
        Long ideasByExternos = ideaService.countIdeasByUserArea("externo");
        externos.set("Externos", ideasByExternos);



        modelArea.addSeries(student);
        modelArea.addSeries(teacher);
        modelArea.addSeries(adminis);
        modelArea.addSeries(externos);

        modelArea.setTitle("Estadísticas por área");
        modelArea.setLegendPosition("ne");
       
        Axis xAxis = modelArea.getAxis(AxisType.X);
        xAxis.setLabel("Areas");
       
        Axis yAxis = modelArea.getAxis(AxisType.Y);
        yAxis.setLabel("Ideas");
        yAxis.setMin(0);
        yAxis.setMax(5);
       
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
