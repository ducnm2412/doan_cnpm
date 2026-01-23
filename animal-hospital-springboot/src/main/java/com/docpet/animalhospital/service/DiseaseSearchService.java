package com.docpet.animalhospital.service;

import com.docpet.animalhospital.domain.*;
import com.docpet.animalhospital.repository.*;
import com.docpet.animalhospital.service.dto.DiseaseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Service để search bệnh theo loài
 * Logic: Detect species → Map table → Search disease
 */
@Service
@Transactional(readOnly = true)
public class DiseaseSearchService {

    private static final Logger LOG = LoggerFactory.getLogger(DiseaseSearchService.class);
    private static final int DEFAULT_SEARCH_LIMIT = 5;

    private final DiseaseDogRepository diseaseDogRepository;
    private final DiseaseCatRepository diseaseCatRepository;
    private final DiseaseTurtleRepository diseaseTurtleRepository;
    private final DiseaseSnakeRepository diseaseSnakeRepository;
    private final DiseasePigRepository diseasePigRepository;
    private final DiseaseGoatRepository diseaseGoatRepository;
    private final DiseaseSheepRepository diseaseSheepRepository;
    private final DiseaseCowRepository diseaseCowRepository;
    private final DiseaseBuffaloRepository diseaseBuffaloRepository;
    private final DiseaseMonkeyRepository diseaseMonkeyRepository;
    private final DiseaseFishRepository diseaseFishRepository;
    private final DiseaseBirdRepository diseaseBirdRepository;
    private final DiseaseMouseRepository diseaseMouseRepository;
    private final DiseaseRabbitRepository diseaseRabbitRepository;

    public DiseaseSearchService(
        DiseaseDogRepository diseaseDogRepository,
        DiseaseCatRepository diseaseCatRepository,
        DiseaseTurtleRepository diseaseTurtleRepository,
        DiseaseSnakeRepository diseaseSnakeRepository,
        DiseasePigRepository diseasePigRepository,
        DiseaseGoatRepository diseaseGoatRepository,
        DiseaseSheepRepository diseaseSheepRepository,
        DiseaseCowRepository diseaseCowRepository,
        DiseaseBuffaloRepository diseaseBuffaloRepository,
        DiseaseMonkeyRepository diseaseMonkeyRepository,
        DiseaseFishRepository diseaseFishRepository,
        DiseaseBirdRepository diseaseBirdRepository,
        DiseaseMouseRepository diseaseMouseRepository,
        DiseaseRabbitRepository diseaseRabbitRepository
    ) {
        this.diseaseDogRepository = diseaseDogRepository;
        this.diseaseCatRepository = diseaseCatRepository;
        this.diseaseTurtleRepository = diseaseTurtleRepository;
        this.diseaseSnakeRepository = diseaseSnakeRepository;
        this.diseasePigRepository = diseasePigRepository;
        this.diseaseGoatRepository = diseaseGoatRepository;
        this.diseaseSheepRepository = diseaseSheepRepository;
        this.diseaseCowRepository = diseaseCowRepository;
        this.diseaseBuffaloRepository = diseaseBuffaloRepository;
        this.diseaseMonkeyRepository = diseaseMonkeyRepository;
        this.diseaseFishRepository = diseaseFishRepository;
        this.diseaseBirdRepository = diseaseBirdRepository;
        this.diseaseMouseRepository = diseaseMouseRepository;
        this.diseaseRabbitRepository = diseaseRabbitRepository;
    }

    /**
     * Search bệnh dựa trên message và species
     * @param userMessage Câu hỏi của user
     * @param species Tên loài động vật
     * @return Danh sách bệnh liên quan
     */
    public List<DiseaseDTO> searchDisease(String userMessage, String species) {
        LOG.info("Searching disease for message: '{}', species: {}", userMessage, species);

        if (species == null || species.isEmpty()) {
            LOG.warn("Species is null or empty, cannot search disease");
            return List.of();
        }

        // Extract keywords từ message (loại bỏ species name)
        List<String> keywords = extractKeywords(userMessage, species);
        LOG.info("Extracted keywords: {}", keywords);

        if (keywords.isEmpty()) {
            LOG.warn("No keywords extracted from message: {}", userMessage);
            return List.of();
        }

        // Map species → table và search
        List<DiseaseDTO> results;
        String lowerSpecies = species.toLowerCase();
        
        if ("Chó".equalsIgnoreCase(species) || "cho".equalsIgnoreCase(species)) {
            results = searchDiseaseDog(keywords);
        } else if ("Mèo".equalsIgnoreCase(species) || "meo".equalsIgnoreCase(species)) {
            results = searchDiseaseCat(keywords);
        } else if ("Rùa".equalsIgnoreCase(species) || "rua".equalsIgnoreCase(species)) {
            results = searchDiseaseTurtle(keywords);
        } else if ("Rắn".equalsIgnoreCase(species) || "ran".equalsIgnoreCase(species)) {
            results = searchDiseaseSnake(keywords);
        } else if ("Lợn".equalsIgnoreCase(species) || "lon".equalsIgnoreCase(species) || "heo".equalsIgnoreCase(species)) {
            results = searchDiseasePig(keywords);
        } else if ("Dê".equalsIgnoreCase(species) || "de".equalsIgnoreCase(species)) {
            results = searchDiseaseGoat(keywords);
        } else if ("Cừu".equalsIgnoreCase(species) || "cuu".equalsIgnoreCase(species)) {
            results = searchDiseaseSheep(keywords);
        } else if ("Bò".equalsIgnoreCase(species) || "bo".equalsIgnoreCase(species)) {
            results = searchDiseaseCow(keywords);
        } else if ("Trâu".equalsIgnoreCase(species) || "trau".equalsIgnoreCase(species)) {
            results = searchDiseaseBuffalo(keywords);
        } else if ("Khỉ".equalsIgnoreCase(species) || "khi".equalsIgnoreCase(species)) {
            results = searchDiseaseMonkey(keywords);
        } else if ("Cá".equalsIgnoreCase(species) || "ca".equalsIgnoreCase(species)) {
            results = searchDiseaseFish(keywords);
        } else if ("Chim".equalsIgnoreCase(species) || "chim".equalsIgnoreCase(species)) {
            results = searchDiseaseBird(keywords);
        } else if ("Chuột".equalsIgnoreCase(species) || "chuot".equalsIgnoreCase(species)) {
            results = searchDiseaseMouse(keywords);
        } else if ("Thỏ".equalsIgnoreCase(species) || "tho".equalsIgnoreCase(species)) {
            results = searchDiseaseRabbit(keywords);
        } else {
            LOG.warn("Unknown species: {}", species);
            return List.of();
        }

        LOG.info("Found {} disease results", results.size());
        return results;
    }

    /**
     * Search trong table disease_dog
     */
    private List<DiseaseDTO> searchDiseaseDog(List<String> keywords) {
        LOG.info("Searching in disease_dog table with keywords: {}", keywords);
        
        Map<Long, DiseaseDTO> diseaseMap = new HashMap<>();
        Map<Long, Integer> relevanceScores = new HashMap<>();

        Pageable pageable = PageRequest.of(0, 20);

        for (String keyword : keywords) {
            List<DiseaseDog> found = diseaseDogRepository.searchByKeyword(keyword, pageable);
            LOG.info("Found {} items with keyword '{}' in disease_dog", found.size(), keyword);

            for (DiseaseDog disease : found) {
                Long id = disease.getId();
                
                if (!diseaseMap.containsKey(id)) {
                    DiseaseDTO dto = convertToDTO(disease, "Chó");
                    diseaseMap.put(id, dto);
                    relevanceScores.put(id, 0);
                }

                // Tính relevance score
                int score = relevanceScores.get(id);
                String lowerTitle = disease.getTitle() != null ? disease.getTitle().toLowerCase() : "";
                String lowerKeywords = disease.getKeywords() != null ? disease.getKeywords().toLowerCase() : "";
                String lowerContent = disease.getContent() != null ? disease.getContent().toLowerCase() : "";
                String lowerKeyword = keyword.toLowerCase();

                if (lowerTitle.contains(lowerKeyword)) score += 3;
                if (lowerKeywords.contains(lowerKeyword)) score += 2;
                if (lowerContent.contains(lowerKeyword)) score += 1;

                relevanceScores.put(id, score);
            }
        }

        // Sort theo relevance score
        return diseaseMap.values().stream()
            .sorted((a, b) -> {
                int scoreA = relevanceScores.getOrDefault(a.getId(), 0);
                int scoreB = relevanceScores.getOrDefault(b.getId(), 0);
                return Integer.compare(scoreB, scoreA);
            })
            .limit(DEFAULT_SEARCH_LIMIT)
            .collect(Collectors.toList());
    }

    /**
     * Search trong table disease_cat
     */
    private List<DiseaseDTO> searchDiseaseCat(List<String> keywords) {
        LOG.info("Searching in disease_cat table with keywords: {}", keywords);
        
        Map<Long, DiseaseDTO> diseaseMap = new HashMap<>();
        Map<Long, Integer> relevanceScores = new HashMap<>();

        Pageable pageable = PageRequest.of(0, 20);

        for (String keyword : keywords) {
            List<DiseaseCat> found = diseaseCatRepository.searchByKeyword(keyword, pageable);
            LOG.info("Found {} items with keyword '{}' in disease_cat", found.size(), keyword);

            for (DiseaseCat disease : found) {
                Long id = disease.getId();
                
                if (!diseaseMap.containsKey(id)) {
                    DiseaseDTO dto = convertToDTO(disease, "Mèo");
                    diseaseMap.put(id, dto);
                    relevanceScores.put(id, 0);
                }

                // Tính relevance score
                int score = relevanceScores.get(id);
                String lowerTitle = disease.getTitle() != null ? disease.getTitle().toLowerCase() : "";
                String lowerKeywords = disease.getKeywords() != null ? disease.getKeywords().toLowerCase() : "";
                String lowerContent = disease.getContent() != null ? disease.getContent().toLowerCase() : "";
                String lowerKeyword = keyword.toLowerCase();

                if (lowerTitle.contains(lowerKeyword)) score += 3;
                if (lowerKeywords.contains(lowerKeyword)) score += 2;
                if (lowerContent.contains(lowerKeyword)) score += 1;

                relevanceScores.put(id, score);
            }
        }

        // Sort theo relevance score
        return diseaseMap.values().stream()
            .sorted((a, b) -> {
                int scoreA = relevanceScores.getOrDefault(a.getId(), 0);
                int scoreB = relevanceScores.getOrDefault(b.getId(), 0);
                return Integer.compare(scoreB, scoreA);
            })
            .limit(DEFAULT_SEARCH_LIMIT)
            .collect(Collectors.toList());
    }

    /**
     * Search trong table disease_turtle
     */
    private List<DiseaseDTO> searchDiseaseTurtle(List<String> keywords) {
        LOG.info("Searching in disease_turtle table with keywords: {}", keywords);
        
        Map<Long, DiseaseDTO> diseaseMap = new HashMap<>();
        Map<Long, Integer> relevanceScores = new HashMap<>();

        Pageable pageable = PageRequest.of(0, 20);

        for (String keyword : keywords) {
            List<DiseaseTurtle> found = diseaseTurtleRepository.searchByKeyword(keyword, pageable);
            LOG.info("Found {} items with keyword '{}' in disease_turtle", found.size(), keyword);

            for (DiseaseTurtle disease : found) {
                Long id = disease.getId();
                
                if (!diseaseMap.containsKey(id)) {
                    DiseaseDTO dto = convertToDTO(disease, "Rùa");
                    diseaseMap.put(id, dto);
                    relevanceScores.put(id, 0);
                }

                int score = relevanceScores.get(id);
                String lowerTitle = disease.getTitle() != null ? disease.getTitle().toLowerCase() : "";
                String lowerKeywords = disease.getKeywords() != null ? disease.getKeywords().toLowerCase() : "";
                String lowerContent = disease.getContent() != null ? disease.getContent().toLowerCase() : "";
                String lowerKeyword = keyword.toLowerCase();

                if (lowerTitle.contains(lowerKeyword)) score += 3;
                if (lowerKeywords.contains(lowerKeyword)) score += 2;
                if (lowerContent.contains(lowerKeyword)) score += 1;

                relevanceScores.put(id, score);
            }
        }

        return diseaseMap.values().stream()
            .sorted((a, b) -> {
                int scoreA = relevanceScores.getOrDefault(a.getId(), 0);
                int scoreB = relevanceScores.getOrDefault(b.getId(), 0);
                return Integer.compare(scoreB, scoreA);
            })
            .limit(DEFAULT_SEARCH_LIMIT)
            .collect(Collectors.toList());
    }

    /**
     * Search trong table disease_snake
     */
    private List<DiseaseDTO> searchDiseaseSnake(List<String> keywords) {
        LOG.info("Searching in disease_snake table with keywords: {}", keywords);
        
        Map<Long, DiseaseDTO> diseaseMap = new HashMap<>();
        Map<Long, Integer> relevanceScores = new HashMap<>();

        Pageable pageable = PageRequest.of(0, 20);

        for (String keyword : keywords) {
            List<DiseaseSnake> found = diseaseSnakeRepository.searchByKeyword(keyword, pageable);
            LOG.info("Found {} items with keyword '{}' in disease_snake", found.size(), keyword);

            for (DiseaseSnake disease : found) {
                Long id = disease.getId();
                
                if (!diseaseMap.containsKey(id)) {
                    DiseaseDTO dto = convertToDTO(disease, "Rắn");
                    diseaseMap.put(id, dto);
                    relevanceScores.put(id, 0);
                }

                int score = relevanceScores.get(id);
                String lowerTitle = disease.getTitle() != null ? disease.getTitle().toLowerCase() : "";
                String lowerKeywords = disease.getKeywords() != null ? disease.getKeywords().toLowerCase() : "";
                String lowerContent = disease.getContent() != null ? disease.getContent().toLowerCase() : "";
                String lowerKeyword = keyword.toLowerCase();

                if (lowerTitle.contains(lowerKeyword)) score += 3;
                if (lowerKeywords.contains(lowerKeyword)) score += 2;
                if (lowerContent.contains(lowerKeyword)) score += 1;

                relevanceScores.put(id, score);
            }
        }

        return diseaseMap.values().stream()
            .sorted((a, b) -> {
                int scoreA = relevanceScores.getOrDefault(a.getId(), 0);
                int scoreB = relevanceScores.getOrDefault(b.getId(), 0);
                return Integer.compare(scoreB, scoreA);
            })
            .limit(DEFAULT_SEARCH_LIMIT)
            .collect(Collectors.toList());
    }

    /**
     * Search trong table disease_pig
     */
    private List<DiseaseDTO> searchDiseasePig(List<String> keywords) {
        LOG.info("Searching in disease_pig table with keywords: {}", keywords);
        
        Map<Long, DiseaseDTO> diseaseMap = new HashMap<>();
        Map<Long, Integer> relevanceScores = new HashMap<>();

        Pageable pageable = PageRequest.of(0, 20);

        for (String keyword : keywords) {
            List<DiseasePig> found = diseasePigRepository.searchByKeyword(keyword, pageable);
            LOG.info("Found {} items with keyword '{}' in disease_pig", found.size(), keyword);

            for (DiseasePig disease : found) {
                Long id = disease.getId();
                
                if (!diseaseMap.containsKey(id)) {
                    DiseaseDTO dto = convertToDTO(disease, "Lợn");
                    diseaseMap.put(id, dto);
                    relevanceScores.put(id, 0);
                }

                int score = relevanceScores.get(id);
                String lowerTitle = disease.getTitle() != null ? disease.getTitle().toLowerCase() : "";
                String lowerKeywords = disease.getKeywords() != null ? disease.getKeywords().toLowerCase() : "";
                String lowerContent = disease.getContent() != null ? disease.getContent().toLowerCase() : "";
                String lowerKeyword = keyword.toLowerCase();

                if (lowerTitle.contains(lowerKeyword)) score += 3;
                if (lowerKeywords.contains(lowerKeyword)) score += 2;
                if (lowerContent.contains(lowerKeyword)) score += 1;

                relevanceScores.put(id, score);
            }
        }

        return diseaseMap.values().stream()
            .sorted((a, b) -> {
                int scoreA = relevanceScores.getOrDefault(a.getId(), 0);
                int scoreB = relevanceScores.getOrDefault(b.getId(), 0);
                return Integer.compare(scoreB, scoreA);
            })
            .limit(DEFAULT_SEARCH_LIMIT)
            .collect(Collectors.toList());
    }

    /**
     * Search trong table disease_goat
     */
    private List<DiseaseDTO> searchDiseaseGoat(List<String> keywords) {
        LOG.info("Searching in disease_goat table with keywords: {}", keywords);
        
        Map<Long, DiseaseDTO> diseaseMap = new HashMap<>();
        Map<Long, Integer> relevanceScores = new HashMap<>();

        Pageable pageable = PageRequest.of(0, 20);

        for (String keyword : keywords) {
            List<DiseaseGoat> found = diseaseGoatRepository.searchByKeyword(keyword, pageable);
            LOG.info("Found {} items with keyword '{}' in disease_goat", found.size(), keyword);

            for (DiseaseGoat disease : found) {
                Long id = disease.getId();
                
                if (!diseaseMap.containsKey(id)) {
                    DiseaseDTO dto = convertToDTO(disease, "Dê");
                    diseaseMap.put(id, dto);
                    relevanceScores.put(id, 0);
                }

                int score = relevanceScores.get(id);
                String lowerTitle = disease.getTitle() != null ? disease.getTitle().toLowerCase() : "";
                String lowerKeywords = disease.getKeywords() != null ? disease.getKeywords().toLowerCase() : "";
                String lowerContent = disease.getContent() != null ? disease.getContent().toLowerCase() : "";
                String lowerKeyword = keyword.toLowerCase();

                if (lowerTitle.contains(lowerKeyword)) score += 3;
                if (lowerKeywords.contains(lowerKeyword)) score += 2;
                if (lowerContent.contains(lowerKeyword)) score += 1;

                relevanceScores.put(id, score);
            }
        }

        return diseaseMap.values().stream()
            .sorted((a, b) -> {
                int scoreA = relevanceScores.getOrDefault(a.getId(), 0);
                int scoreB = relevanceScores.getOrDefault(b.getId(), 0);
                return Integer.compare(scoreB, scoreA);
            })
            .limit(DEFAULT_SEARCH_LIMIT)
            .collect(Collectors.toList());
    }

    /**
     * Search trong table disease_sheep
     */
    private List<DiseaseDTO> searchDiseaseSheep(List<String> keywords) {
        LOG.info("Searching in disease_sheep table with keywords: {}", keywords);
        
        Map<Long, DiseaseDTO> diseaseMap = new HashMap<>();
        Map<Long, Integer> relevanceScores = new HashMap<>();

        Pageable pageable = PageRequest.of(0, 20);

        for (String keyword : keywords) {
            List<DiseaseSheep> found = diseaseSheepRepository.searchByKeyword(keyword, pageable);
            LOG.info("Found {} items with keyword '{}' in disease_sheep", found.size(), keyword);

            for (DiseaseSheep disease : found) {
                Long id = disease.getId();
                
                if (!diseaseMap.containsKey(id)) {
                    DiseaseDTO dto = convertToDTO(disease, "Cừu");
                    diseaseMap.put(id, dto);
                    relevanceScores.put(id, 0);
                }

                int score = relevanceScores.get(id);
                String lowerTitle = disease.getTitle() != null ? disease.getTitle().toLowerCase() : "";
                String lowerKeywords = disease.getKeywords() != null ? disease.getKeywords().toLowerCase() : "";
                String lowerContent = disease.getContent() != null ? disease.getContent().toLowerCase() : "";
                String lowerKeyword = keyword.toLowerCase();

                if (lowerTitle.contains(lowerKeyword)) score += 3;
                if (lowerKeywords.contains(lowerKeyword)) score += 2;
                if (lowerContent.contains(lowerKeyword)) score += 1;

                relevanceScores.put(id, score);
            }
        }

        return diseaseMap.values().stream()
            .sorted((a, b) -> {
                int scoreA = relevanceScores.getOrDefault(a.getId(), 0);
                int scoreB = relevanceScores.getOrDefault(b.getId(), 0);
                return Integer.compare(scoreB, scoreA);
            })
            .limit(DEFAULT_SEARCH_LIMIT)
            .collect(Collectors.toList());
    }

    /**
     * Search trong table disease_cow
     */
    private List<DiseaseDTO> searchDiseaseCow(List<String> keywords) {
        LOG.info("Searching in disease_cow table with keywords: {}", keywords);
        
        Map<Long, DiseaseDTO> diseaseMap = new HashMap<>();
        Map<Long, Integer> relevanceScores = new HashMap<>();

        Pageable pageable = PageRequest.of(0, 20);

        for (String keyword : keywords) {
            List<DiseaseCow> found = diseaseCowRepository.searchByKeyword(keyword, pageable);
            LOG.info("Found {} items with keyword '{}' in disease_cow", found.size(), keyword);

            for (DiseaseCow disease : found) {
                Long id = disease.getId();
                
                if (!diseaseMap.containsKey(id)) {
                    DiseaseDTO dto = convertToDTO(disease, "Bò");
                    diseaseMap.put(id, dto);
                    relevanceScores.put(id, 0);
                }

                int score = relevanceScores.get(id);
                String lowerTitle = disease.getTitle() != null ? disease.getTitle().toLowerCase() : "";
                String lowerKeywords = disease.getKeywords() != null ? disease.getKeywords().toLowerCase() : "";
                String lowerContent = disease.getContent() != null ? disease.getContent().toLowerCase() : "";
                String lowerKeyword = keyword.toLowerCase();

                if (lowerTitle.contains(lowerKeyword)) score += 3;
                if (lowerKeywords.contains(lowerKeyword)) score += 2;
                if (lowerContent.contains(lowerKeyword)) score += 1;

                relevanceScores.put(id, score);
            }
        }

        return diseaseMap.values().stream()
            .sorted((a, b) -> {
                int scoreA = relevanceScores.getOrDefault(a.getId(), 0);
                int scoreB = relevanceScores.getOrDefault(b.getId(), 0);
                return Integer.compare(scoreB, scoreA);
            })
            .limit(DEFAULT_SEARCH_LIMIT)
            .collect(Collectors.toList());
    }

    /**
     * Search trong table disease_buffalo
     */
    private List<DiseaseDTO> searchDiseaseBuffalo(List<String> keywords) {
        LOG.info("Searching in disease_buffalo table with keywords: {}", keywords);
        
        Map<Long, DiseaseDTO> diseaseMap = new HashMap<>();
        Map<Long, Integer> relevanceScores = new HashMap<>();

        Pageable pageable = PageRequest.of(0, 20);

        for (String keyword : keywords) {
            List<DiseaseBuffalo> found = diseaseBuffaloRepository.searchByKeyword(keyword, pageable);
            LOG.info("Found {} items with keyword '{}' in disease_buffalo", found.size(), keyword);

            for (DiseaseBuffalo disease : found) {
                Long id = disease.getId();
                
                if (!diseaseMap.containsKey(id)) {
                    DiseaseDTO dto = convertToDTO(disease, "Trâu");
                    diseaseMap.put(id, dto);
                    relevanceScores.put(id, 0);
                }

                int score = relevanceScores.get(id);
                String lowerTitle = disease.getTitle() != null ? disease.getTitle().toLowerCase() : "";
                String lowerKeywords = disease.getKeywords() != null ? disease.getKeywords().toLowerCase() : "";
                String lowerContent = disease.getContent() != null ? disease.getContent().toLowerCase() : "";
                String lowerKeyword = keyword.toLowerCase();

                if (lowerTitle.contains(lowerKeyword)) score += 3;
                if (lowerKeywords.contains(lowerKeyword)) score += 2;
                if (lowerContent.contains(lowerKeyword)) score += 1;

                relevanceScores.put(id, score);
            }
        }

        return diseaseMap.values().stream()
            .sorted((a, b) -> {
                int scoreA = relevanceScores.getOrDefault(a.getId(), 0);
                int scoreB = relevanceScores.getOrDefault(b.getId(), 0);
                return Integer.compare(scoreB, scoreA);
            })
            .limit(DEFAULT_SEARCH_LIMIT)
            .collect(Collectors.toList());
    }

    /**
     * Search trong table disease_monkey
     */
    private List<DiseaseDTO> searchDiseaseMonkey(List<String> keywords) {
        LOG.info("Searching in disease_monkey table with keywords: {}", keywords);
        
        Map<Long, DiseaseDTO> diseaseMap = new HashMap<>();
        Map<Long, Integer> relevanceScores = new HashMap<>();

        Pageable pageable = PageRequest.of(0, 20);

        for (String keyword : keywords) {
            List<DiseaseMonkey> found = diseaseMonkeyRepository.searchByKeyword(keyword, pageable);
            LOG.info("Found {} items with keyword '{}' in disease_monkey", found.size(), keyword);

            for (DiseaseMonkey disease : found) {
                Long id = disease.getId();
                
                if (!diseaseMap.containsKey(id)) {
                    DiseaseDTO dto = convertToDTO(disease, "Khỉ");
                    diseaseMap.put(id, dto);
                    relevanceScores.put(id, 0);
                }

                int score = relevanceScores.get(id);
                String lowerTitle = disease.getTitle() != null ? disease.getTitle().toLowerCase() : "";
                String lowerKeywords = disease.getKeywords() != null ? disease.getKeywords().toLowerCase() : "";
                String lowerContent = disease.getContent() != null ? disease.getContent().toLowerCase() : "";
                String lowerKeyword = keyword.toLowerCase();

                if (lowerTitle.contains(lowerKeyword)) score += 3;
                if (lowerKeywords.contains(lowerKeyword)) score += 2;
                if (lowerContent.contains(lowerKeyword)) score += 1;

                relevanceScores.put(id, score);
            }
        }

        return diseaseMap.values().stream()
            .sorted((a, b) -> {
                int scoreA = relevanceScores.getOrDefault(a.getId(), 0);
                int scoreB = relevanceScores.getOrDefault(b.getId(), 0);
                return Integer.compare(scoreB, scoreA);
            })
            .limit(DEFAULT_SEARCH_LIMIT)
            .collect(Collectors.toList());
    }

    /**
     * Search trong table disease_fish
     */
    private List<DiseaseDTO> searchDiseaseFish(List<String> keywords) {
        LOG.info("Searching in disease_fish table with keywords: {}", keywords);
        
        Map<Long, DiseaseDTO> diseaseMap = new HashMap<>();
        Map<Long, Integer> relevanceScores = new HashMap<>();

        Pageable pageable = PageRequest.of(0, 20);

        for (String keyword : keywords) {
            List<DiseaseFish> found = diseaseFishRepository.searchByKeyword(keyword, pageable);
            LOG.info("Found {} items with keyword '{}' in disease_fish", found.size(), keyword);

            for (DiseaseFish disease : found) {
                Long id = disease.getId();
                
                if (!diseaseMap.containsKey(id)) {
                    DiseaseDTO dto = convertToDTO(disease, "Cá");
                    diseaseMap.put(id, dto);
                    relevanceScores.put(id, 0);
                }

                int score = relevanceScores.get(id);
                String lowerTitle = disease.getTitle() != null ? disease.getTitle().toLowerCase() : "";
                String lowerKeywords = disease.getKeywords() != null ? disease.getKeywords().toLowerCase() : "";
                String lowerContent = disease.getContent() != null ? disease.getContent().toLowerCase() : "";
                String lowerKeyword = keyword.toLowerCase();

                if (lowerTitle.contains(lowerKeyword)) score += 3;
                if (lowerKeywords.contains(lowerKeyword)) score += 2;
                if (lowerContent.contains(lowerKeyword)) score += 1;

                relevanceScores.put(id, score);
            }
        }

        return diseaseMap.values().stream()
            .sorted((a, b) -> {
                int scoreA = relevanceScores.getOrDefault(a.getId(), 0);
                int scoreB = relevanceScores.getOrDefault(b.getId(), 0);
                return Integer.compare(scoreB, scoreA);
            })
            .limit(DEFAULT_SEARCH_LIMIT)
            .collect(Collectors.toList());
    }

    /**
     * Search trong table disease_bird
     */
    private List<DiseaseDTO> searchDiseaseBird(List<String> keywords) {
        LOG.info("Searching in disease_bird table with keywords: {}", keywords);
        
        Map<Long, DiseaseDTO> diseaseMap = new HashMap<>();
        Map<Long, Integer> relevanceScores = new HashMap<>();

        Pageable pageable = PageRequest.of(0, 20);

        for (String keyword : keywords) {
            List<DiseaseBird> found = diseaseBirdRepository.searchByKeyword(keyword, pageable);
            LOG.info("Found {} items with keyword '{}' in disease_bird", found.size(), keyword);

            for (DiseaseBird disease : found) {
                Long id = disease.getId();
                
                if (!diseaseMap.containsKey(id)) {
                    DiseaseDTO dto = convertToDTO(disease, "Chim");
                    diseaseMap.put(id, dto);
                    relevanceScores.put(id, 0);
                }

                int score = relevanceScores.get(id);
                String lowerTitle = disease.getTitle() != null ? disease.getTitle().toLowerCase() : "";
                String lowerKeywords = disease.getKeywords() != null ? disease.getKeywords().toLowerCase() : "";
                String lowerContent = disease.getContent() != null ? disease.getContent().toLowerCase() : "";
                String lowerKeyword = keyword.toLowerCase();

                if (lowerTitle.contains(lowerKeyword)) score += 3;
                if (lowerKeywords.contains(lowerKeyword)) score += 2;
                if (lowerContent.contains(lowerKeyword)) score += 1;

                relevanceScores.put(id, score);
            }
        }

        return diseaseMap.values().stream()
            .sorted((a, b) -> {
                int scoreA = relevanceScores.getOrDefault(a.getId(), 0);
                int scoreB = relevanceScores.getOrDefault(b.getId(), 0);
                return Integer.compare(scoreB, scoreA);
            })
            .limit(DEFAULT_SEARCH_LIMIT)
            .collect(Collectors.toList());
    }

    /**
     * Search trong table disease_mouse
     */
    private List<DiseaseDTO> searchDiseaseMouse(List<String> keywords) {
        LOG.info("Searching in disease_mouse table with keywords: {}", keywords);
        
        Map<Long, DiseaseDTO> diseaseMap = new HashMap<>();
        Map<Long, Integer> relevanceScores = new HashMap<>();

        Pageable pageable = PageRequest.of(0, 20);

        for (String keyword : keywords) {
            List<DiseaseMouse> found = diseaseMouseRepository.searchByKeyword(keyword, pageable);
            LOG.info("Found {} items with keyword '{}' in disease_mouse", found.size(), keyword);

            for (DiseaseMouse disease : found) {
                Long id = disease.getId();
                
                if (!diseaseMap.containsKey(id)) {
                    DiseaseDTO dto = convertToDTO(disease, "Chuột");
                    diseaseMap.put(id, dto);
                    relevanceScores.put(id, 0);
                }

                int score = relevanceScores.get(id);
                String lowerTitle = disease.getTitle() != null ? disease.getTitle().toLowerCase() : "";
                String lowerKeywords = disease.getKeywords() != null ? disease.getKeywords().toLowerCase() : "";
                String lowerContent = disease.getContent() != null ? disease.getContent().toLowerCase() : "";
                String lowerKeyword = keyword.toLowerCase();

                if (lowerTitle.contains(lowerKeyword)) score += 3;
                if (lowerKeywords.contains(lowerKeyword)) score += 2;
                if (lowerContent.contains(lowerKeyword)) score += 1;

                relevanceScores.put(id, score);
            }
        }

        return diseaseMap.values().stream()
            .sorted((a, b) -> {
                int scoreA = relevanceScores.getOrDefault(a.getId(), 0);
                int scoreB = relevanceScores.getOrDefault(b.getId(), 0);
                return Integer.compare(scoreB, scoreA);
            })
            .limit(DEFAULT_SEARCH_LIMIT)
            .collect(Collectors.toList());
    }

    /**
     * Convert DiseaseDog to DiseaseDTO
     */
    private DiseaseDTO convertToDTO(DiseaseDog disease, String species) {
        DiseaseDTO dto = new DiseaseDTO();
        dto.setId(disease.getId());
        dto.setTitle(disease.getTitle());
        dto.setKeywords(disease.getKeywords());
        dto.setContent(disease.getContent());
        dto.setSeverityLevel(disease.getSeverityLevel());
        dto.setIsActive(disease.getIsActive());
        dto.setCreatedDate(disease.getCreatedDate());
        dto.setLastModifiedDate(disease.getLastModifiedDate());
        dto.setSpecies(species);
        return dto;
    }

    /**
     * Convert DiseaseCat to DiseaseDTO
     */
    private DiseaseDTO convertToDTO(DiseaseCat disease, String species) {
        DiseaseDTO dto = new DiseaseDTO();
        dto.setId(disease.getId());
        dto.setTitle(disease.getTitle());
        dto.setKeywords(disease.getKeywords());
        dto.setContent(disease.getContent());
        dto.setSeverityLevel(disease.getSeverityLevel());
        dto.setIsActive(disease.getIsActive());
        dto.setCreatedDate(disease.getCreatedDate());
        dto.setLastModifiedDate(disease.getLastModifiedDate());
        dto.setSpecies(species);
        return dto;
    }

    /**
     * Convert DiseaseTurtle to DiseaseDTO
     */
    private DiseaseDTO convertToDTO(DiseaseTurtle disease, String species) {
        DiseaseDTO dto = new DiseaseDTO();
        dto.setId(disease.getId());
        dto.setTitle(disease.getTitle());
        dto.setKeywords(disease.getKeywords());
        dto.setContent(disease.getContent());
        dto.setSeverityLevel(disease.getSeverityLevel());
        dto.setIsActive(disease.getIsActive());
        dto.setCreatedDate(disease.getCreatedDate());
        dto.setLastModifiedDate(disease.getLastModifiedDate());
        dto.setSpecies(species);
        return dto;
    }

    /**
     * Convert DiseaseSnake to DiseaseDTO
     */
    private DiseaseDTO convertToDTO(DiseaseSnake disease, String species) {
        DiseaseDTO dto = new DiseaseDTO();
        dto.setId(disease.getId());
        dto.setTitle(disease.getTitle());
        dto.setKeywords(disease.getKeywords());
        dto.setContent(disease.getContent());
        dto.setSeverityLevel(disease.getSeverityLevel());
        dto.setIsActive(disease.getIsActive());
        dto.setCreatedDate(disease.getCreatedDate());
        dto.setLastModifiedDate(disease.getLastModifiedDate());
        dto.setSpecies(species);
        return dto;
    }

    /**
     * Convert DiseasePig to DiseaseDTO
     */
    private DiseaseDTO convertToDTO(DiseasePig disease, String species) {
        DiseaseDTO dto = new DiseaseDTO();
        dto.setId(disease.getId());
        dto.setTitle(disease.getTitle());
        dto.setKeywords(disease.getKeywords());
        dto.setContent(disease.getContent());
        dto.setSeverityLevel(disease.getSeverityLevel());
        dto.setIsActive(disease.getIsActive());
        dto.setCreatedDate(disease.getCreatedDate());
        dto.setLastModifiedDate(disease.getLastModifiedDate());
        dto.setSpecies(species);
        return dto;
    }

    /**
     * Convert DiseaseGoat to DiseaseDTO
     */
    private DiseaseDTO convertToDTO(DiseaseGoat disease, String species) {
        DiseaseDTO dto = new DiseaseDTO();
        dto.setId(disease.getId());
        dto.setTitle(disease.getTitle());
        dto.setKeywords(disease.getKeywords());
        dto.setContent(disease.getContent());
        dto.setSeverityLevel(disease.getSeverityLevel());
        dto.setIsActive(disease.getIsActive());
        dto.setCreatedDate(disease.getCreatedDate());
        dto.setLastModifiedDate(disease.getLastModifiedDate());
        dto.setSpecies(species);
        return dto;
    }

    /**
     * Convert DiseaseSheep to DiseaseDTO
     */
    private DiseaseDTO convertToDTO(DiseaseSheep disease, String species) {
        DiseaseDTO dto = new DiseaseDTO();
        dto.setId(disease.getId());
        dto.setTitle(disease.getTitle());
        dto.setKeywords(disease.getKeywords());
        dto.setContent(disease.getContent());
        dto.setSeverityLevel(disease.getSeverityLevel());
        dto.setIsActive(disease.getIsActive());
        dto.setCreatedDate(disease.getCreatedDate());
        dto.setLastModifiedDate(disease.getLastModifiedDate());
        dto.setSpecies(species);
        return dto;
    }

    /**
     * Convert DiseaseCow to DiseaseDTO
     */
    private DiseaseDTO convertToDTO(DiseaseCow disease, String species) {
        DiseaseDTO dto = new DiseaseDTO();
        dto.setId(disease.getId());
        dto.setTitle(disease.getTitle());
        dto.setKeywords(disease.getKeywords());
        dto.setContent(disease.getContent());
        dto.setSeverityLevel(disease.getSeverityLevel());
        dto.setIsActive(disease.getIsActive());
        dto.setCreatedDate(disease.getCreatedDate());
        dto.setLastModifiedDate(disease.getLastModifiedDate());
        dto.setSpecies(species);
        return dto;
    }

    /**
     * Convert DiseaseBuffalo to DiseaseDTO
     */
    private DiseaseDTO convertToDTO(DiseaseBuffalo disease, String species) {
        DiseaseDTO dto = new DiseaseDTO();
        dto.setId(disease.getId());
        dto.setTitle(disease.getTitle());
        dto.setKeywords(disease.getKeywords());
        dto.setContent(disease.getContent());
        dto.setSeverityLevel(disease.getSeverityLevel());
        dto.setIsActive(disease.getIsActive());
        dto.setCreatedDate(disease.getCreatedDate());
        dto.setLastModifiedDate(disease.getLastModifiedDate());
        dto.setSpecies(species);
        return dto;
    }

    /**
     * Convert DiseaseMonkey to DiseaseDTO
     */
    private DiseaseDTO convertToDTO(DiseaseMonkey disease, String species) {
        DiseaseDTO dto = new DiseaseDTO();
        dto.setId(disease.getId());
        dto.setTitle(disease.getTitle());
        dto.setKeywords(disease.getKeywords());
        dto.setContent(disease.getContent());
        dto.setSeverityLevel(disease.getSeverityLevel());
        dto.setIsActive(disease.getIsActive());
        dto.setCreatedDate(disease.getCreatedDate());
        dto.setLastModifiedDate(disease.getLastModifiedDate());
        dto.setSpecies(species);
        return dto;
    }

    /**
     * Convert DiseaseFish to DiseaseDTO
     */
    private DiseaseDTO convertToDTO(DiseaseFish disease, String species) {
        DiseaseDTO dto = new DiseaseDTO();
        dto.setId(disease.getId());
        dto.setTitle(disease.getTitle());
        dto.setKeywords(disease.getKeywords());
        dto.setContent(disease.getContent());
        dto.setSeverityLevel(disease.getSeverityLevel());
        dto.setIsActive(disease.getIsActive());
        dto.setCreatedDate(disease.getCreatedDate());
        dto.setLastModifiedDate(disease.getLastModifiedDate());
        dto.setSpecies(species);
        return dto;
    }

    /**
     * Convert DiseaseBird to DiseaseDTO
     */
    private DiseaseDTO convertToDTO(DiseaseBird disease, String species) {
        DiseaseDTO dto = new DiseaseDTO();
        dto.setId(disease.getId());
        dto.setTitle(disease.getTitle());
        dto.setKeywords(disease.getKeywords());
        dto.setContent(disease.getContent());
        dto.setSeverityLevel(disease.getSeverityLevel());
        dto.setIsActive(disease.getIsActive());
        dto.setCreatedDate(disease.getCreatedDate());
        dto.setLastModifiedDate(disease.getLastModifiedDate());
        dto.setSpecies(species);
        return dto;
    }

    /**
     * Convert DiseaseMouse to DiseaseDTO
     */
    private DiseaseDTO convertToDTO(DiseaseMouse disease, String species) {
        DiseaseDTO dto = new DiseaseDTO();
        dto.setId(disease.getId());
        dto.setTitle(disease.getTitle());
        dto.setKeywords(disease.getKeywords());
        dto.setContent(disease.getContent());
        dto.setSeverityLevel(disease.getSeverityLevel());
        dto.setIsActive(disease.getIsActive());
        dto.setCreatedDate(disease.getCreatedDate());
        dto.setLastModifiedDate(disease.getLastModifiedDate());
        dto.setSpecies(species);
        return dto;
    }

    /**
     * Convert DiseaseRabbit to DiseaseDTO
     */
    private DiseaseDTO convertToDTO(DiseaseRabbit disease, String species) {
        DiseaseDTO dto = new DiseaseDTO();
        dto.setId(disease.getId());
        dto.setTitle(disease.getTitle());
        dto.setKeywords(disease.getKeywords());
        dto.setContent(disease.getContent());
        dto.setSeverityLevel(disease.getSeverityLevel());
        dto.setIsActive(disease.getIsActive());
        dto.setCreatedDate(disease.getCreatedDate());
        dto.setLastModifiedDate(disease.getLastModifiedDate());
        dto.setSpecies(species);
        return dto;
    }

    /**
     * Extract keywords từ message, loại bỏ species name và stop words
     * Giữ lại cả cụm từ (bigrams) và từ đơn lẻ
     */
    private List<String> extractKeywords(String message, String species) {
        if (message == null || message.trim().isEmpty()) {
            return List.of();
        }

        // Stop words tiếng Việt
        List<String> stopWords = Arrays.asList(
            "của", "tôi", "bạn", "là", "và", "có", "không", "được", "một", "các",
            "với", "cho", "về", "này", "đó", "đã", "sẽ", "đang", "rất", "như",
            "thì", "mà", "nếu", "khi", "từ", "trong", "đến", "bị", "ở", "vì"
        );

        // Loại bỏ species name từ message
        String lowerMessage = message.toLowerCase();
        String lowerSpecies = species != null ? species.toLowerCase() : "";
        if (!lowerSpecies.isEmpty()) {
            lowerMessage = lowerMessage.replace(lowerSpecies, " ");
            // Loại bỏ các biến thể của tất cả các loài
            lowerMessage = lowerMessage.replace("chó", " ").replace("cho", " ");
            lowerMessage = lowerMessage.replace("mèo", " ").replace("meo", " ");
            lowerMessage = lowerMessage.replace("rùa", " ").replace("rua", " ");
            lowerMessage = lowerMessage.replace("rắn", " ").replace("ran", " ");
            lowerMessage = lowerMessage.replace("lợn", " ").replace("lon", " ").replace("heo", " ");
            lowerMessage = lowerMessage.replace("dê", " ").replace("de", " ");
            lowerMessage = lowerMessage.replace("cừu", " ").replace("cuu", " ");
            lowerMessage = lowerMessage.replace("bò", " ").replace("bo", " ");
            lowerMessage = lowerMessage.replace("trâu", " ").replace("trau", " ");
            lowerMessage = lowerMessage.replace("khỉ", " ").replace("khi", " ");
            lowerMessage = lowerMessage.replace("cá", " ").replace("ca", " ");
            lowerMessage = lowerMessage.replace("chim", " ");
            lowerMessage = lowerMessage.replace("chuột", " ").replace("chuot", " ");
        }

        // Chuẩn hóa khoảng trắng
        lowerMessage = lowerMessage.replaceAll("[^\\p{L}\\p{N}\\s]", " ")
            .replaceAll("\\s+", " ")
            .trim();

        // Tách từ
        String[] words = lowerMessage.split("\\s+");
        
        List<String> keywords = new ArrayList<>();
        
        // Lọc ra các từ không phải stop words
        List<String> nonStopWords = new ArrayList<>();
        for (String word : words) {
            word = word.trim();
            if (word.length() > 1 && !stopWords.contains(word)) {
                nonStopWords.add(word);
            }
        }
        
        // Thêm các từ đơn lẻ
        for (String word : nonStopWords) {
            keywords.add(word);
            String normalized = normalizeVietnamese(word);
            if (!normalized.equals(word) && !keywords.contains(normalized)) {
                keywords.add(normalized);
            }
        }
        
        // Thêm các cụm từ (bigrams) từ các từ không phải stop words liền kề
        // Ví dụ: "bị co giật" -> sau khi lọc stop words -> ["co", "giật"] -> bigram "co giật"
        for (int i = 0; i < nonStopWords.size() - 1; i++) {
            String word1 = nonStopWords.get(i);
            String word2 = nonStopWords.get(i + 1);
            
            String bigram = word1 + " " + word2;
            if (!keywords.contains(bigram)) {
                keywords.add(bigram);
                
                // Thêm normalized version nếu khác
                String normalized = normalizeVietnamese(bigram);
                if (!normalized.equals(bigram) && !keywords.contains(normalized)) {
                    keywords.add(normalized);
                }
            }
        }

        LOG.debug("Extracted keywords from '{}': {}", message, keywords);
        return keywords;
    }

    /**
     * Normalize tiếng Việt - loại bỏ dấu
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

    // ==================== MULTI-STAGE SEQUENTIAL MATCHING ====================
    
    /**
     * Helper class để lưu thông tin về species match
     */
    private static class SpeciesMatch {
        private final String species;
        private final int startPosition;
        private final int endPosition;
        
        public SpeciesMatch(String species, int startPosition, int endPosition) {
            this.species = species;
            this.startPosition = startPosition;
            this.endPosition = endPosition;
        }
        
        public String getSpecies() { return species; }
        public int getStartPosition() { return startPosition; }
        public int getEndPosition() { return endPosition; }
    }

    /**
     * Thuật toán Multi-stage Sequential Matching
     * Bước 1: Duyệt câu từ đầu để tìm species keyword
     * Bước 2: Duyệt phần còn lại sau species để tìm disease keywords
     * Bước 3: Search trong database của loài đó với disease keywords
     * 
     * @param userMessage Câu hỏi của user
     * @return Danh sách bệnh liên quan
     */
    public List<DiseaseDTO> searchDiseaseSequential(String userMessage) {
        LOG.info("Using Multi-stage Sequential Matching for message: '{}'", userMessage);
        
        if (userMessage == null || userMessage.trim().isEmpty()) {
            LOG.warn("Message is null or empty");
            return List.of();
        }

        // Stage 1: Duyệt câu để tìm species keyword với vị trí
        SpeciesMatch speciesMatch = findSpeciesInSentence(userMessage);
        
        if (speciesMatch == null) {
            LOG.warn("Could not find species in sentence: '{}'", userMessage);
            return List.of();
        }
        
        LOG.info("Found species '{}' at position [{}, {}]", 
            speciesMatch.getSpecies(), 
            speciesMatch.getStartPosition(), 
            speciesMatch.getEndPosition());

        // Stage 2: Tìm disease keywords trong phần còn lại sau species
        int endPosition = Math.min(speciesMatch.getEndPosition(), userMessage.length());
        String remainingText = userMessage.substring(endPosition).trim();
        List<String> diseaseKeywords = extractDiseaseKeywordsAfterSpecies(remainingText);
        
        LOG.info("Extracted {} disease keywords after species: {}", 
            diseaseKeywords.size(), diseaseKeywords);

        if (diseaseKeywords.isEmpty()) {
            LOG.warn("No disease keywords found after species position");
            return List.of();
        }

        // Stage 3: Search trong database của loài đó
        return searchDiseaseForSpecies(speciesMatch.getSpecies(), diseaseKeywords);
    }

    /**
     * Duyệt câu từ đầu để tìm species keyword với vị trí
     * Tìm species đầu tiên xuất hiện trong câu
     */
    private SpeciesMatch findSpeciesInSentence(String message) {
        if (message == null || message.isEmpty()) {
            return null;
        }

        String lowerMessage = message.toLowerCase();
        String normalizedMessage = normalizeVietnamese(lowerMessage);
        
        // Danh sách các species patterns với tên loài tương ứng
        Map<String, String> speciesPatterns = new HashMap<>();
        speciesPatterns.put("chó", "Chó");
        speciesPatterns.put("cho", "Chó");
        speciesPatterns.put("dog", "Chó");
        speciesPatterns.put("cún", "Chó");
        speciesPatterns.put("cun", "Chó");
        speciesPatterns.put("cẩu", "Chó");
        speciesPatterns.put("cau", "Chó");
        
        speciesPatterns.put("mèo", "Mèo");
        speciesPatterns.put("meo", "Mèo");
        speciesPatterns.put("cat", "Mèo");
        speciesPatterns.put("kitten", "Mèo");
        
        speciesPatterns.put("chim", "Chim");
        speciesPatterns.put("bird", "Chim");
        
        speciesPatterns.put("rùa", "Rùa");
        speciesPatterns.put("rua", "Rùa");
        speciesPatterns.put("turtle", "Rùa");
        speciesPatterns.put("tortoise", "Rùa");
        
        speciesPatterns.put("rắn", "Rắn");
        speciesPatterns.put("ran", "Rắn");
        speciesPatterns.put("snake", "Rắn");
        
        speciesPatterns.put("lợn", "Lợn");
        speciesPatterns.put("lon", "Lợn");
        speciesPatterns.put("heo", "Lợn");
        speciesPatterns.put("pig", "Lợn");
        
        speciesPatterns.put("dê", "Dê");
        speciesPatterns.put("de", "Dê");
        speciesPatterns.put("goat", "Dê");
        
        speciesPatterns.put("cừu", "Cừu");
        speciesPatterns.put("cuu", "Cừu");
        speciesPatterns.put("sheep", "Cừu");
        
        speciesPatterns.put("bò", "Bò");
        speciesPatterns.put("bo", "Bò");
        speciesPatterns.put("cow", "Bò");
        
        speciesPatterns.put("trâu", "Trâu");
        speciesPatterns.put("trau", "Trâu");
        speciesPatterns.put("buffalo", "Trâu");
        
        speciesPatterns.put("khỉ", "Khỉ");
        speciesPatterns.put("khi", "Khỉ");
        speciesPatterns.put("monkey", "Khỉ");
        
        speciesPatterns.put("cá", "Cá");
        speciesPatterns.put("ca", "Cá");
        speciesPatterns.put("fish", "Cá");
        
        speciesPatterns.put("chuột", "Chuột");
        speciesPatterns.put("chuot", "Chuột");
        speciesPatterns.put("mouse", "Chuột");
        speciesPatterns.put("rat", "Chuột");
        
        speciesPatterns.put("thỏ", "Thỏ");
        speciesPatterns.put("tho", "Thỏ");
        speciesPatterns.put("rabbit", "Thỏ");

        // Duyệt câu để tìm species đầu tiên
        // Ưu tiên tìm từ dài trước (ví dụ "mèo con" trước "mèo")
        List<String> sortedPatterns = speciesPatterns.keySet().stream()
            .sorted((a, b) -> Integer.compare(b.length(), a.length()))
            .collect(Collectors.toList());

        for (String pattern : sortedPatterns) {
            // Tìm trong message gốc (có dấu)
            int position = lowerMessage.indexOf(pattern);
            if (position != -1) {
                String species = speciesPatterns.get(pattern);
                int endPosition = position + pattern.length();
                LOG.debug("Found species '{}' (pattern: '{}') at position [{}, {}]", 
                    species, pattern, position, endPosition);
                return new SpeciesMatch(species, position, endPosition);
            }
            
            // Tìm trong normalized message (không dấu)
            int normalizedPosition = normalizedMessage.indexOf(pattern);
            if (normalizedPosition != -1) {
                String species = speciesPatterns.get(pattern);
                int endPosition = normalizedPosition + pattern.length();
                LOG.debug("Found species '{}' (pattern: '{}') at normalized position [{}, {}]", 
                    species, pattern, normalizedPosition, endPosition);
                // Map lại về vị trí trong message gốc
                return new SpeciesMatch(species, normalizedPosition, endPosition);
            }
        }

        return null;
    }

    /**
     * Extract disease keywords từ phần text sau vị trí species
     * Chỉ lấy các từ khóa liên quan đến bệnh/triệu chứng
     */
    private List<String> extractDiseaseKeywordsAfterSpecies(String remainingText) {
        if (remainingText == null || remainingText.trim().isEmpty()) {
            return List.of();
        }

        // Stop words tiếng Việt
        List<String> stopWords = Arrays.asList(
            "của", "tôi", "bạn", "là", "và", "có", "không", "được", "một", "các",
            "với", "cho", "về", "này", "đó", "đã", "sẽ", "đang", "rất", "như",
            "thì", "mà", "nếu", "khi", "từ", "trong", "đến", "bị", "ở", "vì",
            "con", "của", "tôi", "em", "nó"
        );

        // Từ khóa về bệnh/triệu chứng (ưu tiên)
        List<String> diseaseKeywords = Arrays.asList(
            "bệnh", "triệu chứng", "symptom", "disease", "illness",
            "nôn", "tiêu chảy", "diarrhea", "vomit", "ốm", "sick",
            "đau", "pain", "mắt", "eye", "mũi", "nose", "tai", "ear",
            "co giật", "động kinh", "convulsion", "seizure",
            "mệt mỏi", "lờ đờ", "sụt cân", "weight loss",
            "rụng lông", "feather loss", "ngứa", "itchy",
            "gãi", "scratch", "da đỏ", "red skin", "viêm da", "dermatitis",
            "chảy máu", "bleeding", "khó thở", "breathing", "thở",
            "ho", "cough", "sốt", "fever", "nhiệt độ", "temperature"
        );

        String lowerText = remainingText.toLowerCase();
        String normalizedText = normalizeVietnamese(lowerText);
        
        // Chuẩn hóa khoảng trắng
        lowerText = lowerText.replaceAll("[^\\p{L}\\p{N}\\s]", " ")
            .replaceAll("\\s+", " ")
            .trim();

        // Tách từ
        String[] words = lowerText.split("\\s+");
        
        List<String> keywords = new ArrayList<>();
        
        // Lọc ra các từ không phải stop words
        List<String> nonStopWords = new ArrayList<>();
        for (String word : words) {
            word = word.trim();
            if (word.length() > 1 && !stopWords.contains(word)) {
                nonStopWords.add(word);
            }
        }
        
        // Thêm các từ đơn lẻ
        for (String word : nonStopWords) {
            keywords.add(word);
            String normalized = normalizeVietnamese(word);
            if (!normalized.equals(word) && !keywords.contains(normalized)) {
                keywords.add(normalized);
            }
        }
        
        // Thêm các cụm từ (bigrams) từ các từ không phải stop words liền kề
        for (int i = 0; i < nonStopWords.size() - 1; i++) {
            String word1 = nonStopWords.get(i);
            String word2 = nonStopWords.get(i + 1);
            
            String bigram = word1 + " " + word2;
            if (!keywords.contains(bigram)) {
                keywords.add(bigram);
                
                // Thêm normalized version nếu khác
                String normalized = normalizeVietnamese(bigram);
                if (!normalized.equals(bigram) && !keywords.contains(normalized)) {
                    keywords.add(normalized);
                }
            }
        }

        // Ưu tiên các từ khóa liên quan đến bệnh
        List<String> prioritizedKeywords = new ArrayList<>();
        for (String keyword : keywords) {
            String lowerKeyword = keyword.toLowerCase();
            for (String diseaseKeyword : diseaseKeywords) {
                if (lowerKeyword.contains(diseaseKeyword.toLowerCase()) || 
                    diseaseKeyword.toLowerCase().contains(lowerKeyword)) {
                    if (!prioritizedKeywords.contains(keyword)) {
                        prioritizedKeywords.add(keyword);
                    }
                }
            }
        }
        
        // Thêm các từ khóa khác nếu chưa đủ
        for (String keyword : keywords) {
            if (!prioritizedKeywords.contains(keyword) && prioritizedKeywords.size() < 10) {
                prioritizedKeywords.add(keyword);
            }
        }

        LOG.debug("Extracted disease keywords from '{}': {}", remainingText, prioritizedKeywords);
        return prioritizedKeywords;
    }

    /**
     * Search disease cho một loài cụ thể với danh sách keywords
     */
    private List<DiseaseDTO> searchDiseaseForSpecies(String species, List<String> keywords) {
        if (species == null || species.isEmpty() || keywords == null || keywords.isEmpty()) {
            return List.of();
        }

        String lowerSpecies = species.toLowerCase();
        
        // Map species → table và search (sử dụng logic hiện có)
        if ("Chó".equalsIgnoreCase(species) || "cho".equalsIgnoreCase(species)) {
            return searchDiseaseDog(keywords);
        } else if ("Mèo".equalsIgnoreCase(species) || "meo".equalsIgnoreCase(species)) {
            return searchDiseaseCat(keywords);
        } else if ("Rùa".equalsIgnoreCase(species) || "rua".equalsIgnoreCase(species)) {
            return searchDiseaseTurtle(keywords);
        } else if ("Rắn".equalsIgnoreCase(species) || "ran".equalsIgnoreCase(species)) {
            return searchDiseaseSnake(keywords);
        } else if ("Lợn".equalsIgnoreCase(species) || "lon".equalsIgnoreCase(species) || "heo".equalsIgnoreCase(species)) {
            return searchDiseasePig(keywords);
        } else if ("Dê".equalsIgnoreCase(species) || "de".equalsIgnoreCase(species)) {
            return searchDiseaseGoat(keywords);
        } else if ("Cừu".equalsIgnoreCase(species) || "cuu".equalsIgnoreCase(species)) {
            return searchDiseaseSheep(keywords);
        } else if ("Bò".equalsIgnoreCase(species) || "bo".equalsIgnoreCase(species)) {
            return searchDiseaseCow(keywords);
        } else if ("Trâu".equalsIgnoreCase(species) || "trau".equalsIgnoreCase(species)) {
            return searchDiseaseBuffalo(keywords);
        } else if ("Khỉ".equalsIgnoreCase(species) || "khi".equalsIgnoreCase(species)) {
            return searchDiseaseMonkey(keywords);
        } else if ("Cá".equalsIgnoreCase(species) || "ca".equalsIgnoreCase(species)) {
            return searchDiseaseFish(keywords);
        } else if ("Chim".equalsIgnoreCase(species) || "chim".equalsIgnoreCase(species)) {
            return searchDiseaseBird(keywords);
        } else if ("Chuột".equalsIgnoreCase(species) || "chuot".equalsIgnoreCase(species)) {
            return searchDiseaseMouse(keywords);
        } else if ("Thỏ".equalsIgnoreCase(species) || "tho".equalsIgnoreCase(species)) {
            return searchDiseaseRabbit(keywords);
        } else {
            LOG.warn("Unknown species: {}", species);
            return List.of();
        }
    }

    /**
     * Search trong table disease_rabbit
     */
    private List<DiseaseDTO> searchDiseaseRabbit(List<String> keywords) {
        LOG.info("Searching in disease_rabbit table with keywords: {}", keywords);
        
        Map<Long, DiseaseDTO> diseaseMap = new HashMap<>();
        Map<Long, Integer> relevanceScores = new HashMap<>();

        Pageable pageable = PageRequest.of(0, 20);

        for (String keyword : keywords) {
            List<DiseaseRabbit> found = diseaseRabbitRepository.searchByKeyword(keyword, pageable);
            LOG.info("Found {} items with keyword '{}' in disease_rabbit", found.size(), keyword);

            for (DiseaseRabbit disease : found) {
                Long id = disease.getId();
                
                if (!diseaseMap.containsKey(id)) {
                    DiseaseDTO dto = convertToDTO(disease, "Thỏ");
                    diseaseMap.put(id, dto);
                    relevanceScores.put(id, 0);
                }

                int score = relevanceScores.get(id);
                String lowerTitle = disease.getTitle() != null ? disease.getTitle().toLowerCase() : "";
                String lowerKeywords = disease.getKeywords() != null ? disease.getKeywords().toLowerCase() : "";
                String lowerContent = disease.getContent() != null ? disease.getContent().toLowerCase() : "";
                String lowerKeyword = keyword.toLowerCase();

                if (lowerTitle.contains(lowerKeyword)) score += 3;
                if (lowerKeywords.contains(lowerKeyword)) score += 2;
                if (lowerContent.contains(lowerKeyword)) score += 1;

                relevanceScores.put(id, score);
            }
        }

        return diseaseMap.values().stream()
            .sorted((a, b) -> {
                int scoreA = relevanceScores.getOrDefault(a.getId(), 0);
                int scoreB = relevanceScores.getOrDefault(b.getId(), 0);
                return Integer.compare(scoreB, scoreA);
            })
            .limit(DEFAULT_SEARCH_LIMIT)
            .collect(Collectors.toList());
    }
}
