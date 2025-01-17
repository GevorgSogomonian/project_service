package faang.school.projectservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import faang.school.projectservice.dto.meet.MeetDto;
import faang.school.projectservice.dto.meet.MeetFilterDto;
import faang.school.projectservice.model.meet.MeetStatus;
import faang.school.projectservice.service.MeetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class MeetControllerTest {

    @Mock
    private MeetService meetService;

    @InjectMocks
    private MeetController meetController;

    private MockMvc mockMvc;
    private long teamId;
    private long meetId;
    private MeetDto meetDto;
    private String meetDtoJson;
    private String meetFilterDtoJson;

    @BeforeEach
    public void setUp() throws JsonProcessingException {
        mockMvc = MockMvcBuilders.standaloneSetup(meetController).build();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        teamId = 1L;
        meetId = 2L;
        meetDto = MeetDto.builder()
                .id(meetId)
                .title("title")
                .description("description")
                .location("location")
                .teamId(teamId)
                .status(MeetStatus.ACTIVE)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now())
                .build();
        MeetFilterDto meetFilterDto = MeetFilterDto.builder()
                .build();
        meetDtoJson = objectMapper.writeValueAsString(meetDto);
        meetFilterDtoJson = objectMapper.writeValueAsString(meetFilterDto);
    }

    @Test
    @DisplayName("testing createMeet method")
    public void testCreateMeet() throws Exception {
        mockMvc.perform(post("/api/v1/meet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(meetDtoJson))
                .andExpect(status().isCreated());
        verify(meetService, times(1)).createMeet(meetDto);
    }

    @Test
    @DisplayName("testing updateMeet method")
    public void testUpdateMethod() throws Exception {
        mockMvc.perform(put("/api/v1/meet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(meetDtoJson))
                .andExpect(status().isOk());
        verify(meetService, times(1)).updateMeet(meetDto);
    }

    @Test
    @DisplayName("testing updateMeet method")
    public void testDeleteMethod() throws Exception {
        mockMvc.perform(delete("/api/v1/meet/{meetId}", meetId))
                .andExpect(status().isOk());
        verify(meetService, times(1)).deleteMeet(meetId);
    }

    @Test
    @DisplayName("testing getFilteredMeetsOfTeam method")
    public void testGetFilteredMeetsOfTeam() throws Exception {
        mockMvc.perform(post("/api/v1/meet/filtered/{teamId}", String.valueOf(teamId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(meetFilterDtoJson))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("testing getAllMeetsOfUser method")
    public void testGetAllMeetsOfUser() throws Exception {
        mockMvc.perform(get("/api/v1/meet"))
                .andExpect(status().isOk());
        verify(meetService, times(1)).getAllMeetsOfUser();
    }

    @Test
    @DisplayName("testing getAllMeetsOfUser method")
    public void testGetMeetById() throws Exception {
        mockMvc.perform(get("/api/v1/meet/{meetId}", meetId))
                .andExpect(status().isOk());
        verify(meetService, times(1)).getMeetById(meetId);
    }
}