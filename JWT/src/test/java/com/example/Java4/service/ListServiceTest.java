package com.example.Java4.service;

import com.example.Java4.entity.List;
import com.example.Java4.entity.ListItem;
import com.example.Java4.entity.User;
import com.example.Java4.repository.ListRepository;
import com.example.Java4.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ListServiceTest {

    @Mock
    private ListRepository listRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ListService listService;

    private List list;

    @BeforeEach
    public void setup() {
        list = List.builder()
                .id(1)
                .hash("exampleHash")
                .isFavorite(true)
                .build();
    }

    @DisplayName("Show all lists")
    @Test
    void getLists() {
        var list1 = List.builder()
                .id(1)
                .hash("list1")
                .isFavorite(true)
                .build();

        given(listRepository.findAll()).willReturn(java.util.List.of(list, list1));

        java.util.List<List> lists = listService.getLists();
        assertThat(lists).isNotNull();
        assertThat(lists.size()).isEqualTo(2);
    }

    @DisplayName("get list by id")
    @Test
    public void givenListId_whenGetListById_thenReturnListObject() {
        given(listRepository.findById(1)).willReturn(Optional.of(list));

        List savedList = listService.getListById(list.getId());

        assertThat(savedList).isNotNull();
        assertThat(savedList).isEqualTo(list);
    }

    @DisplayName("update list")
    @Test
    public void givenListObject_updateRepo() {
        given(listRepository.save(list)).willReturn(list);

        list.setHash("new hash");
        list.setIsFavorite(false);
        List updatedList = listService.updateList(list);

        assertThat(updatedList.getHash()).isEqualTo("new hash");
        assertThat(updatedList.getIsFavorite()).isEqualTo(false);
    }

    @DisplayName("delete list by id")
    @Test
    public void givenListId_whenDeleteList_thenNothing() {
        int id = 1;
        willDoNothing().given(listRepository).deleteById(id);

        listService.deleteList(id);

        verify(listRepository, times(1)).deleteById(id);
    }

    @DisplayName("save list")
    @Test
    public void givenListObject_whenSaveList_thenReturnListObject() {
        given(listRepository.save(list)).willReturn(list);

        List savedList = listService.saveList(list);
        verify(listRepository, times(1)).save(list);

        assertThat(savedList).isNotNull();
        assertThat(savedList).isEqualTo(list);
    }

    @DisplayName("save lists")
    @Test
    public void givenListOfListObjects_whenSaveLists_thenReturnListOfListObjects() {
        java.util.List<List> lists = Arrays.asList(
                list,
                List.builder().id(2).hash("inny hash").build()
        );
        given(listRepository.saveAll(lists)).willReturn(lists);

        java.util.List<List> resultLists = listService.saveLists(lists);

        assertThat(resultLists).hasSize(2).extracting(List::getId).containsExactly(1, 2);
    }

    @DisplayName("show lists items")
    @Test
    public void givenListId_whenGetItemsByListId_thenReturnListOfItems() {
        ListItem item1 = ListItem.builder().name("name").build();
        ListItem item2 = ListItem.builder().name("name2").build();
        Set<ListItem> itemList = new HashSet<>(Arrays.asList(item1, item2));

        given(listRepository.findById(1)).willReturn(Optional.of(List.builder().listItems(itemList).build()));

        java.util.List<ListItem> resultList = listService.getItemsByListId(1);

        assertThat(resultList)
                .hasSize(2)
                .extracting(ListItem::getName)
                .containsExactly("name", "name2");

    }

    @DisplayName("show lists items")
    @Test
    public void givenNonExistingListId_whenGetItemsByListId_thenReturnNull() {


        given(listRepository.findById(1)).willReturn(Optional.empty());

        java.util.List<ListItem> resultList = listService.getItemsByListId(1);

        assertThat(resultList).isNull();

    }

    @DisplayName("assign list to user")
    @Test
    public void givenUserIdAndListId_whenAssignListToUser_thenListAssignedToUser() {
        int userId = 1;
        int listId = 2;

        User user = User.builder().id(userId).lists(new HashSet<>()).build();
        List list = List.builder().id(listId).users(new HashSet<>()).build();

        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        given(listRepository.findById(listId)).willReturn(Optional.of(list));
        given(listRepository.save(list)).willReturn(list);

        List result = listService.assignListToUser(userId, listId);

        assertThat(result).isNotNull();
        assertThat(result.getUsers()).contains(user);
        assertThat(user.getLists()).contains(result);

        verify(userRepository).findById(userId);
        verify(listRepository).findById(listId);
        verify(listRepository).save(list);
    }


}