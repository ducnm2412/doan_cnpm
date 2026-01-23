-- =====================================================
-- Script INSERT các keyword triệu chứng phổ biến vào bảng disease_pig
-- =====================================================

USE animalhospital;

-- Insert các triệu chứng phổ biến với keywords
INSERT INTO disease_pig (title, keywords, content, severity_level, is_active, created_date) VALUES

-- =====================================================
-- 1. TIÊU HÓA (RẤT HAY GẶP Ở HEO)
-- =====================================================

-- 1. Tiêu chảy / Đi phân lỏng
('Tiêu chảy, đi phân lỏng ở heo',
 'tiêu chảy,đi phân lỏng,đi ngoài lỏng,phân lỏng,đi cầu lỏng,ỉa chảy,phân không thành khuôn,phân nước',
 'Tiêu chảy là triệu chứng rất phổ biến ở heo, đặc biệt ở heo con. Nghi ngờ: Nhiễm khuẩn đường ruột (E.coli, Salmonella), Bệnh tiêu chảy heo con (PED - Porcine Epidemic Diarrhea), Rotavirus, Coccidiosis, Thay đổi thức ăn đột ngột, Ngộ độc thức ăn. Follow-up: Có nôn không? Có bỏ ăn không? Có phân có máu không? Có mất nước không? Có sốt không? Tiêu chảy ở heo con có thể gây tử vong nhanh, cần điều trị ngay.',
 'MODERATE',
 TRUE,
 NOW()),

-- 2. Nôn / Ói
('Nôn, ói ở heo',
 'nôn,ói,ói ra thức ăn,ói ra bọt,ói vàng,buồn nôn,nôn mửa,ói khan',
 'Nôn và ói ở heo có thể do nhiều nguyên nhân. Nghi ngờ: Nhiễm khuẩn đường ruột, Ngộ độc thức ăn, Nuốt dị vật, Bệnh đường ruột, Nhiễm trùng đường tiêu hóa, Bệnh dạ dày. Follow-up: Có tiêu chảy không? Có bỏ ăn không? Có bụng chướng không? Có sốt không? Nếu nôn kéo dài hoặc kèm máu, cần đưa heo đến bác sĩ thú y ngay.',
 'MODERATE',
 TRUE,
 NOW()),

-- 3. Bỏ ăn / Biếng ăn / Ăn ít
('Bỏ ăn, biếng ăn, ăn ít ở heo',
 'bỏ ăn,biếng ăn,ăn ít,không ăn,chán ăn,không thèm ăn,ăn kém,không chịu ăn',
 'Bỏ ăn ở heo có thể do nhiều nguyên nhân nghiêm trọng. Nghi ngờ: Sốt / nhiễm trùng, Bệnh hô hấp, Bệnh tiêu hóa, Bệnh tai xanh (PRRS), Bệnh dịch tả heo châu Phi (ASF), Ngộ độc, Stress. Follow-up: Có sốt không? Có ho / khó thở không? Có nôn / tiêu chảy không? Có sưng khớp không? Có tím tai không? Nếu heo bỏ ăn quá 24 giờ, cần đưa đến bác sĩ thú y ngay.',
 'MODERATE',
 TRUE,
 NOW()),

-- 4. Phân có máu / Phân đen
('Phân có máu, phân đen ở heo',
 'phân có máu,phân đen,đi ngoài ra máu,máu trong phân,phân màu đen,phân đen như nhựa đường',
 'Phân có máu hoặc phân đen ở heo là dấu hiệu nghiêm trọng của xuất huyết đường tiêu hóa. Nghi ngờ: Viêm đường ruột nặng, Loét dạ dày, Coccidiosis, Bệnh tiêu chảy heo con, Khối u đường tiêu hóa, Ngộ độc. Follow-up: Có tiêu chảy không? Có nôn không? Có bỏ ăn không? Có sốt không? Cần đưa heo đến bác sĩ thú y ngay để kiểm tra và điều trị.',
 'SEVERE',
 TRUE,
 NOW()),

-- =====================================================
-- 2. HÔ HẤP
-- =====================================================

-- 5. Ho / Ho khan / Ho có đờm
('Ho, ho khan, ho có đờm ở heo',
 'ho,ho khan,ho có đờm,ho liên tục,ho về đêm,ho nhiều,ho kéo dài',
 'Ho ở heo có thể do nhiều nguyên nhân. Nghi ngờ: Bệnh hô hấp phức hợp (PRDC), Viêm phổi, Viêm phế quản, Nhiễm khuẩn đường hô hấp, Mycoplasma, Bệnh tai xanh (PRRS). Follow-up: Có khó thở không? Có chảy nước mũi không? Có sốt không? Có bỏ ăn không? Nếu ho kéo dài hoặc kèm khó thở, cần đưa heo đến bác sĩ thú y để kiểm tra.',
 'MODERATE',
 TRUE,
 NOW()),

-- 6. Khó thở / Thở gấp / Thở nhanh
('Khó thở, thở gấp, thở nhanh ở heo',
 'khó thở,thở gấp,thở nhanh,thở khó,thở nặng nhọc,thở hổn hển,thở há miệng',
 'Khó thở, thở gấp ở heo là dấu hiệu nghiêm trọng của bệnh hô hấp. Nghi ngờ: Viêm phổi nặng, Bệnh hô hấp phức hợp (PRDC), Bệnh tai xanh (PRRS), Nhiễm trùng đường hô hấp nghiêm trọng, Dị vật đường thở. Follow-up: Có ho không? Có chảy nước mũi không? Có tím tai / tím mũi không? Có sốt không? Khó thở ở heo là trường hợp khẩn cấp, cần đưa đến bác sĩ thú y ngay.',
 'SEVERE',
 TRUE,
 NOW()),

-- 7. Chảy nước mũi / Sổ mũi
('Chảy nước mũi, sổ mũi ở heo',
 'chảy nước mũi,sổ mũi,nước mũi đặc,nước mũi vàng,nước mũi xanh,sổ mũi nhiều',
 'Chảy nước mũi, sổ mũi ở heo thường là dấu hiệu của nhiễm trùng đường hô hấp. Nghi ngờ: Bệnh hô hấp phức hợp (PRDC), Viêm phổi, Nhiễm khuẩn đường hô hấp, Bệnh tai xanh (PRRS), Mycoplasma. Follow-up: Có ho không? Có khó thở không? Có sốt không? Có bỏ ăn không? Cần đưa heo đến bác sĩ thú y để kiểm tra và điều trị bằng thuốc kháng sinh.',
 'MODERATE',
 TRUE,
 NOW()),

-- =====================================================
-- 3. DA VÀ KHỚP
-- =====================================================

-- 8. Mụn / Mụn mủ / Loét da
('Mụn, mụn mủ, loét da ở heo',
 'mụn,mụn mủ,loét da,vết loét trên da,mụn nhọt,mụn viêm,mụn có mủ,áp xe da',
 'Mụn, mụn mủ, loét da ở heo có thể do nhiều nguyên nhân. Nghi ngờ: Nhiễm trùng da, Viêm da do vi khuẩn, Bệnh ghẻ heo (Mange), Nhiễm nấm, Chấn thương da, Vết thương không lành. Follow-up: Có ngứa / gãi nhiều không? Có sưng không? Có chảy dịch không? Có mùi hôi không? Cần đưa heo đến bác sĩ thú y để kiểm tra và điều trị bằng thuốc kháng sinh.',
 'MODERATE',
 TRUE,
 NOW()),

-- 9. Sưng khớp / Khập khiễng
('Sưng khớp, khập khiễng ở heo',
 'sưng khớp,khập khiễng,đi khập khiễng,đi cà nhắc,đi không đều chân,khớp sưng,khớp bị sưng',
 'Sưng khớp, khập khiễng ở heo có thể do nhiều nguyên nhân. Nghi ngờ: Viêm khớp, Nhiễm trùng khớp, Bệnh tai xanh (PRRS), Chấn thương, Bệnh chân và miệng (FMD), Nhiễm khuẩn. Follow-up: Có sốt không? Có bỏ ăn không? Có sưng ở chân khác không? Có đau không? Cần đưa heo đến bác sĩ thú y để kiểm tra và điều trị.',
 'MODERATE',
 TRUE,
 NOW()),

-- 10. Da đỏ / Viêm da
('Da đỏ, viêm da ở heo',
 'da đỏ,viêm da,da bị đỏ,da viêm,da kích ứng,da sưng đỏ',
 'Da đỏ, viêm da ở heo có thể do nhiều nguyên nhân. Nghi ngờ: Nhiễm trùng da, Bệnh ghẻ heo (Mange), Nhiễm nấm, Dị ứng, Chấn thương da, Nhiễm ký sinh trùng. Follow-up: Có ngứa / gãi nhiều không? Có mụn / loét da không? Có rụng lông không? Có mùi hôi không? Cần đưa heo đến bác sĩ thú y để kiểm tra và điều trị.',
 'MODERATE',
 TRUE,
 NOW()),

-- =====================================================
-- 4. SỐT VÀ NHIỄM TRÙNG
-- =====================================================

-- 11. Sốt / Nóng / Run
('Sốt, nóng, run ở heo',
 'sốt,nóng,run,run rẩy,thân nhiệt cao,người nóng,phát sốt',
 'Sốt, nóng, run ở heo là dấu hiệu của nhiễm trùng hoặc bệnh tật. Nghi ngờ: Nhiễm trùng, Bệnh hô hấp, Bệnh tiêu hóa, Bệnh tai xanh (PRRS), Bệnh dịch tả heo châu Phi (ASF), Nhiễm khuẩn, Nhiễm virus. Follow-up: Có nôn / tiêu chảy không? Có ho / khó thở không? Có bỏ ăn không? Có tím tai không? Cần đo nhiệt độ và đưa heo đến bác sĩ thú y nếu sốt cao hoặc kéo dài.',
 'MODERATE',
 TRUE,
 NOW()),

-- 12. Tím tai / Tím mũi / Tím da
('Tím tai, tím mũi, tím da ở heo',
 'tím tai,tím mũi,tím da,tai tím,mũi tím,da tím,thiếu oxy',
 'Tím tai, tím mũi, tím da ở heo là dấu hiệu nghiêm trọng của thiếu oxy trong máu. Nghi ngờ: Bệnh tai xanh (PRRS), Viêm phổi nặng, Bệnh hô hấp nghiêm trọng, Bệnh tim, Nhiễm trùng đường hô hấp nặng. Follow-up: Có khó thở không? Có ho không? Có sốt không? Có bỏ ăn không? Đây là trường hợp khẩn cấp, cần đưa heo đến bác sĩ thú y ngay lập tức.',
 'CRITICAL',
 TRUE,
 NOW()),

-- =====================================================
-- 5. THẦN KINH
-- =====================================================

-- 13. Co giật / Động kinh
('Co giật, động kinh ở heo',
 'co giật,động kinh,lên cơn co giật,co giật toàn thân,run,run rẩy',
 'Co giật, động kinh ở heo là dấu hiệu nghiêm trọng. Nghi ngờ: Bệnh thần kinh, Nhiễm trùng não, Ngộ độc, Chấn thương đầu, U não, Bệnh cột sống, Thiếu canxi, Bệnh giả dại (Pseudorabies). Follow-up: Có mất thăng bằng không? Có quay vòng vòng không? Có nghiêng đầu không? Có nôn không? Trong cơn co giật, cần giữ heo an toàn và đưa đến bác sĩ thú y ngay sau cơn co giật để kiểm tra.',
 'SEVERE',
 TRUE,
 NOW()),

-- 14. Lờ đờ / Mệt mỏi / Không hoạt động
('Lờ đờ, mệt mỏi, không hoạt động ở heo',
 'lờ đờ,mệt mỏi,không hoạt động,không di chuyển,ngủ nhiều,không phản ứng,kiệt sức',
 'Lờ đờ, mệt mỏi ở heo có thể do nhiều nguyên nhân nghiêm trọng. Nghi ngờ: Nhiễm trùng, Bệnh hô hấp, Bệnh tiêu hóa, Sốt, Ngộ độc, Thiếu máu, Suy dinh dưỡng, Bệnh tai xanh (PRRS). Follow-up: Có bỏ ăn không? Có sốt không? Có nôn / tiêu chảy không? Có khó thở không? Cần đưa heo đến bác sĩ thú y để kiểm tra và xác định nguyên nhân.',
 'MODERATE',
 TRUE,
 NOW()),

-- =====================================================
-- 6. SINH SẢN
-- =====================================================

-- 15. Sẩy thai / Đẻ non
('Sẩy thai, đẻ non ở heo nái',
 'sẩy thai,đẻ non,heo nái sẩy thai,heo nái đẻ non,mất thai,thai chết',
 'Sẩy thai, đẻ non ở heo nái có thể do nhiều nguyên nhân nghiêm trọng. Nghi ngờ: Bệnh tai xanh (PRRS), Bệnh dịch tả heo châu Phi (ASF), Nhiễm trùng, Bệnh Parvovirus, Bệnh Leptospirosis, Ngộ độc, Stress, Suy dinh dưỡng. Follow-up: Có sốt không? Có bỏ ăn không? Có chảy dịch âm đạo không? Có các heo nái khác bị không? Cần đưa heo nái đến bác sĩ thú y ngay để kiểm tra và điều trị, đồng thời cách ly để tránh lây lan.',
 'SEVERE',
 TRUE,
 NOW());

-- Kiểm tra dữ liệu đã insert
SELECT id, title, keywords, severity_level FROM disease_pig ORDER BY created_date DESC LIMIT 15;
