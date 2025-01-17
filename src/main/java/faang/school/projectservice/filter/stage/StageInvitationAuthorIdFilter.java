package faang.school.projectservice.filter.stage;

import faang.school.projectservice.dto.stage.StageInvitationFilterDto;
import faang.school.projectservice.model.stage_invitation.StageInvitation;

import java.util.stream.Stream;

public class StageInvitationAuthorIdFilter implements StageInvitationFilter {
    @Override
    public boolean isApplicable(StageInvitationFilterDto filter) {
        return filter.getAuthorId() != null;
    }

    @Override
    public void apply(Stream<StageInvitation> stageInvitations, StageInvitationFilterDto filter) {
        stageInvitations
                .filter(stageInvitation -> stageInvitation.getAuthor().getId().equals(filter.getAuthorId()));
    }
}
