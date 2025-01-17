package faang.school.projectservice.controller;

import faang.school.projectservice.dto.meet.MeetDto;
import faang.school.projectservice.dto.meet.MeetFilterDto;
import faang.school.projectservice.service.MeetService;
import faang.school.projectservice.validator.meet.CreateMeet;
import faang.school.projectservice.validator.meet.UpdateMeet;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/meet")
@RequiredArgsConstructor
public class MeetController {

    private final MeetService meetService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public MeetDto createMeet(@RequestBody @Validated(CreateMeet.class) MeetDto meetDto) {
        return meetService.createMeet(meetDto);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public MeetDto updateMeet(@RequestBody @Validated(UpdateMeet.class) MeetDto meetDto) {
        return meetService.updateMeet(meetDto);
    }

    @DeleteMapping("/{meetId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteMeet(@PathVariable Long meetId) {
        meetService.deleteMeet(meetId);
    }

    @PostMapping("/filtered/{teamId}")
    @ResponseStatus(HttpStatus.OK)
    public List<MeetDto> getFilteredMeetsOfTeam(@PathVariable Long teamId,
                                                @RequestBody MeetFilterDto meetFilterDto) {
        return meetService.getFilteredMeetsOfTeam(teamId, meetFilterDto);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<MeetDto> getAllMeetsOfUser() {
        return meetService.getAllMeetsOfUser();
    }

    @GetMapping("/{meetId}")
    @ResponseStatus(HttpStatus.OK)
    public MeetDto getMeetById(@PathVariable Long meetId) {
        return meetService.getMeetById(meetId);
    }
}
