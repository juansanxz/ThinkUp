package com.project.thinkup.beans;



import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.project.thinkup.model.Idea;
import com.project.thinkup.service.IdeaService;


@ManagedBean
@Component
@ApplicationScoped
public class IdeasBean {

    private List<Idea> ideas;
    private Idea selectedIdea;
    private List<Idea> selectedIdeas;
    private List<Idea> filteredIdeas;

    @Autowired
    IdeaService ideaService;

    @PostConstruct
    public void init() {
        this.ideas = ideaService.getAllIdeas();

    }

    public Idea getSelectedIdea() {
        return selectedIdea;
    }

    public void setSelectedIdea(Idea selectedIdea) {
        this.selectedIdea = selectedIdea;
    }

    public List<Idea> getFilteredIdeas() {
        return filteredIdeas;
    }

    public void setFilteredIdeas(List<Idea> filteredIdeas) {
        this.filteredIdeas = filteredIdeas;
    }

    public List<Idea> getIdeas() {
        return ideas;
    }

    public void setIdeas(List<Idea> ideas) {
        this.ideas = ideas;
    }

    public List<Idea> getSelectedIdeas() {
        return selectedIdeas;
    }

    public void setSelectedIdeas(List<Idea> selectedIdeas) {
        this.selectedIdeas = selectedIdeas;
    }

    public void refresh() {
        this.ideas = ideaService.getAllIdeas();
    }

    public boolean hasSelectedIdeas() {
        return this.selectedIdeas != null && !this.selectedIdeas.isEmpty();
    }

    public String getGroupButtonMessage() {
        if (hasSelectedIdeas()) {
            int size = this.selectedIdeas.size();
            return size > 1 ? size + " Ideas seleccionados" : "1 idea seleccionada";
        }
        return "Agrupar";
    }




	
    
}
