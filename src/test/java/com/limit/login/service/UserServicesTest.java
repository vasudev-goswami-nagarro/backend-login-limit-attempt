package com.limit.login.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.limit.login.model.User;
import com.limit.login.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserServices.class})
@ExtendWith(SpringExtension.class)
class UserServicesTest {
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServices userServices;

    /**
     * Method under test: {@link UserServices#increaseFailedAttempts(User)}
     */
    @Test
    void testIncreaseFailedAttempts() {
        doNothing().when(userRepository).updateFailedAttempts(anyInt(), (String) any());

        User user = new User();
        user.setAccountNonLocked(true);
        user.setEnabled(true);
        user.setFailedAttempt(1);
        user.setId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date fromResult = Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        user.setLockTime(fromResult);
        user.setPassword("iloveyou");
        user.setUsername("janedoe");
        userServices.increaseFailedAttempts(user);
        verify(userRepository).updateFailedAttempts(anyInt(), (String) any());
        assertEquals(1, user.getFailedAttempt());
        assertTrue(user.isEnabled());
        assertTrue(user.isAccountNonLocked());
        assertEquals("janedoe", user.getUsername());
        assertEquals("iloveyou", user.getPassword());
        assertSame(fromResult, user.getLockTime());
        assertEquals(123L, user.getId().longValue());
    }

    /**
     * Method under test: {@link UserServices#resetFailedAttempts(String)}
     */
    @Test
    void testResetFailedAttempts() {
        doNothing().when(userRepository).updateFailedAttempts(anyInt(), (String) any());
        userServices.resetFailedAttempts("janedoe");
        verify(userRepository).updateFailedAttempts(anyInt(), (String) any());
    }

    /**
     * Method under test: {@link UserServices#lockAccount(User)}
     */
    @Test
    void testLockAccount() {
        User user = new User();
        user.setAccountNonLocked(true);
        user.setEnabled(true);
        user.setFailedAttempt(1);
        user.setId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLockTime(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        user.setPassword("iloveyou");
        user.setUsername("janedoe");
        when(userRepository.save((User) any())).thenReturn(user);

        User user1 = new User();
        user1.setAccountNonLocked(true);
        user1.setEnabled(true);
        user1.setFailedAttempt(1);
        user1.setId(123L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setLockTime(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        user1.setPassword("iloveyou");
        user1.setUsername("janedoe");
        userServices.lockAccount(user1);
        verify(userRepository).save((User) any());
        assertFalse(user1.isAccountNonLocked());
    }

    /**
     * Method under test: {@link UserServices#findByUsername(String)}
     */
    @Test
    void testFindByUsername() {
        User user = new User();
        user.setAccountNonLocked(true);
        user.setEnabled(true);
        user.setFailedAttempt(1);
        user.setId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLockTime(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        user.setPassword("iloveyou");
        user.setUsername("janedoe");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByUsername((String) any())).thenReturn(ofResult);
        assertSame(user, userServices.findByUsername("janedoe"));
        verify(userRepository).findByUsername((String) any());
    }

    /**
     * Method under test: {@link UserServices#unlockUserAccount(User)}
     */
    @Test
    void testUnlockUserAccount() {
        User user = new User();
        user.setAccountNonLocked(true);
        user.setEnabled(true);
        user.setFailedAttempt(1);
        user.setId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLockTime(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        user.setPassword("iloveyou");
        user.setUsername("janedoe");
        when(userRepository.save((User) any())).thenReturn(user);

        User user1 = new User();
        user1.setAccountNonLocked(true);
        user1.setEnabled(true);
        user1.setFailedAttempt(1);
        user1.setId(123L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setLockTime(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        user1.setPassword("iloveyou");
        user1.setUsername("janedoe");
        assertTrue(userServices.unlockUserAccount(user1));
        verify(userRepository).save((User) any());
        assertEquals(0, user1.getFailedAttempt());
        assertTrue(user1.isAccountNonLocked());
        assertNull(user1.getLockTime());
    }

    /**
     * Method under test: {@link UserServices#scheduleUnlockAccount(User)}
     */
    @Test
    void testScheduleUnlockAccount() {
        User user = new User();
        user.setAccountNonLocked(true);
        user.setEnabled(true);
        user.setFailedAttempt(1);
        user.setId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date fromResult = Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        user.setLockTime(fromResult);
        user.setPassword("iloveyou");
        user.setUsername("janedoe");
        userServices.scheduleUnlockAccount(user);
        assertEquals(1, user.getFailedAttempt());
        assertTrue(user.isEnabled());
        assertTrue(user.isAccountNonLocked());
        assertEquals("janedoe", user.getUsername());
        assertEquals("iloveyou", user.getPassword());
        assertSame(fromResult, user.getLockTime());
        assertEquals(123L, user.getId().longValue());
    }

    /**
     * Method under test: {@link UserServices#scheduleUnlockAccount(User)}
     */
    @Test
    void testScheduleUnlockAccount2() {
        User user = mock(User.class);
        doNothing().when(user).setAccountNonLocked(anyBoolean());
        doNothing().when(user).setEnabled(anyBoolean());
        doNothing().when(user).setFailedAttempt(anyInt());
        doNothing().when(user).setId((Long) any());
        doNothing().when(user).setLockTime((Date) any());
        doNothing().when(user).setPassword((String) any());
        doNothing().when(user).setUsername((String) any());
        user.setAccountNonLocked(true);
        user.setEnabled(true);
        user.setFailedAttempt(1);
        user.setId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLockTime(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        user.setPassword("iloveyou");
        user.setUsername("janedoe");
        userServices.scheduleUnlockAccount(user);
        verify(user).setAccountNonLocked(anyBoolean());
        verify(user).setEnabled(anyBoolean());
        verify(user).setFailedAttempt(anyInt());
        verify(user).setId((Long) any());
        verify(user).setLockTime((Date) any());
        verify(user).setPassword((String) any());
        verify(user).setUsername((String) any());
    }
}

