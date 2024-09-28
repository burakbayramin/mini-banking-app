package com.burakbayramin.mini_banking_app.repository;

import com.burakbayramin.mini_banking_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    /**
     * Belirli bir kullanıcı adına sahip kullanıcıyı getirir.
     *
     * @param username Aranacak kullanıcı adı
     * @return Kullanıcı mevcutsa Optional<User> döner
     */
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    /**
     * Belirtilen kullanıcı adına sahip bir kullanıcının var olup olmadığını kontrol eder.
     *
     * @param username Kontrol edilecek kullanıcı adı
     * @return Kullanıcı mevcutsa true, değilse false döner
     */
    boolean existsByUsername(String username);

    /**
     * Belirtilen e-posta adresine sahip bir kullanıcının var olup olmadığını kontrol eder.
     *
     * @param email Kontrol edilecek e-posta adresi
     * @return Kullanıcı mevcutsa true, değilse false döner
     */
    boolean existsByEmail(String email);
}
