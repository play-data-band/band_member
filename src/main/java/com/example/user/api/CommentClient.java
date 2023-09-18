package com.example.user.api;

import com.example.user.domain.request.AlbumUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("BAND-COMMENT-SERVICE")
public interface CommentClient {
    @PutMapping("/api/v1/comment/updateMemberInComment/{memberId}")
    void updateComment(@PathVariable("memberId") Long memberId,
                       @RequestBody AlbumUpdateRequest albumUpdateRequest);
}
