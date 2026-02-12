package br.com.weltonfaria.todolist.user;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsersRepository extends JpaRepository<UserModel, UUID> {
    UserModel findByUsername(String username);
}
