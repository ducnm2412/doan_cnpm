-- =====================================================
-- Script INSERT các keyword triệu chứng phổ biến vào bảng disease_monkey
-- =====================================================

USE animalhospital;

-- Insert các triệu chứng phổ biến với keywords
INSERT INTO disease_monkey (title, keywords, content, severity_level, is_active, created_date) VALUES

-- =====================================================
-- 1. TIÊU HÓA (RẤT HAY GẶP Ở KHỈ)
-- =====================================================

-- 1. Tiêu chảy / Đi phân lỏng
('Tiêu chảy, đi phân lỏng ở khỉ',
 'tiêu chảy,đi phân lỏng,đi ngoài lỏng,phân lỏng,đi cầu lỏng,ỉa chảy,phân không thành khuôn',
 'Tiêu chảy là triệu chứng rất phổ biến ở khỉ, có thể do nhiều nguyên nhân. Nghi ngờ: Nhiễm khuẩn đường ruột, Ký sinh trùng đường ruột (giun, sán), Thay đổi thức ăn đột ngột, Ngộ độc thức ăn, Bệnh đường ruột, Nhiễm trùng đường tiêu hóa. Follow-up: Có nôn không? Có bỏ ăn không? Có phân có máu không? Có mất nước không? Nếu tiêu chảy kéo dài hoặc kèm máu, cần đưa khỉ đến bác sĩ thú y ngay.',
 'MODERATE',
 TRUE,
 NOW()),

-- 2. Nôn / Ói / Ói ra thức ăn
('Nôn, ói, ói ra thức ăn ở khỉ',
 'nôn,ói,ói ra thức ăn,ói ra bọt,ói vàng,buồn nôn,nôn mửa,ói khan',
 'Nôn và ói là triệu chứng phổ biến ở khỉ, có thể do nhiều nguyên nhân. Nghi ngờ: Nhiễm khuẩn đường ruột, Ngộ độc thức ăn, Nuốt dị vật, Bệnh đường ruột, Nhiễm trùng đường tiêu hóa, Ký sinh trùng. Follow-up: Có tiêu chảy không? Có bỏ ăn không? Có bụng chướng không? Có đau không? Nếu nôn kéo dài hoặc kèm máu, cần đưa khỉ đến bác sĩ thú y ngay.',
 'MODERATE',
 TRUE,
 NOW()),

-- 3. Bỏ ăn / Biếng ăn / Ăn ít
('Bỏ ăn, biếng ăn, ăn ít ở khỉ',
 'bỏ ăn,biếng ăn,ăn ít,không ăn,chán ăn,không thèm ăn,ăn kém,không chịu ăn',
 'Bỏ ăn ở khỉ có thể do nhiều nguyên nhân nghiêm trọng. Nghi ngờ: Nhiễm trùng, Bệnh tiêu hóa, Tắc nghẽn đường ruột, Ngộ độc, Stress, Bệnh răng miệng, Nhiệt độ môi trường không phù hợp. Follow-up: Có nôn không? Có tiêu chảy không? Có sụt cân không? Có lờ đờ không? Có sốt không? Nếu khỉ bỏ ăn quá 24-48 giờ, cần đưa đến bác sĩ thú y ngay.',
 'MODERATE',
 TRUE,
 NOW()),

-- 4. Phân có máu / Phân đen
('Phân có máu, phân đen ở khỉ',
 'phân có máu,phân đen,đi ngoài ra máu,máu trong phân,phân màu đen,phân đen như nhựa đường',
 'Phân có máu hoặc phân đen ở khỉ là dấu hiệu nghiêm trọng của xuất huyết đường tiêu hóa. Nghi ngờ: Viêm đường ruột nặng, Loét dạ dày, Ký sinh trùng đường ruột, Khối u đường tiêu hóa, Ngộ độc. Follow-up: Có tiêu chảy không? Có nôn không? Có bỏ ăn không? Có đau không? Cần đưa khỉ đến bác sĩ thú y ngay để kiểm tra và điều trị.',
 'SEVERE',
 TRUE,
 NOW()),

-- =====================================================
-- 2. HÔ HẤP
-- =====================================================

-- 5. Ho / Ho khan / Ho có đờm
('Ho, ho khan, ho có đờm ở khỉ',
 'ho,ho khan,ho có đờm,ho liên tục,ho về đêm,ho nhiều,ho kéo dài',
 'Ho ở khỉ có thể do nhiều nguyên nhân. Nghi ngờ: Nhiễm trùng đường hô hấp, Viêm phổi, Viêm phế quản, Nhiễm khuẩn đường hô hấp, Bệnh hô hấp do virus, Dị vật đường thở. Follow-up: Có khó thở không? Có chảy nước mũi không? Có sốt không? Có bỏ ăn không? Nếu ho kéo dài hoặc kèm khó thở, cần đưa khỉ đến bác sĩ thú y để kiểm tra.',
 'MODERATE',
 TRUE,
 NOW()),

-- 6. Khó thở / Thở gấp / Thở nhanh
('Khó thở, thở gấp, thở nhanh ở khỉ',
 'khó thở,thở gấp,thở nhanh,thở khó,thở nặng nhọc,thở hổn hển,thở há miệng',
 'Khó thở, thở gấp ở khỉ là dấu hiệu nghiêm trọng của vấn đề hô hấp. Nghi ngờ: Viêm phổi, Nhiễm trùng đường hô hấp nghiêm trọng, Bệnh hô hấp do vi khuẩn hoặc virus, Dị vật đường thở, Bệnh tim. Follow-up: Có ho không? Có chảy nước mũi không? Có tím lưỡi / lợi không? Có sốt không? Khó thở ở khỉ là trường hợp khẩn cấp, cần đưa đến bác sĩ thú y ngay.',
 'SEVERE',
 TRUE,
 NOW()),

-- 7. Chảy nước mũi / Sổ mũi
('Chảy nước mũi, sổ mũi ở khỉ',
 'chảy nước mũi,sổ mũi,nước mũi đặc,nước mũi vàng,nước mũi xanh,sổ mũi nhiều',
 'Chảy nước mũi, sổ mũi ở khỉ thường là dấu hiệu của nhiễm trùng đường hô hấp. Nghi ngờ: Nhiễm trùng đường hô hấp trên, Viêm phổi, Nhiễm khuẩn, Nhiễm virus, Cảm cúm. Follow-up: Có ho không? Có khó thở không? Có sốt không? Có bỏ ăn không? Cần đưa khỉ đến bác sĩ thú y để kiểm tra và điều trị bằng thuốc kháng sinh.',
 'MODERATE',
 TRUE,
 NOW()),

-- =====================================================
-- 3. DA LÔNG
-- =====================================================

-- 8. Rụng lông / Hói / Lông thưa
('Rụng lông, hói, lông thưa ở khỉ',
 'rụng lông,hói,hói đầu,rụng lông nhiều,lông rụng,lông thưa,trụi lông',
 'Rụng lông hoặc hói ở khỉ có thể do nhiều nguyên nhân. Nghi ngờ: Nấm da (Ringworm), Ký sinh trùng ngoài da (ve, rận, bọ chét), Viêm da dị ứng, Nhiễm trùng da, Rối loạn nội tiết, Stress. Follow-up: Có ngứa / gãi nhiều không? Có da đỏ / viêm da không? Có vảy / gàu không? Có mụn / ghẻ không? Cần đưa khỉ đến bác sĩ thú y để kiểm tra và điều trị.',
 'MODERATE',
 TRUE,
 NOW()),

-- 9. Ngứa / Gãi nhiều
('Ngứa, gãi nhiều ở khỉ',
 'ngứa,gãi nhiều,gãi liên tục,ngứa ngáy,ngứa da,khỉ gãi nhiều',
 'Ngứa và gãi nhiều là triệu chứng phổ biến ở khỉ, có thể do nhiều nguyên nhân. Nghi ngờ: Nấm da, Ký sinh trùng ngoài da (ve, rận, bọ chét), Viêm da dị ứng, Nhiễm trùng da, Dị ứng. Follow-up: Có rụng lông không? Có da đỏ / viêm da không? Có vảy / gàu không? Có mụn / ghẻ không? Nếu gãi quá nhiều gây trầy xước, cần đưa khỉ đến bác sĩ thú y để điều trị.',
 'MODERATE',
 TRUE,
 NOW()),

-- 10. Da đỏ / Viêm da / Da sưng
('Da đỏ, viêm da, da sưng ở khỉ',
 'da đỏ,viêm da,da sưng,da bị đỏ,da viêm,da kích ứng,da sưng đỏ',
 'Da đỏ, viêm da ở khỉ có thể do nhiều nguyên nhân. Nghi ngờ: Nhiễm trùng da, Nhiễm nấm, Nhiễm khuẩn, Dị ứng, Ký sinh trùng ngoài da, Chấn thương da. Follow-up: Có ngứa / gãi nhiều không? Có rụng lông không? Có chảy dịch không? Có mùi hôi không? Cần đưa khỉ đến bác sĩ thú y để kiểm tra và điều trị bằng thuốc kháng sinh hoặc kháng viêm.',
 'MODERATE',
 TRUE,
 NOW()),

-- =====================================================
-- 4. MẮT
-- =====================================================

-- 11. Mắt đỏ / Chảy ghèn mắt / Chảy nước mắt
('Mắt đỏ, chảy ghèn mắt, chảy nước mắt ở khỉ',
 'mắt đỏ,chảy ghèn mắt,chảy nước mắt,ghèn mắt,nước mắt nhiều,viêm mắt,đỏ mắt',
 'Mắt đỏ, chảy ghèn mắt ở khỉ có thể do nhiều nguyên nhân. Nghi ngờ: Viêm kết mạc, Nhiễm trùng mắt, Nhiễm khuẩn mắt, Dị vật trong mắt, Tắc ống lệ, Bệnh mắt do virus. Follow-up: Có sưng mắt không? Có mắt đóng không? Có ho / khó thở không? Có sốt không? Cần đưa khỉ đến bác sĩ thú y để kiểm tra và điều trị.',
 'MODERATE',
 TRUE,
 NOW()),

-- 12. Mắt đóng / Mắt sưng
('Mắt đóng, mắt sưng ở khỉ',
 'mắt đóng,mắt sưng,mắt bị sưng,sưng mắt,phù mắt,mí mắt sưng,mắt nhắm',
 'Mắt đóng, mắt sưng ở khỉ có thể do nhiều nguyên nhân. Nghi ngờ: Nhiễm trùng mắt, Viêm mắt, Nhiễm khuẩn mắt, Dị vật trong mắt, Chấn thương mắt, Áp xe mắt. Follow-up: Có mắt đỏ không? Có chảy ghèn mắt không? Có đau không? Có sốt không? Cần đưa khỉ đến bác sĩ thú y để kiểm tra và điều trị ngay.',
 'MODERATE',
 TRUE,
 NOW()),

-- =====================================================
-- 5. HÀNH VI VÀ THẦN KINH
-- =====================================================

-- 13. Lờ đờ / Mệt mỏi / Không hoạt động
('Lờ đờ, mệt mỏi, không hoạt động ở khỉ',
 'lờ đờ,mệt mỏi,không hoạt động,không chạy nhảy,ngủ nhiều,không phản ứng,kiệt sức',
 'Lờ đờ, mệt mỏi ở khỉ có thể do nhiều nguyên nhân nghiêm trọng. Nghi ngờ: Nhiễm trùng, Bệnh hô hấp, Bệnh tiêu hóa, Ngộ độc, Sốt, Thiếu máu, Suy dinh dưỡng. Follow-up: Có bỏ ăn không? Có sốt không? Có nôn / tiêu chảy không? Có khó thở không? Cần đưa khỉ đến bác sĩ thú y để kiểm tra và xác định nguyên nhân.',
 'MODERATE',
 TRUE,
 NOW()),

-- 14. Sốt / Nóng / Run
('Sốt, nóng, run ở khỉ',
 'sốt,nóng,run,run rẩy,thân nhiệt cao,người nóng,phát sốt',
 'Sốt, nóng, run ở khỉ là dấu hiệu của nhiễm trùng hoặc bệnh tật. Nghi ngờ: Nhiễm trùng, Bệnh hô hấp, Bệnh tiêu hóa, Nhiễm khuẩn, Nhiễm virus, Ngộ độc. Follow-up: Có nôn / tiêu chảy không? Có ho / khó thở không? Có bỏ ăn không? Có lờ đờ không? Cần đo nhiệt độ và đưa khỉ đến bác sĩ thú y nếu sốt cao hoặc kéo dài.',
 'MODERATE',
 TRUE,
 NOW()),

-- 15. Co giật / Động kinh
('Co giật, động kinh ở khỉ',
 'co giật,động kinh,lên cơn co giật,co giật toàn thân,run,run rẩy',
 'Co giật, động kinh ở khỉ là dấu hiệu nghiêm trọng. Nghi ngờ: Bệnh thần kinh, Nhiễm trùng não, Ngộ độc, Chấn thương đầu, U não, Bệnh cột sống, Thiếu canxi. Follow-up: Có mất thăng bằng không? Có quay vòng vòng không? Có nghiêng đầu không? Có nôn không? Trong cơn co giật, cần giữ khỉ an toàn và đưa đến bác sĩ thú y ngay sau cơn co giật để kiểm tra.',
 'SEVERE',
 TRUE,
 NOW());

-- Kiểm tra dữ liệu đã insert
SELECT id, title, keywords, severity_level FROM disease_monkey ORDER BY created_date DESC LIMIT 15;
