package com.example.todo.service;

import com.example.todo.entity.List;
import com.example.todo.entity.ListItem;
import com.example.todo.repository.ListItemRepository;
import com.example.todo.repository.ListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListItemService {
    @Autowired
    ListItemRepository itemRepository;

    @Autowired
    ListRepository listRepository;

    public ListItem saveListItem(ListItem listItem) {
        return itemRepository.save(listItem);
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

    public String deleteItemById(int itemId) {
        itemRepository.deleteById(itemId);
        return "Deleted item:" + itemId;
    }
}
