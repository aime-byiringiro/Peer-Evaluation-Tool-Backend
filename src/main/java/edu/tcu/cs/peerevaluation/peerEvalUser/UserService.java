package edu.tcu.cs.peerevaluation.peerEvalUser;

import edu.tcu.cs.peerevaluation.student.StudentService;
import edu.tcu.cs.peerevaluation.system.exception.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  private final StudentService studentService;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, StudentService studentService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.studentService = studentService;
  }

  public List<PeerEvalUser> findAll() {
    return this.userRepository.findAll();
  }

  public PeerEvalUser findById(Integer userId) {
    return this.userRepository.findById(userId)
        .orElseThrow(() -> new ObjectNotFoundException("user", userId));
  }

  public PeerEvalUser save(PeerEvalUser newPeerEvalUser) {
    //System.out.println(newPeerEvalUser.getPassword());
    newPeerEvalUser.setPassword(this.passwordEncoder.encode(newPeerEvalUser.getPassword()));
    return this.userRepository.save(newPeerEvalUser);
  }

  public PeerEvalUser update(Integer userId, PeerEvalUser update) {
    PeerEvalUser oldPeerEvalUser = this.userRepository.findById(userId)
        .orElseThrow(() -> new ObjectNotFoundException("user", userId));
    oldPeerEvalUser.setUsername(update.getUsername());
    oldPeerEvalUser.setEnabled(update.isEnabled());
    oldPeerEvalUser.setRoles(update.getRoles());
    return this.userRepository.save(oldPeerEvalUser);
  }

  public void delete(Integer userId) {
    PeerEvalUser user = this.userRepository.findById(userId)
          .orElseThrow(() -> new ObjectNotFoundException("user", userId));
    if (user.getStudent() != null) {
      studentService.delete(user.getStudent().getId());
    }
    this.userRepository.deleteById(userId);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return this.userRepository.findByUsername(username) // First, we need to find this user from database.
        .map(peerEvalUser -> new MyUserPrincipal(peerEvalUser)) // If found, wrap the returned user instance in a
                                                                // MyUserPrincipal instance.
        .orElseThrow(() -> new UsernameNotFoundException("username " + username + " is not found.")); // Otherwise,
                                                                                                      // throw an
                                                                                                      // exception.
  }

  public PeerEvalUser updatePass(PeerEvalUser currentUser) {
    currentUser.setPassword(this.passwordEncoder.encode(currentUser.getPassword()));
    return this.userRepository.save(currentUser);
  }

  public void deactivate(Integer userId) {
    PeerEvalUser foundInstructor = this.userRepository.findById(userId)
        .orElseThrow(() -> new ObjectNotFoundException("instructor", userId));
    foundInstructor.setEnabled(false);
  }

  public void activate(Integer instructorId) {
    PeerEvalUser foundInstructor = this.userRepository.findById(instructorId)
        .orElseThrow(() -> new ObjectNotFoundException("instructor", instructorId));
    foundInstructor.setEnabled(true);
  }

}
