package PerfumeOnMe.spring.chatbot.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import PerfumeOnMe.spring.chatbot.domain.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
	// 챗봇 대화 맥락용(10개)
	List<ChatMessage> findTop10ByUserIdOrderByCreatedAtDesc(Long userId);

	// 대화 전체 이력 페이징
	Page<ChatMessage> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
}