package com.example.Java4.service;

import com.example.Java4.entity.List;
import com.example.Java4.entity.ListItem;
import com.example.Java4.repository.ListItemRepository;
import com.example.Java4.repository.ListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ListItemService {
    @Autowired
    ListItemRepository itemRepository;

    @Autowired
    ListRepository listRepository;

    public ListItem saveListItem(ListItem listItem) {
        return itemRepository.save(listItem);
    }

    public ListItem updateListItem(ListItem item) {
        return itemRepository.save(item);
    }

    public java.util.List<ListItem> getListItems() {
        return itemRepository.findAll();
    }

    public ListItem assignItemToList(int itemId, int listId) {
        ListItem item = itemRepository.findById(itemId).orElse(null);
        List list = listRepository.findById(listId).orElse(null);
        System.out.println(item);
        System.out.println(list);

        if(item != null && list != null) {
            item.setList(list);
            list.addListItem(item);
            itemRepository.save(item);
            listRepository.save(list);
        }
        return item;
    }

    @Transactional // for deletion to work in bidirectional
    public String deleteItemById(int itemId) {
        itemRepository.deleteById(itemId);
        return "Deleted item:" + itemId;
    }

    public ListItem getItemById(int id) {
        return itemRepository.findById(id).orElse(null);
    }

    public boolean doesItemBelongToList(int itemId, int listId) {
        // Check if the list with the given ID belongs to the user with the specified username
        return itemRepository.existsByIdAndListId(listId, listId);
    }
}