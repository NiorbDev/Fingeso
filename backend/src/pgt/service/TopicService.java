package pgt.service;

import pgt.domain.TopicStatus;
import pgt.dto.TopicResponse;
import pgt.repository.ThesisTopicRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class TopicService {
    private final ThesisTopicRepository topics;

    public TopicService(ThesisTopicRepository topics) {
        this.topics = topics;
    }

    public List<TopicResponse> listAvailable() {
        return topics.findByStatusInOrderByPublishedAtDesc(List.of(TopicStatus.AVAILABLE))
                .stream().map(TopicResponse::from).toList();
    }

    public TopicResponse findById(Long id) {
        return topics.findById(id).map(TopicResponse::from)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el tema solicitado."));
    }
}
