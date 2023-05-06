package com.project.thinkup.beans;



import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.project.thinkup.model.Idea;
import com.project.thinkup.model.Topic;
import com.project.thinkup.service.IdeaService;
import com.project.thinkup.service.TopicService;


@ManagedBean
@Component
@ApplicationScoped
public class IdeasBean {

    private List<Idea> ideas;
    private List<Topic> topics;
    private Idea selectedIdea;
    private Topic selectedTopic;
    private List<Idea> selectedIdeas;
    private List<Idea> filteredIdeas;

    @Autowired
    IdeaService ideaService;

    @Autowired
    TopicService topicService;

    @PostConstruct
    public void init() {
        this.ideas = ideaService.getAllIdeas();
        this.topics = topicService.getAllTopics();
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

    
    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    public void setSelectedIdeas(List<Idea> selectedIdeas) {
        this.selectedIdeas = selectedIdeas;
    }

    public Topic getSelectedTopic() {
        return selectedTopic;
    }

    public void setSelectedTopic(Topic selectedTopic) {
        this.selectedTopic = selectedTopic;
    }

    public void refresh() {
        this.ideas = ideaService.getAllIdeas();
        this.topics = topicService.getAllTopics();
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

    public void openNew() {
        this.selectedTopic = new Topic();
    }


    public void saveTopic() {
        if (this.selectedTopic.getTopicId() == null) {
            if(!topicService.topicExist(selectedTopic.getTitle())){
                topicService.addTopic(selectedTopic);
                refresh();
                RequestContext.getCurrentInstance().execute("PF('managetopicDialog').hide()");
                FacesContext context = FacesContext.getCurrentInstance();
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Tema creado con exito!","Crear tema");
                context.addMessage("anotherkey", msg);               
            }else{
                FacesContext context = FacesContext.getCurrentInstance();
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "El tema ya existe!","Tema ya existe");
                context.addMessage("anotherkey", msg);
            }
        }
        else {
            if(topicService.updateTopic(selectedTopic) != null){
                FacesContext context = FacesContext.getCurrentInstance();
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Tema actualizado con exito!","Tema modificado con exito");
                context.addMessage("anotherkey", msg);

            }else{
                FacesContext context = FacesContext.getCurrentInstance();
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al actualizar","Error al actualizar");
                context.addMessage("anotherkey", msg);
            }

        }
    }  


    
}
