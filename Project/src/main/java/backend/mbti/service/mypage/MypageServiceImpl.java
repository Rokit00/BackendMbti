package backend.mbti.service.mypage;

import backend.mbti.domain.dto.mbti.MbtiGroupRequest;
import backend.mbti.domain.dto.mypage.MemberUpdateRequest;
import backend.mbti.domain.dto.post.PostByIdRequest;
import backend.mbti.domain.mbti.Mbti;
import backend.mbti.domain.member.Member;
import backend.mbti.domain.post.Post;
import backend.mbti.repository.mbti.MbtiRepository;
import backend.mbti.repository.member.MemberRepository;
import backend.mbti.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MypageServiceImpl implements MypageService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final MbtiRepository mbtiRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 유저 정보 뷰


    @Override
    public Member getUserInfo(String username) {
        Member member = memberRepository.findAllByUserId(username);
        return member;
    }

    // 회원 정보 수정
    @Override
    public Member updateAllMemberInfo(String userId, MemberUpdateRequest request, String username) {
        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없습니다."));

        if (!member.getUserId().equals(username)) {
            throw new AccessDeniedException("올바르지 못한 접근");
        }

        // 업데이트 로직
        member.setNickName(request.getNickName());
        member.setMbti(request.getMbti());
        member.setEmail(request.getEmail());
        member.setBirthday(request.getBirthday());

        if (request.getCurrentPassword() != null && request.getNewPassword() != null) {
            if (!bCryptPasswordEncoder.matches(request.getCurrentPassword(), member.getPassword())) {
                throw new IllegalArgumentException("비밀번호 틀림!");
            }
            member.setPassword(bCryptPasswordEncoder.encode(request.getNewPassword()));
        }

        return memberRepository.save(member);
    }

    // 프로필 이미지
    @Override
    public void uploadProfilePicture(Long memberId, MultipartFile file) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new EntityNotFoundException("Member not found"));

        try {
            if (!file.isEmpty()) {
                // 파일을 저장할 경로 설정
                String uploadDir = "/path/to/profile/pictures/";

                // 파일 이름 생성
                String fileName = memberId + ".jpg";

                // 파일 저장
                Path filePath = Paths.get(uploadDir, fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                // 프로필 사진 경로 업데이트
                String profilePicturePath = "/profile/pictures/" + fileName;
                member.setProfilePicturePath(profilePicturePath);

                memberRepository.save(member);
            }
        } catch (IOException e) {
            throw new RuntimeException("파일 업로드 실페!", e);
        }
    }

    // 내가 만든 케미
    @Override
    public Mbti createMbtiGroup(MbtiGroupRequest request, String userId) {
        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없습니다."));

        Mbti mbtiGroup = new Mbti();
        mbtiGroup.setMbtiType(request.getMbtiType());
        mbtiGroup.setGroupName(request.getGroupName());
        mbtiGroup.setMember(member);

        return mbtiRepository.save(mbtiGroup);
    }

    // 내가 만든 토론
    @Override
    public List<Post> getPostsByMember(String userId) {
        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없습니다."));

        return postRepository.findByMemberOrderByCreatedAtDesc(member);
    }

    // 문의하기

    // 회원 탈퇴
    @Override
    public void deleteMember(String userId, String username) {
        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없습니다."));

        if (!member.getUserId().equals(username)) {
            throw new AccessDeniedException("잘못된 접근! 회원 탈퇴 불가!");
        }

        memberRepository.delete(member);
    }
}
