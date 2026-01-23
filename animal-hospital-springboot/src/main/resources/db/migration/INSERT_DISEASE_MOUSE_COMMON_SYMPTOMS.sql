-- =====================================================
-- Script INSERT các keyword triệu chứng phổ biến vào bảng disease_mouse
-- =====================================================

USE animalhospital;

-- Insert các triệu chứng phổ biến với keywords
INSERT INTO disease_mouse (title, keywords, content, severity_level, is_active, created_date) VALUES

-- =====================================================
-- 1. TIÊU HÓA (RẤT HAY GẶP Ở CHUỘT)
-- =====================================================

-- 1. Tiêu chảy / Đi phân lỏng
('Tiêu chảy, đi phân lỏng ở chuột',
 'tiêu chảy,đi phân lỏng,đi ngoài lỏng,phân lỏng,đi cầu lỏng,ỉa chảy,phân không thành khuôn,phân nước',
 'Tiêu chảy là triệu chứng rất phổ biến ở chuột, có thể do nhiều nguyên nhân. Nghi ngờ: Nhiễm khuẩn đường ruột (E.coli, Salmonella), Ký sinh trùng đường ruột (giun, sán), Thay đổi thức ăn đột ngột, Ngộ độc thức ăn, Bệnh đường ruột, Nhiễm trùng đường tiêu hóa. Follow-up: Có nôn không? Có bỏ ăn không? Có phân có máu không? Có mất nước không? Có sốt không? Tiêu chảy ở chuột có thể gây tử vong nhanh, cần điều trị ngay.',
 'MODERATE',
 TRUE,
 NOW()),

-- 2. Bỏ ăn / Biếng ăn / Ăn ít
('Bỏ ăn, biếng ăn, ăn ít ở chuột',
 'bỏ ăn,biếng ăn,ăn ít,không ăn,chán ăn,không thèm ăn,ăn kém,không chịu ăn',
 'Bỏ ăn ở chuột có thể do nhiều nguyên nhân nghiêm trọng. Nghi ngờ: Sốt / nhiễm trùng, Bệnh hô hấp, Bệnh tiêu hóa, Bệnh răng miệng (răng mọc quá dài), Ngộ độc, Stress, Nhiệt độ môi trường không phù hợp. Follow-up: Có sốt không? Có ho / khó thở không? Có nôn / tiêu chảy không? Có sụt cân không? Có khó nhai không? Nếu chuột bỏ ăn quá 24 giờ, cần đưa đến bác sĩ thú y ngay.',
 'MODERATE',
 TRUE,
 NOW()),

-- 3. Phân có máu / Phân đen
('Phân có máu, phân đen ở chuột',
 'phân có máu,phân đen,đi ngoài ra máu,máu trong phân,phân màu đen,phân đen như nhựa đường',
 'Phân có máu hoặc phân đen ở chuột là dấu hiệu nghiêm trọng của xuất huyết đường tiêu hóa. Nghi ngờ: Viêm đường ruột nặng, Loét dạ dày, Ký sinh trùng đường ruột, Khối u đường tiêu hóa, Ngộ độc, Nhiễm khuẩn đường ruột nặng. Follow-up: Có tiêu chảy không? Có nôn không? Có bỏ ăn không? Có sốt không? Cần đưa chuột đến bác sĩ thú y ngay để kiểm tra và điều trị.',
 'SEVERE',
 TRUE,
 NOW()),

-- 4. Táo bón / Không đi ngoài
('Táo bón, không đi ngoài ở chuột',
 'táo bón,không đi ngoài,rặn,đi ngoài khó,phân cứng,không đi cầu được',
 'Táo bón hoặc không đi ngoài ở chuột là dấu hiệu nghiêm trọng. Nghi ngờ: Tắc nghẽn đường ruột, Nuốt dị vật, Thiếu nước, Thiếu chất xơ trong thức ăn, Bệnh đường ruột, Nhiễm ký sinh trùng. Follow-up: Có bỏ ăn không? Có nôn không? Có bụng chướng không? Có đau không? Nếu chuột không đi ngoài quá 2-3 ngày, cần đưa đến bác sĩ thú y ngay.',
 'MODERATE',
 TRUE,
 NOW()),

-- =====================================================
-- 2. HÔ HẤP
-- =====================================================

-- 5. Ho / Khó thở / Thở gấp
('Ho, khó thở, thở gấp ở chuột',
 'ho,ho khan,khó thở,thở gấp,thở nhanh,thở khó,thở nặng nhọc,thở hổn hển',
 'Ho, khó thở, thở gấp ở chuột là dấu hiệu nghiêm trọng của bệnh hô hấp. Nghi ngờ: Viêm phổi, Nhiễm khuẩn đường hô hấp, Bệnh hô hấp do vi khuẩn hoặc virus, Dị vật đường thở, Bệnh tim, Nhiễm trùng đường hô hấp trên. Follow-up: Có chảy nước mũi không? Có sốt không? Có tím lưỡi / lợi không? Có bỏ ăn không? Khó thở ở chuột là trường hợp khẩn cấp, cần đưa đến bác sĩ thú y ngay.',
 'SEVERE',
 TRUE,
 NOW()),

-- 6. Chảy nước mũi / Sổ mũi
('Chảy nước mũi, sổ mũi ở chuột',
 'chảy nước mũi,sổ mũi,nước mũi đặc,nước mũi vàng,nước mũi xanh,sổ mũi nhiều',
 'Chảy nước mũi, sổ mũi ở chuột thường là dấu hiệu của nhiễm trùng đường hô hấp. Nghi ngờ: Viêm phổi, Nhiễm khuẩn đường hô hấp, Bệnh hô hấp do vi khuẩn hoặc virus, Cảm cúm, Nhiễm trùng đường hô hấp trên. Follow-up: Có ho không? Có khó thở không? Có sốt không? Có bỏ ăn không? Cần đưa chuột đến bác sĩ thú y để kiểm tra và điều trị bằng thuốc kháng sinh.',
 'MODERATE',
 TRUE,
 NOW()),

-- =====================================================
-- 3. DA LÔNG
-- =====================================================

-- 7. Rụng lông / Hói / Lông thưa
('Rụng lông, hói, lông thưa ở chuột',
 'rụng lông,hói,hói đầu,rụng lông nhiều,lông rụng,lông thưa,trụi lông',
 'Rụng lông hoặc hói ở chuột có thể do nhiều nguyên nhân. Nghi ngờ: Nấm da (Ringworm), Ký sinh trùng ngoài da (ve, rận, bọ chét), Viêm da dị ứng, Nhiễm trùng da, Rối loạn nội tiết, Stress, Suy dinh dưỡng. Follow-up: Có ngứa / gãi nhiều không? Có da đỏ / viêm da không? Có vảy / gàu không? Có mụn / ghẻ không? Cần đưa chuột đến bác sĩ thú y để kiểm tra và điều trị.',
 'MODERATE',
 TRUE,
 NOW()),

-- 8. Ngứa / Gãi nhiều
('Ngứa, gãi nhiều ở chuột',
 'ngứa,gãi nhiều,gãi liên tục,ngứa ngáy,ngứa da,chuột gãi nhiều',
 'Ngứa và gãi nhiều là triệu chứng phổ biến ở chuột, có thể do nhiều nguyên nhân. Nghi ngờ: Nấm da, Ký sinh trùng ngoài da (ve, rận, bọ chét), Viêm da dị ứng, Nhiễm trùng da, Dị ứng. Follow-up: Có rụng lông không? Có da đỏ / viêm da không? Có vảy / gàu không? Có mụn / ghẻ không? Nếu gãi quá nhiều gây trầy xước, cần đưa chuột đến bác sĩ thú y để điều trị.',
 'MODERATE',
 TRUE,
 NOW()),

-- 9. Da đỏ / Viêm da / Da sưng
('Da đỏ, viêm da, da sưng ở chuột',
 'da đỏ,viêm da,da sưng,da bị đỏ,da viêm,da kích ứng,da sưng đỏ',
 'Da đỏ, viêm da ở chuột có thể do nhiều nguyên nhân. Nghi ngờ: Nhiễm trùng da, Nhiễm nấm, Nhiễm khuẩn, Dị ứng, Ký sinh trùng ngoài da, Chấn thương da, Bệnh ghẻ. Follow-up: Có ngứa / gãi nhiều không? Có rụng lông không? Có chảy dịch không? Có mùi hôi không? Cần đưa chuột đến bác sĩ thú y để kiểm tra và điều trị bằng thuốc kháng sinh hoặc kháng viêm.',
 'MODERATE',
 TRUE,
 NOW()),

-- =====================================================
-- 4. MẮT
-- =====================================================

-- 10. Mắt đỏ / Chảy ghèn / Chảy nước mắt
('Mắt đỏ, chảy ghèn, chảy nước mắt ở chuột',
 'mắt đỏ,chảy ghèn,chảy nước mắt,ghèn mắt,nước mắt nhiều,viêm mắt,đỏ mắt',
 'Mắt đỏ, chảy ghèn, chảy nước mắt ở chuột có thể do nhiều nguyên nhân. Nghi ngờ: Viêm kết mạc, Nhiễm trùng mắt, Nhiễm khuẩn mắt, Dị vật trong mắt, Tắc ống lệ, Chấn thương mắt. Follow-up: Có một mắt hay cả hai mắt? Có sưng mắt không? Có đau không? Có ho / khó thở không? Cần đưa chuột đến bác sĩ thú y để kiểm tra và điều trị.',
 'MODERATE',
 TRUE,
 NOW()),

-- 11. Mắt đóng / Mắt sưng
('Mắt đóng, mắt sưng ở chuột',
 'mắt đóng,mắt sưng,mắt bị sưng,sưng mắt,phù mắt,mí mắt sưng,mắt nhắm',
 'Mắt đóng hoặc mắt sưng ở chuột có thể do nhiều nguyên nhân. Nghi ngờ: Nhiễm trùng mắt, Áp xe quanh mắt, Dị vật trong mắt, Chấn thương mắt, Nhiễm khuẩn mắt, Khối u quanh mắt. Follow-up: Có mắt đỏ không? Có chảy ghèn không? Có đau không? Có một mắt hay cả hai mắt? Cần đưa chuột đến bác sĩ thú y ngay để kiểm tra và điều trị.',
 'MODERATE',
 TRUE,
 NOW()),

-- =====================================================
-- 5. RĂNG MIỆNG (QUAN TRỌNG Ở CHUỘT)
-- =====================================================

-- 12. Răng mọc quá dài / Khó nhai
('Răng mọc quá dài, khó nhai ở chuột',
 'răng mọc quá dài,khó nhai,nhai một bên,nhai khó,nhai đau,răng dài',
 'Răng mọc quá dài hoặc khó nhai ở chuột là vấn đề rất phổ biến vì răng chuột mọc liên tục. Nghi ngờ: Răng mọc quá dài (Malocclusion), Áp xe răng, Loét miệng, Dị vật trong miệng, Nhiễm trùng răng miệng. Follow-up: Có bỏ ăn không? Có chảy nước dãi không? Có hôi miệng không? Có sưng mặt không? Cần đưa chuột đến bác sĩ thú y ngay để mài răng và điều trị.',
 'MODERATE',
 TRUE,
 NOW()),

-- 13. Chảy nước dãi / Hôi miệng
('Chảy nước dãi, hôi miệng ở chuột',
 'chảy nước dãi,nước dãi nhiều,chảy dãi,hôi miệng,miệng hôi,mùi hôi miệng',
 'Chảy nước dãi và hôi miệng ở chuột thường là dấu hiệu của bệnh răng miệng nghiêm trọng. Nghi ngờ: Răng mọc quá dài gây loét, Áp xe răng, Nhiễm trùng răng miệng, Loét miệng, Bệnh đường tiêu hóa. Follow-up: Có khó nhai không? Có bỏ ăn không? Có sưng mặt không? Cần kiểm tra răng miệng tại phòng khám thú y để xác định nguyên nhân.',
 'MODERATE',
 TRUE,
 NOW()),

-- =====================================================
-- 6. THẦN KINH VÀ HÀNH VI
-- =====================================================

-- 14. Co giật / Động kinh
('Co giật, động kinh ở chuột',
 'co giật,động kinh,lên cơn co giật,co giật toàn thân,run,run rẩy',
 'Co giật, động kinh ở chuột là dấu hiệu nghiêm trọng. Nghi ngờ: Bệnh thần kinh, Nhiễm trùng não, Ngộ độc, Chấn thương đầu, Khối u não, Bệnh cột sống, Thiếu canxi. Follow-up: Có mất thăng bằng không? Có quay vòng vòng không? Có nghiêng đầu không? Có nôn không? Trong cơn co giật, cần giữ chuột an toàn và đưa đến bác sĩ thú y ngay sau cơn co giật để kiểm tra.',
 'SEVERE',
 TRUE,
 NOW()),

-- 15. Lờ đờ / Mệt mỏi / Không hoạt động
('Lờ đờ, mệt mỏi, không hoạt động ở chuột',
 'lờ đờ,mệt mỏi,không hoạt động,không di chuyển,ngủ nhiều,không phản ứng,kiệt sức',
 'Lờ đờ, mệt mỏi ở chuột có thể do nhiều nguyên nhân nghiêm trọng. Nghi ngờ: Nhiễm trùng, Bệnh hô hấp, Bệnh tiêu hóa, Sốt, Ngộ độc, Thiếu máu, Suy dinh dưỡng, Bệnh răng miệng. Follow-up: Có bỏ ăn không? Có sốt không? Có nôn / tiêu chảy không? Có khó thở không? Có khó nhai không? Cần đưa chuột đến bác sĩ thú y để kiểm tra và xác định nguyên nhân.',
 'MODERATE',
 TRUE,
 NOW());

-- Kiểm tra dữ liệu đã insert
SELECT id, title, keywords, severity_level FROM disease_mouse ORDER BY created_date DESC LIMIT 15;
