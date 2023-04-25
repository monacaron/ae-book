package com.c201.aebook.api.user.service.impl;

import com.c201.aebook.api.user.persistence.entity.UserEntity;
import com.c201.aebook.api.user.presentation.dto.response.UserResponseDTO;
import com.c201.aebook.api.user.service.UserService;
import com.c201.aebook.api.user.persistence.repository.UserRepository;
import com.c201.aebook.api.vo.UserSO;
import com.c201.aebook.converter.UserConverter;
import com.c201.aebook.utils.exception.CustomException;
import com.c201.aebook.utils.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Override
    public void duplicatedUserByNickname(String nickname) {
        // 닉네임 존재 여부를 true, false로 반환
        boolean userNickname = userRepository.existsByNickname(nickname);

        // 닉네임이 존재한다면 중복이므로 에러 던지기
        if(userNickname) {
            throw new CustomException(ErrorCode.DUPLICATE_RESOURCE);
        }
    }

    @Override
    public String getProfileImage(long userId) {
        // 사용자 아이디로 프로필 이미지 찾기
        return userRepository.findProfileUrlById(userId);
    }

    @Override
    @Transactional
    public UserResponseDTO updateUserInfo(Long userId, UserSO userSO) {
        // 1. 사용자 아이디로 user 찾기
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 2. 사용자 정보(nickname, profileUrl) 업데이트
        user.updateUserEntity(userSO.getNickname(), userSO.getProfileUrl());

        // 3. userResponseDTO에 저장
        UserResponseDTO userResponseDTO = userConverter.toUserResponse(user.getNickname(), user.getProfileUrl());

        return userResponseDTO;
    }
}
