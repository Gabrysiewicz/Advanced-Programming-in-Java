package com.example.Java4.controller;

import com.example.Java4.entity.ListItem;
import com.example.Java4.service.ListItemService;
import com.example.Java4.service.ListService;
import com.example.Java4.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import com.example.Java4.utils.Functions;
import java.util.List;

@RequestMapping("/item")
@RestController

public class ListItemController {
    @Autowired
    ListItemService listItemService;
    @Autowired
    ListService listService;

    @PostMapping("/") // localhost:8080/item
    public ListItem addListItem(@RequestBody ListItem listItem) {
        return listItemService.saveListItem(listItem);
    }

    @PutMapping("/{itemId}/list/{listId}")  // localhost:8080/item/1/list/1
    public ResponseEntity<ListItem> assignToList(@PathVariable int itemId, @PathVariable int listId, Authentication authentication) {
        // Get the token
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        String jwtToken = Functions.extractJwtToken(jwtAuthenticationToken);
        // Get the userId
        int userId = TokenService.getUserIdFromToken(jwtToken);
        // Authorize
        if (hasUserAccessToList(userId, listId)) {
            System.out.println("Item does belong to a user: ");
            com.example.Java4.entity.ListItem item = listItemService.assignItemToList(itemId, listId);
            return ResponseEntity.ok(item);
        }
        System.out.println("Item does NOT belong to a user: ");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    // ADMIN
    @GetMapping("/s")  // localhost:8080/items
    public List<ListItem> getListItems() {
        return listItemService.getListItems();
    }

    // USER
    @DeleteMapping("/{id}")  // localhost:8080/item/1
    public ResponseEntity<ListItem> deleteItemById(@PathVariable int id, Authentication authentication) {
        // Get the token
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        String jwtToken = Functions.extractJwtToken(jwtAuthenticationToken);
        // Get the userId
        int userId = TokenService.getUserIdFromToken(jwtToken);
        // Authorize
        if (hasUserAccessToList(userId, id)) {
            System.out.println("Item does belong to a user: ");
            listItemService.deleteItemById(id);
            return ResponseEntity.ok(null);
        }
        System.out.println("Item does NOT belong to a user: ");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("/{id}")  // localhost:8080/item/1
    public ResponseEntity<ListItem> getItemById(@PathVariable int id, Authentication authentication) {
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        String jwtToken = Functions.extractJwtToken(jwtAuthenticationToken);
        int userId = TokenService.getUserIdFromToken(jwtToken);

        ListItem item = listItemService.getItemById(id);
        if(item == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        com.example.Java4.entity.List list = item.getList();

        if (hasUserAccessToList(userId, list.getId())) {
            System.out.println("Item does belong to a user: ");

            return ResponseEntity.ok(item);
        }
        System.out.println("Item does NOT belong to a user: ");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PutMapping("/")  // localhost:8080/item/
    public ResponseEntity<ListItem> updateListItem(@RequestBody ListItem item, Authentication authentication) {
        // Get the token
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        String jwtToken = Functions.extractJwtToken(jwtAuthenticationToken);
        // Get the userId
        int userId = TokenService.getUserIdFromToken(jwtToken);
        int id = item.getId();
        // Authorize
        if (hasUserAccessToList(userId, id)) {
            System.out.println("Item does belong to a user: ");
            listItemService.updateListItem(item);
            return ResponseEntity.ok(item);
        }
        System.out.println("Item does NOT belong to a user: ");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    private boolean hasUserAccessToList(int userId, int listId) {
        // Check if the user has access to the list
        return listService.doesListBelongToUser(listId , userId);
    }
    private boolean doesItemBelongToList(int itemId, int listId) {
        // Check if the item belongs to the list
        return listItemService.doesItemBelongToList(itemId, listId);
    }
}