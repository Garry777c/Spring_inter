package com.javarush.service;

import com.javarush.dao.TaskDAO;
import com.javarush.domain.Status;
import com.javarush.domain.Task;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Objects.isNull;

@Service
public class TaskService {

    private final TaskDAO taskDAO;

    public TaskService(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    public List<Task> getAll(int offset, int limit){
        return taskDAO.getAll(offset, limit);
    }

    public int getAllCount(){
        return taskDAO.getAllCount();
    }

    @Transactional
    public Task edit(int id, String description, Status status){
        Task task = taskDAO.getByID(id);
        if(isNull(task)) {
            throw new ObjectNotFoundException("Task not found here", Task.class);
        }

        task.setDescription(description);
        task.setStatus(status);
        taskDAO.saveOrUpdate(task);
        return task;
    }

    @Transactional
    public void delete(int id){
        Task task = taskDAO.getByID(id);
        if(isNull(task)) {
            throw new RuntimeException("Not found here");
        }

        taskDAO.delete(task);
    }

    public Task create(String description, Status status){
        Task task = new Task();
        task.setDescription(description);
        task.setStatus(status);
        taskDAO.saveOrUpdate(task);
        return task;
    }


}
