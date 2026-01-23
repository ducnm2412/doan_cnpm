-- =====================================================
-- Script INSERT các keyword triệu chứng phổ biến vào bảng disease_goat
-- =====================================================

USE animalhospital;

-- Insert các triệu chứng phổ biến với keywords
INSERT INTO disease_goat (title, keywords, content, severity_level, is_active, created_date) VALUES

-- =====================================================
-- 1. TIÊU HÓA (RẤT HAY GẶP Ở DÊ)
-- =====================================================

-- 1. Tiêu chảy / Đi phân lỏng
('Tiêu chảy, đi phân lỏng ở dê',
 'tiêu chảy,đi phân lỏng,đi ngoài lỏng,phân lỏng,đi cầu lỏng,ỉa chảy,phân không thành khuôn,phân nước',
 'Tiêu chảy là triệu chứng rất phổ biến ở dê, đặc biệt ở dê con. Nghi ngờ: Nhiễm khuẩn đường ruột (E.coli, Salmonella), Coccidiosis (bệnh cầu trùng), Ký sinh trùng đường ruột (giun, sán), Thay đổi thức ăn đột ngột, Ngộ độc thức ăn, Bệnh đường ruột. Follow-up: Có nôn không? Có bỏ ăn không? Có phân có máu không? Có mất nước không? Có sốt không? Tiêu chảy ở dê con có thể gây tử vong nhanh, cần điều trị ngay.',
 'MODERATE',
 TRUE,
 NOW()),

-- 2. Bỏ ăn / Biếng ăn / Ăn ít
('Bỏ ăn, biếng ăn, ăn ít ở dê',
 'bỏ ăn,biếng ăn,ăn ít,không ăn,chán ăn,không thèm ăn,ăn kém,không chịu ăn',
 'Bỏ ăn ở dê có thể do nhiều nguyên nhân nghiêm trọng. Nghi ngờ: Sốt / nhiễm trùng, Bệnh hô hấp, Bệnh tiêu hóa, Bệnh tụ huyết trùng, Bệnh viêm vú, Ngộ độc, Stress, Bệnh răng miệng. Follow-up: Có sốt không? Có ho / khó thở không? Có nôn / tiêu chảy không? Có sưng khớp không? Có sụt cân không? Nếu dê bỏ ăn quá 24 giờ, cần đưa đến bác sĩ thú y ngay.',
 'MODERATE',
 TRUE,
 NOW()),

-- 3. Phân có máu / Phân đen
('Phân có máu, phân đen ở dê',
 'phân có máu,phân đen,đi ngoài ra máu,máu trong phân,phân màu đen,phân đen như nhựa đường',
 'Phân có máu hoặc phân đen ở dê là dấu hiệu nghiêm trọng của xuất huyết đường tiêu hóa. Nghi ngờ: Viêm đường ruột nặng, Coccidiosis nặng, Loét dạ dày, Ký sinh trùng đường ruột, Khối u đường tiêu hóa, Ngộ độc. Follow-up: Có tiêu chảy không? Có nôn không? Có bỏ ăn không? Có sốt không? Cần đưa dê đến bác sĩ thú y ngay để kiểm tra và điều trị.',
 'SEVERE',
 TRUE,
 NOW()),

-- 4. Bụng chướng / Bụng phình
('Bụng chướng, bụng phình ở dê',
 'bụng chướng,bụng phình,bụng to,bụng căng,chướng bụng,phình bụng,bụng cứng',
 'Bụng chướng ở dê có thể do nhiều nguyên nhân nghiêm trọng. Nghi ngờ: Tắc nghẽn đường ruột, Nuốt dị vật, Bệnh đường ruột, Tích tụ khí trong dạ dày, Bệnh dạ dày, Nhiễm khuẩn đường ruột. Follow-up: Có bỏ ăn không? Có không đi ngoài được không? Có đau không? Có nôn không? Bụng chướng ở dê là trường hợp cấp cứu, cần đưa đến bác sĩ thú y ngay lập tức.',
 'SEVERE',
 TRUE,
 NOW()),

-- =====================================================
-- 2. HÔ HẤP
-- =====================================================

-- 5. Ho / Ho khan / Ho có đờm
('Ho, ho khan, ho có đờm ở dê',
 'ho,ho khan,ho có đờm,ho liên tục,ho về đêm,ho nhiều,ho kéo dài',
 'Ho ở dê có thể do nhiều nguyên nhân. Nghi ngờ: Viêm phổi, Viêm phế quản, Nhiễm khuẩn đường hô hấp, Bệnh hô hấp do vi khuẩn hoặc virus, Mycoplasma, Dị vật đường thở. Follow-up: Có khó thở không? Có chảy nước mũi không? Có sốt không? Có bỏ ăn không? Nếu ho kéo dài hoặc kèm khó thở, cần đưa dê đến bác sĩ thú y để kiểm tra.',
 'MODERATE',
 TRUE,
 NOW()),

-- 6. Khó thở / Thở gấp / Thở nhanh
('Khó thở, thở gấp, thở nhanh ở dê',
 'khó thở,thở gấp,thở nhanh,thở khó,thở nặng nhọc,thở hổn hển,thở há miệng',
 'Khó thở, thở gấp ở dê là dấu hiệu nghiêm trọng của bệnh hô hấp. Nghi ngờ: Viêm phổi nặng, Nhiễm trùng đường hô hấp nghiêm trọng, Bệnh hô hấp do vi khuẩn hoặc virus, Dị vật đường thở, Bệnh tim. Follow-up: Có ho không? Có chảy nước mũi không? Có tím lưỡi / tím lợi không? Có sốt không? Khó thở ở dê là trường hợp khẩn cấp, cần đưa đến bác sĩ thú y ngay.',
 'SEVERE',
 TRUE,
 NOW()),

-- 7. Chảy nước mũi / Sổ mũi
('Chảy nước mũi, sổ mũi ở dê',
 'chảy nước mũi,sổ mũi,nước mũi đặc,nước mũi vàng,nước mũi xanh,sổ mũi nhiều',
 'Chảy nước mũi, sổ mũi ở dê thường là dấu hiệu của nhiễm trùng đường hô hấp. Nghi ngờ: Viêm phổi, Nhiễm khuẩn đường hô hấp, Bệnh hô hấp do vi khuẩn hoặc virus, Mycoplasma, Cảm cúm. Follow-up: Có ho không? Có khó thở không? Có sốt không? Có bỏ ăn không? Cần đưa dê đến bác sĩ thú y để kiểm tra và điều trị bằng thuốc kháng sinh.',
 'MODERATE',
 TRUE,
 NOW()),

-- =====================================================
-- 3. DA VÀ LÔNG
-- =====================================================

-- 8. Rụng lông / Hói / Lông thưa
('Rụng lông, hói, lông thưa ở dê',
 'rụng lông,hói,hói đầu,rụng lông nhiều,lông rụng,lông thưa,trụi lông',
 'Rụng lông hoặc hói ở dê có thể do nhiều nguyên nhân. Nghi ngờ: Nấm da (Ringworm), Ký sinh trùng ngoài da (ve, rận, bọ chét), Viêm da dị ứng, Nhiễm trùng da, Rối loạn nội tiết, Stress, Suy dinh dưỡng. Follow-up: Có ngứa / gãi nhiều không? Có da đỏ / viêm da không? Có vảy / gàu không? Có mụn / ghẻ không? Cần đưa dê đến bác sĩ thú y để kiểm tra và điều trị.',
 'MODERATE',
 TRUE,
 NOW()),

-- 9. Ngứa / Gãi nhiều
('Ngứa, gãi nhiều ở dê',
 'ngứa,gãi nhiều,gãi liên tục,ngứa ngáy,ngứa da,dê gãi nhiều',
 'Ngứa và gãi nhiều là triệu chứng phổ biến ở dê, có thể do nhiều nguyên nhân. Nghi ngờ: Nấm da, Ký sinh trùng ngoài da (ve, rận, bọ chét), Viêm da dị ứng, Nhiễm trùng da, Dị ứng, Bệnh ghẻ. Follow-up: Có rụng lông không? Có da đỏ / viêm da không? Có vảy / gàu không? Có mụn / ghẻ không? Nếu gãi quá nhiều gây trầy xước, cần đưa dê đến bác sĩ thú y để điều trị.',
 'MODERATE',
 TRUE,
 NOW()),

-- 10. Da đỏ / Viêm da / Da sưng
('Da đỏ, viêm da, da sưng ở dê',
 'da đỏ,viêm da,da sưng,da bị đỏ,da viêm,da kích ứng,da sưng đỏ',
 'Da đỏ, viêm da ở dê có thể do nhiều nguyên nhân. Nghi ngờ: Nhiễm trùng da, Nhiễm nấm, Nhiễm khuẩn, Dị ứng, Ký sinh trùng ngoài da, Chấn thương da, Bệnh ghẻ. Follow-up: Có ngứa / gãi nhiều không? Có rụng lông không? Có chảy dịch không? Có mùi hôi không? Cần đưa dê đến bác sĩ thú y để kiểm tra và điều trị bằng thuốc kháng sinh hoặc kháng viêm.',
 'MODERATE',
 TRUE,
 NOW()),

-- =====================================================
-- 4. SỐT VÀ NHIỄM TRÙNG
-- =====================================================

-- 11. Sốt / Nóng / Run
('Sốt, nóng, run ở dê',
 'sốt,nóng,run,run rẩy,thân nhiệt cao,người nóng,phát sốt',
 'Sốt, nóng, run ở dê là dấu hiệu của nhiễm trùng hoặc bệnh tật. Nghi ngờ: Nhiễm trùng, Bệnh hô hấp, Bệnh tiêu hóa, Bệnh tụ huyết trùng, Bệnh viêm vú, Nhiễm khuẩn, Nhiễm virus. Follow-up: Có nôn / tiêu chảy không? Có ho / khó thở không? Có bỏ ăn không? Có sưng khớp không? Cần đo nhiệt độ và đưa dê đến bác sĩ thú y nếu sốt cao hoặc kéo dài.',
 'MODERATE',
 TRUE,
 NOW()),

-- 12. Sưng khớp / Khập khiễng
('Sưng khớp, khập khiễng ở dê',
 'sưng khớp,khập khiễng,đi khập khiễng,đi cà nhắc,đi không đều chân,khớp sưng,khớp bị sưng',
 'Sưng khớp, khập khiễng ở dê có thể do nhiều nguyên nhân. Nghi ngờ: Viêm khớp, Nhiễm trùng khớp, Chấn thương, Bệnh chân và miệng (FMD), Nhiễm khuẩn, Bệnh tụ huyết trùng. Follow-up: Có sốt không? Có bỏ ăn không? Có sưng ở chân khác không? Có đau không? Cần đưa dê đến bác sĩ thú y để kiểm tra và điều trị.',
 'MODERATE',
 TRUE,
 NOW()),

-- =====================================================
-- 5. VIÊM VÚ (QUAN TRỌNG Ở DÊ CÁI)
-- =====================================================

-- 13. Vú sưng / Vú đỏ / Viêm vú
('Vú sưng, vú đỏ, viêm vú ở dê cái',
 'vú sưng,vú đỏ,viêm vú,vú bị sưng,vú viêm,vú đau,sưng vú',
 'Vú sưng, vú đỏ, viêm vú ở dê cái là bệnh rất phổ biến và nghiêm trọng. Nghi ngờ: Viêm vú (Mastitis), Nhiễm trùng vú, Nhiễm khuẩn vú, Tắc ống dẫn sữa, Áp xe vú. Follow-up: Có sốt không? Có sữa có máu không? Có sữa đặc / có mủ không? Có bỏ ăn không? Có đau khi vắt sữa không? Cần đưa dê cái đến bác sĩ thú y ngay để điều trị bằng thuốc kháng sinh.',
 'SEVERE',
 TRUE,
 NOW()),

-- 14. Sữa có máu / Sữa đặc / Sữa có mủ
('Sữa có máu, sữa đặc, sữa có mủ ở dê cái',
 'sữa có máu,sữa đặc,sữa có mủ,sữa màu vàng,sữa màu xanh,sữa không bình thường',
 'Sữa có máu, sữa đặc, sữa có mủ ở dê cái là dấu hiệu nghiêm trọng của viêm vú. Nghi ngờ: Viêm vú nặng (Mastitis), Nhiễm trùng vú nghiêm trọng, Áp xe vú, Nhiễm khuẩn vú. Follow-up: Có vú sưng / đỏ không? Có sốt không? Có bỏ ăn không? Có đau khi vắt sữa không? Cần đưa dê cái đến bác sĩ thú y ngay để điều trị và có thể cần ngừng vắt sữa từ vú bị viêm.',
 'SEVERE',
 TRUE,
 NOW()),

-- =====================================================
-- 6. THẦN KINH VÀ HÀNH VI
-- =====================================================

-- 15. Lờ đờ / Mệt mỏi / Không hoạt động
('Lờ đờ, mệt mỏi, không hoạt động ở dê',
 'lờ đờ,mệt mỏi,không hoạt động,không di chuyển,ngủ nhiều,không phản ứng,kiệt sức',
 'Lờ đờ, mệt mỏi ở dê có thể do nhiều nguyên nhân nghiêm trọng. Nghi ngờ: Nhiễm trùng, Bệnh hô hấp, Bệnh tiêu hóa, Sốt, Ngộ độc, Thiếu máu, Suy dinh dưỡng, Bệnh tụ huyết trùng. Follow-up: Có bỏ ăn không? Có sốt không? Có nôn / tiêu chảy không? Có khó thở không? Có sưng vú không? Cần đưa dê đến bác sĩ thú y để kiểm tra và xác định nguyên nhân.',
 'MODERATE',
 TRUE,
 NOW());

-- Kiểm tra dữ liệu đã insert
SELECT id, title, keywords, severity_level FROM disease_goat ORDER BY created_date DESC LIMIT 15;
