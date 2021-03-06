package com.ems.controllers;

/**
 * Created by Marius on 2017-04-07.
 */

import com.ems.messaging.Message;
import com.ems.messaging.MessageService;
import com.ems.projectsinfo.Project;
import com.ems.projectsinfo.ProjectService;
import com.ems.projectsinfo.Subtask;
import com.ems.projectsinfo.Task;
import com.ems.userinfo.Role;
import com.ems.userinfo.RoleService;
import com.ems.userinfo.User;
import com.ems.userinfo.UserService;
import com.ems.validator.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private MessageService messageService;   
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private RoleValidator roleValidator;
    @Autowired
    private ProjectValidator projectValidator;
    @Autowired
    private TaskValidator taskValidator;
    @Autowired
    private SubtaskValidator subtaskValidator;
    @Autowired
    private MessageValidator messageValidator;

    @RequestMapping(value="/admin", method = RequestMethod.GET)
    public String projects(Model model, Authentication authentication){
        model.addAttribute("currentUserName", authentication.getName());
        return "admin/admin";
    }    
    @RequestMapping(value="/admin/user", method = RequestMethod.GET)
    public String user(Model model) {
        model.addAttribute("users", userService.getUserList());
        return "admin/user";
    }    
    @GetMapping(value = "/admin/user/{id}")
    public String userEditGet(Model model, @PathVariable("id") int id )
    {
        model.addAttribute("userForm", userService.getUser(id));
        return "admin/user-edit";
    }
    @PostMapping(value = "/admin/user/{id}")
    public String userEditPost(@ModelAttribute("userForm") User userForm, BindingResult bindingResult)
    {
        userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "admin/user-edit";
        }
        userService.updateEverything(userForm);
        return "redirect:/admin/user";
    }
    @PostMapping(value = "/admin/user/{id}/delete")
    public String userDeletePost( @PathVariable("id") int id )
    {
        userService.delete(id);
        return "redirect:/admin/user";
    }
    @RequestMapping(value="/admin/project", method = RequestMethod.GET)
    public String project(Model model) {
        model.addAttribute("projects", projectService.listAllProjects());
        return "admin/project";
    }
    @GetMapping(value = "/admin/project/create")
    public String projectCreateGet(Model model) {
        model.addAttribute("projectForm", new Project());
        return "admin/project-create";
    }
    @PostMapping(value = "/admin/project/create")
    public String projectCreatePost(@ModelAttribute("projectForm") Project projectForm, BindingResult bindingResult)
    {
        projectValidator.validate(projectForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "admin/project-create";
        }
        projectService.create(projectForm);
        return "redirect:/admin/project";
    }
    @GetMapping(value = "/admin/project/{id}")
    public String projectEditGet(Model model, @PathVariable("id") int id )
    {
        model.addAttribute("projectForm", projectService.getProject(id));
        return "admin/project-edit";
    }
    @PostMapping(value = "/admin/project/{id}")
    public String projectEditPost(@ModelAttribute("projectForm") Project projectForm, BindingResult bindingResult)
    {
        projectValidator.validate(projectForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "admin/project-edit";
        }
        projectService.updateEverything(projectForm);
        return "redirect:/admin/project";
    }
    @PostMapping(value = "/admin/project/{id}/delete")
    public String projectDeletePost( @PathVariable("id") int id )
    {
        projectService.delete(id);
        return "redirect:/admin/project";
    }
    @RequestMapping(value="/admin/task", method = RequestMethod.GET)
    public String task(Model model) {
        model.addAttribute("tasks", projectService.listAllTasks());
        return "admin/task";
    }
    @GetMapping(value = "/admin/task/create")
    public String taskCreateGet(Model model) {
        model.addAttribute("taskForm", new Task());
        return "admin/task-create";
    }
    @PostMapping(value = "/admin/task/create")
    public String taskCreatePost(@ModelAttribute("taskForm") Task taskForm, BindingResult bindingResult)
    {
        taskValidator.validate(taskForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "admin/task-create";
        }
        projectService.create(taskForm);
        return "redirect:/admin/task";
    }
    @GetMapping(value = "/admin/task/{id}")
    public String taskEditGet(Model model, @PathVariable("id") int id )
    {
        model.addAttribute("taskForm", projectService.getTask(id));
        return "admin/task-edit";
    }
    @PostMapping(value = "/admin/task/{id}")
    public String taskEditPost(@ModelAttribute("taskForm") Task taskForm, BindingResult bindingResult)
    {
        taskValidator.validate(taskForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "admin/task-edit";
        }
        projectService.updateEverything(taskForm);
        return "redirect:/admin/task";
    }
    @PostMapping(value = "/admin/task/{id}/delete")
    public String taskDeletePost( @PathVariable("id") int id )
    {
        projectService.deleteTask(id);
        return "redirect:/admin/task";
    }
    @RequestMapping(value="/admin/subtask", method = RequestMethod.GET)
    public String subtask(Model model) {
        model.addAttribute("subtasks", projectService.listAllSubtasks());
        return "admin/subtask";
    }
    @GetMapping(value = "/admin/subtask/create")
    public String subtaskCreateGet(Model model) {
        model.addAttribute("subtaskForm", new Subtask());
        return "admin/subtask-create";
    }
    @PostMapping(value = "/admin/subtask/create")
    public String subtaskCreatePost(@ModelAttribute("subtaskForm") Subtask subtaskForm, BindingResult bindingResult)
    {
        subtaskValidator.validate(subtaskForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "admin/subtask-create";
        }
        projectService.create(subtaskForm);
        return "redirect:/admin/subtask";
    }
    @GetMapping(value = "/admin/subtask/{id}")
    public String subtaskEditGet(Model model, @PathVariable("id") int id )
    {
        model.addAttribute("subtaskForm", projectService.getSubtask(id));
        return "admin/subtask-edit";
    }
    @PostMapping(value = "/admin/subtask/{id}")
    public String subtaskEditPost(@ModelAttribute("subtaskForm") Subtask subtaskForm, BindingResult bindingResult)
    {
        subtaskValidator.validate(subtaskForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "admin/subtask-edit";
        }
        projectService.updateEverything(subtaskForm);
        return "redirect:/admin/subtask";
    }
    @PostMapping(value = "/admin/subtask/{id}/delete")
    public String subtaskDeletePost( @PathVariable("id") int id )
    {
        projectService.deleteSubtask(id);
        return "redirect:/admin/subtask";
    }
    @RequestMapping(value="/admin/message", method = RequestMethod.GET)
    public String message(Model model) {
        model.addAttribute("messages", messageService.listAllMessages());
        return "admin/message";
    }
    @GetMapping(value = "/admin/message/create")
    public String messageCreateGet(Model model) {
        model.addAttribute("messageForm", new Message());
        return "admin/message-create";
    }
    @PostMapping(value = "/admin/message/create")
    public String messageCreatePost(@ModelAttribute("messageForm") Message messageForm, BindingResult bindingResult)
    {
        messageValidator.validate(messageForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "admin/message-create";
        }
        messageService.create(messageForm);
        return "redirect:/admin/message";
    }
    @GetMapping(value = "/admin/message/{id}")
    public String messageEditGet(Model model, @PathVariable("id") int id )
    {
        model.addAttribute("messageForm", messageService.getMessage(id));
        return "admin/message-edit";
    }
    @PostMapping(value = "/admin/message/{id}")
    public String messageEditPost(@ModelAttribute("messageForm") Message messageForm, BindingResult bindingResult)
    {
        messageValidator.validate(messageForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "admin/message-edit";
        }
        messageService.updateEverything(messageForm);
        return "redirect:/admin/message";
    }
    @PostMapping(value = "/admin/message/{id}/delete")
    public String messageDeletePost( @PathVariable("id") int id )
    {
        messageService.delete(id);
        return "redirect:/admin/message";
    }
    @RequestMapping(value = "/admin/role", method = RequestMethod.GET)
    public String role(Model model) {
        model.addAttribute("roles", roleService.listAllRoles());
        return "admin/role";
    }
    @GetMapping(value = "/admin/role/create")
    public String roleCreateGet(Model model) {
        model.addAttribute("roleForm", new Role());
        return "admin/role-create";
    }
    @PostMapping(value = "/admin/role/create")
    public String roleCreatePost(@ModelAttribute("roleForm") Role roleForm, BindingResult bindingResult)
    {
        roleValidator.validate(roleForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "admin/role-create";
        }
        roleService.createRole(roleForm);
        return "redirect:/admin/role";
    }
    @GetMapping(value = "/admin/role/{id}")
    public String roleEditGet(Model model, @PathVariable("id") int id )
    {
        model.addAttribute("roleForm", roleService.getRole(id));
        return "admin/role-edit";
    }
    @PostMapping(value = "/admin/role/{id}")
    public String roleEditPost(@ModelAttribute("roleForm") Role roleForm, BindingResult bindingResult)
    {
        roleValidator.validate(roleForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "admin/role-edit";
        }
        roleService.updateEverything(roleForm);
        return "redirect:/admin/role";
    }
    @PostMapping(value = "/admin/role/{id}/delete")
    public String roleDeletePost( @PathVariable("id") int id )
    {
        roleService.delete(id);
        return "redirect:/admin/role";
    }
}
