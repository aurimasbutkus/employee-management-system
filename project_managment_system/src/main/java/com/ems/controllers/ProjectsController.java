package com.ems.controllers;

import com.ems.projectsinfo.Project;
import com.ems.projectsinfo.ProjectService;
import com.ems.projectsinfo.Subtask;
import com.ems.projectsinfo.Task;
import com.ems.userinfo.UserService;
import com.ems.validator.ProjectValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


/**
 * Created by Martynas on 5/5/2017.
 */
@Controller
public class ProjectsController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectValidator projectValidator;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value="projects", method = RequestMethod.GET)
    public String projects( Model model, Authentication authentication){
        String username = authentication.getName();
        Integer user_id = userService.getUser(username).getId();
        model.addAttribute("projects", projectService.listAllUserProjects(user_id));
        return "projects";
    }

    @RequestMapping(value="project-core/{id}", method = RequestMethod.GET)
    public String getProject(@PathVariable("id") int id, Model model, Authentication authentication){
        model.addAttribute("currentUserName", authentication.getName());
        model.addAttribute("project", projectService.getProject(id));
        model.addAttribute("tasks", projectService.listAllProjectTasks(id));
        model.addAttribute("subtasks", projectService.listAllSubtasks());
        model.addAttribute("newTask",new Task());
        model.addAttribute("newSubtask",new Subtask());
        return "project-core";
    }

    @PostMapping(value="project-core/{id}/add-task")
    public String addTask(@ModelAttribute("newTask") Task newTask, BindingResult bindingResult,
                          @PathVariable("id") Integer id, Model model, Authentication authentication){
        newTask.setFkProject(id);
        newTask.setCreationDate(new Date(System.currentTimeMillis()));
        projectService.create(newTask);
        return "redirect:/project-core/{id}";
    }
    @RequestMapping(value="/project-core/{project_id}/remove-task/{task_id}", method = RequestMethod.POST)
    public String removeTask(@PathVariable("task_id") Integer task_id) {
        projectService.deleteTask(task_id);
        return "redirect:/project-core/{id}";
    }
    @PostMapping(value="project-core/{project_id}/add-subtask/{task_id}")
    public String addTask(@ModelAttribute("newSubtask") Subtask newSubtask, BindingResult bindingResult,
                          @PathVariable("project_id") Integer project_id,
                          @PathVariable("task_id") Integer task_id, Model model,
                          Authentication authentication){
        newSubtask.setFkTask(task_id);
        newSubtask.setStatus(2);
        projectService.create(newSubtask);
        return "redirect:/project-core/{project_id}";
    }

    @RequestMapping(value="project-core/{project_id}/remove-subtask/{subtask_id}", method = RequestMethod.GET)
    public String removeSubtask(@PathVariable("project_id") Integer project_id,
                                @PathVariable("subtask_id") Integer subtask_id,Model model,
                                Authentication authentication){
        projectService.deleteSubtask(subtask_id);
        return "redirect:/project-core/{project_id}";
    }

    @GetMapping(value="projects/new")
    public String projectCreation( Model model){
        model.addAttribute("newProject", new Project());
        return "project-creation";
    }

    @PostMapping(value="projects/new-submit")
    public String createNewProject(@ModelAttribute("newProject") Project newProject, BindingResult bindingResult,
                                   Model model, Authentication authentication){
        projectValidator.validate(newProject, bindingResult);
        if (bindingResult.hasErrors()) {
            printErrors(bindingResult);
            return "project-creation";
        }
        if(newProject.getDeadline() == null)
            projectService.create(newProject.getName(), newProject.getDescription(),
                                    newProject.getStartDate());
        else
            projectService.create(newProject.getName(), newProject.getDescription(),
                                    newProject.getStartDate(), newProject.getDeadline());
        String username = authentication.getName();
        Integer user_id = userService.getUser(username).getId();
        model.addAttribute("projects", projectService.listAllUserProjects(user_id));
        return "projects";
    }

    private void printErrors(BindingResult bindingResult)
    {
        for (Object object : bindingResult.getAllErrors()) {
            if(object instanceof FieldError) {
                FieldError fieldError = (FieldError) object;
                String message = fieldError.getField() + " " + messageSource.getMessage(fieldError, null);
                System.out.println(message);
            }
        }
    }
}
