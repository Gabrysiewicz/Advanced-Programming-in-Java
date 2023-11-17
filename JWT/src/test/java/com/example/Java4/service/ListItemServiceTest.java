package com.example.Java4.service;

import com.example.Java4.entity.ListItem;
import com.example.Java4.repository.ListItemRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ListItemServiceTest {

    @Mock
    ListItemRepository listItemRepository;

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
    }

    @Test
    void updateListItem() {
        given(listItemRepository.save(listItem)).willReturn(listItem);
        listItem.setName("nowe imie");
        listItem.setIsDone(false);
        ListItem resultItem = listItemService.updateListItem(listItem);

        assertThat(resultItem.getName()).isEqualTo("nowe imie");
        assertThat(resultItem.getIsDone()).isEqualTo(false);


    }

    @Test
    void getListItems() {
    }

    @Test
    void assignItemToList() {
    }

    @Test
    void deleteItemById() {
    }

    @Test
    void getItemById() {
    }
}