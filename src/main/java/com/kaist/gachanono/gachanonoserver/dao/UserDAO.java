package com.kaist.gachanono.gachanonoserver.dao;

import java.util.List;

import com.kaist.gachanono.gachanonoserver.dto.UserDTO;

public interface UserDAO {
    List<UserDTO> getUsers();    
}
