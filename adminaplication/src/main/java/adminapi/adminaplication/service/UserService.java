package adminapi.adminaplication.service;

import adminapi.adminaplication.model.User;
import adminapi.adminaplication.model.enums.UserType;
import adminapi.adminaplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    /*public void createUser(User user) throws Exception{
        if(user == null){
            throw new Exception("Bad input");
        }
        User oldUser = userRepository.findByEmail(user.getEmailAddress());
        if(oldUser != null){
            throw new Exception("User with given email already exists");
        }

        userRepository.save(user);
    }*/

    /*public void changeUserType(UserType type, String email) throws Exception{
        User user = userRepository.findByEmail(email).;

        if(type == null){
            throw  new Exception("Bad type");
        }

        if(user == null){
            throw new Exception("User not found");
        }
        else if(user.getType() == UserType.SUPER_ADMIN){
            throw new Exception("Can not change type of super admin user");
        }

        user.setType(type);
        userRepository.save(user);
    }*/

    public List<User> allUsers(){
        return userRepository.findAll();
    }

    /*public void deleteUser(String email) throws Exception{
        User user = userRepository.findByEmail(email);

        if(user == null){
            throw new Exception("User not found");
        }
        else if(user.getType() == UserType.SUPER_ADMIN){
            throw new Exception("Can not delete super admin user");
        }

        userRepository.delete(user);
    }*/
}
