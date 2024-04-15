package edu.tcu.cs.peerevaluation.peerEvalUser;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<PeerEvalUser, Integer> {

    Optional<PeerEvalUser> findByUsername(String username);

}
