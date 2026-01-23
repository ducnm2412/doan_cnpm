-- =====================================================
-- Script INSERT các keyword triệu chứng phổ biến vào bảng disease_fish
-- =====================================================

USE animalhospital;

-- Insert các triệu chứng phổ biến với keywords
INSERT INTO disease_fish (title, keywords, content, severity_level, is_active, created_date) VALUES

-- =====================================================
-- 1. BỆNH NẤM VÀ KÝ SINH TRÙNG (RẤT PHỔ BIẾN Ở CÁ)
-- =====================================================

-- 1. Đốm trắng / Nấm trắng / Ich
('Đốm trắng, nấm trắng, ich ở cá',
 'đốm trắng,nấm trắng,ich,đốm trắng trên da,đốm trắng trên vây,nấm trên cá',
 'Đốm trắng hoặc nấm trắng là bệnh rất phổ biến ở cá, thường do ký sinh trùng Ichthyophthirius multifiliis (Ich) hoặc nấm. Nghi ngờ: Bệnh Ich (White Spot Disease), Nấm da (Fungal infection), Nhiễm ký sinh trùng ngoài da, Stress do môi trường kém. Follow-up: Có cá cọ xát vào đồ vật không? Có thở gấp không? Có bỏ ăn không? Có nhiều đốm trắng không? Cần cách ly cá bệnh và điều trị bằng thuốc chống ký sinh trùng hoặc nấm.',
 'MODERATE',
 TRUE,
 NOW()),

-- 2. Cọ xát vào đồ vật / Cọ mình
('Cọ xát vào đồ vật, cọ mình ở cá',
 'cọ xát,cọ mình,cọ vào đá,cọ vào thành bể,cọ vào cây thủy sinh,ngứa',
 'Cá cọ xát vào đồ vật hoặc cọ mình là dấu hiệu của ngứa hoặc khó chịu. Nghi ngờ: Bệnh Ich (White Spot Disease), Nhiễm ký sinh trùng ngoài da (Flukes, Lice), Nấm da, Kích ứng da, Nhiễm khuẩn da. Follow-up: Có đốm trắng không? Có vảy bong tróc không? Có vây bị rách không? Có thở gấp không? Cần kiểm tra nước và điều trị bằng thuốc chống ký sinh trùng.',
 'MODERATE',
 TRUE,
 NOW()),

-- 3. Vảy bong tróc / Vảy rụng
('Vảy bong tróc, vảy rụng ở cá',
 'vảy bong tróc,vảy rụng,vảy bong,vảy mất,vảy không đều,da tróc',
 'Vảy bong tróc hoặc vảy rụng ở cá có thể do nhiều nguyên nhân. Nghi ngờ: Nhiễm ký sinh trùng ngoài da, Nấm da, Nhiễm khuẩn da, Chấn thương, Thiếu dinh dưỡng, Chất lượng nước kém. Follow-up: Có đốm trắng không? Có cọ xát vào đồ vật không? Có vây bị rách không? Có bỏ ăn không? Cần kiểm tra chất lượng nước và điều trị phù hợp.',
 'MODERATE',
 TRUE,
 NOW()),

-- =====================================================
-- 2. BỆNH VỀ VÂY (PHỔ BIẾN Ở CÁ)
-- =====================================================

-- 4. Vây bị rách / Vây cụt / Vây bị ăn mòn
('Vây bị rách, vây cụt, vây bị ăn mòn ở cá',
 'vây bị rách,vây cụt,vây bị ăn mòn,vây bị thối,vây không nguyên vẹn,fin rot',
 'Vây bị rách, cụt hoặc bị ăn mòn là dấu hiệu của bệnh Fin Rot (thối vây). Nghi ngờ: Bệnh Fin Rot (do vi khuẩn), Nhiễm khuẩn vây, Nấm vây, Chấn thương, Cá khác cắn, Chất lượng nước kém. Follow-up: Có vây bị đỏ không? Có vây bị sưng không? Có bỏ ăn không? Có nhiều cá bị không? Cần cải thiện chất lượng nước và điều trị bằng thuốc kháng khuẩn.',
 'MODERATE',
 TRUE,
 NOW()),

-- 5. Vây bị đỏ / Vây sưng
('Vây bị đỏ, vây sưng ở cá',
 'vây bị đỏ,vây sưng,vây đỏ,vây viêm,vây bị viêm',
 'Vây bị đỏ hoặc sưng là dấu hiệu của viêm nhiễm. Nghi ngờ: Bệnh Fin Rot, Nhiễm khuẩn vây, Nhiễm nấm vây, Chấn thương, Chất lượng nước kém. Follow-up: Có vây bị rách không? Có vây bị ăn mòn không? Có bỏ ăn không? Cần cải thiện chất lượng nước và điều trị bằng thuốc kháng khuẩn hoặc kháng nấm.',
 'MODERATE',
 TRUE,
 NOW()),

-- =====================================================
-- 3. BỆNH VỀ MANG (NGUY HIỂM Ở CÁ)
-- =====================================================

-- 6. Thở gấp / Thở nhanh / Mang đỏ
('Thở gấp, thở nhanh, mang đỏ ở cá',
 'thở gấp,thở nhanh,mang đỏ,mang sưng,mang mở rộng,khó thở',
 'Thở gấp, thở nhanh hoặc mang đỏ là dấu hiệu nghiêm trọng của vấn đề về mang. Nghi ngờ: Nhiễm ký sinh trùng mang (Gill Flukes), Nhiễm khuẩn mang, Nhiễm nấm mang, Thiếu oxy trong nước, Chất lượng nước kém, Ngộ độc. Follow-up: Có mang mở rộng không? Có cá nổi trên mặt nước không? Có bỏ ăn không? Có nhiều cá bị không? Cần kiểm tra oxy và chất lượng nước ngay, điều trị bằng thuốc chống ký sinh trùng hoặc kháng khuẩn.',
 'SEVERE',
 TRUE,
 NOW()),

-- 7. Mang mở rộng / Mang không đóng
('Mang mở rộng, mang không đóng ở cá',
 'mang mở rộng,mang không đóng,mang mở,mang phình,mang sưng',
 'Mang mở rộng hoặc không đóng là dấu hiệu nghiêm trọng của vấn đề về mang. Nghi ngờ: Nhiễm ký sinh trùng mang (Gill Flukes), Nhiễm khuẩn mang, Nhiễm nấm mang, Thiếu oxy trong nước, Ngộ độc, Chất lượng nước kém. Follow-up: Có thở gấp không? Có mang đỏ không? Có cá nổi trên mặt nước không? Cần kiểm tra oxy và chất lượng nước ngay, điều trị khẩn cấp.',
 'SEVERE',
 TRUE,
 NOW()),

-- =====================================================
-- 4. BỆNH VỀ DA VÀ MÀU SẮC (PHỔ BIẾN Ở CÁ)
-- =====================================================

-- 8. Da đỏ / Viêm da / Loét da
('Da đỏ, viêm da, loét da ở cá',
 'da đỏ,viêm da,loét da,vết loét trên da,da bị đỏ,da sưng',
 'Da đỏ, viêm da hoặc loét da ở cá có thể do nhiều nguyên nhân. Nghi ngờ: Nhiễm khuẩn da (Bacterial infection), Nhiễm nấm da, Loét da (Ulcer disease), Chấn thương, Chất lượng nước kém, Nhiễm ký sinh trùng. Follow-up: Có vảy bong tróc không? Có vây bị rách không? Có bỏ ăn không? Cần cải thiện chất lượng nước và điều trị bằng thuốc kháng khuẩn.',
 'MODERATE',
 TRUE,
 NOW()),

-- 9. Màu sắc nhạt / Mất màu
('Màu sắc nhạt, mất màu ở cá',
 'màu sắc nhạt,mất màu,màu nhạt đi,màu không đẹp,màu xỉn',
 'Màu sắc nhạt hoặc mất màu ở cá có thể do nhiều nguyên nhân. Nghi ngờ: Stress, Chất lượng nước kém, Thiếu dinh dưỡng, Bệnh tật, Tuổi già, Nhiễm ký sinh trùng. Follow-up: Có bỏ ăn không? Có mệt mỏi không? Có cọ xát vào đồ vật không? Cần kiểm tra chất lượng nước và chế độ dinh dưỡng.',
 'MILD',
 TRUE,
 NOW()),

-- 10. Da có lớp nhầy / Da đục
('Da có lớp nhầy, da đục ở cá',
 'da có lớp nhầy,da đục,lớp nhầy trên da,da mờ,da không trong',
 'Da có lớp nhầy hoặc da đục là dấu hiệu của vấn đề về da. Nghi ngờ: Nhiễm ký sinh trùng ngoài da, Nhiễm khuẩn da, Nhiễm nấm da, Chất lượng nước kém, Stress. Follow-up: Có đốm trắng không? Có cọ xát vào đồ vật không? Có vảy bong tróc không? Cần kiểm tra chất lượng nước và điều trị phù hợp.',
 'MODERATE',
 TRUE,
 NOW()),

-- =====================================================
-- 5. BỆNH VỀ HÀNH VI (DẤU HIỆU BỆNH TẬT)
-- =====================================================

-- 11. Bỏ ăn / Không ăn
('Bỏ ăn, không ăn ở cá',
 'bỏ ăn,không ăn,chán ăn,không thèm ăn,ăn ít,ăn kém',
 'Bỏ ăn ở cá là dấu hiệu nghiêm trọng của bệnh tật hoặc stress. Nghi ngờ: Nhiễm ký sinh trùng, Nhiễm khuẩn, Nhiễm nấm, Bệnh đường ruột, Stress, Chất lượng nước kém, Ngộ độc. Follow-up: Có đốm trắng không? Có vây bị rách không? Có thở gấp không? Có mệt mỏi không? Cần kiểm tra chất lượng nước và điều trị nguyên nhân gốc.',
 'MODERATE',
 TRUE,
 NOW()),

-- 12. Nổi trên mặt nước / Không bơi
('Nổi trên mặt nước, không bơi ở cá',
 'nổi trên mặt nước,không bơi,ngồi đáy,ngồi một chỗ,không di chuyển',
 'Cá nổi trên mặt nước hoặc không bơi là dấu hiệu nghiêm trọng. Nghi ngờ: Thiếu oxy trong nước, Bệnh bàng quang bơi (Swim Bladder Disease), Nhiễm ký sinh trùng mang, Nhiễm khuẩn, Ngộ độc, Chất lượng nước kém. Follow-up: Có thở gấp không? Có mang đỏ không? Có bỏ ăn không? Có nhiều cá bị không? Cần kiểm tra oxy và chất lượng nước ngay, có thể cần sục khí.',
 'SEVERE',
 TRUE,
 NOW()),

-- 13. Bơi lệch / Bơi không đều
('Bơi lệch, bơi không đều ở cá',
 'bơi lệch,bơi không đều,bơi nghiêng,bơi xiên,bơi khó khăn',
 'Cá bơi lệch hoặc bơi không đều có thể do nhiều nguyên nhân. Nghi ngờ: Bệnh bàng quang bơi (Swim Bladder Disease), Chấn thương, Nhiễm ký sinh trùng, Nhiễm khuẩn, Khối u, Ngộ độc. Follow-up: Có bỏ ăn không? Có nổi trên mặt nước không? Có vây bị rách không? Cần kiểm tra và điều trị nguyên nhân gốc.',
 'MODERATE',
 TRUE,
 NOW()),

-- 14. Mệt mỏi / Lờ đờ / Không hoạt động
('Mệt mỏi, lờ đờ, không hoạt động ở cá',
 'mệt mỏi,lờ đờ,không hoạt động,ngồi im,ngồi đáy,không di chuyển',
 'Mệt mỏi, lờ đờ, không hoạt động ở cá có thể do nhiều nguyên nhân. Nghi ngờ: Nhiễm ký sinh trùng, Nhiễm khuẩn, Nhiễm nấm, Stress, Chất lượng nước kém, Thiếu oxy, Ngộ độc. Follow-up: Có bỏ ăn không? Có thở gấp không? Có đốm trắng không? Có nhiều cá bị không? Cần kiểm tra chất lượng nước và điều trị phù hợp.',
 'MODERATE',
 TRUE,
 NOW()),

-- =====================================================
-- 6. BỆNH VỀ CƠ THỂ (PHỔ BIẾN Ở CÁ)
-- =====================================================

-- 15. Vảy dựng đứng / Vảy phình / Bụng sưng
('Vảy dựng đứng, vảy phình, bụng sưng ở cá',
 'vảy dựng đứng,vảy phình,vảy mở,vảy không khép,vảy nổi,bụng sưng,bụng phình,giống quả thông',
 'Vảy dựng đứng hoặc vảy phình là dấu hiệu nghiêm trọng của bệnh Dropsy. Nghi ngờ: Bệnh Dropsy (tích nước trong cơ thể), Nhiễm khuẩn đường ruột, Bệnh thận, Nhiễm ký sinh trùng, Chất lượng nước kém. Follow-up: Có bụng sưng không? Có bỏ ăn không? Có mệt mỏi không? Cần cách ly cá bệnh và điều trị bằng thuốc kháng khuẩn, cải thiện chất lượng nước.',
 'SEVERE',
 TRUE,
 NOW());

-- Kiểm tra dữ liệu đã insert
SELECT id, title, keywords, severity_level FROM disease_fish ORDER BY created_date DESC LIMIT 15;
