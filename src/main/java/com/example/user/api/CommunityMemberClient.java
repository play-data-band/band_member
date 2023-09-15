package com.example.user.api;

import com.example.user.domain.request.CommunityMemberRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("communitymember-service")
public interface CommunityMemberClient {
    @PutMapping("/api/v1/communitymember/updatemember/{memberId}")
    void updateMemberInCommunityMember(
            @RequestBody CommunityMemberRequest communityMemberRequest,
            @PathVariable("memberId") Long memberId
    );
}
