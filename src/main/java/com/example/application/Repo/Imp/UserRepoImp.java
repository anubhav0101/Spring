package com.example.application.Repo.Imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.application.Repo.UserRepo;
import com.example.application.entity.Users;
@Component
public class UserRepoImp {
    @Autowired
    UserRepo userR;
    public boolean checkMail(String s){
        ArrayList<Users> u =userR.findByemail(s);
        Integer size=u.size();
        if(size>0)
            return false;
        else
        return true;
    }
    public List<Users> findByemail(String s){
        return userR.findByemail(s);
    }
    public void addUser(Users u){
        userR.save(u);
    }
    public void updateUser(Users u) {
    	userR.saveAndFlush(u);
    }
    
    public boolean checkUser(String s){
        List<Users> u =findByemail(s);
        if(u==null)
            return false;
        return true;
    }
    public Users findUserByEmail(String s){
        Users u = findByemail(s).get(0);
        return u;
    }
    public void delete(int id){
    	userR.deleteById(id);
    }
    
    public Users findEntityById(int id) {

        List<Users> u = userR.findById(id);
        return u.get(0);
    }
}
