package com.example.user.repository;

import com.example.user.domain.entity.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReserveRepository extends JpaRepository<Reserve,Long> {
    List<Reserve> findAllByUserId(Long userId);
    void deleteByUserIdAndCommunityId(Long userId, Long communityId);
    List<Reserve> findAllByCommunityId(Long communityId);

    @Modifying
    @Query("UPDATE Reserve r SET r.communityImgPath = :communityImgPath, r.communityName = :communityName WHERE r.communityId = :communityId")
    void update(Long communityId, String communityImgPath, String communityName);
}
