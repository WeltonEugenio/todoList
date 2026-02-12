package br.com.weltonfaria.todolist.task;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.weltonfaria.todolist.utils.Ultils;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/tasks")
public class TaskController {

   
    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request) {
          
        // read idUser attribute set by the authentication filter
        Object attr = request.getAttribute("idUser");
        taskModel.setIdUser((UUID) attr);
            
        // validate startAT: must be present and after now
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
            
        if (taskModel.getStartAT() == null || taskModel.getStartAT().isBefore(now)) {
            String msg = "data inicio dever ser maior que data atual";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
        }
        
        if (taskModel.getEndAT() == null || taskModel.getEndAT().isBefore(now) || taskModel.getEndAT().isBefore(taskModel.getStartAT())) {
            String msg = "data final dever ser maior que data atual e maior que data inicio";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
        }
        
        
        var task = this.taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @RequestMapping("/")
    public List<TaskModel> list(HttpServletRequest request){
        var idUser = request.getAttribute("idUser");
        return this.taskRepository.findByIdUser((UUID) idUser);
     
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID id) {
        var task = this.taskRepository.findById(id).orElse(null);
        var idUser = request.getAttribute("idUser");

        if (task == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tarefa não encontrada"); 
        }

        if(!task.getIdUser().equals(idUser))  {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário não tem permissão para atualizar esta tarefa");
        }

        Ultils.copyNonNullProperties(taskModel, task);
        var taskUpdated = this.taskRepository.save(task);
        return ResponseEntity.ok().body(taskUpdated);

    }
    
}
