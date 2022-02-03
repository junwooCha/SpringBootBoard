package com.koreait.springbootboard.user;

import com.koreait.springbootboard.MyUserUtils;
import com.koreait.springbootboard.user.model.UserEntity;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired private UserMapper mapper;
    @Autowired private MyUserUtils userUtils;

    public int join(UserEntity entity) {
        //유효성 검사

        String hashedUpw = BCrypt.hashpw(entity.getUpw(), BCrypt.gensalt());
        entity.setUpw(hashedUpw);
        try {
            return mapper.insUser(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 0:db에러, 1:로그인 성공, 2:아이디없음, 3:비밀번호다름
    public int login(UserEntity entity){
        UserEntity dbUser = null;
        try{
            dbUser = mapper.selUser(entity);
        }catch (Exception e){
            e.printStackTrace();
            return 0; //db에러발생
        }
        if(dbUser == null){
            return 2; //아이디 없음
        }else if(!BCrypt.checkpw(dbUser.getUpw(),entity.getUpw())){
            return 3; //비밀번호 다름
        }
        dbUser.setUpw(null);
        dbUser.setRdt(null);
        dbUser.setMdt(null);
        userUtils.setLoginUser(dbUser);
        return 1; //로그인
    }

}
