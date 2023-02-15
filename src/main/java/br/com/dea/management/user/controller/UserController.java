package br.com.dea.management.user.controller;

import br.com.dea.management.user.domain.User;
import br.com.dea.management.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "User Controller", description = "The User API")
public class UserController {
    @Autowired
    UserService userService;

    @Operation(summary = "Load the list of users paginated.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Page or Page Size params not valid"),
            @ApiResponse(responseCode = "500", description = "Error fetching user list"),
    })
    @GetMapping("user/all")
    public Page<User> getAllUsers(@RequestParam Integer page,
                                  @RequestParam Integer pageSize) {
        return this.userService.findAllUsersPaginated(page, pageSize);
    }
}
