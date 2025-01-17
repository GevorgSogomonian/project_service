package faang.school.projectservice.controller.internship;

import faang.school.projectservice.dto.filter.InternshipFilterDto;
import faang.school.projectservice.dto.internship.InternshipDto;
import faang.school.projectservice.service.internship.InternshipService;
import faang.school.projectservice.validator.groups.CreateGroup;
import faang.school.projectservice.validator.groups.UpdateGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/internships")
@RequiredArgsConstructor
@Validated
public class InternshipController {
    private final InternshipService internshipService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InternshipDto createInternship(@RequestBody @Validated(CreateGroup.class) InternshipDto internshipDto) {
        return internshipService.createInternship(internshipDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public InternshipDto updateInternship(@PathVariable long id, @RequestBody @Validated(UpdateGroup.class) InternshipDto internshipDto) {
        return internshipService.updateInternship(id, internshipDto);
    }

    @GetMapping("/filter")
    @ResponseStatus(HttpStatus.OK)
    public List<InternshipDto> getInternshipsByFilter(@RequestBody InternshipFilterDto internshipFilterDto) {
        return internshipService.getInternshipsByFilter(internshipFilterDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InternshipDto> getAllInternships() {
        return internshipService.getAllInternships();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public InternshipDto getInternshipById(@PathVariable long id) {
        return internshipService.getInternshipById(id);
    }
}
