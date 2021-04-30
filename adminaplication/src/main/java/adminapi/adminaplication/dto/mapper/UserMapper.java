package adminapi.adminaplication.dto.mapper;

import adminapi.adminaplication.dto.request.UserDTO;
import adminapi.adminaplication.model.User;
import adminapi.adminaplication.model.enums.UserType;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UserMapper implements  MapperInterface<User, UserDTO>{
    @Override
    public User toEntity(UserDTO dto) {
        UserType type = this.makeType(dto.getType());
        if(type == null){
            return null;
        }
        User user = new User(dto.getId(), dto.getEmail(), dto.getPassword(), type);
        return user;
    }

    public UserType makeType(String dtoType){
        UserType type = UserType.SUPER_ADMIN;
        if(dtoType.toLowerCase().equals("doctor")){
            type = UserType.DOCTOR;
        }
        else if(dtoType.toLowerCase().equals("hospital admin")){
            type = UserType.HOSPITAL_ADMIN;
        }
        else{
            return null;
        }
        return type;
    }

    @Override
    public List<User> toEntityList(List<UserDTO> dtoList) {
        List<User> users = new ArrayList<>();
        for(UserDTO userDTO : dtoList){
            users.add(this.toEntity(userDTO));
        }
        return users;
    }

    @Override
    public UserDTO toDto(User entity) {

        UserDTO dto = new UserDTO(entity.getId(), entity.getEmail(), "", entity.getType().toString());
        return dto;
    }

    @Override
    public List<UserDTO> toDtoList(List<User> entityList) {
        List<UserDTO> userDTOS = new ArrayList<>();
        for(User user : entityList){
            userDTOS.add(this.toDto(user));
        }
        return userDTOS;
    }
}
