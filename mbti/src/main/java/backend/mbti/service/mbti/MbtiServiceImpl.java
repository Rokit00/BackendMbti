package backend.mbti.service.mbti;

import backend.mbti.domain.dto.MbtiGroupRequest;
import backend.mbti.domain.mbti.Mbti;
import backend.mbti.repository.mbti.MbtiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MbtiServiceImpl implements MbtiService{

    private final MbtiRepository mbtiRepository;
    @Override
    public boolean saveMbtiData(MbtiGroupRequest mbtiGroupRequest) {
        try {
            Mbti mbti = new Mbti();
            mbti.setMbtiType(mbtiGroupRequest.getMbtiType());
            mbti.setUserName(mbtiGroupRequest.getUserName());
            mbti.setGroupName(mbtiGroupRequest.getGroupName());

            mbtiRepository.save(mbti);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // MBTI 그룹에 사용자들 담기 (섹션2-4 참고)
    @Override
    public Map<String, Map<String, Double>> calculateGroupMatchPercentages(String groupName) {
        List<Mbti> groupMembers = mbtiRepository.findByGroupName(groupName);

        Map<String, Map<String, Double>> groupMatchPercentages = new HashMap<>();

        for (Mbti member : groupMembers) {
            String memberType = member.getMbtiType();
            Map<String, Double> typeMatches = new HashMap<>();

            for (Mbti otherMember : groupMembers) {
                if (!otherMember.equals(member)) {
                    typeMatches.put(otherMember.getMbtiType(), calculateMatchPercentage(memberType, otherMember.getMbtiType()));
                }
            }

            groupMatchPercentages.put(memberType, typeMatches);
        }

        return groupMatchPercentages;
    }

    // 궁합 백분율 계산 (섹션2-4 참고)
    @Override
    public Double calculateMatchPercentage(String mbtiType1, String mbtiType2) {
        if (mbtiType1.equals(mbtiType2)) {
            return 100.0;
        } else {
            return 50.0;
        }
    }
}
