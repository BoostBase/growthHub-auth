package com.growthhub.auth.repository;

import com.growthhub.auth.domain.User;
import com.growthhub.auth.domain.type.Provider;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    Optional<User> findByProviderAndSocialId(Provider provider, String socialId);
}
