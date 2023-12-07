package com.example.Java4.controller;

import com.example.Java4.entity.List;
import com.example.Java4.entity.ListItem;
import com.example.Java4.service.ListService;
import com.example.Java4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import com.example.Java4.service.TokenService;
import com.example.Java4.security.SecurityConfiguration;
import com.example.Java4.utils.Functions;

@RequestMapping("/list")
@RestController
public class ListController {

    @Autowired
    private ListService service;
    @Autowired
    private UserService userService;


    private final SecurityConfiguration securityConfiguration;

    @Autowired
    public ListController(SecurityConfiguration securityConfiguration) {
        this.securityConfiguration = securityConfiguration;
    }

    @PostMapping("/")  // localhost:8080/list/
    public List addList(@RequestBody List list,  Authentication authentication) {
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        String jwtToken = Functions.extractJwtToken(jwtAuthenticationToken);

        int userId = TokenService.getUserIdFromToken(jwtToken);

        List newList = service.saveList(list);
        assignListToUserIllegal(userId, newList.getId());
        System.out.println(newList);
        return newList;
    }

    @PostMapping("/s")  // localhost:8080/list/s
    public java.util.List<List> addLists(@RequestBody java.util.List<List> lists) {
        return service.saveLists(lists);
    }

    // ADMIN
    @GetMapping("/s")   // localhost:8080/list/s
    public java.util.List<List> findAllLists() {
        return service.getLists();
    }
    @GetMapping("/u")   // localhost:8080/list/u
    public java.util.List<List> findAllListsForUser(Authentication authentication) {
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        String jwtToken = Functions.extractJwtToken(jwtAuthenticationToken);

        int userId = TokenService.getUserIdFromToken(jwtToken);
        return service.getListsForUser(userId);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<List> findListById(@PathVariable int id, Authentication authentication) {
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        String jwtToken = Functions.extractJwtToken(jwtAuthenticationToken);

        int userId = TokenService.getUserIdFromToken(jwtToken);
        if (hasUserAccessToList(userId, id)) {
            System.out.println("List does belong to a user: ");
            List list = service.getListById(id);
            return ResponseEntity.ok(list);
        }
        System.out.println("List does NOT belong to a user: ");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
    private boolean hasUserAccessToList(int userId, int listId) {
        return service.doesListBelongToUser(listId, userId);
    }

    @PutMapping("/")  // localhost:8080/list
    public ResponseEntity<List> updateList(@RequestBody List list, Authentication authentication) {
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        String jwtToken = Functions.extractJwtToken(jwtAuthenticationToken);

        int userId = TokenService.getUserIdFromToken(jwtToken);
        int id = list.getId();
        if (hasUserAccessToList(userId, id)) {
            System.out.println("List does belong to a user: ");
            List updatedList = service.updateList(list);
            return ResponseEntity.ok(updatedList);
        }
        System.out.println("List does NOT belong to a user: ");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @DeleteMapping("/{id}")  // localhost:8080/list/1
    public ResponseEntity<List> deleteList(@PathVariable int id, Authentication authentication) {
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        String jwtToken = Functions.extractJwtToken(jwtAuthenticationToken);

        int userId = TokenService.getUserIdFromToken(jwtToken);
        if (hasUserAccessToList(userId, id)) {
            System.out.println("List does belong to a user: ");
            service.deleteList(id);
            return ResponseEntity.ok(null);
        }
        System.out.println("List does NOT belong to a user: ");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping("/{listId}/user/{userId}")  // localhost:8080/list/1/user/1
    public ResponseEntity<List> assignListToUser(@PathVariable int userId, @PathVariable int listId, Authentication authentication) {
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        String jwtToken = Functions.extractJwtToken(jwtAuthenticationToken);

        int userIdJwt = TokenService.getUserIdFromToken(jwtToken);
        if (hasUserAccessToList(userIdJwt, listId)) {
            List list = service.assignListToUser(userId, listId);
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
    private List assignListToUserIllegal(@PathVariable int userId, @PathVariable int listId) {
        return service.assignListToUser(userId, listId);
    }
    @GetMapping("/{listId}/items")  // localhost:8080/list/1/items
    public ResponseEntity<java.util.List<ListItem>> getItemsByListId(@PathVariable int listId, Authentication authentication) {
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        String jwtToken = Functions.extractJwtToken(jwtAuthenticationToken);

        int userId = TokenService.getUserIdFromToken(jwtToken);
        if (hasUserAccessToList(userId, listId)) {
            System.out.println("List does belong to a user: ");
            java.util.List<ListItem> list = service.getItemsByListId(listId);
            return ResponseEntity.ok(list);
        }
        System.out.println("List does NOT belong to a user: ");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
