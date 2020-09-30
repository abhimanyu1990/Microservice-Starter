package poc.projectmgt.user.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import poc.projectmgt.user.dto.UserDTO;
import poc.projectmgt.user.entities.User;

@Mapper( componentModel = "spring")
public interface UserMapper {
		public static final UserMapper INSTANCE = Mappers.getMapper( UserMapper.class ); 
	    public UserDTO userToUserDTO(User user); 
	    public User  userDTOToUser(UserDTO user);
}
