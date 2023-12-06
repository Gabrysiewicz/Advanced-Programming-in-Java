package com.example.Java4.service;

import com.example.Java4.entity.List;
import com.example.Java4.entity.ListItem;
import com.example.Java4.repository.ListItemRepository;
import com.example.Java4.repository.ListRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ListItemServiceTest {

    @Mock
    ListItemRepository listItemRepository;

    @Mock
    ListRepository listRepository;

    @InjectMocks
    ListItemService listItemService;

    private ListItem listItem;

    @BeforeEach
    public void setup() {
        this.listItem = ListItem.builder()
                .id(1)
                .name("nazwa itemu 1")
                .isDone(true)
                .description("opis itemu")
                .build();
    }

    @Test
    void saveListItem() {
        given(listItemRepository.save(listItem)).willReturn(listItem);

        ListItem savedItem = listItemService.saveListItem(listItem);

        assertThat(savedItem).isEqualTo(listItem);
        verify(listItemRepository, times(1)).save(listItem);
    }

    @Test
    void updateListItem() {
        given(listItemRepository.save(listItem)).willReturn(listItem);
        listItem.setName("nowe imie");
        listItem.setIsDone(false);

        ListItem resultItem = listItemService.updateListItem(listItem);

        assertThat(resultItem.getName()).isEqualTo("nowe imie");
        assertThat(resultItem.getIsDone()).isEqualTo(false);
        verify(listItemRepository, times(1)).save(listItem);
    }

    @Test
    void getListItems() {
        ListItem item2 = ListItem.builder()
                .id(2)
                .name("drugi item")
                .build();

        given(listItemRepository.findAll()).willReturn(java.util.List.of(listItem, item2));

        java.util.List<ListItem> resultItems = listItemService.getListItems();

        assertThat(resultItems).hasSize(2);
        assertThat(resultItems.get(0)).isEqualTo(listItem);
        assertThat(resultItems.get(1)).isEqualTo(item2);
        verify(listItemRepository, times(1)).findAll();
    }

    @Test
    void assignItemToList() {
        ListItem item = ListItem.builder()
                .id(2)
                .name("item 2")
                .isDone(false)
                .description("description")
                .build();

        List list = List.builder()
                .id(3)
                .name("listname")
                .isFavorite(true)
                .listItems(new HashSet<>())
                .build();

        given(listItemRepository.findById(2)).willReturn(Optional.of(item));
        given(listRepository.findById(3)).willReturn(Optional.of(list));

        ListItem assignedItem = listItemService.assignItemToList(2, 3);

        assertThat(assignedItem.getList()).isEqualTo(list);
        assertThat(list.getListItems()).contains(item);

        verify(listItemRepository, times(1)).save(item);
        verify(listRepository, times(1)).save(list);
    }

    @Test
    void deleteItemById() {
        int itemId = 1;
        willDoNothing().given(listItemRepository).deleteById(itemId);

        String result = listItemService.deleteItemById(itemId);
        // todo: make all delete test somewhat similar
        assertThat(result).isEqualTo("Deleted item:" + itemId);
        verify(listItemRepository, times(1)).deleteById(itemId);
    }

    @Test
    void getItemById() {
        given(listItemRepository.findById(1)).willReturn(Optional.of(listItem));

        ListItem resultItem = listItemService.getItemById(1);

        assertThat(resultItem).isEqualTo(listItem);
        verify(listItemRepository, times(1)).findById(1);
    }
}