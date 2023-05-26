package com.project.thinkup.beans;



import java.util.ArrayList;
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
    private Idea selectedIdeaInTopic;
    private Topic selectedTopic;
    private String menuTopic;
    private List<Idea> selectedIdeas;
    private List<Idea> filteredIdeas;
    private List<Idea> selectedIdeasInTopic;
    private List<Idea> filteredIdeasInTopic;
    private static final String ANOTHERKEY = "anotherkey";
    private static final String GROUPIDEA = "Agrupar idea";

    @Autowired
    IdeaService ideaService;

    @Autowired
    TopicService topicService;

    @PostConstruct
    public void init() {
        this.ideas = ideaService.getAllIdeasWithoutTopic();
        this.topics = topicService.getAllTopics();
    }

    public Idea getSelectedIdea() {
        return selectedIdea;
    }

    public List<Idea> getSelectedIdeasInTopic() {
        return selectedIdeasInTopic;
    }

    public void setSelectedIdeasInTopic(List<Idea> selectedIdeasInTopic) {
        this.selectedIdeasInTopic = selectedIdeasInTopic;
    }

    public List<Idea> getFilteredIdeasInTopic() {
        return filteredIdeasInTopic;
    }

    public void setFilteredIdeasInTopic(List<Idea> filteredIdeasInTopic) {
        this.filteredIdeasInTopic = filteredIdeasInTopic;
    }

    public Idea getSelectedIdeaInTopic() {
        return selectedIdeaInTopic;
    }

    public void setSelectedIdeaInTopic(Idea selectedIdeaInTopic) {
        this.selectedIdeaInTopic = selectedIdeaInTopic;
    }

    public void setSelectedIdea(Idea selectedIdea) {
        this.selectedIdea = selectedIdea;
    }

    public String getMenuTopic() {
        return menuTopic;
    }

    public void setMenuTopic(String menuTopic) {
        this.menuTopic = menuTopic;
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
        this.ideas = ideaService.getAllIdeasWithoutTopic();
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
                context.addMessage(ANOTHERKEY, msg);               
            }else{
                FacesContext context = FacesContext.getCurrentInstance();
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "El tema ya existe!","Tema ya existe");
                context.addMessage(ANOTHERKEY, msg);
            }
        }
        else {
            if(topicService.updateTopic(selectedTopic) != null){
                RequestContext.getCurrentInstance().execute("PF('managetopicDialog').hide()");
                FacesContext context = FacesContext.getCurrentInstance();
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Tema actualizado con exito!","Tema modificado con exito");
                context.addMessage(ANOTHERKEY, msg);

            }else{
                FacesContext context = FacesContext.getCurrentInstance();
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al actualizar","Error al actualizar");
                context.addMessage(ANOTHERKEY, msg);
            }

        }
    }  

    public List<String> complete(String query) {
        List<String> results = new ArrayList<>();
        for (Topic topic : topics) {
            String title = topic.getTitle();
            if (title.toLowerCase().startsWith(query.toLowerCase())) {
                results.add(title);
            }
        }
        return results;
    }
    
    public void groupIdea() {
        try{
            Topic currentMenuTopic = topicService.getTopicByTitle(menuTopic);
            currentMenuTopic.addIdea(selectedIdea);
            topicService.updateTopic(currentMenuTopic);
            ideaService.updateIdea(selectedIdea);
            refresh();
            RequestContext.getCurrentInstance().execute("PF('grouptopicDialog').hide()");
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Idea agregada con exito al tema" + menuTopic,GROUPIDEA);
            context.addMessage(ANOTHERKEY, msg);   


        }catch(Exception e){
            RequestContext.getCurrentInstance().execute("PF('grouptopicDialog').hide()");
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Problema al agrupar:" + e.getMessage(),GROUPIDEA);
            context.addMessage(ANOTHERKEY, msg);   

        }
    }

    public void ungroupIdea(){
        try{
            selectedTopic.removeIdea(selectedIdeaInTopic);
            selectedTopic = topicService.updateTopic(selectedTopic);
            ideaService.updateIdea(selectedIdeaInTopic);
            refresh();
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "La idea se ha borrado del Tema correctamente",GROUPIDEA);
            context.addMessage(ANOTHERKEY, msg);   


        }catch(Exception e){
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Problema al borrar la idea del grupox:" + e.getMessage(),GROUPIDEA);
            context.addMessage(ANOTHERKEY, msg);   

        }
    }

    public void deleteSelectedTopic() {
        try{
            for(Idea ideaInTopic: selectedTopic.getIdeas()){
                ideaInTopic.setTopic(null);
                ideaService.updateIdea(ideaInTopic);
            }
            topicService.deleteTopic(selectedTopic.getTopicId());
            this.selectedTopic = null;
            refresh();
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Tema borrado con exito!", "Tema borrado con exito!");
            context.addMessage(ANOTHERKEY, msg);

        }catch(Exception e){
            String message = e.getMessage();
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, message, message);
            context.addMessage(ANOTHERKEY, msg);


        }

    }

    public void groupSelectedIdeas() {
        try{
            Topic currentMenuTopic = topicService.getTopicByTitle(menuTopic);
            for(Idea idea: this.selectedIdeas){
                currentMenuTopic.addIdea(idea);
                ideaService.updateIdea(idea);
            }
            topicService.updateTopic(currentMenuTopic);
            this.selectedIdeas = null;
            refresh();
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ideas agrupadas con exito!", "Ideas agrupadas con exito!");
            context.addMessage(ANOTHERKEY, msg);

        }catch(Exception e){
            String message = e.getMessage();
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, message + " Error al agrupar Ideas al tema", message);
            context.addMessage(ANOTHERKEY, msg);
        }
    }
}  
        


    

