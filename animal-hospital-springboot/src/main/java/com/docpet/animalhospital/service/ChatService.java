package com.docpet.animalhospital.service;

import com.docpet.animalhospital.service.dto.ChatRequestDTO;
import com.docpet.animalhospital.service.dto.ChatResponseDTO;
import com.docpet.animalhospital.service.dto.DiseaseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ChatService {

    private static final Logger LOG = LoggerFactory.getLogger(ChatService.class);

    private final DiseaseSearchService diseaseSearchService;
    private final AIService aiService;

    public ChatService(
        DiseaseSearchService diseaseSearchService,
        AIService aiService
    ) {
        this.diseaseSearchService = diseaseSearchService;
        this.aiService = aiService;
    }

    /**
     * Xử lý chat message từ anonymous user
     * @param request Chat request
     * @return Chat response
     */
    public ChatResponseDTO processMessage(ChatRequestDTO request) {
        LOG.info("Processing chat message: {}", request.getMessage());

        // Generate hoặc reuse sessionId
        String sessionId = request.getSessionId();
        if (sessionId == null || sessionId.isEmpty()) {
            sessionId = UUID.randomUUID().toString();
        }

        try {
            String message = request.getMessage().trim();
            
            // 1. Xử lý các câu chào hỏi, cảm ơn, tạm biệt thông thường
            String greetingResponse = handleGreetingsAndCommonPhrases(message);
            if (greetingResponse != null) {
                ChatResponseDTO response = new ChatResponseDTO(greetingResponse, sessionId);
                return response;
            }

            // 2. Detect species từ message hoặc conversation history
            String species = detectSpecies(message);
            if (species == null && request.getConversationHistory() != null) {
                // Tìm species trong lịch sử conversation
                for (String historyMsg : request.getConversationHistory()) {
                    species = detectSpecies(historyMsg);
                    if (species != null) break;
                }
            }
            
            LOG.info("Detected species: {} (from message: '{}')", species, message);
            if (species == null) {
                LOG.warn("Could not detect species from message: '{}'", message);
            }

            // 3. Kiểm tra xem có phải câu hỏi về thú y không
            boolean isVeterinaryQuestion = isVeterinaryRelatedQuestion(message);
            
            // 4. Nếu là câu hỏi về bệnh/triệu chứng, search disease
            // Sử dụng thuật toán Multi-stage Sequential Matching mới
            // Thuật toán này sẽ tự động detect species và tìm disease keywords sau vị trí species
            List<DiseaseDTO> diseaseList = List.of();
            if (isVeterinaryQuestion) {
                LOG.info("Using Multi-stage Sequential Matching for message: '{}'", message);
                diseaseList = diseaseSearchService.searchDiseaseSequential(message);
                
                // Update species từ thuật toán mới (nếu có)
                if (diseaseList.isEmpty()) {
                    LOG.warn("Sequential matching found no results for message: '{}'", message);
                }
            }
            
            LOG.info("Found {} disease results from database", diseaseList.size());
            if (!diseaseList.isEmpty()) {
                LOG.info("Disease titles: {}", 
                    diseaseList.stream()
                        .map(DiseaseDTO::getTitle)
                        .collect(Collectors.toList()));
            }

            // 5. Build prompt với context và conversation history
            String prompt = buildPrompt(message, diseaseList, species, request.getConversationHistory());

            // 6. Generate response từ AI
            String aiResponse;
            if (aiService.isAvailable()) {
                try {
                    aiResponse = aiService.generateResponse(prompt, null);
                    LOG.debug("AI response generated successfully");
                } catch (Exception e) {
                    LOG.error("Error generating AI response, using fallback", e);
                    aiResponse = buildFallbackResponse(diseaseList, message, species);
                }
            } else {
                LOG.warn("AI service not available, using fallback");
                aiResponse = buildFallbackResponse(diseaseList, message, species);
            }

            // 5. Create response
            ChatResponseDTO response = new ChatResponseDTO(aiResponse, sessionId);
            return response;

        } catch (Exception e) {
            LOG.error("Error processing chat message", e);
            // Return error message
            ChatResponseDTO errorResponse = new ChatResponseDTO();
            errorResponse.setSessionId(sessionId);
            errorResponse.setResponse(
                "Xin lỗi, đã có lỗi xảy ra khi xử lý câu hỏi của bạn. " +
                "Vui lòng thử lại sau hoặc liên hệ trực tiếp với bác sĩ thú y."
            );
            return errorResponse;
        }
    }

    /**
     * Detect species từ message
     * Normalize tiếng Việt để nhận diện tốt hơn
     */
    private String detectSpecies(String message) {
        if (message == null || message.isEmpty()) {
            LOG.debug("detectSpecies: message is null or empty");
            return null;
        }

        String lowerMessage = message.toLowerCase();
        String normalizedMessage = normalizeVietnamese(lowerMessage);
        LOG.debug("detectSpecies: original='{}', lower='{}', normalized='{}'", message, lowerMessage, normalizedMessage);
        
        // Check for "chó" or "dog" - check cả có dấu và không dấu
        if (lowerMessage.contains("chó") || lowerMessage.contains("cho") || 
            normalizedMessage.contains("cho") ||
            lowerMessage.contains("dog") || 
            lowerMessage.contains("cún") || lowerMessage.contains("cun") ||
            normalizedMessage.contains("cun") ||
            lowerMessage.contains("cẩu") || lowerMessage.contains("cau") ||
            normalizedMessage.contains("cau")) {
            return "Chó";
        }
        
        // Check for "mèo" or "cat" - check cả có dấu và không dấu
        if (lowerMessage.contains("mèo") || lowerMessage.contains("meo") ||
            normalizedMessage.contains("meo") ||
            lowerMessage.contains("cat") || 
            lowerMessage.contains("mèo con") || lowerMessage.contains("meo con") ||
            normalizedMessage.contains("meo con") ||
            lowerMessage.contains("kitten")) {
            return "Mèo";
        }

        // Check for "chim" or "bird" - check cả có dấu và không dấu
        if (lowerMessage.contains("chim") || 
            normalizedMessage.contains("chim") ||
            lowerMessage.contains("bird")) {
            return "Chim";
        }

        // Check for "rùa" or "turtle"
        if (lowerMessage.contains("rùa") || lowerMessage.contains("rua") ||
            normalizedMessage.contains("rua") ||
            lowerMessage.contains("turtle") || lowerMessage.contains("tortoise")) {
            return "Rùa";
        }

        // Check for "rắn" or "snake"
        if (lowerMessage.contains("rắn") || lowerMessage.contains("ran") ||
            normalizedMessage.contains("ran") ||
            lowerMessage.contains("snake")) {
            return "Rắn";
        }

        // Check for "lợn" or "pig"
        if (lowerMessage.contains("lợn") || lowerMessage.contains("lon") ||
            normalizedMessage.contains("lon") ||
            lowerMessage.contains("heo") || lowerMessage.contains("pig")) {
            return "Lợn";
        }

        // Check for "dê" or "goat"
        if (lowerMessage.contains("dê") || lowerMessage.contains("de") ||
            normalizedMessage.contains("de") ||
            lowerMessage.contains("goat")) {
            return "Dê";
        }

        // Check for "cừu" or "sheep"
        if (lowerMessage.contains("cừu") || lowerMessage.contains("cuu") ||
            normalizedMessage.contains("cuu") ||
            lowerMessage.contains("sheep")) {
            return "Cừu";
        }

        // Check for "bò" or "cow"
        if (lowerMessage.contains("bò") || lowerMessage.contains("bo") ||
            normalizedMessage.contains("bo") ||
            lowerMessage.contains("cow")) {
            return "Bò";
        }

        // Check for "trâu" or "buffalo"
        if (lowerMessage.contains("trâu") || lowerMessage.contains("trau") ||
            normalizedMessage.contains("trau") ||
            lowerMessage.contains("buffalo")) {
            return "Trâu";
        }

        // Check for "khỉ" or "monkey"
        if (lowerMessage.contains("khỉ") || lowerMessage.contains("khi") ||
            normalizedMessage.contains("khi") ||
            lowerMessage.contains("monkey")) {
            return "Khỉ";
        }

        // Check for "cá" or "fish"
        if (lowerMessage.contains("cá") || lowerMessage.contains("ca") ||
            normalizedMessage.contains("ca") ||
            lowerMessage.contains("fish")) {
            return "Cá";
        }

        // Check for "chuột" or "mouse"
        if (lowerMessage.contains("chuột") || lowerMessage.contains("chuot") ||
            normalizedMessage.contains("chuot") ||
            lowerMessage.contains("mouse") || lowerMessage.contains("rat")) {
            return "Chuột";
        }

        // Check for "thỏ" or "rabbit" - check cả có dấu và không dấu
        if (lowerMessage.contains("thỏ") || lowerMessage.contains("tho") ||
            normalizedMessage.contains("tho") ||
            lowerMessage.contains("rabbit")) {
            return "Thỏ";
        }

        return null; // Không detect được
    }

    /**
     * Normalize tiếng Việt - loại bỏ dấu để so sánh tốt hơn
     */
    private String normalizeVietnamese(String text) {
        if (text == null) return "";
        
        return text
            .replaceAll("[àáạảãâầấậẩẫăằắặẳẵ]", "a")
            .replaceAll("[èéẹẻẽêềếệểễ]", "e")
            .replaceAll("[ìíịỉĩ]", "i")
            .replaceAll("[òóọỏõôồốộổỗơờớợởỡ]", "o")
            .replaceAll("[ùúụủũưừứựửữ]", "u")
            .replaceAll("[ỳýỵỷỹ]", "y")
            .replaceAll("[đ]", "d")
            .replaceAll("[ÀÁẠẢÃÂẦẤẬẨẪĂẰẮẶẲẴ]", "A")
            .replaceAll("[ÈÉẸẺẼÊỀẾỆỂỄ]", "E")
            .replaceAll("[ÌÍỊỈĨ]", "I")
            .replaceAll("[ÒÓỌỎÕÔỒỐỘỔỖƠỜỚỢỞỠ]", "O")
            .replaceAll("[ÙÚỤỦŨƯỪỨỰỬỮ]", "U")
            .replaceAll("[ỲÝỴỶỸ]", "Y")
            .replaceAll("[Đ]", "D");
    }

    /**
     * Xử lý các câu chào hỏi, cảm ơn, tạm biệt
     */
    private String handleGreetingsAndCommonPhrases(String message) {
        String lowerMessage = message.toLowerCase().trim();
        
        // Chào hỏi
        if (lowerMessage.matches(".*(xin chào|chào|hello|hi|hey|chào bạn|chào bot).*")) {
            return "Xin chào! 👋 Rất vui được gặp bạn! 😊\n\n" +
                   "Tôi là bác sĩ thú y AI, tôi có thể giúp bạn tư vấn về sức khỏe các loài động vật như chó, mèo, chim, rùa, rắn và nhiều loài khác.\n\n" +
                   "Bạn có thể hỏi tôi bất cứ điều gì về thú cưng của bạn nhé! 💬";
        }
        
        // Cảm ơn
        if (lowerMessage.matches(".*(cảm ơn|cám ơn|thanks|thank you|thank|tks).*")) {
            return "Không có gì đâu! 😊 Rất vui được giúp đỡ bạn.\n\n" +
                   "Nếu bạn còn có câu hỏi nào khác về thú cưng, cứ hỏi tôi nhé! " +
                   "Tôi luôn sẵn sàng hỗ trợ bạn. 🐾";
        }
        
        // Tạm biệt
        if (lowerMessage.matches(".*(tạm biệt|bye|goodbye|chào tạm biệt|hẹn gặp lại).*")) {
            return "Tạm biệt bạn! 👋\n\n" +
                   "Chúc bạn và thú cưng luôn khỏe mạnh! 🐕🐈\n" +
                   "Nếu có gì cần hỏi thêm, cứ quay lại nhé! 😊";
        }
        
        // Hỏi tên
        if (lowerMessage.matches(".*(bạn tên gì|tên của bạn|who are you|bạn là ai).*")) {
            return "Tôi là bác sĩ thú y AI! 😊\n\n" +
                   "Tôi được tạo ra để giúp bạn tư vấn về sức khỏe thú cưng. " +
                   "Bạn có thể hỏi tôi về bất kỳ vấn đề nào liên quan đến các loài động vật như chó, mèo, chim, rùa, rắn, lợn, dê, cừu, bò, trâu, khỉ, cá, chuột và nhiều loài khác nhé! 🐾";
        }
        
        return null; // Không phải câu chào hỏi thông thường
    }

    /**
     * Kiểm tra xem có phải câu hỏi liên quan đến thú y không
     */
    private boolean isVeterinaryRelatedQuestion(String message) {
        if (message == null || message.trim().isEmpty()) {
            return false;
        }
        
        String lowerMessage = message.toLowerCase();
        
        // Các từ khóa về thú y
        String[] veterinaryKeywords = {
            "chó", "mèo", "dog", "cat", "cún", "cẩu", "kitten",
            "chim", "bird", "rùa", "rua", "turtle", "rắn", "ran", "snake",
            "lợn", "lon", "heo", "pig", "dê", "de", "goat",
            "cừu", "cuu", "sheep", "bò", "bo", "cow",
            "trâu", "trau", "buffalo", "khỉ", "khi", "monkey",
            "cá", "ca", "fish", "chuột", "chuot", "mouse", "rat",
            "thỏ", "tho", "rabbit",
            "thú cưng", "pet", "thú y", "veterinary",
            "bệnh", "triệu chứng", "symptom", "disease", "illness",
            "nôn", "tiêu chảy", "diarrhea", "vomit", "ốm", "sick",
            "chăm sóc", "care", "dinh dưỡng", "nutrition", "thức ăn", "food",
            "tiêm phòng", "vaccine", "vaccination", "sức khỏe", "health",
            "điều trị", "treatment", "thuốc", "medicine", "khám", "examination",
            "tư vấn", "advice", "hỏi", "question", "giúp", "help",
            "đau", "pain", "mắt", "eye", "mũi", "nose", "tai", "ear",
            "co giật", "động kinh", "convulsion", "seizure", "mệt mỏi", "lờ đờ",
            "sụt cân", "weight loss", "rụng lông", "feather loss", "ngứa", "itchy",
            "gãi", "scratch", "da đỏ", "red skin", "viêm da", "dermatitis"
        };
        
        // Kiểm tra xem có từ khóa nào trong message không
        for (String keyword : veterinaryKeywords) {
            if (lowerMessage.contains(keyword)) {
                return true;
            }
        }
        
        // Nếu là câu hỏi ngắn và có dấu hỏi, có thể là câu hỏi về thú y
        if (message.contains("?") || message.contains("？")) {
            return true;
        }
        
        return false;
    }

    /**
     * Build prompt cho AI với context từ disease database và conversation history
     */
    private String buildPrompt(String userMessage, List<DiseaseDTO> diseaseList, String species, List<String> conversationHistory) {
        StringBuilder prompt = new StringBuilder();

        // System prompt - Cải thiện để thân thiện hơn
        prompt.append("Bạn là một bác sĩ thú y AI chuyên nghiệp, thân thiện và nhiệt tình. ");
        prompt.append("Bạn yêu thú cưng và luôn muốn giúp đỡ chủ nuôi chăm sóc thú cưng tốt nhất.\n\n");
        
        prompt.append("PHONG CÁCH GIAO TIẾP:\n");
        prompt.append("- Luôn thân thiện, ấm áp và đồng cảm với người dùng\n");
        prompt.append("- Sử dụng emoji phù hợp để làm cho cuộc trò chuyện vui vẻ hơn (🐕🐈😊💬)\n");
        prompt.append("- Trả lời tự nhiên như đang nói chuyện với bạn bè\n");
        prompt.append("- Khuyến khích người dùng hỏi thêm nếu còn thắc mắc\n");
        prompt.append("- Nếu người dùng hỏi về chủ đề không liên quan đến thú y, hãy nhẹ nhàng hướng họ về chủ đề thú y\n");
        prompt.append("- Bạn có thể trả lời các câu hỏi về thú y ngay cả khi không biết loài cụ thể (chó hay mèo)\n");
        prompt.append("- Nếu câu hỏi cần biết loài cụ thể, hãy hỏi một cách tự nhiên và thân thiện\n\n");
        
        prompt.append("QUAN TRỌNG VỀ Y TẾ:\n");
        prompt.append("- Luôn nhấn mạnh rằng đây chỉ là tư vấn sơ bộ, không thay thế khám bác sĩ thú y\n");
        prompt.append("- Nếu có triệu chứng nghiêm trọng (ngộ độc, chảy máu, khó thở, co giật), khuyên đến bác sĩ NGAY LẬP TỨC\n");
        prompt.append("- Trả lời bằng tiếng Việt, dễ hiểu, thân thiện\n");
        prompt.append("- Sử dụng thông tin từ database bệnh được cung cấp bên dưới\n\n");
        
        // Thêm conversation history nếu có
        if (conversationHistory != null && !conversationHistory.isEmpty()) {
            prompt.append("LỊCH SỬ CUỘC TRÒ CHUYỆN:\n");
            for (int i = 0; i < conversationHistory.size(); i++) {
                prompt.append(String.format("- Câu %d: %s\n", i + 1, conversationHistory.get(i)));
            }
            prompt.append("\nHãy nhớ context từ các câu hỏi trước để trả lời nhất quán.\n\n");
        }

        // Context từ disease database
        if (!diseaseList.isEmpty()) {
            prompt.append("THÔNG TIN BỆNH LIÊN QUAN (").append(species).append("):\n");
            prompt.append("---\n");
            
            for (int i = 0; i < diseaseList.size(); i++) {
                DiseaseDTO disease = diseaseList.get(i);
                prompt.append(String.format("\n[Bệnh %d]\n", i + 1));
                prompt.append("Tiêu đề: ").append(disease.getTitle()).append("\n");
                
                if (disease.getSeverityLevel() != null) {
                    prompt.append("Mức độ: ").append(disease.getSeverityLevel()).append("\n");
                }
                
                prompt.append("Nội dung: ").append(disease.getContent()).append("\n");
                prompt.append("---\n");
            }
            
            prompt.append("\nHãy sử dụng thông tin trên để trả lời câu hỏi của người dùng.\n\n");
        } else {
            prompt.append("Lưu ý: Không tìm thấy thông tin bệnh cụ thể trong database cho ").append(species).append(". ");
            prompt.append("Hãy trả lời dựa trên kiến thức thú y chung của bạn, ");
            prompt.append("nhưng luôn nhấn mạnh cần tham khảo ý kiến bác sĩ thú y.\n\n");
        }

        // User message
        prompt.append("CÂU HỎI HIỆN TẠI CỦA NGƯỜI DÙNG:\n");
        if (species != null) {
            prompt.append("(Loài thú cưng: ").append(species).append(")\n");
        } else {
            prompt.append("(Chưa xác định loài thú cưng - bạn có thể trả lời chung hoặc hỏi nếu cần)\n");
        }
        prompt.append(userMessage).append("\n\n");
        
        prompt.append("Hãy trả lời một cách thân thiện, tự nhiên và hữu ích. ");
        prompt.append("Nếu câu hỏi liên quan đến thú y nhưng chưa có loài cụ thể, bạn có thể:\n");
        prompt.append("- Trả lời chung cho cả chó và mèo\n");
        prompt.append("- Hoặc hỏi nhẹ nhàng về loài thú cưng nếu cần thiết\n");
        prompt.append("Nếu người dùng hỏi về chủ đề không liên quan đến thú y, hãy nhẹ nhàng hướng họ về chủ đề thú y. ");
        prompt.append("Luôn khuyến khích họ hỏi thêm nếu còn thắc mắc:");

        return prompt.toString();
    }

    /**
     * Fallback response khi AI service không available
     */
    private String buildFallbackResponse(List<DiseaseDTO> diseaseList, String userMessage, String species) {
        if (diseaseList.isEmpty()) {
            // Trả lời thân thiện hơn khi không tìm thấy thông tin
            StringBuilder response = new StringBuilder();
            response.append("Xin chào! 😊 Tôi là bác sĩ thú y AI.\n\n");
            response.append("Tôi hiện chưa tìm thấy thông tin cụ thể về câu hỏi của bạn. ");
            response.append("Bạn có thể mô tả chi tiết hơn về vấn đề của thú cưng không? ");
            if (species != null) {
                response.append("(Tôi biết bạn đang hỏi về ").append(species.toLowerCase()).append(") ");
            }
            response.append("Hoặc bạn có thể hỏi tôi về các chủ đề khác như:\n");
            response.append("🐕 Dinh dưỡng cho chó/mèo\n");
            response.append("🏥 Các bệnh thường gặp\n");
            response.append("💊 Cách chăm sóc thú cưng\n");
            response.append("📋 Lịch tiêm phòng\n\n");
            response.append("Nếu vấn đề nghiêm trọng, vui lòng liên hệ trực tiếp với bác sĩ thú y nhé! 🏥");
            return response.toString();
        }

        // Trả về disease đầu tiên với format thân thiện hơn
        DiseaseDTO firstDisease = diseaseList.get(0);
        StringBuilder response = new StringBuilder();
        
        response.append("Dựa trên câu hỏi của bạn, đây là thông tin tôi tìm được:\n\n");
        response.append("**").append(firstDisease.getTitle()).append("**\n\n");
        response.append(firstDisease.getContent());
        
        response.append("\n\n💡 **Lưu ý:** Đây chỉ là thông tin tham khảo. ");
        response.append("Nếu thú cưng của bạn có triệu chứng nghiêm trọng, ");
        response.append("vui lòng đưa đến bác sĩ thú y ngay lập tức để được khám và điều trị chính xác. 🏥\n\n");
        response.append("Bạn còn có câu hỏi nào khác không? Tôi luôn sẵn sàng giúp đỡ! 😊");

        return response.toString();
    }
}

