package backend.mbti.service.mbti;

import backend.mbti.domain.dto.MbtiGroupRequest;

import java.util.Map;

public interface MbtiService {
    boolean saveMbtiData(MbtiGroupRequest mbtiGroupRequest);

    Map<String, Map<String, Double>> calculateGroupMatchPercentages(String groupName);

    Double calculateMatchPercentage(String mbtiType1, String mbtiType2);
}
