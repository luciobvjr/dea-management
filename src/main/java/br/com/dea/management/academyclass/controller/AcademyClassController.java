package br.com.dea.management.academyclass.controller;

import br.com.dea.management.academyclass.domain.AcademyClass;
import br.com.dea.management.academyclass.dto.AcademyClassDto;
import br.com.dea.management.academyclass.dto.CreateAcademyClassRequestDto;
import br.com.dea.management.academyclass.dto.UpdateAcademyClassRequestDto;
import br.com.dea.management.academyclass.service.AcademyClassService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@Tag(name = "Academy Class", description = "The Academy class API")
public class AcademyClassController {
    @Autowired
    AcademyClassService academyClassService;

    @Operation(summary = "Load the list of academyClass paginated.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Page or Page Size params not valid"),
            @ApiResponse(responseCode = "500", description = "Error fetching academyClass list"),
    })
    @GetMapping("academy-class")
    public Page<AcademyClassDto> getAcademyClass(@RequestParam Integer page,
                                                 @RequestParam Integer pageSize) {
        log.info(String.format("Fetching academyClass : page : %s : pageSize : %s", page, pageSize));
        Page<AcademyClass> academyClasses = academyClassService.findAllAcademyClassPaginated(page, pageSize);
        log.info("AcademyClasses loaded successfully");

        return academyClasses.map(AcademyClassDto::fromAcademyClass);
    }

    @Operation(summary = "Load academyClass by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "AcademyClass id not valid"),
            @ApiResponse(responseCode = "404", description = "AcademyClass not found"),
            @ApiResponse(responseCode = "500", description = "Error fetching academyClass list"),
    })
    @GetMapping("academy-class/{id}")
    public AcademyClassDto getAcademyClass(@PathVariable Long id) {
        log.info(String.format("Fetching academyClass with id : %s ", id));
        AcademyClassDto academyClass = AcademyClassDto.fromAcademyClass(this.academyClassService.findAcademyClassById(id));
        log.info(String.format("AcademyClass loaded successfully with id : %s", academyClass.getId()));

        return academyClass;
    }

    @Operation(summary = "Create a new academy class")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Payload not valid"),
            @ApiResponse(responseCode = "500", description = "Error creating academy class")
    })
    @PostMapping("academy-class")
    public void createAcademyClass(@Valid @RequestBody CreateAcademyClassRequestDto createAcademyClassRequestDto) {
        log.info(String.format("Creating academy class : Payload : %s", createAcademyClassRequestDto));

        AcademyClass academyClass = academyClassService.createAcademyClass(createAcademyClassRequestDto);

        log.info(String.format("Academy class created successfully : id : %s", academyClass.getId()));
    }

    @Operation(summary = "Update an academy class")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Payload not valid"),
            @ApiResponse(responseCode = "404", description = "Academy class not found"),
            @ApiResponse(responseCode = "500", description = "Error updating academy class")
    })
    @PutMapping("academy-class/{academyClassId}")
    public void updateAcademyClass(@PathVariable Long academyClassId, @Valid @RequestBody UpdateAcademyClassRequestDto updateAcademyClassRequestDto) {
        log.info(String.format("Updating academy class : Payload : %s", updateAcademyClassRequestDto));

        AcademyClass academyClass = academyClassService.updateAcademyClass(academyClassId, updateAcademyClassRequestDto);

        log.info(String.format("Academy class updated successfully : id : %s", academyClass.getId()));
    }

    @Operation(summary = "Delete a academy class")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Payload not valid"),
            @ApiResponse(responseCode = "404", description = "Academy class not found"),
            @ApiResponse(responseCode = "500", description = "Error deleting academy class"),
    })
    @DeleteMapping("/academy-class/{academyClassId}")
    public void deleteAcademyClass(@PathVariable Long academyClassId) {
        log.info(String.format("Removing academy class : Id : %s", academyClassId));

        academyClassService.deleteAcademyClass(academyClassId);

        log.info(String.format("Academy class removed successfully : id : %s", academyClassId));
    }
}
