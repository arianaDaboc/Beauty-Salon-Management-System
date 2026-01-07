package service;
import model.staff;
import repository.staff_repository;

import java.sql.SQLException;
import java.util.List;
public class staff_service {
    private final staff_repository repo=new staff_repository();
    public void create(staff s) throws Exception{
        if(s.getStaff_name()==null || s.getStaff_name().isBlank())
        {
            throw new IllegalArgumentException("Staff name cannot be empty");
        }
        repo.create(s);
    }
    public staff findbyId(int id) throws Exception{
        staff s=repo.findbyid(id);
        if (s == null) throw new Exception("Staff not found.");
        return s;
    }
    public List<staff> findAll()
    {
        try {
            return repo.findall();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void update(staff s) throws Exception {
        if (repo.findbyid(s.getStaff_id()) == null) {
            throw new Exception("Staff does not exist.");
        }
        repo.update(s);
    }
    public void delete(int id) throws Exception {
        if (repo.findbyid(id) == null) {
            throw new Exception("Staff does not exist.");
        }
        repo.delete(id);
    }
}
