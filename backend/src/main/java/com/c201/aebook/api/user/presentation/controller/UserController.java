package com.c201.aebook.api.user.presentation.controller;

import com.c201.aebook.api.common.BaseResponse;
import com.c201.aebook.api.user.persistence.entity.UserEntity;
import com.c201.aebook.api.user.presentation.dto.request.UserUpdateRequestDTO;
import com.c201.aebook.api.user.presentation.dto.response.UserResponeDTO;
import com.c201.aebook.api.user.service.impl.UserServiceImpl;
import com.c201.aebook.api.vo.UserSO;
import com.c201.aebook.auth.CustomUserDetails;
import com.c201.aebook.converter.UserConverter;
import com.c201.aebook.utils.S3Uploader;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name="회원관리")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userService;
    private final S3Uploader s3Uploader;
    private final UserConverter userConverter;

    @Operation(summary = "닉네임 중복 검사", description = "사용자의 닉네임 중복 검사를 합니다.")
    @GetMapping(path = "/exists")
    public BaseResponse<?> checkUserNickname(
            @RequestParam(name = "nickname") String nickname
    ) {
        userService.duplicatedUserByNickname(nickname); // 닉네임 중복 검사\
        return new BaseResponse<>(true, HttpStatus.OK.value(), "닉네임 중복 없음");
    }

    @Operation(summary = "사용자 정보 수정", description = "사용자의 닉네임과 프로필 이미지를 변경합니다.")
    @PatchMapping(
            path = "/info",
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE }
    )
    public BaseResponse<?> modifyUserInfo(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestPart(value="content") UserUpdateRequestDTO userUpdateRequestDTO,
            @RequestPart(value="imgUrl", required = false) MultipartFile multipartFile
    ) throws IOException {
        // 1. 사용자 아이디로 S3 디렉토리 설정
        Long userId = Long.parseLong(customUserDetails.getUsername());
        String dirName = String.valueOf(userId) + "/profile";

        // 2. 사용자가 이미지를 변경하였다면 새로 이미지 업로드, 아니라면 이전 이미지 가져오기
        String profileUrl;
        if (multipartFile!=null) {
            profileUrl = s3Uploader.upload(multipartFile, dirName);
        } else {
            // 파일을 받지 않았으면 이전 이미지
            profileUrl = userService.getProfileImage(userId);
        }
        // log.info("profileUrl : {}", profileUrl);

        // 3. 사용자 정보 수정
        UserSO userSO = userConverter.toUserSO(userUpdateRequestDTO.getNickname(), profileUrl);
        UserResponeDTO userResponeDTO = userService.updateUserInfo(userId, userSO);

        return new BaseResponse<>(userResponeDTO, HttpStatus.OK.value(), "사용자 정보 변경");
    }



}