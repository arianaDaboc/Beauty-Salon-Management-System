SELECT username, role_name, password_hash
FROM app_user u
JOIN role r ON r.role_id = u.role_id;
