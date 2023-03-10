package com.example.springhomework;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Test
    void shouldReturnEmptyListWhenNoData() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        List<User> allUsers = userService.findAll();

        assertNotNull(allUsers);
        assertEquals(0, allUsers.size());
    }

    @Test
    void shouldReturnAllUsers() {
        List<User> mockedUsers = asList(
                User.builder().id(0L).age(18L).name("A").createdAt(Instant.now()).updatedAt(Instant.now()).build(),
                User.builder().id(1L).age(19L).name("B").createdAt(Instant.now()).updatedAt(Instant.now()).build()
        );

        when(userRepository.findAll()).thenReturn(mockedUsers);

        List<User> allUsers = userService.findAll();

        assertNotNull(allUsers);
        assertEquals(2, allUsers.size());
        assertEquals(0, allUsers.get(0).getId());
        assertEquals(1, allUsers.get(1).getId());
        assertEquals(18, allUsers.get(0).getAge());
        assertEquals(19, allUsers.get(1).getAge());
        assertEquals("A", allUsers.get(0).getName());
        assertEquals("B", allUsers.get(1).getName());
    }

    @Test
    void shouldGetOnlyFirstUserInFirstPageWithPageSizeIs1() {
        List<User> mockedUsers = asList(
                User.builder().id(0L).age(18L).name("A").createdAt(Instant.now()).updatedAt(Instant.now()).build(),
                User.builder().id(1L).age(19L).name("B").createdAt(Instant.now()).updatedAt(Instant.now()).build()
        );
        PagedListHolder<User> page = new PagedListHolder<>(mockedUsers);
        page.setPageSize(1);
        page.setPage(0);
        Pageable pageable = PageRequest.of(0, 1);
        Page<User> result = new PageImpl<>(page.getPageList(), pageable, page.getPageSize());
        when(userRepository.findAll(pageable)).thenReturn(result);

        List<User> allUsers = userService.findAll(0, 1);

        assertNotNull(allUsers);
        assertEquals(1, allUsers.size());
        assertEquals(0, allUsers.get(0).getId());
        assertEquals(18, allUsers.get(0).getAge());
        assertEquals("A", allUsers.get(0).getName());
    }

    @Test
    void shouldReturnAllUsersWithAge18WithoutPagination() {
        List<User> mockedUsers = asList(
                User.builder().id(0L).age(18L).name("A").createdAt(Instant.now()).updatedAt(Instant.now()).build(),
                User.builder().id(1L).age(19L).name("B").createdAt(Instant.now()).updatedAt(Instant.now()).build()
        );
        when(userRepository.findByAge(18L)).thenReturn(mockedUsers.subList(0,1));

        List<User> allUsers = userService.findByAge(18L);

        assertNotNull(allUsers);
        assertEquals(1, allUsers.size());
        assertEquals(0, allUsers.get(0).getId());
        assertEquals(18, allUsers.get(0).getAge());
        assertEquals("A", allUsers.get(0).getName());
    }

    @Test
    void shouldGetOnlyFirstUserWithAge18InFirstPageWithPageSizeIs1() {
        List<User> mockedUsers = asList(
                User.builder().id(0L).age(18L).name("A").createdAt(Instant.now()).updatedAt(Instant.now()).build(),
                User.builder().id(1L).age(19L).name("B").createdAt(Instant.now()).updatedAt(Instant.now()).build()
        );
        PagedListHolder<User> page = new PagedListHolder<>(mockedUsers);
        page.setPageSize(1);
        page.setPage(0);
        Pageable pageable = PageRequest.of(0, 1);
        Page<User> result = new PageImpl<>(page.getPageList(), pageable, page.getPageSize());
        when(userRepository.findByAge(pageable, 18L)).thenReturn(result);

        List<User> allUsers = userService.findByAge(0, 1, 18L);

        assertNotNull(allUsers);
        assertEquals(1, allUsers.size());
        assertEquals(0, allUsers.get(0).getId());
        assertEquals(18, allUsers.get(0).getAge());
        assertEquals("A", allUsers.get(0).getName());
    }

    @Test
    void shouldReturnAllUsersWithNameAWithoutPagination() {
        List<User> mockedUsers = asList(
                User.builder().id(0L).age(18L).name("A").createdAt(Instant.now()).updatedAt(Instant.now()).build(),
                User.builder().id(1L).age(19L).name("B").createdAt(Instant.now()).updatedAt(Instant.now()).build()
        );
        when(userRepository.findByName("A")).thenReturn(mockedUsers.subList(0,1));

        List<User> allUsers = userService.findByName("A");

        assertNotNull(allUsers);
        assertEquals(1, allUsers.size());
        assertEquals(0, allUsers.get(0).getId());
        assertEquals(18, allUsers.get(0).getAge());
        assertEquals("A", allUsers.get(0).getName());
    }

    @Test
    void shouldGetOnlyFirstUserWithNameAInFirstPageWithPageSizeIs1() {
        List<User> mockedUsers = asList(
                User.builder().id(0L).age(18L).name("A").createdAt(Instant.now()).updatedAt(Instant.now()).build(),
                User.builder().id(1L).age(19L).name("B").createdAt(Instant.now()).updatedAt(Instant.now()).build()
        );
        PagedListHolder<User> page = new PagedListHolder<>(mockedUsers);
        page.setPageSize(1);
        page.setPage(0);
        Pageable pageable = PageRequest.of(0, 1);
        Page<User> result = new PageImpl<>(page.getPageList(), pageable, page.getPageSize());
        when(userRepository.findByName(pageable, "A")).thenReturn(result);

        List<User> allUsers = userService.findByName(0, 1, "A");

        assertNotNull(allUsers);
        assertEquals(1, allUsers.size());
        assertEquals(0, allUsers.get(0).getId());
        assertEquals(18, allUsers.get(0).getAge());
        assertEquals("A", allUsers.get(0).getName());
    }

    @Test
    void shouldReturnAllUsersBetweenMar7thAndMar9thWithoutPagination() {
        List<User> mockedUsers = asList(
                User.builder().id(0L).age(18L).name("A").createdAt(Instant.now()).updatedAt(Instant.now()).build(),
                User.builder().id(1L).age(19L).name("B").createdAt(Instant.now()).updatedAt(Instant.now()).build()
        );
        Instant start = LocalDate.parse("2023-03-07").atStartOfDay(ZoneId.of("UTC")).toInstant();
        Instant end = LocalDate.parse("2023-03-09").atStartOfDay(ZoneId.of("UTC")).toInstant();
        when(userRepository.findByCreatedAtBetween(start, end)).thenReturn(mockedUsers);

        List<User> allUsers = userService.findAllBetweenDates(start, end);

        assertNotNull(allUsers);
        assertEquals(2, allUsers.size());
        assertEquals(0, allUsers.get(0).getId());
        assertEquals(18, allUsers.get(0).getAge());
        assertEquals("A", allUsers.get(0).getName());
        assertEquals(1, allUsers.get(1).getId());
        assertEquals(19, allUsers.get(1).getAge());
        assertEquals("B", allUsers.get(1).getName());
    }

    @Test
    void shouldGetOnlyFirstUserBetweenMar7thAndMar9thInFirstPageWithPageSizeIs1() {
        List<User> mockedUsers = asList(
                User.builder().id(0L).age(18L).name("A").createdAt(Instant.now()).updatedAt(Instant.now()).build(),
                User.builder().id(1L).age(19L).name("B").createdAt(Instant.now()).updatedAt(Instant.now()).build()
        );
        PagedListHolder<User> page = new PagedListHolder<>(mockedUsers);
        page.setPageSize(1);
        page.setPage(0);
        Pageable pageable = PageRequest.of(0, 1);
        Page<User> result = new PageImpl<>(page.getPageList(), pageable, page.getPageSize());
        Instant start = LocalDate.parse("2023-03-07").atStartOfDay(ZoneId.of("UTC")).toInstant();
        Instant end = LocalDate.parse("2023-03-09").atStartOfDay(ZoneId.of("UTC")).toInstant();
        when(userRepository.findByCreatedAtBetween(pageable, start, end)).thenReturn(result);

        List<User> allUsers = userService.findAllBetweenDates(0, 1, start, end);

        assertNotNull(allUsers);
        assertEquals(1, allUsers.size());
        assertEquals(0, allUsers.get(0).getId());
        assertEquals(18, allUsers.get(0).getAge());
        assertEquals("A", allUsers.get(0).getName());
    }

    @Test
    void shouldSaveSuccessfully() {
        User savedUser = User.builder().id(0L).age(18L).name("A").createdAt(Instant.now()).updatedAt(Instant.now()).build();

        userService.save(savedUser);

        verify(userRepository, times(1)).save(any());
    }

    @Test
    void shouldCallDeleteMethodOnce() {
        User deletedUser = User.builder().id(1L).build();
        doNothing().when(userRepository).delete(deletedUser);

        userService.delete(deletedUser);

        verify(userRepository, times(1)).delete(any());
    }

    @Test
    void shouldUpdateUserSuccessfully() throws Exception {
        User updatedUser = User.builder().id(1L).name("UPDATE").age(100L).build();

        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(updatedUser));

        assert updatedUser != null;
        userService.update(updatedUser);

        verify(userRepository, times(1)).save(any());
    }

}
