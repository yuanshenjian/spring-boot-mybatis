package org.yood.springboot.mybatis.web.validator;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindException;
import org.yood.springboot.mybatis.entity.Authority;
import org.yood.springboot.mybatis.entity.User;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserValidatorTest {

    private UserValidator userValidator;
    private BindException bindException;

    @Before
    public void setup() {
        userValidator = new UserValidator();
        bindException = new BindException(new User(), "user");
    }

    @Test
    public void testSupports() throws Exception {
        assertTrue(userValidator.supports(User.class));
    }

    @Test
    public void testSupportsValid() throws Exception {
        assertFalse(userValidator.supports(Object.class));
    }

    @Test
    public void testValidateWhenValidUser() throws Exception {
        User user = new User();
        user.setName("sjyuan");
        user.setPassword(new BCryptPasswordEncoder().encode("000"));
        user.setAge(25);
        user.setPhone("18192235667");
        user.setSex(User.Sex.MALE);
        List<Authority.Role> roles = new ArrayList<>();
        roles.add(Authority.Role.ADMIN);
        user.setRoles(roles);
        userValidator.validate(user, bindException);
        assertFalse("Has errors={" + bindException.getAllErrors() + "}", bindException.hasErrors());
    }
}