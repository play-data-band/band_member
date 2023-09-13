package com.example.user.service;

import com.example.user.domain.entity.Reserve;
import com.example.user.domain.request.ReserveRequest;
import com.example.user.domain.request.UpdateRequest;
import com.example.user.domain.response.ReserveResponse;
import com.example.user.repository.ReserveRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReserveService {
    private final ReserveRepository reserveRepository;
    @Transactional
    public void save(Long userId, ReserveRequest request){
        reserveRepository.save(request.toEntity(userId));
    }

    @Transactional
    public List<ReserveResponse> findByUserId(Long userId){
        List<Reserve> allByUserId = reserveRepository.findAllByUserId(userId);
        return allByUserId
                .stream()
                .map(ReserveResponse::new)
                .toList();
    }

    @Transactional
    public void deleteById(Long userId, Long communityId){
        reserveRepository.deleteByUserIdAndCommunityId(userId,communityId);
    }

    @Transactional
    public void updateById(Long communityId, UpdateRequest updateRequest){
        reserveRepository.update(communityId,updateRequest.getCommunityImgPath(),updateRequest.getCommunityName());

    }

}
