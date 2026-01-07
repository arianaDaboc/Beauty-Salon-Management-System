package service;

import model.user;
import repository.user_repository;
import util.PasswordAuthentication;

public class user_service {
    private final user_repository repo = new user_repository();
    private final PasswordAuthentication passwordAuth = PasswordAuthentication.getInstance();

    public user login(String username, String password) {
        user u = repo.findByUsername(username);
        if (u == null) {
            return null;
        }
        String storedToken = u.getPasswordHash();
        if (storedToken == null || storedToken.isEmpty()) {
            return null;
        }
        if (storedToken.startsWith("$31$")) {
            try {
                //compare hashes
                if (passwordAuth.authenticate(password, storedToken)) {
                    return u;
                }
            } catch (Exception e) {
            }
        }
        else if (storedToken.equals(password)) {
            try {
                String newHash = passwordAuth.hash(password);
                u.setPasswordHash(newHash);
                // Update in database
                repo.updatePasswordHash(u.getUserId(), newHash);
                return u;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void register(user u, String plainPassword) throws Exception {
        if (plainPassword == null || plainPassword.isEmpty()) {
            throw new Exception("Password is required");
        }
        // Hash the password
        String passwordToken = passwordAuth.hash(plainPassword);
        u.setPasswordHash(passwordToken);
        repo.create(u);
    }
    public user findById(int id) throws Exception {
        user u = repo.findById(id);
        if (u == null) {
            throw new Exception("User not found.");
        }
        return u;
    }
}
